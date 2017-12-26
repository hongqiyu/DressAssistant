package com.example.dressassistant;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by 洪祺瑜 on 2017-12-24.
 */

public class Forgetpagetwo extends AppCompatActivity {

    private static final String DB_NAME="dressassistant.db";
    private SQLiteDatabase db;

    //    判断问题和回答是否存在
    private boolean isValidUser(String strQ1, String strQ2, String strQ3, String strA1, String strA2, String strA3){
        Cursor cursor = db.rawQuery("select * from PersInfo where pers_Q1='" + strQ1 + "'and pers_Q2='" + strQ2 + "'and pers_Q3='" + strQ3 + "'and pers_A1='" + strA1 + "'and pers_A2='" + strA2 + "'and pers_A3='" + strA3 ,null);
        if(cursor.getCount() == 1){
            cursor.close();
            return true;
        }
        else {
            cursor.close();
            return false;
        }
    }

    //打开数据库
    private void OpenCreateDB()
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
            db.execSQL("CREATE TABLE IF NOT EXISTS PersInfo(_id INTEGER PRIMARY KEY AUTOINCREMENT, pers_UsID VARCHAR, pers_UsNa VARCHAR, pers_Password VARCHAR, pers_Phone VARCHAR, pers_HePi VARCHAR, pers_Birthday VARCHAR, pers_FILID VARCHAR, pers_FLID VARCHAR, pers_FaID VARCHAR, pers_CUID VARCHAR, pers_PlID VARCHAR, pers_FeID VARCHAR, pers_GDUID VARCHAR, pers_MyMe VARCHAR, pers_CPLID VARCHAR, pers_FCID VARCHAR, pers_Q1 VARCHAR, pers_Q2 VARCHAR, pers_Q3 VARCHAR, pers_A1 VARCHAR, pers_A2 VARCHAR, pers_A3 VARCHAR)");
        }
        catch(SQLException se)
        {
            String msg = "doInstall.error:[%s].%s";
            Log.d("tag",String.format(msg,se.getClass(), se.getMessage()));
        }
    }
    //判断xml上的输入是否为空
    private boolean isStrEmpty(String strInput)
    {
        if(strInput.equals(""))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void function(String strQ1, String strQ2, String strQ3, String strA1, String strA2, String strA3){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpagetwo);
        Button b=(Button) findViewById(R.id.button24);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(Forgetpagetwo.this,Forgetpagethree.class);
                startActivity(intent);
            }
        });
    }
}
