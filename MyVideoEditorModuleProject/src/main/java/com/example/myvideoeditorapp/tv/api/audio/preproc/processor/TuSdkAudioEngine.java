// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.api.audio.preproc.processor;

import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioInfo;
import android.media.MediaCodec;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface TuSdkAudioEngine
{
    void processInputBuffer(final ByteBuffer p0, final MediaCodec.BufferInfo p1) throws IOException;
    
    void changeAudioInfo(final TuSdkAudioInfo p0);
    
    void reset();
    
    void release();
    
    public interface TuSdKAudioEngineOutputBufferDelegate
    {
        void onProcess(final ByteBuffer p0, final MediaCodec.BufferInfo p1) throws IOException;
    }
}
