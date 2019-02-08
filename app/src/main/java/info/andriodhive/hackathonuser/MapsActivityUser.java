package info.andriodhive.hackathonuser;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;

public class MapsActivityUser extends  FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

//    Double saurav = 25.2602856;
//    Double shashwat = 82.9921155;


    private GoogleMap mMap;
    private LatLng mCenterLatLong;
    private String Empty = "Dustbin Empty" , Full = "Dustbin Full", HalfFull = "Dustbin Half full";
    private int fillRamanujan;
    private TextView mUserName, mSwaccthaPoints;
    private int swacchtaPoints = 0;
    Double d, d1,d2;

    private Button Throw;

    private static String TAG = MapsActivityUser.class.getSimpleName();
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 5000;
    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1000;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mLocationProviderClient;

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Location location = locationResult.getLastLocation();


            if (location != null)
                changeMap(location);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_user);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mUserName = findViewById(R.id.tv_username);
        mSwaccthaPoints = findViewById(R.id.tv_swacchta_points);
        Throw = findViewById(R.id.btn_throw);



        DatabaseReference myref = FirebaseDatabase.getInstance().getReference();
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.child("UserName").getValue(String.class);
                mUserName.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        mLocationProviderClient = new FusedLocationProviderClient(this);
        mapFragment.getMapAsync(this);
        if (checkPlayServices()) {
            if (!isLocationEnabled(this)) {
                // notify user
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("Location not enabled!");
                dialog.setPositiveButton("Open location settings", (paramDialogInterface, paramInt) -> {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                });
                dialog.setNegativeButton("Cancel", (paramDialogInterface, paramInt) -> {
                    // TODO Auto-generated method stub

                });
                dialog.show();
            }
            buildGoogleApiClient();
        } else {
            Toast.makeText(this, "Location not supported in this device", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "OnMapReady");
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setOnCameraIdleListener(() -> {
            mCenterLatLong = mMap.getCameraPosition().target;
            Location mLocation = new Location("");
            mLocation.setLatitude(mCenterLatLong.latitude);
            mLocation.setLongitude(mCenterLatLong.longitude);

        });
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents, null);

                TextView title = ((TextView) infoWindow.findViewById(R.id.title1));
                title.setText(marker.getTitle());

                TextView snippet = ((TextView) infoWindow.findViewById(R.id.snippet));
                snippet.setText(marker.getSnippet());

                return infoWindow;
            }
        });



        fillRamanujan = getIntent().getIntExtra("data1",0);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.child("Dustbin1").setValue(ramanujan);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double saurav = dataSnapshot.child("Dustbin1").child("latitude").getValue(Double.class);
                Double shashwat = dataSnapshot.child("Dustbin1").child("longitude").getValue(Double.class);

                LatLng ramanujan = new LatLng(saurav,shashwat);

                if (fillRamanujan < 20){
                    Marker mRamanujan = mMap.addMarker(new MarkerOptions().
                            position(ramanujan).
                            title("Dustbin at G8").
                            snippet(Empty).
                            alpha(.7f).
                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));}

                else if(fillRamanujan > 20){
                    mMap.addMarker(new MarkerOptions().position(ramanujan).
                            title("Dustbin at G8").
                            snippet(HalfFull).
                            alpha(.7f).
                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                }
                else  {
//            mRamanujan.remove();

                    mMap.addMarker(new MarkerOptions().position(ramanujan).
                            title("Dustbin at G8").
                            snippet(Full).
                            alpha(.7f).
                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onConnected(Bundle bundle) {
        if (
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_FINE_LOCATION);

            return;
        }
        goToCurrentLocation();
    }

    @SuppressLint("MissingPermission")
    private void goToCurrentLocation() {
        mMap.setMyLocationEnabled(true);
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setNumUpdates(1);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, null);
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Google API Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Unable to connect", Toast.LENGTH_SHORT).show();
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            mLocationProviderClient.removeLocationUpdates(mLocationCallback);
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GoogleApiAvailability.getInstance().isUserResolvableError(resultCode)) {
                GoogleApiAvailability.getInstance().getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.e(TAG, "Google Play services not usable: " + resultCode);
                finish();
            }
            return false;
        }
        return true;
    }

    private void changeMap(Location location) {
        Log.d(TAG, "Reaching map" + mMap);
        // check if map is created successfully or not
        if (mMap != null) {
            Double lat = location.getLatitude();
            Double lng = location.getLongitude();
            LatLng latLong = new LatLng(lat, lng);

            DatabaseReference myref = FirebaseDatabase.getInstance().getReference();
            myref.child("location").setValue(location);

            myref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Double lat1 = dataSnapshot.child("Dustbin1").child("latitude").getValue(Double.class);
                    Double lng1 = dataSnapshot.child("Dustbin1").child("longitude").getValue(Double.class);
                    Double lat2 = dataSnapshot.child("location").child("latitude").getValue(Double.class);
                    Double lng2 = dataSnapshot.child("location").child("longitude").getValue(Double.class);

                    Log.e(TAG,lat1.toString());
//                    d1 = (lat1 - lat2)*(lat1-lat2);
//                    d2 = (lng1 - lng2)*(lng1-lng2);
                    int R = 6371; // km
                     d1 = (lng1 - lng2) * Math.cos((lat1 + lat2) / 2);
                     d2 = (lat2 - lat1);
                    d = Math.sqrt(d1 * d1 + d2 * d2) * R*10;


                    myref.child("d").setValue(d);
                    if(d<40){
                        Throw.setVisibility(View.VISIBLE);
                        if (d > 40) {
                            Throw.setVisibility(View.GONE
                            );
                        }
                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLong)
                    .zoom(18f)
                    .tilt(70)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(this, data);
                LatLng latLong = place.getLatLng();
                Location location = new Location("");
                location.setLatitude(latLong.latitude);
                location.setLongitude(latLong.longitude);

                changeMap(location);

            }
        } else {
            Log.e(TAG, PlaceAutocomplete.getStatus(this, data).getStatusMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                goToCurrentLocation();
        }
    }


    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }




}
