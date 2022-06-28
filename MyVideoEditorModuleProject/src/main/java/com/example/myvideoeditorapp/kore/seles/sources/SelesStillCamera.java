// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.sources;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.hardware.Camera;
import android.os.Build;

import com.example.myvideoeditorapp.kore.media.camera.TuSdkCameraOrientationImpl;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.ContextUtils;
import com.example.myvideoeditorapp.kore.utils.RectHelper;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.utils.hardware.CameraConfigs;
import com.example.myvideoeditorapp.kore.utils.hardware.CameraHelper;
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;

import java.io.IOException;
import java.util.Calendar;

public abstract class SelesStillCamera extends SelesVideoCamera implements SelesStillCameraInterface
{
    private boolean b;
    private TuSdkSize c;
    private boolean d;
    private boolean e;
    private boolean f;
    private int g;
    private float h;
    private CameraConfigs.CameraFlash i;
    private CameraConfigs.CameraAutoFocus j;
    private long k;
    private CameraConfigs.CameraAntibanding l;
    private float m;
    private float n;
    private Runnable o;
    private boolean p;
    
    public boolean isCapturePhoto() {
        return this.b;
    }
    
    private void a(final boolean b) {
        this.onCapturePhotoStateChanged(this.b = b);
    }
    
    private int a() {
        final TuSdkSize screenSize = ContextUtils.getScreenSize(this.getContext());
        if (this.g < 1 || this.g > screenSize.maxSide()) {
            this.g = screenSize.maxSide();
        }
        return this.g;
    }
    
    @Override
    public void setPreviewMaxSize(final int g) {
        this.g = g;
    }
    
    @Override
    public final TuSdkSize getOutputSize() {
        if (this.c == null) {
            this.c = ContextUtils.getScreenSize(this.getContext());
        }
        return this.c;
    }
    
    @Override
    public void setOutputSize(final TuSdkSize tuSdkSize) {
        try {
            if (tuSdkSize == null || !tuSdkSize.isSize()) {
                return;
            }
            this.c = tuSdkSize.limitSize();
            if (this.inputCamera() == null) {
                return;
            }
            final Camera.Parameters parameters = this.inputCamera().getParameters();
            CameraHelper.setPictureSize(this.getContext(), parameters, this.c, this.n);
            this.inputCamera().setParameters(parameters);
        }
        catch (Exception ex) {
            TLog.e(ex);
        }
    }
    
    public float getRegionRatio() {
        return this.getOutputSize().getRatioFloat();
    }
    
    public boolean isUnifiedParameters() {
        return this.d;
    }
    
    @Override
    public void setUnifiedParameters(final boolean d) {
        this.d = d;
    }
    
    public boolean isAutoReleaseAfterCaptured() {
        return this.e;
    }
    
    @Override
    public void setAutoReleaseAfterCaptured(final boolean e) {
        this.e = e;
    }
    
    public boolean isDisableMirrorFrontFacing() {
        return this.f;
    }
    
    @Override
    public void setDisableMirrorFrontFacing(final boolean f) {
        this.f = f;
    }
    
    public float getPreviewEffectScale() {
        return this.h;
    }
    
    @Override
    public void setPreviewEffectScale(final float h) {
        if (h <= 0.0f) {
            return;
        }
        if (h > 1.0f) {
            this.h = 1.0f;
        }
        this.h = h;
    }
    
    @Override
    public long getLastFocusTime() {
        return this.k;
    }
    
    @Override
    public CameraConfigs.CameraFlash getFlashMode() {
        if (this.inputCamera() == null) {
            return CameraConfigs.CameraFlash.Off;
        }
        final Camera.Parameters parameters = this.inputCamera().getParameters();
        if (parameters != null) {
            return CameraHelper.getFlashMode(parameters);
        }
        if (this.i == null) {
            this.i = CameraConfigs.CameraFlash.Off;
        }
        return this.i;
    }
    
    @Override
    public void setFlashMode(final CameraConfigs.CameraFlash i) {
        if (i == null) {
            return;
        }
        this.i = i;
        if (!CameraHelper.canSupportFlash(this.getContext()) || this.inputCamera() == null) {
            return;
        }
        final Camera.Parameters parameters = this.inputCamera().getParameters();
        if (parameters == null) {
            return;
        }
        CameraHelper.setFlashMode(parameters, i);
        this.inputCamera().setParameters(parameters);
    }
    
    @Override
    public boolean canSupportFlash() {
        try {
            return this.inputCamera() != null && CameraHelper.canSupportFlash(this.getContext()) && CameraHelper.supportFlash(this.inputCamera().getParameters());
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    @Override
    public void setAntibandingMode(final CameraConfigs.CameraAntibanding l) {
        this.l = l;
        if (this.inputCamera() == null) {
            return;
        }
        final Camera.Parameters parameters = this.inputCamera().getParameters();
        if (parameters == null) {
            return;
        }
        CameraHelper.setAntibanding(parameters, this.l);
        this.inputCamera().setParameters(parameters);
    }
    
    @Override
    public CameraConfigs.CameraAntibanding getAntiBandingMode() {
        if (this.inputCamera() == null) {
            return this.l;
        }
        final Camera.Parameters parameters = this.inputCamera().getParameters();
        if (parameters != null) {
            return CameraHelper.antiBandingType(parameters.getAntibanding());
        }
        return this.l;
    }
    
    @Override
    public void autoMetering(final PointF pointF) {
    }
    
    public void setFocusMode(final CameraConfigs.CameraAutoFocus j, final PointF pointF) {
        if (j == null) {
            return;
        }
        this.j = j;
        if (this.inputCamera() == null) {
            return;
        }
        final Camera.Parameters parameters = this.inputCamera().getParameters();
        if (parameters == null) {
            return;
        }
        CameraHelper.setFocusMode(parameters, this.j, this.getCenterIfNull(pointF), this.mOutputRotation);
        this.inputCamera().setParameters(parameters);
    }
    
    public void setFocusPoint(final PointF pointF) {
        if (this.inputCamera() == null) {
            return;
        }
        final Camera.Parameters parameters = this.inputCamera().getParameters();
        if (parameters == null) {
            return;
        }
        CameraHelper.setFocusPoint(parameters, this.getCenterIfNull(pointF), this.mOutputRotation);
        this.inputCamera().setParameters(parameters);
    }
    
    public CameraConfigs.CameraAutoFocus getFocusMode() {
        if (this.inputCamera() == null) {
            return this.j;
        }
        final Camera.Parameters parameters = this.inputCamera().getParameters();
        if (parameters != null) {
            return CameraHelper.focusModeType(parameters.getFocusMode());
        }
        return this.j;
    }
    
    @Override
    public boolean canSupportAutoFocus() {
        if (this.inputCamera() == null) {
            return false;
        }
        boolean canSupportAutofocus;
        try {
            canSupportAutofocus = CameraHelper.canSupportAutofocus(this.getContext(), this.inputCamera().getParameters());
        }
        catch (RuntimeException ex) {
            canSupportAutofocus = false;
            ex.printStackTrace();
        }
        return canSupportAutofocus;
    }
    
    @Override
    public void cancelAutoFocus() {
        if (this.inputCamera() == null || !CameraHelper.canSupportAutofocus(this.getContext())) {
            return;
        }
        this.inputCamera().cancelAutoFocus();
    }
    
    @Override
    public void autoFocus(final CameraConfigs.CameraAutoFocus cameraAutoFocus, final PointF pointF, final TuSdkAutoFocusCallback tuSdkAutoFocusCallback) {
        this.setFocusMode(cameraAutoFocus, pointF);
        this.autoFocus(tuSdkAutoFocusCallback);
    }
    
    public void cancelAutoFocusTimer() {
        if (this.o == null) {
            return;
        }
        ThreadHelper.cancel(this.o);
        this.o = null;
    }
    
    public void doFocusCallback(final TuSdkAutoFocusCallback tuSdkAutoFocusCallback) {
        this.cancelAutoFocusTimer();
        tuSdkAutoFocusCallback.onAutoFocus(this.p, this);
        this.cancelAutoFocus();
    }
    
    @Override
    public void autoFocus(final TuSdkAutoFocusCallback tuSdkAutoFocusCallback) {
        if (this.inputCamera() == null || !this.canSupportAutoFocus()) {
            if (tuSdkAutoFocusCallback != null) {
                tuSdkAutoFocusCallback.onAutoFocus(false, this);
            }
            return;
        }
        this.k = Calendar.getInstance().getTimeInMillis();
        Object o = null;
        this.p = false;
        if (tuSdkAutoFocusCallback != null) {
            o = new Camera.AutoFocusCallback() {
                public void onAutoFocus(final boolean b, final Camera camera) {
                    SelesStillCamera.this.p = b;
                    SelesStillCamera.this.doFocusCallback(tuSdkAutoFocusCallback);
                }
            };
        }
        try {
            this.inputCamera().autoFocus((Camera.AutoFocusCallback)o);
            ThreadHelper.postDelayed(this.o = new Runnable() {
                @Override
                public void run() {
                    SelesStillCamera.this.doFocusCallback(tuSdkAutoFocusCallback);
                }
            }, 1500L);
        }
        catch (Exception ex) {
            TLog.e("autoFocus", ex);
        }
    }
    
    @TargetApi(16)
    public void setAutoFocusMoveCallback(final Camera.AutoFocusMoveCallback autoFocusMoveCallback) {
        if (this.inputCamera() != null || !CameraHelper.canSupportAutofocus(this.getContext())) {
            this.inputCamera().setAutoFocusMoveCallback(autoFocusMoveCallback);
        }
    }
    
    protected PointF getCenterIfNull(PointF pointF) {
        if (pointF == null) {
            pointF = new PointF(0.5f, 0.5f);
        }
        return pointF;
    }
    
    public SelesStillCamera(final Context context, final CameraConfigs.CameraFacing cameraFacing) {
        super(context, cameraFacing);
        this.d = false;
        this.h = 0.75f;
        this.o = null;
        this.p = false;
        if (ContextUtils.getScreenSize(context) != null && ContextUtils.getScreenSize(context).maxSide() < 1000) {
            this.h = 0.85f;
        }
        else {
            this.h = 0.75f;
        }
    }
    
    @Override
    protected void onInitConfig(final Camera camera) {
        if (camera == null) {
            return;
        }
        super.onInitConfig(camera);
        Camera.Parameters parameters;
        try {
            parameters = camera.getParameters();
        }
        catch (Exception ex) {
            return;
        }
        if (parameters == null) {
            return;
        }
        if (this.isUnifiedParameters()) {
            CameraHelper.unifiedParameters(parameters);
        }
        CameraHelper.setPreviewSize(this.getContext(), parameters, this.a(), this.getPreviewEffectScale(), this.getPreviewRatio());
        final TuSdkSize limitSize = TuSdkSize.create(parameters.getPreviewSize().width, parameters.getPreviewSize().height).limitSize();
        if (this.getOutputPictureRatio() == 0.0f && this.c == null) {
            CameraHelper.setPictureSize(this.getContext(), parameters, limitSize, limitSize.getRatioFloat());
        }
        else if (this.c != null) {
            CameraHelper.setPictureSize(this.getContext(), parameters, this.getOutputSize(), this.getOutputPictureRatio());
        }
        CameraHelper.setFocusMode(parameters, CameraHelper.focusModes);
        this.j = CameraHelper.focusModeType(parameters.getFocusMode());
        CameraHelper.setFlashMode(parameters, this.getFlashMode());
        CameraHelper.setAntibanding(parameters, this.getAntiBandingMode());
        if (Build.VERSION.SDK_INT >= 14) {
            CameraHelper.setFocusArea(parameters, this.getCenterIfNull(null), null, this.isBackFacingCameraPresent());
        }
        try {
            camera.setParameters(parameters);
        }
        catch (Exception ex2) {}
    }
    
    public void setPreviewRatio(final float m) {
        this.m = m;
    }
    
    public float getPreviewRatio() {
        return this.m;
    }
    
    public void setOutputPictureRatio(final float n) {
        this.n = n;
    }
    
    protected float getOutputPictureRatio() {
        return this.n;
    }
    
    @Override
    public void stopCameraCapture() {
        this.b = false;
        if (this.inputCamera() != null) {
            this.j = null;
        }
        this.cancelAutoFocusTimer();
        super.stopCameraCapture();
    }
    
    protected void onCapturePhotoStateChanged(final boolean b) {
    }
    
    @Override
    public ImageOrientation capturePhotoOrientation() {
        if (!this.isDisableMirrorFrontFacing() || this.isBackFacingCameraPresent() || !this.isHorizontallyMirrorFrontFacingCamera()) {
            return this.mOutputRotation;
        }
        return TuSdkCameraOrientationImpl.computerOutputOrientation(this.getContext(), this.inputCameraInfo(), this.isHorizontallyMirrorRearFacingCamera(), false, this.getOutputImageOrientation());
    }
    
    protected void onTakePictured(final byte[] array) {
        if (this.isAutoReleaseAfterCaptured()) {
            this.stopCameraCapture();
        }
        else {
            this.pauseCameraCapture();
        }
    }
    
    protected void onTakePictureFailed() {
        this.startCameraCapture();
    }
    
    protected Camera.ShutterCallback getShutterCallback() {
        return null;
    }
    
    protected Bitmap decodeToBitmapAtAsync(final byte[] array) throws IOException {
        return BitmapHelper.imageDecode(array, true);
    }
    
    @Override
    public void capturePhotoAsJPEGData(final CapturePhotoAsJPEGDataCallback capturePhotoAsJPEGDataCallback) {
        final boolean b = !this.isCapturing() || this.b;
        ThreadHelper.post(new Runnable() {
            @Override
            public void run() {
                if (b) {
                    if (capturePhotoAsJPEGDataCallback != null) {
                        capturePhotoAsJPEGDataCallback.onCapturePhotoAsJPEGData(null);
                    }
                    return;
                }
                SelesStillCamera.this.a(true);
            }
        });
        if (b) {
            return;
        }
        ThreadHelper.runThread(new Runnable() {
            @Override
            public void run() {
                SelesStillCamera.this.a(capturePhotoAsJPEGDataCallback);
            }
        });
    }
    
    @Override
    public void capturePhotoAsBitmap(final SelesOutInput selesOutInput, final CapturePhotoAsBitmapCallback capturePhotoAsBitmapCallback) {
        this.capturePhotoAsBitmap(selesOutInput, this.capturePhotoOrientation(), capturePhotoAsBitmapCallback);
    }
    
    @Override
    public void capturePhotoAsBitmap(final SelesOutInput selesOutInput, final ImageOrientation imageOrientation, final CapturePhotoAsBitmapCallback capturePhotoAsBitmapCallback) {
        this.capturePhotoAsJPEGData(new CapturePhotoAsJPEGDataCallback() {
            @Override
            public void onCapturePhotoAsJPEGData(final byte[] array) {
                if (array == null) {
                    if (capturePhotoAsBitmapCallback != null) {
                        capturePhotoAsBitmapCallback.onCapturePhotoAsBitmap(null);
                    }
                    return;
                }
                ThreadHelper.runThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SelesStillCamera.this.a(array, selesOutInput, imageOrientation, capturePhotoAsBitmapCallback);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });
    }
    
    private void a(final CapturePhotoAsJPEGDataCallback capturePhotoAsJPEGDataCallback) {
        try {
            this.inputCamera().takePicture(this.getShutterCallback(), (Camera.PictureCallback)null, (Camera.PictureCallback)new Camera.PictureCallback() {
                public void onPictureTaken(final byte[] array, final Camera camera) {
                    SelesStillCamera.this.a(false);
                    SelesStillCamera.this.onTakePictured(array);
                    if (capturePhotoAsJPEGDataCallback != null) {
                        capturePhotoAsJPEGDataCallback.onCapturePhotoAsJPEGData(array);
                    }
                }
            });
        }
        catch (RuntimeException ex) {
            TLog.e(ex, "takePictureAtAsync", new Object[0]);
            ThreadHelper.post(new Runnable() {
                @Override
                public void run() {
                    SelesStillCamera.this.a(false);
                    SelesStillCamera.this.onTakePictureFailed();
                    if (capturePhotoAsJPEGDataCallback != null) {
                        capturePhotoAsJPEGDataCallback.onCapturePhotoAsJPEGData(null);
                    }
                }
            });
        }
    }
    
    private void a(final byte[] array, final SelesOutInput selesOutInput, final ImageOrientation imageOrientation, final CapturePhotoAsBitmapCallback capturePhotoAsBitmapCallback) throws IOException {
        final Bitmap decodeToBitmapAtAsync = this.decodeToBitmapAtAsync(array);
        if (decodeToBitmapAtAsync == null) {
            if (capturePhotoAsBitmapCallback != null) {
                ThreadHelper.post(new Runnable() {
                    @Override
                    public void run() {
                        if (capturePhotoAsBitmapCallback != null) {
                            capturePhotoAsBitmapCallback.onCapturePhotoAsBitmap(null);
                        }
                    }
                });
            }
            return;
        }
        ThreadHelper.runThread(new Runnable() {
            @Override
            public void run() {
                SelesStillCamera.this.a(decodeToBitmapAtAsync, selesOutInput, imageOrientation, capturePhotoAsBitmapCallback);
            }
        });
    }
    
    private void a(Bitmap bitmap, final SelesOutInput selesOutInput, final ImageOrientation inputRotation, final CapturePhotoAsBitmapCallback capturePhotoAsBitmapCallback) {
        if (selesOutInput != null && bitmap != null) {
            bitmap = BitmapHelper.imageResize(bitmap, this.getOutputSize(), false, this.getOutputPictureRatio(), false);
            final TuSdkSize computerScaleSize = BitmapHelper.computerScaleSize(bitmap, this.getOutputSize(), false, false);
            final SelesPicture selesPicture = new SelesPicture(bitmap, false);
            selesPicture.setScaleSize(computerScaleSize);
            float n = this.getOutputPictureRatio();
            if (this.getRegionRatio() == 0.0f) {
                n = this.getOutputSize().getRatioFloat();
            }
            final TuSdkSize create = TuSdkSize.create(bitmap);
            if (create.maxSide() != this.getOutputSize().maxSide() || create.minSide() != this.getOutputSize().minSide()) {
                selesPicture.setOutputRect(RectHelper.computerMinMaxSideInRegionRatio(selesPicture.getScaleSize(), n));
            }
            selesPicture.setInputRotation(inputRotation);
            selesPicture.addTarget(selesOutInput, 0);
            selesPicture.processImage();
            bitmap = selesPicture.imageFromCurrentlyProcessedOutput();
        }
        Bitmap finalBitmap = bitmap;
        ThreadHelper.post(new Runnable() {
            @Override
            public void run() {
                if (capturePhotoAsBitmapCallback != null) {
                    capturePhotoAsBitmapCallback.onCapturePhotoAsBitmap(finalBitmap);
                }
            }
        });
    }
}
