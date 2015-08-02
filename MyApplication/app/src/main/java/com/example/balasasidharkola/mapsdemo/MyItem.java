package com.example.balasasidharkola.mapsdemo;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by BALA SASiDHAR KOLA on 02-Aug-15.
 */
public class MyItem implements ClusterItem{

    private final LatLng mPosition;

    public MyItem(double lat, double lon) {
        mPosition = new LatLng(lat, lon);
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}
