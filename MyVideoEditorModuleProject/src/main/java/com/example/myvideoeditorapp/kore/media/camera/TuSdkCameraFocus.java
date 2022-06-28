// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.camera;

import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkFace;

import java.util.List;

public interface TuSdkCameraFocus
{
    void configure(final TuSdkCameraBuilder p0, final TuSdkCameraOrientation p1, final TuSdkCameraSize p2);
    
    void changeStatus(final TuSdkCamera.TuSdkCameraStatus p0);
    
    boolean allowFocusToShot();
    
    void autoFocus(final TuSdkCameraFocusListener p0);
    
    public interface TuSdkCameraFocusFaceListener
    {
        void onFocusFaceDetection(final List<TuSdkFace> p0, final TuSdkSize p1);
    }
    
    public interface TuSdkCameraFocusListener
    {
        void onFocusStart(final TuSdkCameraFocus p0);
        
        void onAutoFocus(final boolean p0, final TuSdkCameraFocus p1);
    }
}
