// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.decoder;

import android.media.MediaCodec;

import java.io.IOException;

public interface TuSdkDecoderListener
{
    void onDecoderUpdated(final MediaCodec.BufferInfo p0);
    
    void onDecoderCompleted(final Exception p0) throws IOException;
}
