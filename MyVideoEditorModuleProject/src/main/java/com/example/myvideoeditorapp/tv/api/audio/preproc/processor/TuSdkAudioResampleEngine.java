// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.api.audio.preproc.processor;

import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioResampleHardImpl;
import android.media.MediaCodec;

import java.io.IOException;
import java.nio.ByteBuffer;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioInfo;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioResampleSync;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioResample;

public class TuSdkAudioResampleEngine implements TuSdkAudioEngine
{
    private TuSdkAudioResample a;
    private TuSdkAudioResampleSync b;
    private TuSdKAudioEngineOutputBufferDelegate c;
    
    public TuSdkAudioResampleEngine(final TuSdkAudioInfo tuSdkAudioInfo) {
        this.b = (TuSdkAudioResampleSync)new TuSdkAudioResampleSync() {
            public void syncAudioResampleOutputBuffer(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) throws IOException {
                TuSdkAudioResampleEngine.this.processOutputBuffer(byteBuffer, bufferInfo);
            }
            
            public void release() {
            }
        };
        if (!this.a(tuSdkAudioInfo)) {
            throw new IllegalArgumentException(String.format("%s outputAudioInfo is error . audioInfo : %s", "TuSdkAudioResampleEngine", tuSdkAudioInfo));
        }
        (this.a = (TuSdkAudioResample)new TuSdkAudioResampleHardImpl(tuSdkAudioInfo)).setMediaSync(this.b);
    }
    
    @Override
    public void processInputBuffer(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) throws IOException {
        this.a.queueInputBuffer(byteBuffer, bufferInfo);
    }
    
    public void processOutputBuffer(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) throws IOException {
        if (this.c == null) {
            return;
        }
        this.c.onProcess(byteBuffer, bufferInfo);
    }
    
    @Override
    public void changeAudioInfo(final TuSdkAudioInfo tuSdkAudioInfo) {
        if (!this.a(tuSdkAudioInfo)) {
            throw new IllegalArgumentException(String.format("%s inputAudioInfo is error . audioInfo : %s", "TuSdkAudioResampleEngine", tuSdkAudioInfo));
        }
        this.a.changeFormat(tuSdkAudioInfo);
    }
    
    public void setOutputBufferDelegate(final TuSdKAudioEngineOutputBufferDelegate c) {
        this.c = c;
    }
    
    @Override
    public void reset() {
        this.a.reset();
    }
    
    @Override
    public void release() {
        this.a.release();
    }
    
    private boolean a(final TuSdkAudioInfo tuSdkAudioInfo) {
        return tuSdkAudioInfo != null && tuSdkAudioInfo.bitWidth != 0 && tuSdkAudioInfo.channelCount != 0 && tuSdkAudioInfo.sampleRate != 0;
    }
}
