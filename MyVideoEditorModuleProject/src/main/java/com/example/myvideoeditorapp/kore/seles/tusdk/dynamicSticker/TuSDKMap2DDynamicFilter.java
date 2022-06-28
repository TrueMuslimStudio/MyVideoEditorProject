// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.dynamicSticker;

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;
import com.example.myvideoeditorapp.kore.sticker.StickerPositionInfo;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.RectHelper;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerDynamicData;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.Iterator;
import java.util.List;

public class TuSDKMap2DDynamicFilter extends SelesFilter
{
    public static final String TUSDK_MAP_2D_VERTEX_SHADER = "attribute vec4 position;attribute vec4 inputTextureCoordinate;varying vec2 textureCoordinate;uniform mat4 uMVPMatrix;uniform mat4 uTexMatrix;void main(){    gl_Position = uMVPMatrix * position;\n    textureCoordinate = (uTexMatrix * inputTextureCoordinate).xy;}";
    protected static final float[] stickerVertices;
    private final FloatBuffer a;
    private final FloatBuffer b;
    private int c;
    private int d;
    private final float[] e;
    private final float[] f;
    private List<TuSDKDynamicStickerImage> g;
    private RectF h;
    private float i;
    protected FaceAligment[] mFaces;
    protected float mDeviceRadian;
    private boolean j;
    
    public boolean isStickerVisibility() {
        return this.j;
    }
    
    public void setStickerVisibility(final boolean j) {
        this.j = j;
    }
    
    public TuSDKMap2DDynamicFilter() {
        this("attribute vec4 position;attribute vec4 inputTextureCoordinate;varying vec2 textureCoordinate;uniform mat4 uMVPMatrix;uniform mat4 uTexMatrix;void main(){    gl_Position = uMVPMatrix * position;\n    textureCoordinate = (uTexMatrix * inputTextureCoordinate).xy;}", "varying highp vec2 textureCoordinate;uniform sampler2D inputImageTexture;void main(){     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);}");
    }
    
    public TuSDKMap2DDynamicFilter(final String s) {
        this("attribute vec4 position;attribute vec4 inputTextureCoordinate;varying vec2 textureCoordinate;uniform mat4 uMVPMatrix;uniform mat4 uTexMatrix;void main(){    gl_Position = uMVPMatrix * position;\n    textureCoordinate = (uTexMatrix * inputTextureCoordinate).xy;}", s);
    }
    
    public TuSDKMap2DDynamicFilter(final String s, final String s2) {
        super(s, s2);
        this.e = new float[16];
        this.f = new float[16];
        this.mDeviceRadian = 0.0f;
        this.a = SelesFilter.buildBuffer(TuSDKMap2DDynamicFilter.stickerVertices);
        this.b = SelesFilter.buildBuffer(TuSDKMap2DDynamicFilter.noRotationTextureCoordinates);
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
        if (this.isStickerVisibility() && this.getStickerCount() > 0) {
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
            final TuSDKDynamicStickerImage tuSDKDynamicStickerImage = this.g.get(n);
            if (tuSDKDynamicStickerImage == null) {
                continue;
            }
            final StickerPositionInfo positionInfo = tuSDKDynamicStickerImage.getSticker().positionInfo;
            if (positionInfo == null) {
                continue;
            }
            switch (positionInfo.getRenderType().ordinal()) {
                case 1: {
                    GLES20.glBlendFunc(774, 771);
                    break;
                }
                case 2: {
                    GLES20.glBlendFunc(775, 1);
                    break;
                }
                default: {
                    GLES20.glBlendFunc(1, 771);
                    break;
                }
            }
            if (tuSDKDynamicStickerImage.getSticker().requireFaceFeature()) {
                for (int a = this.a(), i = 0; i < a; ++i) {
                    this.a(tuSDKDynamicStickerImage, floatBuffer, floatBuffer2, i);
                }
            }
            else {
                this.a(tuSDKDynamicStickerImage, floatBuffer, floatBuffer2, -1);
            }
            ++n;
        }
    }
    
    private void a(final TuSDKDynamicStickerImage tuSDKDynamicStickerImage, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2, final int n) {
        if (tuSDKDynamicStickerImage == null || !tuSDKDynamicStickerImage.isEnabled() || tuSDKDynamicStickerImage.getCurrentTextureID() < 1) {
            return;
        }
        final TuSdkSize sizeOfFBO = this.sizeOfFBO();
        final StickerPositionInfo positionInfo = tuSDKDynamicStickerImage.getSticker().positionInfo;
        float[] a = { -0.5f, -0.5f, 0.0f, 1.0f, 0.5f, -0.5f, 0.0f, 1.0f, -0.5f, 0.5f, 0.0f, 1.0f, 0.5f, 0.5f, 0.0f, 1.0f };
        if (n < 0) {
            this.a(positionInfo, sizeOfFBO, tuSDKDynamicStickerImage.getTextureSize());
        }
        else if (n >= 0) {
            if (!this.a(positionInfo, sizeOfFBO, n, floatBuffer)) {
                return;
            }
            a = this.a(this.a(n), a);
        }
        floatBuffer.clear();
        floatBuffer.put(a).position(0);
        GLES20.glActiveTexture(33986);
        GLES20.glBindTexture(3553, tuSDKDynamicStickerImage.getCurrentTextureID());
        GLES20.glUniform1i(this.mFilterInputTextureUniform, 2);
        GLES20.glUniformMatrix4fv(this.d, 1, false, this.f, 0);
        GLES20.glUniformMatrix4fv(this.c, 1, false, this.e, 0);
        GLES20.glVertexAttribPointer(this.mFilterPositionAttribute, 4, 5126, false, 0, (Buffer)floatBuffer);
        GLES20.glVertexAttribPointer(this.mFilterTextureCoordinateAttribute, 2, 5126, false, 0, (Buffer)floatBuffer2);
        GLES20.glDrawArrays(5, 0, 4);
    }
    
    private float[] a(final FaceAligment faceAligment, final float[] array) {
        if (faceAligment == null || array == null) {
            return array;
        }
        float n;
        if (faceAligment.yaw < 0.0f) {
            n = ((faceAligment.yaw < -50.0f) ? -50.0f : faceAligment.yaw) / 120.0f;
        }
        else {
            n = ((faceAligment.yaw > 50.0f) ? 50.0f : faceAligment.yaw) / 120.0f;
        }
        float n2;
        if (faceAligment.pitch < 0.0f) {
            n2 = ((faceAligment.pitch < -50.0f) ? -50.0f : faceAligment.pitch) / 120.0f;
        }
        else {
            n2 = ((faceAligment.pitch > 50.0f) ? 50.0f : faceAligment.pitch) / 120.0f;
        }
        final int n3 = 3;
        array[n3] += n;
        final int n4 = 7;
        array[n4] += -n;
        final int n5 = 11;
        array[n5] += n;
        final int n6 = 15;
        array[n6] += -n;
        final int n7 = 3;
        array[n7] -= n2;
        final int n8 = 7;
        array[n8] -= n2;
        return array;
    }
    
    public void updateFaceFeatures(final FaceAligment[] mFaces, final float n) {
        this.mFaces = mFaces;
        this.mDeviceRadian = (float)(-Math.toRadians(n));
    }
    
    public void updateStickers(final List<TuSDKDynamicStickerImage> g) {
        this.g = g;
    }
    
    public void setDisplayRect(final RectF h, final float i) {
        this.h = h;
        this.i = i;
    }
    
    public void seekStickerToFrameTime(final long n) {
        if (this.g == null) {
            return;
        }
        final Iterator<TuSDKDynamicStickerImage> iterator = this.g.iterator();
        while (iterator.hasNext()) {
            iterator.next().seekStickerToFrameTime(n);
        }
    }
    
    public void setBenchmarkTime(final long benchmarkTime) {
        if (this.g == null) {
            return;
        }
        final Iterator<TuSDKDynamicStickerImage> iterator = this.g.iterator();
        while (iterator.hasNext()) {
            iterator.next().setBenchmarkTime(benchmarkTime);
        }
    }
    
    public void setEnableAutoplayMode(final boolean enableAutoplayMode) {
        if (this.g == null) {
            return;
        }
        final Iterator<TuSDKDynamicStickerImage> iterator = this.g.iterator();
        while (iterator.hasNext()) {
            iterator.next().setEnableAutoplayMode(enableAutoplayMode);
        }
    }
    
    private int a() {
        if (this.mFaces == null) {
            return 0;
        }
        return this.mFaces.length;
    }
    
    private FaceAligment a(final int n) {
        if (n < 0 || n >= this.a() || this.mFaces == null || this.mFaces.length < 1) {
            return null;
        }
        return this.mFaces[n];
    }
    
    protected int getStickerCount() {
        if (this.g == null) {
            return 0;
        }
        return this.g.size();
    }
    
    public TuSDKDynamicStickerImage getStickerImageByData(final StickerDynamicData obj) {
        if (this.g == null || this.g.size() < 1) {
            return null;
        }
        for (int i = 0; i < this.g.size(); ++i) {
            if (this.g.get(i).equals(obj)) {
                return this.g.get(i);
            }
        }
        return null;
    }
    
    public int[] getCurrentStickerIndexs() {
        if (this.g == null || this.g.size() < 1) {
            return new int[] { 0 };
        }
        final int[] array = new int[this.g.size()];
        for (int i = 0; i < this.g.size(); ++i) {
            array[i] = this.g.get(i).getCurrentFrameIndex();
        }
        return array;
    }
    
    public void setCurrentStickerIndexs(final int[] array) {
        if (this.g == null || this.g.size() != array.length) {
            return;
        }
        for (int i = 0; i < this.g.size(); ++i) {
            this.g.get(i).setCurrentFrameIndex(array[i]);
        }
    }
    
    private void a(final StickerPositionInfo stickerPositionInfo, final TuSdkSize tuSdkSize, final TuSdkSize tuSdkSize2) {
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
        float rotation = stickerPositionInfo.rotation;
        pointF.x = tuSdkSize.width * stickerPositionInfo.stickerWidth;
        pointF.y = tuSdkSize.height * stickerPositionInfo.stickerHeight;
        switch (stickerPositionInfo.getPosType().ordinal()) {
            case 1: {
                rotation = 0.0f;
                pointF.x = pointF4.x;
                pointF.y = pointF4.y;
                pointF2.x = pointF4.x / 2.0f + pointF3.x;
                pointF2.y = pointF4.y / 2.0f + pointF3.y;
                break;
            }
            case 2:
            case 3: {
                pointF2.x = pointF.x / 2.0f + pointF4.x * stickerPositionInfo.offsetX + pointF3.x;
                pointF2.y = pointF.y / 2.0f + pointF4.y * stickerPositionInfo.offsetY + pointF3.y;
                break;
            }
            case 4: {
                pointF2.x = pointF4.x - pointF.x / 2.0f + pointF4.x * stickerPositionInfo.offsetX + pointF3.x;
                pointF2.y = pointF.y / 2.0f + pointF4.y * stickerPositionInfo.offsetY + pointF3.y;
                break;
            }
            case 5: {
                pointF2.x = pointF.x / 2.0f + pointF4.x * stickerPositionInfo.offsetX + pointF3.x;
                pointF2.y = pointF4.y - pointF.y / 2.0f + pointF4.y * stickerPositionInfo.offsetY + pointF3.y;
                break;
            }
            case 6: {
                pointF2.x = pointF4.x - pointF.x / 2.0f + pointF4.x * stickerPositionInfo.offsetX + pointF3.x;
                pointF2.y = pointF4.y - pointF.y / 2.0f + pointF4.y * stickerPositionInfo.offsetY + pointF3.y;
                break;
            }
            case 7: {
                pointF2.x = pointF4.x / 2.0f + pointF4.x * stickerPositionInfo.offsetX + pointF3.x;
                pointF2.y = pointF4.y / 2.0f + pointF4.y * stickerPositionInfo.offsetY + pointF3.y;
                break;
            }
            case 8: {
                pointF2.x = pointF4.x / 2.0f + pointF4.x * stickerPositionInfo.offsetX + pointF3.x;
                pointF2.y = pointF.y / 2.0f + pointF4.y * stickerPositionInfo.offsetY + pointF3.y;
                break;
            }
            case 9: {
                pointF2.x = pointF.x / 2.0f + pointF4.x * stickerPositionInfo.offsetX + pointF3.x;
                pointF2.y = pointF4.y / 2.0f + pointF4.y * stickerPositionInfo.offsetY + pointF3.y;
                break;
            }
            case 10: {
                pointF2.x = pointF4.x - pointF.x / 2.0f + pointF4.x * stickerPositionInfo.offsetX + pointF3.x;
                pointF2.y = pointF4.y / 2.0f + pointF4.y * stickerPositionInfo.offsetY + pointF3.y;
                break;
            }
            case 11: {
                pointF2.x = pointF4.x / 2.0f + pointF4.x * stickerPositionInfo.offsetX + pointF3.x;
                pointF2.y = pointF4.y - pointF.y / 2.0f + pointF4.y * stickerPositionInfo.offsetY + pointF3.y;
                break;
            }
        }
        this.a(tuSdkSize, pointF, pointF2, rotation);
    }
    
    private boolean a(final StickerPositionInfo stickerPositionInfo, final TuSdkSize tuSdkSize, final int n, final FloatBuffer floatBuffer) {
        final FaceAligment a = this.a(n);
        if (a == null) {
            return false;
        }
        final float n2 = (float)(-(this.mDeviceRadian + Math.toRadians(a.roll)));
        final PointF a2 = this.a(stickerPositionInfo.getPosType(), -1, a.getMarks());
        final PointF a3 = this.a(stickerPositionInfo.getPosType(), 1, a.getMarks());
        final PointF pointF = a2;
        pointF.x *= tuSdkSize.width;
        final PointF pointF2 = a2;
        pointF2.y *= tuSdkSize.height;
        final PointF pointF3 = a3;
        pointF3.x *= tuSdkSize.width;
        final PointF pointF4 = a3;
        pointF4.y *= tuSdkSize.height;
        final float n3 = (float)Math.sqrt(Math.pow(a2.x - a3.x, 2.0) + Math.pow(a2.y - a3.y, 2.0));
        final float n4 = (float)(-n2 * 180.0f / 3.141592653589793);
        final PointF pointF5 = new PointF(n3 * stickerPositionInfo.scale, n3 * stickerPositionInfo.scale / stickerPositionInfo.ratio);
        final PointF a4;
        final PointF pointF6 = a4 = this.a(stickerPositionInfo.getPosType(), 0, a.getMarks());
        a4.x *= tuSdkSize.width;
        final PointF pointF7 = pointF6;
        pointF7.y *= tuSdkSize.height;
        final float n5 = n3 * stickerPositionInfo.offsetX;
        final float n6 = n3 * stickerPositionInfo.offsetY;
        final PointF pointF8 = pointF6;
        pointF8.x += (float)(n5 * Math.cos(-n2) + n6 * Math.sin(n2));
        final PointF pointF9 = pointF6;
        pointF9.y += (float)(n5 * Math.sin(-n2) + n6 * Math.cos(n2));
        this.a(tuSdkSize, pointF5, pointF6, n4);
        return true;
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
    
    private PointF a(final StickerPositionInfo.StickerPositionType stickerPositionType, final int n, final PointF[] array) {
        int[] array2 = null;
        switch (stickerPositionType.ordinal()) {
            case 12: {
                if (n == -1) {
                    array2 = new int[] { 36, 37, 38, 39, 40, 41 };
                    break;
                }
                if (n == 1) {
                    array2 = new int[] { 42, 43, 44, 45, 46, 47 };
                    break;
                }
                array2 = new int[] { 27 };
                break;
            }
            case 13: {
                if (n == -1) {
                    array2 = new int[] { 48, 49, 59 };
                    break;
                }
                if (n == 1) {
                    array2 = new int[] { 53, 54, 55 };
                    break;
                }
                array2 = new int[] { 66 };
                break;
            }
            case 14:
            case 15: {
                if (n == -1) {
                    array2 = new int[] { 2, 29, 30, 31, 32 };
                    break;
                }
                if (n == 1) {
                    array2 = new int[] { 14, 29, 30, 34, 35 };
                    break;
                }
                array2 = new int[] { 30 };
                break;
            }
            case 16: {
                if (n == -1) {
                    array2 = new int[] { 36, 37, 38, 39, 40, 41 };
                    break;
                }
                if (n == 1) {
                    array2 = new int[] { 42, 43, 44, 45, 46, 47 };
                    break;
                }
                array2 = new int[] { 27 };
                break;
            }
        }
        final PointF pointF = new PointF();
        for (int i = 0; i < array2.length; ++i) {
            final PointF pointF2 = pointF;
            pointF2.x += array[array2[i]].x;
            final PointF pointF3 = pointF;
            pointF3.y += array[array2[i]].y;
        }
        pointF.x /= array2.length;
        pointF.y /= array2.length;
        return pointF;
    }
    
    static {
        stickerVertices = new float[] { -0.5f, -0.5f, 0.0f, 1.0f, 0.5f, -0.5f, 0.0f, 1.0f, -0.5f, 0.5f, 0.0f, 1.0f, 0.5f, 0.5f, 0.0f, 1.0f };
    }
}
