package com.companyproject.fujitsu.discountdeals;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button msigninbtn;
    UserSessionManager session;
    EditText musertxt, mpasswordtxt;
    Spinner mlogintype;

    String LOGINURL = "http://deal.condoassist2u.com/api/users/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new UserSessionManager(getApplicationContext());

        msigninbtn = (Button) findViewById(R.id.signin_btn);
        msigninbtn.setOnClickListener(this);

        musertxt = (EditText) findViewById(R.id.useredtxt);
        mpasswordtxt = (EditText) findViewById(R.id.passwordedtxt);
        mlogintype = (Spinner) findViewById(R.id.chooselogintype);
        //   Spinner spinner = (Spinner) findViewById(R.id.chooselogintype);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.newstype_arrays, R.layout.textcolor);

        mlogintype.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        Login();

    }

    private void Login(){

        final String KEY_USER = "email";
        final String KEY_PASSWORD = "password";
        final String KEY_TYPE = "usertype";

        final String USER = musertxt.getText().toString().trim();
        final String PASSWORD = mpasswordtxt.getText().toString().trim();
        final String type = "3";



            String url = null;
            URL sourceUrl = null;
            String REGISTER_URL = "http://deal.condoassist2u.com/api/users/login.html";

            REGISTER_URL = REGISTER_URL.replaceAll(" ", "%20");
            try {
               sourceUrl = new URL(REGISTER_URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
           url = sourceUrl.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //   Log.d("jaba", usernsme);
                            try {
                                JSONObject jsonresponse = new JSONObject(response).getJSONObject("response");
                                String success = jsonresponse.getString("httpCode");
                                if (success.equals("202")) {

                                    final String USERID = jsonresponse.getJSONObject("UserData").getString("user_id");
                                    session.createUserLoginSession(USERID);
                                    Log.d("sess00","user "+USERID);
                                    Intent registerintent = new Intent(MainActivity.this, STORELIST.class);
                                    registerintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    registerintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(registerintent);
                                    finish();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setMessage("Registration Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                         //   Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                             Log.d("jabadidis", type);
                           // Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                        }
                    }) {
                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request

                    params.put(KEY_USER, USER);
                    params.put(KEY_PASSWORD, PASSWORD);
                    params.put(KEY_TYPE, type);
                    return params;

                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
        }


}


