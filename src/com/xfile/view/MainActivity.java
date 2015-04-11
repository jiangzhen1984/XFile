package com.xfile.view;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xfile.R;
import com.xfile.comm.IXFileService;
import com.xfile.comm.IntentConstants;
import com.xfile.comm.Response;
import com.xfile.comm.ResponseListener;
import com.xfile.model.Doctor;

public class MainActivity extends Activity {

	
	private IXFileService mService;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent i = new Intent(IntentConstants.ACTION_SERVICE);
		i.addCategory(IntentConstants.CATEGORY);
		bindService(i, mConnection, Context.BIND_AUTO_CREATE);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(mConnection);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public  class PlaceholderFragment extends Fragment {

		private ListView lv;
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			lv = (ListView)rootView.findViewById(R.id.listView1);
			mLocalAdapter = new LocalAdapter();
			lv.setAdapter(mLocalAdapter);
			return rootView;
		}
		
		
		
		
	}
	
	
	private LocalAdapter mLocalAdapter;
	class LocalAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list == null ? 0 : list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return list.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.widget_doctor_card, parent,
						false);
			}
			Doctor doct = list.get(position);
			TextView tvName = (TextView)convertView.findViewById(R.id.textView1);
			TextView tvDept = (TextView)convertView.findViewById(R.id.textView2);
			tvName.setText(doct.getName());
			tvDept.setText(doct.getDepartment());
			return convertView;
		}
		
	}
	
	private List<Doctor> list;
	
	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = (IXFileService) service;
			mService.queryDoctors(null, new ResponseListener() {

				@Override
				public void onResponse(Response res) {
					list = (List<Doctor>)res.getResult();
					mLocalAdapter.notifyDataSetChanged();
				}
				
			});

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

	};
}
