// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.audio;

public interface TuSdkAudioEffects
{
    void release();
    
    boolean enableAcousticEchoCanceler();
    
    boolean enableAutomaticGainControl();
    
    boolean enableNoiseSuppressor();
}
