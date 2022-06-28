// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer;

import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaTimeSlice;
import android.graphics.RectF;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkMediaProgress;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkAudioRender;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkSurfaceRender;
import android.media.MediaFormat;

import java.io.IOException;
import java.util.List;
import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;

public interface TuSdkMediaFilesCuter
{
    void setMediaDataSource(final TuSdkMediaDataSource p0);
    
    void setMediaDataSources(final List<TuSdkMediaDataSource> p0);
    
    void setOutputFilePath(final String p0);
    
    int setOutputVideoFormat(final MediaFormat p0);
    
    int setOutputAudioFormat(final MediaFormat p0);
    
    void setSurfaceRender(final TuSdkSurfaceRender p0);
    
    void setAudioRender(final TuSdkAudioRender p0);
    
    boolean run(final TuSdkMediaProgress p0) throws IOException;
    
    void stop();
    
    TuSdkSize preferredOutputSize();
    
    void setOutputOrientation(final ImageOrientation p0);
    
    void setCanvasRect(final RectF p0);
    
    void setCropRect(final RectF p0);
    
    void setTimeSlice(final TuSdkMediaTimeSlice p0);
}
