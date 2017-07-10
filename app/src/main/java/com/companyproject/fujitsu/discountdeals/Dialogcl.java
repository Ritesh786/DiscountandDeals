package com.companyproject.fujitsu.discountdeals;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dialogcl extends AppCompatActivity implements View.OnClickListener {

    Button mokbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogcl);

        mokbtn = (Button) findViewById(R.id.ok_btn);
        mokbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Dialogcl.this.finish();
    }
}
