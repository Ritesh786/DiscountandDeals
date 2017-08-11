package com.companyproject.fujitsu.discountdeals;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StoreDetail extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView mremarklist;

    private TextAdapter mNameAdapter;
    private List<Customeclass> dummydata = new ArrayList<>();
    ImageView mbackimage;

    String storeid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);

        mremarklist = (ListView) findViewById(R.id.remark_list);

        mbackimage = (ImageView) findViewById(R.id.go_back);
        mbackimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StoreDetail.this.finish();
            }
        });

        mNameAdapter = new TextAdapter(StoreDetail.this, dummydata);
        mremarklist.setAdapter(mNameAdapter);
        mremarklist.setOnItemClickListener(this);

        storeid = getIntent().getStringExtra("storeid");
        GetStoreLIst();

    }


    public void GetStoreLIst(){
        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(StoreDetail.this);
        pDialog.setMessage("Getting Data ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        final String KEY_USERID = "user_id";

        String url = null;
        URL sourceUrl = null;
        String REGISTER_URL = "http://deal.condoassist2u.com/api/shopping-mall.html?shop_id="+storeid;

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
                                JSONArray jsonArray = jsonresponse.getJSONArray("shopping_mall_list");
                                Log.d("array00","jarray "+jsonArray.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    Customeclass customeclass = new Customeclass();
                                    customeclass.setText1(obj.getString("store_name"));
                                    customeclass.setText2(obj.getString("city_name"));
                                    customeclass.setStoreid(obj.getString("store_id"));
                                    dummydata.add(customeclass);
                                    Log.d("dummydata00","dummy11 "+dummydata);
                                }
                                mNameAdapter.notifyDataSetChanged();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(StoreDetail.this);
                                builder.setMessage("Shopping Mall Not Found....")
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
                      //  Log.d("userid00", "user00"+user_id);
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
        RequestQueue requestQueue = Volley.newRequestQueue(StoreDetail.this);
        requestQueue.add(stringRequest);

        if(dummydata!=null) dummydata.clear();

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Customeclass mo123 = (Customeclass) parent.getItemAtPosition(position);

        Intent newsdetailintnt = new Intent(StoreDetail.this,DealDetail.class);
        newsdetailintnt.putExtra("storeid",mo123.getStoreid());;
        startActivity(newsdetailintnt);


    }
}
