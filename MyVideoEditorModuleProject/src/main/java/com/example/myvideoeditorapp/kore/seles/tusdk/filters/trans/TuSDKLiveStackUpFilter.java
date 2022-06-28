// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.trans;

import java.nio.Buffer;
import android.opengl.GLES20;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;

public class TuSDKLiveStackUpFilter extends SelesFilter implements SelesParameters.FilterParameterInterface
{
    private static int b;
    private int c;
    private FloatBuffer d;
    private IntBuffer e;
    private int f;
    private int g;
    private SelesFramebuffer h;
    private FloatBuffer i;
    private FloatBuffer j;
    private float[] k;
    private float[] l;
    private long m;
    private float n;
    private long o;
    private float p;
    int[] a;
    private static float q;
    
    public TuSDKLiveStackUpFilter() {
        super("-strans", "-sstack");
        this.k = new float[] { -1.0f, -1.0f, 0.0f, 1.0f, 1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, -1.0f, 1.0f, 0.0f, 1.0f };
        this.l = new float[] { 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f };
        this.a = new int[] { 0, 1, 2, 0, 3, 2 };
        this.p = TuSDKLiveStackUpFilter.q;
    }
    
    @Override
    protected void onInitOnGLThread() {
        super.onInitOnGLThread();
        this.n = 0.0f;
        this.o = 0L;
        this.m = 0L;
        this.f = this.mFilterProgram.uniformIndex("animationPercent");
        this.mFilterInputTextureUniform = this.mFilterProgram.uniformIndex("inputImageTexture");
        this.g = this.mFilterProgram.uniformIndex("inputImageTexture2");
        (this.e = ByteBuffer.allocateDirect(this.a.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer().put(this.a)).position(0);
        this.initializeAttributes();
    }
    
    @Override
    protected SelesParameters initParams(SelesParameters initParams) {
        initParams = super.initParams(initParams);
        initParams.appendFloatArg("duration", TuSDKLiveStackUpFilter.q, TuSDKLiveStackUpFilter.q, Float.MAX_VALUE);
        return initParams;
    }
    
    @Override
    protected void submitFilterArg(final SelesParameters.FilterArg filterArg) {
        super.submitFilterArg(filterArg);
        if (filterArg.equalsKey("duration")) {
            this.a(filterArg.getValue());
        }
    }
    
    private void a(final float a) {
        this.p = Math.max(a, TuSDKLiveStackUpFilter.q);
    }
    
    @Override
    protected void renderToTexture(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        this.runPendingOnDrawTasks();
        if (this.isPreventRendering()) {
            this.inputFramebufferUnlock();
            return;
        }
        SelesContext.setActiveShaderProgram(this.mFilterProgram);
        (this.mOutputFramebuffer = SelesContext.sharedFramebufferCache().fetchFramebuffer(SelesFramebuffer.SelesFramebufferMode.FBO_AND_TEXTURE, this.sizeOfFBO(), this.getOutputTextureOptions())).activateFramebuffer();
        if (this.mUsingNextFrameForImageCapture) {
            this.mOutputFramebuffer.lock();
        }
        this.a();
        this.setUniformsForProgramAtIndex(0);
        GLES20.glClearColor(this.mBackgroundColorRed, this.mBackgroundColorGreen, this.mBackgroundColorBlue, this.mBackgroundColorAlpha);
        GLES20.glClear(16384);
        this.setFloat(Math.abs(this.n), this.f, this.mFilterProgram);
        GLES20.glActiveTexture(33986);
        GLES20.glBindTexture(3553, this.h.getTexture());
        GLES20.glUniform1i(this.mFilterInputTextureUniform, 2);
        GLES20.glActiveTexture(33987);
        GLES20.glBindTexture(3553, this.mFirstInputFramebuffer.getTexture());
        GLES20.glUniform1i(this.g, 3);
        GLES20.glVertexAttribPointer(this.mFilterPositionAttribute, 4, 5126, false, 0, (Buffer)this.i);
        GLES20.glVertexAttribPointer(this.mFilterTextureCoordinateAttribute, 2, 5126, false, 0, (Buffer)this.j);
        GLES20.glVertexAttribPointer(this.c, 2, 5126, false, 0, (Buffer)this.d);
        GLES20.glDrawElements(4, this.e.limit(), 5125, (Buffer)this.e);
        this.inputFramebufferUnlock();
        this.cacaptureImageBuffer();
    }
    
    @Override
    protected void initializeAttributes() {
        super.initializeAttributes();
        this.mFilterProgram.addAttribute("inputTexture2Coordinate");
        GLES20.glEnableVertexAttribArray(this.c = this.mFilterProgram.attributeIndex("inputTexture2Coordinate"));
    }
    
    @Override
    public void setInputFramebuffer(final SelesFramebuffer selesFramebuffer, final int n) {
        if (selesFramebuffer != null && (this.h == null || !this.h.getSize().equals(selesFramebuffer.getSize()))) {
            (this.h = selesFramebuffer).lock();
            if (this.mFirstInputFramebuffer == null || this.mFirstInputFramebuffer.getSize().equals(selesFramebuffer.getSize())) {
                return;
            }
        }
        if (selesFramebuffer != null) {
            (this.mFirstInputFramebuffer = selesFramebuffer).lock();
        }
    }
    
    @Override
    protected void informTargetsAboutNewFrame(final long m) {
        super.informTargetsAboutNewFrame(this.m = m);
    }
    
    @Override
    protected void inputFramebufferUnlock() {
        super.inputFramebufferUnlock();
    }
    
    private void a() {
        if (this.o == 0L) {
            this.o = this.m;
            this.n = 0.0f;
        }
        else {
            this.n = Math.abs((this.m - this.o) / this.p);
            if (this.n > 1.0f) {
                this.n = 1.0f;
            }
            else if (this.n < 0.0f) {
                this.n = 0.0f;
            }
        }
        final float[] array = { -1.0f, -1.0f, 0.0f, 1.0f, 1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, -1.0f, 1.0f, 0.0f, 1.0f };
        final float[] array2 = { 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f };
        final float[] array3 = array2.clone();
        if (Math.abs(this.n) < 0.5f) {
            final float[] array4 = array2;
            final int n = 0;
            array4[n] += Math.abs(this.n);
            final float[] array5 = array2;
            final int n2 = 2;
            array5[n2] += Math.abs(this.n);
            final float[] array6 = array2;
            final int n3 = 4;
            array6[n3] += Math.abs(this.n);
            final float[] array7 = array2;
            final int n4 = 6;
            array7[n4] += Math.abs(this.n);
            final float[] array8 = array3;
            final int n5 = 0;
            array8[n5] -= Math.abs(this.n);
            final float[] array9 = array3;
            final int n6 = 2;
            array9[n6] -= Math.abs(this.n);
            final float[] array10 = array3;
            final int n7 = 4;
            array10[n7] -= Math.abs(this.n);
            final float[] array11 = array3;
            final int n8 = 6;
            array11[n8] -= Math.abs(this.n);
        }
        else {
            final float[] array12 = array2;
            final int n9 = 0;
            array12[n9] += 1.0f - Math.abs(this.n);
            final float[] array13 = array2;
            final int n10 = 2;
            array13[n10] += 1.0f - Math.abs(this.n);
            final float[] array14 = array2;
            final int n11 = 4;
            array14[n11] += 1.0f - Math.abs(this.n);
            final float[] array15 = array2;
            final int n12 = 6;
            array15[n12] += 1.0f - Math.abs(this.n);
            final float[] array16 = array3;
            final int n13 = 0;
            array16[n13] -= 1.0f - Math.abs(this.n);
            final float[] array17 = array3;
            final int n14 = 2;
            array17[n14] -= 1.0f - Math.abs(this.n);
            final float[] array18 = array3;
            final int n15 = 4;
            array18[n15] -= 1.0f - Math.abs(this.n);
            final float[] array19 = array3;
            final int n16 = 6;
            array19[n16] -= 1.0f - Math.abs(this.n);
        }
        this.i = SelesFilter.buildBuffer(array);
        this.j = SelesFilter.buildBuffer(array2);
        this.d = SelesFilter.buildBuffer(array3);
    }
    
    static {
        TuSDKLiveStackUpFilter.b = 6;
        TuSDKLiveStackUpFilter.q = 1.0E9f;
    }
}
