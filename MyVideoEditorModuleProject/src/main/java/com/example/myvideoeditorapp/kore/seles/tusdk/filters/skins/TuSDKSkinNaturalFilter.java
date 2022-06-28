// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.skins;

import com.example.myvideoeditorapp.kore.seles.filters.SelesThreeInputFilter;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.tusdk.filters.base.TuSDKSurfaceBlurFilter;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilterGroup;

public class TuSDKSkinNaturalFilter extends SelesFilterGroup implements SelesParameters.FilterParameterInterface
{
    private SelesFilter a;
    private TuSDKSurfaceBlurFilter b;
    private TuSDKSkinNaturalMixFilter c;
    private float d;
    private float e;
    private float f;
    
    public TuSDKSkinNaturalFilter() {
        (this.a = new SelesFilter()).setScale(0.5f);
        this.b = new TuSDKSurfaceBlurFilter();
        this.a.addTarget(this.b, 0);
        this.addFilter(this.c = new TuSDKSkinNaturalMixFilter());
        this.b.addTarget(this.c, 1);
        this.a.addTarget(this.c, 2);
        this.setInitialFilters(this.a, this.c);
        this.setTerminalFilter(this.c);
        this.d = 0.8f;
        this.e = 0.0f;
        this.f = 0.0f;
        this.b.setThresholdLevel(4.0f);
    }
    
    public void setSmoothing(final float n) {
        this.d = n;
        this.c.setIntensity(n);
    }
    
    public void setFair(final float n) {
        this.e = n;
        this.c.setFair(n);
    }
    
    private void a(final float n) {
        this.f = n;
        this.c.setRuddy(n);
    }
    
    @Override
    protected SelesParameters initParams(SelesParameters initParams) {
        initParams = super.initParams(initParams);
        initParams.appendFloatArg("smoothing", this.d);
        initParams.appendFloatArg("whitening", this.e);
        initParams.appendFloatArg("ruddy", this.f);
        return initParams;
    }
    
    @Override
    protected void submitFilterArg(final SelesParameters.FilterArg filterArg) {
        if (filterArg == null) {
            return;
        }
        if (filterArg.equalsKey("smoothing")) {
            this.setSmoothing(filterArg.getValue());
        }
        else if (filterArg.equalsKey("whitening")) {
            this.setFair(filterArg.getValue());
        }
        else if (filterArg.equalsKey("ruddy")) {
            this.a(filterArg.getValue());
        }
    }
    
    private class TuSDKSkinNaturalMixFilter extends SelesThreeInputFilter
    {
        private float b;
        private float c;
        private float d;
        private int e;
        private int f;
        private int g;
        
        public TuSDKSkinNaturalMixFilter() {
            super("-ssnf1");
            this.b = 1.0f;
            this.c = 0.0f;
            this.d = 0.0f;
        }
        
        @Override
        protected void onInitOnGLThread() {
            super.onInitOnGLThread();
            this.e = this.mFilterProgram.uniformIndex("uIntensity");
            this.f = this.mFilterProgram.uniformIndex("uFair");
            this.g = this.mFilterProgram.uniformIndex("uRuddy");
            this.setIntensity(this.b);
            this.setFair(this.c);
            this.setRuddy(this.d);
        }
        
        public void setIntensity(final float b) {
            this.setFloat(this.b = b, this.e, this.mFilterProgram);
        }
        
        public void setFair(final float c) {
            this.setFloat(this.c = c, this.f, this.mFilterProgram);
        }
        
        public void setRuddy(final float d) {
            this.setFloat(this.d = d, this.g, this.mFilterProgram);
        }
    }
}
