// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.audio;

import android.media.MediaCodec;

import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioResampleSync;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface TuSdkAudioResample
{
    void setMediaSync(final TuSdkAudioResampleSync p0);
    
    void changeFormat(final TuSdkAudioInfo p0);
    
    void changeSpeed(final float p0);
    
    void changeSequence(final boolean p0);
    
    void setStartPrefixTimeUs(final long p0);
    
    boolean needResample();
    
    void reset();
    
    void flush();
    
    long getLastInputTimeUs();
    
    long getPrefixTimeUs();
    
    boolean queueInputBuffer(final ByteBuffer p0, final MediaCodec.BufferInfo p1) throws IOException;
    
    void release();
}
