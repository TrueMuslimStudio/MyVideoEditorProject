// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.audio;

import android.media.MediaCrypto;
import android.view.Surface;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaCodecImpl;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.media.codec.exception.TuSdkNoMediaTrackException;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaUtils;
import android.media.MediaCodec;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkAudioRender;
import android.media.MediaFormat;
import com.example.myvideoeditorapp.kore.media.codec.TuSdkMediaExtractor;
import com.example.myvideoeditorapp.kore.media.codec.TuSdkCodecOutput;

import java.io.IOException;
import java.nio.ByteBuffer;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaCodec;
import android.annotation.TargetApi;

@TargetApi(18)
public class TuSdkAudioDecodecOperationImpl implements TuSdkAudioDecodecOperation
{
    private int a;
    private TuSdkMediaCodec b;
    private boolean c;
    private ByteBuffer[] d;
    private final long e = 10000L;
    private TuSdkCodecOutput.TuSdkDecodecOutput f;
    private TuSdkMediaExtractor g;
    private MediaFormat h;
    private TuSdkAudioInfo i;
    private TuSdkAudioRender j;
    private boolean k;
    private TuSdkAudioRender.TuSdkAudioRenderCallback l;
    
    @Override
    public TuSdkAudioInfo getAudioInfo() {
        return this.i;
    }
    
    @Override
    public void setAudioRender(final TuSdkAudioRender j) {
        this.j = j;
    }
    
    public TuSdkAudioDecodecOperationImpl(final TuSdkCodecOutput.TuSdkDecodecOutput f) {
        this.a = -1;
        this.k = false;
        this.l = new TuSdkAudioRender.TuSdkAudioRenderCallback() {
            @Override
            public boolean isEncodec() {
                return false;
            }
            
            @Override
            public TuSdkAudioInfo getAudioInfo() {
                return TuSdkAudioDecodecOperationImpl.this.i;
            }
            
            @Override
            public void returnRenderBuffer(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) throws IOException {
                TuSdkAudioDecodecOperationImpl.this.a(byteBuffer, bufferInfo);
            }
        };
        if (f == null) {
            throw new NullPointerException(String.format("%s init failed, codecOutput is NULL", "TuSdkAudioDecodecOperationImpl"));
        }
        this.f = f;
    }
    
    @Override
    public void flush() {
        if (this.b == null || this.k) {
            return;
        }
        this.b.flush();
    }
    
    @Override
    public boolean decodecInit(final TuSdkMediaExtractor g) throws IOException {
        this.a = TuSdkMediaUtils.getMediaTrackIndex(g, "audio/");
        if (this.a < 0) {
            this.decodecException(new TuSdkNoMediaTrackException(String.format("%s decodecInit can not find media track: %s", "TuSdkAudioDecodecOperationImpl", "audio/")));
            TLog.e("%s Audio decodecInit mTrackIndex reulst false", "TuSdkAudioDecodecOperationImpl");
            return false;
        }
        this.h = g.getTrackFormat(this.a);
        g.selectTrack(this.a);
        if (!this.f.canSupportMediaInfo(this.a, this.h)) {
            TLog.e("%s decodecInit can not Support MediaInfo: %s", "TuSdkAudioDecodecOperationImpl", this.h);
            return false;
        }
        this.i = new TuSdkAudioInfo(this.h);
        this.b = TuSdkMediaCodecImpl.createDecoderByType(this.h.getString("mime"));
        if (this.b.configureError() != null || !this.b.configure(this.h, null, null, 0)) {
            this.decodecException(this.b.configureError());
            this.b = null;
            TLog.e("%s Audio decodecInit mMediaCodec reulst false", "TuSdkAudioDecodecOperationImpl");
            return false;
        }
        this.g = g;
        this.b.start();
        this.d = this.b.getOutputBuffers();
        this.c = false;
        return true;
    }
    
    @Override
    public boolean decodecProcessUntilEnd(final TuSdkMediaExtractor tuSdkMediaExtractor) throws IOException {
        final TuSdkMediaCodec b = this.b;
        if (b == null) {
            return true;
        }
        if (!this.c) {
            this.c = this.f.processExtractor(tuSdkMediaExtractor, b);
        }
        final MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        final int dequeueOutputBuffer = b.dequeueOutputBuffer(bufferInfo, 10000L);
        switch (dequeueOutputBuffer) {
            case -2: {
                this.f.outputFormatChanged(b.getOutputFormat());
                break;
            }
            case -1: {
                break;
            }
            case -3: {
                this.d = b.getOutputBuffers();
                break;
            }
            default: {
                if (dequeueOutputBuffer < 0) {
                    break;
                }
                if (bufferInfo.size > 0) {
                    final ByteBuffer byteBuffer = this.d[dequeueOutputBuffer];
                    if (this.j == null || !this.j.onAudioSliceRender(byteBuffer, bufferInfo, this.l)) {
                        this.f.processOutputBuffer(tuSdkMediaExtractor, this.a, byteBuffer, bufferInfo);
                    }
                }
                if (!this.k) {
                    b.releaseOutputBuffer(dequeueOutputBuffer, false);
                }
                this.f.updated(bufferInfo);
                break;
            }
        }
        if ((bufferInfo.flags & 0x4) != 0x0) {
            TLog.d("%s process Audio Buffer Stream end", "TuSdkAudioDecodecOperationImpl");
            return this.f.updatedToEOS(bufferInfo);
        }
        return false;
    }
    
    private void a(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) throws IOException {
        if (this.k || this.f == null) {
            return;
        }
        this.f.processOutputBuffer(this.g, this.a, byteBuffer, bufferInfo);
    }
    
    @Override
    public void decodecRelease() {
        this.k = true;
        if (this.b == null) {
            return;
        }
        this.b.stop();
        this.b.release();
        this.b = null;
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.decodecRelease();
        super.finalize();
    }
    
    @Override
    public void decodecException(final Exception ex) throws IOException {
        this.f.onCatchedException(ex);
    }
}
