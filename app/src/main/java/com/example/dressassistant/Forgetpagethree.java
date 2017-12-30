package com.example.dressassistant;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 洪祺瑜 on 2017-12-24.
 */

public class Forgetpagethree extends AppCompatActivity {

    private static final String DB_NAME="dressassistant.db";
    private SQLiteDatabase db;
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
    private void updateUserPwd(String UserPwd, String reUserPwd, String UserName){
        if(isStrEmpty(UserPwd) == false){
            if(isStrEmpty(reUserPwd) == false){
                if(isPwdSame(UserPwd,reUserPwd)){
                    ContentValues cvUpdatePwd = new ContentValues();
                    cvUpdatePwd.put("pers_PassWord",UserPwd);
                    db.update("PersInfo",cvUpdatePwd,"pers_UsID=?",new String[]{UserName});
                    Intent intent=new Intent(Forgetpagethree.this,Login.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Forgetpagethree.this,"两次输入的密码不匹配！",Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(Forgetpagethree.this,"确认密码不能为空！",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(Forgetpagethree.this,"新密码不能为空！",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpagethree);
        Intent in = getIntent();
        final String UserName = in.getStringExtra("UserName");
        //点击
        Button b=(Button) findViewById(R.id.button25);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText ET1 = (EditText)findViewById(R.id.et1);
                EditText ET2 = (EditText)findViewById(R.id.et2);
                String pwd = ET1.getText().toString();
                String rePwd = ET2.getText().toString();
                updateUserPwd(pwd,rePwd,UserName);
            }
        });
    }
}
