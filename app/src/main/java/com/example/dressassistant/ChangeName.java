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
import android.widget.Toast;

/**
 * Created by 洪祺瑜 on 2018-01-04.
 */


public class ChangeName extends AppCompatActivity {
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
    private void updateNickName(String strNickNmae,String strUserName){
        if(isStrEmpty(strNickNmae) == false){
            if(db!=null){
                ContentValues cvNick = new ContentValues();
                cvNick.put("pers_UsNa",strNickNmae);
                db.update("PersInfo",cvNick,"pers_UsID = ?",new String[] { strUserName });
                Toast.makeText(ChangeName.this,"修改成功！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChangeName.this,Myself.class);
                intent.putExtra("NickName",strNickNmae);
                startActivity(intent);
            }
        }
        else {
            Toast.makeText(ChangeName.this,"新昵称不能为空！",Toast.LENGTH_SHORT).show();
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changename);
        OpenCreateDB();//打开数据库
        Button b1 = (Button)findViewById(R.id.b);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText)findViewById(R.id.et1);
                String NickName = et.getText().toString();
                Intent in = getIntent();
                String UserName = in.getStringExtra("UserName");
                updateNickName(NickName,UserName);
            }
        });
    }
}
