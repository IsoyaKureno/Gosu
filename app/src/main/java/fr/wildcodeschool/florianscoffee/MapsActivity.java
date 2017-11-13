package fr.wildcodeschool.florianscoffee;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import java.io.IOException;
import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMyLocationButtonClickListener
        , RoutingListener{


    private static final String TAG = "MapsActivity";

    private GoogleMap mMap;
    private LatLngBounds Limite = new LatLngBounds(
            new LatLng(41.36, -5.16), new LatLng(51.1, 9.8));

    private Location mLocation;

    public static ArrayList<CoffeeShopModel> mCoffeeShopList = new ArrayList<>();

    private DatabaseReference myRef;

    private Marker marker;
    private LatLng geoLoc;
    private Float zoom;

    private LocationManager mLocationManager = null;
    private LocationListener mLocationListener = null;
    private static final int PERMISSION_REQUEST_LOCALISATION = 10;
    private boolean mPermissionDenied = false;
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };


    private ProgressDialog progressDialog;
    private ArrayList<Polyline> polylines;
    private Routing routing;

    private static final int[] COLORS = new int[]{R.color.shorterItinerary,R.color.other1,R.color.other2,R.color.other3,R.color.other4};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("baristas");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mCoffeeShopList.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    CoffeeShopModel viaFerrata = data.getValue(CoffeeShopModel.class);
                    placeMarker(viaFerrata);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });


        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.abs_layout);
        polylines = new ArrayList<>();

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

        googleMap.getUiSettings().setMapToolbarEnabled(false);
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        mMap.setLatLngBoundsForCameraTarget(Limite);
        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(geoLoc, zoom));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                route(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), marker.getPosition());
                return false;
            }
        });

        Button requestButton = findViewById(R.id.buttonRequest);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Polyline poly : polylines){
                    poly.remove();
                }
                marker.hideInfoWindow();
            }
        });
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


    public void placeMarker(CoffeeShopModel coffeeShopModel) {
        String nom = coffeeShopModel.getNom();
        String horaireClose = coffeeShopModel.getHoraireClose();
        Double latitude = coffeeShopModel.getLatitude();
        Double longitude = coffeeShopModel.getLongitude();

        marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title(nom)
                .snippet("Ouvert jusqu'à : " + horaireClose)

        );
        marker.setTag(coffeeShopModel);
    }


    public void route(LatLng latLngDepart, LatLng latLngArrivee)
    {
        progressDialog = ProgressDialog.show(this, "Patientez",
                "Nous cherchons votre itineraire", true);
        routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.WALKING)
                .withListener(this)
                .alternativeRoutes(true)
                .waypoints(latLngDepart, latLngArrivee)
                .key("AIzaSyDNC9ntg8CyDXuBG0oYBa7L3GUjPc2AjGs")
                .build();
        routing.execute();
    }


    @Override
    public void onRoutingFailure(RouteException e) {
        // The Routing request failed
        progressDialog.dismiss();
        if(e != null) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingStart() {
        // The Routing Request starts
    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex)
    {
        progressDialog.dismiss();

        if(polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        for (int i = route.size() - 1; i >= 0; i--) {
            PolylineOptions polyOptions = new PolylineOptions();
            int colorIndex = i;
            if (i > COLORS.length) {
                colorIndex = COLORS.length - 1;
            }
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + (-colorIndex * 3));
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polylines.add(polyline);

            Toast.makeText(getApplicationContext(), route.get(0).getDistanceValue() + "m", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingCancelled() {
        Log.i(TAG, "Routing was cancelled.");
    }
}
