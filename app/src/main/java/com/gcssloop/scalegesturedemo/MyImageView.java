package com.gcssloop.scalegesturedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;


public class MyImageView extends AppCompatImageView {

    private int mWidth;
    private int mHeight;
    private Matrix matrix;
    private Paint paint;
    private float x;
    private float y;
    private int mDegree=0;
    private ScaleGestureDetector detector;
    private float startScal;
    private Matrix mInvertMatrix;
    private boolean isFirst=false;

    public MyImageView(Context context) {
        this(context, null);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);
        matrix = new Matrix();
        mInvertMatrix = new Matrix();
        setScaleType(ImageView.ScaleType.MATRIX);
        detector = new ScaleGestureDetector(getContext(),new ScaleGestureDetector.OnScaleGestureListener(){

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                Log.d("衡水市",detector.getScaleFactor()+"==");
                float realScaleFactor = getRealScaleFactor(detector.getScaleFactor());
                float[] xy=mapPoint(detector.getFocusX(),detector.getFocusY(),matrix);
                matrix.postScale(realScaleFactor,realScaleFactor,detector.getFocusX(),detector.getFocusY());
                setImageMatrix(matrix);
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {

            }});
    }
    //--- 将坐标转换为画布坐标 ---
    private float[] mapPoint(float x, float y, Matrix matrix) {
        float[] temp = new float[2];
        temp[0] = x;
        temp[1] = y;
        matrix.mapPoints(temp);
        return temp;
    }
    public void setZhuan(int degree) {
        mDegree+= degree;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

       if (!isFirst){
           startScal = getWidth()*1.0f/getDrawable().getIntrinsicWidth();
           //float height = getDrawable().getIntrinsicWidth()/2.0f;
           matrix.postScale(startScal,startScal);
           matrix.postTranslate(0,mHeight/2.0f- getDrawable().getIntrinsicHeight()/2.0f);
          // matrix.postTranslate(0,getHeight()/2.0f-height);
           setImageMatrix(matrix);
           MIN_SCALE=startScal*1.0f;
           isFirst=true;

       }
    }



    //--- 限制缩放比例 ---
    private static final float MAX_SCALE = 4.0f;    //最大缩放比例
    private static  float MIN_SCALE = 1.0f;    // 最小缩放比例

    private float getRealScaleFactor(float currentScaleFactor) {
        float realScale = 1.0f;
        float userScale =getScale();    // 用户当前的缩放比例
        float theoryScale = userScale * currentScaleFactor;           // 理论缩放数值
Log.d("HHH ",userScale+"=="+theoryScale);
        // 如果用户在执行放大操作并且理论缩放数据大于4.0
        if (currentScaleFactor > 1.0f && theoryScale > MAX_SCALE) {
            realScale = MAX_SCALE / userScale;
        } else if (currentScaleFactor < 1.0f && theoryScale < MIN_SCALE) {
            realScale = MIN_SCALE / userScale;
        } else {
            realScale = currentScaleFactor;
        }
        return realScale;
    }

    private float getScale() {
        float scale[]=new float[9];
        matrix.getValues(scale);
        return scale[Matrix.MSCALE_X];
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
      /*  switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }*/
        detector.onTouchEvent(event);
        return true;
    }


}
