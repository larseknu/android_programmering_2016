package com.example.perev.playingwithgooglemapslecture;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Property;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        AdapterView.OnItemSelectedListener,
        GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;

    private LatLng HIOF = new LatLng(59.12797849, 11.35272861);
    private LatLng FREDRIKSTAD = new LatLng(59.211418, 10.9391918);

    private int mKittyCounter = 0;
    private ArrayList<Marker> mKittyMarkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mKittyMarkers = new ArrayList<>();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);

        Spinner spinner = (Spinner)findViewById(R.id.layers_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.layers_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    private void setUiSettings() {
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
    }

    private void setUpMarkers() {
        mMap.addMarker(new MarkerOptions().position(HIOF).title("Østfold University College"));
        mMap.addMarker(new MarkerOptions().position(FREDRIKSTAD).title("Fredrikstad Kino"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.kitty_attack:
                for (Marker kittyMarker : mKittyMarkers)
                    animateMarker(kittyMarker, HIOF);
                break;
            case R.id.remove_kittens:
                removeAllKittyMarkers();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void removeAllKittyMarkers(){
        for (Marker kittyMarker: mKittyMarkers){
            kittyMarker.remove();
        }
        mKittyMarkers.clear();
        mKittyCounter = 0;
    }

    static void animateMarker (Marker marker, LatLng finalPosition){
        TypeEvaluator<LatLng> typeEvaluator = new TypeEvaluator<LatLng>() {
            @Override
            public LatLng evaluate(float fraction, LatLng startValue, LatLng endValue) {
                double lat = (endValue.latitude - startValue.latitude) * fraction + startValue.latitude;
                double lng = (endValue.longitude - startValue.longitude) * fraction + startValue.longitude;
                return new LatLng(lat, lng);
            }
        };
        Property<Marker, LatLng> property = Property.of(Marker.class, LatLng.class, "position");
        ObjectAnimator animator = ObjectAnimator.ofObject(marker, property, typeEvaluator, finalPosition);
        animator.setDuration(1000);
        animator.start();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap != null) {
            setUpMarkers();
            setUiSettings();
            mMap.setOnMapLongClickListener(this);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(HIOF, 13, 0, 0)));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String layerType = (String)parent.getItemAtPosition(position);
        if(layerType.equals(getString(R.string.hybrid))){
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
        else if(layerType.equals(getString(R.string.satellite))){
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        else if(layerType.equals(getString(R.string.terrain))){
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }
        else if(layerType.equals(getString(R.string.none))){
            mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        }
        else {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        addKittenMarker(latLng, "Kitty Invasion!");
    }

    private void addKittenMarker(LatLng kittenLocation, String snippet){
        BitmapDescriptor kittenIcon = BitmapDescriptorFactory.fromResource(
                getResources().getIdentifier("kitten_0" + (mKittyCounter%3+1), "drawable", this.getPackageName()));

        mKittyCounter++;

        MarkerOptions markerOptions = new MarkerOptions()
                .position(kittenLocation)
                .title("Mittens the " + mKittyCounter + ".")
                .snippet(snippet)
                .icon(kittenIcon);

        Marker kittyMarker = mMap.addMarker(markerOptions);
        mKittyMarkers.add(kittyMarker);
    }
}
