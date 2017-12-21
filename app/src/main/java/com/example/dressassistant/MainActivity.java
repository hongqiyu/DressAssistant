package com.example.dressassistant;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
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
import java.util.ArrayList;
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
            R.drawable.co,
            R.drawable.zo,
            R.drawable.zt,
            R.drawable.cc,
            R.drawable.co
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
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                Intent intent=new Intent(MainActivity.this,MySpace.class);
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


