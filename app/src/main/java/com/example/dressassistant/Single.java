package com.example.dressassistant;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 洪祺瑜 on 2017-12-28.
 */

public class Single extends AppCompatActivity {
    private String UserName;
    private SQLiteDatabase db;
    private static final String DB_NAME = "dressassistant.db";
    private String SystemTime;
    private String flag;
    private String dresstype;
    private String sepfid;


    //从sqlite读取图片，并显示在桌面上
    public boolean getPhoto(String photoName, int id) {
        Cursor cur = db.rawQuery("select * from AllPicture where alpi_PiID='" + photoName + "'", null);
        cur.moveToFirst();
        byte[] in = cur.getBlob(cur.getColumnIndex("alpi_PiLo"));
        Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);

        BitmapDrawable bd = new BitmapDrawable(getResources(), bmpout);
        ImageView imageView = (ImageView) findViewById(id);
        imageView.setImageDrawable(bd);
        imageView.invalidate();
        bmpout = null;
        System.gc();
        cur.close();
        return false;
    }
    //打开数据库
    public void OpenCreateDB() {
        try {
            db = openOrCreateDatabase(DB_NAME, this.MODE_PRIVATE, null);
        } catch (Throwable e) {
            Log.e("tag", "open error:" + e.getMessage());
            db = null;
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS PersInfo(_id INTEGER PRIMARY KEY, pers_UsID VARCHAR, pers_UsNa VARCHAR, pers_Password VARCHAR, pers_Phone VARCHAR, pers_HePi VARCHAR, pers_Birthday VARCHAR, pers_FILID VARCHAR, pers_FLID VARCHAR, pers_FaID VARCHAR, pers_CUID VARCHAR, pers_PlID VARCHAR, pers_FeID VARCHAR, pers_GDUID VARCHAR, pers_MyMe VARCHAR, pers_CPLID VARCHAR, pers_FCID VARCHAR, pers_Q1 VARCHAR, pers_Q2 VARCHAR, pers_Q3 VARCHAR, pers_A1 VARCHAR, pers_A2 VARCHAR, pers_A3 VARCHAR)");
        } catch (SQLException se) {
            String msg = "doInstall.error:[%s].%s";
            Log.d("tag", String.format(msg, se.getClass(), se.getMessage()));
        }
    }
    public void getInfomation(){
        Intent intent = getIntent();
        UserName = intent.getStringExtra("UserName");
        flag = intent.getStringExtra("flag");
        dresstype = intent.getStringExtra("type");
        sepfid = intent.getStringExtra("id");
    }
    public void showSeparate(){
        switch (sepfid){
            case "sy":
                if(flag.equals("false")) {
                    showInsyF();
                }
                else{
                    showInsyT();
                }

                break;
            case "kz":
                break;
            case "qz":
                break;
            case "ps":
                break;
        }
    }

    String neck = "0", shoulder = "0", chest = "0",arm = "0",waist ="0",leWi = "0",leHe = "0",hip = "0";

    public void showInsyT() {
        int allcount  = 0;
        TextView text1=(TextView) findViewById(R.id.text1);
        text1.setText("上衣推荐");
        String[] s = {"mf", "dwt", "mndy","wy"};
        int[] id = {R.id.mf1, R.id.mf2, R.id.dwt1, R.id.dwt2, R.id.mndy1, R.id.mndy2, R.id.wy1, R.id.wy2};
        int i = 0;
        String[] sepa ={"prat_Shoulder","prat_Neck","prat_Chest","prat_Arm","prat_Waist","prat_LeWi","prat_LeHe","prat_Hip"};
        String[] figure = {"fide_Shoulder", "fide_Neck", "fide_Chest", "fide_Arm", "fide_Waist", "fide_LeWi", "fide_LeHe", "fide_Hip"};
        String maxName;
        Cursor cur = null;
        Cursor cur2 = null;
        Cursor cur3 = null;
        Cursor cur4 = null;
        temp = new String[8];
        String se = null;
        String fi = null;
        cur3 = db.rawQuery("select * from FIDe where fide_FiID ='" + UserName +"'", null);
        cur3.moveToNext();
        while (i < 4) {
            allcount  = 0;
            cur = db.rawQuery("select * from AllPicture where alpi_Flag ='Separate'" + "order by alpi_PoVa desc", null);
            while(true) {
                cur.moveToNext();
                maxName = cur.getString(cur.getColumnIndex("alpi_PiID"));
                cur2 = db.rawQuery("select * from Separate where sepa_SeID ='" + maxName + "'and sepa_Type = '" + s[i] + "'", null);
                cur4 = db.rawQuery("select * from PRAt where prat_PiID ='" + maxName +"'", null);
                cur4.moveToNext();
                if (cur2.getCount() > 0) {
                    temp[i * 2] = maxName;
                   for(int j = 0; j < 8; j++){
                       if((cur3.getString(cur3.getColumnIndex(figure[j]))).equals(cur4.getString(cur4.getColumnIndex(sepa[j])))){
                           allcount++;
                       }
                   }
                    if(allcount > 2)
                        getPhoto(maxName, id[(i + 1) * 2 - 2]);
                    break;
                }
            }
            allcount  = 0;
            while(true) {
                cur.moveToNext();
                maxName = cur.getString(cur.getColumnIndex("alpi_PiID"));
                cur2 = db.rawQuery("select * from Separate where sepa_SeID ='" + maxName + "'and sepa_Type = '" + s[i] + "'", null);
                cur4 = db.rawQuery("select * from PRAt where prat_PiID ='" + maxName +"'", null);
                cur4.moveToNext();
                if (cur2.getCount() > 0) {
                    temp[i * 2 + 1] = maxName;
                    for(int j = 0; j < 8; j++){
                        if((cur3.getString(cur3.getColumnIndex(figure[j]))).equals(cur4.getString(cur4.getColumnIndex(sepa[j])))){
                            allcount++;
                        }
                    }
                    if(allcount > 2)
                        getPhoto(maxName, id[(i + 1) * 2 - 1]);
                    break;
                }
            }
            i++;
        }
        cur.close();
        cur2.close();
    }
    private String[] temp;
    public void showInsyF() {
        TextView text1=(TextView) findViewById(R.id.text1);
        text1.setText("上衣推荐");
        String[] s = {"mf", "dwt", "mndy","wy"};
        int[] id = {R.id.mf1, R.id.mf2, R.id.dwt1, R.id.dwt2, R.id.mndy1, R.id.mndy2, R.id.wy1, R.id.wy2};
        int i = 0;
        int maxPoVa;
        String maxName;
        Cursor cur = null;
        Cursor cur2 = null;
        temp = new String[8];
        while (i < 4) {
            cur = db.rawQuery("select * from AllPicture where alpi_Flag ='Separate'" + "order by alpi_PoVa desc", null);
            while(true) {
                cur.moveToNext();
                maxName = cur.getString(cur.getColumnIndex("alpi_PiID"));
                cur2 = db.rawQuery("select * from Separate where sepa_SeID ='" + maxName + "'and sepa_Type = '" + s[i] + "'", null);
                if (cur2.getCount() > 0) {
                    temp[i * 2] = maxName;
                    getPhoto(maxName, id[(i + 1) * 2 - 2]);
                    break;
                }
            }
            while(true) {
                cur.moveToNext();
                maxName = cur.getString(cur.getColumnIndex("alpi_PiID"));
                cur2 = db.rawQuery("select * from Separate where sepa_SeID ='" + maxName + "'and sepa_Type = '" + s[i] + "'", null);
                if (cur2.getCount() > 0) {
                    temp[i * 2 + 1] = maxName;
                    getPhoto(maxName, id[(i + 1) * 2 - 1]);
                    break;
                }
            }
            i++;
        }
        cur.close();
        cur2.close();
    }


    //移除收藏夹
    public boolean deleteFavorite(String id) {
        //删除收藏夹的图片
        String sql = "delete from FavoriteDetail where fade_PiID = '" + id + "' and fade_FaID = '" + UserName + "'";
        db.execSQL(sql);
        //减少图片汇总表的人气值
        Cursor cur = null;
        cur = db.rawQuery("select * from AllPicture where alpi_PiID = '" + id + "'", null);
        cur.moveToFirst();
        int PoVa = cur.getInt(cur.getColumnIndex("alpi_PoVa")) - 1;
        sql = "update [AllPicture] set alpi_PoVa = '" + PoVa + "'where alpi_PiID = '" + id + "'";
        try {
            db.execSQL(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    //对收藏夹操作
    public void Favorite(String id) {
        Cursor cur = null;
        cur = db.rawQuery("select * from FavoriteDetail where fade_PiID = '" + id + "' and fade_FaID = '" + UserName + "'", null);

        if (cur.getCount() > 0) {
            deleteFavorite(id);
            Toast.makeText(Single.this, "移除收藏夹成功!", Toast.LENGTH_SHORT).show();
        } else if (cur.getCount() == 0) {
            addFavorite(id);
            Toast.makeText(Single.this, "加入收藏夹成功!", Toast.LENGTH_SHORT).show();
        }
        cur.close();
    }
    //加入收藏夹
    public boolean addFavorite(String id) {

        //插入收藏夹表
        ContentValues FavoriteDetail = new ContentValues();
        FavoriteDetail.put("fade_FaID", UserName);
        FavoriteDetail.put("fade_PiID", id);
        FavoriteDetail.put("fade_Time", SystemTime);
        db.insert("FavoriteDetail", null, FavoriteDetail);
        //增加图片汇总表的人气值
        Cursor cur = null;
        cur = db.rawQuery("select * from AllPicture where alpi_PiID = '" + id + "'", null);
        cur.moveToFirst();
        int PoVa = cur.getInt(cur.getColumnIndex("alpi_PoVa")) + 1;
        String sql = "update [AllPicture] set alpi_PoVa = '" + PoVa + "'where alpi_PiID = '" + id + "'";
        try {
            db.execSQL(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single);
        OpenCreateDB();
        getInfomation();
        showSeparate();

        ImageView.OnClickListener listener = new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserName == null){
                    Toast.makeText(Single.this, "未登陆!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int id = v.getId();
                switch (id) {
                    case R.id.add1:
                        Favorite(temp[0]);
                        break;
                    case R.id.add2:
                        Favorite(temp[1]);
                        break;
                    case R.id.add3:
                        Favorite(temp[2]);
                        break;
                    case R.id.add4:
                        Favorite(temp[3]);
                        break;
                    case R.id.add5:
                        Favorite(temp[4]);
                        break;
                    case R.id.add6:
                        Favorite(temp[5]);
                        break;
                    case R.id.add7:
                        Favorite(temp[5]);
                        break;
                    case R.id.add8:
                        Favorite(temp[5]);
                        break;
                    default:
                        break;
                }
            }
        };
        ImageView add1 =(ImageView) findViewById(R.id.add1);
        add1.setOnClickListener(listener);
        ImageView add2 =(ImageView) findViewById(R.id.add2);
        add2.setOnClickListener(listener);
        ImageView add3 =(ImageView) findViewById(R.id.add3);
        add3.setOnClickListener(listener);
        ImageView add4 =(ImageView) findViewById(R.id.add4);
        add4.setOnClickListener(listener);
        ImageView add5 =(ImageView) findViewById(R.id.add5);
        add5.setOnClickListener(listener);
        ImageView add6 =(ImageView) findViewById(R.id.add6);
        add6.setOnClickListener(listener);
        ImageView add7 =(ImageView) findViewById(R.id.add7);
        add7.setOnClickListener(listener);
        ImageView add8 =(ImageView) findViewById(R.id.add8);
        add8.setOnClickListener(listener);




        ImageView pictureA = (ImageView) findViewById(R.id.mf1);
        pictureA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Single.this, SingleDetails.class);
                startActivity(intent);
            }
        });
    }
}
