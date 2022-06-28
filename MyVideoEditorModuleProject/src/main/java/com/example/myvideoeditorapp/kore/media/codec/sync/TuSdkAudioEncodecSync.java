// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.sync;

import android.media.MediaCodec;

import com.example.myvideoeditorapp.kore.media.codec.TuSdkMediaMuxer;
import com.example.myvideoeditorapp.kore.media.codec.TuSdkMediaSync;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioInfo;

import java.nio.ByteBuffer;

public interface TuSdkAudioEncodecSync extends TuSdkMediaSync
{
    boolean isAudioEncodeCompleted();
    
    void syncAudioEncodecInfo(final TuSdkAudioInfo p0);
    
    void syncAudioEncodecOutputBuffer(final TuSdkMediaMuxer p0, final int p1, final ByteBuffer p2, final MediaCodec.BufferInfo p3);
    
    void syncAudioEncodecUpdated(final MediaCodec.BufferInfo p0);
    
    void syncAudioEncodecCompleted();
}
