// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer;

import android.media.MediaFormat;

public interface AVAssetTrackDecoder extends AVAssetTrackSampleBufferOutput<AVAssetTrackSampleBufferOutput.AVAssetTrackSampleBufferInput>
{
    boolean feedInputBuffer();
    
    boolean drainOutputBuffer();
    
    boolean renderOutputBuffers();
    
    boolean renderOutputBuffer();
    
    void onInputFormatChanged(final MediaFormat p0);
    
    long durationTimeUs();
    
    long outputTimeUs();
    
    boolean seekTo(final long p0, final boolean p1);
    
    boolean isDecodeCompleted();
    
    void reset();
}
