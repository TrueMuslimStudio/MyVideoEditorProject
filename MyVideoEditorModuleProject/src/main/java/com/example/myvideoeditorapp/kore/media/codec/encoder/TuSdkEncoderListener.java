// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.encoder;

import android.media.MediaCodec;

public interface TuSdkEncoderListener
{
    void onEncoderUpdated(final MediaCodec.BufferInfo p0);
    
    void onEncoderCompleted(final Exception p0);
}
