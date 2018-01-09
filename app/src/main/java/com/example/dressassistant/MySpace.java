package com.example.dressassistant;

/**
 * Created by 洪祺瑜 on 2017/12/2.
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MySpace extends AppCompatActivity {
    private String UserName;
    public void getUserName() {
        Intent intent = getIntent();
        UserName = intent.getStringExtra("UserName");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myspace);
        getUserName();

        Button bu=(Button) findViewById(R.id.button8);
        bu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MySpace.this,HairStyle.class);
                startActivity(intent);
            }
        });
        Button b=(Button) findViewById(R.id.button5);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MySpace.this,DressStyle.class);
                startActivity(intent);
            }
        });
        Button but=(Button) findViewById(R.id.button6);
        but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MySpace.this,MakeupStyle.class);
                startActivity(intent);
            }
        });
        Button buu=(Button) findViewById(R.id.button10);
        buu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MySpace.this,Login.class);
                startActivity(intent);
            }
        });
        Button bb=(Button)findViewById(R.id.button);
        bb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MySpace.this,MainActivity.class);
                startActivity(intent);
            }
        });
        Button bt=(Button)findViewById(R.id.buttonb);
        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MySpace.this,MyCollect.class);
                intent.putExtra("UserName",UserName);
                if(UserName == null){
                    Toast.makeText(MySpace.this, "未登陆!", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(intent);
            }
        });
        LinearLayout l=(LinearLayout)findViewById(R.id.Lin2);
        l.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent in = getIntent();
                String UserName = in.getStringExtra("UserName");
                Intent intent = new Intent(MySpace.this,Myself.class);
                intent.putExtra("UserName",UserName);
                startActivity(intent);

            }
        });
    }
}


