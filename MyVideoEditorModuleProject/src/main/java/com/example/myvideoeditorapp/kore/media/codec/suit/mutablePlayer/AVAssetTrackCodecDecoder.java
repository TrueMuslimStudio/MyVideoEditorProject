// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer;

import java.io.IOException;
import java.util.Iterator;
import java.nio.ByteBuffer;
import android.media.MediaCrypto;
import android.os.Build;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaFormat;
import java.util.ArrayList;
import com.example.myvideoeditorapp.kore.utils.TLog;
import android.media.MediaFormat;
import android.view.Surface;
import android.media.MediaCodec;
import java.util.List;
import android.annotation.TargetApi;

@TargetApi(16)
public class AVAssetTrackCodecDecoder implements AVAssetTrackDecoder
{
    private List<AVAssetTrackSampleBufferInput> a;
    private MediaCodec b;
    private Surface c;
    private MediaFormat d;
    private AVAssetTrack e;
    private boolean f;
    private AVAssetTrackOutputSouce g;
    private long h;
    private int i;
    private boolean j;
    private AVSampleBuffer k;
    private AVSampleBuffer l;
    private boolean m;
    private long n;
    private PositionSeeker o;
    private boolean p;
    
    public AVAssetTrackCodecDecoder(final AVAssetTrackOutputSouce g) {
        this.i = 30;
        this.j = false;
        this.m = false;
        this.n = 10000L;
        this.o = new PositionSeeker();
        this.p = false;
        if (g == null) {
            TLog.i("%s No tracks are available in the data source.", g);
            return;
        }
        this.g = g;
        this.a = new ArrayList<AVAssetTrackSampleBufferInput>();
        this.j = this.g.lowFrameRateVideo();
        if (this.g.inputTrack() != null) {
            this.d = this.g.inputTrack().mediaFormat();
            this.e = this.g.inputTrack();
        }
    }
    
    public void setUseSoftDecodec(final boolean m) {
        this.m = m;
    }
    
    public void setOutputSurface(final Surface c) {
        if (this.g.inputTrack() == null || this.g.inputTrack().mediaType() != AVMediaType.AVMediaTypeVideo) {
            TLog.i("%s Only video tracks support surface.", this);
            return;
        }
        this.c = c;
    }
    
    protected void maybeInitCodec() {
        if (this.b != null) {
            return;
        }
        try {
            if (this.d == null) {
                this.d = this.g.inputTrack().mediaFormat();
            }
            final String string = TuSdkMediaFormat.getString(this.d, "mime", null);
            if (string == null) {
                TLog.e("%s \uff1aThe mCodecFormat is invalid. ", this);
                return;
            }
            if (this.d.containsKey("encoder-delay") && Build.VERSION.SDK_INT > 28) {
                this.d.getClass().getDeclaredMethod("removeKey", String.class).invoke(this.d, "encoder-delay");
            }
            switch (this.g.inputTrack().mediaType().ordinal()) {
                case 1: {
                    this.b = MediaCodec.createDecoderByType(string);
                    break;
                }
                case 2: {
                    if (this.m) {
                        this.b = MediaCodec.createByCodecName("c2.android.avc.decoder");
                        break;
                    }
                    this.b = MediaCodec.createDecoderByType(string);
                    break;
                }
            }
            this.b.configure(this.d, this.c, (MediaCrypto)null, 0);
            this.b.start();
            this.f = false;
        }
        catch (Exception ex) {}
    }
    
    protected void releaseCodec() {
        if (this.b == null) {
            return;
        }
        this.b.stop();
        this.b.release();
        this.b = null;
    }
    
    @Override
    public void onInputFormatChanged(final MediaFormat d) {
        if (this.d == d) {
            return;
        }
        System.currentTimeMillis();
        this.releaseCodec();
        this.d = d;
        this.e = this.g.inputTrack();
        this.maybeInitCodec();
    }
    
    @Override
    public long durationTimeUs() {
        return this.g.durationTimeUs();
    }
    
    @Override
    public long outputTimeUs() {
        return this.h;
    }
    
    @Override
    public boolean seekTo(final long n, final boolean b) {
        return this.o.a(n, b);
    }
    
    @Override
    public boolean isDecodeCompleted() {
        return this.f;
    }
    
    @Override
    public void reset() {
        this.h = 0L;
        this.l = null;
        this.k = null;
        this.f = false;
        this.g.reset();
        this.releaseCodec();
        if (this.g.inputTrack() != null) {
            this.d = this.g.inputTrack().mediaFormat();
            this.e = this.g.inputTrack();
        }
        else {
            this.d = null;
        }
    }
    
    @Override
    public boolean renderOutputBuffers() {
        if (this.isOutputDone()) {
            return false;
        }
        this.maybeInitCodec();
        while (this.drainOutputBuffer() && !this.c()) {}
        while (this.feedInputBuffer()) {}
        return true;
    }
    
    @Override
    public boolean renderOutputBuffer() {
        if (this.isOutputDone()) {
            return false;
        }
        this.maybeInitCodec();
        while (this.drainOutputBuffer() && !this.c()) {}
        while (this.feedInputBuffer()) {}
        return true;
    }
    
    public boolean isOutputDone() {
        return this.f;
    }
    
    private boolean a() {
        return this.j;
    }
    
    private long b() {
        return (long)(1.0f / this.i * 1000000.0f);
    }
    
    private boolean c() {
        return this.k != null && this.l != null && this.k.renderTimeUs() < this.l.renderTimeUs();
    }
    
    @Override
    public boolean feedInputBuffer() {
        try {
            if (this.b == null) {
                return false;
            }
            if (this.p) {
                return false;
            }
            final AVSampleBuffer sampleBuffer = this.g.readSampleBuffer(0);
            if (this.g.isOutputDone() || sampleBuffer == null) {
                final int dequeueInputBuffer = this.b.dequeueInputBuffer(20000L);
                if (dequeueInputBuffer >= 0) {
                    this.b.queueInputBuffer(dequeueInputBuffer, 0, 0, 0L, 4);
                }
                return this.drainOutputBuffer() && false;
            }
            if ((this.d != null && this.d != sampleBuffer.format()) || sampleBuffer.isFormat()) {
                final int dequeueInputBuffer2 = this.b.dequeueInputBuffer(20000L);
                if (dequeueInputBuffer2 >= 0) {
                    this.b.queueInputBuffer(dequeueInputBuffer2, 0, 0, this.g.outputTimeUs(), 4);
                    this.p = true;
                }
                return false;
            }
            final int dequeueInputBuffer3 = this.b.dequeueInputBuffer(20000L);
            if (dequeueInputBuffer3 < 0) {
                return false;
            }
            switch (dequeueInputBuffer3) {
                case -1: {
                    return false;
                }
                default: {
                    final ByteBuffer byteBuffer = this.b.getInputBuffers()[dequeueInputBuffer3];
                    byteBuffer.position(0);
                    byteBuffer.put(sampleBuffer.buffer());
                    byteBuffer.flip();
                    this.b.queueInputBuffer(dequeueInputBuffer3, sampleBuffer.info().offset, sampleBuffer.info().size, sampleBuffer.info().presentationTimeUs, sampleBuffer.info().flags);
                    this.g.advance();
                    return true;
                }
            }
        }
        catch (Exception ex) {
            TLog.e(ex);
            return false;
        }
    }
    
    private void d() {
        if (this.l == null || this.l.renderTimeUs() >= this.g.outputTimeUs()) {
            return;
        }
        final MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        bufferInfo.offset = 0;
        bufferInfo.presentationTimeUs = this.g.outputTimeUs() - this.l.renderTimeUs();
        final AVSampleBuffer l = new AVSampleBuffer(null, bufferInfo, null);
        l.setRenderTimeUs(this.g.outputTimeUs());
        this.k = this.l;
        this.l = l;
        while (this.c()) {
            this.drainOutputBuffer();
        }
    }
    
    @Override
    public boolean drainOutputBuffer() {
        try {
            if (this.b == null) {
                return false;
            }
            if (this.c()) {
                if (!this.o.a()) {
                    this.h += this.b();
                    final MediaCodec.BufferInfo info = this.k.info();
                    info.presentationTimeUs += this.b();
                    this.k.setRenderTimeUs(this.h);
                    this.b(this.k);
                    return false;
                }
                if (this.g.isDecodeOnly(this.k.info().presentationTimeUs)) {
                    this.h += this.b();
                    final MediaCodec.BufferInfo info2 = this.k.info();
                    info2.presentationTimeUs += this.b();
                    this.k.setRenderTimeUs(this.h);
                    return true;
                }
                this.o.b();
                this.b(this.k);
                return false;
            }
            else {
                if (this.l != null && !this.l.isRenered()) {
                    this.a(this.l);
                    return false;
                }
                final MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                final int dequeueOutputBuffer = this.b.dequeueOutputBuffer(bufferInfo, 10000L);
                if ((bufferInfo.flags & 0x4) != 0x0) {
                    this.d();
                    if (this.g.isOutputDone()) {
                        this.b.releaseOutputBuffer(dequeueOutputBuffer, false);
                        this.releaseCodec();
                        this.f = true;
                    }
                    else {
                        final AVSampleBuffer sampleBuffer = this.g.readSampleBuffer(0);
                        if ((sampleBuffer != null && this.d != sampleBuffer.format()) || sampleBuffer.isFormat()) {
                            this.onInputFormatChanged(sampleBuffer.format());
                            this.p = false;
                            this.g.step();
                        }
                    }
                    return false;
                }
                switch (dequeueOutputBuffer) {
                    case -3: {
                        return true;
                    }
                    case -1: {
                        if (!this.p) {
                            final AVSampleBuffer sampleBuffer2 = this.g.readSampleBuffer(0);
                            if (sampleBuffer2 != null && this.d != sampleBuffer2.format()) {
                                this.onInputFormatChanged(sampleBuffer2.format());
                            }
                        }
                        return false;
                    }
                    case -2: {
                        this.a(this.b.getOutputFormat());
                        return true;
                    }
                    default: {
                        if (dequeueOutputBuffer < 0 || bufferInfo.size <= 0) {
                            return true;
                        }
                        final ByteBuffer byteBuffer = this.b.getOutputBuffers()[dequeueOutputBuffer];
                        final MediaCodec.BufferInfo bufferInfo2 = new MediaCodec.BufferInfo();
                        bufferInfo2.presentationTimeUs = bufferInfo.presentationTimeUs;
                        bufferInfo2.size = bufferInfo.size;
                        bufferInfo2.flags = bufferInfo.flags;
                        bufferInfo2.offset = bufferInfo.offset;
                        final AVSampleBuffer l = new AVSampleBuffer(byteBuffer, bufferInfo, null);
                        l.setRenderIndex(dequeueOutputBuffer);
                        l.setRenderTimeUs(((AVAssetTrackPipeMediaExtractor)this.g).calOutputTimeUs(l.info().presentationTimeUs, this.p));
                        if (this.a()) {
                            this.k = this.l;
                            (this.l = l).setRenderIndex(dequeueOutputBuffer);
                            if (this.k == null) {
                                this.k = this.l;
                            }
                        }
                        if (this.c()) {
                            return this.drainOutputBuffer();
                        }
                        if (this.g.isDecodeOnly(l.info().presentationTimeUs)) {
                            this.b.releaseOutputBuffer(l.renderIndex(), false);
                            l.makeRendered();
                            return true;
                        }
                        final boolean a = this.o.a();
                        this.o.b();
                        this.a(l);
                        return !a;
                    }
                }
            }
        }
        catch (Exception ex) {
            TLog.e(ex);
            return false;
        }
    }
    
    @TargetApi(21)
    private void a(final AVSampleBuffer avSampleBuffer) throws IOException {
        if (avSampleBuffer.renderIndex() < 0) {
            TLog.i("maybeRender index < 0", new Object[0]);
            return;
        }
        avSampleBuffer.setRenderTimeUs(this.h = ((AVAssetTrackPipeMediaExtractor)this.g).calOutputTimeUs(avSampleBuffer.info().presentationTimeUs, this.p));
        if (this.g.inputTrack().mediaType() == AVMediaType.AVMediaTypeAudio) {
            this.b(avSampleBuffer);
            this.b.releaseOutputBuffer(avSampleBuffer.renderIndex(), true);
        }
        else {
            this.b.releaseOutputBuffer(avSampleBuffer.renderIndex(), true);
            this.b(avSampleBuffer);
        }
        avSampleBuffer.makeRendered();
    }
    
    private void b(final AVSampleBuffer avSampleBuffer) throws IOException {
        if (this.o.a()) {
            return;
        }
        final Iterator<AVAssetTrackSampleBufferInput> iterator = this.targets().iterator();
        while (iterator.hasNext()) {
            iterator.next().newFrameReady(avSampleBuffer);
        }
    }
    
    private void a(final MediaFormat mediaFormat) {
        final Iterator<AVAssetTrackSampleBufferInput> iterator = this.targets().iterator();
        while (iterator.hasNext()) {
            iterator.next().outputFormatChaned(mediaFormat, this.g.inputTrack());
        }
    }
    
    @Override
    public List<AVAssetTrackSampleBufferInput> targets() {
        return this.a;
    }
    
    @Override
    public void addTarget(final AVAssetTrackSampleBufferInput avAssetTrackSampleBufferInput, final int n) {
        this.a.add(n, avAssetTrackSampleBufferInput);
    }
    
    @Override
    public void addTarget(final AVAssetTrackSampleBufferInput avAssetTrackSampleBufferInput) {
        this.a.add(avAssetTrackSampleBufferInput);
    }
    
    @Override
    public void removeTarget(final AVAssetTrackSampleBufferInput avAssetTrackSampleBufferInput) {
        this.a.remove(avAssetTrackSampleBufferInput);
    }
    
    private class PositionSeeker
    {
        private long b;
        
        PositionSeeker() {
            this.b = -1L;
            this.b();
        }
        
        private boolean a() {
            return this.b != -1L;
        }
        
        private boolean a(final long b, final boolean b2) {
            if (AVAssetTrackCodecDecoder.this.g.inputTrack() == null) {
                return false;
            }
            if (!b2) {
                return this.a(b, 2);
            }
            AVAssetTrackCodecDecoder.this.l = null;
            AVAssetTrackCodecDecoder.this.k = null;
            AVAssetTrackCodecDecoder.this.h = 0L;
            this.b = b;
            AVAssetTrackCodecDecoder.this.d = null;
            AVAssetTrackCodecDecoder.this.g.seekTo(b, 0);
            AVAssetTrackCodecDecoder.this.releaseCodec();
            AVAssetTrackCodecDecoder.this.maybeInitCodec();
            AVAssetTrackCodecDecoder.this.b.flush();
            while (AVAssetTrackCodecDecoder.this.o.a() && AVAssetTrackCodecDecoder.this.b != null) {
                while (AVAssetTrackCodecDecoder.this.feedInputBuffer()) {}
                while (AVAssetTrackCodecDecoder.this.drainOutputBuffer()) {}
            }
            return this.b < 0L;
        }
        
        private boolean a(final long b, final int n) {
            this.b = b;
            AVAssetTrackCodecDecoder.this.g.seekTo(b, n);
            return AVAssetTrackCodecDecoder.this.renderOutputBuffers();
        }
        
        private void b() {
            this.b = -1L;
        }
    }
}
