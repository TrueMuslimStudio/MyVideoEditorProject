// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.sync;

import com.example.myvideoeditorapp.kore.media.codec.TuSdkMediaSync;


public interface TuSdkMediaDecodecSync extends TuSdkMediaSync
{
    TuSdkAudioDecodecSync buildAudioDecodecSync();
    
    TuSdkVideoDecodecSync buildVideoDecodecSync();
    
    TuSdkVideoDecodecSync getVideoDecodecSync();
    
    TuSdkAudioDecodecSync getAudioDecodecSync();
}
