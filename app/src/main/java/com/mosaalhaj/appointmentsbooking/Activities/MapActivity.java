
package com.mosaalhaj.appointmentsbooking.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mosaalhaj.appointmentsbooking.Models.Company;
import com.mosaalhaj.appointmentsbooking.R;

import static com.mosaalhaj.appointmentsbooking.Items.Constants.COMPANY;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map ;
    private Company company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        company = getIntent().getParcelableExtra(COMPANY);




        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment_company_location);
        mapFragment.getMapAsync(this);






    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        double lat , lng ;

        lat = Double.parseDouble(company.getAddress().getLatitude());
        lng = Double.parseDouble(company.getAddress().getLongitude());

        LatLng companyGeo = new LatLng(lat,lng);
        map.addMarker(new MarkerOptions()
                .position(companyGeo)
                .title(company.getName()));
        map.moveCamera(CameraUpdateFactory.newLatLng(companyGeo));


    }



//    private void getDeviceLocation(GoogleMap map) {
//        /*
//         * Get the best and most recent location of the device, which may be null in rare
//         * cases when a location is not available.
//         */
//        try {
//            if (locationPermissionGranted) {
//                Task<Location> locationResult = locationProviderClient.getLastLocation();
//                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//                        if (task.isSuccessful()) {
//                            // Set the map's camera position to the current location of the device.
//                            Location lastKnownLocation = task.getResult();
//                            if (lastKnownLocation != null) {
//
//                                LatLng latLng = new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude() );
//                                map.addMarker(new MarkerOptions().position(latLng).title("Your Location"));
//                            }
//                        }
//                    }
//                });
//            }
//        } catch (SecurityException e)  {
//            Log.e("Exception: %s", e.getMessage(), e);
//        }
//    }

//    private void getLocationPermission(GoogleMap map) {
//        /*
//         * Request location permission, so that we can get the location of the
//         * device. The result of the permission request is handled by a callback,
//         * onRequestPermissionsResult.
//         */
//        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            locationPermissionGranted = true;
//            map.setMyLocationEnabled(true);
//            map.getUiSettings().setMyLocationButtonEnabled(true);
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},0);
//        }
//    }

}