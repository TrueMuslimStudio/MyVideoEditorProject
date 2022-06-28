// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.seles.sources;

import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaTimeSlice;

import java.io.IOException;
import java.util.ArrayList;
import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;
import com.example.myvideoeditorapp.tv.video.editor.TuSdkMediaTimeEffect;

import android.view.ViewGroup;

public interface TuSdkEditorPlayer
{
    void setPreviewContainer(final ViewGroup p0);
    
    void setDataSource(final TuSdkMediaDataSource p0);
    
    void setEditTimeSlice(final ArrayList<TuSdkMediaTimeSlice> p0);
    
    void enableFirstFramePause(final boolean p0);
    
    void setCanvasColor(final float p0, final float p1, final float p2, final float p3);
    
    void setCanvasColor(final int p0);
    
    void setBackGround(final int p0);
    
    void startPreview();
    
    void pausePreview();
    
    boolean isPause();
    
    void seekTimeUs(final long p0);
    
    void seekOutputTimeUs(final long p0);
    
    void seekInputTimeUs(final long p0);
    
    long getCurrentTimeUs();
    
    long getTotalTimeUs();
    
    long getCurrentInputTimeUs();
    
    long getCurrentOutputTimeUs();
    
    long getOutputTotalTimeUS();
    
    long getInputTotalTimeUs();
    
    void setOutputRatio(final float p0, final boolean p1);
    
    void setOutputSize(final TuSdkSize p0, final boolean p1);
    
    void setVideoSoundVolume(final float p0);
    
    void addProgressListener(final TuSdkProgressListener p0);
    
    void removeProgressListener(final TuSdkProgressListener p0);
    
    void removeAllProgressListener();
    
    void addPreviewSizeChangeListener(final TuSdkPreviewSizeChangeListener p0);
    
    void removePreviewSizeChangeListener(final TuSdkPreviewSizeChangeListener p0);
    
    void removeAllPreviewSizeChangeListener();
    
    void setTimeEffect(final TuSdkMediaTimeEffect p0) throws IOException;
    
    void clearTimeEffect() throws IOException;
    
    boolean isReversing();
    
    void destroy();
    
    public interface TuSdkPreviewSizeChangeListener
    {
        void onPreviewSizeChanged(final TuSdkSize p0);
    }
    
    public interface TuSdkProgressListener
    {
        void onStateChanged(final int p0);
        
        void onProgress(final long p0, final long p1, final float p2);
    }
}
