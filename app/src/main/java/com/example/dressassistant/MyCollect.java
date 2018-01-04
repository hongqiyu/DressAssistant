package com.example.dressassistant;

/**
 * Created by 洪祺瑜 on 2017/12/2.
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class MyCollect extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycollect);
        Button button2 = (Button)findViewById(R.id.button6);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MyCollect.this,MySpace.class);
                startActivity(intent);
            }
        });
    }
}
