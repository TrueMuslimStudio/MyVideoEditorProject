// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.utils.hardware;

import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import java.util.List;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import android.graphics.RectF;

public interface TuSdkVideoCameraExtendViewInterface
{
    void viewWillDestory();
    
    void setCamera(final TuSdkStillCameraInterface p0);
    
    void setEnableLongTouchCapture(final boolean p0);
    
    void setDisableFocusBeep(final boolean p0);
    
    void setDisableContinueFoucs(final boolean p0);
    
    void setRegionPercent(final RectF p0);
    
    void setGuideLineViewState(final boolean p0);
    
    void setEnableFilterConfig(final boolean p0);
    
    void cameraStateChanged(final TuSdkStillCameraInterface p0, final TuSdkStillCameraAdapter.CameraState p1);
    
    void notifyFilterConfigView(final SelesOutInput p0);
    
    void showRangeView();
    
    void setRangeViewFoucsState(final boolean p0);
    
    void setCameraFaceDetection(final List<TuSdkFace> p0, final TuSdkSize p1);
}
