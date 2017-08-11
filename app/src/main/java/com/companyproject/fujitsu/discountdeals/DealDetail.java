package com.companyproject.fujitsu.discountdeals;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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


public class DealDetail extends AppCompatActivity {

    ImageView mbackimage;
    String storeid = null;
    private DealAdapter mNameAdapter;
    private List<DealClass> dummydata = new ArrayList<>();
    ListView mremarklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_detail);

        mbackimage = (ImageView) findViewById(R.id.back_ads);
        mbackimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DealDetail.this.finish();
            }
        });

        storeid = getIntent().getStringExtra("storeid");

        mremarklist = (ListView) findViewById(R.id.list_detail);

        mNameAdapter = new DealAdapter(DealDetail.this, dummydata);
        mremarklist.setAdapter(mNameAdapter);
    //    mremarklist.setOnItemClickListener(this);
        GetStoreLIst();

    }

    public void GetStoreLIst(){
        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(DealDetail.this);
        pDialog.setMessage("Getting Data ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        final String KEY_USERID = "user_id";

        String url = null;
        URL sourceUrl = null;
        String REGISTER_URL = "http://deal.condoassist2u.com/api/deals.html?store="+storeid;

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
                                JSONArray jsonArray = jsonresponse.getJSONArray("deal_detail");
                                Log.d("array00","jarray "+jsonArray.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    DealClass dealClass = new DealClass();
                                    dealClass.setImage(obj.getString("image_src"));
                                    dealClass.setDealtitle(obj.getString("deal_title"));
                                    dealClass.setCityname(obj.getString("city_name"));
                                    dealClass.setStatename(obj.getString("state_name"));
                                    dealClass.setCountryname(obj.getString("country_name"));
                                    dealClass.setMinimumcredit(obj.getString("minimum_credit_value"));
                                    dealClass.setCategoryname(obj.getString("category_name"));
                                    dummydata.add(dealClass);
                                    Log.d("dummydata00","dummy11 "+dummydata);
                                }
                                mNameAdapter.notifyDataSetChanged();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(DealDetail.this);
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
        RequestQueue requestQueue = Volley.newRequestQueue(DealDetail.this);
        requestQueue.add(stringRequest);

        if(dummydata!=null) dummydata.clear();

    }

}
