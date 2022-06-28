// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.seles.sources;

import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.seles.sources.SelesBaseCameraInterface;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkStillCameraAdapter;

public interface SelesVideoCameraInterface extends SelesBaseCameraInterface
{
    TuSdkStillCameraAdapter.CameraState getState();
    
    void setRendererFPS(final int p0);
    
    void switchFilter(final String p0);
    
    int getDeviceAngle();
    
    void updateFaceFeatures(final FaceAligment[] p0, final float p1);
}
