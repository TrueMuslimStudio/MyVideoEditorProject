// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer;

import com.example.myvideoeditorapp.kore.utils.TLog;
import android.media.MediaFormat;
import android.media.MediaCodec;
import java.nio.ByteBuffer;

public class AVSampleBuffer
{
    public static final int AV_BUFFER_FLAG_NONE = -1;
    public static final int AV_BUFFER_FLAG_DATA = 0;
    public static final int AV_BUFFER_FLAG_FORMAT = 1;
    public static final int AV_BUFFER_FLAG_DECODE_ONLY = 2;
    private ByteBuffer a;
    private MediaCodec.BufferInfo b;
    private MediaFormat c;
    private int d;
    private long e;
    private int f;
    
    public AVSampleBuffer(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo, final MediaFormat mediaFormat) {
        this(byteBuffer, bufferInfo, mediaFormat, 0);
    }
    
    public AVSampleBuffer(final ByteBuffer a, final MediaCodec.BufferInfo b, final MediaFormat c, final int d) {
        this.d = -1;
        this.f = -1;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
    
    public AVSampleBuffer(final MediaFormat c) {
        this.d = -1;
        this.f = -1;
        this.c = c;
        this.d = 1;
    }
    
    public void setRenderTimeUs(final long e) {
        this.e = e;
    }
    
    public void setRenderIndex(final int f) {
        this.f = f;
    }
    
    public int renderIndex() {
        return this.f;
    }
    
    public boolean makeRendered() {
        if (this.f == -1) {
            return false;
        }
        this.f = -1;
        return true;
    }
    
    public boolean isRenered() {
        return this.f == -1;
    }
    
    public long renderTimeUs() {
        return this.e;
    }
    
    public MediaFormat format() {
        return this.c;
    }
    
    public ByteBuffer buffer() {
        return this.a;
    }
    
    public MediaCodec.BufferInfo info() {
        return this.b;
    }
    
    public boolean isKeyFrame() {
        if (this.b == null) {
            TLog.w("%s : isKeyFrame return false. because info is null.", this);
            return false;
        }
        return (this.b.flags & 0x1) != 0x0;
    }
    
    public void setFlag(final int d) {
        this.d = d;
    }
    
    public boolean isDecodeOnly() {
        return this.d == 2;
    }
    
    public boolean isFormat() {
        return this.d == 1;
    }
    
    public boolean isData() {
        return this.d == 0;
    }
}
