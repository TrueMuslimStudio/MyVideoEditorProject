// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.skins;

import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilterGroup;

public class TuSDKGPUSkinGaussianBlurSmoothFilter extends SelesFilterGroup implements SelesParameters.FilterParameterInterface
{
    TuSDKGaussianBlurSevenRadiusSmoothFilter a;
    TuSDKLuminanceRangeFilter b;
    
    public TuSDKGPUSkinGaussianBlurSmoothFilter() {
        (this.a = new TuSDKGaussianBlurSevenRadiusSmoothFilter()).setScale(0.5f);
        this.b = new TuSDKLuminanceRangeFilter();
        this.a.addTarget(this.b);
        this.setInitialFilters(this.a);
        this.setTerminalFilter(this.b);
        this.setBlurSize(0.7f);
        this.setSaturation(1.0f);
    }
    
    public void setBlurSize(final float n) {
        this.a.setBlurSize(n * 3.0f);
        this.b.setRangeReductionFactor(n * 0.8f);
    }
    
    public void setSaturation(final float saturation) {
        this.b.setSaturation(saturation);
    }
    
    @Override
    protected SelesParameters initParams(SelesParameters initParams) {
        initParams = super.initParams(initParams);
        initParams.appendFloatArg("mixied", this.a.getBlurSize() / 3.0f, 0.0f, 1.0f);
        initParams.appendFloatArg("saturation", this.b.getSaturation(), 0.0f, 2.0f);
        return initParams;
    }
    
    @Override
    protected void submitFilterArg(final SelesParameters.FilterArg filterArg) {
        if (filterArg == null) {
            return;
        }
        if (filterArg.equalsKey("mixied")) {
            this.setBlurSize(filterArg.getValue());
        }
        if (filterArg.equalsKey("saturation")) {
            this.setSaturation(filterArg.getValue());
        }
    }
}
