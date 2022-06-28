// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer;

import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkEncodeSurface;
import com.example.myvideoeditorapp.kore.media.record.TuSdkRecordSurface;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkMediaEncodecSync;

public interface TuSdkMediaFilesSync extends TuSdkMediaEncodecSync
{
    long benchmarkUs();
    
    void setBenchmarkEnd();
    
    float calculateProgress();
    
    long totalDurationUs();
    
    boolean isEncodecCompleted();
    
    void syncVideoEncodecDrawFrame(final long p0, final boolean p1, final TuSdkRecordSurface p2, final TuSdkEncodeSurface p3);
}
