// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.cosmetic;

import com.example.myvideoeditorapp.kore.seles.SelesFramebufferCache;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import java.nio.Buffer;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import java.util.Iterator;
import android.opengl.GLES20;
import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import java.util.Map;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.filters.SelesTwoInputFilter;

public class CosmeticEyePartFilter extends SelesTwoInputFilter implements SelesParameters.FilterFacePositionInterface
{
    private CosmeticEyePartModel a;
    private FaceAligment[] b;
    private Map<CosmeticEyePartType, TuSDKCosmeticImage> c;
    private boolean d;
    private final Object e;
    private FloatBuffer f;
    private FloatBuffer g;
    private FloatBuffer h;
    private IntBuffer i;
    private int j;
    private int k;
    private int l;
    private int m;
    private float[] n;
    private float[] o;
    
    public CosmeticEyePartFilter() {
        super("-scosv1", "-scosf4");
        this.d = false;
        this.e = new Object();
        this.n = new float[3];
        this.o = new float[3];
        this.disableSecondFrameCheck();
        this.c = new HashMap<CosmeticEyePartType, TuSDKCosmeticImage>();
        this.a = new CosmeticEyePartModel();
        this.n[0] = 1.0f;
        this.n[1] = 1.0f;
        this.n[2] = 1.0f;
    }
    
    @Override
    protected void onInitOnGLThread() {
        super.onInitOnGLThread();
        this.f = ByteBuffer.allocateDirect(CosmeticEyePartModel.COSMETIC_EYEPART_MATERIAL_POINTS_LENGTH * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.g = ByteBuffer.allocateDirect(CosmeticEyePartModel.COSMETIC_EYEPART_MATERIAL_POINTS_LENGTH * 3 / 2 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.h = ByteBuffer.allocateDirect(CosmeticEyePartModel.COSMETIC_EYEPART_MATERIAL_POINTS_LENGTH * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.i = ByteBuffer.allocateDirect(CosmeticEyePartModel.COSMETIC_EYEPART_TRIANGLES_MAP_LENGTH * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        this.a.setTextureCoordinate2(this.h);
        this.h.position(0);
        this.a.setElementIndices(this.i);
        this.i.position(0);
        this.j = this.mFilterProgram.uniformIndex("inputImageTexture3");
        this.k = this.mFilterProgram.uniformIndex("inputImageTexture4");
        this.l = this.mFilterProgram.uniformIndex("alphas");
        this.m = this.mFilterProgram.uniformIndex("enables");
    }
    
    private void a() {
        GLES20.glUniform3fv(this.l, 1, this.n, 0);
        GLES20.glUniform3fv(this.m, 1, this.o, 0);
    }
    
    @Override
    protected void inputFramebufferBindTexture() {
        GLES20.glActiveTexture(33986);
        GLES20.glBindTexture(3553, (this.mFirstInputFramebuffer == null) ? 0 : this.mFirstInputFramebuffer.getTexture());
        GLES20.glUniform1i(this.mFilterInputTextureUniform, 2);
        for (final Map.Entry<CosmeticEyePartType, TuSDKCosmeticImage> entry : this.c.entrySet()) {
            final CosmeticEyePartType cosmeticEyePartType = entry.getKey();
            final TuSDKCosmeticImage tuSDKCosmeticImage = entry.getValue();
            if (cosmeticEyePartType == CosmeticEyePartType.COSMETIC_EYESHADOW_TYPE) {
                GLES20.glActiveTexture(33987);
                GLES20.glBindTexture(3553, tuSDKCosmeticImage.getTextureID());
                GLES20.glUniform1i(this.mFilterInputTextureUniform2, 3);
            }
            else if (cosmeticEyePartType == CosmeticEyePartType.COSMETIC_EYELINE_TYPE) {
                GLES20.glActiveTexture(33988);
                GLES20.glBindTexture(3553, tuSDKCosmeticImage.getTextureID());
                GLES20.glUniform1i(this.j, 4);
            }
            else {
                if (cosmeticEyePartType != CosmeticEyePartType.COSMETIC_EYELASH_TYPE) {
                    continue;
                }
                GLES20.glActiveTexture(33989);
                GLES20.glBindTexture(3553, tuSDKCosmeticImage.getTextureID());
                GLES20.glUniform1i(this.k, 5);
            }
        }
    }
    
    @Override
    protected void renderToTexture(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        this.runPendingOnDrawTasks();
        if (this.isPreventRendering()) {
            this.inputFramebufferUnlock();
            return;
        }
        if (this.b == null) {
            SelesContext.setActiveShaderProgram(this.mFilterProgram);
            (this.mOutputFramebuffer = this.mFirstInputFramebuffer).activateFramebuffer();
            if (this.mUsingNextFrameForImageCapture) {
                this.mOutputFramebuffer.lock();
            }
            this.captureFilterImage(this.getClass().getSimpleName(), this.mInputTextureSize.width, this.mInputTextureSize.height);
            this.cacaptureImageBuffer();
            return;
        }
        final FaceAligment[] b;
        synchronized (this.e) {
            b = this.b;
        }
        final TuSdkSize sizeOfFBO = this.sizeOfFBO();
        for (int n = 0; this.d && n < b.length; ++n) {
            this.a.updateFace(b[n], sizeOfFBO);
            this.a.setPosition(this.f);
            this.f.position(0);
            this.a.setTextureCoordinate(this.g);
            this.g.position(0);
            SelesContext.setActiveShaderProgram(this.mFilterProgram);
            final SelesFramebufferCache sharedFramebufferCache = SelesContext.sharedFramebufferCache();
            if (sharedFramebufferCache == null) {
                return;
            }
            if (this.mOutputFramebuffer != null) {
                this.mOutputFramebuffer.unlock();
            }
            (this.mOutputFramebuffer = sharedFramebufferCache.fetchFramebuffer(SelesFramebuffer.SelesFramebufferMode.FBO_AND_TEXTURE, sizeOfFBO, this.getOutputTextureOptions())).activateFramebuffer();
            if (this.mUsingNextFrameForImageCapture && n == this.b.length - 1) {
                this.mOutputFramebuffer.lock();
            }
            this.setUniformsForProgramAtIndex(0);
            GLES20.glEnable(3042);
            GLES20.glBlendFunc(770, 771);
            this.inputFramebufferBindTexture();
            this.a();
            GLES20.glVertexAttribPointer(this.mFilterPositionAttribute, 2, 5126, false, 0, (Buffer)this.f);
            GLES20.glVertexAttribPointer(this.mFilterTextureCoordinateAttribute, 3, 5126, false, 0, (Buffer)this.g);
            GLES20.glVertexAttribPointer(this.mFilterSecondTextureCoordinateAttribute, 2, 5126, false, 0, (Buffer)this.h);
            GLES20.glDrawElements(4, this.i.limit(), 5125, (Buffer)this.i);
            GLES20.glDisable(3042);
            this.captureFilterImage(this.getClass().getSimpleName(), this.mInputTextureSize.width, this.mInputTextureSize.height);
            this.inputFramebufferUnlock();
            this.cacaptureImageBuffer();
            if (n < b.length - 1) {
                this.setInputFramebuffer(this.framebufferForOutput(), 0);
            }
        }
    }
    
    @Override
    public void updateFaceFeatures(final FaceAligment[] b, final float n) {
        if (b == null || b.length < 1) {
            this.d = false;
            this.b = null;
            return;
        }
        synchronized (this.e) {
            this.b = b;
        }
        this.d = true;
    }
    
    public void updateStickers(final CosmeticEyePartType cosmeticEyePartType, final TuSDKCosmeticImage tuSDKCosmeticImage) {
        this.c.put(cosmeticEyePartType, tuSDKCosmeticImage);
        this.o[cosmeticEyePartType.ordinal()] = 1.0f;
    }
    
    public void close(final CosmeticEyePartType cosmeticEyePartType) {
        this.o[cosmeticEyePartType.ordinal()] = 0.0f;
    }
    
    public boolean enable() {
        return this.o[0] == 1.0f || this.o[1] == 1.0f || this.o[2] == 1.0f;
    }
    
    @Override
    protected SelesParameters initParams(SelesParameters initParams) {
        initParams = super.initParams(initParams);
        initParams.appendFloatArg("eyeshadowAlpha", this.n[0]);
        initParams.appendFloatArg("eyelineAlpha", this.n[1]);
        initParams.appendFloatArg("eyeLashAlpha", this.n[2]);
        return initParams;
    }
    
    @Override
    protected void submitFilterArg(final SelesParameters.FilterArg filterArg) {
        if (filterArg == null) {
            return;
        }
        if (filterArg.equalsKey("eyeshadowAlpha")) {
            this.n[0] = filterArg.getValue();
        }
        if (filterArg.equalsKey("eyelineAlpha")) {
            this.n[1] = filterArg.getValue();
        }
        if (filterArg.equalsKey("eyeLashAlpha")) {
            this.n[2] = filterArg.getValue();
        }
    }
    
    public enum CosmeticEyePartType
    {
        COSMETIC_EYESHADOW_TYPE, 
        COSMETIC_EYELINE_TYPE, 
        COSMETIC_EYELASH_TYPE;
    }
}
