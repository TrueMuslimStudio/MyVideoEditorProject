// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec;

import android.media.MediaCodec;
import java.nio.ByteBuffer;
import android.media.MediaFormat;

public interface TuSdkMediaMuxer
{
    int addTrack(final MediaFormat p0);
    
    void writeSampleData(final int p0, final ByteBuffer p1, final MediaCodec.BufferInfo p2);
}
