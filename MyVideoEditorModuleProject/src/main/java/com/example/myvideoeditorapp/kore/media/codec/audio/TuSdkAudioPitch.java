// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.audio;

import android.media.MediaCodec;

import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioPitchSync;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface TuSdkAudioPitch
{
    void setMediaSync(final TuSdkAudioPitchSync p0);
    
    void changeFormat(final TuSdkAudioInfo p0);
    
    void changePitch(final float p0);
    
    void changeSpeed(final float p0);
    
    boolean needPitch();
    
    void reset();
    
    void flush();
    
    boolean queueInputBuffer(final ByteBuffer p0, final MediaCodec.BufferInfo p1) throws IOException;
    
    void release();
}
