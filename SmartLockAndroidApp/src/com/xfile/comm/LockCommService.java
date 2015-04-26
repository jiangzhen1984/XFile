package com.xfile.comm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;

/**
 * 
 * @author 28851274
 * 
 */
public class LockCommService extends Service {

	public static final String EXTRA_KEY_LOCK_ADDRES ="lock_address";
	
	public static final String EXTRA_KEY_LOCK_UUID = "lock_uuid";

	public static final String EXTRA_KEY_LOCK_NAME = "lock_name";

	public static final String EXTRA_KEY_LOCK_secure = "lock_secure";

	public static final String EXTRA_KEY_LOCK_OPT = "opt";

	public static final String EXTRA_KEY_OPT_RESULT = "opt_result";

	public static final String BROADCAST_ACTION = "com.xfile.lock_comm_action";
	
	public static final String BROADCAST_CATEGORY = "com.xfile";

	private String mMacDevice;
	private String mLockName;
	private String mLockUUID;
	private String mLockPin;
	private int mOpt = -1;

	private Handler mHandler = new Handler();
	
	private ConnectThread mConnThread;

	private boolean mReceiverRegistered;

	private State mState = State.NORMAL;

	BluetoothAdapter mBluetoothAdapter;
	
	private Object mThreadLock = new Object();

	@Override
	public void onCreate() {
		super.onCreate();
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mMacDevice = intent.getStringExtra(EXTRA_KEY_LOCK_ADDRES);
		mLockName = intent.getStringExtra(EXTRA_KEY_LOCK_NAME);
		mLockUUID = intent.getStringExtra(EXTRA_KEY_LOCK_UUID);
		mLockPin = intent.getStringExtra(EXTRA_KEY_LOCK_secure);
		mOpt = intent.getIntExtra(EXTRA_KEY_LOCK_OPT, -1);
		if (TextUtils.isEmpty(mLockUUID) || TextUtils.isEmpty(mLockName)
				|| TextUtils.isEmpty(mLockPin) || mOpt == -1) {
			this.stopSelf();
			return START_STICKY;
		} else {
			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			if (!mBluetoothAdapter.isEnabled()) {
				return START_STICKY;
			}
			lookingForDevice();
		}

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mReceiverRegistered) {
			this.unregisterReceiver(mReceiver);
		}
		// close thread
		if (mConnThread != null && mConnThread.isAlive()) {
			mConnThread.cancel();
			mConnThread = null;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	
	private void lookingForDevice() {
		boolean found = false;
		Set<BluetoothDevice> bl = mBluetoothAdapter.getBondedDevices();
		for (BluetoothDevice bd : bl) {
			if (bd.getName().equals(mLockName)) {
				pairDevice(bd);
				found  = true;
				break;
			}
		}
		
		if(!found) {
			lookingForDevice(mLockName);
		} 
	}
	
	
	private void pairDevice(BluetoothDevice device) {
		if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
			IntentFilter mFilter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
			registerReceiver(mReceiver, mFilter);
			device.createBond();
		} else {
			startConnect(device);
		}
	}

	private void lookingForDevice(String name) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			mBluetoothAdapter
					.startLeScan(new BluetoothAdapter.LeScanCallback() {

						@Override
						public void onLeScan(BluetoothDevice device, int rssi,
								byte[] scanRecord) {
							// TODO Auto-generated method stub

						}

					});
		}
		
		mReceiverRegistered = true;
		IntentFilter mFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		mFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		mFilter.addAction(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, mFilter);
		mState = State.SEARHING;
		BluetoothAdapter.getDefaultAdapter().startDiscovery();
	}
	
	

	
	private synchronized boolean startConnect(BluetoothDevice device) {
		if (mState ==  State.CONNECTING) {
			return false;
		}
		mState = State.CONNECTING;
		mConnThread = new ConnectThread(device);
		mConnThread.start();
		return true;
	}
	
	
	private void sendResultAndFinish(State st) {
		Intent i = new Intent(BROADCAST_ACTION);
		i.addCategory(BROADCAST_CATEGORY);
		i.putExtra(EXTRA_KEY_OPT_RESULT, st);
		this.sendBroadcast(i);
		stopSelf();
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(BluetoothDevice.ACTION_FOUND)) {
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (device.getName().equals(mLockName)
						&& mState == State.SEARHING) {
					pairDevice(device);
					//TODO how to avoid finish event?
				}
			} else if (action
					.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
				if (mState == State.SEARHING) {
					mState = LockCommService.State.FAILED_TIMEOUT;
					sendResultAndFinish(mState);
				}
			} else if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
				BluetoothDevice bd = (BluetoothDevice)intent.getExtras().get(BluetoothDevice.EXTRA_DEVICE);
				int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.BOND_NONE);
				if (state == BluetoothDevice.BOND_BONDED) {
					// mThreadLock is not null means bond request by thread
					if (mConnThread != null) {
						synchronized (mThreadLock) {
							mThreadLock.notify();
						}
					} else {
						startConnect(bd);
					}
				} else {
					//TODO what should we do?
				}
			}
		}
	};

	private class ConnectThread extends Thread {
		private BluetoothSocket mSocket = null;
		private BluetoothDevice mDevice = null;
		InputStream reader = null;
		OutputStream writer = null;

		public ConnectThread(BluetoothDevice device) {
			mDevice = device;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				mDevice.setPin(mLockPin.getBytes());
				mDevice.setPairingConfirmation(true);
				mBluetoothAdapter.stopLeScan(null);
			} 
			// Cancel discovery because it will slow down the connection
			mBluetoothAdapter.cancelDiscovery();
		}

		public void run() {
						
			if (mDevice.getBondState() != BluetoothDevice.BOND_NONE) {
				mDevice.createBond();
			}
			
			synchronized (mThreadLock) {
				try {
					mThreadLock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			//TODO waiting for bound
			try {
				mSocket = mDevice.createRfcommSocketToServiceRecord(UUID
						.fromString(mLockUUID));
			} catch (IOException e) {
				e.printStackTrace();
				mState = LockCommService.State.FAILED;
			}
			

			try {
				// Connect the device through the socket. This will block
				// until it succeeds or throws an exception
				mSocket.connect(); 
			} catch (Exception connectException) {
				try {
					mSocket.close();
				} catch (IOException closeException) {
				}
				mState = LockCommService.State.FAILED;
				sendResultAndFinish(mState);
				return;
			}

			Message msg = Message.obtain(mHandler, mTimeOutRunnable);
			mHandler.sendMessageDelayed(msg, 5000);
			
			updateLock(mSocket);
			mHandler.removeCallbacks(mTimeOutRunnable);
			try {
				sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			try {
				mSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		private void updateLock(BluetoothSocket socket) {
		
			try {
				reader = mSocket.getInputStream();
				writer = mSocket.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
				try {
					mSocket.close();
				} catch (IOException closeException) {
				}
				mState = LockCommService.State.FAILED;
				sendResultAndFinish(mState);
			}

			try {
				writer.write((mLockPin + mOpt).getBytes());
				writer.flush();
				//FIXME add time out
				int ret = reader.read();
				if (ret == 0) {
					mState = LockCommService.State.NORMAL;
					sendResultAndFinish(mState);
				} else {
					mState = LockCommService.State.INSECURE_FAILED;
					sendResultAndFinish(mState);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		/** Will cancel an in-progress connection, and close the socket */
		public void cancel() {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				mSocket.close();
				this.interrupt();
			} catch (IOException e) {
			}
		}

	}
	
	
	private Runnable mTimeOutRunnable = new Runnable() {

		@Override
		public void run() {
			if (mConnThread != null) {
				mConnThread.cancel();
				mConnThread = null;
				mState = LockCommService.State.FAILED_TIMEOUT;
				sendResultAndFinish(mState);
			}
		}
		
	};

	public enum State {
		NORMAL, SEARHING, MATCHING, CONNECTED, DISCONNECTED, CONNECTING, FAILED, INSECURE_FAILED, FAILED_TIMEOUT;
	}

}
