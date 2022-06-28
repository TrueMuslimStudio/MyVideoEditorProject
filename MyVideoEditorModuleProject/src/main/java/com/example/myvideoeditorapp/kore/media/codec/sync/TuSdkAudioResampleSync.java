// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.sync;

import android.media.MediaCodec;

import com.example.myvideoeditorapp.kore.media.codec.TuSdkMediaSync;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface TuSdkAudioResampleSync extends TuSdkMediaSync
{
    void syncAudioResampleOutputBuffer(final ByteBuffer p0, final MediaCodec.BufferInfo p1) throws IOException;
}
