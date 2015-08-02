package com.example.balasasidharkola.mapsdemo;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationChangeListener, GoogleMap.OnCameraChangeListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Location mCurrentLocation;
    private ClusterManager<MyItem> mClusterManager;
    private ClusterManager<Person> pClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    private void setUpMap() {
        try{
            LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
            setUpClusterer();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setUpClusterer() {

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), 18));
        mClusterManager = new ClusterManager<MyItem>(this, mMap);

        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        addItems();
    }

    private void addItems() {
        pClusterManager = new ClusterManager<Person>(this, mMap);
        pClusterManager.setRenderer(new PersonRenderer(this, mMap, pClusterManager));
        double lat = mCurrentLocation.getLatitude();
        double lng = mCurrentLocation.getLongitude();

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {
            double offset = i / 600d;
            Log.d("OFFSET", ""+i);
            lat = lat + offset;
            lng = lng + offset;
            LatLng latLng = new LatLng(lat, lng);
            Log.d("LATLANG", ""+latLng);
            Person offsetItem = new Person(latLng, ""+i, R.drawable.ic_marker_vendor);
            pClusterManager.addItem(offsetItem);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(googleMap !=null){
            googleMap.setMyLocationEnabled(true);
            googleMap.setOnMyLocationChangeListener(this);
            googleMap.setOnCameraChangeListener(this);
            mMap = googleMap;
        }
    }

    @Override
    public void onMyLocationChange(Location location) {
        if (mCurrentLocation == null || mCurrentLocation.distanceTo(location) > 2000){
            mCurrentLocation = location;
            setUpMap();
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
//        mMap.setOnMyLocationChangeListener(null);
//        LatLng latLng = new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
    }
}
