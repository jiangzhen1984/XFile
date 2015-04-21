package com.xfile.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;

import com.xfile.R;
import com.xfile.model.Lock;

public class LockSearch extends Activity {

	private View mCycleDateView;
	private View mOneTimeDateView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lock_list);

		mCycleDateView = findViewById(R.id.linearLayout3);
		mOneTimeDateView = findViewById(R.id.linearLayout2);

		if (savedInstanceState == null) {
			mCycleDateView.setVisibility(View.GONE);
		}

		((RadioGroup) findViewById(R.id.radioGroup1))
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId == R.id.radio0) {
							mCycleDateView.setVisibility(View.GONE);
							mOneTimeDateView.setVisibility(View.VISIBLE);
						} else {
							mCycleDateView.setVisibility(View.VISIBLE);
							mOneTimeDateView.setVisibility(View.GONE);
						}
					}

				});
		this.overridePendingTransition(R.animator.right_to_left_in, -1);
		
		
		findViewById(R.id.sh).setOnFocusChangeListener(timeSelect1);
		findViewById(R.id.sh).setOnClickListener(timeSelect);
		findViewById(R.id.st).setOnFocusChangeListener(timeSelect1);
		findViewById(R.id.st).setOnClickListener(timeSelect);
		findViewById(R.id.eh).setOnFocusChangeListener(timeSelect1);
		findViewById(R.id.eh).setOnClickListener(timeSelect);
		findViewById(R.id.et).setOnFocusChangeListener(timeSelect1);
		findViewById(R.id.et).setOnClickListener(timeSelect);
		

		list = new ArrayList<Lock>();
		for (int i = 0; i < 50; i ++) {
			list.add(new Lock());
		}
		mLocalAdapter = new LocalAdapter();
		ListView lv = ((ListView)findViewById(R.id.listView1));
		lv.setAdapter(mLocalAdapter);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	
	private OnClickListener timeSelect = new OnClickListener() {

		@Override
		public void onClick(View v) {
			TimePickerDialog dialog = new TimePickerDialog(LockSearch.this, new OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				}
				
			}, 15, 12, true);
			
			dialog.show();
		}
		
	};
	
	private OnFocusChangeListener timeSelect1 = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
				TimePickerDialog dialog = new TimePickerDialog(LockSearch.this, new OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					}
					
				}, 15, 12, true);
				
				dialog.show();
			}
		}
		
	};
	
	
	private List<Lock> list;
	
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
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(LockSearch.this).inflate(R.layout.widget_doctor_card, parent,
						false);
			}
			Lock doct = list.get(position);
			TextView tvName = (TextView)convertView.findViewById(R.id.textView1);
			TextView tvDept = (TextView)convertView.findViewById(R.id.textView2);
			tvName.setText("望京花园 3区 #3-174");
			tvDept.setText("距离 目的地 245米  2/小时 可用时间  9:00 - 15:00");
			return convertView;
		}
		
	}
	

}
