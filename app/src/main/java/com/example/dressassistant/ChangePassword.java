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
import android.widget.Toast;

/**
 * Created by 洪祺瑜 on 2018-01-04.
 */


public class ChangePassword extends AppCompatActivity {

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

    String DBOld;
    //将密保问题取出
    private void QueryQues(String strUserName){

        Cursor cursor = db.rawQuery("select * from PersInfo where pers_UsID='" + strUserName + "'",null);
        if(cursor.moveToFirst()){  //循环遍历查找数组
            DBOld = cursor.getString(cursor.getColumnIndex("pers_Password"));
            // Toast.makeText(Forgetpagetwo.this,Q1,Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

    //修改密码时，判断输入的新密码，确认密码是否相同
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

    //    判断输入旧密码和数据库中的密码是否匹配
    private boolean isRightPwd(String strInputOld, String strDB){
        if(strInputOld.equals(strDB)){
            return true;
        }
        else
        {
            return false;
        }
    }

    private void updatePwd(String strOldPassword,String strNewPassword,String strReNewPassworord,String strUserName)
    {
        if(isStrEmpty(strOldPassword) == false){
            if(isStrEmpty(strNewPassword) == false){
                if(isStrEmpty(strReNewPassworord) == false){
                    if ((isRightPwd(strOldPassword,DBOld))){
                        if(isPwdSame(strNewPassword,strReNewPassworord)){
                            ContentValues cvUpdatePwd = new ContentValues();
                            cvUpdatePwd.put("pers_Password",strNewPassword);
                            if(db != null){
                                db.update("PersInfo",cvUpdatePwd,"pers_UsID = ?",new String[] { strUserName });
                                Toast.makeText(ChangePassword.this,"修改成功！",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ChangePassword.this,Myself.class);
                                intent.putExtra("Password",strNewPassword);
                                startActivity(intent);
                            }
                        }
                        else {
                            Toast.makeText(ChangePassword.this,"新密码和确认密码不一致！",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(ChangePassword.this,"旧密码错误！",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ChangePassword.this,"确认密码不能为空！",Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(ChangePassword.this,"新密码不能为空！",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(ChangePassword.this,"旧密码不能为空！",Toast.LENGTH_SHORT).show();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);

        OpenCreateDB();




        Button b = (Button)findViewById(R.id.b1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et1 = (EditText)findViewById(R.id.et1);
                EditText et2 = (EditText)findViewById(R.id.et2);
                EditText et3 = (EditText)findViewById(R.id.et3);
                String oldPassword = et1.getText().toString();
                String newPassword = et2.getText().toString();
                String reNewPassworord = et3.getText().toString();
                Intent in = getIntent();
                String UserName = in.getStringExtra("UserName");
                QueryQues(UserName);
                updatePwd(oldPassword,newPassword,reNewPassworord,UserName);
            }
        });

    }
}
