/**
 * 
 */
package com.xfile.comm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.xfile.model.Doctor;

/**
 * @author jiangzhen
 *
 */
public class XFileService extends Service {
	
	private static final String TAG = "XFileService";
	
	private static final int SEND_REQUEST = 1;
	
	private static final int GET_RESPONSE = 2;
	
	private NativeService mService;
	
	private Map<Key, Value> mPendingReqs = new HashMap<Key, Value>();
	
	private HandlerThread mQueue = new HandlerThread("XFileService");

	private LocalHandler mLocalHandler;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mService = new NativeService();
		mQueue.start();
		mLocalHandler = new LocalHandler(mQueue.getLooper());
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//TODO logout
		
		mLocalHandler = null;
		
		mQueue.quit();
		
	}


	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return mService;
	}
	
	
	class NativeService extends Binder implements IXFileService {

		@Override
		public void signIn(Request req, ResponseListener listener) {
			if (!(req instanceof SignInRequest)) {
				listener.onResponse(new SignInResponse(req, ResponseState.INCORRECT_PAR, null));
				return;
			}
			// Send request to remote
			mPendingReqs.put(new Key(req.mTransationId), new Value(req, listener));
			//TODO should send remote request directly
			Message.obtain(mLocalHandler, SEND_REQUEST, req).sendToTarget();
		}

		@Override
		public void signUp(Request req, ResponseListener listener) {
			// TODO Auto-generated method stub
			
		}
		
		

		@Override
		public void queryDoctors(Request req, ResponseListener listener) {
			List<Doctor> list = new ArrayList<Doctor>();
			for (int i =0; i < 20; i ++) {
				list.add(new Doctor(i, "李医生" + 1, " 皮肤科"));
			}
			listener.onResponse(new SignInResponse(req, ResponseState.SUCCESS, list));
		}

		@Override
		public void cancelWaiting(Request req) {
			if (req == null) {
				Log.e(TAG, req+" is null");
				return;
			}
			Value pending = mPendingReqs.remove(new Key(req.mTransationId));
			if (pending != null) {
				Log.i(TAG, pending.req+" is cancelled");
			}
		}

		
	}
	
	
	class Key {
		long transactionId;

		
		public Key(long transactionId) {
			super();
			this.transactionId = transactionId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ (int) (transactionId ^ (transactionId >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Key other = (Key) obj;
			if (transactionId != other.transactionId)
				return false;
			return true;
		}

		
	}
	
	
	class Value {
		Request req;
		ResponseListener listener;
		public Value(Request req, ResponseListener listener) {
			super();
			this.req = req;
			this.listener = listener;
		}
		
	}
	
	
	class LocalHandler extends Handler {

		public LocalHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SEND_REQUEST:
				//TODO send request to remote
				SignInRequest sir = (SignInRequest) msg.obj;
				if (sir.password.equals("123456") && sir.userName.equals("123456")) {
					try {
						Thread.currentThread().sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				
					Value val = mPendingReqs.get(new Key(sir.mTransationId));
					val.listener
							.onResponse(new SignInResponse(val.req));
				} else {
					Value val = mPendingReqs.get(new Key(sir.mTransationId));
					val.listener
							.onResponse(new SignInResponse(val.req, ResponseState.FAILED, null));
				}
				break;
			case GET_RESPONSE:
				break;
			}
		}
		
	}

}
