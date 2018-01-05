package com.example.dressassistant;

/**
 * Created by 洪祺瑜 on 2017/12/2.
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HairDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hairdetails);
        ImageView bu=(ImageView) findViewById(R.id.ive1);
        bu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(HairDetails.this,HairInformation.class);
                startActivity(intent);
            }
        });
    }
}
