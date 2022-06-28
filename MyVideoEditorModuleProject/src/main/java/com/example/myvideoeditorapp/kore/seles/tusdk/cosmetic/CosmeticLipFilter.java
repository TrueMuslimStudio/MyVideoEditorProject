// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.cosmetic;

import com.example.myvideoeditorapp.kore.seles.tusdk.liveSticker.TuSDKLiveStickerImage;
import com.example.myvideoeditorapp.kore.seles.SelesFramebufferCache;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import java.nio.Buffer;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.utils.TLog;
import android.opengl.GLES20;
import java.util.Iterator;
import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import java.util.HashMap;
import com.example.myvideoeditorapp.kore.seles.sources.SelesPicture;
import java.util.List;
import com.example.myvideoeditorapp.kore.secret.SdkValid;
import java.util.ArrayList;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import java.util.Map;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.filters.SelesTwoInputFilter;

public class CosmeticLipFilter extends SelesTwoInputFilter implements SelesParameters.FilterFacePositionInterface
{
    private CosmeticLipModel a;
    private FaceAligment[] b;
    private SelesFramebuffer c;
    private Map<CosmeticLipType, SelesFramebuffer> d;
    private boolean e;
    private final Object f;
    private FloatBuffer g;
    private FloatBuffer h;
    private FloatBuffer i;
    private IntBuffer j;
    private int k;
    private int l;
    private int m;
    private int n;
    private float o;
    private CosmeticLipType p;
    private float[] q;
    
    public CosmeticLipFilter() {
        super("-scosv1", "-scosf1");
        this.e = false;
        this.f = new Object();
        this.q = new float[3];
        this.disableSecondFrameCheck();
        this.a = new CosmeticLipModel();
        this.o = 1.0f;
        this.p = CosmeticLipType.COSMETIC_WUMIAN_TYPE;
    }
    
    @Override
    protected void onInitOnGLThread() {
        super.onInitOnGLThread();
        final ArrayList<String> list = new ArrayList<String>();
        list.add("5b18ee87d69366ee95628a2385119574");
        list.add("c135bd5e3a7edc4dfc82b824baffecb4");
        list.add("f01fbd44543a93983b64c791f371abfa");
        final List<SelesPicture> internalTextures = SdkValid.shared.readInternalTextures(list);
        final Iterator<SelesPicture> iterator = internalTextures.iterator();
        while (iterator.hasNext()) {
            iterator.next().runOnDrawSync();
        }
        this.c = internalTextures.get(0).framebufferForOutput();
        (this.d = new HashMap<CosmeticLipType, SelesFramebuffer>()).put(CosmeticLipType.COSMETIC_ZIRUN_TYPE, internalTextures.get(1).framebufferForOutput());
        this.d.put(CosmeticLipType.COSMETIC_SHUIRUN_TYPE, internalTextures.get(2).framebufferForOutput());
        this.g = ByteBuffer.allocateDirect(CosmeticLipModel.COSMETIC_LIP_MATERIAL_POINTS_LENGTH * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.h = ByteBuffer.allocateDirect(CosmeticLipModel.COSMETIC_LIP_MATERIAL_POINTS_LENGTH * 3 / 2 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.i = ByteBuffer.allocateDirect(CosmeticLipModel.COSMETIC_LIP_MATERIAL_POINTS_LENGTH * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.j = ByteBuffer.allocateDirect(CosmeticLipModel.COSMETIC_LIP_TRIANGLES_MAP_LENGTH * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        this.a.setTextureCoordinate2(this.i);
        this.i.position(0);
        this.a.setElementIndices(this.j);
        this.j.position(0);
        this.n = this.mFilterProgram.uniformIndex("inputImageTexture3");
        this.k = this.mFilterProgram.uniformIndex("alpha");
        this.l = this.mFilterProgram.uniformIndex("type");
        this.m = this.mFilterProgram.uniformIndex("lipColor");
    }
    
    private void a() {
        GLES20.glUniform1f(this.k, this.o);
        GLES20.glUniform1i(this.l, this.p.ordinal());
        GLES20.glUniform3fv(this.m, 1, this.q, 0);
    }
    
    @Override
    protected void inputFramebufferBindTexture() {
        GLES20.glActiveTexture(33986);
        GLES20.glBindTexture(3553, (this.mFirstInputFramebuffer == null) ? 0 : this.mFirstInputFramebuffer.getTexture());
        GLES20.glUniform1i(this.mFilterInputTextureUniform, 2);
        if (this.c == null) {
            TLog.e("cosmetic lip filter error: mask is null pointer", new Object[0]);
            return;
        }
        GLES20.glActiveTexture(33987);
        GLES20.glBindTexture(3553, this.c.getTexture());
        GLES20.glUniform1i(this.mFilterInputTextureUniform2, 3);
        if (this.p == CosmeticLipType.COSMETIC_ZIRUN_TYPE || this.p == CosmeticLipType.COSMETIC_SHUIRUN_TYPE) {
            if (this.d == null) {
                TLog.e("cosmetic lip filter error: matrial is null pointer", new Object[0]);
                return;
            }
            GLES20.glActiveTexture(33988);
            GLES20.glBindTexture(3553, this.d.get(this.p).getTexture());
            GLES20.glUniform1i(this.n, 4);
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
        synchronized (this.f) {
            b = this.b;
        }
        final TuSdkSize sizeOfFBO = this.sizeOfFBO();
        for (int n = 0; this.e && n < b.length; ++n) {
            this.a.updateFace(b[n], sizeOfFBO);
            this.a.setPosition(this.g);
            this.g.position(0);
            this.a.setTextureCoordinate(this.h);
            this.h.position(0);
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
            GLES20.glVertexAttribPointer(this.mFilterPositionAttribute, 2, 5126, false, 0, (Buffer)this.g);
            GLES20.glVertexAttribPointer(this.mFilterTextureCoordinateAttribute, 3, 5126, false, 0, (Buffer)this.h);
            GLES20.glVertexAttribPointer(this.mFilterSecondTextureCoordinateAttribute, 2, 5126, false, 0, (Buffer)this.i);
            GLES20.glDrawElements(4, this.j.limit(), 5125, (Buffer)this.j);
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
            this.e = false;
            this.b = null;
            return;
        }
        synchronized (this.f) {
            this.b = b;
        }
        this.e = true;
    }
    
    public void updateSticker(final TuSDKLiveStickerImage tuSDKLiveStickerImage) {
    }
    
    public void updateColor(final int[] array) {
        this.q[0] = array[0] / 255.0f;
        this.q[1] = array[1] / 255.0f;
        this.q[2] = array[2] / 255.0f;
    }
    
    public void updateType(final CosmeticLipType p) {
        this.p = p;
    }
    
    @Override
    protected SelesParameters initParams(SelesParameters initParams) {
        initParams = super.initParams(initParams);
        initParams.appendFloatArg("lipAlpha", this.o);
        return initParams;
    }
    
    @Override
    protected void submitFilterArg(final SelesParameters.FilterArg filterArg) {
        if (filterArg == null) {
            return;
        }
        if (filterArg.equalsKey("lipAlpha")) {
            this.o = filterArg.getValue();
        }
    }
    
    public enum CosmeticLipType
    {
        COSMETIC_WUMIAN_TYPE, 
        COSMETIC_ZIRUN_TYPE, 
        COSMETIC_SHUIRUN_TYPE;
    }
}
