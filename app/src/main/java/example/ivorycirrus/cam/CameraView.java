package example.ivorycirrus.cam;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraView  extends SurfaceView implements SurfaceHolder.Callback,Camera.PreviewCallback{
    private static final int PREVIEW_WIDTH = 320;
    private static final int PREVIEW_HEIGHT = 240;
    private static final int PREVIEW_FPS_MIN = 2;
    private static final int PREVIEW_FPS_MAX = 16;

    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraView(Context context,Camera camera){
        super(context);
        mCamera=camera;
        mHolder=getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        double originalWidth = MeasureSpec.getSize(widthMeasureSpec);
        double originalHeight = MeasureSpec.getSize(heightMeasureSpec);

        int calculatedHeight = (int)(originalWidth * PREVIEW_HEIGHT / PREVIEW_WIDTH);

        int finalWidth, finalHeight;

        if (calculatedHeight > originalHeight) {
            finalWidth = (int)(originalHeight * PREVIEW_WIDTH / PREVIEW_HEIGHT);
            finalHeight = (int) originalHeight;
        } else {
            finalWidth = (int) originalWidth;
            finalHeight = calculatedHeight;
        }

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(finalWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY));
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.setPreviewCallback(null);
        mCamera.release();
        mCamera=null;
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        try{
            mCamera.stopPreview();
        } catch (Exception e){
            e.printStackTrace();
        }
        try{
            Camera.Parameters parameters = mCamera.getParameters();

            parameters.setPreviewSize(PREVIEW_WIDTH, PREVIEW_HEIGHT);
            parameters.setPreviewFormat(ImageFormat.NV21);
            if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            }

            mCamera.setParameters(parameters);
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewCallback(this);

            mCamera.startPreview();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onPreviewFrame(byte[] data,Camera camera){
        try{
            YuvImage yuvimage=new YuvImage(data,ImageFormat.NV21,PREVIEW_WIDTH,PREVIEW_HEIGHT,null);
            System.out.println("Preview >> Width and Height"+yuvimage.getHeight()+"::"+yuvimage.getWidth());
        }catch(Exception e){
            Log.d("CameraView","parse error");
        }
    }

}