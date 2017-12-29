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

public class Forgetpagetwo extends AppCompatActivity {

    private static final String DB_NAME="dressassistant.db";
    private SQLiteDatabase db;



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
    String Q1,Q2,Q3;
    //将密保问题取出
    private void QueryQues(String strUserName){

        Cursor cursor = db.rawQuery("select * from PersInfo where pers_UsID='" + strUserName + "'",null);
        if(cursor.moveToFirst()){  //循环遍历查找数组
             Q1 = cursor.getString(cursor.getColumnIndex("pers_Q1"));
             Q2 = cursor.getString(cursor.getColumnIndex("pers_Q2"));
             Q3 = cursor.getString(cursor.getColumnIndex("pers_Q3"));
           // Toast.makeText(Forgetpagetwo.this,Q1,Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

    //    判断问题和回答是否匹配
    private boolean isRightAnswer(String UserName, String strA1, String strA2, String strA3){
        Cursor cursor = db.rawQuery("select * from PersInfo where pers_UsID='" + UserName + "'and pers_A1='" + strA1 + "'and pers_A2='" + strA2 + "'and pers_A3='" + strA3 ,null);
        if(cursor.getCount() == 1){
            cursor.close();
            return true;
        }
        else {
            cursor.close();
            return false;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpagetwo);
        //打开数据库
        OpenCreateDB();
        final TextView TV1 = (TextView)findViewById(R.id.tv1);
        TextView TV2 = (TextView)findViewById(R.id.tv2);
        TextView TV3 = (TextView)findViewById(R.id.tv3);
        //从上一个界面传送UserName
        Intent intent = getIntent();
        final String UserName = intent.getStringExtra("UserName");
        QueryQues(UserName);//取出密保问题

        //密保问题显示在TextView上
        TV1.setText(Q1);
        TV2.setText(Q2);
        TV3.setText(Q3);
        //点击下一步
        Button b=(Button) findViewById(R.id.button24);
        Button.OnClickListener listener= new Button.OnClickListener(){
            public void onClick(View v) {

                EditText ET1 = (EditText)findViewById(R.id.et1);
                EditText ET2 = (EditText)findViewById(R.id.et2);
                EditText ET3 = (EditText)findViewById(R.id.et3);
                String strA1 = ET1.getText().toString();
                String strA2 = ET2.getText().toString();
                String strA3 = ET3.getText().toString();

                if(isStrEmpty(strA1) == false) {
                    if (isStrEmpty(strA2) == false){
                        if (isStrEmpty(strA3) == false){
                            if(isRightAnswer(UserName,strA1,strA2,strA3)){
                                Toast.makeText(Forgetpagetwo.this,"问题与答案匹配！",Toast.LENGTH_SHORT).show();
                                Intent in=new Intent(Forgetpagetwo.this,Forgetpagethree.class);
                                startActivity(in);
                            }
                            else {
                                Toast.makeText(Forgetpagetwo.this,"答案错误，请重新输入！",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(Forgetpagetwo.this,"答案三不能为空！",Toast.LENGTH_SHORT).show();
                            ET1.setFocusable(true);
                        }
                    }else {
                        Toast.makeText(Forgetpagetwo.this,"答案二不能为空！",Toast.LENGTH_SHORT).show();
                        ET2.setFocusable(true);
                    }
                }else {
                    Toast.makeText(Forgetpagetwo.this,"答案一不能为空！",Toast.LENGTH_SHORT).show();
                    ET1.setFocusable(true);
                }
            }
        };
        b.setOnClickListener(listener);
    }
}
