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
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by 洪祺瑜 on 2017-12-24.
 */

public class Figure extends AppCompatActivity {

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
            db.execSQL("CREATE TABLE IF NOT EXISTS FIDe(_id INTEGER PRIMARY KEY, fide_FiID VARCHAR, fide_FiNa VARCHAR, fide_Feature VARCHAR, fide_FiCo VARCHAR, fide_Shoulder VARCHAR, fide_Neck VARCHAR, fide_Chest VARCHAR, fide_Arm VARCHAR, fide_Waist VARCHAR, fide_LeWi VARCHAR, fide_LeHe VARCHAR, fide_Hip VARCHAR, fide_Weight VARCHAR, fide_Height VARCHAR, fide_HeFl VARCHAR, fide_WeFl VARCHAR)");
        }
        catch(SQLException se)
        {
            String msg = "doInstall.error:[%s].%s";
            Log.d("tag",String.format(msg,se.getClass(), se.getMessage()));
        }
    }
    String  sshoulder,sneck,schest,sarm,swaist,sleWi,sleHe,ship;
    private void FIDeInfo(String strShoulder,String strNeck,String strChest,String strArm,String strWaist,String strLeWi,String strLeHe,String strHip){

        sshoulder = strShoulder;
        sneck = strNeck;
        schest = strChest;
        sarm = strArm;
        swaist = strWaist;
        sleWi = strLeWi;
        sleHe = strLeHe;
        ship = strHip;
    }

    private void insertFIDeInfo(String strShoulder,String strNeck,String strChest,String strArm,String strWaist,String strLeWi,String strLeHe,String strHip){
        String UserName;
        //接受上个界面的UserName
        Intent intent = getIntent();
        UserName = intent.getStringExtra("UserName");

        ContentValues cvFIDeInfo = new ContentValues();
        cvFIDeInfo.put("fide_Shoulder",strShoulder);
        cvFIDeInfo.put("fide_Neck",strNeck);
        cvFIDeInfo.put("fide_Chest",strChest);
        cvFIDeInfo.put("fide_Arm",strArm);
        cvFIDeInfo.put("fide_Waist",strWaist);
        cvFIDeInfo.put("fide_LeWi",strLeWi);
        cvFIDeInfo.put("fide_LeHe",strLeHe);
        cvFIDeInfo.put("fide_Hip",strHip);
        if(db != null) {
            db.insert("FIDe", null, cvFIDeInfo);
            Toast.makeText(Figure.this, "设置成功！", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(Figure.this,Question.class);
            in.putExtra("UserName",UserName);
            startActivity(in);
        }
    }
    //判断是否有图片选中图片
    private boolean isIVSelected(ImageView iv1,ImageView iv2,ImageView iv3,ImageView iv4,ImageView iv5){
        if(iv1.isSelected()||iv2.isSelected()||iv3.isSelected()||iv4.isSelected()||iv5.isSelected()){

            return true;
        }

        else
            return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.figure);

        //打开数据库
        OpenCreateDB();

        //点击第一张身材图，倒三角
        final ImageView iv1 = (ImageView)findViewById(R.id.IV1);


        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Figure.this,"选",Toast.LENGTH_SHORT).show();
                String  shoulder,neck,chest,arm,waist,leWi,leHe,hip;
                shoulder = "1";//肩宽
                neck = "0";
                chest = "0";
                arm = "0";
                waist = "0";
                leWi = "0";
                leHe = "0";
                hip = "-1";//窄臀
                iv1.setSelected(true);
                FIDeInfo(shoulder,neck,chest,arm,waist,leWi,leHe,hip);
            }
        });
        //点击第二张身材图，梨形
        final ImageView iv2 = (ImageView)findViewById(R.id.IV2);

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Figure.this,"2",Toast.LENGTH_SHORT).show();
                String  shoulder,neck,chest,arm,waist,leWi,leHe,hip;
                shoulder = "0";
                neck = "0";
                chest = "0";
                arm = "0";
                waist = "0";
                leWi = "1";//腿粗
                leHe = "0";
                hip = "1";//宽臀
                iv2.setSelected(true);
                FIDeInfo(shoulder,neck,chest,arm,waist,leWi,leHe,hip);

            }
        });
        //点击第三张身材图，苹果形
        final ImageView iv3 = (ImageView)findViewById(R.id.IV3);

        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Figure.this,"3",Toast.LENGTH_SHORT).show();
                String  shoulder,neck,chest,arm,waist,leWi,leHe,hip;
                shoulder = "0";
                neck = "0";
                chest = "0";
                arm = "0";
                waist = "1";//腰粗
                leWi = "0";
                leHe = "0";
                hip = "1";//宽臀
                iv3.setSelected(true);
                FIDeInfo(shoulder,neck,chest,arm,waist,leWi,leHe,hip);
            }
        });

        //点击第四张身材图，沙漏型
        final ImageView iv4 = (ImageView)findViewById(R.id.IV4);

        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Figure.this,"4",Toast.LENGTH_SHORT).show();
                String  shoulder,neck,chest,arm,waist,leWi,leHe,hip;
                shoulder = "0";
                neck = "0";
                chest = "0";
                arm = "0";
                waist = "-1";//细腰
                leWi = "0";
                leHe = "0";
                hip = "0";
                iv4.setSelected(true);
                FIDeInfo(shoulder,neck,chest,arm,waist,leWi,leHe,hip);
            }
        });

        //点击第五张身材图，直筒型
        final ImageView iv5 = (ImageView)findViewById(R.id.IV5);

        iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Figure.this,"5",Toast.LENGTH_SHORT).show();
                String  shoulder,neck,chest,arm,waist,leWi,leHe,hip;
                shoulder = "0";
                neck = "0";
                chest = "0";
                arm = "0";
                waist = "0";//细腰
                leWi = "0";
                leHe = "0";
                hip = "0";
                iv5.setSelected(true);
                FIDeInfo(shoulder,neck,chest,arm,waist,leWi,leHe,hip);
            }
        });

        final Button TVV=(Button) findViewById(R.id.b1);
        TVV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isIVSelected(iv1,iv2,iv3,iv4,iv5)){
                    insertFIDeInfo(sshoulder,sneck,schest,sarm,swaist,sleWi,sleHe,ship);
                }
                else {
                    Toast.makeText(Figure.this,"请选择一个身形",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
