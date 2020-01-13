package com.gcssloop.scalegesturedemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class ShareActivity extends AppCompatActivity {

    private ImageView img_1;
    private ImageView img_2;
    private ImageView img_3;
    private ImageView img_4;
    private ImageView img_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
       ImageView img_click= findViewById(R.id.img_click);
        img_1 = findViewById(R.id.img_1);
        img_2 = findViewById(R.id.img_2);
        img_3 = findViewById(R.id.img_3);
        img_4 = findViewById(R.id.img_4);
        img_5 = findViewById(R.id.img_5);
        img_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOpen(img_1,380,1,0);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickOpen(img_2,380,2,90);
                    }
                },90);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickOpen(img_3,380,3,180);
                    }
                },180);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickOpen(img_4,380,4,270);
                    }
                },270);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickOpen(img_5,380,5,340);
                    }
                },340);
            }
        });
        img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickClose(img_1,380,1);
                clickClose(img_2,380,2);
                clickClose(img_3,380,3);
                clickClose(img_4,380,4);
                clickClose(img_5,380,5);
            }
        });
    }

    private void clickOpen(  View view,int radics,int index,long startDelay) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }else {
            return;
        }
        float x = -(float)( radics * Math.cos(Math.PI / 6 * (index )));
        float y =  -(float)(radics * Math.sin(Math.PI / 6 * (index )));
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "TranslationX",0, x);
        ObjectAnimator TranslationY = ObjectAnimator.ofFloat(view, "TranslationY", 0,y);
        ObjectAnimator ScaleX = ObjectAnimator.ofFloat(view, "ScaleX", 0,1);
        ObjectAnimator ScaleY = ObjectAnimator.ofFloat(view, "ScaleY", 0,1);
        ObjectAnimator Alpha = ObjectAnimator.ofFloat(view, "Alpha", 0,1);
        AnimatorSet set=new AnimatorSet();
        set.playTogether(translationX,TranslationY,ScaleX,ScaleY,Alpha);
        set.setDuration(500);
       
        set.setInterpolator(new LinearInterpolator());
        set.start();

    }


    private void clickClose(final View view,int radics,int index) {
        float x = -(float)( radics * Math.cos(Math.PI / 6 * (index)));
        float y =  -(float)(radics * Math.sin(Math.PI / 6 * (index)));
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "TranslationX", x,0);
        ObjectAnimator TranslationY = ObjectAnimator.ofFloat(view, "TranslationY", y,0);
        ObjectAnimator ScaleX = ObjectAnimator.ofFloat(view, "ScaleX", 1,0);
        ObjectAnimator ScaleY = ObjectAnimator.ofFloat(view, "ScaleY", 1,0);
        ObjectAnimator Alpha = ObjectAnimator.ofFloat(view, "Alpha", 1,0);
        AnimatorSet set=new AnimatorSet();
        set.playTogether(translationX,TranslationY,ScaleX,ScaleY,Alpha);
        set.setDuration(500);
        set.setInterpolator(new LinearInterpolator());
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}
