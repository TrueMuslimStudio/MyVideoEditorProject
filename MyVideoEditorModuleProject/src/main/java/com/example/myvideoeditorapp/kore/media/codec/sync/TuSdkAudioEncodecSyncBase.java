// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.sync;

import android.media.MediaCodec;

import com.example.myvideoeditorapp.kore.media.codec.TuSdkMediaMuxer;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioInfo;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioResample;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioResampleHardImpl;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkCodecCapabilities;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaUtils;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;

import java.nio.ByteBuffer;

public abstract class TuSdkAudioEncodecSyncBase implements TuSdkAudioEncodecSync
{
    protected boolean mReleased;
    protected TuSdkAudioInfo mAudioInfo;
    private TuSdkAudioResample a;
    private boolean b;
    private long c;
    
    public TuSdkAudioEncodecSyncBase() {
        this.mReleased = false;
        this.b = false;
        this.c = 0L;
    }
    
    public TuSdkAudioResample getAudioResample() {
        return this.a;
    }
    
    @Override
    public boolean isAudioEncodeCompleted() {
        return this.b;
    }
    
    @Override
    public void release() {
        this.mReleased = true;
        if (this.a != null) {
            this.a.release();
            this.a = null;
        }
    }
    
    private long a(final long n) {
        if (this.mAudioInfo == null) {
            return 0L;
        }
        return n * 1024000000L / this.mAudioInfo.sampleRate;
    }
    
    public long lastStandardPtsUs() {
        return this.a(this.c);
    }
    
    public long nextStandardPtsUs() {
        return this.a(this.c + 1L);
    }
    
    protected long getAndAddCountPtsUs() {
        final long lastStandardPtsUs = this.lastStandardPtsUs();
        ++this.c;
        return lastStandardPtsUs;
    }
    
    protected long getAndAddCountPtsUs(final long n) {
        long andAddCountPtsUs = -1L;
        while (this.nextStandardPtsUs() < n) {
            andAddCountPtsUs = this.getAndAddCountPtsUs();
        }
        return andAddCountPtsUs;
    }
    
    protected boolean isInterrupted() {
        return ThreadHelper.isInterrupted() || this.mReleased;
    }
    
    @Override
    public void syncAudioEncodecInfo(final TuSdkAudioInfo mAudioInfo) {
        this.mAudioInfo = mAudioInfo;
        if (this.a == null && mAudioInfo != null) {
            this.a = new TuSdkAudioResampleHardImpl(mAudioInfo);
        }
    }
    
    @Override
    public void syncAudioEncodecOutputBuffer(final TuSdkMediaMuxer tuSdkMediaMuxer, final int n, final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) {
        TuSdkMediaUtils.processOutputBuffer(tuSdkMediaMuxer, n, byteBuffer, bufferInfo);
    }
    
    @Override
    public void syncAudioEncodecUpdated(final MediaCodec.BufferInfo bufferInfo) {
        if (TLog.LOG_AUDIO_ENCODEC_INFO) {
            TuSdkCodecCapabilities.logBufferInfo(String.format("%s syncAudioEncodecUpdated", "TuSdkAudioEncodecSyncBase"), bufferInfo);
        }
    }
    
    @Override
    public void syncAudioEncodecCompleted() {
        if (TLog.LOG_AUDIO_ENCODEC_INFO) {
            TLog.d("%s syncAudioEncodecCompleted", "TuSdkAudioEncodecSyncBase");
        }
        this.b = true;
    }
}
