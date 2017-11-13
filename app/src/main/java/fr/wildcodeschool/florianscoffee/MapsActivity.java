package fr.wildcodeschool.florianscoffee;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMyLocationButtonClickListener {


    private static final String TAG = "MapActivity";

    private GoogleMap mMap;
    private LatLngBounds Limite = new LatLngBounds(
            new LatLng(41.36, -5.16), new LatLng(51.1, 9.8));

    private Location mLocation;

    private LatLng geoLoc;
    private Float zoom;

    private LocationManager mLocationManager = null;
    private LocationListener mLocationListener = null;
    private static final int PERMISSION_REQUEST_LOCALISATION = 10;
    private boolean mPermissionDenied = false;
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            public void onProviderEnabled(String provider) {
                Log.i(TAG, "onProviderEnabled: ");
            }

            public void onProviderDisabled(String provider) {
                Log.i(TAG, "onProviderDisabled: ");
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPermission();
        if (mLocation == null) {
            Toast.makeText(this, "Vous n'avez pas activé la localisation", Toast.LENGTH_LONG).show();
            geoLoc = Limite.getCenter();
            zoom = 5f;

        } else {
            geoLoc = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
            zoom = 15f;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        // Ajouter le style a la map
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can'filtreText find style. Error: ", e);
        }
        mMap.setLatLngBoundsForCameraTarget(Limite);
        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(geoLoc, zoom));
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, PERMISSION_REQUEST_LOCALISATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION, true);

        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    public static double distFrom ( double lat1, double lng1, double lat2, double lng2){
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = (earthRadius * c) / 1000;
        dist = Math.round(dist * 100);
        dist = dist / 100;
        return dist;
    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults){

        if (requestCode != PERMISSION_REQUEST_LOCALISATION) {
            return;
        }
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MapsActivity.this,
                    getResources().getString(R.string.permission_granted),
                    Toast.LENGTH_SHORT).show();
            checkPermission();
        }
    }

    private void checkPermission () {
        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    PERMISSIONS, PERMISSION_REQUEST_LOCALISATION);
            return;
        }
        String provider = mLocationManager.getBestProvider(new Criteria(), false);
        mLocation = mLocationManager.getLastKnownLocation(provider);
        if (mLocation != null) {
            double distance = distFrom(mLocation.getLatitude(), mLocation.getLongitude(), 45, 3);
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
        enableMyLocation();
    }

    @Override
    protected void onResumeFragments () {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    private void showMissingPermissionError () {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }
}
