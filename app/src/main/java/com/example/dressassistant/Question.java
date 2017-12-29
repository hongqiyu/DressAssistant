package com.example.dressassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

/**
 * Created by 洪祺瑜 on 2017-12-24.
 */


public class Question extends AppCompatActivity {

    private void InsertPersInfo(){
        String UserName,UserNick,UserPwd,Q1,Q2,Q3,A1,A2,A3,strHeight,strWeight;
        Intent intent = getIntent();
        UserName = intent.getStringExtra("UserName");
        UserNick = intent.getStringExtra("UserNick");
        UserPwd = intent.getStringExtra("UserPwd");
        Q1 = intent.getStringExtra("Q1");
        Q2 = intent.getStringExtra("Q2");
        Q3 = intent.getStringExtra("Q3");
        A1 = intent.getStringExtra("A1");
        A2 = intent.getStringExtra("A2");
        A3 = intent.getStringExtra("A3");
        strHeight = intent.getStringExtra("strHeight");
        strWeight = intent.getStringExtra("strWeight");

        Intent in = new Intent(Question.this,Login.class);
        in.putExtra("Q1",Q1);
        in.putExtra("Q2",Q2);
        in.putExtra("Q3",Q3);
        in.putExtra("A1",A1);
        in.putExtra("A2",A2);
        in.putExtra("A3",A3);
        in.putExtra("UserName",UserName);
        in.putExtra("UserNick",UserNick);
        in.putExtra("UserPwd",UserPwd);
        in.putExtra("strWeight",strWeight);
        in.putExtra("strHeight",strHeight);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        Button TVV=(Button) findViewById(R.id.b1);
        TVV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RadioButton RB1 = (RadioButton)findViewById(R.id.rb1);
                RadioButton RB2 = (RadioButton)findViewById(R.id.rb2);
                RadioButton RB3 = (RadioButton)findViewById(R.id.rb3);
                RadioButton RB4 = (RadioButton)findViewById(R.id.rb4);
                RadioButton RB5 = (RadioButton)findViewById(R.id.rb5);
                RadioButton RB6 = (RadioButton)findViewById(R.id.rb6);
                RadioButton RB7 = (RadioButton)findViewById(R.id.rb7);
                RadioButton RB8 = (RadioButton)findViewById(R.id.rb7);
                RadioButton RB9 = (RadioButton)findViewById(R.id.rb7);

                Intent intent=new Intent(Question.this,Login.class);
                startActivity(intent);
            }
        });
    }
}