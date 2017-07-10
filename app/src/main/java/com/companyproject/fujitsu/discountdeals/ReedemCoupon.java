package com.companyproject.fujitsu.discountdeals;


import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

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
import com.google.zxing.qrcode.encoder.QRCode;

import org.json.JSONException;
import org.json.JSONObject;


public class ReedemCoupon extends Fragment implements View.OnClickListener {

    Button msubmitbtn,mqrbtn;
    Dialog dialog;
 //   private IntentIntegrator qrScan;
    EditText mcoupontxt;
    private  int QRCODE = 1;

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

                dialog.show();

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
}

