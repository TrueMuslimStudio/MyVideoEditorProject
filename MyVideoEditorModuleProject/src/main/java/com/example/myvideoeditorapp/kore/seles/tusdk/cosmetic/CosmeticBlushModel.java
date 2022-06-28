// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.cosmetic;

import com.example.myvideoeditorapp.kore.utils.calc.PointCalc;
import android.graphics.PointF;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;

public class CosmeticBlushModel extends CosmeticModelBase
{
    private static final int[] a;
    public static final int COSMETIC_BLUSH_TRIANGLES_MAP_LENGTH;
    private static final float[] b;
    public static final int COSMETIC_BLUSH_MATERIAL_POINTS_LENGTH;
    private static final int[] c;
    private static final int d;
    private float[] e;
    private float[] f;
    
    public CosmeticBlushModel() {
        this.e = new float[CosmeticBlushModel.COSMETIC_BLUSH_MATERIAL_POINTS_LENGTH];
        this.f = new float[CosmeticBlushModel.COSMETIC_BLUSH_MATERIAL_POINTS_LENGTH * 3 / 2];
    }
    
    public void setPosition(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.e);
    }
    
    public void setTextureCoordinate(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.f);
    }
    
    public void setTextureCoordinate2(final FloatBuffer floatBuffer) {
        floatBuffer.put(CosmeticBlushModel.b);
    }
    
    public void setElementIndices(final IntBuffer intBuffer) {
        intBuffer.put(CosmeticBlushModel.a);
    }
    
    public void updateFace(final FaceAligment faceAligment, final TuSdkSize tuSdkSize) {
        if (faceAligment == null) {
            return;
        }
        for (int i = 0; i < CosmeticBlushModel.DEFAULT_VERTEX.length / 2; ++i) {
            this.e[2 * i] = CosmeticBlushModel.DEFAULT_VERTEX[2 * i];
            this.e[2 * i + 1] = CosmeticBlushModel.DEFAULT_VERTEX[2 * i + 1];
            this.f[3 * i] = CosmeticBlushModel.DEFAULT_TEXTURE[3 * i];
            this.f[3 * i + 1] = CosmeticBlushModel.DEFAULT_TEXTURE[3 * i + 1];
            this.f[3 * i + 2] = CosmeticBlushModel.DEFAULT_TEXTURE[3 * i + 2];
        }
        for (int j = 0; j < CosmeticBlushModel.d; ++j) {
            final PointF pointF = faceAligment.getOrginMarks()[CosmeticBlushModel.c[j]];
            this.e[CosmeticBlushModel.DEFAULT_VERTEX.length + 2 * j] = pointF.x * 2.0f - 1.0f;
            this.e[CosmeticBlushModel.DEFAULT_VERTEX.length + 2 * j + 1] = pointF.y * 2.0f - 1.0f;
            this.f[CosmeticBlushModel.DEFAULT_TEXTURE.length + 3 * j] = pointF.x;
            this.f[CosmeticBlushModel.DEFAULT_TEXTURE.length + 3 * j + 1] = pointF.y;
            this.f[CosmeticBlushModel.DEFAULT_TEXTURE.length + 3 * j + 2] = 1.0f;
        }
        final PointF[] orginMarks = faceAligment.getOrginMarks();
        final PointF[] array = { PointCalc.crossPoint(orginMarks[5], orginMarks[82], orginMarks[9], orginMarks[73]), PointCalc.crossPoint(orginMarks[27], orginMarks[83], orginMarks[23], orginMarks[76]) };
        final int n = CosmeticBlushModel.DEFAULT_VERTEX.length + CosmeticBlushModel.d * 2;
        final int n2 = CosmeticBlushModel.DEFAULT_TEXTURE.length + CosmeticBlushModel.d * 3;
        for (int k = 0; k < array.length; ++k) {
            final PointF pointF2 = array[k];
            this.e[n + 2 * k] = pointF2.x * 2.0f - 1.0f;
            this.e[n + 2 * k + 1] = pointF2.y * 2.0f - 1.0f;
            this.f[n2 + 3 * k] = pointF2.x;
            this.f[n2 + 3 * k + 1] = pointF2.y;
            this.f[n2 + 3 * k + 2] = 1.0f;
        }
    }
    
    static {
        a = new int[] { 0, 1, 2, 0, 3, 2, 4, 5, 40, 5, 6, 40, 6, 7, 40, 7, 8, 40, 8, 9, 40, 9, 10, 40, 10, 11, 40, 11, 12, 40, 12, 13, 40, 13, 14, 40, 14, 35, 40, 35, 36, 40, 36, 37, 40, 37, 38, 40, 38, 39, 40, 4, 39, 40, 14, 15, 16, 14, 34, 35, 14, 15, 34, 15, 16, 17, 15, 33, 34, 15, 17, 33, 17, 32, 33, 17, 32, 41, 31, 32, 41, 30, 31, 41, 29, 30, 41, 28, 29, 41, 27, 28, 41, 26, 27, 41, 25, 26, 41, 24, 25, 41, 23, 24, 41, 22, 23, 41, 21, 22, 41, 20, 21, 41, 19, 20, 41, 18, 19, 41, 17, 18, 41 };
        COSMETIC_BLUSH_TRIANGLES_MAP_LENGTH = CosmeticBlushModel.a.length;
        b = new float[] { 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0425f, 0.1875f, 0.045f, 0.283333f, 0.05f, 0.379167f, 0.0575f, 0.475f, 0.07f, 0.570833f, 0.0825f, 0.666667f, 0.1f, 0.758333f, 0.1225f, 0.854167f, 0.1525f, 0.9375f, 0.3275f, 0.8625f, 0.38f, 0.616667f, 0.5025f, 0.5625f, 0.5f, 0.6875f, 0.6175f, 0.6125f, 0.66f, 0.8625f, 0.8325f, 0.929167f, 0.8625f, 0.845833f, 0.885f, 0.754167f, 0.9f, 0.6625f, 0.915f, 0.570833f, 0.9275f, 0.475f, 0.935f, 0.379167f, 0.94f, 0.283333f, 0.9425f, 0.183333f, 0.7975f, 0.170833f, 0.76f, 0.208333f, 0.715f, 0.225f, 0.6625f, 0.220833f, 0.6175f, 0.2125f, 0.565f, 0.2125f, 0.43f, 0.2125f, 0.3725f, 0.2125f, 0.3275f, 0.220833f, 0.275f, 0.229167f, 0.2275f, 0.208333f, 0.19f, 0.175f, 0.215f, 0.570833f, 0.775f, 0.5875f };
        COSMETIC_BLUSH_MATERIAL_POINTS_LENGTH = CosmeticBlushModel.b.length;
        c = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 84, 82, 46, 49, 83, 90, 23, 24, 25, 26, 27, 28, 29, 30, 31, 61, 62, 76, 63, 58, 79, 78, 55, 56, 73, 57, 52 };
        d = CosmeticBlushModel.c.length;
    }
}
