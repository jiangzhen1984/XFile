package com.xfile.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xfile.R;
import com.xfile.comm.IXFileService;
import com.xfile.comm.IntentConstants;
import com.xfile.comm.Request;
import com.xfile.comm.Response;
import com.xfile.comm.ResponseListener;
import com.xfile.comm.ResponseState;
import com.xfile.comm.SignInRequest;

public class SignUpActivity extends Activity {

	private static final int REQUEST_SIGN_IN = 1;
	private static final int REQUEST_SIGN_IN_RESPONSE = 2;
	private static final int REQUEST_SIGN_UP = 3;
	private static final int REQUEST_SIGN_UP_RESPONSE = 4;

	private EditText mUserNameET;
	private EditText mPwdET;

	private Button mSignUpButton;

	private IXFileService mService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		mSignUpButton = (Button) findViewById(R.id.sign_up_btn);
		mSignUpButton.setOnClickListener(mSignUpBtnListener);
		mUserNameET = (EditText) findViewById(R.id.sign_up_username);
		mPwdET = (EditText) findViewById(R.id.sign_up_password);

		Intent i = new Intent(IntentConstants.ACTION_SERVICE);
		i.addCategory(IntentConstants.CATEGORY);
		bindService(i, mConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(mConnection);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	private Request mPendingRequest;

	private ProgressDialog mDialog;
	private OnClickListener mSignUpBtnListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String userName = mUserNameET.getText().toString();
			String pwd = mPwdET.getText().toString();
			if (TextUtils.isEmpty(userName)) {
				mUserNameET
						.setError(getText(R.string.sign_up_error_username_required));
				return;
			}
			if (TextUtils.isEmpty(pwd)) {
				mUserNameET
						.setError(getText(R.string.sign_up_error_pwd_required));
				return;
			}
			mDialog = ProgressDialog.show(SignUpActivity.this,
					getText(R.string.sign_up_progress_title),
					getText(R.string.sign_up_progress_sign_in), false, true,
					new OnCancelListener() {

						@Override
						public void onCancel(DialogInterface dialog) {
							dialog.dismiss();
							mService.cancelWaiting(mPendingRequest);
							Toast.makeText(SignUpActivity.this, "已取消", Toast.LENGTH_SHORT).show();
							mPendingRequest = null;
						}

					});
			
			mPendingRequest = new SignInRequest(userName, pwd);
			// TODO show progress bar
			Message.obtain(mLocalHandler, REQUEST_SIGN_IN,
					mPendingRequest).sendToTarget();
		}

	};

	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = (IXFileService) service;

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

	};

	private Handler mLocalHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (mService == null) {
				Message delayedMsg = Message.obtain(msg);
				this.sendMessageDelayed(delayedMsg, 300);
			}
			switch (msg.what) {
			case REQUEST_SIGN_IN:
				mService.signIn((Request) msg.obj, mResponseListener);
				break;
			case REQUEST_SIGN_IN_RESPONSE:

				break;
			}
		}

	};

	private ResponseListener mResponseListener = new ResponseListener() {

		@Override
		public void onResponse(Response res) {
			mDialog.dismiss();
			mPendingRequest = null;
			if (res.getState() == ResponseState.SUCCESS) {
				// TODO show progress
				Intent i = new Intent("com.xfile.action.main_activity");
				i.addCategory("com.xfile");
				startActivity(i);
				finish();
			} else if (res.getState() == ResponseState.FAILED) {
				Toast.makeText(SignUpActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
			}
		}

	};

}
