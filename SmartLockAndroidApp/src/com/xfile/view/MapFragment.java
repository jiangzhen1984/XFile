package com.xfile.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

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

public class MapFragment extends Fragment {

	BaiduMap mBaiduMap;
	MapView mMapView;
	LocationClient mLocClient;
	private boolean isFirstLoc = true;;

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
