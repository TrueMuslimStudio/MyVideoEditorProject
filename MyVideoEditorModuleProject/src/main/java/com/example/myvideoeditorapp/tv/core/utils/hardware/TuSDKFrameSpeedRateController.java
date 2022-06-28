// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.utils.hardware;

import com.example.myvideoeditorapp.kore.utils.TLog;
import android.media.MediaCodec;

import java.io.IOException;

public class TuSDKFrameSpeedRateController
{
    private float a;
    private long b;
    private long c;
    private long d;
    private int e;
    private long f;
    private FrameSpeedRateCallback g;
    
    public TuSDKFrameSpeedRateController() {
        this.a = 1.0f;
        this.d = 0L;
        this.f = -1L;
    }
    
    public void setFrameSpeedRateCallback(final FrameSpeedRateCallback g) {
        this.g = g;
    }
    
    public void prepare() {
        if (this.c <= 0L) {
            return;
        }
        this.d = this.nanoTimeUs() - this.c;
        this.b = this.c;
        this.e = 0;
        this.f = this.nanoTimeUs();
    }
    
    public void reset() {
        this.e = 0;
        this.b = 0L;
        this.d = 0L;
    }
    
    public void requestAdjustSpeed(final MediaCodec.BufferInfo bufferInfo) throws IOException {
        if (this.a < 0.1f || this.a > 2.0f) {
            TLog.e("invalid speed rate : %f \n in speed rate \uff1a %f \n Max speed rate \uff1a%f ", new Object[] { this.a, 2.0f, 0.1f });
            return;
        }
        this.a();
        if (this.a == 1.0f) {
            this.c = this.calculateCurrentPTS();
            bufferInfo.presentationTimeUs = this.c;
            this.g.onAvailableFrameTimeUs(this.c);
        }
        else if (this.a < 1.0f) {
            if (this.b()) {
                return;
            }
            this.c = this.calculateCurrentPTS();
            bufferInfo.presentationTimeUs = this.c;
            this.g.onAvailableFrameTimeUs(this.c);
        }
        else {
            final int n = (int)(1.0f / (this.a - 1.0f));
            this.c = this.calculateCurrentPTS();
            bufferInfo.presentationTimeUs = this.c;
            this.g.onAvailableFrameTimeUs(this.c);
            if (this.e % n == 0) {
                this.c = this.calculateCurrentPTS();
                bufferInfo.presentationTimeUs = this.c;
                this.g.onAvailableFrameTimeUs(this.c);
            }
        }
    }
    
    public void setSpeedRate(final float a) {
        this.a = a;
    }
    
    public long nanoTimeUs() {
        return System.nanoTime() / 1000L;
    }
    
    public long nanoTimePTS() {
        if (this.f > 0L) {
            this.d += this.nanoTimeUs() - this.f;
            this.f = -1L;
        }
        return this.nanoTimeUs() - this.d;
    }
    
    public long calculateCurrentPTS() {
        final long nanoTimePTS = this.nanoTimePTS();
        if (this.b == 0L) {
            this.b = nanoTimePTS;
        }
        return this.b + (long)((nanoTimePTS - this.b) * this.a);
    }
    
    private void a() {
        ++this.e;
    }
    
    private boolean b() {
        return this.e > 1 && this.e % Math.round(1.0f / this.a) != 0;
    }
    
    public interface FrameSpeedRateCallback
    {
        void onAvailableFrameTimeUs(final long p0) throws IOException;
    }
}
