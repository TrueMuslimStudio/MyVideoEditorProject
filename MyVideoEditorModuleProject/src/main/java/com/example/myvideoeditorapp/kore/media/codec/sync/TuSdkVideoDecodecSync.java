// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.sync;

import android.media.MediaCodec;

import com.example.myvideoeditorapp.kore.media.codec.TuSdkMediaExtractor;
import com.example.myvideoeditorapp.kore.media.codec.TuSdkMediaSync;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaCodec;
import com.example.myvideoeditorapp.kore.media.codec.video.TuSdkVideoInfo;

import java.io.IOException;
import java.nio.ByteBuffer;


public interface TuSdkVideoDecodecSync extends TuSdkMediaSync
{
    void syncVideoDecodeCompleted();
    
    boolean isVideoDecodeCompleted();
    
    boolean isVideoDecodeCrashed();
    
    boolean hasVideoDecodeTrack();
    
    void syncVideoDecodeCrashed(final Exception p0);
    
    void syncVideoDecodecInfo(final TuSdkVideoInfo p0, final TuSdkMediaExtractor p1) throws IOException;
    
    boolean syncVideoDecodecExtractor(final TuSdkMediaExtractor p0, final TuSdkMediaCodec p1) throws IOException;
    
    void syncVideoDecodecOutputBuffer(final ByteBuffer p0, final MediaCodec.BufferInfo p1, final TuSdkVideoInfo p2);
    
    void syncVideoDecodecUpdated(final MediaCodec.BufferInfo p0);
    
    long calcInputTimeUs(final long p0);
    
    long calcEffectFrameTimeUs(final long p0);
}
