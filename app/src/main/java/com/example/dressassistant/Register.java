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
    private static final String DB_NAME="dbuser.db";
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
            db.execSQL("CREATE TABLE IF NOT EXISTS tuserinfo(_id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR, userpwd VARCHAR, usertype INTEGER)");
        }
        catch(SQLException se)
        {
            String msg = "doInstall.error:[%s].%s";
            Log.d("tag",String.format(msg,se.getClass(), se.getMessage()));
        }
    }
//检查管理员是否存在，没用到
    private boolean isExistAdmin()
    {
        Cursor cursor = db.rawQuery("select * from tuserinfo where usertype=1", null);
        if(cursor.getCount()>0)
        {
            cursor.close();
            return true;
        }
        else
        {
            cursor.close();
            return false;
        }
    }
    //数据库插入管理员，没用到
    private void insertAdminInfo(){
        String strUserName = "czl";
        String strUserPwd="czl";
        int iUserType=1;
        TextView tv1 = (TextView) findViewById(R.id.tv1);
        if(isExistAdmin() == false){
            ContentValues cvUserInfo = new ContentValues();
            cvUserInfo.put("username",strUserName);
            cvUserInfo.put("userpwd", strUserPwd);
            cvUserInfo.put("usertype",iUserType);
            if(db != null)
            {
                db.insert("tuserinfo", null, cvUserInfo);
                Log.d("msg","插入结束");
                Toast.makeText(Register.this,"注册成功!", Toast.LENGTH_LONG).show();
                tv1.setText("用户名：" + strUserName + "\n" + "密码：" + strUserPwd + "\n" + "类别：" + iUserType);

            }
        }
        else
        {
            tv1.setText("已存在系统管理员\n 用户名：" + strUserName + "\n" + "密码：" + strUserPwd + "\n");
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
        Cursor cursor = db.rawQuery("select * from tuserinfo where username='" + strUserName +"'", null);
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
    private void insertUserInfo(String strUserName, String strUserPwd)
    {
        int iUserType = 0;
        if(isExistUserName(strUserName) == false){
            ContentValues cvRUserInfo = new ContentValues();
            cvRUserInfo.put("username",strUserName);
            cvRUserInfo.put("userpwd", strUserPwd);
            cvRUserInfo.put("usertype", iUserType);
            if(db != null)
            {
                db.insert("tuserinfo", null, cvRUserInfo);
                Toast.makeText(Register.this,"注册成功！", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(Register.this, "您要注册的用户名已经存在！", Toast.LENGTH_SHORT).show();
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
                EditText EditText2 = (EditText) findViewById(R.id.editText2);   //读入xml密码
                insertUserInfo(EditText1.getText().toString(),EditText2.getText().toString());
                Intent intent=new Intent(Register.this,Requestion.class);
                startActivity(intent);
            }
        });
    }
}
