package com.example.dressassistant;

/**
 * Created by 洪祺瑜 on 2017/12/2.
 */
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MySpace extends AppCompatActivity {
    private String UserName;
    private SQLiteDatabase db;
    private static final String DB_NAME = "dressassistant.db";
    public void getUserName() {
        Intent intent = getIntent();
        UserName = intent.getStringExtra("UserName");
    }
    //打开数据库
    public void OpenCreateDB() {
        try {
            db = openOrCreateDatabase(DB_NAME, this.MODE_PRIVATE, null);
        } catch (Throwable e) {
            Log.e("tag", "open error:" + e.getMessage());
            db = null;
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS PersInfo(_id INTEGER PRIMARY KEY, pers_UsID VARCHAR, pers_UsNa VARCHAR, pers_Password VARCHAR, pers_Phone VARCHAR, pers_HePi VARCHAR, pers_Birthday VARCHAR, pers_FILID VARCHAR, pers_FLID VARCHAR, pers_FaID VARCHAR, pers_CUID VARCHAR, pers_PlID VARCHAR, pers_FeID VARCHAR, pers_GDUID VARCHAR, pers_MyMe VARCHAR, pers_CPLID VARCHAR, pers_FCID VARCHAR, pers_Q1 VARCHAR, pers_Q2 VARCHAR, pers_Q3 VARCHAR, pers_A1 VARCHAR, pers_A2 VARCHAR, pers_A3 VARCHAR)");
        } catch (SQLException se) {
            String msg = "doInstall.error:[%s].%s";
            Log.d("tag", String.format(msg, se.getClass(), se.getMessage()));
        }
    }
    private String SystemTime;
    public void getSystemTime(){
        //系统时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        SystemTime = formatter.format(curDate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myspace);
        getUserName();
        OpenCreateDB();//打开数据库
        getSystemTime();
        if(UserName != null){
            TextView textView2=(TextView) findViewById(R.id.textView2);
            textView2.setText(UserName);
        }
        Button bu=(Button) findViewById(R.id.button8);
        bu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MySpace.this,HairDetails.class);
                intent.putExtra("UserName",UserName);
                startActivity(intent);
            }
        });
        Button b=(Button) findViewById(R.id.button5);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MySpace.this,DressStyle.class);
                intent.putExtra("UserName",UserName);
                startActivity(intent);
            }
        });
        Button but=(Button) findViewById(R.id.button6);
        but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MySpace.this,MakeupStyle.class);
                intent.putExtra("UserName",UserName);
                startActivity(intent);
            }
        });
        Button buu=(Button) findViewById(R.id.button10);
        buu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MySpace.this,Login.class);
                UserName = null;
                intent.putExtra("UserName",UserName);
                startActivity(intent);
            }
        });
        Button bb=(Button)findViewById(R.id.button);
        bb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MySpace.this,MainActivity.class);
                intent.putExtra("UserName",UserName);
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
                Cursor cur = db.rawQuery("select * from FavoriteDetail where fade_FaID ='" + UserName +"'",null);
                if(cur.getCount() == 0) {
                    Toast.makeText(MySpace.this, "未有收藏记录，快去收藏", Toast.LENGTH_SHORT).show();
                    cur.close();
                }
                else {
                    cur.close();
                    intent.putExtra("UserName",UserName);
                    startActivity(intent);
                }
            }
        });
        Button bc=(Button)findViewById(R.id.buttonc);
        bc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MySpace.this,MyHistory.class);
                intent.putExtra("UserName",UserName);
                if(UserName == null){
                    Toast.makeText(MySpace.this, "未登陆!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Cursor cur = db.rawQuery("select * from UHPl where uhpl_UsID = '" + UserName + "'", null);
                if(cur.getCount() == 0) {
                    Toast.makeText(MySpace.this, "未有历史计划", Toast.LENGTH_SHORT).show();
                    cur.close();
                }
                else {
                    cur.close();
                    intent.putExtra("UserName",UserName);
                    startActivity(intent);
                }
            }
        });
        Button bn=(Button)findViewById(R.id.buttonn);
        bn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MySpace.this,MyPlan.class);
                intent.putExtra("UserName",UserName);
                if(UserName == null){
                    Toast.makeText(MySpace.this, "未登陆!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Cursor cur = db.rawQuery("select * from PlDe where plde_PlID ='" + UserName+SystemTime +"'",null);
                if(cur.getCount() == 0) {
                    Toast.makeText(MySpace.this, "还未有计划，快去添加", Toast.LENGTH_SHORT).show();
                    cur.close();
                }
                else {
                    cur.close();
                    intent.putExtra("UserName",UserName);
                    startActivity(intent);
                }
            }
        });
        LinearLayout l=(LinearLayout)findViewById(R.id.Lin2);
        l.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent in = getIntent();
                Intent intent = new Intent(MySpace.this,Myself.class);
                intent.putExtra("UserName",UserName);
                startActivity(intent);

            }
        });
    }
}


