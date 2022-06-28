// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.audio;

import com.example.myvideoeditorapp.kore.api.extend.TuSdkAudioRender;
import com.example.myvideoeditorapp.kore.media.codec.TuSdkDecodecOperation;

public interface TuSdkAudioDecodecOperation extends TuSdkDecodecOperation
{
    void setAudioRender(final TuSdkAudioRender p0);
    
    TuSdkAudioInfo getAudioInfo();
}
