package com.example.dressassistant;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 洪祺瑜 on 2018-01-03.
 */


public class Myself extends AppCompatActivity {
    private static final String DB_NAME="dressassistant.db";
    private SQLiteDatabase db;

    //打开数据库
    public void OpenCreateDB()
    {
        try
        {
            db = openOrCreateDatabase(DB_NAME, this.MODE_PRIVATE, null);
        }
        catch (Throwable e)
        {
            Log.e("tag","open error:" + e.getMessage());
            db = null;
        }

        try
        {
            db.execSQL("CREATE TABLE IF NOT EXISTS PersInfo(_id INTEGER PRIMARY KEY, pers_UsID VARCHAR, pers_UsNa VARCHAR, pers_Password VARCHAR, pers_Phone VARCHAR, pers_HePi VARCHAR, pers_Birthday VARCHAR, pers_FILID VARCHAR, pers_FLID VARCHAR, pers_FaID VARCHAR, pers_CUID VARCHAR, pers_PlID VARCHAR, pers_FeID VARCHAR, pers_GDUID VARCHAR, pers_MyMe VARCHAR, pers_CPLID VARCHAR, pers_FCID VARCHAR, pers_Q1 VARCHAR, pers_Q2 VARCHAR, pers_Q3 VARCHAR, pers_A1 VARCHAR, pers_A2 VARCHAR, pers_A3 VARCHAR)");
        }
        catch(SQLException se)
        {
            String msg = "doInstall.error:[%s].%s";
            Log.d("tag",String.format(msg,se.getClass(), se.getMessage()));
        }
    }

    String NickName,phoneNum,birthday;
    private void QueryQues(String strUserName){
        Cursor cursor = db.rawQuery("select * from PersInfo where pers_UsID='" + strUserName + "'",null);
        if(cursor.moveToFirst()){  //循环遍历查找数组
            NickName = cursor.getString(cursor.getColumnIndex("pers_UsNa"));
            phoneNum = cursor.getString(cursor.getColumnIndex("pers_Phone"));
            birthday = cursor.getString(cursor.getColumnIndex("pers_Birthday"));
            // Toast.makeText(Forgetpagetwo.this,Q1,Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myself);

        OpenCreateDB();
        Intent in = getIntent();
        final String UserName = in.getStringExtra("UserName");

        QueryQues(UserName);
        TextView tv1 = (TextView)findViewById(R.id.tv1);
        tv1.setText(NickName);
        TextView tv2 = (TextView)findViewById(R.id.tv2);
        tv2.setText(phoneNum);
        TextView tv3 = (TextView)findViewById(R.id.tv3);
        tv3.setText(birthday);

        LinearLayout l=(LinearLayout)findViewById(R.id.lint2);
        l.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Myself.this,ChangeName.class);
                intent.putExtra("UserName",UserName);
                startActivity(intent);
            }
        });
        LinearLayout l2=(LinearLayout)findViewById(R.id.lint4);
        l2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(Myself.this,ChangeTele.class);
                intent.putExtra("UserName",UserName);
                startActivity(intent);
            }
        });
        LinearLayout l3=(LinearLayout)findViewById(R.id.lint5);
        l3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(Myself.this,ChangeBirth.class);
                intent.putExtra("UserName",UserName);
                startActivity(intent);
            }
        });
        LinearLayout l4=(LinearLayout)findViewById(R.id.lint6);
        l4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(Myself.this,ChangePassword.class);
                intent.putExtra("UserName",UserName);
                startActivity(intent);
            }
        });
    }
}
