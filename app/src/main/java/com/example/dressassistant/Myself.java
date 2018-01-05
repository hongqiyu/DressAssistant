package com.example.dressassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by 洪祺瑜 on 2018-01-03.
 */


public class Myself extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myself);
        LinearLayout l=(LinearLayout)findViewById(R.id.lint2);
        l.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(Myself.this,ChangeName.class);
                startActivity(intent);
            }
        });
        LinearLayout l1=(LinearLayout)findViewById(R.id.lint3);
        l1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(Myself.this,ChangeSignature.class);
                startActivity(intent);
            }
        });
        LinearLayout l2=(LinearLayout)findViewById(R.id.lint4);
        l2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(Myself.this,ChangeTele.class);
                startActivity(intent);
            }
        });
        LinearLayout l3=(LinearLayout)findViewById(R.id.lint5);
        l3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(Myself.this,ChangeBirth.class);
                startActivity(intent);
            }
        });
        LinearLayout l4=(LinearLayout)findViewById(R.id.lint6);
        l4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(Myself.this,ChangePassword.class);
                startActivity(intent);
            }
        });
    }
}
