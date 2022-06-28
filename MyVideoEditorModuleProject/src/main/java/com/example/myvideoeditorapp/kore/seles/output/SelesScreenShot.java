// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.output;

import com.example.myvideoeditorapp.kore.seles.filters.SelesCropFilter;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;

@Deprecated
public class SelesScreenShot extends SelesCropFilter
{
    private SelesScreenShotDelegate a;
    private IntBuffer b;
    
    public void setDelegate(final SelesScreenShotDelegate a) {
        this.a = a;
    }
    
    @Override
    public void setInputSize(final TuSdkSize tuSdkSize, final int n) {
        final TuSdkSize mInputTextureSize = this.mInputTextureSize;
        super.setInputSize(tuSdkSize, n);
        if (!mInputTextureSize.equals(this.mInputTextureSize)) {
            this.b = null;
        }
    }
    
    @Override
    protected void renderToTexture(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        super.renderToTexture(floatBuffer, floatBuffer2);
        if (this.a != null) {
            this.setEnabled(this.a.onFrameRendered(this));
        }
    }
    
    public IntBuffer renderedBuffer() {
        final EGLContext eglGetCurrentContext = ((EGL10)EGLContext.getEGL()).eglGetCurrentContext();
        if (eglGetCurrentContext == null || eglGetCurrentContext == EGL10.EGL_NO_CONTEXT) {
            return null;
        }
        final GL10 gl10 = (GL10)eglGetCurrentContext.getGL();
        final TuSdkSize outputSize = this.getOutputSize();
        if (this.b == null) {
            this.b = IntBuffer.allocate(outputSize.width * outputSize.height);
        }
        this.b.position(0);
        gl10.glReadPixels(0, 0, outputSize.width, outputSize.height, 6408, 5121, (Buffer)this.b);
        return this.b;
    }
    
    public interface SelesScreenShotDelegate
    {
        boolean onFrameRendered(final SelesScreenShot p0);
    }
}
