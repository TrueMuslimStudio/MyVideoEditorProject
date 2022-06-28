// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.camera;

import com.example.myvideoeditorapp.kore.struct.TuSdkSize;


public interface TuSdkCameraSize
{
    void configure(final TuSdkCameraBuilder p0);
    
    void changeStatus(final TuSdkCamera.TuSdkCameraStatus p0);
    
    TuSdkSize previewOptimalSize();
    
    int previewBufferLength();
    
    TuSdkSize getOutputSize();
}
