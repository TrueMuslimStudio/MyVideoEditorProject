// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.colors;

import com.example.myvideoeditorapp.kore.seles.tusdk.FilterOption;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.filters.SelesTwoInputFilter;

public class TuSDKColorMixedFilter extends SelesTwoInputFilter implements SelesParameters.FilterParameterInterface
{
    private float a;
    private int b;
    
    public TuSDKColorMixedFilter() {
        super("-sc1");
        this.a = 1.0f;
        this.disableSecondFrameCheck();
    }
    
    public TuSDKColorMixedFilter(final FilterOption filterOption) {
        this();
        if (filterOption != null && filterOption.args != null && filterOption.args.containsKey("mixied")) {
            final float float1 = Float.parseFloat(filterOption.args.get("mixied"));
            if (float1 > 0.0f) {
                this.setMixed(float1);
            }
        }
    }
    
    @Override
    protected void onInitOnGLThread() {
        super.onInitOnGLThread();
        this.b = this.mFilterProgram.uniformIndex("mixturePercent");
        this.setMixed(this.a);
    }
    
    public float getMixed() {
        return this.a;
    }
    
    public void setMixed(final float a) {
        this.setFloat(this.a = a, this.b, this.mFilterProgram);
    }
    
    @Override
    protected SelesParameters initParams(SelesParameters initParams) {
        initParams = super.initParams(initParams);
        initParams.appendFloatArg("mixied", this.getMixed());
        return initParams;
    }
    
    @Override
    protected void submitFilterArg(final SelesParameters.FilterArg filterArg) {
        if (filterArg == null) {
            return;
        }
        if (filterArg.equalsKey("mixied")) {
            this.setMixed(filterArg.getValue());
        }
    }
}
