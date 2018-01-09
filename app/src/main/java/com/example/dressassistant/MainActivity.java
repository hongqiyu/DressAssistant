package com.example.dressassistant;
import android.content.ContentValues;
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
    private Handler handler=new Handler();
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View>dots;
    private int currentItem;
    //记录上一次点的位置  
    private int oldPosition=0;
    //存放图片的id  
    private int[] imageIds=new int[]{
            R.drawable.zrzrone,
            R.drawable.ct,
            R.drawable.zrfxtwo,
            R.drawable.zrfxone,
            R.drawable.zrfxthree
    };
    //存放图片的标题  
    private String[] titles=new String[]{
            "最美的妆容，配最美的你",
            "冬天，仙女们都这么穿",
            "好看的发型，就要这么扎",
            "做最美的自己",
            "据说，好看的人都扎了这种发型"
    };
    private TextView title;
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;
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

    //插入图片汇总表、套服、单品、图片属性推荐表（图片类型、资源id、图片名、人气值）
    public boolean insertSuOrSe(String type, int id, String photoId, int PoVa){
        Cursor cursor = db.rawQuery("select * from AllPicture where alpi_PiID='" + photoId +"'", null);
        if(cursor.getCount()>0){
            cursor.close();
            return false;
        }
        Resources res = getResources(); //打开drawable文件夹的资源
        Bitmap bmp = BitmapFactory.decodeResource(res,id);  // 此处id  例如为 R.drawable.co
        int size = bmp.getWidth()*bmp.getHeight()*4;
        ByteArrayOutputStream os = new ByteArrayOutputStream(size); //设置读取大小
        bmp.compress(Bitmap.CompressFormat.PNG,100,os); //设置压缩格式，存入流
        //插入图片推荐属性表
        ContentValues PRAt = new ContentValues();
        PRAt.put("prat_PiID", photoId);
         db.insert("PRAt", null, PRAt);

        if(type == "suit") {
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
        }
        else if(type == "Separate"){
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
    public boolean insertHaOrMa(String type, int id, String photoId, String videoName, String Flag, int PoVa){
//        Cursor cursor = db.rawQuery("select * from AllPicture where alpi_PiID='" + photoId +"'", null);
//        if(cursor.getCount()>0) {
//            cursor.close();
//            return true;
//        }
        Resources res = getResources(); //打开drawable文件夹的资源
        Bitmap bmp = BitmapFactory.decodeResource(res,id);  // 此处id  例如为 R.drawable.co
        int size = bmp.getWidth()*bmp.getHeight()*4;
        ByteArrayOutputStream os = new ByteArrayOutputStream(size); //设置读取大小
        bmp.compress(Bitmap.CompressFormat.PNG,100,os); //设置压缩格式，存入流
        ContentValues values = new ContentValues();
        if(type == "haircut"){
            Cursor cursor = db.rawQuery("select * from AllPicture where alpi_PiID='" + photoId +"'", null);
            if(cursor.getCount()>0) {
                cursor.close();
            }
            else {
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
            if(Flag == "yes"){
                String local= "res/raw" + videoName;
                Haircut.put("hair_VeTu", local);
                Haircut.put("hair_Flag", "yes");
            }
            else
                Haircut.put("hair_Flag", "no");
            db.insert("Haircut", null, Haircut);
            return true;
        }
        else if(type == "makeup"){


            Cursor cursor = db.rawQuery("select * from AllPicture where alpi_PiID='" + photoId +"'", null);
            if(cursor.getCount()>0) {
                cursor.close();
            }
            else {
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

            if(Flag == "yes"){
                String local= "res/raw" + videoName;
                Makeup.put("make_VeTu", local);
                Makeup.put("make_Flag", "yes");
            }
            else
                Makeup.put("make_Flag", "no");
            db.insert("Makeup", null, Makeup);
            return true;
        }
        return false;
    }

    //从sqlite读取图片，并显示在桌面上
    public boolean getPhoto(String photoName, int id){
        Cursor cur = db.rawQuery("select * from AllPicture where alpi_PiID='" + photoName + "'",null);
        cur.moveToFirst();
        byte[] in = cur.getBlob(cur.getColumnIndex("alpi_PiLo"));
        Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0 ,in.length);

        BitmapDrawable bd = new BitmapDrawable(getResources(),bmpout);
        ImageView imageView = (ImageView) findViewById(id);
        imageView.setImageDrawable(bd);
        return false;
    }
    public void showInMain(){
        String[] s = {"suit", "makeup", "haircut"};
        int[] id = {R.id.imageView8, R.id.imageView9, R.id.imageView10,R.id.imageView3,R.id.imageView11,R.id.imageView4};
        int i = 0;
        int maxPoVa;
        String maxName;
        Cursor cur = null;
        Cursor cur2 = null;
        Cursor cur3 = null;
        while (i < 3){
            cur = db.rawQuery("select * from AllPicture where alpi_Flag = '" + s[i] + "'" + "order by alpi_PoVa desc", null);
            cur.moveToFirst();

            maxPoVa = cur.getInt(cur.getColumnIndex("alpi_PoVa"));
            maxName = cur.getString(cur.getColumnIndex("alpi_PiID"));
            getPhoto(maxName, id[(i+1)*2-2]);

            cur.moveToNext();
            maxPoVa = cur.getInt(cur.getColumnIndex("alpi_PoVa"));
            maxName = cur.getString(cur.getColumnIndex("alpi_PiID"));
            getPhoto(maxName, id[(i+1)*2-1]);
            i++;
        }
    }
    public boolean deleteFavorite(String id, String userID){
        //删除收藏夹的图片
        String sql = "delete from FavoriteDetail wherefade_FaID = '" + id + "'";
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
        }catch(SQLException e){
            return false;
        }
    }

    public void Favorite(String id, String userID) {
        Cursor cur = null;
        cur = db.rawQuery("select * from FavoriteDetail where fade_FaID = '" + id + "'", null);
        if(cur.getCount()>0)
            deleteFavorite(id, userID);
        else if (cur.getCount() == 0)
            addFavorite(id, userID);
    }

    public boolean addFavorite(String id, String userID){
        //系统时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        //插入收藏夹表
        ContentValues FavoriteDetail = new ContentValues();
        FavoriteDetail.put("fade_FaID", userID);
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
        }catch(SQLException e){
            return false;
        }
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //打开数据库
        OpenCreateDB();
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
//        String resTypeName = getContext().getResources().getResourceTypeName(id)

//
//        insertSuOrSe("suit", R.drawable.cc, "cc", 6);
//        insertHaOrMa("makeup",R.drawable.zrzrone, "zrzrone", null, "no",6);
        showInMain();
        ImageView imageView8=(ImageView) findViewById(R.id.imageView8);
        String drawable =getResources().getResourceName(R.id.imageView8);
        Toast.makeText(MainActivity.this, drawable, Toast.LENGTH_SHORT).show();
        ImageView.OnClickListener listener = new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                switch (id) {
                    case R.id.IM1:
                    case R.id.IM5:
                    case R.id.IM9:
                    case R.id.IM13:
                    case R.id.IM17:
                    case R.id.IM21:
//                        Favorite(v.getId().getBackground(), userID);
                        break;
                    default:
                        break;
                }
            }
        };
        ImageView IM4 =(ImageView) findViewById(R.id.IM1);
        IM4.setOnClickListener(listener);



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


