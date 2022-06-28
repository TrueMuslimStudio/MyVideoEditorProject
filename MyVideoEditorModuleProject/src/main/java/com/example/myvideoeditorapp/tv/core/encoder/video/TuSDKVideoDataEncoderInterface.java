// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.encoder.video;


import com.example.myvideoeditorapp.tv.core.encoder.TuSDKVideoDataEncoderDelegate;

public interface TuSDKVideoDataEncoderInterface
{
    TuSDKVideoEncoderSetting getVideoEncoderSetting();
    
    void setVideoEncoderSetting(final TuSDKVideoEncoderSetting p0);
    
    void setDelegate(final TuSDKVideoDataEncoderDelegate p0);
}
