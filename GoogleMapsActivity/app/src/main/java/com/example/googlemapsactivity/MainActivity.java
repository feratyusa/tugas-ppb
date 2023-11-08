package com.example.googlemapsactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

// Implement OnMapReadyCallback.
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout file as the content view.
        setContentView(R.layout.activity_main);

        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button cari = (Button) findViewById(R.id.cari);
        Button geolocator = (Button) findViewById(R.id.cari_geolocator);
        cari.setOnClickListener(op);
        geolocator.setOnClickListener(op);
    }

    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.cari){
                goToLokasi();
            }
            else if(v.getId() == R.id.cari_geolocator){
                goToAlamat();
            }
        }
    };
    private void goToLokasi(){
        EditText lat = findViewById(R.id.latitude);
        EditText lng = findViewById(R.id.longitude);
        EditText zoom = findViewById(R.id.zoom);

        Double dlat = Double.parseDouble(lat.getText().toString());
        Double dlng = Double.parseDouble(lng.getText().toString());
        Float fzoom = Float.parseFloat(zoom.getText().toString());

        Toast.makeText(this, "Move to Lat:"+dlat + " Long:"+dlng, Toast.LENGTH_LONG).show();
        goToMap(dlat, dlng, fzoom);
    }
    private void goToAlamat(){
        EditText tempat = findViewById(R.id.geolocator);
        Geocoder g = new Geocoder(getBaseContext());
        try {
            List<android.location.Address> daftar = g.getFromLocationName(tempat.getText().toString(), 1);
            Address alamat = daftar.get(0);

            String nemuAlamat = alamat.getAddressLine(0);
            Double dlat = alamat.getLatitude();
            Double dlng = alamat.getLongitude();

            Toast.makeText(this, "Ditemukan"+nemuAlamat, Toast.LENGTH_LONG).show();

            EditText elt = findViewById(R.id.latitude);
            EditText elng = findViewById(R.id.longitude);
            EditText ezoom = findViewById(R.id.zoom);

            Float fzoom = Float.parseFloat(ezoom.getText().toString());
            Toast.makeText(this, "Move to Lat:"+dlat + " Long:"+dlng, Toast.LENGTH_LONG).show();
            goToMap(dlat, dlng, fzoom);

            elt.setText(dlat.toString());
            elng.setText(dlng.toString());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void goToMap(Double lat, Double lng, Float zoom){
        LatLng lokasiBaru = new LatLng(lat, lng);
        gMap.addMarker(new MarkerOptions().position(lokasiBaru).title("Marker in "+lat+":"+lng));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasiBaru, zoom));
    }

    // Get a handle to the GoogleMap object and display marker.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
    }
}