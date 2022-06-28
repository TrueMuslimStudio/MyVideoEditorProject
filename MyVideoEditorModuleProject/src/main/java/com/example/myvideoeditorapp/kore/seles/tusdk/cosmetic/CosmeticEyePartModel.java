// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.cosmetic;

import android.graphics.Matrix;
import com.example.myvideoeditorapp.kore.utils.calc.PointCalc;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import android.graphics.PointF;

public class CosmeticEyePartModel extends CosmeticModelBase
{
    private static final int[] a;
    public static final int COSMETIC_EYEPART_TRIANGLES_MAP_LENGTH;
    private static final float[] b;
    public static final int COSMETIC_EYEPART_MATERIAL_POINTS_LENGTH;
    private static final int[] c;
    private static final int[] d;
    private float[] e;
    private float[] f;
    private PointF[] g;
    private PointF[] h;
    private PointF[] i;
    
    public CosmeticEyePartModel() {
        this.e = new float[CosmeticEyePartModel.COSMETIC_EYEPART_MATERIAL_POINTS_LENGTH];
        this.f = new float[CosmeticEyePartModel.COSMETIC_EYEPART_MATERIAL_POINTS_LENGTH * 3 / 2];
        this.g = new PointF[33];
        this.h = new PointF[33];
        this.i = new PointF[66];
    }
    
    public void setPosition(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.e);
    }
    
    public void setTextureCoordinate(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.f);
    }
    
    public void setTextureCoordinate2(final FloatBuffer floatBuffer) {
        floatBuffer.put(CosmeticEyePartModel.b);
    }
    
    public void setElementIndices(final IntBuffer intBuffer) {
        intBuffer.put(CosmeticEyePartModel.a);
    }
    
    public void updateFace(final FaceAligment faceAligment, final TuSdkSize tuSdkSize) {
        if (faceAligment == null) {
            return;
        }
        this.a(faceAligment, tuSdkSize);
        for (int i = 0; i < CosmeticEyePartModel.DEFAULT_VERTEX.length / 2; ++i) {
            this.e[2 * i] = CosmeticEyePartModel.DEFAULT_VERTEX[2 * i];
            this.e[2 * i + 1] = CosmeticEyePartModel.DEFAULT_VERTEX[2 * i + 1];
            this.f[3 * i] = CosmeticEyePartModel.DEFAULT_TEXTURE[3 * i];
            this.f[3 * i + 1] = CosmeticEyePartModel.DEFAULT_TEXTURE[3 * i + 1];
            this.f[3 * i + 2] = CosmeticEyePartModel.DEFAULT_TEXTURE[3 * i + 2];
        }
        for (int j = 0; j < this.i.length; ++j) {
            final PointF normalize = PointCalc.normalize(this.i[j], tuSdkSize);
            this.e[CosmeticEyePartModel.DEFAULT_VERTEX.length + 2 * j] = normalize.x * 2.0f - 1.0f;
            this.e[CosmeticEyePartModel.DEFAULT_VERTEX.length + 2 * j + 1] = normalize.y * 2.0f - 1.0f;
            this.f[CosmeticEyePartModel.DEFAULT_TEXTURE.length + 3 * j] = normalize.x;
            this.f[CosmeticEyePartModel.DEFAULT_TEXTURE.length + 3 * j + 1] = normalize.y;
            this.f[CosmeticEyePartModel.DEFAULT_TEXTURE.length + 3 * j + 2] = 1.0f;
        }
    }
    
    PointF[] a(float n, float n2, float n3, final float n4) {
        n *= n4;
        n2 *= n4;
        n3 *= n4;
        final float n5 = (float)(n3 * Math.sqrt(1.0f - n * n / (n2 * n2)));
        return new PointF[] { new PointF(n, -n5), new PointF(n, n5) };
    }
    
    private void a(final FaceAligment faceAligment, final TuSdkSize tuSdkSize) {
        final float[] array = new float[CosmeticEyePartModel.c.length * 2];
        for (int i = 0; i < CosmeticEyePartModel.c.length; ++i) {
            this.g[i] = PointCalc.real(faceAligment.getEyeMarks()[CosmeticEyePartModel.c[i]], tuSdkSize);
            array[2 * i] = this.g[i].x;
            array[2 * i + 1] = this.g[i].y;
        }
        final float n = (float)Math.atan((this.g[8].y - this.g[0].y) / (this.g[8].x - this.g[0].x));
        final PointF center = PointCalc.center(this.g[0], this.g[8]);
        final float distance = PointCalc.distance(this.g[0], this.g[8]);
        final float distance2 = PointCalc.distance(this.g[4], this.g[12]);
        final float[] array2 = new float[CosmeticEyePartModel.c.length * 2];
        final Matrix matrix = new Matrix();
        final float n2 = (distance - distance2 * 0.7f) * 1.0f;
        final float n3 = distance * 1.25f;
        final float n4 = (float)Math.sqrt(n3 * n3 - n2 * n2);
        final float n5 = -n3;
        final PointF[] a = this.a(n5 + 2.0f * n3 / 12.0f, n3, n4, 1.0f);
        final PointF[] a2 = this.a(n5 + 2.0f * n3 * 2.0f / 9.0f, n3, n4, 0.95f);
        final PointF[] a3 = this.a(n5 + 2.0f * n3 * 3.0f / 8.0f, n3, n4, 0.9f);
        final PointF[] a4 = this.a(n5 + 2.0f * n3 * 4.0f / 8.0f, n3, n4, 0.85f);
        final PointF[] a5 = this.a(n5 + 2.0f * n3 * 5.0f / 8.0f, n3, n4, 0.8f);
        final PointF[] a6 = this.a(n5 + 2.0f * n3 * 6.0f / 8.0f, n3, n4, 0.75f);
        final PointF[] a7 = this.a(n5 + 2.0f * n3 * 11.0f / 12.0f, n3, n4, 0.7f);
        array[0] = n5;
        array[1] = 0.0f;
        array[2] = a[0].x;
        array[3] = a[0].y;
        array[4] = a2[0].x;
        array[5] = a2[0].y;
        array[6] = a3[0].x;
        array[7] = a3[0].y;
        array[8] = a4[0].x;
        array[9] = a4[0].y;
        array[10] = a5[0].x;
        array[11] = a5[0].y;
        array[12] = a6[0].x;
        array[13] = a6[0].y;
        array[14] = a7[0].x;
        array[15] = a7[0].y;
        array[16] = n3 * 0.7f;
        array[17] = 0.0f;
        array[18] = a7[1].x;
        array[19] = a7[1].y;
        array[20] = a6[1].x;
        array[21] = a6[1].y;
        array[22] = a5[1].x;
        array[23] = a5[1].y;
        array[24] = a4[1].x;
        array[25] = a4[1].y;
        array[26] = a3[1].x;
        array[27] = a3[1].y;
        array[28] = a2[1].x;
        array[29] = a2[1].y;
        array[30] = a[1].x;
        array[31] = a[1].y;
        matrix.reset();
        matrix.postRotate((float)Math.toDegrees(n));
        matrix.postTranslate(center.x, center.y);
        matrix.mapPoints(array2, array);
        for (int j = 0; j < 16; ++j) {
            this.g[CosmeticEyePartModel.c.length + j] = new PointF(array2[2 * j], array2[2 * j + 1]);
        }
        for (int k = 0; k < this.g.length; ++k) {
            this.i[k] = this.g[k];
        }
        final float[] array3 = new float[CosmeticEyePartModel.c.length * 2];
        for (int l = 0; l < CosmeticEyePartModel.d.length; ++l) {
            this.h[l] = PointCalc.real(faceAligment.getEyeMarks()[CosmeticEyePartModel.d[l]], tuSdkSize);
            array3[2 * l] = this.h[l].x;
            array3[2 * l + 1] = this.h[l].y;
        }
        final float n6 = (float)Math.atan((this.h[8].y - this.h[0].y) / (this.h[8].x - this.h[0].x));
        final PointF center2 = PointCalc.center(this.h[0], this.h[8]);
        final float distance3 = PointCalc.distance(this.h[0], this.h[8]);
        final float distance4 = PointCalc.distance(this.h[4], this.h[12]);
        final float[] array4 = new float[CosmeticEyePartModel.c.length * 2];
        final Matrix matrix2 = new Matrix();
        final float n7 = (distance3 - distance4 * 0.7f) * 1.0f;
        final float n8 = distance3 * 1.25f;
        final float n9 = (float)Math.sqrt(n8 * n8 - n7 * n7);
        final float n10 = n8;
        final PointF[] a8 = this.a(n10 - 2.0f * n8 / 12.0f, n8, n9, 1.0f);
        final PointF[] a9 = this.a(n10 - 2.0f * n8 * 2.0f / 9.0f, n8, n9, 0.95f);
        final PointF[] a10 = this.a(n10 - 2.0f * n8 * 3.0f / 8.0f, n8, n9, 0.9f);
        final PointF[] a11 = this.a(n10 - 2.0f * n8 * 4.0f / 8.0f, n8, n9, 0.85f);
        final PointF[] a12 = this.a(n10 - 2.0f * n8 * 5.0f / 8.0f, n8, n9, 0.8f);
        final PointF[] a13 = this.a(n10 - 2.0f * n8 * 6.0f / 8.0f, n8, n9, 0.75f);
        final PointF[] a14 = this.a(n10 - 2.0f * n8 * 11.0f / 12.0f, n8, n9, 0.7f);
        array3[0] = n10;
        array3[1] = array4[1];
        array3[2] = a8[0].x;
        array3[3] = a8[0].y;
        array3[4] = a9[0].x;
        array3[5] = a9[0].y;
        array3[6] = a10[0].x;
        array3[7] = a10[0].y;
        array3[8] = a11[0].x;
        array3[9] = a11[0].y;
        array3[10] = a12[0].x;
        array3[11] = a12[0].y;
        array3[12] = a13[0].x;
        array3[13] = a13[0].y;
        array3[14] = a14[0].x;
        array3[15] = a14[0].y;
        array3[16] = -n8 * 0.7f;
        array3[17] = array4[1];
        array3[18] = a14[1].x;
        array3[19] = a14[1].y;
        array3[20] = a13[1].x;
        array3[21] = a13[1].y;
        array3[22] = a12[1].x;
        array3[23] = a12[1].y;
        array3[24] = a11[1].x;
        array3[25] = a11[1].y;
        array3[26] = a10[1].x;
        array3[27] = a10[1].y;
        array3[28] = a9[1].x;
        array3[29] = a9[1].y;
        array3[30] = a8[1].x;
        array3[31] = a8[1].y;
        matrix2.reset();
        matrix2.postRotate((float)Math.toDegrees(n6));
        matrix2.postTranslate(center2.x, center2.y);
        matrix2.mapPoints(array4, array3);
        for (int n11 = 0; n11 < 16; ++n11) {
            this.h[CosmeticEyePartModel.c.length + n11] = new PointF(array4[2 * n11], array4[2 * n11 + 1]);
        }
        for (int n12 = 0; n12 < this.h.length; ++n12) {
            this.i[n12 + 33] = this.h[n12];
        }
    }
    
    static {
        a = new int[] { 0, 1, 2, 0, 3, 2, 4, 5, 20, 5, 6, 20, 6, 7, 20, 7, 8, 20, 8, 9, 20, 9, 10, 20, 10, 11, 20, 11, 12, 20, 12, 13, 20, 13, 14, 20, 14, 15, 20, 15, 16, 20, 16, 17, 20, 17, 18, 20, 18, 19, 20, 4, 19, 20, 4, 21, 22, 4, 5, 22, 5, 22, 23, 5, 6, 23, 6, 23, 24, 6, 7, 24, 7, 24, 25, 7, 8, 25, 8, 9, 25, 9, 25, 26, 9, 10, 26, 10, 26, 27, 10, 11, 27, 11, 27, 28, 11, 12, 28, 12, 28, 29, 12, 29, 30, 12, 13, 30, 13, 30, 31, 13, 14, 31, 14, 31, 32, 14, 15, 32, 15, 32, 33, 15, 16, 33, 16, 17, 33, 17, 33, 34, 17, 18, 34, 18, 34, 35, 18, 19, 35, 19, 35, 36, 4, 19, 36, 4, 21, 36, 37, 38, 53, 38, 39, 53, 39, 40, 53, 40, 41, 53, 41, 42, 53, 42, 43, 53, 43, 44, 53, 44, 45, 53, 45, 46, 53, 46, 47, 53, 47, 48, 53, 48, 49, 53, 49, 50, 53, 50, 51, 53, 51, 52, 53, 37, 52, 53, 37, 54, 55, 37, 38, 55, 38, 55, 56, 38, 39, 56, 39, 56, 57, 39, 40, 57, 40, 57, 58, 40, 41, 58, 41, 42, 58, 42, 58, 59, 42, 43, 59, 43, 59, 60, 43, 44, 60, 44, 60, 61, 44, 45, 61, 45, 61, 62, 45, 62, 63, 45, 46, 63, 46, 63, 64, 46, 47, 64, 47, 64, 65, 47, 48, 65, 48, 65, 66, 48, 49, 66, 49, 50, 66, 50, 66, 67, 50, 51, 67, 51, 67, 68, 51, 52, 68, 52, 68, 69, 37, 52, 69, 37, 54, 69 };
        COSMETIC_EYEPART_TRIANGLES_MAP_LENGTH = CosmeticEyePartModel.a.length;
        b = new float[] { 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.375f, 0.5625f, 0.425f, 0.50625f, 0.49f, 0.4625f, 0.565f, 0.4375f, 0.64f, 0.434375f, 0.72f, 0.459375f, 0.795f, 0.50625f, 0.8475f, 0.5625f, 0.895f, 0.63125f, 0.8325f, 0.659375f, 0.7625f, 0.68125f, 0.695f, 0.690625f, 0.64f, 0.696875f, 0.5725f, 0.69375f, 0.4925f, 0.66875f, 0.4225f, 0.621875f, 0.6375f, 0.5625f, -0.0125f, 0.509375f, 0.1225f, 0.15625f, 0.335f, 0.025f, 0.5375f, 0.0f, 0.6825f, 0.03125f, 0.8075f, 0.096875f, 0.915f, 0.196875f, 1.035f, 0.3875f, 1.085f, 0.65625f, 0.99f, 0.903125f, 0.84f, 1.059375f, 0.72f, 1.128125f, 0.585f, 1.159375f, 0.4375f, 1.15625f, 0.2475f, 1.075f, 0.0625f, 0.890625f, 0.375f, 0.5625f, 0.425f, 0.50625f, 0.49f, 0.4625f, 0.565f, 0.4375f, 0.64f, 0.434375f, 0.72f, 0.459375f, 0.795f, 0.50625f, 0.8475f, 0.5625f, 0.895f, 0.63125f, 0.8325f, 0.659375f, 0.7625f, 0.68125f, 0.695f, 0.690625f, 0.64f, 0.696875f, 0.5725f, 0.69375f, 0.4925f, 0.66875f, 0.4225f, 0.621875f, 0.6375f, 0.5625f, -0.0125f, 0.509375f, 0.1225f, 0.15625f, 0.335f, 0.025f, 0.5375f, 0.0f, 0.6825f, 0.03125f, 0.8075f, 0.096875f, 0.915f, 0.196875f, 1.035f, 0.3875f, 1.085f, 0.65625f, 0.99f, 0.903125f, 0.84f, 1.059375f, 0.72f, 1.128125f, 0.585f, 1.159375f, 0.4375f, 1.15625f, 0.2475f, 1.075f, 0.0625f, 0.890625f };
        COSMETIC_EYEPART_MATERIAL_POINTS_LENGTH = CosmeticEyePartModel.b.length;
        c = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 };
        d = new int[] { 25, 24, 23, 22, 21, 20, 19, 18, 17, 32, 31, 30, 29, 28, 27, 26, 33 };
    }
}
