package com.example.dressassistant;

/**
 * Created by 洪祺瑜 on 2017/12/2.
 */
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HairDetails extends AppCompatActivity {
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
    public void hairInfo(String id){
        Intent intent = new Intent(HairDetails.this,HairInformation.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    //对收藏夹操作
    public void Favorite(String id) {
        Cursor cur = null;
        cur = db.rawQuery("select * from FavoriteDetail where fade_PiID = '" + id + "' and fade_FaID = '" + UserName + "'", null);

        if (cur.getCount() > 0) {
            deleteFavorite(id);
            Toast.makeText(HairDetails.this, "移除收藏夹成功!", Toast.LENGTH_SHORT).show();
        } else if (cur.getCount() == 0) {
            addFavorite(id);
            Toast.makeText(HairDetails.this, "加入收藏夹成功!", Toast.LENGTH_SHORT).show();
        }
        cur.close();
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



    //明日计划
    public void tomorrowPlan(String id, String type) {
        Cursor cur = null;
        Cursor cur1 = null;
        //添加到历史计划
        cur1 = db.rawQuery("select * from UHPl where uhpl_UsID = '" + UserName + "' and htpl_Time = '"+ SystemTime + "'", null);
        if(cur1.getCount() == 0){
            ContentValues UHPl = new ContentValues();
            UHPl.put("uhpl_UsID", UserName);
            UHPl.put("uhpl_PlID", UserName+SystemTime);
            UHPl.put("htpl_Time", SystemTime);
            db.insert("UHPl", null, UHPl);
        }

        cur = db.rawQuery("select * from PlDe where plde_PlID = '" + UserName+SystemTime + "'", null);
        if (cur.getCount() == 0) {
            addTomoPlan(id, type);
            Toast.makeText(HairDetails.this, "计划添加成功", Toast.LENGTH_SHORT).show();
        }
        else
            updateTomoPlan(id, type);
        cur1.close();
        cur.close();
    }

    //新建明日计划
    public void addTomoPlan(String id, String type) {
        //插入type类型计划
        ContentValues PlDe = new ContentValues();
        PlDe.put("plde_PlID", UserName+SystemTime);
        PlDe.put("plde_Time", SystemTime);
        if (type == "suit")
            PlDe.put("plde_SuID", id);
        else if (type == "makeup")
            PlDe.put("plde_MaID", id);
        else if (type == "haircut")
            PlDe.put("plde_HaID", id);
        try {
            db.insert("PlDe", null, PlDe);
        }catch(Exception e){
            Toast.makeText(HairDetails.this, "新建明日计划出现异常!", Toast.LENGTH_SHORT).show();
        }
    }


    //修改已存在计划，加入其他类型图片，修改已存在类型图片
    public void updateTomoPlan(String id, String type) {
        //系统时间
        Cursor cur = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        String flag = "change";
        if (type == "suit") {
            cur = db.rawQuery("select * from PlDe where plde_PlID ='" + UserName+SystemTime + "' and plde_SuID != '" + null + "'", null);
            String sql = "update [PlDe] set plde_SuID = '" + id + "'where plde_PlID = '" + UserName+SystemTime + "'";
            //若已存在该图片，替换？
            if (cur.getCount() > 0) {
                cur = db.rawQuery("select * from PlDe where plde_PlID ='" + UserName+SystemTime + "' and plde_SuID = '" + id + "'", null);
                if(cur.getCount() > 0) {
                    sql = "update [PlDe] set plde_SuID ='" + null + "'where plde_PlID = '" + UserName+SystemTime + "'";
                    flag = "delete";
                }
                exTomoPlan(id, sql, flag);
            }
            //不存在该图片修改该用户计划，加入
            else {
                db.execSQL(sql);
//                sql = "update [PlDe] set plde_Time = '" + SystemTime + "' where plde_PlID = '" + UserName + "'";
//                db.execSQL(sql);
            }
        }
        else if (type == "makeup") {
            cur = db.rawQuery("select * from PlDe where plde_PlID ='" + UserName+SystemTime + "' and plde_MaID != '" + null + "'", null);
            String sql = "update [PlDe] set plde_MaID = '" + id + "'where plde_PlID = '" + UserName+SystemTime + "'";
            if (cur.getCount() > 0) {
                cur = db.rawQuery("select * from PlDe where plde_PlID ='" + UserName+SystemTime + "' and plde_MaID = '" + id + "'", null);
                if(cur.getCount() > 0) {
                    sql = "update [PlDe] set plde_MaID ='" + null + "' where plde_PlID = '" + UserName+SystemTime + "'";
                    flag = "delete";
                }
                exTomoPlan(id, sql, flag);
            }
            else {
                db.execSQL(sql);
            }
        }
        else if (type == "haircut") {
            cur = db.rawQuery("select * from PlDe where plde_PlID ='" + UserName+SystemTime + "' and plde_HaID != '" + null + "'", null);
            String sql = "update [PlDe] set plde_HaID = '" + id + "'where plde_PlID = '" + UserName+SystemTime + "'";
            if (cur.getCount() > 0) {
                cur = db.rawQuery("select * from PlDe where plde_PlID ='" + UserName+SystemTime + "' and plde_HaID = '" + id + "'", null);
                if(cur.getCount() > 0) {
                    sql = "update [PlDe] set plde_HaID ='" + null + "' where plde_PlID = '" + UserName+SystemTime + "'";
                    flag = "delete";
                }
                exTomoPlan(id, sql, flag);
            }
            else {
                db.execSQL(sql);
            }
        }
        cur.close();
    }

    //替换明日计划
    public void exTomoPlan(String id, final String sql, final String flag){
        //通过AlertDiaglog.Builder这个类来实例化我们的一个AlertDialog的对象
        AlertDialog.Builder builder = new AlertDialog.Builder(HairDetails.this);
        //设置Content来显示信息
        if(flag == "delete")
            builder.setMessage("已存在该计划，是否确定移除?");
        else
            builder.setMessage("已存在该类型计划，是否确定替换?");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.execSQL(sql);
                if(flag == "delete")
                    Toast.makeText(HairDetails.this, "移除成功！", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(HairDetails.this, "替换成功！", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }




    private String SystemTime;
    public void getSystemTime(){
        //系统时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        SystemTime = formatter.format(curDate);
    }
    private String UserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hairdetails);
        OpenCreateDB();
        Intent intent = getIntent();
        UserName = intent.getStringExtra("UserName");
        getSystemTime();




        ImageView.OnClickListener listener = new ImageView.OnClickListener(){
            public void onClick(View v){
                int id = v.getId();
                switch (id){
                    case R.id.iv1:
                        hairInfo("lhiarpgt");
                        break;
                    case R.id.iv2:
                        hairInfo("lbbt");
                        break;
                    case R.id.iv3:
                        hairInfo("ldbl");
                        break;
                    case R.id.iv4:
                        hairInfo("hfxbwz");
                        break;
                    case R.id.iv5:
                        hairInfo("lsmw");
                        break;
                    case R.id.iv6:
                        hairInfo("ldmw");
                        break;
                    case R.id.iv7:
                        hairInfo("lbfmw");
                        break;
                    case R.id.iv8:
                        hairInfo("lhswzt");
                        break;
                    case R.id.iv9:
                        hairInfo("lhbt");
                        break;
                    case R.id.iv10:
                        hairInfo("lwgb");
                        break;
                    case R.id.f1:
                        Favorite("hpgt");
                        break;
                    case R.id.f2:
                        Favorite("hbbt");
                        break;
                    case R.id.f3:
                        Favorite("hdbl");
                        break;
                    case R.id.f4:
                        Favorite("hbwz");
                        break;
                    case R.id.f5:
                        Favorite("hsmw");
                        break;
                    case R.id.f6:
                        Favorite("hdmw");
                        break;
                    case R.id.f7:
                        Favorite("hfs");
                        break;
                    case R.id.f8:
                        Favorite("hwz");
                        break;
                    case R.id.f9:
                        Favorite("hhb");
                        break;
                    case R.id.f10:
                        tomorrowPlan("hwg","haircut");
                        break;
                    case R.id.t1:
                        tomorrowPlan("hpgt","haircut");
                        break;
                    case R.id.t2:
                        tomorrowPlan("hbbt","haircut");
                        break;
                    case R.id.t3:
                        tomorrowPlan("hdbl","haircut");
                        break;
                    case R.id.t4:
                        tomorrowPlan("hbwz","haircut");
                        break;
                    case R.id.t5:
                        tomorrowPlan("hsmw","haircut");
                        break;
                    case R.id.t6:
                        tomorrowPlan("hdmw","haircut");
                        break;
                    case R.id.t7:
                        tomorrowPlan("hfs","haircut");
                        break;
                    case R.id.t8:
                        tomorrowPlan("hwz","haircut");
                        break;
                    case R.id.t9:
                        tomorrowPlan("hhb","haircut");
                        break;
                    case R.id.t10:
                        tomorrowPlan("hwg","haircut");
                        break;
                    default:
                        break;
                }
            }
        };
        ImageView iv1 = (ImageView)findViewById(R.id.iv1);
        iv1.setOnClickListener(listener);
        ImageView iv2 = (ImageView)findViewById(R.id.iv2);
        iv2.setOnClickListener(listener);
        ImageView iv3 = (ImageView)findViewById(R.id.iv3);
        iv3.setOnClickListener(listener);
        ImageView iv4 = (ImageView)findViewById(R.id.iv4);
        iv4.setOnClickListener(listener);
        ImageView iv5 = (ImageView)findViewById(R.id.iv5);
        iv5.setOnClickListener(listener);
        ImageView iv6 = (ImageView)findViewById(R.id.iv6);
        iv6.setOnClickListener(listener);
        ImageView iv7 = (ImageView)findViewById(R.id.iv7);
        iv7.setOnClickListener(listener);
        ImageView iv8 = (ImageView)findViewById(R.id.iv8);
        iv8.setOnClickListener(listener);
        ImageView iv9 = (ImageView)findViewById(R.id.iv9);
        iv9.setOnClickListener(listener);
        ImageView iv10 = (ImageView)findViewById(R.id.iv10);
        iv10.setOnClickListener(listener);

        ImageView f1 = (ImageView)findViewById(R.id.f1);
        f1.setOnClickListener(listener);
        ImageView f2 = (ImageView)findViewById(R.id.f2);
        f2.setOnClickListener(listener);
        ImageView f3 = (ImageView)findViewById(R.id.f3);
        f3.setOnClickListener(listener);
        ImageView f4 = (ImageView)findViewById(R.id.f4);
        f4.setOnClickListener(listener);
        ImageView f5 = (ImageView)findViewById(R.id.f5);
        f5.setOnClickListener(listener);
        ImageView f6 = (ImageView)findViewById(R.id.f6);
        f6.setOnClickListener(listener);
        ImageView f7 = (ImageView)findViewById(R.id.f7);
        f7.setOnClickListener(listener);
        ImageView f8 = (ImageView)findViewById(R.id.f8);
        f8.setOnClickListener(listener);
        ImageView f9 = (ImageView)findViewById(R.id.f9);
        f9.setOnClickListener(listener);
        ImageView f10 = (ImageView)findViewById(R.id.f10);
        f10.setOnClickListener(listener);

        ImageView t1 = (ImageView)findViewById(R.id.t1);
        t1.setOnClickListener(listener);
        ImageView t2 = (ImageView)findViewById(R.id.t2);
        t2.setOnClickListener(listener);
        ImageView t3 = (ImageView)findViewById(R.id.t3);
        t3.setOnClickListener(listener);
        ImageView t4 = (ImageView)findViewById(R.id.t4);
        t4.setOnClickListener(listener);
        ImageView t5 = (ImageView)findViewById(R.id.t5);
        t5.setOnClickListener(listener);
        ImageView t6 = (ImageView)findViewById(R.id.t6);
        t6.setOnClickListener(listener);
        ImageView t7 = (ImageView)findViewById(R.id.t7);
        t7.setOnClickListener(listener);
        ImageView t8 = (ImageView)findViewById(R.id.t8);
        t8.setOnClickListener(listener);
        ImageView t9 = (ImageView)findViewById(R.id.t9);
        t9.setOnClickListener(listener);
        ImageView t10 = (ImageView)findViewById(R.id.t10);
        t10.setOnClickListener(listener);

        ImageView bu=(ImageView) findViewById(R.id.iv1);
        bu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(HairDetails.this,HairInformation.class);
                startActivity(intent);
            }
        });
        Button b=(Button) findViewById(R.id.button5);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(HairDetails.this,DressStyle.class);
                startActivity(intent);
            }
        });
        Button but=(Button) findViewById(R.id.button6);
        but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(HairDetails.this,MakeupStyle.class);
                startActivity(intent);
            }
        });
        Button butto=(Button) findViewById(R.id.button7);
        butto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(HairDetails.this,MySpace.class);
                startActivity(intent);
            }
        });
        Button buttonn=(Button)findViewById(R.id.button);
        buttonn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(HairDetails.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}



