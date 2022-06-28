// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.opengl.EGLContext;

import com.example.myvideoeditorapp.kore.api.extend.TuSdkAudioRender;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkFilterBridge;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkSurfaceRender;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioInfo;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaTimeline;
import com.example.myvideoeditorapp.kore.seles.sources.SelesWatermark;
import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface TuSdkMediaFileRecorder
{
    void setRecordProgress(final TuSdkMediaFileRecorderProgress p0);
    
    void setOutputFilePath(final String p0);
    
    void setWatermark(final SelesWatermark p0);
    
    int setOutputVideoFormat(final MediaFormat p0);
    
    int setOutputAudioFormat(final MediaFormat p0);
    
    TuSdkAudioInfo getOutputAudioInfo();
    
    TuSdkFilterBridge getFilterBridge();
    
    void setFilterBridge(final TuSdkFilterBridge p0);
    
    void disconnect();
    
    void setSurfaceRender(final TuSdkSurfaceRender p0);
    
    void setAudioRender(final TuSdkAudioRender p0);
    
    void changeSpeed(final float p0);
    
    boolean startRecord(final EGLContext p0) throws IOException;
    
    void stopRecord();
    
    void pauseRecord();
    
    void resumeRecord();
    
    void newFrameReadyInGLThread(final long p0);
    
    void newFrameReadyWithAudio(final ByteBuffer p0, final MediaCodec.BufferInfo p1) throws IOException;
    
    void release();
    
    public interface TuSdkMediaFileRecorderProgress
    {
        void onProgress(final long p0, final TuSdkMediaDataSource p1);
        
        void onCompleted(final Exception p0, final TuSdkMediaDataSource p1, final TuSdkMediaTimeline p2) throws IOException;
    }
}
