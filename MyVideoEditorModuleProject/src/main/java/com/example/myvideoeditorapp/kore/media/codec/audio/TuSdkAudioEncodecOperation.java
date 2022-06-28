// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.audio;

import android.media.MediaCodec;

import java.io.IOException;
import java.nio.ByteBuffer;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkAudioRender;
import com.example.myvideoeditorapp.kore.media.codec.TuSdkEncodecOperation;

public interface TuSdkAudioEncodecOperation extends TuSdkEncodecOperation
{
    void setAudioRender(final TuSdkAudioRender p0);
    
    TuSdkAudioInfo getAudioInfo();
    
    int writeBuffer(final ByteBuffer p0, final MediaCodec.BufferInfo p1) throws IOException;
    
    void autoFillMuteData(final long p0, final long p1, final boolean p2) throws IOException;
    
    void autoFillMuteDataWithinEnd(final long p0, final long p1) throws IOException;
    
    void signalEndOfInputStream(final long p0) throws IOException;
}
