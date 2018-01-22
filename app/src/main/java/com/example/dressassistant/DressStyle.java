package com.example.dressassistant;

/**
 * Created by 洪祺瑜 on 2017/12/2.
 */
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DressStyle extends AppCompatActivity {
    private String UserName;
    private SQLiteDatabase db;
    private static final String DB_NAME = "dressassistant.db";
    private String SystemTime;

    //获取用户ID
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

    public void getSystemTime(){
        //系统时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        SystemTime = formatter.format(curDate);
    }
    //跳转下个页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dressstyle);
        getUserName();
        OpenCreateDB();
        getSystemTime();

        final Intent intent = new Intent(DressStyle.this, Single.class);
        final Intent intent2 = new Intent(DressStyle.this, Personal.class);
        intent.putExtra("UserName",UserName);

        ImageView.OnClickListener listener = new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserName != null){
                    //推荐
                    intent.putExtra("flag","true");
                    intent2.putExtra("flag","true");
                }
                else {//正常按照，人气值展示
                    intent.putExtra("flag", "false");
                    intent2.putExtra("flag", "false");
                }
                switch(v.getId()){
                    case R.id.d1:
//                        if(UserName == null){
//                            Toast.makeText(DressStyle.this, "未登陆！", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
                            intent.putExtra("id", "sy");
                            intent.putExtra("type", "separate");
                            startActivity(intent);
//                        }
                        break;
                    case R.id.d2:
//                        if(UserName == null){
//                            Toast.makeText(DressStyle.this, "未登陆！", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
                            intent.putExtra("id", "kz");
                            intent.putExtra("type", "separate");
                            startActivity(intent);
//                        }
                        break;
                    case R.id.d3:
//                        if(UserName == null){
//                            Toast.makeText(DressStyle.this, "未登陆！", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
                            intent.putExtra("id", "qz");
                            intent.putExtra("type", "separate");
                            startActivity(intent);
//                        }
//                        break;
                    case R.id.d4:
//                        if(UserName == null){

//                            Toast.makeText(DressStyle.this, "未登陆！", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
                            intent.putExtra("id","ps");
                            intent.putExtra("type", "separate");
                            startActivity(intent);
//                        }
//                        break;
                    default:
                        intent.putExtra("type", "suit");
                        intent.putExtra("id",String.valueOf(v.getId()));
                        startActivity(intent2);
                        break;
                }
            }
        };

        ImageView c1 =(ImageView) findViewById(R.id.c1);
        c1.setOnClickListener(listener);
        ImageView c2 =(ImageView) findViewById(R.id.c2);
        c2.setOnClickListener(listener);
        ImageView c3 =(ImageView) findViewById(R.id.c3);
        c3.setOnClickListener(listener);
        ImageView c4 =(ImageView) findViewById(R.id.c4);
        c4.setOnClickListener(listener);
        ImageView c5 =(ImageView) findViewById(R.id.c5);
        c5.setOnClickListener(listener);
        ImageView c6 =(ImageView) findViewById(R.id.c6);
        c6.setOnClickListener(listener);
        ImageView c7 =(ImageView) findViewById(R.id.c7);
        c7.setOnClickListener(listener);
        ImageView c8 =(ImageView) findViewById(R.id.c8);
        c8.setOnClickListener(listener);
        ImageView f1 =(ImageView) findViewById(R.id.f1);
        f1.setOnClickListener(listener);
        ImageView f2 =(ImageView) findViewById(R.id.f2);
        f2.setOnClickListener(listener);
        ImageView f3 =(ImageView) findViewById(R.id.f3);
        f3.setOnClickListener(listener);
        ImageView f4 =(ImageView) findViewById(R.id.f4);
        f4.setOnClickListener(listener);
        ImageView d1 =(ImageView) findViewById(R.id.d1);
        d1.setOnClickListener(listener);
        ImageView d2 =(ImageView) findViewById(R.id.d2);
        d2.setOnClickListener(listener);
        ImageView d3 =(ImageView) findViewById(R.id.d3);
        d3.setOnClickListener(listener);
        ImageView d4 =(ImageView) findViewById(R.id.d4);
        d4.setOnClickListener(listener);

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DressStyle.this, Personal.class);
                intent.putExtra("type", "special");
                startActivity(intent);
            }
        });

        Button buttonn=(Button)findViewById(R.id.button);
        buttonn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(DressStyle.this,MainActivity.class);
                intent.putExtra("UserName",UserName);
                startActivity(intent);
            }
        });
        Button bu=(Button) findViewById(R.id.button8);
        bu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(DressStyle.this,HairDetails.class);
                intent.putExtra("UserName",UserName);
                startActivity(intent);
            }
        });
        Button but=(Button) findViewById(R.id.button6);
        but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(DressStyle.this,MakeupStyle.class);
                intent.putExtra("UserName",UserName);
                startActivity(intent);
            }
        });
        Button butt=(Button) findViewById(R.id.button7);
        butt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(DressStyle.this,MySpace.class);
                intent.putExtra("UserName",UserName);
                startActivity(intent);
            }
        });

    }
}

