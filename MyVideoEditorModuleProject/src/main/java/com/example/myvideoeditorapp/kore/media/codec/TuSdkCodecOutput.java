// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec;

import java.io.IOException;
import java.nio.ByteBuffer;
import android.view.Surface;
import android.media.MediaCodec;
import android.media.MediaFormat;

import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaCodec;

public interface TuSdkCodecOutput
{
    void outputFormatChanged(final MediaFormat p0);
    
    void updated(final MediaCodec.BufferInfo p0) throws IOException;
    
    boolean updatedToEOS(final MediaCodec.BufferInfo p0) throws IOException;
    
    void onCatchedException(final Exception p0) throws IOException;
    
    public interface TuSdkDecodecVideoSurfaceOutput extends TuSdkDecodecOutput
    {
        Surface requestSurface();
    }
    
    public interface TuSdkEncodecOutput extends TuSdkCodecOutput
    {
        void processOutputBuffer(final TuSdkMediaMuxer p0, final int p1, final ByteBuffer p2, final MediaCodec.BufferInfo p3);
    }
    
    public interface TuSdkDecodecOutput extends TuSdkCodecOutput
    {
        boolean canSupportMediaInfo(final int p0, final MediaFormat p1) throws IOException;
        
        boolean processExtractor(final TuSdkMediaExtractor p0, final TuSdkMediaCodec p1) throws IOException;
        
        void processOutputBuffer(final TuSdkMediaExtractor p0, final int p1, final ByteBuffer p2, final MediaCodec.BufferInfo p3) throws IOException;
    }
}
