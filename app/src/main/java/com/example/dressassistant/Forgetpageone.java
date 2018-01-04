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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 洪祺瑜 on 2017-12-24.
 */


public class Forgetpageone extends AppCompatActivity {

    private static final String DB_NAME="dressassistant.db";
    private SQLiteDatabase db;

    //    判断用户名是否存在
    private boolean isValidUser(String strUserName){
        Cursor cursor = db.rawQuery("select * from PersInfo where pers_UsID='"+strUserName+"'",null);
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

    private void function(String strUserName){
        if(isStrEmpty(strUserName) == false){
            if(isValidUser(strUserName) == true) {
                Toast.makeText(Forgetpageone.this, "输入成功！", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Forgetpageone.this,Forgetpagetwo.class);
                intent.putExtra("UserName",strUserName);
                startActivity(intent);
            }
            else {
                Toast.makeText(Forgetpageone.this,"该用户不存在，请输入正确帐号！",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(Forgetpageone.this,"帐号不能为空！",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpageone);
        //打开数据库
        OpenCreateDB();
        //点击下一步
        Button b=(Button) findViewById(R.id.button23);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               EditText ET = (EditText)findViewById(R.id.editText);
                function(ET.getText().toString());
            }
        });
    }
}
