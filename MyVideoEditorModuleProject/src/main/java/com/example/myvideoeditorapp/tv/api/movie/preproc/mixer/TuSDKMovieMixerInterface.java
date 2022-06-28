// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.api.movie.preproc.mixer;

import com.example.myvideoeditorapp.tv.api.audio.preproc.mixer.TuSDKAudioEntry;
import com.example.myvideoeditorapp.tv.core.common.TuSDKMediaDataSource;

import java.util.List;

public interface TuSDKMovieMixerInterface
{
    void mix(final TuSDKMediaDataSource p0, final List<TuSDKAudioEntry> p1, final boolean p2);
    
    TuSDKMovieMixerInterface setVideoSoundVolume(final float p0);
}
