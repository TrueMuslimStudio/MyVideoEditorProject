// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.cosmetic;

import com.example.myvideoeditorapp.kore.utils.calc.PointCalc;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import android.graphics.PointF;

public class CosmeticLipModel extends CosmeticModelBase
{
    private static final int[] a;
    public static final int COSMETIC_LIP_TRIANGLES_MAP_LENGTH;
    private static final float[] b;
    public static final int COSMETIC_LIP_MATERIAL_POINTS_LENGTH;
    private static final int[] c;
    private static final int d;
    private float[] e;
    private float[] f;
    private PointF[] g;
    
    public CosmeticLipModel() {
        this.e = new float[CosmeticLipModel.COSMETIC_LIP_MATERIAL_POINTS_LENGTH];
        this.f = new float[CosmeticLipModel.COSMETIC_LIP_MATERIAL_POINTS_LENGTH * 3 / 2];
        this.g = new PointF[52];
    }
    
    public void setPosition(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.e);
    }
    
    public void setTextureCoordinate(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.f);
    }
    
    public void setTextureCoordinate2(final FloatBuffer floatBuffer) {
        floatBuffer.put(CosmeticLipModel.b);
    }
    
    public void setElementIndices(final IntBuffer intBuffer) {
        intBuffer.put(CosmeticLipModel.a);
    }
    
    public void updateFace(final FaceAligment faceAligment, final TuSdkSize tuSdkSize) {
        if (faceAligment == null) {
            return;
        }
        this.a(faceAligment, tuSdkSize);
        for (int i = 0; i < CosmeticLipModel.DEFAULT_VERTEX.length / 2; ++i) {
            this.e[2 * i] = CosmeticLipModel.DEFAULT_VERTEX[2 * i];
            this.e[2 * i + 1] = CosmeticLipModel.DEFAULT_VERTEX[2 * i + 1];
            this.f[3 * i] = CosmeticLipModel.DEFAULT_TEXTURE[3 * i];
            this.f[3 * i + 1] = CosmeticLipModel.DEFAULT_TEXTURE[3 * i + 1];
            this.f[3 * i + 2] = CosmeticLipModel.DEFAULT_TEXTURE[3 * i + 2];
        }
        for (int j = 0; j < this.g.length; ++j) {
            final PointF normalize = PointCalc.normalize(this.g[j], tuSdkSize);
            this.e[CosmeticLipModel.DEFAULT_VERTEX.length + 2 * j] = normalize.x * 2.0f - 1.0f;
            this.e[CosmeticLipModel.DEFAULT_VERTEX.length + 2 * j + 1] = normalize.y * 2.0f - 1.0f;
            this.f[CosmeticLipModel.DEFAULT_TEXTURE.length + 3 * j] = normalize.x;
            this.f[CosmeticLipModel.DEFAULT_TEXTURE.length + 3 * j + 1] = normalize.y;
            this.f[CosmeticLipModel.DEFAULT_TEXTURE.length + 3 * j + 2] = 1.0f;
        }
    }
    
    private void a(final FaceAligment faceAligment, final TuSdkSize tuSdkSize) {
        for (int i = 0; i < faceAligment.getMouthMarks().length; ++i) {
            this.g[i] = PointCalc.real(faceAligment.getMouthMarks()[i], tuSdkSize);
        }
        final PointF center = PointCalc.center(this.g[28], this.g[36]);
        final float distance = PointCalc.distance(center, this.g[0]);
        final float distance2 = PointCalc.distance(center, this.g[12]);
        final float distance3 = PointCalc.distance(center, this.g[6]);
        final float distance4 = PointCalc.distance(center, this.g[18]);
        final PointF extension = PointCalc.extension(center, this.g[0], distance * 0.3f);
        final PointF extension2 = PointCalc.extension(center, this.g[12], distance2 * 0.3f);
        final PointF extension3 = PointCalc.extension(center, this.g[6], distance3 * 0.5f);
        final PointF extension4 = PointCalc.extension(center, this.g[18], distance4 * 0.5f);
        final PointF boundCornerPoint = PointCalc.boundCornerPoint(center, extension, extension3);
        final PointF boundCornerPoint2 = PointCalc.boundCornerPoint(center, extension2, extension3);
        final PointF boundCornerPoint3 = PointCalc.boundCornerPoint(center, extension2, extension4);
        final PointF boundCornerPoint4 = PointCalc.boundCornerPoint(center, extension, extension4);
        final PointF center2 = PointCalc.center(boundCornerPoint, extension3);
        final PointF center3 = PointCalc.center(extension3, boundCornerPoint2);
        final PointF center4 = PointCalc.center(boundCornerPoint3, extension4);
        final PointF center5 = PointCalc.center(extension4, boundCornerPoint4);
        this.g[40] = boundCornerPoint;
        this.g[41] = center2;
        this.g[42] = extension3;
        this.g[43] = center3;
        this.g[44] = boundCornerPoint2;
        this.g[45] = extension2;
        this.g[46] = boundCornerPoint3;
        this.g[47] = center4;
        this.g[48] = extension4;
        this.g[49] = center5;
        this.g[50] = boundCornerPoint4;
        this.g[51] = extension;
        this.a(this.g);
    }
    
    private void a(final PointF[] array) {
        final float distance = PointCalc.distance(array[6], array[18]);
        this.a(array, 25, 39, distance);
        this.a(array, 26, 38, distance);
        this.a(array, 27, 37, distance);
        this.a(array, 28, 36, distance);
        this.a(array, 29, 35, distance);
        this.a(array, 30, 34, distance);
        this.a(array, 31, 33, distance);
    }
    
    private void a(final PointF[] array, final int n, final int n2, final float n3) {
        final float distance = PointCalc.distance(array[n], array[n2]);
        if (distance / n3 < 0.08 && distance / n3 > 0.0f) {
            array[n2] = (array[n] = PointCalc.center(array[n], array[n2]));
        }
    }
    
    static {
        a = new int[] { 0, 1, 2, 0, 3, 2, 4, 44, 55, 4, 5, 44, 5, 6, 44, 6, 44, 45, 6, 7, 45, 7, 8, 45, 8, 45, 46, 8, 9, 46, 9, 10, 46, 10, 11, 46, 11, 12, 46, 12, 46, 47, 12, 13, 47, 13, 14, 47, 14, 47, 48, 14, 15, 48, 15, 16, 48, 16, 48, 49, 16, 49, 50, 16, 17, 50, 17, 18, 50, 18, 50, 51, 18, 19, 51, 19, 20, 51, 20, 51, 52, 20, 21, 52, 21, 22, 52, 22, 23, 52, 23, 24, 52, 24, 52, 53, 24, 25, 53, 25, 26, 53, 26, 53, 54, 26, 27, 54, 4, 27, 54, 4, 54, 55, 4, 5, 28, 5, 6, 28, 6, 28, 29, 6, 7, 29, 7, 8, 29, 8, 29, 30, 8, 30, 31, 8, 9, 31, 9, 10, 31, 10, 31, 32, 10, 32, 33, 10, 11, 33, 11, 12, 33, 12, 33, 34, 12, 34, 35, 12, 13, 35, 13, 14, 35, 14, 35, 36, 14, 15, 36, 15, 16, 36, 16, 17, 36, 17, 18, 36, 18, 36, 37, 18, 19, 37, 19, 20, 37, 20, 37, 38, 20, 38, 39, 20, 21, 39, 21, 22, 39, 22, 39, 40, 22, 40, 41, 22, 23, 41, 23, 24, 41, 24, 41, 42, 24, 42, 43, 24, 25, 43, 25, 26, 43, 26, 28, 43, 26, 27, 28, 4, 27, 28, 28, 29, 43, 29, 30, 43, 30, 42, 43, 30, 41, 42, 30, 31, 41, 31, 32, 41, 32, 40, 41, 32, 39, 40, 32, 33, 39, 33, 34, 39, 34, 38, 39, 34, 37, 38, 34, 35, 37, 35, 36, 37 };
        COSMETIC_LIP_TRIANGLES_MAP_LENGTH = CosmeticLipModel.a.length;
        b = new float[] { 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.235f, 0.5f, 0.275997f, 0.425401f, 0.32f, 0.36f, 0.358509f, 0.302952f, 0.405f, 0.27f, 0.452238f, 0.285028f, 0.5f, 0.29f, 0.547762f, 0.285028f, 0.595f, 0.27f, 0.641491f, 0.302952f, 0.68f, 0.36f, 0.724003f, 0.425401f, 0.765f, 0.5f, 0.723041f, 0.552062f, 0.68f, 0.6f, 0.638913f, 0.646346f, 0.595f, 0.68f, 0.548088f, 0.702332f, 0.5f, 0.71f, 0.451912f, 0.702332f, 0.405f, 0.68f, 0.361087f, 0.646346f, 0.32f, 0.6f, 0.276959f, 0.552062f, 0.285f, 0.5f, 0.344178f, 0.458815f, 0.405f, 0.43f, 0.449789f, 0.416906f, 0.495f, 0.41f, 0.550283f, 0.413758f, 0.605f, 0.43f, 0.66098f, 0.459252f, 0.715f, 0.5f, 0.661294f, 0.548096f, 0.605f, 0.58f, 0.550067f, 0.588033f, 0.495f, 0.59f, 0.449946f, 0.587027f, 0.405f, 0.58f, 0.342747f, 0.548457f, 0.08f, 0.06f, 0.29f, 0.06f, 0.5f, 0.06f, 0.71f, 0.06f, 0.92f, 0.06f, 0.92f, 0.5f, 0.92f, 0.94f, 0.71f, 0.94f, 0.5f, 0.94f, 0.29f, 0.94f, 0.08f, 0.94f, 0.08f, 0.5f };
        COSMETIC_LIP_MATERIAL_POINTS_LENGTH = CosmeticLipModel.b.length;
        c = new int[] { 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103 };
        d = CosmeticLipModel.c.length;
    }
}
