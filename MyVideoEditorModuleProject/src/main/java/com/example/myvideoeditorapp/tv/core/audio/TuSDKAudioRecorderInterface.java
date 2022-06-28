// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.audio;

import com.example.myvideoeditorapp.tv.core.encoder.audio.TuSDKAudioDataEncoderInterface;

public interface TuSDKAudioRecorderInterface
{
    void startRecording();
    
    void stopRecording();
    
    boolean isRecording();
    
    void mute(final boolean p0);
    
    TuSDKAudioDataEncoderInterface getAudioEncoder();
}
