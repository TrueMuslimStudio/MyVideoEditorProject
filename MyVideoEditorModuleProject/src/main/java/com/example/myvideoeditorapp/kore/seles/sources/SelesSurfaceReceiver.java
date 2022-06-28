// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.sources;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.Build;

import com.example.myvideoeditorapp.kore.media.record.TuSdkRecordSurface;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.seles.SelesGLProgram;
import com.example.myvideoeditorapp.kore.seles.extend.SelesSurfaceTexture;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCorpBuilder;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

public class SelesSurfaceReceiver extends SelesOutput implements TuSdkRecordSurface, SelesSurfaceHolder
{
    public static final String SELES_VERTEX_SHADER = "attribute vec4 position;attribute vec4 inputTextureCoordinate;varying vec2 textureCoordinate;uniform mat4 uSTMatrix;uniform mat4 uMVPMatrix;void main(){    gl_Position = uMVPMatrix * position;    textureCoordinate = (uSTMatrix * inputTextureCoordinate).xy;}";
    private ImageOrientation a;
    private TuSdkSize b;
    protected SelesFramebuffer mSurfaceFBO;
    private SelesSurfaceTexture c;
    private FloatBuffer d;
    private FloatBuffer e;
    private SelesGLProgram f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private boolean l;
    private long m;
    private SelesVerticeCoordinateCorpBuilder n;
    private SurfaceTexture.OnFrameAvailableListener o;
    private RectF p;
    private boolean q;
    private float r;
    private float s;
    private float t;
    private float u;
    private float[] v;
    private float[] w;
    private final Map<SelesContext.SelesInput, Integer> x;
    
    @Override
    public boolean isInited() {
        return this.q;
    }
    
    public SurfaceTexture getSurfaceTexture() {
        return this.c;
    }
    
    @Override
    public SurfaceTexture requestSurfaceTexture() {
        if (this.mSurfaceFBO == null) {
            TLog.w("%s requestSurface need run first initInGLThread in GL Thread", "SelesSurfaceReceiver");
            return null;
        }
        this.e();
        (this.c = new SelesSurfaceTexture(this.mSurfaceFBO.getTexture())).setOnFrameAvailableListener(this.o);
        return this.c;
    }
    
    public void setSurfaceTextureListener(final SurfaceTexture.OnFrameAvailableListener onFrameAvailableListener) {
        this.o = onFrameAvailableListener;
        if (this.c != null) {
            this.c.setOnFrameAvailableListener(onFrameAvailableListener);
        }
    }
    
    @Override
    public void updateSurfaceTexImage() {
        if (this.c == null) {
            TLog.w("%s updateSurfaceTexImage need run first newFrameReadyInGLThread in GL Thread", "SelesSurfaceReceiver");
            return;
        }
        this.c.updateTexImage();
    }
    
    public void forceUpdateSurfaceTexImage() {
        if (this.c == null) {
            TLog.w("%s updateSurfaceTexImage need run first newFrameReadyInGLThread in GL Thread", "SelesSurfaceReceiver");
            return;
        }
        this.c.forceUpdateTexImage();
    }
    
    public void updateSurfaceTexImage(final long n) {
        this.updateSurfaceTexImage();
        this.newFrameReadyInGLThread(n);
    }
    
    @Override
    public long getSurfaceTexTimestampNs() {
        if (this.c == null) {
            TLog.w("%s getSurfaceTexTimestampNs need run first newFrameReadyInGLThread in GL Thread", "SelesSurfaceReceiver");
            return 0L;
        }
        return this.c.getTimestamp();
    }
    
    @Override
    public void setSurfaceTexTimestampNs(final long defindTimestamp) {
        if (this.c == null) {
            TLog.w("%s setSurfaceTexTimestampNs need run first newFrameReadyInGLThread in GL Thread", "SelesSurfaceReceiver");
            return;
        }
        this.c.setDefindTimestamp(defindTimestamp);
    }
    
    @Override
    public void setTextureCoordinateBuilder(final SelesVerticeCoordinateCorpBuilder n) {
        this.n = n;
        if (this.n != null && this.p != null) {
            this.n.setPreCropRect(this.p);
        }
    }
    
    @Override
    public void setPreCropRect(final RectF p) {
        this.p = p;
        if (this.n != null) {
            this.n.setPreCropRect(this.p);
        }
    }
    
    public void setEnableClip(final boolean enableClip) {
        if (this.n != null) {
            this.n.setEnableClip(enableClip);
        }
    }
    
    public TuSdkSize setOutputRatio(final float outputRatio) {
        if (this.n != null) {
            return this.n.setOutputRatio(outputRatio);
        }
        return null;
    }
    
    public void setOutputSize(final TuSdkSize tuSdkSize) {
        this.b = tuSdkSize;
        if (this.n != null) {
            this.n.setOutputSize(tuSdkSize);
        }
    }
    
    public void setCropRect(final RectF cropRect) {
        if (this.n != null) {
            this.n.setCropRect(cropRect);
        }
    }
    
    public void setCanvasRect(final RectF canvasRect) {
        if (this.n != null) {
            this.n.setCanvasRect(canvasRect);
        }
    }
    
    public void setCanvasColor(final float r, final float s, final float t, final float u) {
        this.r = r;
        this.s = s;
        this.t = t;
        this.u = u;
    }
    
    public void setCanvasColor(final int n) {
        this.setCanvasColor(Color.red(n) / 255.0f, Color.green(n) / 255.0f, Color.blue(n) / 255.0f, Color.alpha(n) / 255.0f);
    }
    
    public SelesSurfaceReceiver() {
        this.a = ImageOrientation.Up;
        this.l = false;
        this.m = -1L;
        this.q = false;
        this.r = 0.0f;
        this.s = 0.0f;
        this.t = 0.0f;
        this.u = 1.0f;
        this.v = new float[16];
        this.w = new float[16];
        this.x = new LinkedHashMap<SelesContext.SelesInput, Integer>();
        this.d = SelesFilter.buildBuffer(SelesFilter.imageVertices);
        this.e = SelesFilter.buildBuffer(SelesFilter.noRotationTextureCoordinates);
        this.runOnDraw(new Runnable() {
            @Override
            public void run() {
                SelesSurfaceReceiver.this.a();
            }
        });
    }
    
    private void a() {
        this.m = Thread.currentThread().getId();
        this.f = SelesContext.program("attribute vec4 position;attribute vec4 inputTextureCoordinate;varying vec2 textureCoordinate;uniform mat4 uSTMatrix;uniform mat4 uMVPMatrix;void main(){    gl_Position = uMVPMatrix * position;    textureCoordinate = (uSTMatrix * inputTextureCoordinate).xy;}", "#extension GL_OES_EGL_image_external : require\nvarying highp vec2 textureCoordinate;uniform samplerExternalOES inputImageTexture;void main(){     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);}");
        if (!this.f.isInitialized()) {
            this.f.addAttribute("position");
            this.f.addAttribute("inputTextureCoordinate");
            if (!this.f.link()) {
                TLog.i("%s Program link log: %s", "SelesSurfaceReceiver", this.f.getProgramLog());
                TLog.i("%s Fragment shader compile log: %s", "SelesSurfaceReceiver", this.f.getFragmentShaderLog());
                TLog.i("%s Vertex link log: %s", "SelesSurfaceReceiver", this.f.getVertexShaderLog());
                this.f = null;
                TLog.e("%s Filter shader link failed: %s", "SelesSurfaceReceiver", this.getClass());
                return;
            }
        }
        this.g = this.f.attributeIndex("position");
        this.h = this.f.attributeIndex("inputTextureCoordinate");
        this.i = this.f.uniformIndex("inputImageTexture");
        this.j = this.f.uniformIndex("uSTMatrix");
        this.k = this.f.uniformIndex("uMVPMatrix");
        Matrix.setIdentityM(this.v, 0);
        SelesContext.setActiveShaderProgram(this.f);
        GLES20.glEnableVertexAttribArray(this.g);
        GLES20.glEnableVertexAttribArray(this.h);
        this.initSurfaceFBO();
        this.l = true;
        this.q = true;
    }
    
    protected void initSurfaceFBO() {
        this.mSurfaceFBO = SelesContext.sharedFramebufferCache().fetchTextureOES();
    }
    
    @Override
    public void initInGLThread() {
        this.runPendingOnDrawTasks();
    }
    
    @Override
    public void newFrameReadyInGLThread(final long n) {
        if (this.m != Thread.currentThread().getId()) {
            TLog.w("%s newFrameReadyInGLThread need run in GL thread", "SelesSurfaceReceiver");
            return;
        }
        if (this.n != null && this.n.calculate(this.mInputTextureSize, this.a, this.d, this.e)) {
            this.b = this.n.outputSize();
        }
        else {
            this.e.clear();
            this.e.put(SelesFilter.textureCoordinates(this.a)).position(0);
            this.b = this.mInputTextureSize;
        }
        this.renderToTexture(this.d, this.e);
        this.a(n);
    }
    
    protected void renderToTexture(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        this.runPendingOnDrawTasks();
        if (!this.b.isSize()) {
            return;
        }
        SelesContext.setActiveShaderProgram(this.f);
        this.b();
        GLES20.glClearColor(this.r, this.s, this.t, this.u);
        GLES20.glClear(16384);
        if (this.c != null) {
            this.c.getTransformMatrix(this.v);
        }
        Matrix.setIdentityM(this.w, 0);
        GLES20.glActiveTexture(33986);
        GLES20.glBindTexture(36197, this.d());
        GLES20.glUniform1i(this.i, 2);
        GLES20.glUniformMatrix4fv(this.j, 1, false, this.v, 0);
        GLES20.glUniformMatrix4fv(this.k, 1, false, this.w, 0);
        GLES20.glEnableVertexAttribArray(this.g);
        GLES20.glEnableVertexAttribArray(this.h);
        GLES20.glVertexAttribPointer(this.g, 2, 5126, false, 0, (Buffer)floatBuffer);
        GLES20.glVertexAttribPointer(this.h, 2, 5126, false, 0, (Buffer)floatBuffer2);
        GLES20.glDrawArrays(5, 0, 4);
        final IntBuffer allocate = IntBuffer.allocate(this.b.width * this.b.height);
        GLES20.glReadPixels(0, 0, this.b.width, this.b.height, 6408, 5121, (Buffer)allocate);
        Bitmap.createBitmap(this.b.width, this.b.height, Bitmap.Config.ARGB_8888).copyPixelsFromBuffer((Buffer)allocate);
        TLog.d("image", new Object[0]);
        GLES20.glBindTexture(36197, 0);
    }
    
    private void b() {
        if (this.l || this.mOutputFramebuffer == null) {
            this.c();
            (this.mOutputFramebuffer = SelesContext.sharedFramebufferCache().fetchFramebuffer(SelesFramebuffer.SelesFramebufferMode.FBO_AND_TEXTURE, this.outputFrameSize())).disableReferenceCounting();
            this.l = false;
        }
        this.mOutputFramebuffer.activateFramebuffer();
    }
    
    public void genOutputFrameBuffer(final TuSdkSize tuSdkSize) {
        if (this.mOutputFramebuffer == null) {
            (this.mOutputFramebuffer = SelesContext.sharedFramebufferCache().fetchFramebuffer(SelesFramebuffer.SelesFramebufferMode.FBO_AND_TEXTURE, tuSdkSize)).disableReferenceCounting();
        }
    }
    
    private void c() {
        if (this.mOutputFramebuffer == null) {
            return;
        }
        this.mOutputFramebuffer.clearAllLocks();
        SelesContext.returnFramebufferToCache(this.mOutputFramebuffer);
        this.mOutputFramebuffer = null;
    }
    
    private int d() {
        int texture = 0;
        if (this.mSurfaceFBO != null) {
            texture = this.mSurfaceFBO.getTexture();
        }
        return texture;
    }
    
    private void a(final long n) {
        this.x.clear();
        for (final SelesContext.SelesInput selesInput : this.mTargets) {
            if (!selesInput.isEnabled()) {
                continue;
            }
            if (selesInput == this.getTargetToIgnoreForUpdates()) {
                continue;
            }
            final int intValue = this.mTargetTextureIndices.get(this.mTargets.indexOf(selesInput));
            this.x.put(selesInput, intValue);
            this.setInputFramebufferForTarget(selesInput, intValue);
            selesInput.setInputSize(this.outputFrameSize(), intValue);
        }
        for (final Map.Entry<SelesContext.SelesInput, Integer> entry : this.x.entrySet()) {
            entry.getKey().newFrameReady(n, entry.getValue());
        }
    }
    
    public TuSdkSize outputFrameSize() {
        return (this.b == null) ? this.mInputTextureSize : this.b;
    }
    
    @Override
    public void setInputSize(TuSdkSize transforOrientation) {
        if (transforOrientation == null || !transforOrientation.isSize()) {
            return;
        }
        transforOrientation = transforOrientation.transforOrientation(this.a);
        if (this.mInputTextureSize.equals(transforOrientation)) {
            return;
        }
        this.mInputTextureSize = transforOrientation;
        this.l = true;
    }
    
    @Override
    public void setInputRotation(final ImageOrientation a) {
        if (a == null || a == this.a) {
            return;
        }
        final TuSdkSize transforOrientation = this.mInputTextureSize.transforOrientation(this.a);
        this.a = a;
        this.mInputTextureSize = transforOrientation.transforOrientation(a);
        this.l = true;
    }
    
    @Override
    protected void onDestroy() {
        this.c();
        this.e();
        if (this.mSurfaceFBO != null) {
            this.mSurfaceFBO.destroy();
            this.mSurfaceFBO = null;
        }
    }
    
    private void e() {
        if (this.c == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 14) {
            this.c.release();
        }
        this.c = null;
    }
}
