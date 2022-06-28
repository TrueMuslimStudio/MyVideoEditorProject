// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.reshape;

import com.example.myvideoeditorapp.kore.seles.SelesContext;
import java.nio.FloatBuffer;
import android.opengl.GLES20;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;

public class TuSDKMeanBlurFilter extends SelesFilter
{
    private int a;
    private int b;
    private boolean c;
    
    public TuSDKMeanBlurFilter() {
        super("precision lowp float;\nuniform sampler2D inputImageTexture;\nvarying highp vec2 textureCoordinate;\nuniform float stepX;\nuniform float stepY;\nvoid main()\n{\nvec4 result = vec4(0.0, 0.0, 0.0, 0.0);\nfor (int i = -2; i <= 2; i++) {\n    for (int j = -2; j <= 2; j++) {\n        result += texture2D(inputImageTexture, textureCoordinate + vec2(float(i) * stepX, float(j) * stepY));\n    }\n}\nresult /= 25.0;\ngl_FragColor = vec4(result.rgb, 1.0);\n}");
    }
    
    @Override
    protected void onInitOnGLThread() {
        this.a = this.mFilterProgram.uniformIndex("stepX");
        this.b = this.mFilterProgram.uniformIndex("stepY");
    }
    
    @Override
    protected void inputFramebufferBindTexture() {
        super.inputFramebufferBindTexture();
        final float n = 1.0f / this.mFirstInputFramebuffer.getSize().width;
        final float n2 = 1.0f / this.mFirstInputFramebuffer.getSize().height;
        GLES20.glUniform1f(this.a, n);
        GLES20.glUniform1f(this.b, n2);
    }
    
    @Override
    protected void renderToTexture(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        if (!this.c) {
            super.renderToTexture(floatBuffer, floatBuffer2);
            return;
        }
        this.runPendingOnDrawTasks();
        if (this.isPreventRendering()) {
            this.inputFramebufferUnlock();
            return;
        }
        SelesContext.setActiveShaderProgram(this.mFilterProgram);
        (this.mOutputFramebuffer = this.mFirstInputFramebuffer).activateFramebuffer();
        if (this.mUsingNextFrameForImageCapture) {
            this.mOutputFramebuffer.lock();
        }
        this.captureFilterImage(this.getClass().getSimpleName(), this.mInputTextureSize.width, this.mInputTextureSize.height);
        this.cacaptureImageBuffer();
    }
    
    void a(final boolean c) {
        this.c = c;
    }
}
