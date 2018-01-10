package com.example.dressassistant;

/**
 * Created by 洪祺瑜 on 2017/12/2.
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DressStyle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dressstyle);
        Button buttonn=(Button)findViewById(R.id.button);
        buttonn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(DressStyle.this,MainActivity.class);
                startActivity(intent);
            }
        });
        Button bu=(Button) findViewById(R.id.button8);
        bu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(DressStyle.this,HairDetails.class);
                startActivity(intent);
            }
        });
        Button but=(Button) findViewById(R.id.button6);
        but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(DressStyle.this,MakeupStyle.class);
                startActivity(intent);
            }
        });
        Button butt=(Button) findViewById(R.id.button7);
        butt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(DressStyle.this,MySpace.class);
                startActivity(intent);
            }
        });
//        ImageView imv1=(ImageView)findViewById(R.id.imv1);
//        imv1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent=new Intent(DressStyle.this,Single.class);
//                startActivity(intent);
//            }
//        });
//        Button bu3=(Button) findViewById(R.id.button3);
//        bu3.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent=new Intent(DressStyle.this,Personal.class);
//                startActivity(intent);
//            }
//        });
    }
    }

