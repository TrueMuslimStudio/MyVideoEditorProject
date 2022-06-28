// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.skins;

import java.util.Iterator;
import com.example.myvideoeditorapp.kore.seles.sources.SelesPicture;
import java.util.List;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterOption;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;
import com.example.myvideoeditorapp.kore.seles.tusdk.filters.base.TuSDKSurfaceBlurFilter;
import com.example.myvideoeditorapp.kore.seles.tusdk.filters.colors.TuSDKColorMixedFilter;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilterGroup;

public class TuSDKSkinColorFilter extends SelesFilterGroup implements SelesParameters.FilterParameterInterface, SelesParameters.FilterTexturesInterface
{
    private float a;
    private float b;
    private float c;
    private float d;
    private float e;
    private float f;
    private TuSDKColorMixedFilter g;
    private TuSDKSurfaceBlurFilter h;
    private SelesFilter i;
    private TuSDKSkinColorMixedFilter j;
    
    public TuSDKSkinColorFilter() {
        this.addFilter(this.g = new TuSDKColorMixedFilter());
        (this.h = new TuSDKSurfaceBlurFilter()).setScale(0.5f);
        (this.i = new SelesFilter()).setScale(0.5f);
        this.addFilter(this.j = new TuSDKSkinColorMixedFilter());
        this.j.addTarget(this.g, 0);
        this.h.addTarget(this.j, 1);
        this.i.addTarget(this.j, 2);
        this.setInitialFilters(this.h, this.i, this.j);
        this.setTerminalFilter(this.g);
        this.setSmoothing(0.8f);
        this.setMixed(0.6f);
        this.setLightLevel(0.4f);
        this.setDetailLevel(0.2f);
    }
    
    public TuSDKSkinColorFilter(final FilterOption filterOption) {
        this();
        if (filterOption != null && filterOption.args != null) {
            if (filterOption.args.containsKey("smoothing")) {
                final float float1 = Float.parseFloat(filterOption.args.get("smoothing"));
                if (float1 > 0.0f) {
                    this.setSmoothing(float1);
                }
            }
            if (filterOption.args.containsKey("mixied")) {
                final float float2 = Float.parseFloat(filterOption.args.get("mixied"));
                if (float2 > 0.0f) {
                    this.setMixed(float2);
                }
            }
            if (filterOption.args.containsKey("lightLevel")) {
                final float float3 = Float.parseFloat(filterOption.args.get("lightLevel"));
                if (float3 > 0.0f) {
                    this.setLightLevel(float3);
                }
            }
            if (filterOption.args.containsKey("detailLevel")) {
                final float float4 = Float.parseFloat(filterOption.args.get("detailLevel"));
                if (float4 > 0.0f) {
                    this.setDetailLevel(float4);
                }
            }
        }
    }
    
    public float getSmoothing() {
        return this.a;
    }
    
    public void setSmoothing(final float a) {
        this.a = a;
        this.j.setIntensity(1.0f - a);
    }
    
    public float getMixed() {
        return this.b;
    }
    
    public void setMixed(final float b) {
        this.b = b;
        this.g.setMixed(this.b);
    }
    
    public float getBlurSize() {
        return this.c;
    }
    
    public void setBlurSize(final float n) {
    }
    
    public float getThresholdLevel() {
        return this.d;
    }
    
    public void setThresholdLevel(final float n) {
    }
    
    public void setLightLevel(final float e) {
        this.e = e;
        this.j.setLightLevel(this.e);
    }
    
    public float getLightLevel() {
        return this.e;
    }
    
    public void setDetailLevel(final float f) {
        this.f = f;
        this.j.setDetailLevel(this.f);
    }
    
    public float getDetailLevel() {
        return this.f;
    }
    
    @Override
    public void appendTextures(final List<SelesPicture> list) {
        if (list == null) {
            return;
        }
        int n = 1;
        for (final SelesPicture selesPicture : list) {
            selesPicture.processImage();
            selesPicture.addTarget(this.g, n);
            ++n;
        }
    }
    
    @Override
    protected SelesParameters initParams(SelesParameters initParams) {
        initParams = super.initParams(initParams);
        final float defaultArg = initParams.getDefaultArg("blurSize");
        if (defaultArg > 0.0f) {
            this.setBlurSize(defaultArg);
        }
        final float defaultArg2 = initParams.getDefaultArg("thresholdLevel");
        if (defaultArg2 > 0.0f) {
            this.setThresholdLevel(defaultArg2);
        }
        initParams.appendFloatArg("smoothing", this.a, 0.0f, 1.0f);
        initParams.appendFloatArg("mixied", this.getMixed());
        if (initParams.getDefaultArg("skinColor") > 0.0f) {
            this.j.setEnableSkinColorDetection(initParams.getDefaultArg("skinColor"));
        }
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
        else if (filterArg.equalsKey("mixied")) {
            this.setMixed(filterArg.getValue());
        }
        else if (!filterArg.equalsKey("blurSize")) {
            if (!filterArg.equalsKey("thresholdLevel")) {
                if (filterArg.equalsKey("lightLevel")) {
                    this.setLightLevel(filterArg.getValue());
                }
                else if (filterArg.equalsKey("detailLevel")) {
                    this.setDetailLevel(filterArg.getValue());
                }
            }
        }
    }
}
