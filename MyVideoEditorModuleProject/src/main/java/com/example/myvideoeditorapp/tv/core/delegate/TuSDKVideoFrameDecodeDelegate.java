// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.delegate;

import android.media.MediaCodec;

import com.example.myvideoeditorapp.tv.core.decoder.TuSDKMediaDecoder;
import com.example.myvideoeditorapp.tv.core.decoder.TuSDKVideoInfo;

public interface TuSDKVideoFrameDecodeDelegate
{
    void onDecoderError(final TuSDKMediaDecoder.TuSDKMediaDecoderError p0);
    
    void onVideoInfoReady(final TuSDKVideoInfo p0);
    
    void onProgressChanged(final long p0, final float p1, final float p2);
    
    void onVideoDecoderNewFrameAvailable(final byte[] p0, final MediaCodec.BufferInfo p1);
    
    void onDecoderComplete();
}
