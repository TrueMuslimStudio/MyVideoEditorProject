// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.reshape;

import android.graphics.PointF;
import java.util.List;
import java.nio.IntBuffer;
import com.example.myvideoeditorapp.kore.seles.SelesFramebufferCache;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import android.opengl.GLES20;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import java.nio.FloatBuffer;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.seles.filters.SelesPointDrawFilter;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;

public class TuSDKPlasticFaceFilter extends SelesFilter implements SelesParameters.FilterFacePositionInterface, SelesParameters.FilterParameterInterface
{
    private SelesPointDrawFilter a;
    private boolean b;
    private final Object c;
    private int d;
    private float[] e;
    private float[] f;
    private int[] g;
    private boolean h;
    private FaceAligment[] i;
    private static final int[] j;
    private float k;
    private float l;
    private float m;
    private float n;
    private float o;
    private float p;
    private float q;
    private float r;
    private float s;
    private float t;
    private float u;
    private float v;
    private float w;
    private float x;
    private float y;
    private float z;
    private float A;
    private float B;
    private float C;
    private float D;
    
    public float getForeheadHeight() {
        return this.k;
    }
    
    public void setForeheadHeight(final float k) {
        this.k = k;
    }
    
    public float getFaceSmall() {
        return this.l;
    }
    
    public void setFaceSmall(final float l) {
        this.l = l;
    }
    
    public float getCheekThin() {
        return this.m;
    }
    
    public void setCheekThin(final float m) {
        this.m = m;
    }
    
    public float getCheekNarrow() {
        return this.n;
    }
    
    public void setCheekNarrow(final float n) {
        this.n = n;
    }
    
    public float getCheekBoneNarrow() {
        return this.o;
    }
    
    public void setCheekBoneNarrow(final float o) {
        this.o = o;
    }
    
    public float getCheekLowBoneNarrow() {
        return this.p;
    }
    
    public void setCheekLowBoneNarrow(final float p) {
        this.p = p;
    }
    
    public float getBrowHeight() {
        return this.r;
    }
    
    public void setBrowHeight(final float r) {
        this.r = r;
    }
    
    public float getBrowThickness() {
        return this.q;
    }
    
    public void setBrowThickness(final float q) {
        this.q = q;
    }
    
    public float getEyeEnlarge() {
        return this.s;
    }
    
    public void setEyeEnlarge(final float s) {
        this.s = s;
    }
    
    public float getEyeAngle() {
        return this.t;
    }
    
    public void setEyeAngle(final float t) {
        this.t = t;
    }
    
    public float getEyeDistance() {
        return this.u;
    }
    
    public void setEyeDistance(final float u) {
        this.u = u;
    }
    
    public float getEyeHeight() {
        return this.v;
    }
    
    public void setEyeHeight(final float v) {
        this.v = v;
    }
    
    public float getEyeInnerConer() {
        return this.w;
    }
    
    public void setEyeInnerConer(final float w) {
        this.w = w;
    }
    
    public float getEyeOuterConer() {
        return this.x;
    }
    
    public void setEyeOuterConer(final float x) {
        this.x = x;
    }
    
    public float getNoseWidth() {
        return this.y;
    }
    
    public void setNoseWidth(final float y) {
        this.y = y;
    }
    
    public float getNoseHeight() {
        return this.z;
    }
    
    public void setNoseHeight(final float z) {
        this.z = z;
    }
    
    public float getPhilterumThickness() {
        return this.A;
    }
    
    public void setPhilterumThickness(final float a) {
        this.A = a;
    }
    
    public float getMouthWidth() {
        return this.B;
    }
    
    public void setMouthWidth(final float b) {
        this.B = b;
    }
    
    public float getLipsThickness() {
        return this.C;
    }
    
    public void setLipsThickness(final float c) {
        this.C = c;
    }
    
    public float getChinThickness() {
        return this.D;
    }
    
    public void setChinThickness(final float d) {
        this.D = d;
    }
    
    public TuSDKPlasticFaceFilter() {
        this.b = false;
        this.c = new Object();
        this.d = 6;
        this.h = false;
        this.k = 0.0f;
        this.l = 0.0f;
        this.m = 0.0f;
        this.n = 0.0f;
        this.o = 0.0f;
        this.p = 0.0f;
        this.q = 0.0f;
        this.r = 0.0f;
        this.s = 0.0f;
        this.t = 0.0f;
        this.u = 0.0f;
        this.v = 0.0f;
        this.w = 0.0f;
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.A = 0.0f;
        this.B = 0.0f;
        this.C = 0.0f;
        this.D = 0.0f;
        this.a();
        if (this.b) {
            this.addTarget(this.a = new SelesPointDrawFilter(), 0);
        }
    }
    
    private void a() {
        final float[] array = { -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f };
        final float[] array2 = { 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f };
        final int[] array3 = { 0, 1, 2, 0, 3, 2 };
        System.arraycopy(array, 0, this.e = new float[198], 0, 8);
        System.arraycopy(array2, 0, this.f = new float[198], 0, 8);
        System.arraycopy(array3, 0, this.g = new int[546], 0, 6);
        for (int i = 0; i < 540; ++i) {
            this.g[6 + i] = TuSDKPlasticFaceFilter.j[i] + 4;
        }
    }
    
    @Override
    public void removeAllTargets() {
        super.removeAllTargets();
        if (this.a != null) {
            this.addTarget(this.a, 0);
        }
    }
    
    @Override
    protected void onInitOnGLThread() {
        super.onInitOnGLThread();
        this.checkGLError("TuSdkPlasticFaceFilter onInitOnGLThread");
    }
    
    @Override
    protected void renderToTexture(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        this.runPendingOnDrawTasks();
        if (this.isPreventRendering()) {
            this.inputFramebufferUnlock();
            return;
        }
        int n = 0;
        final FaceAligment[] i = this.i;
        while (true) {
            this.d = 6;
            if (this.h && n < i.length) {
                final TuSDKPlasticFaceModel tuSDKPlasticFaceModel = new TuSDKPlasticFaceModel(i[n], this.sizeOfFBO());
                System.arraycopy(tuSDKPlasticFaceModel.getPoints(false), 0, this.f, 8, 190);
                tuSDKPlasticFaceModel.calcForeheadHeight(this.k);
                tuSDKPlasticFaceModel.calcFaceSmall(this.l);
                tuSDKPlasticFaceModel.calcCheekThin(this.m);
                tuSDKPlasticFaceModel.calcCheekNarrow(this.n);
                tuSDKPlasticFaceModel.calcCheekBoneNarrow(this.o);
                tuSDKPlasticFaceModel.calcCheekLowerBoneNarrow(this.p);
                tuSDKPlasticFaceModel.calcBrowThickness(this.q);
                tuSDKPlasticFaceModel.calcBrowHeight(this.r);
                tuSDKPlasticFaceModel.calcEyeEnlarge(this.s);
                tuSDKPlasticFaceModel.calcEyeAngle(this.t);
                tuSDKPlasticFaceModel.calcEyeDistance(this.u);
                tuSDKPlasticFaceModel.calcEyeHeight(this.v);
                tuSDKPlasticFaceModel.calcEyeInnerConerOpen(this.w);
                tuSDKPlasticFaceModel.calcEyeOuterConerOpen(this.x);
                tuSDKPlasticFaceModel.calcNoseWidth(this.y);
                tuSDKPlasticFaceModel.calcNoseHeight(this.z);
                tuSDKPlasticFaceModel.calcPhiltrumThickness(this.A);
                tuSDKPlasticFaceModel.calcMouthWidth(this.B);
                tuSDKPlasticFaceModel.calcLipsThickness(this.C);
                tuSDKPlasticFaceModel.calcChinThickness(this.D);
                System.arraycopy(tuSDKPlasticFaceModel.getPoints(true), 0, this.e, 8, 190);
                if (this.b) {
                    this.a(tuSDKPlasticFaceModel.getPoints());
                }
                this.d = 546;
            }
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
            if (this.mUsingNextFrameForImageCapture && n == this.i.length - 1) {
                this.mOutputFramebuffer.lock();
            }
            this.setUniformsForProgramAtIndex(0);
            GLES20.glClearColor(this.mBackgroundColorRed, this.mBackgroundColorGreen, this.mBackgroundColorBlue, this.mBackgroundColorAlpha);
            GLES20.glClear(16384);
            this.inputFramebufferBindTexture();
            final FloatBuffer floatBuffer3 = ByteBuffer.allocateDirect(792).order(ByteOrder.nativeOrder()).asFloatBuffer();
            floatBuffer3.position(0);
            floatBuffer3.put(this.e);
            floatBuffer3.position(0);
            final FloatBuffer floatBuffer4 = ByteBuffer.allocateDirect(792).order(ByteOrder.nativeOrder()).asFloatBuffer();
            floatBuffer4.position(0);
            floatBuffer4.put(this.f);
            floatBuffer4.position(0);
            final IntBuffer intBuffer = ByteBuffer.allocateDirect(2184).order(ByteOrder.nativeOrder()).asIntBuffer();
            intBuffer.position(0);
            intBuffer.put(this.g);
            intBuffer.position(0);
            GLES20.glVertexAttribPointer(this.mFilterPositionAttribute, 2, 5126, false, 0, (Buffer)floatBuffer3);
            GLES20.glVertexAttribPointer(this.mFilterTextureCoordinateAttribute, 2, 5126, false, 0, (Buffer)floatBuffer4);
            GLES20.glDrawElements(4, this.d, 5125, (Buffer)intBuffer);
            this.captureFilterImage(this.getClass().getSimpleName(), this.mInputTextureSize.width, this.mInputTextureSize.height);
            this.inputFramebufferUnlock();
            this.cacaptureImageBuffer();
            if (i == null || n >= i.length || ++n >= i.length) {
                return;
            }
            this.setInputFramebuffer(this.framebufferForOutput(), 0);
        }
    }
    
    @Override
    protected SelesParameters initParams(SelesParameters initParams) {
        initParams = super.initParams(initParams);
        initParams.appendFloatArg("forehead", this.getForeheadHeight(), -1.0f, 1.0f);
        initParams.appendFloatArg("smallFace", this.getFaceSmall(), 0.0f, 1.0f);
        initParams.appendFloatArg("chinSize", this.getCheekThin(), 0.0f, 1.0f);
        initParams.appendFloatArg("cheekNarrow", this.getCheekNarrow(), 0.0f, 1.0f);
        initParams.appendFloatArg("cheekBoneNarrow", this.getCheekBoneNarrow(), 0.0f, 1.0f);
        initParams.appendFloatArg("cheekLowBoneNarrow", this.getCheekLowBoneNarrow(), 0.0f, 1.0f);
        initParams.appendFloatArg("archEyebrow", this.getBrowThickness(), -1.0f, 1.0f);
        initParams.appendFloatArg("browPosition", this.getBrowHeight(), -1.0f, 1.0f);
        initParams.appendFloatArg("eyeSize", this.getEyeEnlarge(), 0.0f, 1.0f);
        initParams.appendFloatArg("eyeAngle", this.getEyeAngle(), -1.0f, 1.0f);
        initParams.appendFloatArg("eyeDis", this.getEyeDistance(), -1.0f, 1.0f);
        initParams.appendFloatArg("eyeHeight", this.getEyeHeight(), -1.0f, 1.0f);
        initParams.appendFloatArg("eyeInnerConer", this.getEyeInnerConer(), 0.0f, 1.0f);
        initParams.appendFloatArg("eyeOuterConer", this.getEyeOuterConer(), 0.0f, 1.0f);
        initParams.appendFloatArg("noseSize", this.getNoseWidth(), 0.0f, 1.0f);
        initParams.appendFloatArg("noseHeight", this.getNoseHeight(), 0.0f, 1.0f);
        initParams.appendFloatArg("philterum", this.getPhilterumThickness(), -1.0f, 1.0f);
        initParams.appendFloatArg("mouthWidth", this.getMouthWidth(), -1.0f, 1.0f);
        initParams.appendFloatArg("lips", this.getLipsThickness(), -1.0f, 1.0f);
        initParams.appendFloatArg("jawSize", this.getChinThickness(), -1.0f, 1.0f);
        return initParams;
    }
    
    @Override
    protected void submitFilterArg(final SelesParameters.FilterArg filterArg) {
        if (filterArg == null) {
            return;
        }
        if (filterArg.equalsKey("forehead")) {
            this.setForeheadHeight(filterArg.getValue());
        }
        else if (filterArg.equalsKey("smallFace")) {
            this.setFaceSmall(filterArg.getValue());
        }
        else if (filterArg.equalsKey("chinSize")) {
            this.setCheekThin(filterArg.getValue());
        }
        else if (filterArg.equalsKey("cheekNarrow")) {
            this.setCheekNarrow(filterArg.getValue());
        }
        else if (filterArg.equalsKey("cheekBoneNarrow")) {
            this.setCheekBoneNarrow(filterArg.getValue());
        }
        else if (filterArg.equalsKey("cheekLowBoneNarrow")) {
            this.setCheekLowBoneNarrow(filterArg.getValue());
        }
        else if (filterArg.equalsKey("archEyebrow")) {
            this.setBrowThickness(filterArg.getValue());
        }
        else if (filterArg.equalsKey("browPosition")) {
            this.setBrowHeight(filterArg.getValue());
        }
        else if (filterArg.equalsKey("eyeSize")) {
            this.setEyeEnlarge(filterArg.getValue());
        }
        else if (filterArg.equalsKey("eyeAngle")) {
            this.setEyeAngle(filterArg.getValue());
        }
        else if (filterArg.equalsKey("eyeDis")) {
            this.setEyeDistance(filterArg.getValue());
        }
        else if (filterArg.equalsKey("eyeHeight")) {
            this.setEyeHeight(filterArg.getValue());
        }
        else if (filterArg.equalsKey("eyeInnerConer")) {
            this.setEyeInnerConer(filterArg.getValue());
        }
        else if (filterArg.equalsKey("eyeOuterConer")) {
            this.setEyeOuterConer(filterArg.getValue());
        }
        else if (filterArg.equalsKey("noseSize")) {
            this.setNoseWidth(filterArg.getValue());
        }
        else if (filterArg.equalsKey("noseHeight")) {
            this.setNoseHeight(filterArg.getValue());
        }
        else if (filterArg.equalsKey("philterum")) {
            this.setPhilterumThickness(filterArg.getValue());
        }
        else if (filterArg.equalsKey("mouthWidth")) {
            this.setMouthWidth(filterArg.getValue());
        }
        else if (filterArg.equalsKey("lips")) {
            this.setLipsThickness(filterArg.getValue());
        }
        else if (filterArg.equalsKey("jawSize")) {
            this.setChinThickness(filterArg.getValue());
        }
    }
    
    @Override
    public void updateFaceFeatures(final FaceAligment[] i, final float n) {
        if (i == null || i.length < 1) {
            this.h = false;
            this.i = null;
            this.d = 6;
            return;
        }
        synchronized (this.c) {
            this.i = i;
        }
        this.h = true;
    }
    
    private void a(final List<PointF> list) {
        if (this.a == null) {
            return;
        }
        final FaceAligment[] array = { new FaceAligment(list.toArray(new PointF[list.size()])) };
        this.a.updateElemIndex(this.g, this.e);
        this.a.updateFaceFeatures(array, 0.0f);
    }
    
    static {
        j = new int[] { 53, 52, 22, 52, 53, 43, 1, 88, 0, 88, 1, 89, 0, 88, 17, 80, 85, 79, 85, 80, 84, 1, 0, 36, 67, 31, 68, 31, 67, 64, 89, 1, 2, 2, 1, 41, 74, 9, 75, 9, 74, 10, 3, 89, 2, 89, 3, 4, 3, 2, 64, 30, 35, 34, 35, 30, 63, 89, 5, 90, 5, 89, 4, 4, 3, 66, 64, 2, 57, 90, 5, 6, 5, 4, 66, 41, 1, 36, 90, 6, 7, 6, 5, 77, 63, 30, 29, 90, 8, 91, 8, 90, 7, 7, 6, 76, 57, 2, 41, 8, 7, 75, 9, 91, 8, 91, 9, 10, 9, 8, 75, 91, 10, 11, 40, 38, 39, 38, 40, 57, 11, 92, 91, 92, 11, 12, 11, 10, 72, 39, 62, 40, 62, 39, 60, 92, 12, 13, 12, 11, 72, 36, 17, 48, 17, 36, 0, 92, 13, 14, 13, 12, 72, 48, 19, 49, 19, 48, 18, 92, 14, 15, 14, 13, 65, 36, 48, 37, 92, 15, 93, 15, 14, 45, 51, 60, 39, 60, 51, 27, 15, 16, 93, 16, 15, 45, 17, 88, 18, 63, 61, 42, 61, 63, 28, 18, 88, 86, 87, 86, 88, 44, 59, 58, 59, 44, 46, 17, 18, 48, 18, 86, 19, 86, 21, 20, 21, 86, 22, 19, 20, 49, 20, 19, 86, 21, 22, 27, 53, 22, 23, 52, 43, 42, 20, 21, 50, 61, 52, 42, 52, 61, 27, 46, 44, 45, 22, 86, 23, 23, 86, 24, 54, 55, 44, 55, 54, 24, 23, 24, 54, 24, 86, 25, 26, 93, 16, 93, 26, 25, 24, 25, 55, 93, 86, 94, 25, 86, 93, 16, 45, 26, 25, 26, 55, 27, 22, 52, 30, 62, 29, 62, 30, 31, 21, 27, 51, 66, 3, 64, 27, 28, 60, 28, 27, 61, 71, 35, 65, 35, 71, 70, 68, 31, 32, 28, 29, 62, 29, 28, 63, 70, 81, 80, 81, 70, 71, 7, 76, 75, 80, 68, 69, 68, 80, 79, 31, 30, 32, 66, 64, 67, 33, 69, 68, 69, 33, 70, 32, 30, 33, 70, 33, 34, 32, 33, 68, 33, 30, 34, 65, 72, 71, 72, 65, 13, 34, 35, 70, 59, 65, 63, 65, 59, 46, 48, 49, 37, 57, 37, 56, 37, 57, 41, 50, 21, 51, 36, 37, 41, 49, 56, 37, 56, 49, 50, 38, 57, 56, 50, 51, 38, 39, 38, 51, 28, 62, 60, 57, 62, 64, 62, 57, 40, 63, 42, 47, 46, 14, 65, 14, 46, 45, 42, 43, 47, 53, 58, 43, 58, 53, 54, 58, 47, 43, 47, 58, 59, 45, 55, 26, 55, 45, 44, 59, 63, 47, 49, 20, 50, 50, 38, 56, 53, 23, 54, 54, 44, 58, 62, 31, 64, 35, 63, 65, 77, 5, 66, 66, 67, 78, 84, 80, 83, 67, 68, 79, 83, 82, 73, 82, 83, 81, 69, 70, 80, 73, 10, 74, 10, 73, 72, 71, 72, 82, 72, 73, 82, 82, 81, 71, 73, 74, 83, 74, 75, 84, 85, 78, 79, 78, 85, 77, 75, 76, 84, 76, 6, 77, 79, 78, 67, 76, 77, 85, 77, 66, 78, 85, 84, 76, 84, 83, 74, 83, 80, 81, 86, 87, 94 };
    }
}
