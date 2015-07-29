package com.example.bootstart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends Activity {

    public static final String PREFS_NAME = "MyPrefsFile";
    private Button login;
    private EditText txtUserName;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=(Button)findViewById(R.id.sign_in_button);
        txtUserName=(EditText)findViewById(R.id.txtUserName);
        txtPassword=(EditText)findViewById(R.id.txtPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (  ( !txtUserName.getText().toString().equals("")) && ( !txtPassword.getText().toString().equals("")) )
                {
                    execute(v);
                }
                else if ( ( !txtUserName.getText().toString().equals("")) )
                {
                    Toast.makeText(getApplicationContext(),
                            "Password empty", Toast.LENGTH_SHORT).show();
                }
                else if ( ( !txtPassword.getText().toString().equals("")) )
                {
                    Toast.makeText(getApplicationContext(),
                            "Email empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Email and Password fields empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void execute(View view){
        new ProcessLogin().execute();
    }


    private class ProcessLogin extends AsyncTask<String, String, JSONObject> {


        private ProgressDialog pDialog;

        String username,password;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            username = txtUserName.getText().toString();
            password = txtPassword.getText().toString();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setTitle("ISysLoc");
            pDialog.setMessage("...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            Service userFunction = new Service();
            JSONObject json = userFunction.loginUser(username, password);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            try {
                if (json.getString("success") != null) {

                    String res = json.getString("success");

                    if(Integer.parseInt(res) == 1){

                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("UserName", txtUserName.getText().toString());
                        editor.commit();

                        Log.d("UserName :", txtUserName.getText().toString());

                        pDialog.dismiss();

                        Intent background = new Intent(getBaseContext(), BackgroundService.class);
                        getBaseContext().startService(background);


                        /**
                         * Close Login Screen
                         **/
                        finish();
                    }else{
                        pDialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "Email and Password is not correct", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
