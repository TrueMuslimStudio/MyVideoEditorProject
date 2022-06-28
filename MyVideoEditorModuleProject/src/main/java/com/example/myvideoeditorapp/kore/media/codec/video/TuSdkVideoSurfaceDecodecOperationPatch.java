// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.video;

import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaCodec;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaCodecImpl;
import com.example.myvideoeditorapp.kore.utils.hardware.HardwareHelper;

import java.util.HashMap;
import java.util.Map;

public class TuSdkVideoSurfaceDecodecOperationPatch
{
    private static final Map<String, String> a;
    
    public TuSdkMediaCodec patchMediaCodec(final String s) {
        boolean matchDeviceModelAndManuFacturer = false;
        for (final Map.Entry<String, String> entry : TuSdkVideoSurfaceDecodecOperationPatch.a.entrySet()) {
            matchDeviceModelAndManuFacturer = HardwareHelper.isMatchDeviceModelAndManuFacturer(entry.getKey(), entry.getValue());
            if (matchDeviceModelAndManuFacturer) {
                break;
            }
        }
        if (!matchDeviceModelAndManuFacturer) {
            return TuSdkMediaCodecImpl.createDecoderByType(s);
        }
        TuSdkMediaCodec tuSdkMediaCodec = null;
        switch (s) {
            case "video/avc": {
                tuSdkMediaCodec = TuSdkMediaCodecImpl.createByCodecName("c2.android.avc.decoder");
                if (tuSdkMediaCodec == null) {
                    tuSdkMediaCodec = TuSdkMediaCodecImpl.createByCodecName("OMX.google.h264.decoder");
                    break;
                }
                break;
            }
            default: {
                tuSdkMediaCodec = TuSdkMediaCodecImpl.createDecoderByType(s);
                break;
            }
        }
        return tuSdkMediaCodec;
    }
    
    static {
        (a = new HashMap<String, String>()).put("BKL-AL00", "HUAWEI");
        TuSdkVideoSurfaceDecodecOperationPatch.a.put("MHA-AL00", "HUAWEI");
        TuSdkVideoSurfaceDecodecOperationPatch.a.put("FRD-AL10", "HUAWEI");
        TuSdkVideoSurfaceDecodecOperationPatch.a.put("NXT-AL10", "HUAWEI");
        TuSdkVideoSurfaceDecodecOperationPatch.a.put("NOH-AN00", "HUAWEI");
        TuSdkVideoSurfaceDecodecOperationPatch.a.put("PRO 6 Plus", "Meizu");
        TuSdkVideoSurfaceDecodecOperationPatch.a.put("MX6", "Meizu");
        TuSdkVideoSurfaceDecodecOperationPatch.a.put("RedMi Note 7", "Xiaomi");
    }
}
