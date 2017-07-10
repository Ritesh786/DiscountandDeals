package com.companyproject.fujitsu.discountdeals;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static android.app.DialogFragment.STYLE_NO_FRAME;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePassword extends Fragment implements View.OnClickListener {

    Button mresetbtn;
    UserSessionManager session;
    String user_id;

    EditText moldpassword,mnewpassword,mconfirmpassword;
    Dialog dialog;

    public ChangePassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        session = new UserSessionManager(getActivity());

        mresetbtn = (Button) view.findViewById(R.id.reset_btn);
        mresetbtn.setOnClickListener(this);

        moldpassword =  (EditText) view.findViewById(R.id.oldpass_edttxt);
        mnewpassword =  (EditText) view.findViewById(R.id.Newpass_edttxt);
        mconfirmpassword =  (EditText) view.findViewById(R.id.confirmpass_edttxt);

        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialoguepass);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);

        HashMap<String, String> user = session.getUserDetails();

        // get name
        user_id = user.get(UserSessionManager.KEY_NAME);
        Log.d("useid00","uservalue "+user_id);

        return view;
    }

    @Override
    public void onClick(View v) {

        ChangePassword();

    }

    private void ChangePassword() {

        final String KEY_OLDPASSWORD = "oldpassword";
        final String KEY_NEWPASSWORD = "newpassword";
        final String KEY_USERID = "user_id";

        final String oldpass = moldpassword.getText().toString().trim();
        final String newpass = mnewpassword.getText().toString().trim();
        final String confirmpass = mconfirmpassword.getText().toString().trim();

        if (newpass.equals(confirmpass)) {
            String url = null;
            URL sourceUrl = null;
            String REGISTER_URL = "http://deal.condoassist2u.com/api/merchant-change-password.html";

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
                                    dialog.show();
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

                         //   Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("passw00", "pass00"+oldpass);
                     //       Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();

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

                params.put(KEY_OLDPASSWORD, oldpass);
                params.put(KEY_NEWPASSWORD, newpass);
                params.put(KEY_USERID, user_id);
                    return params;

                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }

        else    {

            Toast.makeText(getActivity(),"New Password And Confirm Password Is Not Matchh..",Toast.LENGTH_SHORT).show();

        }
    }


}
