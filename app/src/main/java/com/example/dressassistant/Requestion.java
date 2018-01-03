package com.example.dressassistant;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by 洪祺瑜 on 2017-12-23.
 */

public class Requestion extends AppCompatActivity {

    private final String TAG = "Requestion";//标准的调试方法，定义要过滤的对象

    private static final String DB_NAME="dressassistant.db";
    private SQLiteDatabase db;
    private static final String[] questions = { "你童年最好的朋友叫什么名字？", "你迄今为止最喜欢的老师姓什么？", "你小时候居住最久的那个地方叫什么名字？", "你最喜欢哪个季节？", "你最喜欢的歌手叫什么名字？" }; //定义数组
    private ArrayAdapter adapter; //存放数据
    private Spinner spinnerCardNumber1; //下拉框
    private Spinner spinnerCardNumber2; //下拉框
    private Spinner spinnerCardNumber3; //下拉框


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
    //判断问题是否相同,相同返回true
    private boolean isQuesSame(String ques1, String ques2)
    {
        if(ques1.equals(ques2)){
            return true;
        }
        else
        {
            return false;
        }
    }

    //插入密保问题和密保答案
    private void insertQA(String strQ1, String strQ2, String strQ3, String strA1, String strA2, String strA3)
    {

        Intent intent = getIntent();
        String UserName = intent.getStringExtra("UserName");
        String UserNick = intent.getStringExtra("UserNick");
        String UserPwd = intent.getStringExtra("UserPwd");

        ContentValues cvQAInfo = new ContentValues();
        if(isStrEmpty(strA1) == false){
            if(isStrEmpty(strA2) == false){
                if(isStrEmpty(strA3) == false){
                    if(isQuesSame(strQ1,strQ2) == false){
                        if(isQuesSame(strQ1,strQ3) == false){
                            if(isQuesSame(strQ2,strQ3) == false){
                                cvQAInfo.put("pers_Q1",strQ1);
                                cvQAInfo.put("pers_Q2",strQ2);
                                cvQAInfo.put("pers_Q3",strQ3);
                                cvQAInfo.put("pers_A1",strA1);
                                cvQAInfo.put("pers_A2",strA2);
                                cvQAInfo.put("pers_A3",strA3);
                                cvQAInfo.put("pers_UsID",UserName);
                                cvQAInfo.put("pers_UsNa",UserNick);
                                cvQAInfo.put("pers_Password",UserPwd);
                                if(db != null){
                                    db.insert("PersInfo", null, cvQAInfo);
                                    Toast.makeText(Requestion.this,"注册成功！", Toast.LENGTH_SHORT).show();
                                    Intent in=new Intent(Requestion.this,Information.class);
                                    /*in.putExtra("Q1",strQ1);
                                    in.putExtra("Q2",strQ2);
                                    in.putExtra("Q3",strQ3);
                                    in.putExtra("A1",strA1);
                                    in.putExtra("A2",strA2);
                                    in.putExtra("A3",strA3);
                                    in.putExtra("UserName",UserName);
                                    in.putExtra("UserNick",UserNick);
                                    in.putExtra("UserPwd",UserPwd);*/
                                    startActivity(in);
                                }
                            }
                            else {
                                Toast.makeText(Requestion.this,"问题二不能和问题三相同！",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(Requestion.this,"问题一不能和问题三相同！",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(Requestion.this,"问题一不能和问题二相同！",Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(Requestion.this,"答案三不能为空",Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(Requestion.this,"答案二不能为空",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(Requestion.this,"答案一不能为空",Toast.LENGTH_SHORT).show();
        }
    }
    String Q1,Q2,Q3;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requestion);
        //打开数据库
        OpenCreateDB();
        spinnerCardNumber1 = (Spinner)findViewById(R.id.q1);
        spinnerCardNumber2 = (Spinner)findViewById(R.id.q2);
        spinnerCardNumber3 = (Spinner)findViewById(R.id.q3);
        //将可选内容与ArrayAdapter连接，
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, questions);
        //将adapter添加到m_Spinner中
        spinnerCardNumber1.setAdapter(adapter);
        spinnerCardNumber2.setAdapter(adapter);
        spinnerCardNumber3.setAdapter(adapter);

        spinnerCardNumber1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                 Q1 = questions[arg2];
                arg0.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerCardNumber2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Q2 = questions[arg2];
                arg0.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCardNumber3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Q3 = questions[arg2];
                arg0.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //点击
        Button b1 = (Button) findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText A1 = (EditText)findViewById(R.id.et1);
                EditText A2 = (EditText)findViewById(R.id.et2);
                EditText A3 = (EditText)findViewById(R.id.et3);
                insertQA(Q1, Q2, Q3, A1.getText().toString(),
                        A2.getText().toString(),A3.getText().toString());
            }
        });
    }
}