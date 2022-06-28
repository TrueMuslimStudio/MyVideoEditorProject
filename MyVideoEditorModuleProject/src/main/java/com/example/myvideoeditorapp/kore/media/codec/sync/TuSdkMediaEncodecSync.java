// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.sync;

import com.example.myvideoeditorapp.kore.media.codec.TuSdkMediaSync;


public interface TuSdkMediaEncodecSync extends TuSdkMediaSync
{
    TuSdkAudioEncodecSync getAudioEncodecSync();
    
    TuSdkVideoEncodecSync getVideoEncodecSync();
}
