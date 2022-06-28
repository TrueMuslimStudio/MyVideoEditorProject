// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit;

import android.graphics.RectF;
import android.media.MediaFormat;

import com.example.myvideoeditorapp.kore.api.extend.TuSdkAudioRender;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkMediaProgress;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkSurfaceRender;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaTimeSlice;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaTimeline;
import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;

import java.io.IOException;
import java.util.List;

public interface TuSdkMediaFileCuter
{
    void setMediaDataSource(final TuSdkMediaDataSource p0);
    
    void setOutputFilePath(final String p0);
    
    int setOutputVideoFormat(final MediaFormat p0);
    
    int setOutputAudioFormat(final MediaFormat p0);
    
    void setSurfaceRender(final TuSdkSurfaceRender p0);
    
    void setAudioRender(final TuSdkAudioRender p0);
    
    void setAudioMixerRender(final TuSdkAudioRender p0);
    
    boolean run(final TuSdkMediaProgress p0) throws IOException;
    
    void stop();
    
    void setOutputOrientation(final ImageOrientation p0);
    
    void setCanvasRect(final RectF p0);
    
    void setCropRect(final RectF p0);
    
    void setEnableClip(final boolean p0);
    
    void setOutputRatio(final float p0);
    
    void setOutputSize(final TuSdkSize p0);
    
    void setCanvasColor(final int p0);
    
    void setCanvasColor(final float p0, final float p1, final float p2, final float p3);
    
    void setTimeline(final TuSdkMediaTimeline p0);
    
    void setTimeSlices(final List<TuSdkMediaTimeSlice> p0);
    
    void setTimeSlice(final TuSdkMediaTimeSlice p0);
    
    void setTimeSlice(final long p0, final long p1);
    
    void setTimeSliceDuration(final long p0, final long p1);
    
    void setTimeSliceScaling(final float p0, final float p1);
    
    void setTimeSliceDurationScaling(final float p0, final float p1);
}
