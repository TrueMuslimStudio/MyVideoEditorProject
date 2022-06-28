// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.cosmetic;

import java.util.List;
import com.example.myvideoeditorapp.kore.utils.calc.PointCalc;
import java.util.ArrayList;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import android.graphics.PointF;

public class CosmeticEyebrowModel extends CosmeticModelBase
{
    private static final int[] a;
    public static final int COSMETIC_EYEBROW_TRIANGLES_MAP_LENGTH;
    private static final float[] b;
    public static final int COSMETIC_EYEBROW_MATERIAL_POINTS_LENGTH;
    private static final int[] c;
    private static final int d;
    private float[] e;
    private float[] f;
    private PointF[] g;
    private PointF[] h;
    
    public CosmeticEyebrowModel() {
        this.e = new float[CosmeticEyebrowModel.COSMETIC_EYEBROW_MATERIAL_POINTS_LENGTH];
        this.f = new float[CosmeticEyebrowModel.COSMETIC_EYEBROW_MATERIAL_POINTS_LENGTH * 3 / 2];
        this.g = new PointF[CosmeticEyebrowModel.d];
        this.h = new PointF[8];
    }
    
    public void setPosition(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.e);
    }
    
    public void setTextureCoordinate(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.f);
    }
    
    public void setTextureCoordinate2(final FloatBuffer floatBuffer) {
        floatBuffer.put(CosmeticEyebrowModel.b);
    }
    
    public void setElementIndices(final IntBuffer intBuffer) {
        intBuffer.put(CosmeticEyebrowModel.a);
    }
    
    public void updateFace(final FaceAligment faceAligment, final TuSdkSize tuSdkSize) {
        if (faceAligment == null) {
            return;
        }
        for (int i = 0; i < CosmeticEyebrowModel.d; ++i) {
            this.g[i] = faceAligment.getOrginMarks()[CosmeticEyebrowModel.c[i]];
        }
        this.a();
        for (int j = 0; j < CosmeticEyebrowModel.DEFAULT_VERTEX.length / 2; ++j) {
            this.e[2 * j] = CosmeticEyebrowModel.DEFAULT_VERTEX[2 * j];
            this.e[2 * j + 1] = CosmeticEyebrowModel.DEFAULT_VERTEX[2 * j + 1];
            this.f[3 * j] = CosmeticEyebrowModel.DEFAULT_TEXTURE[3 * j];
            this.f[3 * j + 1] = CosmeticEyebrowModel.DEFAULT_TEXTURE[3 * j + 1];
            this.f[3 * j + 2] = CosmeticEyebrowModel.DEFAULT_TEXTURE[3 * j + 2];
        }
        for (int k = 0; k < this.h.length; ++k) {
            final PointF pointF = this.h[k];
            this.e[CosmeticEyebrowModel.DEFAULT_VERTEX.length + 2 * k] = pointF.x * 2.0f - 1.0f;
            this.e[CosmeticEyebrowModel.DEFAULT_VERTEX.length + 2 * k + 1] = pointF.y * 2.0f - 1.0f;
            this.f[CosmeticEyebrowModel.DEFAULT_TEXTURE.length + 3 * k] = pointF.x;
            this.f[CosmeticEyebrowModel.DEFAULT_TEXTURE.length + 3 * k + 1] = pointF.y;
            this.f[CosmeticEyebrowModel.DEFAULT_TEXTURE.length + 3 * k + 2] = 1.0f;
        }
    }
    
    void a() {
        final ArrayList list = new ArrayList();
        final PointF crossPoint = PointCalc.crossPoint(this.g[0], this.g[1], this.g[2], this.g[3]);
        final float distance = PointCalc.distance(this.g[0], this.g[1]);
        final PointF extension = PointCalc.extension(crossPoint, this.g[0], distance * 0.1f);
        final PointF extension2 = PointCalc.extension(crossPoint, this.g[1], distance * 0.1f);
        final List<PointF> footPoint = PointCalc.footPoint(extension, crossPoint, distance * 0.15f);
        final List<PointF> footPoint2 = PointCalc.footPoint(extension2, crossPoint, distance * 0.15f);
        this.h[0] = footPoint.get(0);
        this.h[1] = footPoint2.get(0);
        this.h[2] = footPoint2.get(1);
        this.h[3] = footPoint.get(1);
        final PointF crossPoint2 = PointCalc.crossPoint(this.g[4], this.g[5], this.g[6], this.g[7]);
        final float distance2 = PointCalc.distance(this.g[4], this.g[5]);
        final PointF extension3 = PointCalc.extension(crossPoint2, this.g[4], distance2 * 0.1f);
        final PointF extension4 = PointCalc.extension(crossPoint2, this.g[5], distance2 * 0.1f);
        final List<PointF> footPoint3 = PointCalc.footPoint(extension3, crossPoint2, distance2 * 0.15f);
        final List<PointF> footPoint4 = PointCalc.footPoint(extension4, crossPoint2, distance2 * 0.15f);
        this.h[4] = footPoint3.get(0);
        this.h[5] = footPoint4.get(0);
        this.h[6] = footPoint4.get(1);
        this.h[7] = footPoint3.get(1);
    }
    
    static {
        a = new int[] { 0, 1, 2, 0, 3, 2, 4, 5, 6, 4, 7, 6, 8, 9, 10, 8, 10, 11 };
        COSMETIC_EYEBROW_TRIANGLES_MAP_LENGTH = CosmeticEyebrowModel.a.length;
        b = new float[] { 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.1f, 1.0f, 0.1f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.1f, 1.0f, 0.1f, 1.0f, 1.0f, 0.0f, 1.0f };
        COSMETIC_EYEBROW_MATERIAL_POINTS_LENGTH = CosmeticEyebrowModel.b.length;
        c = new int[] { 33, 37, 35, 65, 42, 38, 40, 70 };
        d = CosmeticEyebrowModel.c.length;
    }
}
