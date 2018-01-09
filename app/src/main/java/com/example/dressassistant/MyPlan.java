package com.example.dressassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by 洪祺瑜 on 2018-01-09.
 */


public class MyPlan extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myplan);
        ImageView bu=(ImageView) findViewById(R.id.imv1);
        bu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MyPlan.this,MakeupDetails.class);
                startActivity(intent);
            }
        });
        ImageView imv2=(ImageView) findViewById(R.id.imv2);
        imv2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MyPlan.this,HairInformation.class);
                startActivity(intent);
            }
        });
        ImageView imv3=(ImageView) findViewById(R.id.imv3);
        imv3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MyPlan.this,PersonalDetails.class);
                startActivity(intent);
            }
        });
    }
}
