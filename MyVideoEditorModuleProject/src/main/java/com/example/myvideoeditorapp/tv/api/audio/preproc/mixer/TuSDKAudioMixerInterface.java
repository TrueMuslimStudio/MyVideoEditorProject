// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.api.audio.preproc.mixer;

import java.util.List;

public interface TuSDKAudioMixerInterface
{
    void mixAudios(final List<TuSDKAudioEntry> p0);
    
    byte[] mixRawAudioBytes(final byte[][] p0);
    
    void cancel();
}
