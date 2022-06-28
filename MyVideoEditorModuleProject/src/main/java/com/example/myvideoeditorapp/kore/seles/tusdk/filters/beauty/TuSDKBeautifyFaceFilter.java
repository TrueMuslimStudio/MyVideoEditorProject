// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.beauty;

import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilterGroup;

public class TuSDKBeautifyFaceFilter extends SelesFilterGroup implements SelesParameters.FilterParameterInterface
{
    private SelesFilter a;
    private TuSDKBoxBlurFilter b;
    private TuSDKBoxBlur2Filter c;
    private TuSDKBoxBlurAlphaFilter d;
    private TuSDKBoxBlurAlphaFilter e;
    private TuSDKFaceSmoothFilter f;
    private TuSDKFaceWhitenFilter g;
    private float h;
    private float i;
    private float j;
    
    public TuSDKBeautifyFaceFilter() {
        (this.a = new SelesFilter()).setScale(0.25f);
        this.b = new TuSDKBoxBlurFilter();
        this.a.addTarget(this.b, 0);
        this.c = new TuSDKBoxBlur2Filter();
        this.a.addTarget(this.c, 0);
        this.b.addTarget(this.c, 1);
        this.d = new TuSDKBoxBlurAlphaFilter(TuSDKBoxBlurAlphaFilter.BoxBlurAlphaOrientation.HORIZONTAL);
        this.c.addTarget(this.d, 0);
        this.e = new TuSDKBoxBlurAlphaFilter(TuSDKBoxBlurAlphaFilter.BoxBlurAlphaOrientation.VERTICAL);
        this.d.addTarget(this.e, 0);
        this.f = new TuSDKFaceSmoothFilter();
        this.e.addTarget(this.f, 1);
        this.g = new TuSDKFaceWhitenFilter();
        this.f.addTarget(this.g, 0);
        this.addFilter(this.g);
        this.setInitialFilters(this.a, this.f);
        this.setTerminalFilter(this.g);
        this.a(0.8f);
        this.b(0.3f);
        this.c(0.4f);
    }
    
    void a(final float n) {
        this.h = n;
        this.f.setSmooth(n);
    }
    
    void b(final float n) {
        this.i = n;
        this.g.setWhiten(n);
    }
    
    void c(final float n) {
        this.j = n;
        this.f.setSharpen(n);
    }
    
    @Override
    protected SelesParameters initParams(SelesParameters initParams) {
        initParams = super.initParams(initParams);
        initParams.appendFloatArg("smoothing", this.h);
        initParams.appendFloatArg("whitening", this.i);
        initParams.appendFloatArg("sharpen", this.j);
        return initParams;
    }
    
    @Override
    protected void submitFilterArg(final SelesParameters.FilterArg filterArg) {
        if (filterArg == null) {
            return;
        }
        if (filterArg.equalsKey("smoothing")) {
            this.a(filterArg.getValue());
        }
        else if (filterArg.equalsKey("whitening")) {
            this.b(filterArg.getValue());
        }
        else if (filterArg.equalsKey("sharpen")) {
            this.c(filterArg.getValue());
        }
    }
}
