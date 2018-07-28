# AndroidCameraPreview
Sample of camera preview application

> **== [[ CAUTION ]] =====================================**<br/>
> This application is a template or a sample for processing camera preview images.<br/>
> It is using ```android.hardware.camera``` API which deprecated since Android 5.0 Lollipop(API 21).<br/>
> I tested on Android 8.0(API 26) and still running well on , but you have to see [Camera2](https://developer.android.com/reference/android/hardware/camera2/package-summary) for production use.

## Implementations
1. Request uses permossion if Android version is 6.0 Marshmellow or heigher
2. Customized surfaceview to show camera preview
3. Add Camera callback interface for processing images of camera preview

## Assumption or restrictions
1. Preview screen is fixed by portrait normally
2. Device has facing back camera and available it
3. Camera supports NV21 image format(YUV image)

## Read More
