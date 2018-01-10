package com.example.dressassistant;

/**
 * Created by 洪祺瑜 on 2017/12/2.
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

public class HairDetails extends AppCompatActivity {

    public void hairInfo(String id){
        Intent intent = new Intent(HairDetails.this,HairInformation.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hairdetails);

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

    }
}



