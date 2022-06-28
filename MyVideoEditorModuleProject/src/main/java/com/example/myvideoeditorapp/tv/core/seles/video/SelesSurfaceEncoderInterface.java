// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.seles.video;

import com.example.myvideoeditorapp.kore.utils.TuSdkWaterMarkOption;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.opengl.EGLContext;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.tv.core.encoder.video.TuSDKVideoDataEncoderInterface;

public interface SelesSurfaceEncoderInterface extends TuSDKVideoDataEncoderInterface, SelesContext.SelesInput
{
    void startRecording(final EGLContext p0, final SurfaceTexture p1);
    
    void stopRecording();
    
    void pausdRecording();
    
    boolean isRecording();
    
    boolean isPaused();
    
    void setCropRegion(final RectF p0);
    
    void setEnableHorizontallyFlip(final boolean p0);
    
    void updateWaterMark(final Bitmap p0, final int p1, final TuSdkWaterMarkOption.WaterMarkPosition p2);
    
    void destroy();
}
