package com.gcssloop.scalegesturedemo;

import android.content.Context;

public class ImageLoader {
    public static LoadManager with(Context context){
        return new LoadManager(context);
    }

}
