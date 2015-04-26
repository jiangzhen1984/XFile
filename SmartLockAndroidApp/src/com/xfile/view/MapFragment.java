package com.xfile.view;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.xfile.R;
import com.xfile.comm.LockCommService;

public class MapFragment extends Fragment {

	private static final int LOCK_OPT_UNLOCK = 0x10;
	private static final int LOCK_OPT_LOCK = 0x20;
	
	private static final int REQUEST_ENABLE_BT = 0x1;
	private static final String REQUEST_ENABLE_BT_EXTRA_KEY_OPT = "opt";
	BaiduMap mBaiduMap;
	MapView mMapView;
	LocationClient mLocClient;
	private boolean isFirstLoc = true;;
	
	private Button mLock;
	private Button mUnLock;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 定位初始化 
		mLocClient = new LocationClient(getActivity());
		mLocClient.registerLocationListener(mLocalListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.map_fragment, container,
				false);
		mMapView = (MapView) rootView.findViewById(R.id.map_view);

		mUnLock = (Button) rootView.findViewById(R.id.unlock);
		mUnLock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkBluetools(false)) {
					updateLock(false);
				}
				
			}
			
		});
		
		mLock = (Button) rootView.findViewById(R.id.lock);
		mLock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkBluetools(true)) {
					updateLock(true);
				}
				
			}
			
		});
		
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setOnMarkerClickListener(mMarkerClickerListener);
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		
	
		rootView.findViewById(R.id.create).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(getActivity(), ParkPositionShare.class);
				startActivity(i);
				
			}
			
		});
		return rootView;
	}

	
	
	
	@Override
	public void onStart() {
		super.onStart();
		mLocClient.start();
	}

	@Override
	public void onStop() {
		super.onStop();
		mLocClient.stop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
		getActivity().unregisterReceiver(mLockStateReceiver);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}
	
	
	private boolean checkBluetools(boolean flag) {
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		if (!adapter.isEnabled()) {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT | (flag ? LOCK_OPT_LOCK : LOCK_OPT_UNLOCK));
		    return false;
		}
		return true;
	}
	
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		int lockCode = REQUEST_ENABLE_BT | LOCK_OPT_LOCK;
		int unLockCode = REQUEST_ENABLE_BT | LOCK_OPT_UNLOCK;
		if (resultCode == Activity.RESULT_OK) {
			updateLock(lockCode == requestCode? true : false);
		}
	}



	ProgressDialog dialog;
	private void updateLock(boolean flag) {
		
		dialog = ProgressDialog.show(getActivity(), "a", flag? "正在上锁...":"正在解锁...", true, false);
		dialog.show();
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(LockCommService.BROADCAST_ACTION);
		filter.addCategory(LockCommService.BROADCAST_CATEGORY);
		getActivity().registerReceiver(mLockStateReceiver, filter);
		
		Intent i = new Intent();
		i.setClass(getActivity(), LockCommService.class);
		i.putExtra(LockCommService.EXTRA_KEY_LOCK_NAME, "Xperia E3 Dual");
		i.putExtra(LockCommService.EXTRA_KEY_LOCK_UUID, "68092189-6bc8-4db2-8f2b-5a4f35ae8379");
		i.putExtra(LockCommService.EXTRA_KEY_LOCK_secure, "1234");
		i.putExtra(LockCommService.EXTRA_KEY_LOCK_OPT, flag?1:0);
		getActivity().startService(i);
		//TODO add time out
	}
	
	
	private BroadcastReceiver mLockStateReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(LockCommService.BROADCAST_ACTION)) {
				dialog.dismiss();
				LockCommService.State res = (LockCommService.State)intent.getExtras().get(LockCommService.EXTRA_KEY_OPT_RESULT);
				Toast.makeText(getActivity(), res == LockCommService.State.NORMAL?"成功":"失败", Toast.LENGTH_SHORT).show();
			}
		}
		
	};
	
	private BDLocationListener mLocalListener = new  BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;

			LatLng ll = new LatLng(location.getLatitude(),
					location.getLongitude());
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(ll.latitude)
					.longitude(ll.longitude).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				float zoomLevel = 15.0F;
				LatLng bounds = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(
						bounds, zoomLevel);
				mBaiduMap.animateMapStatus(u);
				
				
				mBaiduMap.clear();
				BitmapDescriptor online = BitmapDescriptorFactory
						.fromResource(R.drawable.marker_online);
				
				Bundle bundle = new Bundle();
				OverlayOptions oo = new MarkerOptions().icon(online)
						.position(ll).extraInfo(bundle);
				mBaiduMap.addOverlay(oo);

			}

		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	};
	
	
	private BaiduMap.OnMarkerClickListener mMarkerClickerListener = new BaiduMap.OnMarkerClickListener() {

		@Override
		public boolean onMarkerClick(Marker marker) {
			Intent i = new Intent();
			i.setClass(getActivity(), LockSearch.class);
			startActivity(i);
			return true;
		}

	};

}
