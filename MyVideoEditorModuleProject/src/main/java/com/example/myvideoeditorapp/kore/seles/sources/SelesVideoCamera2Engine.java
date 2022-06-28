// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.sources;

import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;
import android.graphics.SurfaceTexture;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;

public interface SelesVideoCamera2Engine
{
    boolean canInitCamera();
    
    boolean onInitCamera();
    
    TuSdkSize previewOptimalSize();
    
    void onCameraWillOpen(final SurfaceTexture p0);
    
    void onCameraStarted();
    
    ImageOrientation previewOrientation();
}
