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
import android.os.IBinder;
import android.text.TextUtils;

/**
 * 
 * @author 28851274
 * 
 */
public class LockCommService extends Service {

	public static final String EXTRA_KEY_LOCK_UUID = "lock_uuid";

	public static final String EXTRA_KEY_LOCK_NAME = "lock_name";

	public static final String EXTRA_KEY_LOCK_secure = "lock_secure";

	public static final String EXTRA_KEY_LOCK_OPT = "opt";

	public static final String EXTRA_KEY_OPT_RESULT = "opt_result";

	public static final String BROADCAST_ACTION = "com.xfile.lock_comm_action";

	private String mLockName;
	private String mLockUUID;
	private String mLockPin;
	private int mOpt = -1;

	private ConnectThread mConnThread;

	private boolean mReceiverRegistered;

	private State mState = State.NORMAL;

	BluetoothAdapter mBluetoothAdapter;

	@Override
	public void onCreate() {
		super.onCreate();
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String name = intent.getStringExtra(EXTRA_KEY_LOCK_NAME);
		String uuid = intent.getStringExtra(EXTRA_KEY_LOCK_UUID);
		String pin = intent.getStringExtra(EXTRA_KEY_LOCK_secure);
		int opt = intent.getIntExtra(EXTRA_KEY_LOCK_OPT, -1);
		if (TextUtils.isEmpty(uuid) || TextUtils.isEmpty(name)
				|| TextUtils.isEmpty(pin) || opt == -1) {
			this.stopSelf();
			return START_STICKY;
		} else {
			doConnection(name, uuid, pin);
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
			// TODO close all stream
			mConnThread.cancel();
			mConnThread = null;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void doConnection(String name, String uuid, String pin) {
		mLockName = name;
		mLockUUID = uuid;
		mLockPin = pin;
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		if (!adapter.isEnabled()) {
			// user doesn't enable bluetool
			mState = LockCommService.State.FAILED;
			sendResultAndFinish(mState);
			return;
		}
		Set<BluetoothDevice> bl = adapter.getBondedDevices();
		for (BluetoothDevice bd : bl) {
			if (bd.getName().equals(name)) {
				mState = State.CONNECTING;
				mConnThread = new ConnectThread(bd);
				mConnThread.start();
				return;
			}
		}

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

	private void sendResultAndFinish(State st) {
		Intent i = new Intent(BROADCAST_ACTION);
		i.putExtra(EXTRA_KEY_OPT_RESULT, st == State.NORMAL ? 0 : 1);
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
					mState = State.CONNECTING;
					mConnThread = new ConnectThread(device);
					mConnThread.start();
				}
			} else if (action
					.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
				if (mState != State.CONNECTING) {
					// TODO handle doesn't searched matched device should retry
				}
			}
		}
	};

	private class ConnectThread extends Thread {
		private BluetoothSocket mSocket = null;
		private BluetoothDevice mDevice = null;

		public ConnectThread(BluetoothDevice device) {
			mDevice = device;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				mDevice.setPin(mLockPin.getBytes());
				mDevice.setPairingConfirmation(true);
			}
			// Get a BluetoothSocket to connect with the given BluetoothDevice
			try {
				mSocket = device.createRfcommSocketToServiceRecord(UUID
						.fromString(mLockUUID));
			} catch (IOException e) {
				e.printStackTrace();
				mState = LockCommService.State.FAILED;
			}
		}

		public void run() {
			if (mState == LockCommService.State.FAILED) {
				sendResultAndFinish(mState);
				return;
			}
			// Cancel discovery because it will slow down the connection
			mBluetoothAdapter.cancelDiscovery();

			try {
				// Connect the device through the socket. This will block
				// until it succeeds or throws an exception
				mSocket.connect();
			} catch (IOException connectException) {
				try {
					mSocket.close();
				} catch (IOException closeException) {
				}
				mState = LockCommService.State.FAILED;
				sendResultAndFinish(mState);
				return;
			}

			updateLock(mSocket);
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
			InputStream reader = null;
			OutputStream writer = null;
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
			try {
				mSocket.close();
			} catch (IOException e) {
			}
		}

	}

	enum State {
		NORMAL, SEARHING, MATCHING, CONNECTED, DISCONNECTED, CONNECTING, FAILED, INSECURE_FAILED;
	}

}
