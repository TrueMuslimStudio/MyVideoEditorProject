// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.output;

import android.opengl.GLES20;

import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateBuilder;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;
import com.example.myvideoeditorapp.kore.seles.sources.SelesWatermark;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;

import java.nio.FloatBuffer;

public class SelesSurfacePusherAsync extends SelesFilter implements SelesSurfaceDisplay
{
    private final SelesSurfaceDisplay a;
    private SelesFramebuffer b;
    private final Object c;
    
    public SelesSurfacePusherAsync() {
        this.a = new SelesSurfacePusher();
        this.c = new Object();
    }
    
    @Override
    public void setTextureCoordinateBuilder(final SelesVerticeCoordinateBuilder textureCoordinateBuilder) {
        this.a.setTextureCoordinateBuilder(textureCoordinateBuilder);
    }
    
    @Override
    protected void onInitOnGLThread() {
        super.onInitOnGLThread();
        this.checkGLError(this.getClass().getSimpleName() + " onInitOnGLThread");
    }
    
    @Override
    public void setWatermark(final SelesWatermark watermark) {
        this.a.setWatermark(watermark);
    }
    
    @Override
    protected void onDestroy() {
        if (this.b != null) {
            this.b.unlock();
            this.b = null;
        }
        if (this.mOutputFramebuffer != null) {
            this.mOutputFramebuffer.unlock();
            this.mOutputFramebuffer = null;
        }
        if (this.mFirstInputFramebuffer != null) {
            this.mFirstInputFramebuffer.unlock();
            this.mFirstInputFramebuffer = null;
        }
        this.a.destroy();
        super.onDestroy();
    }
    
    @Override
    public void setPusherRotation(final ImageOrientation imageOrientation, final int n) {
        this.a.setInputRotation(imageOrientation, n);
    }
    
    @Override
    public void newFrameReadyInGLThread(final long n) {
        this.runPendingOnDrawTasks();
    }
    
    @Override
    public void duplicateFrameReadyInGLThread(final long n) {
        synchronized (this.c) {
            this.a.duplicateFrameReadyInGLThread(n);
        }
    }
    
    @Override
    protected void informTargetsAboutNewFrame(final long n) {
        if (this.mOutputFramebuffer == null) {
            return;
        }
        synchronized (this.c) {
            final SelesFramebuffer b = this.b;
            this.b = this.mOutputFramebuffer;
            if (!this.mUsingNextFrameForImageCapture) {
                this.mOutputFramebuffer = null;
            }
            if (b != null) {
                b.unlock();
            }
            this.a.setInputSize(this.outputFrameSize(), 0);
            this.a.setInputFramebuffer(this.b, 0);
            this.a.newFrameReady(n, 0);
        }
    }
    
    @Override
    public void newFrameReady(final long n, final int n2) {
        GLES20.glFinish();
        this.runOnDraw(new Runnable() {
            @Override
            public void run() {
                SelesSurfacePusherAsync.this.a(n, n2);
            }
        });
    }
    
    private void a(final long n, final int n2) {
        super.newFrameReady(n, n2);
    }
    
    @Override
    protected void renderToTexture(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        super.renderToTexture(floatBuffer, floatBuffer2);
        this.checkGLError(this.getClass().getSimpleName());
        this.captureFilterImage(this.getClass().getSimpleName(), this.mInputTextureSize.width, this.mInputTextureSize.height);
    }
}
