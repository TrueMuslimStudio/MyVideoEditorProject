// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.decoder;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.text.TextUtils;
import android.view.Surface;

import com.example.myvideoeditorapp.tv.core.common.TuSDKMediaDataSource;
import com.example.myvideoeditorapp.tv.video.editor.TuSdkTimeRange;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

@TargetApi(16)
public class TuSDKAudioDecoder extends TuSDKMediaDecoder implements TuSDKAudioDecoderInterface
{
    private OnAudioDecoderDelegate a;
    private TuSdkTimeRange b;
    private TuSDKAudioInfo c;
    private String d;
    private FileOutputStream e;
    private long f;
    private int g;
    private volatile boolean h;
    
    public void setDelegate(final OnAudioDecoderDelegate a) {
        this.a = a;
    }
    
    @Override
    public MediaCodec getAudioDecoder() {
        if (this.mAudioDecoder == null) {
            this.mAudioDecoder = this.createAudioDecoder();
            final MediaFormat audioTrackFormat = this.getAudioTrackFormat();
            if (audioTrackFormat == null) {
                return null;
            }
            this.mAudioDecoder.configure(audioTrackFormat, (Surface)null, (MediaCrypto)null, 0);
        }
        return this.mAudioDecoder;
    }
    
    @Override
    public MediaCodec getVideoDecoder() {
        return null;
    }
    
    public TuSDKAudioDecoder(final String s) {
        super(TuSDKMediaDataSource.create(s));
        this.h = true;
    }
    
    public TuSDKAudioDecoder(final String s, final String s2) {
        this(TuSDKMediaDataSource.create(s), s2);
    }
    
    public TuSDKAudioDecoder(final TuSDKMediaDataSource tuSDKMediaDataSource, final String d) {
        super(tuSDKMediaDataSource);
        this.h = true;
        this.d = d;
    }
    
    public TuSDKAudioInfo getAudioInfo() {
        if (this.c == null) {
            this.c = TuSDKAudioInfo.createWithMediaFormat(this.getAudioTrackFormat());
        }
        return this.c;
    }
    
    public long getDurationTimes() {
        if (this.getAudioInfo() == null) {
            return 0L;
        }
        return this.getAudioInfo().durationTimeUs;
    }
    
    public TuSdkTimeRange getDecodeTimeRange() {
        return this.b;
    }
    
    public void setDecodeTimeRange(final TuSdkTimeRange b) {
        this.b = b;
    }
    
    private boolean a() {
        return this.getDecodeTimeRange() != null && this.getDecodeTimeRange().isValid();
    }
    
    @Override
    public void start() throws FileNotFoundException {
        this.mMovieReader = this.createMovieReader();
        if (this.mMovieReader != null) {
            this.mMovieReader.setTimeRange(this.b);
            super.start();

            this.c();

        }
    }
    
    @Override
    public void stop() {
        if (!this.h) {
            return;
        }
        this.h = false;
        if (this.d != null) {
            new File(this.d).delete();
        }
    }
    
    private void b() {
        super.stop();
        this.h = false;
        if (this.e != null) {
            try {
                this.e.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    @Override
    public void seekTo(final long n) {
        if (this.mMovieReader == null) {
            return;
        }
        this.mMovieReader.seekTo(n, 2);
    }
    
    protected void writeRawDataToFile(final byte[] b) {
        if (this.e == null) {
            return;
        }
        try {
            this.e.write(b);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void c() throws FileNotFoundException {
        if (this.selectAudioTrack() == -1) {
            this.onDecoderError(TuSDKMediaDecoderError.InvalidDataSource);
        } else {
            this.h = true;
            TuSDKAudioInfo var1 = this.getAudioInfo();
            var1.setFilePath(this.d);
            if (!TextUtils.isEmpty(var1.getFilePath())) {
                this.e = new FileOutputStream(var1.getFilePath());
            }

            if (this.a != null) {
                this.a.onDecodeRawInfo(var1);
            }

            boolean var2 = false;
            boolean var3 = false;
            if (this.a()) {
                this.seekTo(this.getDecodeTimeRange().getStartTimeUS());
            } else {
                this.seekTo(0L);
            }

            for(; !var3 && this.h; var3 = this.e()) {
                if (!var2) {
                    var2 = this.d();
                }
            }

            var1.size = this.g;
            this.c.size = this.g;
            this.b();
            if (this.a != null && this.h) {
                this.a.onDecode((byte[])null, (MediaCodec.BufferInfo)null, 1.0D);
            }

        }
    }
    
    private boolean d() {
        final ByteBuffer[] inputBuffers = this.mAudioDecoder.getInputBuffers();
        final int dequeueInputBuffer = this.mAudioDecoder.dequeueInputBuffer(500L);
        if (dequeueInputBuffer >= 0) {
            final int sampleData = this.mMovieReader.readSampleData(inputBuffers[dequeueInputBuffer], 0);
            if (sampleData < 0) {
                this.mAudioDecoder.queueInputBuffer(dequeueInputBuffer, 0, 0, this.mMovieReader.getSampleTime(), 4);
                return true;
            }
            this.mAudioDecoder.queueInputBuffer(dequeueInputBuffer, 0, sampleData, this.mMovieReader.getSampleTime(), 0);
        }
        return false;
    }
    
    private boolean e() {
        final MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        final ByteBuffer[] outputBuffers = this.mAudioDecoder.getOutputBuffers();
        final int dequeueOutputBuffer = this.mAudioDecoder.dequeueOutputBuffer(bufferInfo, 500L);
        if (dequeueOutputBuffer >= 0) {
            if ((bufferInfo.flags & 0x2) != 0x0) {
                this.mAudioDecoder.releaseOutputBuffer(dequeueOutputBuffer, false);
                return false;
            }
            if (bufferInfo.size > 0) {
                final ByteBuffer byteBuffer = outputBuffers[dequeueOutputBuffer];
                byteBuffer.position(bufferInfo.offset);
                byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
                final byte[] dst = new byte[bufferInfo.size];
                byteBuffer.get(dst);
                this.g += dst.length;
                this.f += this.c.getFrameInterval();
                final float n = (this.getDurationTimes() > 0L) ? ((float)(bufferInfo.presentationTimeUs / this.getDurationTimes())) : 0.0f;
                this.onDecode(dst, n);
                this.writeRawDataToFile(dst);
                if (this.a != null) {
                    this.a.onDecode(dst, bufferInfo, n);
                }
            }
            this.mAudioDecoder.releaseOutputBuffer(dequeueOutputBuffer, false);
            if ((bufferInfo.flags & 0x4) != 0x0) {
                return true;
            }
            if (this.a() && this.f >= this.getDecodeTimeRange().durationTimeUS()) {
                this.unselectAudioTrack();
                return true;
            }
        }
        else if (dequeueOutputBuffer == -3) {
            this.mAudioDecoder.getOutputBuffers();
        }
        return false;
    }
    
    @Override
    protected void onDecoderError(final TuSDKMediaDecoderError tuSDKMediaDecoderError) {
        super.onDecoderError(tuSDKMediaDecoderError);
        if (this.a != null) {
            this.a.onDecoderErrorCode(tuSDKMediaDecoderError);
        }
    }
    
    @Override
    public void onDecode(final byte[] array, final double n) {
    }
    
    public interface OnAudioDecoderDelegate
    {
        void onDecodeRawInfo(final TuSDKAudioInfo p0);
        
        void onDecode(final byte[] p0, final MediaCodec.BufferInfo p1, final double p2);
        
        void onDecoderErrorCode(final TuSDKMediaDecoderError p0);
    }
}
