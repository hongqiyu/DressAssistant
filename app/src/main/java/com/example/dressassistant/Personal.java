package com.example.dressassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by 洪祺瑜 on 2017-12-29.
 */

public class Personal extends AppCompatActivity {
//    public void showSuit(){
//        switch (sepfid){
//            case "sy":
//                showInsy();
//                break;
//            case "kz":
//                break;
//            case "qz":
//                break;
//            case "ps":
//                break;
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if(dresstype.equals("separate")){
//            if(flag.equals("false")) {
//                showSeparate();
//            }
//            else{
//
//            }
//        }
//        else if(dresstype.equals("suit")){
//            if(flag.equals("false")) {
//                showSuit();
//            }
//            else{
//
//            }
//        }
//        else if(dresstype.equals("special")){
//
//        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal);
        ImageView bu=(ImageView) findViewById(R.id.ii3);
        bu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(Personal.this,PersonalDetails.class);
                startActivity(intent);
            }
        });
    }
}
