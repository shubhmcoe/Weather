package com.example.dell.dncstdio;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by dell on 29-12-2017.
 */

public class NetworkUtils {
    private final static String city ="http://dataservice.accuweather.com/locations/v1/cities/search?apikey=xnvXTjas7RVFFGEe01tzCrgQJ30Zhj2I&q=kolkata";
    private final static String TAG ="NetworkUtils";
    private final static String LOCATION_KEY = "http://dataservice.accuweather.com/locations/v1/cities/search";
    private final static String WEATHER_DB_URL = "http://dataservice.accuweather.com/forecasts/v1/hourly/1hour";
    private final static String API_KEY ="v79eScli2VWYGxv9cFjMVuyR1fCgl5to";
    private final static String QUERY_PARAM = "q";
    private final static String PARAM_API_KEY = "apikey";
    private final static String PARAM_METRIC = "metric";
    private final static String METRIC_VALUE = "true";

    public static URL buildUrlForLocation(String location){
        Uri locuri= Uri.parse(LOCATION_KEY).buildUpon()
                .appendQueryParameter(PARAM_API_KEY,API_KEY)
                .appendQueryParameter(QUERY_PARAM,location)
                .build();
        URL url = null;
        try{
            url = new URL(locuri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.i(TAG,"buildurlforlocation : url : "+url);
        return url;
    }

    public static URL buildUrlForWeather(String locationKey){
        Uri builturi= Uri.parse(WEATHER_DB_URL).buildUpon()
                .appendPath(locationKey)
                .appendQueryParameter(PARAM_API_KEY,API_KEY)
                .appendQueryParameter(PARAM_METRIC,METRIC_VALUE)
                .build();
        URL url = null;
        try{
            url = new URL(builturi.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
       Log.i(TAG,"buildurlforweather : url : "+url);
        return url;
    }

    public static String getresponsefromhttpurl(URL url) throws IOException
    {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in =urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            }else
            {
                return null;

            }
        }finally {
            urlConnection.disconnect();
    }
    }
}
