// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.cosmetic;

import com.example.myvideoeditorapp.kore.seles.SelesFramebufferCache;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import java.nio.Buffer;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.utils.TLog;
import android.opengl.GLES20;
import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.filters.SelesTwoInputFilter;

public class CosmeticBlushFilter extends SelesTwoInputFilter implements SelesParameters.FilterFacePositionInterface
{
    private CosmeticBlushModel a;
    private FaceAligment[] b;
    private TuSDKCosmeticImage c;
    private boolean d;
    private final Object e;
    private FloatBuffer f;
    private FloatBuffer g;
    private FloatBuffer h;
    private IntBuffer i;
    private int j;
    private float k;
    
    public CosmeticBlushFilter() {
        super("-scosv1", "-scosf2");
        this.d = false;
        this.e = new Object();
        this.disableSecondFrameCheck();
        this.a = new CosmeticBlushModel();
        this.k = 1.0f;
    }
    
    @Override
    protected void onInitOnGLThread() {
        super.onInitOnGLThread();
        this.f = ByteBuffer.allocateDirect(CosmeticBlushModel.COSMETIC_BLUSH_MATERIAL_POINTS_LENGTH * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.g = ByteBuffer.allocateDirect(CosmeticBlushModel.COSMETIC_BLUSH_MATERIAL_POINTS_LENGTH * 3 / 2 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.h = ByteBuffer.allocateDirect(CosmeticBlushModel.COSMETIC_BLUSH_MATERIAL_POINTS_LENGTH * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.i = ByteBuffer.allocateDirect(CosmeticBlushModel.COSMETIC_BLUSH_TRIANGLES_MAP_LENGTH * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        this.a.setTextureCoordinate2(this.h);
        this.h.position(0);
        this.a.setElementIndices(this.i);
        this.i.position(0);
        this.j = this.mFilterProgram.uniformIndex("alpha");
    }
    
    private void a() {
        GLES20.glUniform1f(this.j, this.k);
    }
    
    @Override
    protected void inputFramebufferBindTexture() {
        GLES20.glActiveTexture(33986);
        GLES20.glBindTexture(3553, (this.mFirstInputFramebuffer == null) ? 0 : this.mFirstInputFramebuffer.getTexture());
        GLES20.glUniform1i(this.mFilterInputTextureUniform, 2);
        if (this.c == null) {
            TLog.e("cosmetic blush filter error: matrial is null pointer", new Object[0]);
            return;
        }
        GLES20.glActiveTexture(33987);
        GLES20.glBindTexture(3553, this.c.getTextureID());
        GLES20.glUniform1i(this.mFilterInputTextureUniform2, 3);
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
    
    public void updateSticker(final TuSDKCosmeticImage c) {
        this.c = c;
    }
    
    @Override
    protected SelesParameters initParams(SelesParameters initParams) {
        initParams = super.initParams(initParams);
        initParams.appendFloatArg("blushAlpha", this.k);
        return initParams;
    }
    
    @Override
    protected void submitFilterArg(final SelesParameters.FilterArg filterArg) {
        if (filterArg == null) {
            return;
        }
        if (filterArg.equalsKey("blushAlpha")) {
            this.k = filterArg.getValue();
        }
    }
}
