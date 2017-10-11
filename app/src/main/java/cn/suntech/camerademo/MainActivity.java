package cn.suntech.camerademo;

import android.graphics.ImageFormat;
import android.hardware.camera2.CameraCharacteristics;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private SurfaceView mSuaceView;
    private SurfaceHolder mSuaceViewHolder;
    private int mCameraID;
    private Handler childHandler;
    private Handler mainHandler;
    private ImageView mImageViewShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initUi();
        initUiData();
    }

    private void initUi() {
        mSuaceView = (SurfaceView) findViewById(R.id.sv_show);
        mImageViewShow = (ImageView) findViewById(R.id.iv_show);

    }

    private void initUiData() {
        mSuaceViewHolder = mSuaceView.getHolder();
        mSuaceViewHolder.addCallback(new SurfaceHolder.Callback2() {
            @Override
            public void surfaceRedrawNeeded(SurfaceHolder surfaceHolder) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                initCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initCamera() {
        HandlerThread handlerThread = new HandlerThread("Camera2");
        handlerThread.start();
        childHandler = new Handler(handlerThread.getLooper());
        mainHandler = new Handler(getMainLooper());

        // 获取后置摄像头
        mCameraID = CameraCharacteristics.LENS_FACING_BACK;
        ImageReader imageReader = ImageReader.newInstance(540, 960, ImageFormat.JPEG, 1);
        imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader imageReader) {
                //mCameraDevice.close();
                mSuaceView.setVisibility(View.GONE);
                mImageViewShow.setVisibility(View.VISIBLE);
                // 拿到拍照照片数据
                // Image image = reader.acquireNextImage();
                /*ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);//由缓冲区存入字节数组
                final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                if (bitmap != null) {

                }
*/
            }
        }, mainHandler);


    }
}
