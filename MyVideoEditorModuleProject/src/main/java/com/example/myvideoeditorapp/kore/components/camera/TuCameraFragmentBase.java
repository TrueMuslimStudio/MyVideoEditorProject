// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.camera;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.myvideoeditorapp.kore.TuSdk;
import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.TuSdkResult;
import com.example.myvideoeditorapp.kore.activity.TuResultFragment;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import com.example.myvideoeditorapp.kore.utils.hardware.CameraConfigs;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkStillCameraAdapter;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkStillCameraInterface;
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;
import com.example.myvideoeditorapp.kore.utils.image.RatioType;

import java.io.IOException;

public abstract class TuCameraFragmentBase extends TuResultFragment
{
    private TuSdkStillCameraInterface a;
    private int b;
    private int c;
    private int d;
    private TuSdkStillCameraInterface.TuSdkStillCameraListener e;
    
    public TuCameraFragmentBase() {
        this.c = 31;
        this.e = new TuSdkStillCameraInterface.TuSdkStillCameraListener() {
            @Override
            public void onStillCameraStateChanged(final TuSdkStillCameraInterface tuSdkStillCameraInterface, final TuSdkStillCameraAdapter.CameraState cameraState) {
                TuCameraFragmentBase.this.onCameraStateChangedImpl(tuSdkStillCameraInterface, cameraState);
                if (cameraState != TuSdkStillCameraAdapter.CameraState.StateStarted) {
                    return;
                }
                StatisticsManger.appendComponent(tuSdkStillCameraInterface.isFrontFacingCameraPresent() ? ComponentActType.camera_action_faceing_front : ComponentActType.camera_action_faceing_back);
            }
            
            @Override
            public void onStillCameraTakedPicture(final TuSdkStillCameraInterface tuSdkStillCameraInterface, final TuSdkResult tuSdkResult) {
                TuCameraFragmentBase.this.onCameraTakedPictureImpl(tuSdkStillCameraInterface, tuSdkResult);
                StatisticsManger.appendComponent(ComponentActType.camera_action_take_picture);
            }
            
            @Override
            public void onFilterChanged(final SelesOutInput selesOutInput) {
                TuCameraFragmentBase.this.onFilterChanged(selesOutInput);
            }
        };
    }
    
    public TuSdkStillCameraInterface getCamera() {
        return this.a;
    }
    
    public abstract float getCameraViewRatio();
    
    public abstract RelativeLayout getCameraView();
    
    public abstract CameraConfigs.CameraFacing getAvPostion();
    
    protected abstract void configCamera(final TuSdkStillCameraInterface p0);
    
    protected abstract void onCameraStateChangedImpl(final TuSdkStillCameraInterface p0, final TuSdkStillCameraAdapter.CameraState p1);
    
    protected abstract void onCameraTakedPictureImpl(final TuSdkStillCameraInterface p0, final TuSdkResult p1);
    
    @Override
    protected void loadView(final ViewGroup viewGroup) {
    }
    
    @Override
    protected void viewDidLoad(final ViewGroup viewGroup) {
        StatisticsManger.appendComponent(ComponentActType.cameraFragment);
    }
    
    protected void initCameraView() {
        this.a = TuSdk.camera((Context)this.getActivity(), this.getAvPostion(), this.getCameraView());
        this.addOrientationListener();
        this.a.setCameraListener(this.e);
        this.configCamera(this.a);
        this.a.startCameraCapture();
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this.a != null) {
            this.a.destroy();
            this.a = null;
        }
    }
    
    protected void onFilterChanged(final SelesOutInput selesOutInput) {
    }
    
    @Override
    protected void asyncProcessingIfNeedSave(final TuSdkResult tuSdkResult) throws IOException {
        if (this.getWaterMarkOption() != null) {
            final Bitmap image = tuSdkResult.image;
            tuSdkResult.image = this.addWaterMarkToImage(tuSdkResult.image);
            BitmapHelper.recycled(image);
        }
        super.asyncProcessingIfNeedSave(tuSdkResult);
    }
    
    @TargetApi(23)
    public String[] getRequiredPermissions() {
        return new String[] { "android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_NETWORK_STATE", "android.permission.ACCESS_FINE_LOCATION" };
    }
    
    protected void handleFlashModel(final CameraConfigs.CameraFlash flashMode) {
        if (flashMode == null) {
            return;
        }
        long n = ComponentActType.camera_action_flash_auto;
        if (flashMode == CameraConfigs.CameraFlash.On) {
            n = ComponentActType.camera_action_flash_on;
        }
        else if (flashMode == CameraConfigs.CameraFlash.Off) {
            n = ComponentActType.camera_action_flash_off;
        }
        if (this.a != null) {
            this.a.setFlashMode(flashMode);
        }
        StatisticsManger.appendComponent(n);
    }
    
    protected void handleCaptureButton() {
        if (this.a != null) {
            this.a.captureImage();
        }
    }
    
    protected void handleCaptureWithVolume() {
        if (this.a != null) {
            this.a.captureImage();
        }
        StatisticsManger.appendComponent(ComponentActType.camera_action_capture_with_volume);
    }
    
    protected void handleSwitchButton() {
        if (this.a != null) {
            this.a.rotateCamera();
        }
    }
    
    protected boolean handleSwitchFilter(final String s) {
        if (this.a != null) {
            this.a.switchFilter(s);
            return true;
        }
        return false;
    }
    
    protected void handleCloseButton() {
        this.dismissActivityWithAnim();
    }
    
    public final int getRatioType() {
        return this.c;
    }
    
    public final void setRatioType(final int c) {
        this.c = c;
        if (this.b == 0) {
            this.b = RatioType.radioType(TuSdkContext.getScreenSize().minMaxRatio());
        }
        if (this.b != 1 && this.b == (this.b & c)) {
            this.c = ((c | 0x1) ^ this.b);
        }
    }
    
    public float getCurrentRatio() {
        if (this.getCameraViewRatio() > 0.0f) {
            return this.getCameraViewRatio();
        }
        if (this.d > 0) {
            return RatioType.ratio(this.d);
        }
        return 0.0f;
    }
    
    public int getCurrentRatioType() {
        return this.d;
    }
    
    protected float getPreviewOffsetTopPercent(final int n) {
        return -1.0f;
    }
    
    protected void setCurrentRatioType(final int d) {
        long n = 0L;
        switch (d) {
            case 16: {
                n = ComponentActType.camera_action_ratio_9_16;
                break;
            }
            case 8: {
                n = ComponentActType.camera_action_ratio_3_4;
                break;
            }
            case 4: {
                n = ComponentActType.camera_action_ratio_2_3;
                break;
            }
            case 2: {
                n = ComponentActType.camera_action_ratio_1_1;
                break;
            }
            default: {
                n = ComponentActType.camera_action_ratio_orgin;
                break;
            }
        }
        this.d = d;
        StatisticsManger.appendComponent(n);
    }
    
    protected void handleCameraRatio() {
        if (this.a == null) {
            return;
        }
        final int nextRatioType = RatioType.nextRatioType(this.getRatioType(), this.d);
        this.setCurrentRatioType(nextRatioType);
        this.a.adapter().getRegionHandler().setOffsetTopPercent(this.getPreviewOffsetTopPercent(this.d));
        this.a.adapter().changeRegionRatio(RatioType.ratio(nextRatioType));
        this.getCamera().setOutputPictureRatio(RatioType.ratio(nextRatioType));
    }
    
    protected void handleGuideLineButton() {
        if (this.a == null) {
            return;
        }
        this.a.adapter().setDisplayGuideLine(!this.a.adapter().isDisplayGuideLine());
    }
}
