package com.example.whiskersapp.petwhiskers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.IGoogleCertificatesApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import  com.google.android.gms.location.LocationListener;


import android.Manifest;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;

public class FindPetFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {
    private static final int MY_PERMISSION_CODE = 1000;
    GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location mLastLocation;
    private double latitude, longitude;
    private Marker mMarker;
    private LocationRequest mLocationRequest;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_pet, container ,false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        mapFragment.getMapAsync(this);


        return view;
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, MY_PERMISSION_CODE);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, MY_PERMISSION_CODE);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClien();
                            mMap.setMyLocationEnabled(true);
                        }
                    } else {
                        Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClien();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClien();
            mMap.setMyLocationEnabled(true);
        }
        mGoogleApiClient.connect();


    }


        private synchronized void buildGoogleApiClien() {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(getContext(), "hi :(", Toast.LENGTH_SHORT).show();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if(mLastLocation != null){
                latitude = mLastLocation.getLatitude();
                longitude = mLastLocation.getLongitude();

                LatLng latLng = new LatLng(latitude, longitude);
                LatLng test = new LatLng(10.3804, 123.9645);
                LatLng test1 = new LatLng(10.4008,123.9994);

                MarkerOptions markerOptions = new MarkerOptions()
                        .position(latLng)
                        .title("Your Position")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));


                Location crntLocation=new Location("crntlocation");
                crntLocation.setLatitude(10.4008);
                crntLocation.setLongitude(123.9994);

                Location newLocation=new Location("newlocation");
                newLocation.setLatitude(10.3804);
                newLocation.setLongitude(123.9645);


//float distance = crntLocation.distanceTo(newLocation);  in meters
                float distance =crntLocation.distanceTo(newLocation) / 1000;
                if(distance > 1){
                    mMap.addMarker(new MarkerOptions()
                            .position(test)
                            .title("lacion"));
                    mMap.addMarker(new MarkerOptions()
                            .position(test1)
                            .title("liloan"));
                }
                Toast.makeText(getContext(),String.format("Y%f", distance),Toast.LENGTH_SHORT ).show();
                mMarker = mMap.addMarker(markerOptions);

                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

            }else{
                Toast.makeText(getContext(), "Please turn on your wifi/mobile data", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Wait for a few secs HAHAH", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if(mMarker != null){
            mMarker.remove();
            latitude = location.getLatitude();
            longitude = location.getLongitude();

           LatLng latLng = new LatLng(latitude, longitude);
           MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title("Your Position")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mMarker = mMap.addMarker(markerOptions);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));


            if(mGoogleApiClient != null){
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

            }
        }

    }
}
