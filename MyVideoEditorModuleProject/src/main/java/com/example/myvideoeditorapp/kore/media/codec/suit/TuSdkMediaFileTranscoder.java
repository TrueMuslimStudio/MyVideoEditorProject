// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit;

import android.media.MediaFormat;

import com.example.myvideoeditorapp.kore.api.extend.TuSdkAudioRender;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkMediaProgress;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkSurfaceRender;
import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;

import java.io.IOException;
import java.util.List;

public interface TuSdkMediaFileTranscoder
{
    void addInputDataSource(final TuSdkMediaDataSource p0);
    
    void addInputDataSouces(final List<TuSdkMediaDataSource> p0);
    
    void setOutputFilePath(final String p0);
    
    int setOutputVideoFormat(final MediaFormat p0);
    
    int setOutputAudioFormat(final MediaFormat p0);
    
    void setSurfaceRender(final TuSdkSurfaceRender p0);
    
    void setAudioRender(final TuSdkAudioRender p0);
    
    boolean run(final TuSdkMediaProgress p0) throws IOException;
    
    void stop();
}
