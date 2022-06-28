// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;

import com.example.myvideoeditorapp.kore.utils.hardware.CameraConfigs;

public interface TuSdkCameraBuilder
{
    Camera.CameraInfo getInfo();
    
    Camera getOrginCamera();
    
    Camera.Parameters getParameters();
    
    CameraConfigs.CameraFacing getFacing();
    
    boolean isBackFacingCameraPresent();
    
    boolean open(final CameraConfigs.CameraFacing p0);
    
    boolean open();
    
    void releaseCamera();
    
    boolean startPreview();
    
    void setPreviewTexture(final SurfaceTexture p0);
    
    void setPreviewCallbackWithBuffer(final Camera.PreviewCallback p0);
    
    void addCallbackBuffer(final byte[] p0);
}
