// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.utils.hardware;

import com.example.myvideoeditorapp.kore.media.codec.video.TuSdkVideoQuality;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;

public class TuSdkRecorderVideoEncoderSetting
{
    public TuSdkSize videoSize;
    public TuSdkVideoQuality videoQuality;
    public int mediacodecAVCIFrameInterval;
    public boolean enableAllKeyFrame;
    
    public TuSdkRecorderVideoEncoderSetting() {
        this.videoSize = new TuSdkSize(320, 480);
        this.videoQuality = TuSdkVideoQuality.RECORD_HIGH2;
        this.mediacodecAVCIFrameInterval = 1;
        this.enableAllKeyFrame = false;
    }
    
    public static TuSdkRecorderVideoEncoderSetting getDefaultRecordSetting() {
        final TuSdkRecorderVideoEncoderSetting tuSdkRecorderVideoEncoderSetting = new TuSdkRecorderVideoEncoderSetting();
        tuSdkRecorderVideoEncoderSetting.videoQuality = TuSdkVideoQuality.safeQuality();
        return tuSdkRecorderVideoEncoderSetting;
    }
}
