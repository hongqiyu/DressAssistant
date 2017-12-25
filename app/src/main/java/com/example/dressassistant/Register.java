package com.example.dressassistant;

import android.content.ContentValues;
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

import java.io.IOException;

/**
 * Created by 洪祺瑜 on 2017-12-13.
 */

public class Register extends AppCompatActivity {
    private static final String DB_NAME="ds.db";
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
            db.execSQL("CREATE TABLE IF NOT EXISTS tuserinfo(_id INTEGER PRIMARY KEY, pers_UsID VARCHAR, pers_UsNa VARCHAR, pers_Password VARCHAR, pers_Phone VARCHAR, pers_HePi VARCHAR, pers_Birthday VARCHAR, pers_FILID VARCHAR, pers_FLID VARCHAR, pers_FaID VARCHAR, pers_CUID VARCHAR, pers_PlID VARCHAR, pers_FeID VARCHAR, pers_GDUID VARCHAR, pers_MyMe VARCHAR, pers_CPLID VARCHAR, pers_FCID VARCHAR, pers_Q1 VARCHAR, pers_Q2 VARCHAR, pers_Q3 VARCHAR, pers_A1 VARCHAR, pers_A2 VARCHAR, pers_A3 VARCHAR)");
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

    //注册时，判断两次输入的密码是否相同
    private boolean isPwdSame(String strUserPwd, String strUserRePwd)
    {
        if(strUserPwd.equals(strUserRePwd)){
            return true;
        }
        else
        {
            return false;
        }
    }

    //判断数据库中是否存在该用户名
    private boolean isExistUserName(String strUserName)
    {
        Cursor cursor = db.rawQuery("select * from PersInfo where pers_UsID='" + strUserName +"'", null);
        if(cursor.getCount()>0){
            cursor.close();
            return true;
        }
        else{
            cursor.close();
            return false;
        }
    }

    //数据库插入用户
    private void insertUserInfo(String strUserName, String strUserNick ,String strUserPwd)
    {
        int iUserType = 0;
        if(isStrEmpty(strUserName) == false){
            if(isStrEmpty(strUserNick) == false){
                if(isStrEmpty(strUserPwd) == false){
                    if(isExistUserName(strUserName) == false){
                        ContentValues cvRUserInfo = new ContentValues();
                        cvRUserInfo.put("pers_UsID",strUserName);//用户名
                        cvRUserInfo.put("pers_UsNa", strUserNick);//昵称
                        cvRUserInfo.put("pers_Password", strUserPwd);
                        if(db != null)
                        {
                            db.insert("PersInfo", null, cvRUserInfo);
                            Toast.makeText(Register.this,"注册成功！", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Register.this,Requestion.class);
                            startActivity(intent);
                        }
                    }
                    else{
                        Toast.makeText(Register.this, "您要注册的用户名已经存在！", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Register.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(Register.this,"昵称不能为空！",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(Register.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //打开数据库
        OpenCreateDB();

        TextView TVV=(TextView)findViewById(R.id.tv1);
        TVV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });

//点击注册
        Button b1 = (Button) findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText EditText1 = (EditText) findViewById(R.id.editText1);   //读入xml用户名
                EditText EditText = (EditText) findViewById(R.id.editText);     //读入xml昵称
                EditText EditText2 = (EditText) findViewById(R.id.editText2);   //读入xml密码
                insertUserInfo(EditText1.getText().toString(),EditText.getText().toString(),EditText2.getText().toString());

            }
        });
    }
}
