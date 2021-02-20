package com.nocompany.camerapreview;


import android.hardware.Camera;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class PreviewActivity extends AppCompatActivity
        implements
        SurfaceHolder.Callback,
        Camera.ShutterCallback,
        Camera.PictureCallback, Camera.PreviewCallback {

    Camera mCamera;
    SurfaceView mPreview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        mPreview = (SurfaceView) findViewById(R.id.preview);

    }


    @Override
    protected void onResume() {
        super.onResume();
        mCamera = Camera.open();
        mCamera.setPreviewCallback(this);
        mPreview.getHolder().addCallback(this);
        mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    @Override
    public void onPause() {
        super.onPause();
        mCamera.stopPreview();
        mCamera.release();
        mPreview.getHolder().removeCallback(this);
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("happy","surfaceChanged");
        Camera.Parameters params = mCamera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        Camera.Size selected = sizes.get(0);
        params.setPreviewSize(selected.width, selected.height);
        mCamera.setParameters(params);
        mCamera.setDisplayOrientation(90);
        mCamera.startPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("happy","surfaceCreated");
        try {
            mCamera.setPreviewDisplay(mPreview.getHolder());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("happy","surfaceDestroyed");
    }





    public void onSnapClick(View v) {
        mCamera.takePicture(this, null, null, this);
    }

    @Override
    public void onShutter() {
        Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

            if (!dir.isDirectory())
                dir.mkdirs();


            String filename = String.format("%s/%s.jpg",
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    UUID.randomUUID().toString()
            );

            //FileOutputStream out = openFileOutput("picture.jpg", Activity.MODE_PRIVATE);
            File output = new File(filename);
            FileOutputStream out = new FileOutputStream(output);
            // FileOutputStream out = openFileOutput(filename, Activity.MODE_PRIVATE);
            out.write(data);
            out.flush();
            out.close();
            Utility.scan(this, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
    }


    /**
     * для изменения кадра камеры - инстаграммные фильтры
     * @param data
     * @param camera
     */
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

    }
}
