// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.api.extend;

import android.media.MediaCodec;

import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioInfo;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface TuSdkAudioRender
{
    boolean onAudioSliceRender(final ByteBuffer p0, final MediaCodec.BufferInfo p1, final TuSdkAudioRenderCallback p2) throws IOException;
    
    public interface TuSdkAudioRenderCallback
    {
        boolean isEncodec();
        
        TuSdkAudioInfo getAudioInfo();
        
        void returnRenderBuffer(final ByteBuffer p0, final MediaCodec.BufferInfo p1) throws IOException;
    }
}
