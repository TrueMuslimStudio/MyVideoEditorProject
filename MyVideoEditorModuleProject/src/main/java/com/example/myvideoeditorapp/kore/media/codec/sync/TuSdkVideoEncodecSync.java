// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.sync;

import android.media.MediaCodec;

import com.example.myvideoeditorapp.kore.media.codec.TuSdkMediaMuxer;
import com.example.myvideoeditorapp.kore.media.codec.TuSdkMediaSync;
import com.example.myvideoeditorapp.kore.media.codec.video.TuSdkVideoInfo;

import java.nio.ByteBuffer;

public interface TuSdkVideoEncodecSync extends TuSdkMediaSync
{
    boolean isVideoEncodeCompleted();
    
    void syncEncodecVideoInfo(final TuSdkVideoInfo p0);
    
    void syncVideoEncodecOutputBuffer(final TuSdkMediaMuxer p0, final int p1, final ByteBuffer p2, final MediaCodec.BufferInfo p3);
    
    void syncVideoEncodecUpdated(final MediaCodec.BufferInfo p0);
    
    void syncVideoEncodecCompleted();
}
