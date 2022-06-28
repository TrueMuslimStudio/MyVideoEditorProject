// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.lives;

import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.seles.filters.SelesTwoInputFilter;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterOption;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFrameKeepFilter;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilterGroup;

public class TuSDKLiveMegrimFilter extends SelesFilterGroup
{
    private SelesFrameKeepFilter a;
    private _TuSDKLiveMegrimMixedFilter b;
    private SelesFilter c;
    
    public TuSDKLiveMegrimFilter() {
        this.addFilter(this.b = new _TuSDKLiveMegrimMixedFilter());
        this.addFilter(this.a = new SelesFrameKeepFilter());
        this.a.addTarget(this.b, 1);
        this.b.addTarget(this.a, 0);
        this.setInitialFilters(this.b);
        this.setTerminalFilter(this.b);
        this.setAlpha(1.0f);
    }
    
    public TuSDKLiveMegrimFilter(final FilterOption filterOption) {
        this();
        if (filterOption != null && filterOption.args != null) {
            if (filterOption.args.containsKey("red")) {
                final float float1 = Float.parseFloat(filterOption.args.get("green"));
                if (float1 > 0.0f) {
                    this.setRed(float1);
                }
            }
            if (filterOption.args.containsKey("green")) {
                final float float2 = Float.parseFloat(filterOption.args.get("green"));
                if (float2 > 0.0f) {
                    this.setGreen(float2);
                }
            }
            if (filterOption.args.containsKey("blue")) {
                final float float3 = Float.parseFloat(filterOption.args.get("blue"));
                if (float3 > 0.0f) {
                    this.setBlue(float3);
                }
            }
        }
    }
    
    public void enableSeprarate() {
        if (this.c != null) {
            return;
        }
        this.addFilter(this.c = new SelesFilter());
        this.b.addTarget(this.c, 0);
        this.setTerminalFilter(this.c);
    }
    
    public float[] getColor() {
        return this.b.getMix();
    }
    
    public void setColor(final float[] mix) {
        this.b.setMix(mix);
    }
    
    public float getRed() {
        return this.b.getMix()[0];
    }
    
    public void setRed(final float n) {
        final float[] color = this.getColor();
        color[0] = n;
        this.setColor(color);
    }
    
    public float getGreen() {
        return this.b.getMix()[1];
    }
    
    public void setGreen(final float n) {
        final float[] color = this.getColor();
        color[1] = n;
        this.setColor(color);
    }
    
    public float getBlue() {
        return this.b.getMix()[2];
    }
    
    public void setBlue(final float n) {
        final float[] color = this.getColor();
        color[2] = n;
        this.setColor(color);
    }
    
    public float getAlpha() {
        return this.b.getMix()[3];
    }
    
    public void setAlpha(final float n) {
        final float[] color = this.getColor();
        color[3] = n;
        this.setColor(color);
    }
    
    @Override
    public void addTarget(final SelesContext.SelesInput selesInput, final int n) {
        this.getTerminalFilter().addTarget(selesInput, n);
        if (this.getTerminalFilter() == this.b && !this.getTerminalFilter().targets().contains(this.a)) {
            this.getTerminalFilter().addTarget(this.a, 1);
        }
    }
    
    @Override
    public void newFrameReady(final long n, final int n2) {
        this.a();
        super.newFrameReady(n, n2);
    }
    
    private void a() {
        if (this.getRed() > 0.9f) {
            this.getParameter().setFilterArg("red", this.getRed() - 0.003f);
            this.submitParameter();
        }
        else if (this.getRed() != 0.0f) {
            this.getParameter().setFilterArg("red", this.getRed() - 0.01f);
            this.submitParameter();
        }
        if (this.getBlue() < 0.8f) {
            this.getParameter().setFilterArg("blue", this.getBlue() + 0.003f);
            this.submitParameter();
        }
    }
    
    @Override
    protected SelesParameters initParams(SelesParameters initParams) {
        initParams = super.initParams(initParams);
        initParams.appendFloatArg("strength", this.getAlpha(), 0.0f, 1.0f);
        initParams.appendFloatArg("red", this.getRed(), 0.0f, 1.0f);
        initParams.appendFloatArg("green", this.getGreen(), 0.0f, 1.0f);
        initParams.appendFloatArg("blue", this.getBlue(), 0.0f, 1.0f);
        return initParams;
    }
    
    @Override
    protected void submitFilterArg(final SelesParameters.FilterArg filterArg) {
        if (filterArg == null) {
            return;
        }
        if (filterArg.equalsKey("strength")) {
            this.setAlpha(filterArg.getValue());
        }
        else if (filterArg.equalsKey("red")) {
            this.setRed(filterArg.getValue());
        }
        else if (filterArg.equalsKey("green")) {
            this.setGreen(filterArg.getValue());
        }
        else if (filterArg.equalsKey("blue")) {
            this.setBlue(filterArg.getValue());
        }
    }
    
    private class _TuSDKLiveMegrimMixedFilter extends SelesTwoInputFilter
    {
        private int b;
        private float[] c;
        
        public _TuSDKLiveMegrimMixedFilter() {
            super("-slive04f");
            this.c = new float[] { 0.0f, 0.75f, 0.8f, 1.0f };
            this.disableSecondFrameCheck();
        }
        
        @Override
        protected void onInitOnGLThread() {
            super.onInitOnGLThread();
            this.b = this.mFilterProgram.uniformIndex("mixturePercent");
            ThreadHelper.postDelayed(new Runnable() {
                @Override
                public void run() {
                    _TuSDKLiveMegrimMixedFilter.this.setMix(_TuSDKLiveMegrimMixedFilter.this.c);
                }
            }, 100L);
        }
        
        public float[] getMix() {
            return this.c;
        }
        
        public void setMix(final float[] c) {
            this.setVec4(this.c = c, this.b, this.mFilterProgram);
        }
    }
}
