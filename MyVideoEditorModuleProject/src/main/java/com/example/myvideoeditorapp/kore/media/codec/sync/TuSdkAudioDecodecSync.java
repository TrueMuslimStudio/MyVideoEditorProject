// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.sync;

import android.media.MediaCodec;

import com.example.myvideoeditorapp.kore.media.codec.TuSdkMediaExtractor;
import com.example.myvideoeditorapp.kore.media.codec.TuSdkMediaSync;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioInfo;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaCodec;

import java.io.IOException;
import java.nio.ByteBuffer;


public interface TuSdkAudioDecodecSync extends TuSdkMediaSync
{
    void syncAudioDecodeCompleted();
    
    boolean isAudioDecodeCompleted();
    
    boolean isAudioDecodeCrashed();
    
    boolean hasAudioDecodeTrack();
    
    void syncAudioDecodeCrashed(final Exception p0);
    
    void syncAudioDecodecInfo(final TuSdkAudioInfo p0, final TuSdkMediaExtractor p1);
    
    boolean syncAudioDecodecExtractor(final TuSdkMediaExtractor p0, final TuSdkMediaCodec p1);
    
    void syncAudioDecodecOutputBuffer(final ByteBuffer p0, final MediaCodec.BufferInfo p1, final TuSdkAudioInfo p2) throws IOException;
    
    void syncAudioDecodecUpdated(final MediaCodec.BufferInfo p0);
}
