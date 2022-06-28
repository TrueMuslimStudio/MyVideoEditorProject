// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.video;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.view.Surface;

import com.example.myvideoeditorapp.kore.media.codec.TuSdkCodecOutput;
import com.example.myvideoeditorapp.kore.media.codec.TuSdkDecodecOperation;
import com.example.myvideoeditorapp.kore.media.codec.TuSdkMediaExtractor;
import com.example.myvideoeditorapp.kore.media.codec.exception.TuSdkNoMediaTrackException;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkCodecCapabilities;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaCodec;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaUtils;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;

import java.io.IOException;
import java.nio.ByteBuffer;

@TargetApi(18)
public class TuSdkVideoSurfaceDecodecOperation implements TuSdkDecodecOperation
{
    private int a;
    private TuSdkMediaCodec b;
    private boolean c;
    private ByteBuffer[] d;
    private final long e = 10000L;
    private TuSdkCodecOutput.TuSdkDecodecVideoSurfaceOutput f;
    private TuSdkVideoSurfaceDecodecOperationPatch g;
    private MediaFormat h;
    private boolean i;
    
    public TuSdkVideoSurfaceDecodecOperation(final TuSdkCodecOutput.TuSdkDecodecVideoSurfaceOutput f) {
        this.a = -1;
        this.i = false;
        if (f == null) {
            throw new NullPointerException(String.format("%s init failed, codecOutput is Empty", "TuSdkVideoSurfaceDecodecOperation"));
        }
        this.f = f;
    }
    
    @Override
    public void flush() {
        if (this.b == null || this.i) {
            return;
        }
        this.b.flush();
    }
    
    @Override
    public boolean decodecInit(final TuSdkMediaExtractor tuSdkMediaExtractor) throws IOException {
        this.a = TuSdkMediaUtils.getMediaTrackIndex(tuSdkMediaExtractor, "video/");
        if (this.a < 0) {
            this.decodecException(new TuSdkNoMediaTrackException(String.format("%s decodecInit can not find media track: %s", "TuSdkVideoSurfaceDecodecOperation", "video/")));
            TLog.e("%s decodecInit mTrackIndex result false  ", "TuSdkVideoSurfaceDecodecOperation");
            return false;
        }
        this.h = tuSdkMediaExtractor.getTrackFormat(this.a);
        final MediaFormat trackFormat = tuSdkMediaExtractor.getTrackFormat(this.a);
        tuSdkMediaExtractor.selectTrack(this.a);
        if (!this.f.canSupportMediaInfo(this.a, this.h)) {
            TLog.e("%s decodecInit mDecodecOutput result false  ", "TuSdkVideoSurfaceDecodecOperation");
            TLog.w("%s decodecInit can not Support MediaInfo: %s", "TuSdkVideoSurfaceDecodecOperation", this.h);
            return false;
        }
        Surface requestSurface = null;
        while (!ThreadHelper.isInterrupted() && (requestSurface = this.f.requestSurface()) == null) {}
        this.b = this.a().patchMediaCodec(this.h.getString("mime"));
        if (this.b.configureError() != null || !this.b.configure(trackFormat, requestSurface, null, 0)) {
            this.decodecException(this.b.configureError());
            this.b = null;
            return false;
        }
        this.b.start();
        this.d = this.b.getOutputBuffers();
        this.c = false;
        return true;
    }
    
    private TuSdkVideoSurfaceDecodecOperationPatch a() {
        if (this.g == null) {
            this.g = new TuSdkVideoSurfaceDecodecOperationPatch();
        }
        return this.g;
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
                TuSdkCodecCapabilities.logBufferInfo(String.format("%s------ %d ", "TuSdkVideoSurfaceDecodecOperation", tuSdkMediaExtractor.getSampleTime()), bufferInfo);
                if (bufferInfo.size > 0) {
                    this.f.processOutputBuffer(tuSdkMediaExtractor, this.a, this.d[dequeueOutputBuffer], bufferInfo);
                }
                if (!this.i) {
                    b.releaseOutputBuffer(dequeueOutputBuffer, true);
                }
                this.f.updated(bufferInfo);
                break;
            }
        }
        return (bufferInfo.flags & 0x4) != 0x0 && this.f.updatedToEOS(bufferInfo);
    }
    
    @Override
    public void decodecRelease() {
        this.i = true;
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
