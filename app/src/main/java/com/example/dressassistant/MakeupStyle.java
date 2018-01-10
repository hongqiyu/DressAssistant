package com.example.dressassistant;

/**
 * Created by 洪祺瑜 on 2017/12/2.
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MakeupStyle extends AppCompatActivity {

    public void makeupInfo(String id){
        Intent intent = new Intent(MakeupStyle.this,HairInformation.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makeupstyle);

        ImageView.OnClickListener listener = new ImageView.OnClickListener(){
            public void onClick(View v){
                int id = v.getId();
                switch (id){
                    case R.id.imageButtonA:
                        makeupInfo("mlqxf");//
                        break;
                    case R.id.imageButtonB:
                        makeupInfo("mlyjz");//
                        break;
                    case R.id.imageButtonC:
                        makeupInfo("mlomf");//
                        break;
                    case R.id.imageButtonD:
                        makeupInfo("mlrmf");//
                        break;
                    case R.id.imageButtonE:
                        makeupInfo("hrxzr");//
                        break;
                    case R.id.imageButtonF:
                        makeupInfo("mlyxz");//
                        break;
//                    case R.id.imageButtonG:
//                        makeupInfo("mlyhz");//视频
//                        break;
                    case R.id.imageButtonH:
                        makeupInfo("mlqsz");//
                        break;
                    case R.id.imageButtonI:
                        makeupInfo("mlmz");//
                        break;
                    case R.id.imageButtonJ:
                        makeupInfo("mlyz");//
                    case R.id.imageButtonK:
                        makeupInfo("mlycz");//
                    default:
                        break;
                }
            }
        };
        ImageView iv1 = (ImageView)findViewById(R.id.imageButtonA);
        iv1.setOnClickListener(listener);
        ImageView iv2 = (ImageView)findViewById(R.id.imageButtonB);
        iv2.setOnClickListener(listener);
        ImageView iv3 = (ImageView)findViewById(R.id.imageButtonC);
        iv3.setOnClickListener(listener);
        ImageView iv4 = (ImageView)findViewById(R.id.imageButtonD);
        iv4.setOnClickListener(listener);
//        ImageView iv5 = (ImageView)findViewById(R.id.imageButtonE);
//        iv5.setOnClickListener(listener);
        ImageView iv6 = (ImageView)findViewById(R.id.imageButtonF);
        iv6.setOnClickListener(listener);
//        ImageView iv7 = (ImageView)findViewById(R.id.imageButtonG);
//        iv7.setOnClickListener(listener);
        ImageView iv8 = (ImageView)findViewById(R.id.imageButtonH);
        iv8.setOnClickListener(listener);
        ImageView iv9 = (ImageView)findViewById(R.id.imageButtonI);
        iv9.setOnClickListener(listener);
        ImageView iv10 = (ImageView)findViewById(R.id.imageButtonJ);
        iv10.setOnClickListener(listener);
        ImageView iv11 = (ImageView)findViewById(R.id.imageButtonK);
        iv11.setOnClickListener(listener);

        Button bu=(Button) findViewById(R.id.button8);
        bu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MakeupStyle.this,HairDetails.class);
                startActivity(intent);
            }
        });
        Button b=(Button) findViewById(R.id.button5);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MakeupStyle.this,DressStyle.class);
                startActivity(intent);
            }
        });
        Button butto=(Button) findViewById(R.id.button7);
        butto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MakeupStyle.this,MySpace.class);
                startActivity(intent);
            }
        });
        Button buttonn=(Button)findViewById(R.id.button);
        buttonn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MakeupStyle.this,MainActivity.class);
                startActivity(intent);
            }
        });
        ImageView imbe=(ImageView)findViewById(R.id.imageButtonE);
        imbe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MakeupStyle.this,MakeupDetail.class);
                startActivity(intent);
            }
        });
    }
    }

