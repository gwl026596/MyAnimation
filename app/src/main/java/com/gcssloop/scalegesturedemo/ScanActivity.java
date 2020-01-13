package com.gcssloop.scalegesturedemo;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.samonxu.qrcode.camera.CameraManager;
import com.samonxu.qrcode.camera.PreviewFrameShotListener;
import com.samonxu.qrcode.camera.Size;
import com.samonxu.qrcode.decode.DecodeListener;
import com.samonxu.qrcode.decode.DecodeThread;
import com.samonxu.qrcode.decode.LuminanceSource;
import com.samonxu.qrcode.decode.PlanarYUVLuminanceSource;
import com.samonxu.qrcode.decode.RGBLuminanceSource;
import com.samonxu.qrcode.view.CaptureView;

public class ScanActivity extends AppCompatActivity implements SurfaceHolder.Callback, PreviewFrameShotListener, DecodeListener {

    private SurfaceView previewSv;
    private CaptureView captureView;
    private CameraManager mCameraManager;
    private boolean isDecoding = false;
    private static final long VIBRATE_DURATION = 200L;
    private DecodeThread mDecodeThread;
    private Rect previewFrameRect = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        previewSv = (SurfaceView) findViewById(R.id.sv_preview);
        captureView = (CaptureView) findViewById(R.id.cv_capture);
        previewSv.getHolder().addCallback(this);
        mCameraManager = new CameraManager(this);
        mCameraManager.setPreviewFrameShotListener(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            mCameraManager.initCamera(surfaceHolder);
        } catch (Exception e) {
            Toast.makeText(this, "相机权限被拒绝，请去设置里面打开", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (!mCameraManager.isCameraAvailable()) {
            Toast.makeText(this, "未开启相机", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
//        if (mCameraManager.isFlashlightAvailable()) {
//            flashCb.setEnabled(true);
//        }
        mCameraManager.startPreview();
        if (!isDecoding) {
            mCameraManager.requestPreviewFrameShot();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mCameraManager.stopPreview();
        if (mDecodeThread != null) {
            mDecodeThread.cancel();
        }
        mCameraManager.release();
    }

    @Override
    public void onPreviewFrame(byte[] data, Size frameSize) {
        if (mDecodeThread != null) {
            mDecodeThread.cancel();
        }
        if (previewFrameRect == null) {
            previewFrameRect = mCameraManager.getPreviewFrameRect(captureView.getFrameRect());
        }
        PlanarYUVLuminanceSource luminanceSource = new PlanarYUVLuminanceSource(data, frameSize, previewFrameRect);
        mDecodeThread = new DecodeThread(luminanceSource, this);
        isDecoding = true;
        mDecodeThread.execute();
    }

    @Override
    public void onDecodeSuccess(Result result, LuminanceSource source, Bitmap bitmap) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(VIBRATE_DURATION);
        isDecoding = false;

        String text = result.getText();

        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "不识别该二维码", Toast.LENGTH_SHORT).show();
            isDecoding = true;
        }
    }

    @Override
    public void onDecodeFailed(LuminanceSource source) {
        if (source instanceof RGBLuminanceSource) {
            Toast.makeText(this, "没有找到二维码", Toast.LENGTH_SHORT).show();
        }
        fial();
    }

    @Override
    public void foundPossibleResultPoint(ResultPoint point) {
        captureView.addPossibleResultPoint(point);
    }

    /**
     * 延迟1s
     */
    private void fial() {

        isDecoding = false;
        mCameraManager.requestPreviewFrameShot();

    }

}
