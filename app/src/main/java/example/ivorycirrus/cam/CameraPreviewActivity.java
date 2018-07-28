package example.ivorycirrus.cam;

import android.content.pm.ActivityInfo;
import android.graphics.ImageFormat;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class CameraPreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Full Screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Fix orientation : portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Set layout
        setContentView(R.layout.activity_camera_preview);

        // Set ui button actions
        findViewById(R.id.btn_finish_preview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraPreviewActivity.this.finish();
            }
        });

        // Initialize Camera
        Camera cam = getCameraInstance();

        // Set-up preview screen
        if(cam != null) {
            // Create view
            CameraView camView = new CameraView(this, cam);
            camView.setPreviewCallback(new CustomPreviewCallback(CameraView.PREVIEW_WIDTH, CameraView.PREVIEW_HEIGHT));

            // Add view to UI
            FrameLayout preview = findViewById(R.id.frm_preview);
            preview.addView(camView);
        }
    }

    /** Get facing back camera instance */
    public static Camera getCameraInstance()
    {
        int camId = -1;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < Camera.getNumberOfCameras(); ++i) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                camId = i;
                break;
            }
        }

        if(camId == -1) return null;

        Camera c=null;
        try{
            c=Camera.open(camId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return c;
    }

    /** Post-processor for preview image streams */
    private class CustomPreviewCallback implements Camera.PreviewCallback {

        private int mImageWidth, mImageHeight;
        CustomPreviewCallback(int imageWidth, int imageHeight){
            mImageWidth = imageWidth;
            mImageHeight = imageHeight;
        }

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            try {
                // TODO : Add ImageProcess Code here...
                YuvImage yuvimage = new YuvImage(data, ImageFormat.NV21, mImageWidth, mImageHeight, null);
                Log.d("CameraView", "Preview >> Width"+yuvimage.getWidth()+" and Height"+yuvimage.getHeight());
            } catch (Exception e) {
                Log.d("CameraView", "parse error");
            }
        }
    }
}
