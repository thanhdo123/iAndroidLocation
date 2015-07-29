package com.example.bootstart;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.content.Context;

public class Service {

    private JSONParser jsonParser;

    public static String URL = "http://locationtracker.byethost7.com";

    // constructor
    public Service(){
        jsonParser = new JSONParser();
    }

    /**
     * Function to Login
     **/
    public JSONObject loginUser(String username, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "login"));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(URL + "/login_service.php", params);
        return json;
    }
}

