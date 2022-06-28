// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.encoder;

import android.media.MediaCodec;
import android.media.MediaFormat;

import com.example.myvideoeditorapp.kore.api.extend.TuSdkAudioRender;
import com.example.myvideoeditorapp.kore.media.codec.TuSdkCodecOutput;
import com.example.myvideoeditorapp.kore.media.codec.TuSdkMediaMuxer;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioEncodecOperation;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioEncodecOperationImpl;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioInfo;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaUtils;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioEncodecSync;
import com.example.myvideoeditorapp.kore.utils.TLog;

import java.io.IOException;
import java.nio.ByteBuffer;

public class TuSdkAudioEncoder
{
    private int a;
    private TuSdkAudioEncodecOperation b;
    private TuSdkEncoderListener c;
    private TuSdkAudioEncodecSync d;
    private TuSdkAudioRender e;
    private TuSdkCodecOutput.TuSdkEncodecOutput f;
    
    public TuSdkAudioEncoder() {
        this.a = -1;
        this.f = new TuSdkCodecOutput.TuSdkEncodecOutput() {
            @Override
            public void outputFormatChanged(final MediaFormat mediaFormat) {
                TLog.d("%s encodec Audio outputFormatChanged: %s", "TuSdkAudioEncoder", mediaFormat);
                if (TuSdkAudioEncoder.this.d != null && TuSdkAudioEncoder.this.b != null) {
                    TuSdkAudioEncoder.this.d.syncAudioEncodecInfo(TuSdkAudioEncoder.this.b.getAudioInfo());
                }
            }
            
            @Override
            public void processOutputBuffer(final TuSdkMediaMuxer tuSdkMediaMuxer, final int n, final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) {
                if (TuSdkAudioEncoder.this.d != null) {
                    TuSdkAudioEncoder.this.d.syncAudioEncodecOutputBuffer(tuSdkMediaMuxer, n, byteBuffer, bufferInfo);
                }
                else {
                    TuSdkMediaUtils.processOutputBuffer(tuSdkMediaMuxer, n, byteBuffer, bufferInfo);
                }
            }
            
            @Override
            public void updated(final MediaCodec.BufferInfo bufferInfo) {
                if (TuSdkAudioEncoder.this.d != null) {
                    TuSdkAudioEncoder.this.d.syncAudioEncodecUpdated(bufferInfo);
                }
                TuSdkAudioEncoder.this.c.onEncoderUpdated(bufferInfo);
            }
            
            @Override
            public boolean updatedToEOS(final MediaCodec.BufferInfo bufferInfo) {
                if (TuSdkAudioEncoder.this.d != null) {
                    TuSdkAudioEncoder.this.d.syncAudioEncodecCompleted();
                }
                TuSdkAudioEncoder.this.c.onEncoderCompleted(null);
                return true;
            }
            
            @Override
            public void onCatchedException(final Exception ex) {
                if (TuSdkAudioEncoder.this.d != null) {
                    TuSdkAudioEncoder.this.d.syncAudioEncodecCompleted();
                }
                TuSdkAudioEncoder.this.c.onEncoderCompleted(ex);
            }
        };
    }
    
    public void setListener(final TuSdkEncoderListener c) {
        if (c == null) {
            TLog.w("%s setListener can not empty.", "TuSdkAudioEncoder");
            return;
        }
        if (this.a != -1) {
            TLog.w("%s setListener need before prepare.", "TuSdkAudioEncoder");
            return;
        }
        this.c = c;
    }
    
    public int setOutputFormat(final MediaFormat mediaFormat) {
        if (this.a != -1) {
            TLog.w("%s setOutputFormat need before prepare.", "TuSdkAudioEncoder");
            return -1;
        }
        final TuSdkAudioEncodecOperationImpl b = new TuSdkAudioEncodecOperationImpl(this.f);
        final int setMediaFormat = b.setMediaFormat(mediaFormat);
        if (setMediaFormat != 0) {
            TLog.w("%s setOutputFormat has a error code: %d, %s", "TuSdkAudioEncoder", setMediaFormat, mediaFormat);
            return setMediaFormat;
        }
        (this.b = b).setAudioRender(this.e);
        return 0;
    }
    
    public TuSdkAudioInfo getAudioInfo() {
        if (this.b == null) {
            return null;
        }
        return this.b.getAudioInfo();
    }
    
    public TuSdkAudioEncodecOperation getOperation() {
        if (this.b == null) {
            TLog.w("%s getOperation need setOutputFormat first", "TuSdkAudioEncoder");
        }
        return this.b;
    }
    
    public void setMediaSync(final TuSdkAudioEncodecSync d) {
        this.d = d;
    }
    
    public void setAudioRender(final TuSdkAudioRender tuSdkAudioRender) {
        this.e = tuSdkAudioRender;
        if (this.b != null) {
            this.b.setAudioRender(tuSdkAudioRender);
        }
    }
    
    public void release() {
        if (this.a == 1) {
            return;
        }
        this.a = 1;
        this.b = null;
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.release();
        super.finalize();
    }
    
    public boolean prepare() {
        if (this.a > -1) {
            TLog.w("%s has prepared.", "TuSdkAudioEncoder");
            return false;
        }
        if (this.b == null) {
            TLog.w("%s run need set setOutputFormat first.", "TuSdkAudioEncoder");
            return false;
        }
        if (this.c == null) {
            TLog.w("%s need setListener first.", "TuSdkAudioEncoder");
            return false;
        }
        this.a = 0;
        return true;
    }
    
    public void signalEndOfInputStream(final long n) throws IOException {
        if (this.b != null) {
            this.b.signalEndOfInputStream(n);
        }
    }
    
    public void autoFillMuteData(final long n, final long n2, final boolean b) throws IOException {
        if (this.b != null) {
            this.b.autoFillMuteData(n, n2, b);
        }
    }
}
