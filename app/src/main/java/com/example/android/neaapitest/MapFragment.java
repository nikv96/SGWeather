package com.example.android.neaapitest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;


/**
 * Created by user on 16-10-2015.
 */

public class MapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener {

    private GoogleApiClient mGoogleApiClient;

    private final int[] MAP_TYPES = {GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        initListeners();
    }

    private void initListeners() {
        getMap().setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            public boolean onMarkerClick(Marker marker) {
                onMapClick(marker.getPosition());
                return true;
            }
        });
        getMap().setOnMapLongClickListener(this);
        getMap().setOnInfoWindowClickListener(this);
        getMap().setOnMapClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        initCamera();

        int PSI_value = 108;
        setPsiMarker(Integer.toString(PSI_value),"West",1.345901, 103.708236);
        setPsiMarker(Integer.toString(PSI_value),"East",1.345901,103.936937);
        setPsiMarker(Integer.toString(PSI_value),"Middle",1.345901,103.822158);
        setPsiMarker(Integer.toString(PSI_value),"North",1.428671,103.822158);
        setPsiMarker(Integer.toString(PSI_value),"South",1.273726,103.822158);
    }

    private void initCamera() {
        CameraPosition position = CameraPosition.builder()
                .target(new LatLng(1.3500, 103.8000))
                .zoom(10f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();
        getMap().animateCamera(CameraUpdateFactory
                .newCameraPosition(position),null);
        getMap().getUiSettings().setRotateGesturesEnabled(false);
        getMap().getUiSettings().setScrollGesturesEnabled(false);
        getMap().getUiSettings().setZoomGesturesEnabled(false);
        getMap().setMapType(MAP_TYPES[1]);
    }

    private void setPsiMarker(String PSI, String title, double lat, double lng){
        PSIScreenActivity psi = new PSIScreenActivity();
        LatLng latLng =new LatLng(lat,lng);
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title(title);
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        psi.setMapLabel(PSI);
        View v = inflater.inflate(R.layout.image_view, null);

        options.icon(BitmapDescriptorFactory.fromBitmap(loadBitmapFromView(v)));
        getMap().addMarker(options);
    }
    public static Bitmap loadBitmapFromView(View v) {

        if (v.getMeasuredHeight() <= 0) {
            v.measure(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
            v.draw(c);
            return b;
        }
        return null;
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
