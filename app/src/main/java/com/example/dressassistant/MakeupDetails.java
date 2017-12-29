package com.example.dressassistant;

/**
 * Created by 洪祺瑜 on 2017/12/2.
 */
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MakeupDetails extends AppCompatActivity {
    private VideoView videoview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makeupdetails);
        initView();
    }
    private void initView(){
        videoview=(VideoView)findViewById(R.id.video);
//        String path1="https://github.com/chenshouyin/DevNote/blob/master/source_for_commen/video_file.mp4";
//        Uri uri=Uri.parse(path1);
//        videoview.setVideoURI(uri);
        String uri="android.resource://"+getPackageName()+"/"+R.raw.big_buck_bunny;
        videoview.setVideoURI(Uri.parse(uri));
        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
            public void onPrepared(MediaPlayer mp){
// TODO Auto-generated method stub  
//设置视频重复播放  
            }
        });
        videoview.start();//播放视频  
        MediaController medis=new MediaController(MakeupDetails.this);//显示控制条 
        videoview.setMediaController(medis);
        medis.setMediaPlayer(videoview);//设置控制的对象  
        medis.show();
    }
}
