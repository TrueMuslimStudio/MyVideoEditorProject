// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.camera;


import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;

public interface TuSdkCameraOrientation
{
    void configure(final TuSdkCameraBuilder p0);
    
    void changeStatus(final TuSdkCamera.TuSdkCameraStatus p0);
    
    ImageOrientation previewOrientation();
    
    ImageOrientation captureOrientation();
}
