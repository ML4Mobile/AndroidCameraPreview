package example.ivorycirrus.cam;

import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class CameraPreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_camera_preview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Camera cam = getCameraInstance();
        if(cam != null) {
            CameraView camView = new CameraView(this, cam);
            FrameLayout preview = findViewById(R.id.frm_preview);
            preview.addView(camView);
        }
    }

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
}
