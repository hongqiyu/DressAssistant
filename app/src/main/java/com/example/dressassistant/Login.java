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

import java.io.IOException;

/**
 * Created by 洪祺瑜 on 2017-12-13.
 */

public class Login extends AppCompatActivity {
    private static final String DB_NAME="dbuser.db";
    private SQLiteDatabase db;
    //    判断用户名和密码是否存在
    private boolean isValidUser(String strUserName, String strUserPwd){
        Cursor cursor = db.rawQuery("select * from tuserinfo where username='"+strUserName+"'and userpwd='"+strUserPwd+"'",null);
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
            db.execSQL("CREATE TABLE IF NOT EXISTS tuserinfo(_id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR, userpwd VARCHAR, usertype INTEGER)");
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
    //创建DBHelper对象
    private  DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //创建数据库，从asserts将数据库导入工程中
        helper = new DBHelper(this);
        try{
            helper.createDataBase();
        }catch (IOException e){
            e.printStackTrace();
        }
        //打开数据库
        OpenCreateDB();
        TextView TVV=(TextView)findViewById(R.id.button20);
        TVV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });

        Button button21 = (Button) findViewById(R.id.button21);
        Button.OnClickListener listener = new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                if(v.getId() == R.id.button21){
                    EditText editText = (EditText) findViewById(R.id.editText);
                    EditText editText2 = (EditText) findViewById(R.id.editText2);
                    String strUserName = editText.getText().toString();
                    String strUserPwd = editText2.getText().toString();
                    if (isStrEmpty(strUserName) == false){
                        if(isStrEmpty(strUserPwd) == false){
                            if(isValidUser(strUserName,strUserPwd) == true){
                                Toast.makeText(Login.this,"用户登录成功！",Toast.LENGTH_SHORT).show();
                               //登陆成功时跳转到主页面，并传参数
                                String data = " Hello MainActivity";
                                Intent intent=new Intent(Login.this,MainActivity.class);
                                intent.putExtra("extra_data", data);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(Login.this,"用户登录失败！",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(Login.this,"密码不得为空！",Toast.LENGTH_SHORT).show();
                            editText2.setFocusable(true);
                        }
                    }
                    else{
                        Toast.makeText(Login.this,"用户名不得为空！",Toast.LENGTH_SHORT).show();
                        editText.setFocusable(true);
                    }
                }
            }
        };
        button21.setOnClickListener(listener);

    }
}
