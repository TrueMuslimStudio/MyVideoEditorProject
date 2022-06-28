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

public class TuSDKMonsterSquareFace extends SelesFilter implements SelesParameters.FilterFacePositionInterface
{
    private static final int[] i;
    boolean a;
    FaceAligment[] b;
    List<MonsterSquareFaceInfo> c;
    FloatBuffer d;
    FloatBuffer e;
    IntBuffer f;
    int g;
    int h;
    
    public TuSDKMonsterSquareFace() {
        this.g = 4;
        this.h = 2;
        this.c = new ArrayList<MonsterSquareFaceInfo>();
        this.d = ByteBuffer.allocateDirect((this.g + 7) * 4 * 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.e = ByteBuffer.allocateDirect((this.g + 7) * 4 * 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.f = ByteBuffer.allocateDirect((this.h + 7) * 4 * 3).order(ByteOrder.nativeOrder()).asIntBuffer();
        this.d.put(new float[] { -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f }).position(0);
        this.e.put(new float[] { 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f }).position(0);
        this.f.put(new int[] { 0, 1, 2, 0, 3, 2 });
        for (int i = 0; i < 21; ++i) {
            this.f.put(TuSDKMonsterSquareFace.i[i] + this.g);
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
            final MonsterSquareFaceInfo monsterSquareFaceInfo = new MonsterSquareFaceInfo(b[i]);
            monsterSquareFaceInfo.b(this.e, this.g * 2);
            this.e.position(0);
            monsterSquareFaceInfo.a();
            monsterSquareFaceInfo.a(this.d, this.g * 2);
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
        GLES20.glVertexAttribPointer(this.mFilterPositionAttribute, 2, 5126, false, 0, (Buffer)this.d);
        GLES20.glVertexAttribPointer(this.mFilterTextureCoordinateAttribute, 2, 5126, false, 0, (Buffer)this.e);
        this.checkGLError(this.getClass().getSimpleName() + " bindFramebuffer");
        GLES20.glDrawElements(4, this.f.limit(), 5125, (Buffer)this.f);
        this.captureFilterImage(this.getClass().getSimpleName(), this.mInputTextureSize.width, this.mInputTextureSize.height);
        this.inputFramebufferUnlock();
        this.cacaptureImageBuffer();
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
        i = new int[] { 0, 2, 3, 0, 3, 4, 1, 4, 5, 1, 5, 6, 2, 3, 4, 2, 4, 6, 4, 5, 6 };
    }
    
    private class MonsterSquareFaceInfo
    {
        PointF a;
        PointF[] b;
        PointF[] c;
        PointF d;
        PointF e;
        
        private MonsterSquareFaceInfo(final FaceAligment faceAligment) {
            this.b = new PointF[2];
            this.c = new PointF[5];
            if (faceAligment != null) {
                this.a(faceAligment.getOrginMarks());
            }
        }
        
        private void a(final FloatBuffer floatBuffer, final int n) {
            floatBuffer.position(n);
            for (int i = 0; i < 2; ++i) {
                floatBuffer.put(this.b[i].x * 2.0f - 1.0f);
                floatBuffer.put(this.b[i].y * 2.0f - 1.0f);
            }
            for (int j = 0; j < 5; ++j) {
                floatBuffer.put(this.c[j].x * 2.0f - 1.0f);
                floatBuffer.put(this.c[j].y * 2.0f - 1.0f);
            }
        }
        
        private void b(final FloatBuffer floatBuffer, final int n) {
            floatBuffer.position(n);
            for (int i = 0; i < 2; ++i) {
                floatBuffer.put(this.b[i].x);
                floatBuffer.put(this.b[i].y);
            }
            for (int j = 0; j < 5; ++j) {
                floatBuffer.put(this.c[j].x);
                floatBuffer.put(this.c[j].y);
            }
        }
        
        private void a() {
            this.c[1].x = this.d.x;
            this.c[1].y = this.d.y;
            this.c[3].x = this.e.x;
            this.c[3].y = this.e.y;
        }
        
        private void a(final PointF[] array) {
            if (array == null || array.length < 3) {
                return;
            }
            this.a = new PointF(array[46].x, array[46].y);
            this.c[0] = new PointF(array[0].x, array[0].y);
            this.c[1] = new PointF(array[8].x, array[8].y);
            this.c[2] = new PointF(array[16].x, array[16].y);
            this.c[3] = new PointF(array[24].x, array[24].y);
            this.c[4] = new PointF(array[32].x, array[32].y);
            final float abs = Math.abs(PointCalc.distance(this.c[0], this.c[4]));
            final float abs2 = Math.abs(PointCalc.distance(this.c[1], this.c[3]));
            final float n = -(abs - abs2) / abs2;
            final PointF crossPoint = PointCalc.crossPoint(array[16], array[43], this.c[1], this.c[3]);
            this.d = PointCalc.percentage(this.c[1], crossPoint, n);
            this.e = PointCalc.percentage(this.c[3], crossPoint, n);
            this.d = PointCalc.percentage(this.d, crossPoint, 0.05f);
            this.e = PointCalc.percentage(this.e, crossPoint, 0.05f);
            this.b[0] = PointCalc.percentage(this.d, this.a, -0.2f);
            this.b[1] = PointCalc.percentage(this.e, this.a, -0.2f);
        }
    }
}
