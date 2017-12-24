package com.example.dressassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by 洪祺瑜 on 2017-12-24.
 */


public class Forgetpageone extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpageone);
        Button b=(Button) findViewById(R.id.button23);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(Forgetpageone.this,Forgetpagetwo.class);
                startActivity(intent);
            }
        });
    }
}
