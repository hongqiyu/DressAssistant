package com.example.dressassistant;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import android.widget.Toast;

import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public class MainActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    //记录上一次点的位置  
    private int oldPosition = 0;
    //存放图片的id  
    private int[] imageIds = new int[]{
            R.drawable.zrzrone,
            R.drawable.ct,
            R.drawable.zrfxtwo,
            R.drawable.zrfxone,
            R.drawable.zrfxthree
    };
    //存放图片的标题  
    private String[] titles = new String[]{
            "最美的妆容，配最美的你",
            "冬天，仙女们都这么穿",
            "好看的发型，就要这么扎",
            "做最美的自己",
            "据说，好看的人都扎了这种发型"
    };
    private TextView title;
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;
    private static final String DB_NAME = "dressassistant.db";
    private SQLiteDatabase db;

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

    //插入图片汇总表、套服、单品、图片属性推荐表（图片类型、资源id、图片名、人气值）
    public boolean insertSuOrSe(String type, int id, String photoId, int PoVa) {
        Cursor cursor = db.rawQuery("select * from AllPicture where alpi_PiID='" + photoId + "'", null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return false;
        }
        Resources res = getResources(); //打开drawable文件夹的资源
        Bitmap bmp = BitmapFactory.decodeResource(res, id);  // 此处id  例如为 R.drawable.co
        int size = bmp.getWidth() * bmp.getHeight() * 4;
        ByteArrayOutputStream os = new ByteArrayOutputStream(size); //设置读取大小
        bmp.compress(Bitmap.CompressFormat.PNG, 100, os); //设置压缩格式，存入流
        //插入图片推荐属性表
        ContentValues PRAt = new ContentValues();
        PRAt.put("prat_PiID", photoId);
        db.insert("PRAt", null, PRAt);

        if (type == "suit") {
            //插入图片汇总表
            ContentValues AllPicture = new ContentValues();
            AllPicture.put("alpi_PiID", photoId);
            AllPicture.put("alpi_Flag", "suit");
            AllPicture.put("alpi_PiLo", os.toByteArray());
            AllPicture.put("alpi_PoVa", PoVa);
            db.insert("AllPicture", null, AllPicture);
            //插入套服表
            ContentValues Suit = new ContentValues();
            Suit.put("suit_SuID", photoId);
            db.insert("Suit", null, Suit);
            return true;
        } else if (type == "Separate") {
            //插入图片汇总表
            ContentValues AllPicture = new ContentValues();
            AllPicture.put("alpi_PiID", photoId);
            AllPicture.put("alpi_Flag", "Separate");
            AllPicture.put("alpi_PiLo", os.toByteArray());
            AllPicture.put("alpi_PoVa", PoVa);
            db.insert("AllPicture", null, AllPicture);
            //插入单品表
            ContentValues Separate = new ContentValues();
            Separate.put("sepa_SuID", photoId);
            db.insert("Separate", null, Separate);
            //插入图片推荐属性表
            return true;
        }
        return false;
    }


    //插入发型表、妆容表、图片汇总表(图片ID，视频地址，有无视频标志)
    public boolean insertHaOrMa(String type, int id, String photoId, String videoName, String Flag, int PoVa) {
//        Cursor cursor = db.rawQuery("select * from AllPicture where alpi_PiID='" + photoId +"'", null);
//        if(cursor.getCount()>0) {
//            cursor.close();
//            return true;
//        }
        Resources res = getResources(); //打开drawable文件夹的资源
        Bitmap bmp = BitmapFactory.decodeResource(res, id);  // 此处id  例如为 R.drawable.co
        int size = bmp.getWidth() * bmp.getHeight() * 4;
        ByteArrayOutputStream os = new ByteArrayOutputStream(size); //设置读取大小
        bmp.compress(Bitmap.CompressFormat.PNG, 100, os); //设置压缩格式，存入流

        ContentValues values = new ContentValues();
        if (type == "haircut") {
            Cursor cursor = db.rawQuery("select * from AllPicture where alpi_PiID='" + photoId + "'", null);
            if (cursor.getCount() > 0) {
                cursor.close();
                return false;
            } else {
                //插入图片汇总表
                ContentValues AllPicture = new ContentValues();
                AllPicture.put("alpi_PiID", photoId);
                AllPicture.put("alpi_PiLo", os.toByteArray());
                AllPicture.put("alpi_Flag", "haircut");
                AllPicture.put("alpi_PoVa", PoVa);
                db.insert("AllPicture", null, AllPicture);
            }
            //插入发型表
            ContentValues Haircut = new ContentValues();
            Haircut.put("hair_haID", photoId);
            if (Flag == "yes") {
                String local = "res/raw" + videoName;
                Haircut.put("hair_VeTu", local);
                Haircut.put("hair_Flag", "yes");
            } else
                Haircut.put("hair_Flag", "no");
            db.insert("Haircut", null, Haircut);
            return true;
        } else if (type == "makeup") {


            Cursor cursor = db.rawQuery("select * from AllPicture where alpi_PiID='" + photoId + "'", null);
            if (cursor.getCount() > 0) {
                cursor.close();
                return false;
            } else {
                //插入图片汇总表
                ContentValues AllPicture = new ContentValues();
                AllPicture.put("alpi_PiID", photoId);
                AllPicture.put("alpi_PiLo", os.toByteArray());
                AllPicture.put("alpi_Flag", "makeup");
                AllPicture.put("alpi_PoVa", PoVa);
                db.insert("AllPicture", null, AllPicture);
            }
            //插入妆容表
            ContentValues Makeup = new ContentValues();
            Makeup.put("make_MaID", photoId);

            if (Flag == "yes") {
                String local = "res/raw" + videoName;
                Makeup.put("make_VeTu", local);
                Makeup.put("make_Flag", "yes");
            } else
                Makeup.put("make_Flag", "no");
            db.insert("Makeup", null, Makeup);
            return true;
        }
        return false;
    }

    //从sqlite读取图片，并显示在桌面上
    public boolean getPhoto(String photoName, int id) {
        Cursor cur = db.rawQuery("select * from AllPicture where alpi_PiID='" + photoName + "'", null);
        cur.moveToFirst();
        byte[] in = cur.getBlob(cur.getColumnIndex("alpi_PiLo"));
        Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);

        BitmapDrawable bd = new BitmapDrawable(getResources(), bmpout);
        ImageView imageView = (ImageView) findViewById(id);
        imageView.setImageDrawable(bd);
        return false;
    }

    public void showInMain() {
        String[] s = {"suit", "makeup", "haircut"};
        int[] id = {R.id.imageView8, R.id.imageView9, R.id.imageView10, R.id.imageView3, R.id.imageView11, R.id.imageView4};
        int i = 0;
        int maxPoVa;
        String maxName;
        Cursor cur = null;
        Cursor cur2 = null;
        Cursor cur3 = null;
        temp = new String[6];
        while (i < 3) {
            cur = db.rawQuery("select * from AllPicture where alpi_Flag = '" + s[i] + "'" + "order by alpi_PoVa desc", null);
            cur.moveToFirst();

            maxPoVa = cur.getInt(cur.getColumnIndex("alpi_PoVa"));
            maxName = cur.getString(cur.getColumnIndex("alpi_PiID"));
            temp[i * 2] = maxName;
            getPhoto(maxName, id[(i + 1) * 2 - 2]);

            cur.moveToNext();
            maxPoVa = cur.getInt(cur.getColumnIndex("alpi_PoVa"));
            maxName = cur.getString(cur.getColumnIndex("alpi_PiID"));
            temp[i * 2 + 1] = maxName;
            getPhoto(maxName, id[(i + 1) * 2 - 1]);
            i++;
        }
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
            Toast.makeText(MainActivity.this, "移除收藏夹成功!", Toast.LENGTH_SHORT).show();
        } else if (cur.getCount() == 0) {
            addFavorite(id);
            Toast.makeText(MainActivity.this, "加入收藏夹成功!", Toast.LENGTH_SHORT).show();
        }
    }

    //加入收藏夹
    public boolean addFavorite(String id) {
        //系统时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        //插入收藏夹表
        ContentValues FavoriteDetail = new ContentValues();
        FavoriteDetail.put("fade_FaID", UserName);
        FavoriteDetail.put("fade_PiID", id);
        FavoriteDetail.put("fade_Time", str);
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

    public void getUserName() {
        Intent intent = getIntent();
        UserName = intent.getStringExtra("UserName");
    }

    //明日计划
    public void tomorrowPlan(String id, String type) {
        Cursor cur = null;
        cur = db.rawQuery("select * from PlDe where plde_PlID = '" + UserName + "'", null);
        if (cur.getCount() == 0) {
            addTomoPlan(id, type);
        } else
            updateTomoPlan(id, type);
    }
    //新建明日计划
    public void addTomoPlan(String id, String type) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        //插入type类型计划
        ContentValues PlDe = new ContentValues();
        PlDe.put("plde_PlID", UserName);
        if (type == "suit")
            PlDe.put("plde_SuID", id);
        else if (type == "makeup")
            PlDe.put("plde_MaID", id);
        else if (type == "haircut")
            PlDe.put("plde_HaID", id);
        try {
            db.insert("PlDe", null, PlDe);
            Toast.makeText(MainActivity.this, "计划添加成功", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(MainActivity.this, "新建明日计划出现异常!", Toast.LENGTH_SHORT).show();
        }
    }
    //修改已存在计划，加入其他类型图片，修改已存在类型图片
    public void updateTomoPlan(String id, String type) {
        //系统时间
        Cursor cur = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        ContentValues PlDe = new ContentValues();
        String flag = "change";
        PlDe.put("plde_PlID", UserName);
        if (type == "suit") {
            cur = db.rawQuery("select * from PlDe where plde_PlID ='" + UserName + "' and plde_SuID != '" + null + "'", null);
            String sql = "update [PlDe] set plde_SuID = '" + id + "'where plde_PlID = '" + UserName + "'";
            //若已存在该图片，替换？
            if (cur.getCount() > 0) {
                cur = db.rawQuery("select * from PlDe where plde_PlID ='" + UserName + "' and plde_SuID = '" + id + "'", null);
                if(cur.getCount() > 0) {
                    sql = "delete from PlDe where plde_PlID = '" + UserName + "' and plde_SuID = '" + id + "'";
                    flag = "delete";
                }
                exTomoPlan(id, sql, flag);
            }
            //不存在该图片修改该用户计划，加入
            else {
                db.execSQL(sql);
            }
        }
        else if (type == "makeup") {
            cur = db.rawQuery("select * from PlDe where plde_PlID ='" + UserName + "' and plde_MaID != '" + null + "'", null);
            String sql = "update [PlDe] set plde_MaID = '" + id + "'where plde_PlID = '" + UserName + "'";
            if (cur.getCount() > 0) {
                cur = db.rawQuery("select * from PlDe where plde_PlID ='" + UserName + "' and plde_MaID = '" + id + "'", null);
                if(cur.getCount() > 0) {
                    sql = "delete from PlDe where plde_PlID = '" + UserName + "' and plde_MaID = '" + id + "'";
                    flag = "delete";
                }
                exTomoPlan(id, sql, flag);
            }
            else {
                db.execSQL(sql);
            }
        }
        else if (type == "haircut") {
            cur = db.rawQuery("select * from PlDe where plde_PlID ='" + UserName + "' and plde_HaID != '" + null + "'", null);
            String sql = "update [PlDe] set plde_HaID = '" + id + "'where plde_PlID = '" + UserName + "'";
            if (cur.getCount() > 0) {
                cur = db.rawQuery("select * from PlDe where plde_PlID ='" + UserName + "' and plde_HaID = '" + id + "'", null);
                if(cur.getCount() > 0) {
                    sql = "delete from PlDe where plde_PlID = '" + UserName + "' and plde_HaID = '" + id + "'";
                    flag = "delete";
                }
                exTomoPlan(id, sql, flag);
            }
            else {
                db.execSQL(sql);
            }
        }
    }
    //替换明日计划
    public void exTomoPlan(String id, final String sql, final String flag){
        //通过AlertDiaglog.Builder这个类来实例化我们的一个AlertDialog的对象
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                    Toast.makeText(MainActivity.this, "移除成功！", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "替换成功！", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
    //删除明日计划
    public void deleteTomoPlan(String id, String type) {
        String sql = null;
        if (type == "suit")
            sql = "delete from PlDe where plde_PlID = '" + UserName + "'and plde_SuID = '" + id + "'";
        else if (type == "makeup")
            sql = "delete from PlDe where plde_PlID = '" + UserName + "'and plde_MaID = '" + id + "'";
        else if (type == "haircut")
            sql = "delete from PlDe where plde_PlID = '" + UserName + "' and plde_HaID = '" + id + "'";
        db.execSQL(sql);
    }
    private String UserName;
    private String[] temp;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUserName();
        //打开数据库
        OpenCreateDB();
//        insertSuOrSe("suit", R.drawable.cc, "cc", 6);
//        insertHaOrMa("haircut",R.drawable.hfxbwz, "hfxbwz", null, "no",10);
        showInMain();
        ImageView.OnClickListener listener = new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserName == null){
                    Toast.makeText(MainActivity.this, "未登陆!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int id = v.getId();
                switch (id) {
                    case R.id.IM1:
                        Favorite(temp[0]);
                        break;
                    case R.id.IM5:
                        Favorite(temp[1]);
                        break;
                    case R.id.IM9:
                        Favorite(temp[2]);
                        break;
                    case R.id.IM13:
                        Favorite(temp[3]);
                        break;
                    case R.id.IM17:
                        Favorite(temp[4]);
                        break;
                    case R.id.IM21:
                        Favorite(temp[5]);
                        break;
                    case R.id.IM4:
                        tomorrowPlan(temp[2],"suit");
                        break;
                    case R.id.IM8:
                        tomorrowPlan(temp[3],"suit");
                        break;
                    case R.id.IM12:
                        tomorrowPlan(temp[4],"makeup");
                        break;
                    case R.id.IM16:
                        tomorrowPlan(temp[5],"makeup");
                        break;
                    case R.id.IM20:
                        tomorrowPlan(temp[4],"haircut");
                        break;
                    case R.id.IM24:
                        tomorrowPlan(temp[5],"haircut");
                        break;
                    default:
                        break;
                }
            }
        };
        ImageView IM1 =(ImageView) findViewById(R.id.IM1);
        IM1.setOnClickListener(listener);
        ImageView IM5 =(ImageView) findViewById(R.id.IM5);
        IM5.setOnClickListener(listener);
        ImageView IM9 =(ImageView) findViewById(R.id.IM9);
        IM9.setOnClickListener(listener);
        ImageView IM13 =(ImageView) findViewById(R.id.IM13);
        IM13.setOnClickListener(listener);
        ImageView IM17 =(ImageView) findViewById(R.id.IM17);
        IM17.setOnClickListener(listener);
        ImageView IM21 =(ImageView) findViewById(R.id.IM21);
        IM21.setOnClickListener(listener);
        ImageView IM4 =(ImageView) findViewById(R.id.IM4);
        IM4.setOnClickListener(listener);
        ImageView IM8 =(ImageView) findViewById(R.id.IM8);
        IM8.setOnClickListener(listener);
        ImageView IM12 =(ImageView) findViewById(R.id.IM12);
        IM12.setOnClickListener(listener);
        ImageView IM16 =(ImageView) findViewById(R.id.IM16);
        IM16.setOnClickListener(listener);
        ImageView IM20 =(ImageView) findViewById(R.id.IM20);
        IM20.setOnClickListener(listener);
        ImageView IM24 =(ImageView) findViewById(R.id.IM24);
        IM24.setOnClickListener(listener);



        Button bu=(Button) findViewById(R.id.button8);
        bu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,HairStyle.class);
                startActivity(intent);
            }
        });
        Button b=(Button) findViewById(R.id.button5);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,DressStyle.class);
                startActivity(intent);
            }
        });
        Button but=(Button) findViewById(R.id.button6);
        but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MakeupStyle.class);
                startActivity(intent);
            }
        });

        Button butto=(Button) findViewById(R.id.button7);
        butto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent in = getIntent();
                String UserName = in.getStringExtra("UserName");
                Intent intent=new Intent(MainActivity.this,MySpace.class);
                intent.putExtra("UserName",UserName);
                startActivity(intent);
            }
        });
        Button but1=(Button) findViewById(R.id.button4);
        but1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scrollToPosition();
            }
        });
        Button but3=(Button) findViewById(R.id.button2);
        but3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scrollTo();
                //点击最热发型时显示返回的参数
                Intent intent = getIntent();
                String data = intent.getStringExtra("userID");
                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
            }
        });
        Button but2=(Button) findViewById(R.id.button3);
        but2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scrollT();
            }
        });
        mViewPaper=(ViewPager)findViewById(R.id.vp);

        //显示的图片  
        images=new ArrayList<ImageView>();
        for(int i=0;i<imageIds.length;i++){
            ImageView imageView=new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        //显示的小点  
        dots=new ArrayList<View>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));
        dots.add(findViewById(R.id.dot_4));

        title=(TextView) findViewById(R.id.title);
        title.setText(titles[0]);

        adapter=new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter);
        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){



            public void onPageSelected(int position){
                title.setText(titles[position]);
                dots.get(position).setBackgroundResource(R.drawable.ex);
                dots.get(oldPosition).setBackgroundResource(R.drawable.ex);

                oldPosition=position;
                currentItem=position;
            }


            public void onPageScrolled(int arg0,float arg1,int arg2){
            }


            public void onPageScrollStateChanged(int arg0){

            }
        });

    }

    /** 
          * 自定义Adapter 
          * @author liuyazhuang 
          * 
          */
    private class ViewPagerAdapter extends PagerAdapter {


        public int getCount(){
            return images.size();
        }


        public boolean isViewFromObject(View arg0,Object arg1){
            return arg0==arg1;
        }


        public void destroyItem(ViewGroup view,int position,Object object){
            // TODO Auto-generated method stub  
//          super.destroyItem(container, position, object);  
//          view.removeView(view.getChildAt(position));  
//          view.removeViewAt(position);  
            view.removeView(images.get(position));
        }
        public Object instantiateItem(ViewGroup view,int position){
            // TODO Auto-generated method stub  
            view.addView(images.get(position));
            return images.get(position);
        }

    }


    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.  
//        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    /** 
          * 利用线程池定时执行动画轮播 
          */

    protected void onStart(){
        // TODO Auto-generated method stub  
        super.onStart();
        scheduledExecutorService=Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                2,
                2,
                TimeUnit.SECONDS);
    }


    /** 
          * 图片轮播任务 
          * @author liuyazhuang 
          * 
          */
    private class ViewPageTask implements Runnable{
        public void run(){
            currentItem=(currentItem+1)%imageIds.length;
            mHandler.sendEmptyMessage(0);
        };
    }

    /** 
          * 接收子线程传递过来的数据 
          */
    private Handler mHandler=new Handler(){
        public void handleMessage(android.os.Message msg){
            mViewPaper.setCurrentItem(currentItem);
        };
    };

    protected void onStop(){
        // TODO Auto-generated method stub  
        super.onStop();
        if(scheduledExecutorService!=null){
            scheduledExecutorService.shutdown();
            scheduledExecutorService=null;
        }
    }
    private void scrollToPosition() {
        handler.post(new Runnable() {

            @Override
            public void run() {
                TextView textView=(TextView)findViewById(R.id.textView);
                int top=textView.getTop();
                ScrollView scrollView=(ScrollView)findViewById(R.id.sc1);
              scrollView.scrollTo(0, top);
//                scrollView.smoothScrollTo(0,top);
            }
        });
    }
    private void scrollTo() {
        handler.post(new Runnable() {

            @Override
            public void run() {
                TextView textView=(TextView)findViewById(R.id.textView2);
                int top=textView.getTop();
                ScrollView scrollView=(ScrollView)findViewById(R.id.sc1);
//                scrollView.scrollTo(0, 3000);
                scrollView.smoothScrollTo(0,top);
            }
        });
    }
    private void scrollT() {
        handler.post(new Runnable() {

            @Override
            public void run() {
                TextView textView=(TextView)findViewById(R.id.textView4);
                int top=textView.getTop();
                ScrollView scrollView=(ScrollView)findViewById(R.id.sc1);
//                scrollView.scrollTo(0, 3000);
                scrollView.smoothScrollTo(0,top);
            }
        });
    }


}


