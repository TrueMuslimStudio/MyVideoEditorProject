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
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import java.util.List;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;

public class TuSDKMonsterNoseFallFace extends SelesFilter implements SelesParameters.FilterFacePositionInterface
{
    private final int i = 12;
    private final int j = 9;
    private final int k = 4;
    private final int l = 25;
    private final int m = 37;
    boolean a;
    FaceAligment[] b;
    List<MonsterNoseFallFaceInfo> c;
    FloatBuffer d;
    FloatBuffer e;
    IntBuffer f;
    private static final int[] n;
    int g;
    int h;
    
    public TuSDKMonsterNoseFallFace() {
        this.g = 4;
        this.h = 2;
        this.c = new ArrayList<MonsterNoseFallFaceInfo>();
        this.d = ByteBuffer.allocateDirect((this.g + 25) * 4 * 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.e = ByteBuffer.allocateDirect((this.g + 25) * 4 * 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.f = ByteBuffer.allocateDirect((this.h + 37) * 4 * 3).order(ByteOrder.nativeOrder()).asIntBuffer();
        this.d.put(new float[] { -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f }).position(0);
        this.e.put(new float[] { 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f }).position(0);
        this.f.put(new int[] { 0, 1, 2, 0, 3, 2 });
        for (int i = 0; i < 111; ++i) {
            this.f.put(TuSDKMonsterNoseFallFace.n[i] + this.g);
        }
        this.f.position(0);
    }
    
    @Override
    protected void onInitOnGLThread() {
        super.onInitOnGLThread();
        this.checkGLError(this.getClass().getSimpleName() + " onInitOnGLThread");
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
            final MonsterNoseFallFaceInfo monsterNoseFallFaceInfo = new MonsterNoseFallFaceInfo(b[i]);
            monsterNoseFallFaceInfo.b(this.e, this.g * 2);
            this.e.position(0);
            monsterNoseFallFaceInfo.a();
            monsterNoseFallFaceInfo.a(this.d, this.g * 2);
            this.d.position(0);
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
        this.captureFilterImage(this.getClass().getSimpleName(), this.mInputTextureSize.width, this.mInputTextureSize.height);
        this.inputFramebufferUnlock();
        this.cacaptureImageBuffer();
    }
    
    @Override
    public void updateFaceFeatures(final FaceAligment[] b, final float n) {
        if (b == null || b.length < 1) {
            this.a = false;
            this.b = null;
            this.c.clear();
            return;
        }
        this.b = b;
        this.a = true;
    }
    
    static {
        n = new int[] { 0, 1, 12, 0, 9, 12, 1, 2, 13, 1, 12, 13, 2, 3, 14, 2, 13, 14, 3, 4, 15, 3, 14, 15, 4, 5, 17, 4, 15, 16, 4, 16, 17, 5, 6, 18, 5, 17, 18, 6, 7, 19, 6, 18, 19, 7, 8, 20, 7, 19, 20, 8, 10, 20, 9, 11, 21, 9, 12, 21, 10, 11, 21, 10, 20, 21, 12, 13, 21, 9, 11, 21, 13, 21, 23, 13, 14, 23, 14, 22, 23, 14, 15, 22, 15, 16, 22, 16, 17, 22, 17, 18, 22, 18, 19, 24, 18, 22, 24, 19, 20, 21, 19, 21, 24, 21, 23, 24, 22, 23, 24 };
    }
    
    private class MonsterNoseFallFaceInfo
    {
        PointF a;
        PointF[] b;
        PointF[] c;
        PointF[] d;
        PointF e;
        
        private MonsterNoseFallFaceInfo(final FaceAligment faceAligment) {
            this.b = new PointF[12];
            this.c = new PointF[9];
            this.d = new PointF[4];
            if (faceAligment != null) {
                this.a(faceAligment.getOrginMarks());
            }
        }
        
        private void a(final PointF[] array) {
            if (array == null || array.length < 3) {
                return;
            }
            this.a = new PointF(array[46].x, array[46].y);
            final int[] array2 = { 0, 4, 8, 12, 16, 20, 24, 28, 32 };
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
                this.b[k] = PointCalc.extensionPercentage(this.a, this.b[k], 0.5f);
            }
            final int[] array3 = { 45, 49, 82, 83 };
            for (int l = 0; l < 4; ++l) {
                this.d[l] = new PointF(array[array3[l]].x, array[array3[l]].y);
            }
            this.e = PointCalc.crossPoint(this.d[0], this.d[1], this.d[2], this.d[3]);
        }
        
        private void a(final FloatBuffer floatBuffer, final int n) {
            floatBuffer.position(n);
            for (int i = 0; i < 12; ++i) {
                floatBuffer.put(this.b[i].x * 2.0f - 1.0f);
                floatBuffer.put(this.b[i].y * 2.0f - 1.0f);
            }
            for (int j = 0; j < 9; ++j) {
                floatBuffer.put(this.c[j].x * 2.0f - 1.0f);
                floatBuffer.put(this.c[j].y * 2.0f - 1.0f);
            }
            for (int k = 0; k < 4; ++k) {
                floatBuffer.put(this.d[k].x * 2.0f - 1.0f);
                floatBuffer.put(this.d[k].y * 2.0f - 1.0f);
            }
        }
        
        private void b(final FloatBuffer floatBuffer, final int n) {
            floatBuffer.position(n);
            for (int i = 0; i < 12; ++i) {
                floatBuffer.put(this.b[i].x);
                floatBuffer.put(this.b[i].y);
            }
            for (int j = 0; j < 9; ++j) {
                floatBuffer.put(this.c[j].x);
                floatBuffer.put(this.c[j].y);
            }
            for (int k = 0; k < 4; ++k) {
                floatBuffer.put(this.d[k].x);
                floatBuffer.put(this.d[k].y);
            }
        }
        
        private void a() {
            this.d[0] = PointCalc.percentage(this.d[0], this.e, -0.5f);
            this.d[1] = PointCalc.percentage(this.d[1], this.d[0], -0.5f);
            this.d[2] = PointCalc.percentage(this.d[2], this.d[0], -0.5f);
            this.d[3] = PointCalc.percentage(this.d[3], this.d[0], -0.5f);
            final Float value = -0.2f;
            this.c[0] = PointCalc.percentage(this.c[0], this.a, value * 0.25f);
            this.c[1] = PointCalc.percentage(this.c[1], this.a, value * 0.75f);
            this.c[2] = PointCalc.percentage(this.c[2], this.a, value * 1.0f);
            this.c[3] = PointCalc.percentage(this.c[3], this.a, value * 0.75f);
            this.c[4] = PointCalc.percentage(this.c[4], this.a, value * 0.4f);
            this.c[5] = PointCalc.percentage(this.c[5], this.a, value * 0.75f);
            this.c[6] = PointCalc.percentage(this.c[6], this.a, value * 1.0f);
            this.c[7] = PointCalc.percentage(this.c[7], this.a, value * 0.75f);
            this.c[8] = PointCalc.percentage(this.c[8], this.a, value * 0.25f);
        }
    }
}
