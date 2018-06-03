package com.example.dell.dncstdio;


import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dell.dncstdio.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    Button geoLocationBt;
    TextView temperature;
    private String locationkey = new String();
    private GoogleMap mMap;
    private UiSettings mUiSettings;
    private static final String TAG = MapsActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geoLocationBt = findViewById(R.id.btnsearch);
        geoLocationBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText searchText = findViewById(R.id.maptext);
                searchText.onEditorAction(EditorInfo.IME_ACTION_DONE);
                String search = searchText.getText().toString();
                Geocoding(search);
                temperature = findViewById(R.id.temp);
                temperature.setText("");
                URL locationUrl = NetworkUtils.buildUrlForLocation(search);
                Log.i(TAG,"onCreate : locationUrl : "+locationUrl );
                new FetchLocationDetails().execute(locationUrl);


            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
            mUiSettings = mMap.getUiSettings();
            mUiSettings.setZoomControlsEnabled(true);
            mUiSettings.setCompassEnabled(true);
            mUiSettings.setMyLocationButtonEnabled(true);
            mUiSettings.setAllGesturesEnabled(true);
            mUiSettings.setTiltGesturesEnabled(true);
            mUiSettings.setMapToolbarEnabled(true);
            LatLng sydney = new LatLng(9.771438, 77.33778000000007);
            mMap.addMarker(new MarkerOptions().position(sydney).title("DnC Stdio"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));

    }
    public void Geocoding(String search){
        if (search != null && !search.equals("")) {
            List<Address> addressList = null;
            Geocoder geocoder = new Geocoder(MapsActivity.this);
            try {
                addressList = geocoder.getFromLocationName(search, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title(search));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }catch (Exception e){
                e.getMessage();
                Toast.makeText(getApplicationContext(),"Invalid Location", Toast.LENGTH_SHORT).show();

            }
        }


    }


    private class FetchLocationDetails extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(URL... urls) {
            URL locationUrl = urls[0];
            String LocationSearchResults = null;
            try{
                LocationSearchResults = NetworkUtils.getresponsefromhttpurl(locationUrl);
            }catch (IOException e){
                e.printStackTrace();
            }
            Log.i(TAG,"do in background : LocationSearchResults : "+ LocationSearchResults);
            return LocationSearchResults;
        }
        @Override
        protected void onPostExecute(String LocationSearchResults) {
            if(LocationSearchResults != null && !LocationSearchResults.equals("")) {
                locationkey = parseLocationJSON(LocationSearchResults);
                Log.i(TAG, "parseJSON location : date: " + locationkey);


                URL weatherUrl = NetworkUtils.buildUrlForWeather(locationkey);
                new FetchWeatherDetails().execute(weatherUrl);
                Log.i(TAG,"onCreate : weatherUrl : "+weatherUrl );
            }else{

                Toast.makeText(getApplicationContext(), "Maximum limit exceded", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(LocationSearchResults);

        }

    }

    private class FetchWeatherDetails extends AsyncTask<URL, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(URL... urls) {
            URL weatherUrl = urls[0];
            String weatherSearchResults = null;
            try{
                weatherSearchResults = NetworkUtils.getresponsefromhttpurl(weatherUrl);
            }catch (IOException e){
                e.printStackTrace();
            }
            Log.i(TAG,"do in background : weathersearchresult : "+ weatherSearchResults);
            return weatherSearchResults;
        }
        @Override
        protected void onPostExecute(String weatherSearchResults) {
            if(weatherSearchResults != null && !weatherSearchResults.equals("")) {
                parseJSON(weatherSearchResults);
            }super.onPostExecute(weatherSearchResults);
        }
    }

    private String parseLocationJSON(String LocationSearchResults){

        if(LocationSearchResults != null){
            try {
                Log.i(TAG,"location : key : "+LocationSearchResults);
                JSONArray locArr = new JSONArray(LocationSearchResults);

                JSONObject locRes = locArr.getJSONObject(0);
                String citykey = locRes.getString("Key");
                Log.i(TAG,"onCreate : key : "+citykey);
                return citykey;
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
            Toast.makeText(getApplicationContext(),"Forecast not available", Toast.LENGTH_SHORT).show();
        return null;
    }

    private void parseJSON(String weatherSearchResults){

        Log.i(TAG, "parseJSON: date: " + weatherSearchResults);
        if(weatherSearchResults != null) {try {
            Log.i(TAG, "parseJSON: date: " + weatherSearchResults);
            JSONArray results = new JSONArray(weatherSearchResults);
            JSONObject rootObject = results.getJSONObject(0);
            String temp =rootObject.getJSONObject("Temperature").getString("Value");
            temperature.setText(temp+" °C");
           }catch (JSONException e) {
            e.printStackTrace();}
        }

    }

}
