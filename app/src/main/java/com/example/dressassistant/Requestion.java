package com.example.dressassistant;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
    private static final String DB_NAME="dressassistant.db";
    private SQLiteDatabase db;
    private static final String[] m_Countries = { "你童年最好的朋友叫什么名字？", "你迄今为止最喜欢的老师姓什么？", "你小时候居住最久的那个地方叫什么名字？", "你最喜欢哪个季节？", "你最喜欢的歌手叫什么名字？" }; //定义数组
    private ArrayAdapter adapter; //存放数据
    private Spinner spinnerCardNumber; //下拉框
    private Spinner spinnerCardNumber2; //下拉框
    private Spinner spinnerCardNumber3; //下拉框

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

    //插入密保问题和密保答案
    private void insertQA(String strQ1, String strQ2, String strQ3, String strA1, String strA2, String strA3)
    {
        ContentValues cvQAInfo = new ContentValues();
        if(isStrEmpty(strA1) == false){
            if(isStrEmpty(strA2) == false){
                if(isStrEmpty(strA3) == false){
                    cvQAInfo.put("pers_Q1",strQ1);
                    cvQAInfo.put("pers_Q2",strQ2);
                    cvQAInfo.put("pers_Q3",strQ3);
                    cvQAInfo.put("pers_A1",strA1);
                    cvQAInfo.put("pers_A2",strA2);
                    cvQAInfo.put("pers_A3",strA3);
                    if(db != null){
                        db.insert("PersInfo", null, cvQAInfo);
                        Toast.makeText(Requestion.this,"设置成功！", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Requestion.this,Information.class);
                        startActivity(intent);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requestion);
        spinnerCardNumber = (Spinner)findViewById(R.id.q1);
        spinnerCardNumber2 = (Spinner)findViewById(R.id.q2);
        spinnerCardNumber3 = (Spinner)findViewById(R.id.q3);
        //将可选内容与ArrayAdapter连接，
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m_Countries);
        //将adapter添加到m_Spinner中
        spinnerCardNumber.setAdapter(adapter);
        spinnerCardNumber2.setAdapter(adapter);
        spinnerCardNumber3.setAdapter(adapter);
        //打开数据库
        OpenCreateDB();
        //点击
        Button b1 = (Button) findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText A1 = (EditText)findViewById(R.id.et1);
                EditText A2 = (EditText)findViewById(R.id.et2);
                EditText A3 = (EditText)findViewById(R.id.et3);
                Spinner Q1 = (Spinner)findViewById(R.id.q1);
                Spinner Q2 = (Spinner)findViewById(R.id.q2);
                Spinner Q3 = (Spinner)findViewById(R.id.q3);
                insertQA(Q1.getSelectedItem().toString(),Q2.getSelectedItem().toString(),
                        Q3.getSelectedItem().toString(), A1.getText().toString(),
                        A2.getText().toString(),A3.getText().toString());
            }
        });
    }
}