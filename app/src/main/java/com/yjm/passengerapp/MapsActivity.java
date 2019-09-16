package com.yjm.passengerapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yjm.passengerapp.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.internal.Util;
import com.yjm.passengerapp.models.Schedule;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private static final int MY_REQUREST_INT = 177;
    private Toolbar toolbar;
    private MapView mapView;
    private DrawerLayout mDrawerLayout;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentUserLocationMaker;
    private static final int Request_User_Location_Code = 99;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mLocationPermissionGranted = false;
    private static final float DEFAULT_ZOOM = 15f;
    private boolean bus = false;
    private ApiInterface apiInterface;
    private static String FromTime_s;
    private static String FromTime_t;
    private static String ToTime_s;
    private static String ToTime_t;
    private static String ShiftDate_s;
    private static String ShiftDate_t;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setupToolbarMenu();
        setupNavigationDrawerMenu();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiInterface = retrofit.create(ApiInterface.class);


//  Map
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkUserLocationPermission();
        }

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(Constants.MAPVIEW_BUNDLE_KEY);
        }
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);


    }

    private void setupToolbarMenu() {
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
    }

    private void setupNavigationDrawerMenu() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }


    // for google map
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        buildGoogleApiClient();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }

//    private void getLocationPermission()
//    {
//        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
//        Manifest.permission.ACCESS_COARSE_LOCATION};
//        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
//        {
//            if(ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION))
//            {
//                mLocationPermissionGranted = true;
//
//            }
//            else {
//                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
//            }
//        }
//        else {
//            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
//        }
//    }


//    private void getDeviceLocation()
//    {
//
//        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        try{
//            if(mLocationPermissionGranted){
//                Task location = mFusedLocationProviderClient.getLastLocation();
//                location.addOnCompleteListener(new OnCompleteListener() {
//                    @Override
//                    public void onComplete(@NonNull Task task) {
//                        if(task.isSuccessful()){
//                            Location currentLocation = (Location) task.getResult();
//                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
//                        }
//                        else{
//
//                        }
//                    }
//                });
//            }
//
//        } catch (SecurityException e){
//
//        }
//    }
//
//    private  void moveCamera(LatLng latLng, float zoom)
//    {
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
//    }
    public boolean checkUserLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            return false;
        }
        else
        {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case Request_User_Location_Code:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if (googleApiClient == null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this, "Permission Denied....", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }



    protected synchronized void buildGoogleApiClient(){

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Please check internet", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onLocationChanged(Location location)
    {
        lastLocation = location;

        if(currentUserLocationMaker != null)
        {
            currentUserLocationMaker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("user Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        currentUserLocationMaker = mMap.addMarker(markerOptions);

//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomBy(12));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12f));

        if(googleApiClient != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle){

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
//            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

            Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            Constants.currentLocation = currentLocation;

            UpdateMap();

        }


    }
    private void UpdateMap(){

        LatLng my_latLng = new LatLng(Constants.currentLocation.getLatitude(), Constants.currentLocation.getLongitude());


        if (bus == true){

            mMap.addMarker(new MarkerOptions().position(Constants.bus_location).title("current location of bus").draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Constants.bus_location, 12f));
            bus = false;
        }
        else {
            mMap.addMarker(new MarkerOptions().position(my_latLng).title("current location of me").draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.person)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(my_latLng, 12f));
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onPause(){
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy(){
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory(){
        mapView.onLowMemory();
        super.onLowMemory();
    }




    @Override //Called when Any Navigation Item is Clicked
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        String itemName = (String) menuItem.getTitle();
        Toast.makeText(MapsActivity.this, itemName + "clicked", Toast.LENGTH_SHORT).show();
        
        closeDrawer();

        switch (menuItem.getItemId()){
            case R.id.bus_position:

                Call<JsonObject> response_of_bus_location = apiInterface.getBusLocation();
                response_of_bus_location.enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){

                            try {
                                JSONObject subObj = new JSONObject(String.valueOf(response));
                                JSONObject json_data = subObj.getJSONObject("body");
                                String location = json_data.getString("PassBusStatLocations");

                                LatLng bus_latlng = new LatLng(32.1240017, 34.8397236000001);
                                LatLng bus_station = new LatLng(39.1240017, 39.8397236000001);


                                Constants.bus_location = bus_latlng;
                                bus = true;
                                UpdateMap();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        else {

                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {


                    }
                });






                break;
            case R.id.schedule:
                Call<JsonObject> response_of_schedule = apiInterface.getShiftsofWeek();
                response_of_schedule.enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()) try {

                            JsonArray schedule_data = response.body().getAsJsonArray("NextWeekPassengerShift");
                            ArrayList<String[]> schedule = new ArrayList();

                            for (int i = 0, count = schedule_data.size(); i < count; i++) {


                                try {
                                    JsonObject jsonObject = (JsonObject) schedule_data.get(i);




                                    ToTime_s = jsonObject.get("ToTime").toString();
                                    ShiftDate_s = jsonObject.get("ShiftDate").toString();
                                    FromTime_s = jsonObject.get("FromTime").toString();

                                    String itemSchdule[] = new String[]{FromTime_s, ToTime_s, ShiftDate_s, ShiftDate_s};
                                    schedule.add(itemSchdule);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            Constants.ARRAY_SCHEDULE = schedule;
                            Intent intent_two = new Intent(MapsActivity.this, scheduleActivity.class);
                            startActivity(intent_two);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        else {

                            }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {


                    }
                });

                break;

            case R.id.seting:
                break;

            case R.id.bill:
                break;
        }
        return false;
    }

    private void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void showDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed(){
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
            closeDrawer();
        else
            super.onBackPressed();
    }

}