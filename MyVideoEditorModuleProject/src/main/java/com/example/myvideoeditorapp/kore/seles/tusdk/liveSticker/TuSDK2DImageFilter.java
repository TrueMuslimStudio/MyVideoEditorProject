// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.liveSticker;

import android.opengl.Matrix;
import com.example.myvideoeditorapp.kore.struct.TuSdkSizeF;
import com.example.myvideoeditorapp.kore.utils.RectHelper;
import android.graphics.Rect;
import android.graphics.PointF;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import java.nio.Buffer;
import android.opengl.GLES20;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import android.graphics.RectF;
import com.example.myvideoeditorapp.kore.seles.tusdk.textSticker.TuSdkImage2DSticker;
import java.util.List;
import java.nio.FloatBuffer;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;

public class TuSDK2DImageFilter extends SelesFilter
{
    public static final String TUSDK_MAP_2D_VERTEX_SHADER = "attribute vec4 position;attribute vec4 inputTextureCoordinate;varying vec2 textureCoordinate;uniform mat4 uMVPMatrix;uniform mat4 uTexMatrix;void main(){    gl_Position = uMVPMatrix * position;\n    textureCoordinate = (uTexMatrix * inputTextureCoordinate).xy;}";
    protected static final float[] stickerVertices;
    private final FloatBuffer a;
    private final FloatBuffer b;
    private int c;
    private int d;
    private final float[] e;
    private final float[] f;
    private List<TuSdkImage2DSticker> g;
    private RectF h;
    private float i;
    protected float mDeviceRadian;
    
    public TuSDK2DImageFilter() {
        this("attribute vec4 position;attribute vec4 inputTextureCoordinate;varying vec2 textureCoordinate;uniform mat4 uMVPMatrix;uniform mat4 uTexMatrix;void main(){    gl_Position = uMVPMatrix * position;\n    textureCoordinate = (uTexMatrix * inputTextureCoordinate).xy;}", "varying highp vec2 textureCoordinate;uniform sampler2D inputImageTexture;void main(){     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);}");
    }
    
    public TuSDK2DImageFilter(final String s) {
        this("attribute vec4 position;attribute vec4 inputTextureCoordinate;varying vec2 textureCoordinate;uniform mat4 uMVPMatrix;uniform mat4 uTexMatrix;void main(){    gl_Position = uMVPMatrix * position;\n    textureCoordinate = (uTexMatrix * inputTextureCoordinate).xy;}", s);
    }
    
    public TuSDK2DImageFilter(final String s, final String s2) {
        super(s, s2);
        this.e = new float[16];
        this.f = new float[16];
        this.mDeviceRadian = 0.0f;
        this.a = SelesFilter.buildBuffer(TuSDK2DImageFilter.stickerVertices);
        this.b = SelesFilter.buildBuffer(TuSDK2DImageFilter.noRotationTextureCoordinates);
    }
    
    public void setDisplayRect(final RectF h, final float i) {
        this.h = h;
        this.i = i;
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    @Override
    protected void onInitOnGLThread() {
        super.onInitOnGLThread();
        this.c = this.mFilterProgram.uniformIndex("uTexMatrix");
        this.d = this.mFilterProgram.uniformIndex("uMVPMatrix");
        this.checkGLError(this.getClass().getSimpleName() + " onInitOnGLThread");
    }
    
    @Override
    public void newFrameReady(final long n, final int n2) {
        if (this.mFirstInputFramebuffer == null) {
            return;
        }
        this.b.clear();
        this.b.put(SelesFilter.textureCoordinates(this.mInputRotation)).position(0);
        this.renderToTexture(this.a, this.b);
        this.informTargetsAboutNewFrame(n);
    }
    
    @Override
    protected void renderToTexture(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
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
        this.checkGLError(this.getClass().getSimpleName() + " activateFramebuffer");
        this.setUniformsForProgramAtIndex(0);
        if (this.getStickerCount() > 0) {
            GLES20.glEnable(3042);
            this.a(floatBuffer, floatBuffer2);
            GLES20.glDisable(3042);
        }
        this.captureFilterImage(this.getClass().getSimpleName(), this.mInputTextureSize.width, this.mInputTextureSize.height);
        this.cacaptureImageBuffer();
    }
    
    private void a(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        int n = 0;
        while (n < this.getStickerCount() && n < this.g.size()) {
            final TuSdkImage2DSticker tuSdkImage2DSticker = this.g.get(n);
            if (tuSdkImage2DSticker == null) {
                continue;
            }
            GLES20.glBlendFunc(1, 771);
            this.a(tuSdkImage2DSticker, floatBuffer, floatBuffer2, -1);
            ++n;
        }
    }
    
    private void a(final TuSdkImage2DSticker tuSdkImage2DSticker, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final int n) {
        if (tuSdkImage2DSticker == null || !tuSdkImage2DSticker.isEnabled()) {
            return;
        }
        final TuSdkSize sizeOfFBO = this.sizeOfFBO();
        final float[] src = { -0.5f, -0.5f, 0.0f, 1.0f, 0.5f, -0.5f, 0.0f, 1.0f, -0.5f, 0.5f, 0.0f, 1.0f, 0.5f, 0.5f, 0.0f, 1.0f };
        this.a(tuSdkImage2DSticker, sizeOfFBO);
        floatBuffer.clear();
        floatBuffer.put(src).position(0);
        GLES20.glActiveTexture(33986);
        GLES20.glBindTexture(3553, tuSdkImage2DSticker.getCurrentTextureId());
        GLES20.glUniform1i(this.mFilterInputTextureUniform, 2);
        GLES20.glUniformMatrix4fv(this.d, 1, false, this.f, 0);
        GLES20.glUniformMatrix4fv(this.c, 1, false, this.e, 0);
        GLES20.glVertexAttribPointer(this.mFilterPositionAttribute, 4, 5126, false, 0, (Buffer)floatBuffer);
        GLES20.glVertexAttribPointer(this.mFilterTextureCoordinateAttribute, 2, 5126, false, 0, (Buffer)floatBuffer2);
        GLES20.glDrawArrays(5, 0, 4);
    }
    
    public void updateStickers(final List<TuSdkImage2DSticker> g) {
        this.g = g;
    }
    
    protected int getStickerCount() {
        if (this.g == null) {
            return 0;
        }
        return this.g.size();
    }
    
    private void a(final TuSdkImage2DSticker tuSdkImage2DSticker, final TuSdkSize tuSdkSize) {
        RectF h = this.h;
        final PointF pointF = new PointF(0.0f, 0.0f);
        final PointF pointF2 = new PointF(0.0f, 0.0f);
        final PointF pointF3 = new PointF(0.0f, 0.0f);
        final PointF pointF4 = new PointF((float)tuSdkSize.width, (float)tuSdkSize.height);
        if (h == null || h.isEmpty()) {
            if (this.i > 0.0f) {
                final TuSdkSize create = TuSdkSize.create(tuSdkSize);
                create.width = (int)(tuSdkSize.height * this.i);
                final Rect rectWithAspectRatioInsideRect = RectHelper.makeRectWithAspectRatioInsideRect(create, new Rect(0, 0, tuSdkSize.width, tuSdkSize.height));
                h = new RectF(rectWithAspectRatioInsideRect.left / (float)tuSdkSize.width, rectWithAspectRatioInsideRect.top / (float)tuSdkSize.height, rectWithAspectRatioInsideRect.right / (float)tuSdkSize.width, rectWithAspectRatioInsideRect.bottom / (float)tuSdkSize.height);
            }
            else {
                h = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
            }
        }
        if (this.i > 0.0f) {
            if (tuSdkSize.width / (float)tuSdkSize.height > this.i) {
                pointF4.x = tuSdkSize.height * this.i;
                pointF4.y = (float)tuSdkSize.height;
            }
            else {
                pointF4.x = (float)tuSdkSize.width;
                pointF4.y = tuSdkSize.width / this.i;
            }
        }
        else {
            pointF4.x = tuSdkSize.width * (h.left + h.right);
            pointF4.y = tuSdkSize.height * (h.top + h.bottom);
        }
        pointF3.x = tuSdkSize.width * h.left;
        pointF3.y = tuSdkSize.height * h.top;
        pointF.x = tuSdkSize.width * tuSdkImage2DSticker.getCurrentSticker().getStickerWidth();
        pointF.y = tuSdkSize.height * tuSdkImage2DSticker.getCurrentSticker().getStickerHeight();
        if (new TuSdkSizeF(pointF.x, pointF.y).toSizeCeil().maxMinRatio() != tuSdkImage2DSticker.getCurrentSticker().getRatio()) {
            if (pointF.x > pointF.y) {
                pointF.x = pointF.y * tuSdkImage2DSticker.getCurrentSticker().getRatio();
            }
            else if (pointF.x < pointF.y) {
                pointF.y = pointF.x / tuSdkImage2DSticker.getCurrentSticker().getRatio();
            }
        }
        pointF2.x = pointF.x / 2.0f + pointF4.x * tuSdkImage2DSticker.getCurrentSticker().getOffsetX() + pointF3.x;
        pointF2.y = pointF.y / 2.0f + pointF4.y * tuSdkImage2DSticker.getCurrentSticker().getOffsetY() + pointF3.y;
        this.a(tuSdkSize, pointF, pointF2, tuSdkImage2DSticker.getCurrentSticker().getRotation());
    }
    
    private void a(final TuSdkSize tuSdkSize, final PointF pointF, final PointF pointF2, final float n) {
        final float[] array = new float[16];
        Matrix.setIdentityM(array, 0);
        final float[] array2 = new float[16];
        Matrix.setIdentityM(array2, 0);
        Matrix.setIdentityM(this.e, 0);
        Matrix.orthoM(array, 0, 0.0f, (float)tuSdkSize.width, 0.0f, (float)tuSdkSize.height, -1.0f, 1.0f);
        Matrix.translateM(array2, 0, pointF2.x, pointF2.y, 0.0f);
        if (n != 0.0f) {
            Matrix.rotateM(array2, 0, n, 0.0f, 0.0f, 1.0f);
        }
        Matrix.scaleM(array2, 0, pointF.x, pointF.y, 1.0f);
        Matrix.multiplyMM(this.f, 0, array, 0, array2, 0);
    }
    
    static {
        stickerVertices = new float[] { -0.5f, -0.5f, 0.0f, 1.0f, 0.5f, -0.5f, 0.0f, 1.0f, -0.5f, 0.5f, 0.0f, 1.0f, 0.5f, 0.5f, 0.0f, 1.0f };
    }
}
