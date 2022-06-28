// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.output;

import android.opengl.GLES20;

import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.seles.SelesFramebufferCache;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;

import java.nio.Buffer;
import java.nio.FloatBuffer;

public class SelesTerminalFilter extends SelesFilter
{
    private boolean a;
    
    public SelesTerminalFilter() {
        this.a = false;
    }
    
    @Override
    public void setInputSize(final TuSdkSize tuSdkSize, final int n) {
        if (tuSdkSize == null) {
            return;
        }
        super.setInputSize(tuSdkSize, n);
        this.a = !tuSdkSize.equals(this.mInputTextureSize);
    }
    
    @Override
    protected void onInitOnGLThread() {
        super.onInitOnGLThread();
        this.checkGLError(this.getClass().getSimpleName() + " onInitOnGLThread");
    }
    
    @Override
    public void newFrameReady(final long n, final int n2) {
        super.newFrameReady(n, n2);
    }
    
    @Override
    protected void renderToTexture(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        this.runPendingOnDrawTasks();
        if (this.isPreventRendering()) {
            this.inputFramebufferUnlock();
            return;
        }
        SelesContext.setActiveShaderProgram(this.mFilterProgram);
        this.checkGLError(this.getClass().getSimpleName() + " setActiveShaderProgram");
        if (this.mOutputFramebuffer == null || this.a) {
            this.a();
            final TuSdkSize sizeOfFBO = this.sizeOfFBO();
            final SelesFramebufferCache sharedFramebufferCache = SelesContext.sharedFramebufferCache();
            if (sharedFramebufferCache == null) {
                return;
            }
            (this.mOutputFramebuffer = sharedFramebufferCache.fetchFramebuffer(SelesFramebuffer.SelesFramebufferMode.FBO_AND_TEXTURE, sizeOfFBO, this.getOutputTextureOptions())).disableReferenceCounting();
        }
        this.mOutputFramebuffer.activateFramebuffer();
        this.checkGLError(this.getClass().getSimpleName() + " activateFramebuffer");
        if (this.mUsingNextFrameForImageCapture) {
            this.mOutputFramebuffer.lock();
        }
        this.setUniformsForProgramAtIndex(0);
        GLES20.glClearColor(this.mBackgroundColorRed, this.mBackgroundColorGreen, this.mBackgroundColorBlue, this.mBackgroundColorAlpha);
        GLES20.glClear(16384);
        this.inputFramebufferBindTexture();
        this.checkGLError(this.getClass().getSimpleName() + " bindFramebuffer");
        GLES20.glVertexAttribPointer(this.mFilterPositionAttribute, 2, 5126, false, 0, (Buffer)floatBuffer);
        GLES20.glVertexAttribPointer(this.mFilterTextureCoordinateAttribute, 2, 5126, false, 0, (Buffer)floatBuffer2);
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glFinish();
        this.inputFramebufferUnlock();
        this.captureFilterImage(this.getClass().getSimpleName(), this.mInputTextureSize.width, this.mInputTextureSize.height);
        this.cacaptureImageBuffer();
    }
    
    @Override
    protected void informTargetsAboutNewFrame(final long n) {
    }
    
    @Override
    protected void onDestroy() {
        this.a();
    }
    
    private void a() {
        this.a = false;
        if (this.mOutputFramebuffer == null) {
            return;
        }
        this.mOutputFramebuffer.enableReferenceCounting();
        SelesContext.recycleFramebuffer(this.mOutputFramebuffer);
        this.mOutputFramebuffer = null;
    }
}
