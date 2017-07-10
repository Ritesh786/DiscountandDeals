package com.companyproject.fujitsu.discountdeals;



import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView mremarklist;

    private TextAdapter mNameAdapter;
    private List<Customeclass> dummydata = new ArrayList<>();
    Toolbar toolbar;
    UserSessionManager session;
    String user_id;
    ProgressDialog prog;
 //   Context mcontext = getContext();
    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_blank, container, false);


        mremarklist = (ListView) view.findViewById(R.id.remark_list);

        mNameAdapter = new TextAdapter(getActivity(), dummydata);
        mremarklist.setAdapter(mNameAdapter);
        mremarklist.setOnItemClickListener(this);

        session = new UserSessionManager(getActivity());

        HashMap<String, String> user = session.getUserDetails();

        user_id = user.get(UserSessionManager.KEY_NAME);

        GetStoreLIst();

        return view;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//         if( position == 0 ) {
//
//    DetailFragment fragment = new DetailFragment();
//    FragmentManager manager = getFragmentManager();
//    manager.beginTransaction().replace(R.id.frame_trans, fragment).addToBackStack("Ritesh").commit();
//
//         }
//
//        if( position==1 ){
//
//            Name fragment = new Name();
//            FragmentManager manager = getFragmentManager();
//            manager.beginTransaction().replace(R.id.frame_trans, fragment).addToBackStack("Shailendra").commit();
//
//        }

    }

    public void GetStoreLIst(){
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
                                        Customeclass customeclass = new Customeclass();
                                        customeclass.setText1(obj.getString("store_name"));
                                        customeclass.setText2(obj.getString("city_name"));
                                        dummydata.add(customeclass);
                                        Log.d("dummydata00","dummy11 "+dummydata);
                                    }
                                    mNameAdapter.notifyDataSetChanged();

                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("You Have Entered Wrong Password..")
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
                    params.put(KEY_USERID, user_id);
                    return params;

                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);

        if(dummydata!=null) dummydata.clear();

        }
    }




