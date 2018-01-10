package com.example.dressassistant;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 洪祺瑜 on 2017-12-28.
 */

public class Single extends AppCompatActivity {
    private String UserName;
    private SQLiteDatabase db;
    private static final String DB_NAME = "dressassistant.db";
    private String SystemTime;

    //获取用户ID
    public void getUserName() {
        Intent intent = getIntent();
        UserName = intent.getStringExtra("UserName");
    }

    //从sqlite读取图片，并显示在桌面上
    public boolean getPhoto(String photoName, int id) {
        if (photoName.equals("null")) {
            return false;
        }
        else {
            Cursor cur = db.rawQuery("select * from AllPicture where alpi_PiID='" + photoName + "'", null);
            cur.moveToFirst();
            byte[] in = cur.getBlob(cur.getColumnIndex("alpi_PiLo"));
            Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
            BitmapDrawable bd = new BitmapDrawable(getResources(), bmpout);
            ImageView imageView = (ImageView) findViewById(id);
            imageView.setImageDrawable(bd);
            bmpout = null;
            System.gc();
            cur.close();
            return true;
        }
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
