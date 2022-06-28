// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.skins;

import com.example.myvideoeditorapp.kore.seles.filters.SelesThreeInputFilter;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.tusdk.filters.base.TuSDKBilateralFilter;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilterGroup;

public class TuSDKSkinMoistFilter extends SelesFilterGroup implements SelesParameters.FilterParameterInterface
{
    private SelesFilter a;
    private TuSDKBilateralFilter b;
    private TuSDKSkinMoistMixFilter c;
    private float d;
    private float e;
    private float f;
    
    public TuSDKSkinMoistFilter() {
        (this.a = new SelesFilter()).setScale(0.5f);
        this.b = new TuSDKBilateralFilter();
        this.a.addTarget(this.b, 0);
        this.addFilter(this.c = new TuSDKSkinMoistMixFilter());
        this.b.addTarget(this.c, 1);
        this.a.addTarget(this.c, 2);
        this.setInitialFilters(this.a, this.c);
        this.setTerminalFilter(this.c);
        this.setSmoothing(0.8f);
        this.setFair(0.0f);
        this.a(0.0f);
        this.b.setSigmaS(5.0f);
        this.b.setSigmaI(0.25f);
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
    
    private class TuSDKSkinMoistMixFilter extends SelesThreeInputFilter
    {
        private int b;
        private int c;
        private int d;
        private float e;
        private float f;
        private float g;
        
        public TuSDKSkinMoistMixFilter() {
            super("-ssmf1");
            this.e = 1.0f;
            this.f = 0.0f;
            this.g = 0.0f;
        }
        
        @Override
        protected void onInitOnGLThread() {
            super.onInitOnGLThread();
            this.b = this.mFilterProgram.uniformIndex("uIntensity");
            this.c = this.mFilterProgram.uniformIndex("uFair");
            this.d = this.mFilterProgram.uniformIndex("uRuddy");
            this.setIntensity(this.e);
            this.setFair(this.f);
            this.setRuddy(this.g);
        }
        
        public void setIntensity(final float e) {
            this.setFloat(this.e = e, this.b, this.mFilterProgram);
        }
        
        public void setFair(final float f) {
            this.setFloat(this.f = f, this.c, this.mFilterProgram);
        }
        
        public void setRuddy(final float g) {
            this.setFloat(this.g = g, this.d, this.mFilterProgram);
        }
    }
}
