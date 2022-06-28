// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.decoder;

import android.media.MediaFormat;
import android.media.MediaCodec;

import java.io.FileNotFoundException;

public interface TuSDKMediaDecoderInterface<T extends TuSDKMovieReader>
{
    T getMediaReader();
    
    MediaCodec getVideoDecoder();
    
    MediaCodec getAudioDecoder();
    
    void start() throws FileNotFoundException;
    
    void stop();
    
    long getCurrentSampleTimeUs();
    
    int findVideoTrack();
    
    int selectVideoTrack();
    
    void unselectVideoTrack();
    
    MediaFormat getVideoTrackFormat();
    
    int findAudioTrack();
    
    int selectAudioTrack();
    
    void unselectAudioTrack();
    
    MediaFormat getAudioTrackFormat();
    
    void destroy();
}
