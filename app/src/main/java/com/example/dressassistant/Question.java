package com.example.dressassistant;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by 洪祺瑜 on 2017-12-24.
 */


public class Question extends AppCompatActivity {
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
            db.execSQL("CREATE TABLE IF NOT EXISTS FQCD(_id INTEGER PRIMARY KEY, fqcd_UsID VARCHAR, fqcd_FiID VARCHAR, fqcd_Shoulder VARCHAR, fqcd_Neck VARCHAR, fqcd_Chest VARCHAR, fqcd_Arm VARCHAR, fqcd_Waist VARCHAR, fqcd_LeWi VARCHAR, fqcd_LeHe VARCHAR, fqcd_Hip VARCHAR)");
        }
        catch(SQLException se)
        {
            String msg = "doInstall.error:[%s].%s";
            Log.d("tag",String.format(msg,se.getClass(), se.getMessage()));
        }
    }
    private void insertFQCDInfo(String strNeck,String strShoulder,String strChest,String strArm,String strWaist,String strLeWi,String strLeHe,String strHip){
        String UserName;
        Intent intent = getIntent();
        UserName = intent.getStringExtra("UserName");
        ContentValues cvInfo = new ContentValues();
        cvInfo.put("fqcd_UsID",UserName);
        cvInfo.put("fqcd_FiID",UserName);
        cvInfo.put("fqcd_Neck",strNeck);
        cvInfo.put("fqcd_Shoulder",strShoulder);
        cvInfo.put("fqcd_Chest",strChest);
        cvInfo.put("fqcd_Arm",strArm);
        cvInfo.put("fqcd_Waist",strWaist);
        cvInfo.put("fqcd_LeWi",strLeWi);
        cvInfo.put("fqcd_LeHe",strLeHe);
        cvInfo.put("fqcd_Hip",strHip);
        if(db != null){
            db.insert("FQCD",null,cvInfo);
            Toast.makeText(Question.this,"选择成功！", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(Question.this,Login.class);
            in.putExtra("UserName",UserName);
            startActivity(in);
        }


    }
    String neck = "0", shoulder = "0", chest = "0",arm = "0",waist ="0",leWi = "0",leHe = "0",hip = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);

        OpenCreateDB();
        CheckBox RB1 = (CheckBox) findViewById(R.id.rb1);

        RB1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    neck = "1";
                }
                else {
                    neck = "0";
                }
            }
        });
        CheckBox RB2 = (CheckBox)findViewById(R.id.rb2);
        RB2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    shoulder = "1";
                }
                else {
                    shoulder = "0";
                }
            }
        });
        CheckBox RB3 = (CheckBox)findViewById(R.id.rb3);
        RB3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    chest = "1";
                }
                else {
                    chest = "0";
                }
            }
        });
        CheckBox RB4 = (CheckBox)findViewById(R.id.rb4);
        RB4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    chest = "-1";
                }
                else {
                    chest = "0";
                }
            }
        });
        CheckBox RB5 = (CheckBox)findViewById(R.id.rb5);
        RB5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    arm = "1";
                }
                else {
                    arm = "0";
                }
            }
        });
        CheckBox RB6 = (CheckBox)findViewById(R.id.rb6);
        RB6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    waist = "1";
                }
                else {
                    waist = "0";
                }
            }
        });
        CheckBox RB7 = (CheckBox)findViewById(R.id.rb7);
        RB7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    leWi = "1";
                }
                else {
                    leHe = "0";
                }
            }
        });
        CheckBox RB8 = (CheckBox)findViewById(R.id.rb8);
        RB8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    leHe = "-1";
                }
                else {
                    leHe = "0";
                }
            }
        });
        CheckBox RB9 = (CheckBox)findViewById(R.id.rb9);
        RB9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hip = "1";
                }
                else {
                    hip = "0";
                }
            }
        });
        Button TVV=(Button) findViewById(R.id.b1);
        TVV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                insertFQCDInfo(neck,shoulder,chest,arm,waist,leWi,leHe,hip);
            }
        });
    }
}