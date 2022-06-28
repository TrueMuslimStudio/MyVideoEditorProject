// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.record;

import android.media.MediaFormat;
import android.opengl.GLSurfaceView;

import com.example.myvideoeditorapp.kore.api.extend.TuSdkAudioRender;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkSurfaceRender;
import com.example.myvideoeditorapp.kore.media.codec.suit.TuSdkMediaFileRecorder;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.sources.SelesWatermark;

import java.io.File;

public interface TuSdkMediaRecordHub
{
    void setRecordListener(final TuSdkMediaRecordHubListener p0);
    
    void setWatermark(final SelesWatermark p0);
    
    void setOutputVideoFormat(final MediaFormat p0);
    
    void setOutputAudioFormat(final MediaFormat p0);
    
    void appendRecordSurface(final TuSdkRecordSurface p0);
    
    void setSurfaceRender(final TuSdkSurfaceRender p0);
    
    void setAudioRender(final TuSdkAudioRender p0);
    
    TuSdkMediaRecordHubStatus getState();
    
    boolean start(final File p0);
    
    void stop();
    
    boolean pause();
    
    boolean resume();
    
    void reset();
    
    void changeSpeed(final float p0);
    
    void changePitch(final float p0);
    
    void release();
    
    void addTarget(final SelesContext.SelesInput p0, final int p1);
    
    void removeTarget(final SelesContext.SelesInput p0);
    
    GLSurfaceView.Renderer getExtenalRenderer();
    
    void initInGLThread();
    
    void newFrameReadyInGLThread();
    
    public interface TuSdkMediaRecordHubListener extends TuSdkMediaFileRecorder.TuSdkMediaFileRecorderProgress
    {
        void onStatusChanged(final TuSdkMediaRecordHubStatus p0, final TuSdkMediaRecordHub p1);
    }
    
    public enum TuSdkMediaRecordHubStatus
    {
        UNINITIALIZED, 
        START, 
        STOP, 
        PREPARE_RECORD, 
        PREPARE_STOP, 
        START_RECORD, 
        PAUSE_RECORD, 
        RELEASED;
    }
}
