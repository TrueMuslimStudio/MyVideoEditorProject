// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.mutableCliper;

import android.media.MediaFormat;

import com.example.myvideoeditorapp.kore.api.extend.TuSdkMediaProgress;

import java.io.IOException;

public interface TuSdkMediaFilesCliperSaver
{
    void appendTrackItem(final TuSdkMediaTrackItem p0, final TuSdkMediaFileCliperPlayer.Callback<Boolean> p1);
    
    int setOutputVideoFormat(final MediaFormat p0);
    
    int setOutputAudioFormat(final MediaFormat p0);
    
    boolean run(final TuSdkMediaProgress p0) throws IOException;
    
    void stop();
}
