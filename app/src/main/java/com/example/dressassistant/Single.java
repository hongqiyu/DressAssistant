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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private String flag;
    private String dresstype;
    private String sepfid;


    //从sqlite读取图片，并显示在桌面上
    public boolean getPhoto(String photoName, int id) {
        Cursor cur = db.rawQuery("select * from AllPicture where alpi_PiID='" + photoName + "'", null);
        cur.moveToFirst();
        byte[] in = cur.getBlob(cur.getColumnIndex("alpi_PiLo"));
        Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);

        BitmapDrawable bd = new BitmapDrawable(getResources(), bmpout);
        ImageView imageView = (ImageView) findViewById(id);
        imageView.setImageDrawable(bd);
        imageView.invalidate();
        bmpout = null;
        System.gc();
        cur.close();
        return false;
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
    public void getInfomation(){
        Intent intent = getIntent();
        UserName = intent.getStringExtra("UserName");
        flag = intent.getStringExtra("flag");
        dresstype = intent.getStringExtra("type");
        sepfid = intent.getStringExtra("id");
    }
    public void showSeparate(){
        switch (sepfid){
            case "sy":
                showInsy();
                break;
            case "kz":
                break;
            case "qz":
                break;
            case "ps":
                break;
        }
    }
    private String[] temp;
    public void showInsy() {
        TextView text1=(TextView) findViewById(R.id.text1);
        text1.setText("上衣推荐");
        String[] s = {"mf", "dwt", "mndy","wy"};
        int[] id = {R.id.mf1, R.id.mf2, R.id.dwt1, R.id.dwt2, R.id.mndy1, R.id.mndy2, R.id.wy1, R.id.wy2};
        int i = 0;
        int maxPoVa;
        String maxName;
        Cursor cur = null;
        Cursor cur2 = null;
        temp = new String[8];
        while (i < 4) {
            cur = db.rawQuery("select * from AllPicture where alpi_Flag ='Separate'" + "order by alpi_PoVa desc", null);
            while(true) {
                cur.moveToNext();
                maxName = cur.getString(cur.getColumnIndex("alpi_PiID"));
                cur2 = db.rawQuery("select * from Separate where sepa_SeID ='" + maxName + "'and sepa_Type = '" + s[i] + "'", null);
                if (cur2.getCount() > 0) {
                    temp[i * 2] = maxName;
                    getPhoto(maxName, id[(i + 1) * 2 - 2]);
                    break;
                }
            }
            while(true) {
                cur.moveToNext();
                maxName = cur.getString(cur.getColumnIndex("alpi_PiID"));
                cur2 = db.rawQuery("select * from Separate where sepa_SeID ='" + maxName + "'and sepa_Type = '" + s[i] + "'", null);
                if (cur2.getCount() > 0) {
                    temp[i * 2 + 1] = maxName;
                    getPhoto(maxName, id[(i + 1) * 2 - 1]);
                    break;
                }
            }
            i++;
        }
        cur.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single);
        OpenCreateDB();
        getInfomation();
        if(dresstype.equals("separate"))
            showSeparate();


        ImageView pictureA = (ImageView) findViewById(R.id.mf1);
        pictureA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Single.this, SingleDetails.class);
                startActivity(intent);
            }
        });
    }
}
