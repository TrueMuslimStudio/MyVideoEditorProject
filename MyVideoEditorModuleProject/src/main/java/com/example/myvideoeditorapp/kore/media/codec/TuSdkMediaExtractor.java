// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.Map;
import android.os.PersistableBundle;
import android.media.DrmInitData;
import android.media.MediaExtractor;
import android.media.MediaCodec;
import android.media.MediaFormat;

import com.example.myvideoeditorapp.kore.media.codec.decoder.TuSdkMediaFrameInfo;

public interface TuSdkMediaExtractor
{
    void release();
    
    void syncPlay() throws IOException;
    
    void play();
    
    MediaFormat getTrackFormat(final int p0);
    
    int getTrackCount();
    
    void selectTrack(final int p0);
    
    long getSampleTime();
    
    int getSampleFlags();
    
    int getSampleTrackIndex();
    
    boolean getSampleCryptoInfo(final MediaCodec.CryptoInfo p0);
    
    long getCachedDuration();
    
    MediaExtractor.CasInfo getCasInfo(final int p0);
    
    DrmInitData getDrmInitData();
    
    PersistableBundle getMetrics();
    
    Map<UUID, byte[]> getPsshInfo();
    
    boolean hasCacheReachedEndOfStream();
    
    boolean isPlaying();
    
    void pause();
    
    void resume();
    
    long seekTo(final long p0);
    
    long seekTo(final long p0, final boolean p1);
    
    long seekTo(final long p0, final int p1);
    
    boolean advance();
    
    int readSampleData(final ByteBuffer p0, final int p1);
    
    long getFrameIntervalUs();
    
    TuSdkMediaFrameInfo getFrameInfo() throws IOException;
    
    long advanceNestest(final long p0);
}
