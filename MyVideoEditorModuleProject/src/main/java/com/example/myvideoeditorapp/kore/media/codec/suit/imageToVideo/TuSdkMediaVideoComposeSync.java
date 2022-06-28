// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.imageToVideo;

import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkEncodeSurface;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioEncodecSync;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioEncodecSyncBase;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkMediaEncodecSync;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkVideoEncodecSync;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkVideoEncodecSyncBase;
import com.example.myvideoeditorapp.kore.media.record.TuSdkRecordSurface;

public class TuSdkMediaVideoComposeSync implements TuSdkMediaEncodecSync
{
    private _VideoEncodecSync a;
    private _AudioEncodecSync b;
    private boolean c;
    
    public TuSdkMediaVideoComposeSync() {
        this.c = false;
    }
    
    @Override
    public TuSdkAudioEncodecSync getAudioEncodecSync() {
        if (this.b == null) {
            this.b = new _AudioEncodecSync();
        }
        return this.b;
    }
    
    @Override
    public TuSdkVideoEncodecSync getVideoEncodecSync() {
        if (this.a == null) {
            this.a = new _VideoEncodecSync();
        }
        return this.a;
    }
    
    @Override
    public void release() {
        if (this.c) {
            return;
        }
        this.c = true;
        if (this.b != null) {
            this.b.release();
            this.b = null;
        }
        if (this.a != null) {
            this.a.release();
            this.a = null;
        }
    }
    
    public void syncVideoEncodecDrawFrame(final long n, final boolean b, final TuSdkRecordSurface tuSdkRecordSurface, final TuSdkEncodeSurface tuSdkEncodeSurface) {
        this.a.syncVideoEncodecDrawFrame(n, b, tuSdkRecordSurface, tuSdkEncodeSurface);
    }
    
    class _AudioEncodecSync extends TuSdkAudioEncodecSyncBase
    {
    }
    
    class _VideoEncodecSync extends TuSdkVideoEncodecSyncBase
    {
        @Override
        protected boolean isLastDecodeFrame(final long n) {
            return true;
        }
    }
}
