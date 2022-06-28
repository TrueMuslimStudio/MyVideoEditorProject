// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer;

public interface AVAssetTrackOutputSouce
{
    boolean seekTo(final long p0, final int p1);
    
    AVSampleBuffer readSampleBuffer(final int p0);
    
    boolean setTimeRange(final AVTimeRange p0);
    
    boolean advance();
    
    boolean isDecodeOnly(final long p0);
    
    boolean isOutputDone();
    
    void reset();
    
    void setAlwaysCopiesSampleData(final boolean p0);
    
    AVAssetTrack inputTrack();
    
    long durationTimeUs();
    
    long outputTimeUs();
    
    long calOutputTimeUs(final long p0);
    
    boolean lowFrameRateVideo();
    
    boolean supportSeek();
    
    boolean step();
}
