// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.cosmetic;

import java.util.List;
import com.example.myvideoeditorapp.kore.utils.calc.PointCalc;
import android.graphics.PointF;
import java.util.ArrayList;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;

public class CosmeticFacialModel extends CosmeticModelBase
{
    private static final int[] a;
    public static final int COSMETIC_FACIAL_TRIANGLES_MAP_LENGTH;
    private static final float[] b;
    public static final int COSMETIC_FACIAL_MATERIAL_POINTS_LENGTH;
    private static final int[] c;
    private static final int d;
    private float[] e;
    private float[] f;
    
    public CosmeticFacialModel() {
        this.e = new float[CosmeticFacialModel.COSMETIC_FACIAL_MATERIAL_POINTS_LENGTH];
        this.f = new float[CosmeticFacialModel.COSMETIC_FACIAL_MATERIAL_POINTS_LENGTH * 3 / 2];
    }
    
    public void setPosition(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.e);
    }
    
    public void setTextureCoordinate(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.f);
    }
    
    public void setTextureCoordinate2(final FloatBuffer floatBuffer) {
        floatBuffer.put(CosmeticFacialModel.b);
    }
    
    public void setElementIndices(final IntBuffer intBuffer) {
        intBuffer.put(CosmeticFacialModel.a);
    }
    
    public void updateFace(final FaceAligment faceAligment, final TuSdkSize tuSdkSize) {
        if (faceAligment == null) {
            return;
        }
        final ArrayList<PointF> list = new ArrayList<PointF>();
        for (int i = 0; i < CosmeticFacialModel.d; ++i) {
            list.add(PointCalc.real(faceAligment.getOrginMarks()[CosmeticFacialModel.c[i]], tuSdkSize));
        }
        final PointF extensionPercentage = PointCalc.extensionPercentage(list.get(69), PointCalc.crossPoint(list.get(35), list.get(41), list.get(36), list.get(40)), 1.0f);
        list.add(extensionPercentage);
        final List<PointF> pointerInsert = PointCalc.pointerInsert(list.get(0), extensionPercentage, list.get(32), 3, false);
        final List<PointF> pointerInsert2 = PointCalc.pointerInsert(list.get(32), extensionPercentage, list.get(0), 3, false);
        for (int j = 0; j < pointerInsert.size(); ++j) {
            list.add(pointerInsert.get(j));
        }
        for (int k = 0; k < pointerInsert2.size(); ++k) {
            list.add(pointerInsert2.get(k));
        }
        final PointF crossPoint = PointCalc.crossPoint(list.get(10), list.get(49), list.get(5), list.get(66));
        final PointF crossPoint2 = PointCalc.crossPoint(list.get(22), list.get(57), list.get(27), list.get(72));
        list.add(crossPoint);
        list.add(crossPoint2);
        for (int l = 0; l < CosmeticFacialModel.DEFAULT_VERTEX.length / 2; ++l) {
            this.e[2 * l] = CosmeticFacialModel.DEFAULT_VERTEX[2 * l];
            this.e[2 * l + 1] = CosmeticFacialModel.DEFAULT_VERTEX[2 * l + 1];
            this.f[3 * l] = CosmeticFacialModel.DEFAULT_TEXTURE[3 * l];
            this.f[3 * l + 1] = CosmeticFacialModel.DEFAULT_TEXTURE[3 * l + 1];
            this.f[3 * l + 2] = CosmeticFacialModel.DEFAULT_TEXTURE[3 * l + 2];
        }
        for (int n = 0; n < list.size(); ++n) {
            final PointF normalize = PointCalc.normalize(list.get(n), tuSdkSize);
            this.e[CosmeticFacialModel.DEFAULT_VERTEX.length + 2 * n] = normalize.x * 2.0f - 1.0f;
            this.e[CosmeticFacialModel.DEFAULT_VERTEX.length + 2 * n + 1] = normalize.y * 2.0f - 1.0f;
            this.f[CosmeticFacialModel.DEFAULT_TEXTURE.length + 3 * n] = normalize.x;
            this.f[CosmeticFacialModel.DEFAULT_TEXTURE.length + 3 * n + 1] = normalize.y;
            this.f[CosmeticFacialModel.DEFAULT_TEXTURE.length + 3 * n + 2] = 1.0f;
        }
    }
    
    static {
        a = new int[] { 0, 1, 2, 0, 3, 2, 4, 37, 91, 37, 38, 91, 38, 91, 92, 38, 92, 93, 38, 39, 93, 39, 93, 90, 39, 44, 90, 44, 90, 96, 43, 44, 96, 43, 95, 96, 43, 94, 95, 42, 43, 94, 36, 42, 94, 6, 47, 97, 6, 7, 97, 7, 8, 97, 8, 9, 97, 9, 10, 97, 10, 11, 97, 11, 12, 97, 12, 13, 97, 13, 14, 97, 14, 78, 97, 70, 78, 97, 69, 70, 97, 52, 69, 97, 52, 53, 97, 53, 54, 97, 47, 54, 97, 14, 15, 78, 15, 78, 89, 15, 16, 89, 16, 17, 89, 17, 88, 89, 17, 18, 88, 18, 19, 88, 19, 87, 88, 19, 20, 87, 20, 21, 87, 21, 86, 87, 21, 22, 86, 22, 23, 86, 23, 85, 86, 23, 24, 85, 24, 25, 85, 25, 84, 85, 25, 26, 84, 26, 84, 98, 26, 27, 98, 27, 28, 98, 28, 29, 98, 29, 30, 98, 30, 31, 98, 31, 32, 98, 32, 33, 98, 33, 34, 98, 34, 55, 98, 55, 62, 98, 61, 62, 98, 60, 61, 98, 60, 77, 98, 76, 77, 98, 76, 84, 98, 5, 6, 47, 4, 5, 47, 4, 37, 47, 37, 47, 48, 37, 41, 48, 41, 48, 49, 40, 41, 49, 40, 49, 50, 40, 50, 51, 40, 51, 64, 40, 63, 64, 39, 40, 63, 39, 44, 63, 44, 45, 63, 45, 63, 65, 45, 59, 65, 45, 58, 59, 45, 57, 58, 45, 46, 57, 46, 56, 57, 42, 46, 56, 42, 55, 56, 36, 42, 55, 35, 36, 55, 34, 35, 55, 51, 52, 69, 51, 64, 69, 64, 67, 69, 64, 66, 67, 63, 64, 66, 63, 65, 66, 65, 66, 67, 65, 67, 77, 59, 65, 77, 59, 60, 77, 67, 68, 69, 67, 68, 77, 69, 70, 71, 68, 69, 71, 68, 71, 72, 68, 72, 73, 68, 73, 74, 68, 74, 75, 68, 75, 77, 75, 76, 77, 70, 78, 79, 70, 71, 79, 71, 72, 79, 72, 79, 80, 72, 73, 80, 73, 80, 81, 73, 81, 82, 73, 74, 82, 74, 82, 83, 74, 75, 83, 75, 76, 83, 76, 83, 84, 37, 38, 41, 38, 39, 41, 39, 40, 41, 42, 43, 46, 43, 44, 46, 44, 45, 46, 47, 48, 54, 48, 53, 54, 48, 49, 53, 49, 50, 53, 50, 52, 53, 50, 51, 52, 55, 56, 62, 56, 61, 62, 56, 57, 61, 57, 58, 61, 58, 60, 61, 58, 59, 60, 78, 79, 89, 79, 88, 89, 79, 80, 88, 80, 81, 88, 81, 87, 88, 81, 86, 87, 81, 82, 86, 82, 83, 86, 83, 85, 86, 83, 84, 85 };
        COSMETIC_FACIAL_TRIANGLES_MAP_LENGTH = CosmeticFacialModel.a.length;
        b = new float[] { 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.13375f, 0.421053f, 0.13125f, 0.463816f, 0.13375f, 0.505482f, 0.1375f, 0.546053f, 0.145f, 0.586623f, 0.15375f, 0.627193f, 0.165f, 0.667763f, 0.17875f, 0.707237f, 0.1975f, 0.746711f, 0.22f, 0.782895f, 0.2475f, 0.816886f, 0.28f, 0.847588f, 0.315f, 0.875f, 0.3525f, 0.903509f, 0.39375f, 0.928728f, 0.44125f, 0.946272f, 0.49875f, 0.951754f, 0.55875f, 0.946272f, 0.60625f, 0.928728f, 0.6475f, 0.903509f, 0.685f, 0.875f, 0.72f, 0.847588f, 0.7525f, 0.816886f, 0.78f, 0.782895f, 0.8025f, 0.746711f, 0.82125f, 0.707237f, 0.835f, 0.667763f, 0.84625f, 0.627193f, 0.855f, 0.586623f, 0.8625f, 0.546053f, 0.86625f, 0.505482f, 0.86875f, 0.463816f, 0.86625f, 0.421053f, 0.17625f, 0.39364f, 0.29125f, 0.345395f, 0.415f, 0.373904f, 0.41f, 0.400219f, 0.29f, 0.380482f, 0.82375f, 0.39364f, 0.70875f, 0.345395f, 0.585f, 0.373904f, 0.59f, 0.400219f, 0.71f, 0.380482f, 0.25125f, 0.458333f, 0.2825f, 0.438596f, 0.32375f, 0.432018f, 0.3675f, 0.445175f, 0.39875f, 0.474781f, 0.36125f, 0.479167f, 0.31875f, 0.48136f, 0.28125f, 0.473684f, 0.74875f, 0.458333f, 0.7175f, 0.438596f, 0.67625f, 0.432018f, 0.6325f, 0.445175f, 0.60125f, 0.474781f, 0.63875f, 0.479167f, 0.68125f, 0.48136f, 0.71875f, 0.473684f, 0.49875f, 0.453947f, 0.445f, 0.474781f, 0.55375f, 0.474781f, 0.5f, 0.509868f, 0.5f, 0.566886f, 0.5f, 0.622807f, 0.425f, 0.596491f, 0.40375f, 0.645833f, 0.43375f, 0.661184f, 0.46625f, 0.667763f, 0.5f, 0.676535f, 0.5325f, 0.667763f, 0.56625f, 0.661184f, 0.595f, 0.645833f, 0.57375f, 0.596491f, 0.3625f, 0.751096f, 0.4175f, 0.748904f, 0.47f, 0.745614f, 0.5f, 0.752193f, 0.52875f, 0.745614f, 0.58125f, 0.748904f, 0.63625f, 0.751096f, 0.60125f, 0.787281f, 0.55875f, 0.813596f, 0.49875f, 0.824561f, 0.44f, 0.813596f, 0.3975f, 0.787281f, 0.49875f, 0.095395f, 0.1575f, 0.296053f, 0.235f, 0.190789f, 0.35375f, 0.119518f, 0.84125f, 0.296053f, 0.76375f, 0.190789f, 0.645f, 0.119518f, 0.285f, 0.635965f, 0.71375f, 0.635965f };
        COSMETIC_FACIAL_MATERIAL_POINTS_LENGTH = CosmeticFacialModel.b.length;
        c = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 35, 37, 67, 65, 42, 40, 38, 68, 70, 52, 53, 72, 54, 55, 56, 73, 57, 61, 60, 75, 59, 58, 63, 76, 62, 43, 78, 79, 44, 45, 46, 80, 82, 47, 48, 49, 50, 51, 83, 81, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95 };
        d = CosmeticFacialModel.c.length;
    }
}
