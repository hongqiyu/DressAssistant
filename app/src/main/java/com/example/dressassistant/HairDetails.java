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

public class HairDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hairdetails);
        ImageView bu=(ImageView) findViewById(R.id.iv1);
        bu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(HairDetails.this,HairInformation.class);
                startActivity(intent);
            }
        });
        Button b=(Button) findViewById(R.id.button5);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(HairDetails.this,DressStyle.class);
                startActivity(intent);
            }
        });
        Button but=(Button) findViewById(R.id.button6);
        but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(HairDetails.this,MakeupStyle.class);
                startActivity(intent);
            }
        });
        Button butto=(Button) findViewById(R.id.button7);
        butto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(HairDetails.this,MySpace.class);
                startActivity(intent);
            }
        });
        Button buttonn=(Button)findViewById(R.id.button);
        buttonn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(HairDetails.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
