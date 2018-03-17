package com.banregio.examenbregio.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banregio.examenbregio.R;
import com.banregio.examenbregio.interfaces.DialogDoubleActions;
import com.banregio.examenbregio.net.APIClient;
import com.banregio.examenbregio.net.IApiClient;
import com.banregio.examenbregio.net.data.Sucursal;
import com.banregio.examenbregio.utils.ProgressLayout;
import com.banregio.examenbregio.utils.UI;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jordan on 16/03/2018.
 */

public class SucursalesFragment extends GenericFragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnInfoWindowClickListener {

    List<Sucursal> sucursales;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    AlertDialog optionsPicker;

    private GoogleMap mMap;
    double latitude;
    double longitude;
    private int PROXIMITY_RADIUS = 10000;

    public static SucursalesFragment newInstance() {
        SucursalesFragment fragment = new SucursalesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_sucursales, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        ProgressLayout.show(getContext());

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged", "entered");

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }


        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        Log.d("onLocationChanged", String.format("latitude:%.3f longitude:%.3f", latitude, longitude));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }
        Log.d("onLocationChanged", "Exit");
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        showOnInfoClick(marker);
    }

    private void showOnInfoClick(final Marker marker) {
        optionsPicker = new AlertDialog.Builder(getContext()).create();
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.marker_click_dialog, null);
        final LatLng markerLocation = marker.getPosition();
        view.findViewById(R.id.btnDrive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:" + latitude + ","
                                + longitude + "?q="
                                + markerLocation.latitude + ","
                                + markerLocation.longitude + "(" + marker.getTitle() + ")"));
                getContext().startActivity(intent);

            }
        });
        view.findViewById(R.id.btnShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSend = new Intent();
                intentSend.setAction(Intent.ACTION_SEND);
                intentSend.setType("text/plain");
                intentSend.putExtra(Intent.EXTRA_TEXT, marker.getTitle() + "\n http://maps.google.com/maps?q="
                        + markerLocation.latitude + "," + markerLocation.longitude + "&iwloc=A");
                startActivity(Intent.createChooser(intentSend, "Compartir Restaurante"));
            }
        });
        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionsPicker.dismiss();
            }
        });
        optionsPicker.setView(view);
        optionsPicker.show();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                mMap.setOnInfoWindowClickListener(this);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            mMap.setOnInfoWindowClickListener(this);
        }

        getSucursales();

    }

    private void getSucursales() {
        APIClient.getClient().create(IApiClient.class).getSucursales().enqueue(new Callback<List<Sucursal>>() {
            @Override
            public void onResponse(Call<List<Sucursal>> call, Response<List<Sucursal>> response) {
                sucursales = response.body();
                showSucursales();
            }

            @Override
            public void onFailure(Call<List<Sucursal>> call, Throwable t) {
                ProgressLayout.hide();
                UI.createSimpleCustomDialog(getString(R.string.error_sucursales_cajeros), getFragmentManager(), new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        getSucursales();
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                }, true, true);
            }
        });
    }

    private void showSucursales() {
        if (sucursales.size() > 0) {
            for (int i = 0; i < sucursales.size(); i++) {
                Sucursal sucursal = sucursales.get(i);
                MarkerOptions markerOptions = new MarkerOptions();
                double lat = Double.parseDouble(sucursal.getLatitude());
                double lng = Double.parseDouble(sucursal.getLongitude());
                LatLng latLng = new LatLng(lat, lng);
                String placeName = sucursal.getNombre();
                String vicinity = "Dirección: " + sucursal.getDomicilio() + "\nTelefono: " + sucursal.getTelefonoApp()
                        + "\nHorario: " + sucursal.getHorario();
                markerOptions.title(placeName)
                        .position(latLng)
                        .snippet(vicinity)
                        .icon(BitmapDescriptorFactory.defaultMarker(sucursal.getTipo().equals("S") ? 20.0f : 0f));
                mMap.addMarker(markerOptions);
            }
        }
        ProgressLayout.hide();
    }



    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
}
