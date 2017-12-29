package com.example.dressassistant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by 洪祺瑜 on 2017-12-29.
 */


public class PersonalDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personaldetails);
        TextView v = (TextView) findViewById(R.id.textView36);//找到你要设透明背景layout的id
        v.getBackground().setAlpha(175);//0~255透明度值
        TextView v2 = (TextView) findViewById(R.id.textView35);//找到你要设透明背景layout的id
        v2.getBackground().setAlpha(175);//0~255透明度值
        TextView v3 = (TextView) findViewById(R.id.textView37);//找到你要设透明背景layout的id
        v3.getBackground().setAlpha(175);//0~255透明度值
        TextView v4 = (TextView) findViewById(R.id.textView38);//找到你要设透明背景layout的id
        v4.getBackground().setAlpha(175);//0~255透明度值
        TextView v5 = (TextView) findViewById(R.id.textView39);//找到你要设透明背景layout的id
        v5.getBackground().setAlpha(175);//0~255透明度值
    }
}

