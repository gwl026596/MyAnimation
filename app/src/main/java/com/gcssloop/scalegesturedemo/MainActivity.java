package com.gcssloop.scalegesturedemo;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScaleImageView scaleImageView=findViewById(R.id.scaleImageView);
        TextView scan=findViewById(R.id.scan);
        ImageLoader.with(this).load("https://slife-prod.oss-cn-shanghai.aliyuncs.com/slifeio/funding/fundingModule/picture-file-1554360828108.jpg").into(scaleImageView);
        //scaleImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.test));
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ScanActivity.class));
            }
        });
    }
}
