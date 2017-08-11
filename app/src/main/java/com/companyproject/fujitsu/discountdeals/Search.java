package com.companyproject.fujitsu.discountdeals;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class Search extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText mdealtitle;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date,date1;

    MaterialBetterSpinner mstorename,mcityname,mstatename;

    UserSessionManager session;
    String user_id;
    String stateid,cityid;

    private ArrayList<String>State_id = new ArrayList<String>();
    private ArrayList<String>City_id = new ArrayList<String>();

    List<String> list = new ArrayList();
    List<String> liststate = new ArrayList();
    List<String> listcity = new ArrayList();

    String storeid;

    Button msearch;

    public Search() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mdealtitle = (EditText) view.findViewById(R.id.dealtitle);

        msearch = (Button) view.findViewById(R.id.Search_btn);
        msearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchdeal();

            }
        });

        mstorename = (MaterialBetterSpinner) view.findViewById(R.id.storename);
        mcityname  = (MaterialBetterSpinner) view.findViewById(R.id.city_spinner);
        mstatename  = (MaterialBetterSpinner) view.findViewById(R.id.state_spinner);
        mstorename.setOnItemSelectedListener(this);
        mstatename.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stateid = State_id.get(position);
                Log.d("sid00","stid "+stateid);
            }
        });
        mcityname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityid = City_id.get(position);
                Log.d("cid00","ctid "+cityid);
            }
        });

        session = new UserSessionManager(getActivity());

        HashMap<String, String> user = session.getUserDetails();

        user_id = user.get(UserSessionManager.KEY_NAME);

        storename();
        statename();
        cityname();

        return view;
    }


    public void storename(){

        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(this.getActivity());
        pDialog.setMessage("Getting Data ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        final String KEY_USERID = "user_id";

        String url = null;
        URL sourceUrl = null;
        String REGISTER_URL = "http://deal.condoassist2u.com/api/store-list.html";

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
                                JSONArray jsonArray = jsonresponse.getJSONArray("store_list");
                                Log.d("array00","jarray "+jsonArray.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    String Storename =   obj.getString("store_name");
                                    storeid  =   obj.getString("store_id");

                                    list.add(Storename);
                                }

                                MySpinnerAdapter adapter = new MySpinnerAdapter(getContext(),list);
                                mstorename.setAdapter(adapter);
                                mstorename.setTextColor(getResources().getColor(R.color.black));
                                mstorename.setHintTextColor(getResources().getColor(android.R.color.darker_gray));

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
                params.put(KEY_USERID, user_id);
                return params;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }

    public void statename(){

        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(this.getActivity());
        pDialog.setMessage("Getting Data ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        final String KEY_USERID = "user_id";

        String url = null;
        URL sourceUrl = null;
        String REGISTER_URL = "http://deal.condoassist2u.com/api/state.html";

        REGISTER_URL = REGISTER_URL.replaceAll(" ", "%20");
        try {
            sourceUrl = new URL(REGISTER_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        url = sourceUrl.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   Log.d("jaba", usernsme);
                        try {
                            JSONObject jsonresponse = new JSONObject(response).getJSONObject("response");
                            String success = jsonresponse.getString("httpCode");
                            if (success.equals("202")) {
                                JSONArray jsonArray = jsonresponse.getJSONArray("state_list");
                                Log.d("array00","jarray "+jsonArray.toString());


                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    String Storename =   obj.getString("state_name");
                                    State_id.add(obj.getString("state_id"));

                                    liststate.add(Storename);
                                }

                                MySpinnerAdapter adapter = new MySpinnerAdapter(getContext(),liststate);
                                mstatename.setAdapter(adapter);
                                mstatename.setTextColor(getResources().getColor(R.color.black));
                                mstatename.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
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
                return params;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }
    public void cityname(){

        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(this.getActivity());
        pDialog.setMessage("Getting Data ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        final String KEY_USERID = "user_id";

        String url = null;
        URL sourceUrl = null;
        String REGISTER_URL = "http://deal.condoassist2u.com/api/city.html";

        REGISTER_URL = REGISTER_URL.replaceAll(" ", "%20");
        try {
            sourceUrl = new URL(REGISTER_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        url = sourceUrl.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   Log.d("jaba", usernsme);
                        try {
                            JSONObject jsonresponse = new JSONObject(response).getJSONObject("response");
                            String success = jsonresponse.getString("httpCode");
                            if (success.equals("202")) {
                                JSONArray jsonArray = jsonresponse.getJSONArray("city_list");
                                Log.d("array00","jarray "+jsonArray.toString());


                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    String Storename =   obj.getString("city_name");
                                  //  cityid = obj.getString("city_id");
                                    City_id.add(obj.getString("city_id"));
                                    listcity.add(Storename);
                                }

                                MySpinnerAdapter adapter = new MySpinnerAdapter(getContext(),listcity);
                                mcityname.setAdapter(adapter);
                                mcityname.setTextColor(getResources().getColor(R.color.black));
                                mcityname.setHintTextColor(getResources().getColor(android.R.color.darker_gray));

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
                return params;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void searchdeal(){

        String deaititile = mdealtitle.getText().toString().trim();

        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(this.getActivity());
        pDialog.setMessage("Getting Data ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        final String KEY_USERID = "user_id";

        String url = null;
        URL sourceUrl = null;
        String REGISTER_URL = "http://deal.condoassist2u.com/api/deals.html?deal_name="+deaititile+"&store="+storeid+"&city="+cityid+"&state="+stateid;

        REGISTER_URL = REGISTER_URL.replaceAll(" ", "%20");
        try {
            sourceUrl = new URL(REGISTER_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        url = sourceUrl.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   Log.d("jaba", usernsme);

                        // JSONObject jsonresponse = new JSONObject(response).getJSONObject("response");
                        Intent showadintnt = new Intent(getContext(), DealDetailone.class);
                        showadintnt.putExtra("mylist", response);
                        //      Log.d("json000", String.valueOf(getimage));
                        startActivity(showadintnt);

//                            String success = jsonresponse.getString("httpCode");
//                            if (success.equals("202")) {
//                                JSONArray jsonArray = jsonresponse.getJSONArray("deal_detail");
//                                Log.d("array00","jarray "+jsonArray.toString());
//
//
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

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
                return params;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);



    }


}



