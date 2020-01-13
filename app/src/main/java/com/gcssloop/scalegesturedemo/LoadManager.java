package com.gcssloop.scalegesturedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoadManager {
    private Context mContext;
    private String mUrl;
    private Integer mResource;
    private static ImageView mImageView;
    public LoadManager(Context context){
        this.mContext=context;
    }

    public LoadManager load(String url){
        this.mUrl=url;
        return  this;
    }
    public LoadManager load(Integer resource){
        this.mResource=resource;
        return  this;
    }
    public LoadManager into(ImageView view){
        this.mImageView=view;
        if (null!=mResource&&mResource!=0){
            mImageView.setImageResource(mResource);
        }else if (!TextUtils.isEmpty(mUrl)){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url=new URL(mUrl);
                        InputStream inputStream = url.openStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        Message message=new Message();
                        message.obj=bitmap;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        return this;
    }

    static Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
              mImageView.setImageBitmap((Bitmap) message.obj);
            return false;
        }
    });
}
