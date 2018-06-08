package com.example.whiskersapp.petwhiskers;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whiskersapp.petwhiskers.Model.LocationAddress;
import com.example.whiskersapp.petwhiskers.Model.Pet;
import com.example.whiskersapp.petwhiskers.Model.User;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import android.Manifest;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;
import static android.os.Looper.getMainLooper;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class MapFragment extends Fragment implements OnMapReadyCallback{
    private Marker marker;
    private GoogleMap mMap;
    private Location mLastLocation;
    private double latitude, longitude;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private long UPDATE_INTERVAL = 10000;  /* 10 secs = 10 * 1000 */
    private long FASTEST_INTERVAL = 1000;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1000;
    private Task<LocationSettingsResponse> result;
    protected static final int REQUEST_CHECK_SETTINGS = 2000;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference table_user;
    private LocationAddress locationAddress;
    private LocationResult location;
    private final float distanceKM = 3;
    private Map<Marker, LocationAddress> markerUserHashMap = new HashMap<Marker, LocationAddress>();
    private BitmapDescriptor icon;
    private LocationCallback locationCallback;
    private FirebaseAuth mAuth;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_frag);
        mapFragment.getMapAsync(this);
        locationAddress = new LocationAddress();
        return  view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        initLocationParameters();

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                final LocationAddress userLoc  = markerUserHashMap.get(arg0);
                // Getting view from the layout file info_window_layout

                View v = getLayoutInflater().inflate(R.layout.custom_info_contents, null);


                final CircleImageView petImg = (CircleImageView)v.findViewById(R.id.user_img_info);

                /*no user pic implemented yet Picasso.with(getActivity().getApplicationContext()).load(petContents.getImgUrl()).networkPolicy(NetworkPolicy.OFFLINE)
                        .placeholder(R.drawable.default_image).into(petImg, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("check picasso", "success");

                    }

                    @Override
                    public void onError() {
                        Picasso.with(getContext().getApplicationContext()).load(petContents.getImgUrl()).placeholder(R.drawable.default_image).into(petImg);
                        Log.e("check picasso", "error");

                    }
                });*/


                return v;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {


                return null;


            }

        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                LocationAddress userInfo  = markerUserHashMap.get(marker);
                Intent intent = new Intent(getActivity(), UserPetList.class);
                intent.putExtra("id", userInfo.getOwner_id());
                startActivity(intent);
            }
        });

    }

    private void initLocationParameters() {
        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);


        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        result = LocationServices.getSettingsClient(getActivity()).checkLocationSettings(locationSettingsRequest);
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);

                    if (!checkPermissions()) {
                        requestPermissions();
                    } else {
                        getCurrentLocation(); //from create
                    }

                    // requests here.
                } catch (ApiException exception) {
                    Log.e("Exception", "Test2");
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) exception;

                                Log.e("check error","Test1");
                                if(getActivity() == null){
                                    Log.e("Get Activity", "yes null");
                                }

                                resolvable.startResolutionForResult(
                                        getActivity(),
                                        REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                            } catch (ClassCastException e) {
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            }
        });


    }
    private boolean checkPermissions() {
        int permissionState = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            startLocationPermissionRequest();


        } else {
            Log.i(TAG, "Requesting permission");
            startLocationPermissionRequest();
        }
    }
    public void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient = new FusedLocationProviderClient(getActivity());
            mMap.setMyLocationEnabled(true);
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    mMap.clear();
                    location = locationResult;
                    LatLng latLng = new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());

                    //  mMap.addMarker(new MarkerOptions().position(latLng).title("You").icon(BitmapDescriptorFactory.fromResource(R.drawable.user_marker)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                    mMap.animateCamera(CameraUpdateFactory.zoomTo(20));
                    getNearbyUser(location);
                }
            };
            fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, locationCallback, getMainLooper());
        }
    }
    public void getNearbyUser(final LocationResult currentLocationResult){

        firebaseDatabase = FirebaseDatabase.getInstance();
        table_user = firebaseDatabase.getReference("location");
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot children: dataSnapshot.getChildren()){
                    locationAddress = children.getValue(LocationAddress.class);
                    String currentUser = mAuth.getCurrentUser().getUid();
                    if(!locationAddress.getOwner_id().equals(currentUser)){
                        LatLng userLatLng = new LatLng(Double.valueOf(locationAddress.getLatitude()),
                                Double.valueOf(locationAddress.getLongitude()));

                        Location currentLocation = new Location("currentLocation");
                        currentLocation.setLatitude(currentLocationResult.getLastLocation().getLatitude());
                        currentLocation.setLongitude(currentLocationResult.getLastLocation().getLongitude());

                        Location userLocation = new Location("userLocation");
                        userLocation.setLatitude(userLatLng.latitude);
                        userLocation.setLongitude(userLatLng.longitude);

                        float distance = currentLocation.distanceTo(userLocation)/1000;

                        if(distance <= distanceKM){
                            icon = BitmapDescriptorFactory.fromResource(R.drawable.man_icon);
                            marker =  mMap.addMarker(new MarkerOptions()
                                    .position(userLatLng)
                                    .icon(icon));
                            markerUserHashMap.put(marker,locationAddress);
                        }




                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        Log.e("Activity", "Test");
        Log.d("Activity","Test1");
        super.onActivityResult(requestCode, resultCode,data);

        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        if (!checkPermissions()) {
                            requestPermissions();
                        } else {
                            getCurrentLocation(); //from onActivityResult
                        }
                        break;
                    case RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        Log.d("Activity","Canccel");

                        Toast.makeText(getContext(), "Permission Denied!", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                break;
        }
    }


}
