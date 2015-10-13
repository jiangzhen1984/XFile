package elacier.provider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;

import org.apache.commons.codec.binary.Hex;
import org.json.simple.JSONObject;

import elacier.provider.msg.BaseMessage;
import elacier.provider.msg.Terminal;

public class IOSApnsPushProvider implements PushProvider {
	
	private ProviderConfig config;
	private SocketFactory socketFactory;
	private Socket apnsSock;
	
	private InputStream reader;
	private OutputStream writer;
	private boolean initFlag;
	
	private APNSPayloadJsonTransformer transformer;
	
	private Queue<NotificationWrapper> msgQueue;
	private Object queueLock;
	
	private PushThread pushThreadWorker;
	private Thread deamonPusher;
	

	public IOSApnsPushProvider(ProviderConfig config,
			SocketFactory socketFactory) {
		super();
		this.config = config;
		this.socketFactory = socketFactory;
		initFlag = false;
		msgQueue = new ConcurrentLinkedQueue<NotificationWrapper>();
		queueLock = new Object();
	}

	@Override
	public void init() {
		SSLSocket sslSocket;
		try {
			sslSocket = (SSLSocket) socketFactory.createSocket(config.getAPNSHost(), config.getAPNSPort());
			 String[] cipherSuites = sslSocket.getSupportedCipherSuites();
	        sslSocket.setEnabledCipherSuites(cipherSuites);
	        sslSocket.startHandshake();
	        writer = sslSocket.getOutputStream();
	        reader = sslSocket.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			//FIXME log
			return;
		}

		
       

        if (pushThreadWorker == null) {
        	pushThreadWorker = new PushThread(writer, queueLock, msgQueue);
        }
        
        if (deamonPusher != null) {
        	if (deamonPusher.isAlive()) {
        		pushThreadWorker.requestStop();
        		deamonPusher.interrupt();
        	}
        }
        
        
        deamonPusher = new Thread(pushThreadWorker);
        deamonPusher.start();
        
        apnsSock = sslSocket;
        initFlag = true;
        
	}

	@Override
	public void release() {
		if (initFlag) {
			pushThreadWorker.requestStop();
			deamonPusher.interrupt();
		}
		
		if (apnsSock != null) {
			try {
				apnsSock.close();
			} catch (IOException e) {
				e.printStackTrace();
				//FIXME log error
			}
		}

	}

	@Override
	public boolean pushNotification(BaseMessage message, Terminal terminal)
			throws IOException {
		if (!initFlag) {
			throw new RuntimeException("Provider doesn't initalize yet");
		}
		synchronized (queueLock) {
			msgQueue.add(new NotificationWrapper(message, new Terminal[]{terminal}));
		}
		pushThreadWorker.requestNotification();
		return true;
	}

	@Override
	public boolean pushNotification(BaseMessage message, List<Terminal> terminal)
			throws IOException {
		if (!initFlag) {
			throw new RuntimeException("Provider doesn't initalize yet");
		}
		synchronized (queueLock) {
			msgQueue.add(new NotificationWrapper(message, (Terminal[])terminal.toArray()));
		}
		pushThreadWorker.requestNotification();
		return true;
	}
	
	
	class NotificationWrapper {
		BaseMessage message;
		Terminal[] terminals;
		public NotificationWrapper(BaseMessage message, Terminal[] terminals) {
			super();
			this.message = message;
			this.terminals = terminals;
		}
		
	}
	
	
	class PushThread extends Thread {

		private boolean runFlag = true;
		private OutputStream out = null;
		private Object lock;
		 Queue<NotificationWrapper> queue;
		
		
		


		public PushThread(OutputStream out, Object lock,
				Queue<NotificationWrapper> queue) {
			super();
			this.out = out;
			this.lock = lock;
			this.queue = queue;
			//TODO check variable
		}


		@Override
		public void run() {
			do {
				//This worker only run in one thread, so just concurrent problem
				//Just key queue lock is enough
				NotificationWrapper nw = null;
				synchronized(lock) {
					nw = queue.poll();
					if (queue.size() <= 0 || nw == null) {
						try {
							lock.wait();
							continue;
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				
				//TODO check out stream
				if (nw == null) {
					continue;
				}
				
				JSONObject json= transformer.transform(nw.message);
				 byte[] payload = json.toString().getBytes();
				 try {
					for (Terminal ter : nw.terminals) {
				        char[] t = ter.getDeviceId().toStringValue().toCharArray();
				        byte[] b = Hex.decodeHex(t);
				        out.write(0);
				        out.write(0);
				        out.write(32);
				        out.write(b);
				        out.write(0);
				        out.write(payload.length);
				        out.write(payload);
					}
					out.flush();
				 } catch(IOException e) {
					 e.printStackTrace();
					 //FIXME check socket exception
				 }catch(Exception e) {
					 e.printStackTrace();
					 //FIXME check socket exception
				 }
				
				
			} while(runFlag);
		}
		
		
		public void requestStop() {
			runFlag = false;
			lock.notify();
		}
		
		public void requestNotification() {
			lock.notify();
		}
		
		
	}
	
	
	
	
	
}
