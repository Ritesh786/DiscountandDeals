package com.companyproject.fujitsu.discountdeals;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.PointF;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

public class QrCode extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener, View.OnClickListener {

      private TextView mresultTextView;
      private QRCodeReaderView qrCodeReaderView;
      ImageView mbackimage;
      private  int QRCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mresultTextView = (TextView) findViewById(R.id.resulttextview);
        mbackimage = (ImageView) findViewById(R.id.back_image);
        mbackimage.setOnClickListener(this);

        qrCodeReaderView.setOnQRCodeReadListener(this);

        qrCodeReaderView.setQRDecodingEnabled(true);

        qrCodeReaderView.setTorchEnabled(true);

        qrCodeReaderView.setBackCamera();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        if (text == null) {
            Toast.makeText(QrCode.this,"Please Scan Qr Code....", Toast.LENGTH_LONG).show();
        } else {
            mresultTextView.setText(text);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("cropimage", text);
            setResult(QRCODE, returnIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }

    @Override
    public void onClick(View v) {
        super.onBackPressed();
        }
    }



