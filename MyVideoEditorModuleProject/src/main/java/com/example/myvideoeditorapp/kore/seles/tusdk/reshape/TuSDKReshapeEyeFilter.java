// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.reshape;

import com.example.myvideoeditorapp.kore.seles.SelesFramebufferCache;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import java.nio.Buffer;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import android.opengl.GLES20;
import java.util.Iterator;
import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import com.example.myvideoeditorapp.kore.seles.sources.SelesPicture;
import java.util.List;
import com.example.myvideoeditorapp.kore.secret.SdkValid;
import java.util.ArrayList;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.filters.SelesTwoInputFilter;

public class TuSDKReshapeEyeFilter extends SelesTwoInputFilter implements SelesParameters.FilterFacePositionInterface, SelesParameters.FilterParameterInterface
{
    private TuSDKReshapeEyeModel a;
    private FaceAligment[] b;
    private SelesFramebuffer c;
    private SelesFramebuffer d;
    private boolean e;
    private final Object f;
    private FloatBuffer g;
    private FloatBuffer h;
    private FloatBuffer i;
    private IntBuffer j;
    private int k;
    private int l;
    private int m;
    private float n;
    private float o;
    
    public TuSDKReshapeEyeFilter() {
        super("-scosv1", "-sreshape2");
        this.e = false;
        this.f = new Object();
        this.disableSecondFrameCheck();
        this.a = new TuSDKReshapeEyeModel();
        this.n = 0.0f;
        this.o = 0.0f;
    }
    
    @Override
    protected void onInitOnGLThread() {
        super.onInitOnGLThread();
        final ArrayList<String> list = new ArrayList<String>();
        list.add("2b0d398ea4db356ce56429bafae108e9");
        list.add("1505c12ce9afbeae966ddb22de154586");
        final List<SelesPicture> internalTextures = SdkValid.shared.readInternalTextures(list);
        final Iterator<SelesPicture> iterator = internalTextures.iterator();
        while (iterator.hasNext()) {
            iterator.next().runOnDrawSync();
        }
        this.c = internalTextures.get(0).framebufferForOutput();
        this.d = internalTextures.get(1).framebufferForOutput();
        this.g = ByteBuffer.allocateDirect(TuSDKReshapeEyeModel.RESHAPE_MATERIAL_POINTS_LENGTH * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.h = ByteBuffer.allocateDirect(TuSDKReshapeEyeModel.RESHAPE_MATERIAL_POINTS_LENGTH * 3 / 2 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.i = ByteBuffer.allocateDirect(TuSDKReshapeEyeModel.RESHAPE_MATERIAL_POINTS_LENGTH * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.j = ByteBuffer.allocateDirect(TuSDKReshapeEyeModel.RESHAPE_TRIANGLES_MAP_LENGTH * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        this.a.setTextureCoordinate2(this.i);
        this.i.position(0);
        this.a.setElementIndices(this.j);
        this.j.position(0);
        this.k = this.mFilterProgram.uniformIndex("inputImageTexture3");
        this.l = this.mFilterProgram.uniformIndex("eyelidAlpha");
        this.m = this.mFilterProgram.uniformIndex("eyemazingAlpha");
    }
    
    private void a() {
        GLES20.glUniform1f(this.l, this.n);
        GLES20.glUniform1f(this.m, this.o);
    }
    
    @Override
    protected void inputFramebufferBindTexture() {
        GLES20.glActiveTexture(33986);
        GLES20.glBindTexture(3553, (this.mFirstInputFramebuffer == null) ? 0 : this.mFirstInputFramebuffer.getTexture());
        GLES20.glUniform1i(this.mFilterInputTextureUniform, 2);
        GLES20.glActiveTexture(33987);
        GLES20.glBindTexture(3553, this.c.getTexture());
        GLES20.glUniform1i(this.mFilterInputTextureUniform2, 3);
        GLES20.glActiveTexture(33988);
        GLES20.glBindTexture(3553, this.d.getTexture());
        GLES20.glUniform1i(this.k, 4);
    }
    
    public boolean isCloseRender() {
        return this.n < 0.01f && this.o < 0.01f;
    }
    
    @Override
    protected void renderToTexture(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        this.runPendingOnDrawTasks();
        if (this.isPreventRendering()) {
            this.inputFramebufferUnlock();
            return;
        }
        if (this.b == null || this.isCloseRender()) {
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
    
    @Override
    protected SelesParameters initParams(SelesParameters initParams) {
        initParams = super.initParams(initParams);
        initParams.appendFloatArg("eyelidAlpha", this.n);
        initParams.appendFloatArg("eyemazingAlpha", this.o);
        return initParams;
    }
    
    @Override
    protected void submitFilterArg(final SelesParameters.FilterArg filterArg) {
        if (filterArg == null) {
            return;
        }
        if (filterArg.equalsKey("eyelidAlpha")) {
            this.n = filterArg.getValue();
        }
        if (filterArg.equalsKey("eyemazingAlpha")) {
            this.o = filterArg.getValue();
        }
    }
    
    public enum TuSDKReshapeEyeType
    {
        RESHAPE_EYELID_TYPE, 
        RESHAPE_EYEMAZING_TYPE;
    }
}
