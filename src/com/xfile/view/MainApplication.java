package com.xfile.view;

import com.xfile.comm.IntentConstants;

import android.app.Application;
import android.content.Intent;

public class MainApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Intent i = new Intent(IntentConstants.ACTION_SERVICE);
		i.addCategory(IntentConstants.CATEGORY);
		startService(i);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		Intent i = new Intent(IntentConstants.ACTION_SERVICE);
		i.addCategory(IntentConstants.CATEGORY);
		stopService(i);
	}

	
}
