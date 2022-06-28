// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.sync;

import android.media.MediaCodec;

import java.io.IOException;
import java.nio.ByteBuffer;
import android.annotation.TargetApi;

@TargetApi(16)
public abstract class TuSdkMediaListener
{
    public abstract void onMediaOutputBuffer(final ByteBuffer p0, final MediaCodec.BufferInfo p1) throws IOException;
    
    public void onMediaOutputBuffer(final ByteBuffer byteBuffer, final int n, final int n2, final int n3, final long n4) throws IOException {
        final MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        bufferInfo.set(n, n2, n4, n3);
        byteBuffer.clear();
        byteBuffer.position(n);
        byteBuffer.limit(n + n2);
        this.onMediaOutputBuffer(byteBuffer, bufferInfo);
    }
}
