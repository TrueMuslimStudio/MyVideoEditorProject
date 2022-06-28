// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.api.audio.preproc.processor;

import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioPitchSoftImpl;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioPitchHardImpl;
import android.media.MediaCodec;

import java.io.IOException;
import java.nio.ByteBuffer;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioInfo;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioPitchSync;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioPitch;

public final class TuSdkAudioPitchEngine implements TuSdkAudioEngine
{
    private TuSdkAudioPitch a;
    private TuSdkSoundPitchType b;
    private TuSdkAudioEnginePitchTypeChangeDelegate c;
    private TuSdKAudioEngineOutputBufferDelegate d;
    private TuSdkAudioPitchSync e;
    
    public TuSdkAudioPitchEngine(final TuSdkAudioInfo tuSdkAudioInfo) {
        this(tuSdkAudioInfo, true);
    }
    
    public TuSdkAudioPitchEngine(TuSdkAudioInfo tuSdkAudioInfo, final boolean b) {
        this.b = TuSdkSoundPitchType.Normal;
        this.e = (TuSdkAudioPitchSync)new TuSdkAudioPitchSync() {
            public void syncAudioPitchOutputBuffer(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) throws IOException {
                TuSdkAudioPitchEngine.this.processOutputBuffer(byteBuffer, bufferInfo);
            }
            
            public void release() {
            }
        };
        if (tuSdkAudioInfo == null) {
            tuSdkAudioInfo = new TuSdkAudioInfo();
        }
        (this.a = this.a(tuSdkAudioInfo, b)).setMediaSync(this.e);
    }
    
    private TuSdkAudioPitch a(final TuSdkAudioInfo tuSdkAudioInfo, final boolean b) {
        if (b) {
            return (TuSdkAudioPitch)new TuSdkAudioPitchHardImpl(tuSdkAudioInfo);
        }
        return (TuSdkAudioPitch)new TuSdkAudioPitchSoftImpl(tuSdkAudioInfo);
    }
    
    @Override
    public void changeAudioInfo(final TuSdkAudioInfo tuSdkAudioInfo) {
        if (tuSdkAudioInfo == null) {
            TLog.e(" %s change the AudioInfo is null !!!", new Object[] { "TuSdkAudioPitchEngine" });
            return;
        }
        this.a.changeFormat(tuSdkAudioInfo);
    }
    
    public void setSoundPitchType(final TuSdkSoundPitchType b) {
        if (this.b == null) {
            return;
        }
        this.b = b;
        this.a.changeSpeed(b.a);
        this.a.changePitch(b.b);
        this.a(b);
    }
    
    public TuSdkSoundPitchType getSoundType() {
        return this.b;
    }
    
    public void setOutputBufferDelegate(final TuSdKAudioEngineOutputBufferDelegate d) {
        this.d = d;
    }
    
    public void setSoundTypeChangeListener(final TuSdkAudioEnginePitchTypeChangeDelegate c) {
        this.c = c;
    }
    
    @Override
    public void processInputBuffer(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) throws IOException {
        this.a.queueInputBuffer(byteBuffer, bufferInfo);
    }
    
    protected void processOutputBuffer(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) throws IOException {
        if (this.d == null) {
            return;
        }
        this.d.onProcess(byteBuffer, bufferInfo);
    }
    
    public void flush() {
        this.a.flush();
        this.a.reset();
    }
    
    @Override
    public void reset() {
        this.a.reset();
        this.setSoundPitchType(TuSdkSoundPitchType.Normal);
    }
    
    @Override
    public void release() {
        this.a.release();
    }
    
    private void a(final TuSdkSoundPitchType tuSdkSoundPitchType) {
        if (this.c == null) {
            return;
        }
        this.c.onSoundTypeChanged(tuSdkSoundPitchType);
    }
    
    public interface TuSdkAudioEnginePitchTypeChangeDelegate
    {
        void onSoundTypeChanged(final TuSdkSoundPitchType p0);
    }
    
    public enum TuSdkSoundPitchType
    {
        Normal(1.0f, 1.0f), 
        Monster(1.0f, 0.6f), 
        Uncle(1.0f, 0.8f), 
        Girl(1.0f, 1.5f), 
        Lolita(1.0f, 2.0f);
        
        float a;
        float b;
        
        private TuSdkSoundPitchType(final float a, final float b) {
            this.a = a;
            this.b = b;
        }
        
        public float getSpeed() {
            return this.a;
        }
        
        public float getPitch() {
            return this.b;
        }
    }
}
