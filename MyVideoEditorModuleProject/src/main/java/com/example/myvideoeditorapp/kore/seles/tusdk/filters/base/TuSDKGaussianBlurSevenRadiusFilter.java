// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.base;

import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkGPU;

public class TuSDKGaussianBlurSevenRadiusFilter extends TuSDKGaussianBlurFiveRadiusFilter
{
    public TuSDKGaussianBlurSevenRadiusFilter() {
        super("-sgv7", "-sgf7");
    }
    
    public static TuSDKGaussianBlurFiveRadiusFilter hardware(final boolean b) {
        if (b && !TuSdkGPU.lowPerformance()) {
            return new TuSDKGaussianBlurSevenRadiusFilter();
        }
        return new TuSDKGaussianBlurFiveRadiusFilter(true);
    }
}
