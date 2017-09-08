package com.companyproject.fujitsu.discountdeals;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

//import com.google.zxing.integration.android.IntentIntegrator;
//import com.google.zxing.integration.android.IntentResult;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.qrcode.encoder.QRCode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class ReedemCoupon extends Fragment implements View.OnClickListener {

    Button msubmitbtn,mqrbtn;
    Dialog dialog;
 //   private IntentIntegrator qrScan;
    EditText mcoupontxt;
    private  int QRCODE = 1;

    UserSessionManager session;
  String user_id;
    public ReedemCoupon() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_reedem_coupon, container, false);

        msubmitbtn = (Button) view.findViewById(R.id.submit_btn);
        mqrbtn = (Button) view.findViewById(R.id.qr_btn);
        mcoupontxt = (EditText) view.findViewById(R.id.coupon_edttxt);
        msubmitbtn.setOnClickListener(this);
        mqrbtn.setOnClickListener(this);

        session = new UserSessionManager(getActivity());

        HashMap<String, String> user = session.getUserDetails();

        user_id = user.get(UserSessionManager.KEY_NAME);

        dialog = new Dialog(getActivity(), android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialogcl);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        return view;

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.submit_btn:

                reedemcoupon();

                break;
            case R.id.qr_btn:

              Intent intent =new Intent(getActivity(), QrCode.class);
               startActivityForResult(intent,QRCODE);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == QRCODE){
          if(data == null){
              Toast.makeText(getContext(),"Please Scan QR Code...", Toast.LENGTH_LONG).show();
          }else {
              mcoupontxt.setText(data.getStringExtra("cropimage"));
          }

        }
    }

    public void reedemcoupon(){

        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(this.getActivity());
        pDialog.setMessage("Getting Data ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        final String code = mcoupontxt.getText().toString().trim();

        final String KEY_USERID = "user_id";
        final String Key_Code = "code";

        String url = null;
        URL sourceUrl = null;
        String REGISTER_URL = "http://deal.condoassist2u.com/api/validate-coupon.html";

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
//                                JSONArray jsonArray = jsonresponse.getJSONArray("store_list");
//                                Log.d("array00","jarray "+jsonArray.toString());
//                                for (int i = 0; i < jsonArray.length(); i++) {
//
//                                    JSONObject obj = jsonArray.getJSONObject(i);
//                                    Customeclass customeclass = new Customeclass();
//                                    customeclass.setText1(obj.getString("store_name"));
//                                    customeclass.setText2(obj.getString("city_name"));
//                                    customeclass.setStoreid(obj.getString("store_id"));
//                                    dummydata.add(customeclass);
//                                    Log.d("dummydata00","dummy11 "+dummydata);
//                                }
//                                mNameAdapter.notifyDataSetChanged();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("Invalid Coupon Code...")
                                        .setNegativeButton("ok", null)
                                        .create()
                                        .show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //  Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                        pDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("userid00", "user00"+user_id);
                        //    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();

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
                params.put(Key_Code, code);
                params.put(KEY_USERID, user_id);
                return params;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    }



