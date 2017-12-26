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
                    if(isQuesSame(strQ1,strQ2) == false){
                        if(isQuesSame(strQ1,strQ3) == false){
                            if(isQuesSame(strQ2,strQ3) == false){
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
                                Toast.makeText(Requestion.this,"问题三不能与问题二相同",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(Requestion.this,"问题三不能与问题一相同",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(Requestion.this,"问题二不能与问题一相同",Toast.LENGTH_SHORT).show();
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
    //判断复选框问题内容是否一致,若一致则true
    private boolean isQuesSame(String str1, String str2)
    {

        if(str1.equals(str2)){
            return true;
        }
        else
        {
            return false;
        }
    }
    Spinner Q1,Q2,Q3;
    String strQues1,strQues2,strQues3;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requestion);
        //打开数据库
        OpenCreateDB();
        final EditText A1 = (EditText)findViewById(R.id.et1);
        final EditText A2 = (EditText)findViewById(R.id.et2);
        final EditText A3 = (EditText)findViewById(R.id.et3);
        Q1 = (Spinner)findViewById(R.id.q1);
        Q2 = (Spinner)findViewById(R.id.q1);
        Q3 = (Spinner)findViewById(R.id.q1);
        strQues1 = (String)Q1.getSelectedItem();
        strQues2 = (String)Q2.getSelectedItem();
        strQues3 = (String)Q3.getSelectedItem();

        Q1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strQues1 = (String)Q1.getSelectedItem();
                Toast.makeText(Requestion.this, strQues1, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Q2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strQues2 = (String)Q2.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Q3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strQues3 = (String)Q3.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //点击确定
        Button b1 = (Button) findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                insertQA(strQues1,strQues2,strQues3, A1.getText().toString(),
                        A2.getText().toString(),A3.getText().toString());
            }
        });
    }
}