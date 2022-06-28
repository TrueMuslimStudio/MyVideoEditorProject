// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.lives;

import com.example.myvideoeditorapp.kore.seles.SelesGLProgram;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import java.nio.FloatBuffer;
import com.example.myvideoeditorapp.kore.struct.TuSdkSizeF;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterOption;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;

public class TuSDKLiveEdgeMagicFilter extends SelesFilter
{
    private int a;
    private int b;
    private float c;
    
    public TuSDKLiveEdgeMagicFilter() {
        super("-ssev1", "-slive02f");
        this.c = 1.6f;
    }
    
    public TuSDKLiveEdgeMagicFilter(final FilterOption filterOption) {
        this();
        if (filterOption != null && filterOption.args != null && filterOption.args.containsKey("edgeStrength")) {
            final float float1 = Float.parseFloat(filterOption.args.get("edgeStrength"));
            if (float1 > 0.0f) {
                this.setEdgeStrength(float1);
            }
        }
    }
    
    @Override
    protected void onInitOnGLThread() {
        super.onInitOnGLThread();
        this.a = this.mFilterProgram.uniformIndex("stepOffset");
        this.b = this.mFilterProgram.uniformIndex("edgeStrength");
        this.setEdgeStrength(this.c);
        this.checkGLError(this.getClass().getSimpleName() + " onInitOnGLThread");
    }
    
    @Override
    public void setupFilterForSize(final TuSdkSize tuSdkSize) {
        super.setupFilterForSize(tuSdkSize);
        if (tuSdkSize == null || !tuSdkSize.isSize()) {
            return;
        }
        this.runOnDraw(new Runnable() {
            @Override
            public void run() {
                TuSDKLiveEdgeMagicFilter.this.setSize(TuSdkSizeF.create(1.0f / tuSdkSize.width, 1.0f / tuSdkSize.height), TuSDKLiveEdgeMagicFilter.this.a, TuSDKLiveEdgeMagicFilter.this.mFilterProgram);
            }
        });
    }
    
    @Override
    protected void renderToTexture(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        super.renderToTexture(floatBuffer, floatBuffer2);
        this.checkGLError(this.getClass().getSimpleName());
        this.captureFilterImage(this.getClass().getSimpleName(), this.mInputTextureSize.width, this.mInputTextureSize.height);
    }
    
    public float getEdgeStrength() {
        return this.c;
    }
    
    public void setEdgeStrength(final float c) {
        this.setFloat(this.c = c, this.b, this.mFilterProgram);
    }
    
    @Override
    protected SelesParameters initParams(SelesParameters initParams) {
        initParams = super.initParams(initParams);
        initParams.appendFloatArg("edgeStrength", this.getEdgeStrength(), 0.0f, 4.0f);
        return initParams;
    }
    
    @Override
    protected void submitFilterArg(final SelesParameters.FilterArg filterArg) {
        if (filterArg == null) {
            return;
        }
        if (filterArg.equalsKey("edgeStrength")) {
            this.setEdgeStrength(filterArg.getValue());
        }
    }
}
