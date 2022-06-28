// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.video;

import com.example.myvideoeditorapp.kore.type.ColorFormatType;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.hardware.CameraConfigs;
import com.example.myvideoeditorapp.tv.core.encoder.video.TuSDKVideoEncoderSetting;

public class TuSDKVideoCaptureSetting
{
    public CameraConfigs.CameraFacing facing;
    public TuSdkSize videoSize;
    public int fps;
    public AVCodecType videoAVCodecType;
    public ColorFormatType imageFormatType;
    
    public TuSDKVideoCaptureSetting() {
        this.facing = CameraConfigs.CameraFacing.Front;
        this.videoSize = new TuSdkSize(320, 480);
        this.fps = TuSDKVideoEncoderSetting.VideoQuality.LIVE_MEDIUM3.getFps();
        this.videoAVCodecType = AVCodecType.HW_CODEC;
        this.imageFormatType = ColorFormatType.NV21;
    }
    
    public enum AVCodecType
    {
        HW_CODEC, 
        SW_CODEC, 
        CUSTOM_CODEC;
    }
}
