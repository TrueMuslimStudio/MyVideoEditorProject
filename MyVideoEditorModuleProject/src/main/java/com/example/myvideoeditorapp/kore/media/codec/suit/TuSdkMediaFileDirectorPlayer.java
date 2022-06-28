// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit;

import android.opengl.GLSurfaceView;

import com.example.myvideoeditorapp.kore.api.extend.TuSdkAudioRender;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkFilterBridge;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkMediaPlayerListener;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkSurfaceDraw;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaTimeline;
import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;

import java.io.IOException;

public interface TuSdkMediaFileDirectorPlayer
{
    void setMediaDataSource(final TuSdkMediaDataSource p0);
    
    void setSurfaceDraw(final TuSdkSurfaceDraw p0);
    
    void setAudioRender(final TuSdkAudioRender p0);
    
    void setAudioMixerRender(final TuSdkAudioRender p0);
    
    void setListener(final TuSdkMediaPlayerListener p0);
    
    TuSdkFilterBridge getFilterBridge();
    
    GLSurfaceView.Renderer getExtenalRenderer();
    
    void setCanvasColor(final float p0, final float p1, final float p2, final float p3);
    
    void setCanvasColor(final int p0);
    
    boolean load(final boolean p0);
    
    void initInGLThread();
    
    void newFrameReadyInGLThread();
    
    void release();
    
    boolean isPause();
    
    void pause();
    
    void resume();
    
    void reset() throws IOException;
    
    long seekToPercentage(final float p0);
    
    void seekTo(final long p0);
    
    long durationUs();
    
    long outputTimeUs();
    
    void setEnableClip(final boolean p0);
    
    TuSdkSize setOutputRatio(final float p0);
    
    void setOutputSize(final TuSdkSize p0);
    
    void preview(final TuSdkMediaTimeline p0) throws IOException;
    
    int setVolume(final float p0);
}
