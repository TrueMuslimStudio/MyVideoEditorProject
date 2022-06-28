// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.decoder;

import android.media.MediaCodec;

import com.example.myvideoeditorapp.kore.media.codec.TuSdkMediaExtractor;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioInfo;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioResample;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioResampleHardImpl;
import com.example.myvideoeditorapp.kore.media.codec.decoder.TuSdkAudioFileDecoder;
import com.example.myvideoeditorapp.kore.media.codec.decoder.TuSdkDecoderListener;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioDecodecSync;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioDecodecSyncBase;
import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.tv.api.audio.preproc.mixer.TuSDKAudioRenderEntry;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class TuSDKAudioRenderDecoder extends TuSdkAudioDecodecSyncBase
{
    private TuSdkAudioFileDecoder a;
    private TuSdkAudioResample b;
    private TuSDKAudioRenderEntry c;
    private TuSdkAudioInfo d;
    private String e;
    private FileOutputStream f;
    
    public TuSDKAudioRenderDecoder(final TuSDKAudioRenderEntry c, final TuSdkAudioInfo d, final String e) {
        this.c = c;
        this.d = d;
        this.e = e;
        this.a();
        this.b();
        this.c();
    }
    
    private void a() {
        (this.a = new TuSdkAudioFileDecoder()).setMediaDataSource((TuSdkMediaDataSource)this.c);
        this.a.setMediaSync((TuSdkAudioDecodecSync)this);
    }
    
    private void b() {
        this.setAudioResample(this.b = (TuSdkAudioResample)new TuSdkAudioResampleHardImpl(this.d));
    }
    
    private void c() {
        try {
            this.f = new FileOutputStream(this.e);
        }
        catch (FileNotFoundException ex) {
            TLog.e((Throwable)ex);
        }
    }
    
    public void syncAudioDecodecInfo(final TuSdkAudioInfo tuSdkAudioInfo, final TuSdkMediaExtractor tuSdkMediaExtractor) {
        super.syncAudioDecodecInfo(tuSdkAudioInfo, tuSdkMediaExtractor);
        if (this.b != null) {
            this.b.changeFormat(tuSdkAudioInfo);
        }
    }
    
    public void syncAudioDecodecOutputBuffer(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo, final TuSdkAudioInfo tuSdkAudioInfo) throws IOException {
        super.syncAudioDecodecOutputBuffer(byteBuffer, bufferInfo, tuSdkAudioInfo);
    }
    
    public void syncAudioResampleOutputBuffer(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) throws IOException {
        try {
            if (this.a(bufferInfo.presentationTimeUs)) {
                this.f.getChannel().write(byteBuffer);
            }
        }
        catch (IOException ex) {
            TLog.e("%s out put raw file error!", new Object[] { "TuSDKAudioRenderDecoder" });
            TLog.e((Throwable)ex);
        }
        super.syncAudioResampleOutputBuffer(byteBuffer, bufferInfo);
    }
    
    private boolean a(final long n) {
        return this.c.getCutTimeRange() == null || this.c.getCutTimeRange().contains(n);
    }
    
    public void setDecodeListener(final TuSdkDecoderListener listener) {
        if (listener == null) {
            return;
        }
        this.a.setListener(listener);
    }
    
    public void start() {
        this.a.start();
    }
    
    public void release() {
        super.release();
    }
}
