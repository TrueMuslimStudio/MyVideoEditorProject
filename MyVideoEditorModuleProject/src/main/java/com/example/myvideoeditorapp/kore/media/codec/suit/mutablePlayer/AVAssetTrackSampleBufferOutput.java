// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer;

import android.media.MediaFormat;

import java.io.IOException;
import java.util.List;

public interface AVAssetTrackSampleBufferOutput<Target extends AVAssetTrackSampleBufferOutput.AVAssetTrackSampleBufferInput>
{
    List<Target> targets();
    
    void addTarget(final Target p0, final int p1);
    
    void addTarget(final Target p0);
    
    void removeTarget(final Target p0);
    
    public interface AVAssetTrackSampleBufferInput
    {
        void newFrameReady(final AVSampleBuffer p0) throws IOException;
        
        void outputFormatChaned(final MediaFormat p0, final AVAssetTrack p1);
    }
}
