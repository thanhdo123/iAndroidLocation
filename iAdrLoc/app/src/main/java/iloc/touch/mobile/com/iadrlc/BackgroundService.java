package iloc.touch.mobile.com.iadrlc;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Admin on 7/14/2015.
 */
public class BackgroundService extends IntentService{

    // Acquire a reference to the system Location Manager
    LocationManager locationManager;
    /**
     * An IntentService must always have a constructor that calls the super constructor. The
     * string supplied to the super constructor is used to give a name to the IntentService's
     * background thread.
     */
    public BackgroundService() {
        super("BackgroundService()");
        Log.e("BackgroundService():", "BackgroundService()");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {

        Log.e("onHandleIntent:", "onHandleIntent");

        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                Log.e("NOTIFICATION", "onLocationChanged Triggered");
                makeUseOfNewLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        if(locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, locationListener);
            Log.e("NETWORK_PROVIDER", "LocationManager.NETWORK_PROVIDER");
        }else if(locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
            Log.e("GPS_PROVIDER", "LocationManager.GPS_PROVIDER");
        }else{
            Log.e("NO_PROVIDER", "NO_PROVIDER");
        }

        //while true - test
        while (true){
            Location location = null;
            if(locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }else if(locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

                location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }else{
                Log.e("NO_PROVIDER", "NO_PROVIDER");
            }

            if (location != null) {
                Log.e("activity", "Lat: " + location.getAltitude() + ", " + location.getLongitude());
            }else{
                Log.e("activity", "location NULL");
            }
            try {
                Thread.sleep(10000);
            }catch(Exception e){

            }
            sendRequest(location, "_testthread");
        }
    }

    void sendRequest(Location location, String test){
        HttpClient Client = new DefaultHttpClient();

        // Create URL string

        String URL = "http://locationtracker.byethost7.com/index.php?content=";

        //Log.i("httpget", URL);

        if (location != null) {
            URL += location.getLatitude() + "_" + location.getLongitude();
        }else{
            Log.e("activity", "location NULL");
        }

        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
        String userName = settings.getString("UserName", null);
        URL += "_" + userName;

        //test string
        URL += test;

        try
        {
            String SetServerString = "";

            // Create Request to server and get response

            HttpGet httpget = new HttpGet(URL);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            SetServerString = Client.execute(httpget, responseHandler);

            // Show response on activity

            Log.e("activity", SetServerString);
        }
        catch(Exception ex)
        {
            Log.e("activity", ex.getMessage());
        }
    }

    void makeUseOfNewLocation(Location location){
        Log.e("Altitude:", "" + location.getAltitude());
        Log.e("Longitude:", "" + location.getLongitude());

        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
        String userName = settings.getString("UserName", null);
        Log.e("userName:", userName);

        sendRequest(location, "_testevent");
    }
}
