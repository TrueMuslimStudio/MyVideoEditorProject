// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.camera;


import com.example.myvideoeditorapp.kore.utils.hardware.CameraConfigs;

public interface TuSdkCameraParameters
{
    void configure(final TuSdkCameraBuilder p0);
    
    void changeStatus(final TuSdkCamera.TuSdkCameraStatus p0);
    
    void setFlashMode(final CameraConfigs.CameraFlash p0);
    
    void setExposureCompensation(final int p0);
}
