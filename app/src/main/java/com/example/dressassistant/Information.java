package com.example.dressassistant;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        Button TVV=(Button) findViewById(R.id.b1);
        TVV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(Information.this,Figure.class);
                startActivity(intent);
            }
        });
    }
}
