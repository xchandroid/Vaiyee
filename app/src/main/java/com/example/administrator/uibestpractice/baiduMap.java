package com.example.administrator.uibestpractice;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class baiduMap extends AppCompatActivity {
    private MapView mapView;
    private LocationClient locationClient;
    public  BaiduMap baiduMap;
    private boolean isFirstLocation=true;
    private   MapStatusUpdate update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_baidu_map);
        mapView = (MapView) findViewById(R.id.baiduMap);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        Button location = (Button)findViewById(R.id.button4);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDingwei(view);
                update = MapStatusUpdateFactory.zoomTo(16f);
                baiduMap.animateMapStatus(update);
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null)
        {
            actionBar.hide();
        }
    }

    public class MyLocationListener implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuilder currentLocation = new StringBuilder();
            currentLocation.append("纬度:").append(bdLocation.getLatitude()).append("\n");
            currentLocation.append("经线:").append(bdLocation.getLongitude()).append("\n").append("国家:").append(bdLocation.getCountry())
                    .append("\n").append("省/区:").append(bdLocation.getProvince()).append("\n")
                    .append("城市:").append(bdLocation.getCity()).append("\n")
                    .append("区/县:").append(bdLocation.getDistrict()).append("\n")
                    .append("街道:").append(bdLocation.getLocationDescribe()).append("\n")
                    .append("定位方式:");
            if (bdLocation.getLocType()==BDLocation.TypeGpsLocation)
            {
                currentLocation.append("GPS");
            }else if (bdLocation.getLocType()==BDLocation.TypeNetWorkLocation)
            {
                currentLocation.append("网络");
            }
            Toast.makeText(com.example.administrator.uibestpractice.baiduMap.this,currentLocation,Toast.LENGTH_SHORT).show();
            if (bdLocation.getLocType()==BDLocation.TypeGpsLocation||bdLocation.getLocType()==BDLocation.TypeNetWorkLocation)
            {
                navigateTO(bdLocation);
            }
        }
    }

    private void navigateTO(BDLocation bdLocation) {

        if (isFirstLocation)
        {
            LatLng ll = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
             update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            isFirstLocation = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(bdLocation.getLatitude());
        locationBuilder.longitude(bdLocation.getLongitude());
        MyLocationData locationData =locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }

    private void requestLocation() {
        initLocation();
        locationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);
        locationClient.setLocOption(option);
    }

    public  void setDingwei(View v)
    {
        requestLocation();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        locationClient.stop();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
}
