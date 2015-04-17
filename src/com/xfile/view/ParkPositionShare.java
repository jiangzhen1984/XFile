package com.xfile.view;

import com.xfile.R;

import android.app.Activity;
import android.os.Bundle;

public class ParkPositionShare extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.park_position_share);
		this.overridePendingTransition(R.animator.right_to_left_in, -1);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	
}
