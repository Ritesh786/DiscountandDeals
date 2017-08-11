package com.companyproject.fujitsu.discountdeals;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DealDetailone extends AppCompatActivity {

    ImageView mbackimage;
    String storeid = null;
    private DealAdapter mNameAdapter;
    private List<DealClass> dummydata = new ArrayList<>();
    ListView mremarklist;
    JSONArray array;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_detailone);

        mremarklist = (ListView) findViewById(R.id.list_detail);

        mbackimage = (ImageView) findViewById(R.id.back_ads);
        mbackimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DealDetailone.this.finish();
            }
        });

        Intent intent = getIntent();

        String response = intent.getStringExtra("mylist");


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

                mNameAdapter = new DealAdapter(DealDetailone.this, dummydata);
                mremarklist.setAdapter(mNameAdapter);
                mNameAdapter.notifyDataSetChanged();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
