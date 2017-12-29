package com.example.dressassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by 洪祺瑜 on 2017-12-28.
 */

public class Single extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single);
        ImageView pictureA = (ImageView) findViewById(R.id.ima);
        pictureA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Single.this, SingleDetails.class);
                startActivity(intent);
            }
        });
    }
}
