// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.output;

import android.graphics.Rect;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.SelesPixelBuffer;
import com.example.myvideoeditorapp.kore.seles.egl.SelesEGL10Core;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.RectHelper;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.TuSdkDeviceInfo;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class SelesOffscreenRotate extends SelesFilter
{
    public static final String ROTATE_VERTEX_SHADER = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nuniform mat4 transformMatrix;\nvarying vec2 textureCoordinate;\nvoid main()\n{\n    gl_Position = transformMatrix * vec4(position.xyz, 1.0);\n    textureCoordinate = inputTextureCoordinate.xy;\n}\n";
    public static final String ROTATE_FRAGMENT_SHADER = "precision highp float;varying vec2 textureCoordinate;uniform sampler2D inputImageTexture;const vec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721);void main(){     vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);     float luminance = dot(textureColor.rgb, luminanceWeighting);     gl_FragColor = vec4(vec3(luminance), textureColor.w);}";
    private SelesEGL10Core a;
    private SelesOffscreenRotateDelegate b;
    private int c;
    private float d;
    private float e;
    private float[] f;
    private TuSdkSize g;
    private final FloatBuffer h;
    private float i;
    private SelesPixelBuffer j;
    private boolean k;
    private boolean l;
    private boolean m;
    private float n;
    
    public float getFullScale() {
        return this.i;
    }
    
    public void setDelegate(final SelesOffscreenRotateDelegate b) {
        this.b = b;
    }
    
    public void setSyncOutput(final boolean m) {
        if (this.m == m) {
            return;
        }
        this.m = m;
    }
    
    public SelesOffscreenRotate() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nuniform mat4 transformMatrix;\nvarying vec2 textureCoordinate;\nvoid main()\n{\n    gl_Position = transformMatrix * vec4(position.xyz, 1.0);\n    textureCoordinate = inputTextureCoordinate.xy;\n}\n", "precision highp float;varying vec2 textureCoordinate;uniform sampler2D inputImageTexture;const vec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721);void main(){     vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);     float luminance = dot(textureColor.rgb, luminanceWeighting);     gl_FragColor = vec4(vec3(luminance), textureColor.w);}");
        this.d = 0.0f;
        this.e = 0.0f;
        this.g = TuSdkSize.create(0, 0);
        this.i = 1.0f;
        this.k = true;
        this.l = false;
        this.m = false;
        this.n = -1.0f;
        this.k = TuSdkDeviceInfo.isSupportPbo();
        Matrix.setIdentityM(this.f = new float[16], 0);
        this.h = SelesFilter.buildBuffer(SelesFilter.imageVertices);
    }
    
    private void a() {
        if (this.j != null) {
            this.j.destory();
        }
        this.j = null;
    }
    
    private void b() {
        this.a();
        if (this.a != null) {
            this.a.destroy();
        }
        this.a = null;
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.b();
    }
    
    @Override
    protected void onInitOnGLThread() {
        super.onInitOnGLThread();
        this.c = this.mFilterProgram.uniformIndex("transformMatrix");
        this.a(this.f);
        this.checkGLError(this.getClass().getSimpleName() + " onInitOnGLThread");
    }
    
    private void a(final float[] f) {
        this.setMatrix4f(this.f = f, this.c, this.mFilterProgram);
    }
    
    @Override
    public void setInputSize(TuSdkSize create, final int n) {
        super.setInputSize(create, n);
        if (this.mInputRotation.isTransposed()) {
            create = TuSdkSize.create(create.height, create.width);
        }
        if (this.g.equals(create)) {
            return;
        }
        if (n == 0 && create.isSize()) {
            this.g = create;
            this.i = create.maxMinRatio();
            this.c();
        }
    }
    
    private void c() {
        final TuSdkSize copy = this.mInputTextureSize.copy();
        final Rect rectWithAspectRatioInsideRect = RectHelper.makeRectWithAspectRatioInsideRect(this.g, new Rect(0, 0, copy.width, copy.height));
        final float n = rectWithAspectRatioInsideRect.width() / (float)copy.width;
        final float n2 = rectWithAspectRatioInsideRect.height() / (float)copy.height;
        final float[] src = { -n, -n2, n, -n2, -n, n2, n, n2 };
        this.h.clear();
        this.h.put(src).position(0);
    }
    
    @Override
    public void newFrameReady(final long n, final int n2) {
        if (this.mFirstInputFramebuffer == null) {
            return;
        }
        GLES20.glFinish();
        this.a(n, n2);
    }
    
    @Override
    public void setCurrentlyReceivingMonochromeInput(final boolean b) {
        if (!b || !this.m) {
            return;
        }
    }
    
    private void a(final long n, final int n2) {
        super.newFrameReady(n, n2);
    }
    
    @Override
    protected void renderToTexture(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        super.renderToTexture(this.h, floatBuffer2);
        this.checkGLError(this.getClass().getSimpleName());
        this.captureFilterImage(this.getClass().getSimpleName(), this.mInputTextureSize.width, this.mInputTextureSize.height);
        final SelesOffscreenRotateDelegate b = this.b;
        try {
            final TuSdkSize outputFrameSize = this.outputFrameSize();
            if (b == null || !outputFrameSize.isSize()) {
                return;
            }
            this.a(outputFrameSize);
            b.onFrameRendered(this);
        }
        catch (Exception ex) {
            TLog.w("Screen Rotate Delegate is null !!!", new Object[0]);
        }
    }
    
    private void a(final TuSdkSize tuSdkSize) {
        if (!this.k) {
            return;
        }
        if (this.j == null || !this.j.getSize().equals(tuSdkSize)) {
            this.a();
            this.j = SelesContext.fetchPixelBuffer(tuSdkSize, 1);
        }
        this.j.preparePackBuffer();
    }
    
    public int[] getAuthors() {
        if (this.j == null) {
            return null;
        }
        return this.j.getBefferInfo();
    }
    
    public Buffer readBuffer() {
        if (this.j == null) {
            return null;
        }
        return this.j.readPackBuffer();
    }
    
    public void setAngle(final float n) {
        if (Math.abs(((n > 350.0f) ? (n - 360.0f) : n) - ((this.n > 350.0f) ? (this.n - 360.0f) : this.n)) < 10.0f && this.n != -1.0f) {
            return;
        }
        this.d = (float)((int)n / 10 * 10);
        this.e = this.d;
        this.n = this.d;
        Matrix.setIdentityM(this.f, 0);
        Matrix.rotateM(this.f, 0, this.d, 0.0f, 0.0f, 1.0f);
        this.setMatrix4f(this.f, this.c, this.mFilterProgram);
    }
    
    public float getAngle() {
        if (this.k) {
            return this.e;
        }
        return this.d;
    }
    
    public IntBuffer renderBuffer() {
        if (this.a == null) {
            return null;
        }
        return this.a.getImageBuffer();
    }
    
    public interface SelesOffscreenRotateDelegate
    {
        boolean onFrameRendered(final SelesOffscreenRotate p0);
    }
}
