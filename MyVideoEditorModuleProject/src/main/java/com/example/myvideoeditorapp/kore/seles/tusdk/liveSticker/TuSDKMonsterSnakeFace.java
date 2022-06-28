// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.liveSticker;

import com.example.myvideoeditorapp.kore.utils.calc.PointCalc;
import android.graphics.PointF;
import com.example.myvideoeditorapp.kore.seles.SelesFramebufferCache;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import java.nio.Buffer;
import android.opengl.GLES20;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import com.example.myvideoeditorapp.kore.seles.filters.SelesPointDrawFilter;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import java.util.List;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;

public class TuSDKMonsterSnakeFace extends SelesFilter implements SelesParameters.FilterFacePositionInterface
{
    public static final int TuSDKMonsterSnakeFaceType = 1;
    public static final int TuSDKMonsterBigFaceType = 2;
    private static final int[] g;
    boolean a;
    FaceAligment[] b;
    List<MonsterSnakeFaceInfo> c;
    FloatBuffer d;
    FloatBuffer e;
    IntBuffer f;
    private int h;
    private int i;
    private int j;
    private SelesPointDrawFilter k;
    private SelesPointDrawFilter l;
    private boolean m;
    
    public TuSDKMonsterSnakeFace() {
        this.h = 4;
        this.i = 2;
        this.j = 2;
        this.m = false;
        this.b();
        this.c = new ArrayList<MonsterSnakeFaceInfo>();
        this.d = ByteBuffer.allocateDirect((this.h + 22) * 4 * 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.e = ByteBuffer.allocateDirect((this.h + 22) * 4 * 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.f = ByteBuffer.allocateDirect((this.i + 30) * 4 * 3).order(ByteOrder.nativeOrder()).asIntBuffer();
        this.d.put(new float[] { -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f }).position(0);
        this.e.put(new float[] { 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f }).position(0);
        this.f.put(new int[] { 0, 1, 2, 0, 3, 2 });
        for (int i = 0; i < 90; ++i) {
            this.f.put(TuSDKMonsterSnakeFace.g[i] + this.h);
        }
        this.f.position(0);
        if (this.m) {
            this.k = new SelesPointDrawFilter();
            this.l = new SelesPointDrawFilter();
            this.addTarget(this.k, 0);
            this.addTarget(this.l, 0);
        }
    }
    
    @Override
    public void removeAllTargets() {
        super.removeAllTargets();
        if (this.m) {
            if (this.k != null) {
                this.addTarget(this.k, 0);
            }
            if (this.l != null) {
                this.addTarget(this.l, 0);
            }
        }
    }
    
    public void setMonsterFaceType(final int j) {
        this.j = j;
    }
    
    @Override
    protected void renderToTexture(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        this.runPendingOnDrawTasks();
        if (this.isPreventRendering()) {
            this.inputFramebufferUnlock();
            return;
        }
        final FaceAligment[] b = this.b;
        if (b == null || b.length == 0) {
            this.a();
            return;
        }
        for (int i = 0; i < b.length; ++i) {
            final MonsterSnakeFaceInfo monsterSnakeFaceInfo = new MonsterSnakeFaceInfo(b[i]);
            monsterSnakeFaceInfo.a(this.e, this.h * 2);
            this.e.position(0);
            switch (this.j) {
                case 1: {
                    monsterSnakeFaceInfo.b();
                    break;
                }
                default: {
                    monsterSnakeFaceInfo.c();
                    break;
                }
            }
            this.d.position(this.h * 2);
            this.d.put(monsterSnakeFaceInfo.a()).position(0);
            this.a();
            if (i < b.length - 1) {
                this.setInputFramebuffer(this.framebufferForOutput(), 0);
            }
        }
    }
    
    private void a() {
        SelesContext.setActiveShaderProgram(this.mFilterProgram);
        final TuSdkSize sizeOfFBO = this.sizeOfFBO();
        final SelesFramebufferCache sharedFramebufferCache = SelesContext.sharedFramebufferCache();
        if (sharedFramebufferCache == null) {
            return;
        }
        if (this.mOutputFramebuffer != null) {
            this.mOutputFramebuffer.unlock();
        }
        (this.mOutputFramebuffer = sharedFramebufferCache.fetchFramebuffer(SelesFramebuffer.SelesFramebufferMode.FBO_AND_TEXTURE, sizeOfFBO, this.getOutputTextureOptions())).activateFramebuffer();
        this.checkGLError(this.getClass().getSimpleName() + " activateFramebuffer");
        if (this.mUsingNextFrameForImageCapture) {
            this.mOutputFramebuffer.lock();
        }
        this.setUniformsForProgramAtIndex(0);
        GLES20.glClearColor(this.mBackgroundColorRed, this.mBackgroundColorGreen, this.mBackgroundColorBlue, this.mBackgroundColorAlpha);
        GLES20.glClear(16384);
        this.inputFramebufferBindTexture();
        this.checkGLError(this.getClass().getSimpleName() + " bindFramebuffer");
        GLES20.glVertexAttribPointer(this.mFilterPositionAttribute, 2, 5126, false, 0, (Buffer)this.d);
        GLES20.glVertexAttribPointer(this.mFilterTextureCoordinateAttribute, 2, 5126, false, 0, (Buffer)this.e);
        GLES20.glDrawElements(4, this.f.limit(), 5125, (Buffer)this.f);
        this.captureFilterImage(this.getClass().getSimpleName(), this.mInputTextureSize.width, this.mInputTextureSize.height);
        this.inputFramebufferUnlock();
        this.cacaptureImageBuffer();
    }
    
    private void b() {
    }
    
    @Override
    public void updateFaceFeatures(final FaceAligment[] b, final float n) {
        if (b == null || b.length < 1) {
            this.a = false;
            this.b = null;
            return;
        }
        this.b = b;
        this.a = true;
    }
    
    static {
        g = new int[] { 0, 1, 12, 0, 9, 12, 1, 2, 13, 1, 12, 13, 2, 3, 14, 2, 13, 14, 3, 4, 15, 3, 14, 15, 4, 5, 17, 4, 15, 16, 4, 16, 17, 5, 6, 18, 5, 17, 18, 6, 7, 19, 6, 18, 19, 7, 8, 20, 7, 19, 20, 8, 10, 20, 9, 11, 21, 9, 12, 21, 10, 11, 21, 10, 20, 21, 12, 13, 21, 13, 14, 21, 14, 15, 21, 15, 16, 21, 16, 17, 21, 17, 18, 21, 18, 19, 21, 19, 20, 21 };
    }
    
    private class MonsterSnakeFaceInfo
    {
        PointF a;
        PointF[] b;
        PointF[] c;
        
        private MonsterSnakeFaceInfo(final FaceAligment faceAligment) {
            this.b = new PointF[12];
            this.c = new PointF[9];
            if (faceAligment != null) {
                this.a(faceAligment.getOrginMarks());
            }
        }
        
        private float[] a() {
            final float[] array = new float[44];
            int n = 0;
            for (int i = 0; i < 12; ++i) {
                array[n++] = this.b[i].x * 2.0f - 1.0f;
                array[n++] = this.b[i].y * 2.0f - 1.0f;
            }
            for (int j = 0; j < 9; ++j) {
                array[n++] = this.c[j].x * 2.0f - 1.0f;
                array[n++] = this.c[j].y * 2.0f - 1.0f;
            }
            array[n++] = this.a.x * 2.0f - 1.0f;
            array[n++] = this.a.y * 2.0f - 1.0f;
            return array;
        }
        
        private void a(final FloatBuffer floatBuffer, final int n) {
            floatBuffer.position(n);
            for (int i = 0; i < 12; ++i) {
                floatBuffer.put(this.b[i].x);
                floatBuffer.put(this.b[i].y);
            }
            for (int j = 0; j < 9; ++j) {
                floatBuffer.put(this.c[j].x);
                floatBuffer.put(this.c[j].y);
            }
            floatBuffer.put(this.a.x);
            floatBuffer.put(this.a.y);
        }
        
        private void b() {
            final float n = 0.2f;
            this.c[0] = PointCalc.percentage(this.c[0], this.a, n * 0.25f);
            this.c[1] = PointCalc.percentage(this.c[1], this.a, n * 0.75f);
            this.c[2] = PointCalc.percentage(this.c[2], this.a, n * 1.0f);
            this.c[3] = PointCalc.percentage(this.c[3], this.a, n * 0.75f);
            this.c[4] = PointCalc.percentage(this.c[4], this.a, n * 0.4f);
            this.c[5] = PointCalc.percentage(this.c[5], this.a, n * 0.75f);
            this.c[6] = PointCalc.percentage(this.c[6], this.a, n * 1.0f);
            this.c[7] = PointCalc.percentage(this.c[7], this.a, n * 0.75f);
            this.c[8] = PointCalc.percentage(this.c[8], this.a, n * 0.25f);
        }
        
        private void c() {
            final float n = -0.2f;
            this.c[0] = PointCalc.percentage(this.c[0], this.a, n * 0.25f);
            this.c[1] = PointCalc.percentage(this.c[1], this.a, n * 0.75f);
            this.c[2] = PointCalc.percentage(this.c[2], this.a, n * 1.0f);
            this.c[3] = PointCalc.percentage(this.c[3], this.a, n * 0.75f);
            this.c[4] = PointCalc.percentage(this.c[4], this.a, n * 0.4f);
            this.c[5] = PointCalc.percentage(this.c[5], this.a, n * 0.75f);
            this.c[6] = PointCalc.percentage(this.c[6], this.a, n * 1.0f);
            this.c[7] = PointCalc.percentage(this.c[7], this.a, n * 0.75f);
            this.c[8] = PointCalc.percentage(this.c[8], this.a, n * 0.25f);
        }
        
        private void a(final PointF[] array) {
            if (array == null || array.length < 3) {
                return;
            }
            final int[] array2 = { 0, 4, 8, 12, 16, 20, 24, 28, 32 };
            this.a = new PointF(array[46].x, array[46].y);
            for (int i = 0; i < 9; ++i) {
                this.c[i] = new PointF(array[array2[i]].x, array[array2[i]].y);
            }
            for (int j = 0; j < 9; ++j) {
                this.b[j] = new PointF(array[array2[j]].x, array[array2[j]].y);
            }
            this.b[9] = new PointF(array[34].x, array[34].y);
            this.b[10] = new PointF(array[41].x, array[41].y);
            this.b[11] = PointCalc.center(array[35], array[40]);
            for (int k = 0; k < 12; ++k) {
                this.b[k] = PointCalc.extension(this.a, this.b[k], 0.5f);
            }
        }
    }
}
