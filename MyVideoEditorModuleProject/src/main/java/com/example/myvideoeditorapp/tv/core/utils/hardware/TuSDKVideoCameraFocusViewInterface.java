// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.utils.hardware;

import android.graphics.RectF;

import com.example.myvideoeditorapp.kore.media.camera.TuSdkCamera;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkFace;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkStillCameraAdapter;
import com.example.myvideoeditorapp.tv.core.components.camera.TuVideoFocusTouchViewBase;
import com.example.myvideoeditorapp.tv.core.seles.sources.SelesVideoCameraInterface;

import java.util.List;

public interface TuSDKVideoCameraFocusViewInterface
{
    void viewWillDestory();
    
    void setCamera(final SelesVideoCameraInterface p0);
    
    void setDisableFocusBeep(final boolean p0);
    
    void setDisableContinueFoucs(final boolean p0);
    
    void setRegionPercent(final RectF p0);
    
    void setGuideLineViewState(final boolean p0);
    
    void setEnableFilterConfig(final boolean p0);
    
    void cameraStateChanged(final SelesVideoCameraInterface p0, final TuSdkStillCameraAdapter.CameraState p1);
    
    void cameraStateChanged(final boolean p0, final TuSdkCamera p1, final TuSdkCamera.TuSdkCameraStatus p2);
    
    void notifyFilterConfigView(final SelesOutInput p0);
    
    void showRangeView();
    
    void setRangeViewFoucsState(final boolean p0);
    
    void setCameraFaceDetection(final List<TuSdkFace> p0, final TuSdkSize p1);
    
    void setEnableFaceFeatureDetection(final boolean p0);
    
    void setGestureListener(final TuVideoFocusTouchViewBase.GestureListener p0);
}
