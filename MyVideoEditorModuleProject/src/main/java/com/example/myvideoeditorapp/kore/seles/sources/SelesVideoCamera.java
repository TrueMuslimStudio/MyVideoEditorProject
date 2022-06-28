// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.sources;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;

import com.example.myvideoeditorapp.kore.media.camera.TuSdkCameraOrientationImpl;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.hardware.CameraConfigs;
import com.example.myvideoeditorapp.kore.utils.hardware.CameraHelper;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkFace;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;

import java.io.IOException;
import java.util.List;

public class SelesVideoCamera extends SelesVideoCameraBase
{
    private Camera.CameraInfo b;
    private boolean c;
    private CameraConfigs.CameraFacing d;
    private boolean e;
    private boolean f;
    private SelesVideoCameraEngine g;
    
    public Camera.CameraInfo inputCameraInfo() {
        return this.b;
    }
    
    public Camera.Parameters inputCameraParameters() {
        if (this.inputCamera() == null) {
            return null;
        }
        return this.inputCamera().getParameters();
    }
    
    public CameraConfigs.CameraFacing cameraPosition() {
        return CameraHelper.cameraPosition(this.inputCameraInfo());
    }
    
    public boolean isFrontFacingCameraPresent() {
        return this.cameraPosition() == CameraConfigs.CameraFacing.Front;
    }
    
    public boolean isBackFacingCameraPresent() {
        return this.cameraPosition() == CameraConfigs.CameraFacing.Back;
    }
    
    public boolean isEnableFaceTrace() {
        return this._isEnableFaceTrace();
    }
    
    public void setEnableFaceTrace(final boolean c) {
        this.c = c;
        if (c) {
            this.a();
        }
        else {
            this.b();
        }
    }
    
    public boolean _isEnableFaceTrace() {
        return this.c;
    }
    
    public boolean isPreviewStarted() {
        return this.e;
    }
    
    public SelesVideoCamera(final Context context, final CameraConfigs.CameraFacing cameraFacing) {
        super(context);
        this.g = new SelesVideoCameraEngine() {
            @Override
            public boolean canInitCamera() {
                SelesVideoCamera.this.b = CameraHelper.firstCameraInfo(SelesVideoCamera.this.d);
                if (SelesVideoCamera.this.b == null) {
                    TLog.e("The device can not find any camera info: %s", SelesVideoCamera.this.b);
                    return false;
                }
                return true;
            }
            
            @Override
            public Camera onInitCamera() {
                if (SelesVideoCamera.this.b == null) {
                    return null;
                }
                Camera camera = CameraHelper.getCamera(SelesVideoCamera.this.b);
                if (camera == null) {
                    final Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                    for (int numberOfCameras = Camera.getNumberOfCameras(), i = 0; i < numberOfCameras; ++i) {
                        try {
                            Camera.getCameraInfo(i, cameraInfo);
                        }
                        catch (Exception ex3) {}
                        if (cameraInfo.facing == 1) {
                            try {
                                camera = Camera.open(i);
                                break;
                            }
                            catch (Exception ex) {
                                TLog.e("Open front-facing camera filed %s", ex);
                            }
                        }
                    }
                    if (camera == null) {
                        try {
                            TLog.e("No front-facing camera found; opening default", new Object[0]);
                            camera = Camera.open();
                        }
                        catch (Exception ex2) {
                            TLog.e("Open default camera filed %s", ex2);
                        }
                    }
                }
                if (camera == null) {
                    TLog.e("The device can not find init camera: %s", SelesVideoCamera.this.b);
                    return null;
                }
                SelesVideoCamera.this.onInitConfig(camera);
                return camera;
            }
            
            @Override
            public TuSdkSize previewOptimalSize() {
                if (SelesVideoCamera.this.inputCamera() == null) {
                    return null;
                }
                return CameraHelper.createSize(SelesVideoCamera.this.inputCamera().getParameters().getPreviewSize());
            }
            
            @Override
            public void onCameraWillOpen(final SurfaceTexture previewTexture) {
                if (SelesVideoCamera.this.inputCamera() == null) {
                    return;
                }
                try {
                    SelesVideoCamera.this.inputCamera().setPreviewTexture(previewTexture);
                }
                catch (IOException ex) {
                    TLog.e(ex, "onCameraWillOpen", new Object[0]);
                }
                catch (RuntimeException ex2) {
                    TLog.e(ex2, "onCameraWillOpen", new Object[0]);
                }
            }
            
            @Override
            public void onCameraStarted() {
            }
            
            @Override
            public ImageOrientation previewOrientation() {
                return SelesVideoCamera.this.computerOutputOrientation();
            }
        };
        this.d = ((cameraFacing == null) ? CameraConfigs.CameraFacing.Back : cameraFacing);
        super.setCameraEngine(this.g);
    }
    
    @Deprecated
    @Override
    public void setCameraEngine(final SelesVideoCameraEngine selesVideoCameraEngine) {
    }
    
    @Override
    protected void onPreviewStarted() {
        super.onPreviewStarted();
        this.e = true;
        this.a();
    }
    
    @Override
    public void stopCameraCapture() {
        super.stopCameraCapture();
        this.b();
        this.e = false;
        this.f = false;
        this.b = null;
    }
    
    protected void onInitConfig(final Camera camera) {
    }
    
    @Override
    protected void onCameraStarted() {
        super.onCameraStarted();
    }
    
    private void a() {
        if (Build.VERSION.SDK_INT > 13 && this._isEnableFaceTrace()) {
            this.c();
        }
    }
    
    private void b() {
        if (Build.VERSION.SDK_INT > 13) {
            this.d();
        }
    }
    
    @TargetApi(14)
    private void c() {
        if (this.inputCamera() == null || !this.isPreviewStarted() || this.f || !CameraHelper.canSupportFaceDetection(this.inputCamera().getParameters())) {
            return;
        }
        this.d();
        this.f = true;
        this.inputCamera().setFaceDetectionListener((Camera.FaceDetectionListener)new Camera.FaceDetectionListener() {
            public void onFaceDetection(final Camera.Face[] array, final Camera camera) {
                SelesVideoCamera.this.onCameraFaceDetection(CameraHelper.transforFaces(array, SelesVideoCamera.this.mOutputRotation), SelesVideoCamera.this.mInputTextureSize.transforOrientation(SelesVideoCamera.this.mOutputRotation));
            }
        });
        try {
            this.inputCamera().startFaceDetection();
        }
        catch (Exception ex) {
            this.f = false;
            TLog.e(ex, "startFaceDetection", new Object[0]);
        }
    }
    
    public void onCameraFaceDetection(final List<TuSdkFace> list, final TuSdkSize tuSdkSize) {
    }
    
    @TargetApi(14)
    private void d() {
        if (this.inputCamera() == null || !this.f) {
            return;
        }
        this.f = false;
        try {
            this.inputCamera().setFaceDetectionListener((Camera.FaceDetectionListener)null);
            this.inputCamera().stopFaceDetection();
        }
        catch (Exception ex) {
            TLog.e(ex, "stopFaceDetection", new Object[0]);
        }
    }
    
    @Override
    public void pauseCameraCapture() {
        super.pauseCameraCapture();
        this.b();
    }
    
    @Override
    public void resumeCameraCapture() {
        super.resumeCameraCapture();
    }
    
    public void rotateCamera() {
        final int cameraCounts = CameraHelper.cameraCounts();
        if (!this.isCapturing() || cameraCounts < 2) {
            return;
        }
        this.d = ((this.d == CameraConfigs.CameraFacing.Front) ? CameraConfigs.CameraFacing.Back : CameraConfigs.CameraFacing.Front);
        this.startCameraCapture();
    }
    
    protected ImageOrientation computerOutputOrientation() {
        return TuSdkCameraOrientationImpl.computerOutputOrientation(this.getContext(), this.b, this.isHorizontallyMirrorRearFacingCamera(), this.isHorizontallyMirrorFrontFacingCamera(), this.getOutputImageOrientation());
    }
}
