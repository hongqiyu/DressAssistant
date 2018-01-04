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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 洪祺瑜 on 2017-12-24.
 */


public class Information extends AppCompatActivity {

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

    private void insertHeWeInfo(String strHeight, String  strWeight){

        Intent intent = getIntent();
        String UserName = intent.getStringExtra("UserName");

        if(isStrEmpty(strHeight) == false){
            if(isStrEmpty(strWeight) == false){
                ContentValues cvHeWeInfo = new ContentValues();
                cvHeWeInfo.put("fide_FiID",UserName);
                cvHeWeInfo.put("fide_Weight",strWeight);
                cvHeWeInfo.put("fide_Height",strHeight);
                if(db != null){
                    db.insert("FIDe",null,cvHeWeInfo);
                    Toast.makeText(Information.this,"设置成功！", Toast.LENGTH_SHORT).show();
                    Intent in=new Intent(Information.this,Figure.class);
                    in.putExtra("UserName",UserName);
                    startActivity(in);
                }
            }
            else {
                Toast.makeText(Information.this,"体重不能为空！",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(Information.this, "身高不能为空！",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        //打开数据库
        OpenCreateDB();

        //点击下一步
        Button TVV=(Button) findViewById(R.id.b1);
        TVV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText EditText1 = (EditText)findViewById(R.id.et);
                EditText EditText2 = (EditText)findViewById(R.id.et1);
                insertHeWeInfo(EditText1.getText().toString(),EditText2.getText().toString());
            }
        });
    }
}
