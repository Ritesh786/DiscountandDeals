package com.companyproject.fujitsu.discountdeals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lue on 18-05-2017.
 */

public class TextAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private List<Customeclass> dummyData;


    public TextAdapter(Context Contex, List<Customeclass> mDummydata) {

        dummyData = mDummydata;

        mLayoutInflater = LayoutInflater.from(Contex);
    }

    @Override
    public int getCount() {
        return dummyData.size();
    }

    @Override
    public Object getItem(int position) {
        return dummyData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Customeclass custome = (Customeclass) getItem(position);
        convertView = mLayoutInflater.inflate(R.layout.textshow,parent,false);
        TextView text1 = (TextView) convertView.findViewById(R.id.textview1);
       // ImageView postimage = (ImageView) convertView.findViewById(R.id.image123);
        TextView text2 = (TextView) convertView.findViewById(R.id.textview2);

        text1.setText(custome.getText1());
        text2.setText(custome.getText2());
   //     postimage.showContextMenu();

        return convertView;
    }
}

