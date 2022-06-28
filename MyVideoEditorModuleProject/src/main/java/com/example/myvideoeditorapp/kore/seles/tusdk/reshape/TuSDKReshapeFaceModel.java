// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.reshape;

import com.example.myvideoeditorapp.kore.utils.calc.PointCalc;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import android.graphics.PointF;
import com.example.myvideoeditorapp.kore.seles.tusdk.cosmetic.CosmeticModelBase;

public class TuSDKReshapeFaceModel extends CosmeticModelBase
{
    private static final int[] a;
    public static final int RESHAPE_TRIANGLES_MAP_LENGTH;
    private static final float[] b;
    public static final int RESHAPE_MATERIAL_POINTS_LENGTH;
    private static final int[] c;
    private static final int[] d;
    private float[] e;
    private float[] f;
    private PointF[] g;
    
    public TuSDKReshapeFaceModel() {
        this.e = new float[TuSDKReshapeFaceModel.RESHAPE_MATERIAL_POINTS_LENGTH];
        this.f = new float[TuSDKReshapeFaceModel.RESHAPE_MATERIAL_POINTS_LENGTH * 3 / 2];
        this.g = new PointF[118];
    }
    
    public void setPosition(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.e);
    }
    
    public void setTextureCoordinate(final FloatBuffer floatBuffer) {
        floatBuffer.put(this.f);
    }
    
    public void setTextureCoordinate2(final FloatBuffer floatBuffer) {
        floatBuffer.put(TuSDKReshapeFaceModel.b);
    }
    
    public void setElementIndices(final IntBuffer intBuffer) {
        intBuffer.put(TuSDKReshapeFaceModel.a);
    }
    
    public void updateFace(final FaceAligment faceAligment, final TuSdkSize tuSdkSize) {
        if (faceAligment == null) {
            return;
        }
        this.a(faceAligment, tuSdkSize);
        for (int i = 0; i < TuSDKReshapeFaceModel.DEFAULT_VERTEX.length / 2; ++i) {
            this.e[2 * i] = TuSDKReshapeFaceModel.DEFAULT_VERTEX[2 * i];
            this.e[2 * i + 1] = TuSDKReshapeFaceModel.DEFAULT_VERTEX[2 * i + 1];
            this.f[3 * i] = TuSDKReshapeFaceModel.DEFAULT_TEXTURE[3 * i];
            this.f[3 * i + 1] = TuSDKReshapeFaceModel.DEFAULT_TEXTURE[3 * i + 1];
            this.f[3 * i + 2] = TuSDKReshapeFaceModel.DEFAULT_TEXTURE[3 * i + 2];
        }
        for (int j = 0; j < this.g.length; ++j) {
            final PointF pointF = this.g[j];
            this.e[TuSDKReshapeFaceModel.DEFAULT_VERTEX.length + 2 * j] = pointF.x * 2.0f - 1.0f;
            this.e[TuSDKReshapeFaceModel.DEFAULT_VERTEX.length + 2 * j + 1] = pointF.y * 2.0f - 1.0f;
            this.f[TuSDKReshapeFaceModel.DEFAULT_TEXTURE.length + 3 * j] = pointF.x;
            this.f[TuSDKReshapeFaceModel.DEFAULT_TEXTURE.length + 3 * j + 1] = pointF.y;
            this.f[TuSDKReshapeFaceModel.DEFAULT_TEXTURE.length + 3 * j + 2] = 1.0f;
        }
    }
    
    private void a(final FaceAligment faceAligment, final TuSdkSize tuSdkSize) {
        final int n = 0;
        for (int i = 0; i < TuSDKReshapeFaceModel.c.length; ++i) {
            this.g[i] = faceAligment.getOrginMarks()[TuSDKReshapeFaceModel.c[i]];
        }
        final int n2 = n + TuSDKReshapeFaceModel.c.length;
        for (int j = 0; j < faceAligment.getEyeMarks().length; ++j) {
            this.g[j + n2] = faceAligment.getEyeMarks()[j];
        }
        final int n3 = n2 + faceAligment.getEyeMarks().length;
        for (int k = 0; k < TuSDKReshapeFaceModel.d.length; ++k) {
            this.g[k + n3] = faceAligment.getMouthMarks()[TuSDKReshapeFaceModel.d[k]];
        }
        final int n4 = n3 + TuSDKReshapeFaceModel.d.length;
        final PointF crossPoint = PointCalc.crossPoint(this.g[5], this.g[46], this.g[9], this.g[66]);
        final PointF crossPoint2 = PointCalc.crossPoint(this.g[27], this.g[52], this.g[23], this.g[83]);
        this.g[n4] = crossPoint;
        this.g[n4 + 1] = crossPoint2;
    }
    
    static {
        a = new int[] { 0, 1, 2, 0, 3, 2, 5, 6, 58, 6, 58, 120, 6, 7, 120, 7, 8, 120, 8, 9, 120, 9, 10, 120, 10, 11, 120, 11, 12, 120, 12, 92, 120, 50, 92, 120, 49, 50, 120, 49, 66, 120, 66, 67, 120, 67, 68, 120, 68, 69, 120, 69, 70, 120, 70, 71, 120, 71, 72, 120, 72, 73, 120, 58, 63, 120, 12, 13, 92, 13, 14, 92, 14, 92, 103, 14, 15, 103, 15, 16, 103, 16, 102, 103, 16, 17, 102, 17, 18, 102, 18, 101, 102, 18, 19, 101, 19, 20, 101, 20, 21, 101, 21, 22, 101, 22, 100, 101, 22, 23, 100, 23, 24, 100, 24, 99, 100, 24, 25, 99, 25, 26, 99, 26, 98, 99, 26, 27, 98, 27, 28, 98, 28, 98, 121, 28, 29, 121, 29, 30, 121, 30, 31, 121, 31, 32, 121, 32, 33, 121, 33, 34, 121, 34, 83, 121, 83, 84, 121, 84, 85, 121, 85, 86, 121, 86, 87, 121, 87, 88, 121, 88, 89, 121, 89, 90, 121, 90, 75, 121, 57, 75, 121, 56, 57, 121, 56, 98, 121, 43, 49, 66, 43, 46, 49, 43, 45, 46, 44, 45, 46, 44, 46, 57, 44, 57, 75, 46, 47, 49, 46, 47, 57, 47, 48, 49, 47, 48, 57, 48, 49, 50, 48, 56, 57, 48, 50, 51, 48, 51, 52, 48, 52, 53, 48, 53, 54, 48, 54, 55, 48, 55, 56, 50, 92, 93, 50, 51, 93, 51, 93, 94, 51, 52, 94, 52, 53, 94, 53, 94, 95, 53, 95, 96, 53, 54, 96, 54, 55, 96, 55, 96, 97, 55, 56, 97, 56, 97, 98, 92, 93, 104, 93, 104, 105, 93, 105, 106, 93, 94, 106, 94, 106, 107, 94, 107, 108, 94, 95, 108, 95, 96, 108, 96, 108, 109, 96, 109, 110, 96, 97, 110, 97, 110, 111, 97, 111, 112, 97, 98, 112, 104, 105, 119, 105, 106, 119, 106, 118, 119, 106, 117, 118, 106, 107, 117, 107, 108, 117, 108, 116, 117, 108, 115, 116, 108, 109, 115, 109, 110, 115, 110, 114, 115, 110, 113, 114, 110, 111, 113, 111, 112, 113, 92, 103, 104, 103, 104, 119, 103, 118, 119, 102, 103, 118, 102, 117, 118, 101, 102, 117, 101, 116, 117, 101, 115, 116, 100, 101, 115, 100, 114, 115, 99, 100, 114, 99, 113, 114, 99, 112, 113, 98, 99, 112, 4, 5, 58, 4, 37, 58, 37, 58, 59, 37, 59, 60, 37, 38, 60, 38, 60, 61, 38, 61, 62, 38, 62, 63, 38, 39, 63, 39, 63, 64, 39, 64, 65, 39, 65, 66, 39, 43, 66, 58, 59, 74, 59, 60, 74, 60, 61, 74, 61, 62, 74, 62, 63, 74, 63, 64, 74, 64, 65, 74, 65, 66, 74, 66, 67, 74, 67, 68, 74, 68, 69, 74, 69, 70, 74, 70, 71, 74, 71, 72, 74, 72, 73, 74, 58, 73, 74, 39, 43, 66, 39, 43, 45, 39, 42, 45, 42, 44, 45, 42, 44, 75, 42, 75, 76, 42, 76, 77, 42, 77, 78, 41, 42, 78, 41, 78, 79, 41, 79, 80, 41, 80, 81, 40, 41, 81, 40, 81, 82, 40, 82, 83, 36, 40, 83, 35, 36, 83, 34, 35, 83, 75, 76, 91, 76, 77, 91, 77, 78, 91, 78, 79, 91, 79, 80, 91, 80, 81, 91, 81, 82, 91, 82, 83, 91, 83, 84, 91, 84, 85, 91, 85, 86, 91, 86, 87, 91, 87, 88, 91, 88, 89, 91, 89, 90, 91, 75, 90, 91 };
        RESHAPE_TRIANGLES_MAP_LENGTH = TuSDKReshapeFaceModel.a.length;
        b = new float[] { 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.2125f, 0.435307f, 0.21f, 0.470395f, 0.21f, 0.504386f, 0.2125f, 0.538377f, 0.21625f, 0.571272f, 0.2225f, 0.60636f, 0.22875f, 0.639254f, 0.24f, 0.672149f, 0.25375f, 0.703947f, 0.27375f, 0.734649f, 0.295f, 0.762061f, 0.32125f, 0.788377f, 0.34875f, 0.811404f, 0.37875f, 0.83443f, 0.41375f, 0.855263f, 0.4525f, 0.868421f, 0.5f, 0.873904f, 0.5475f, 0.868421f, 0.58625f, 0.855263f, 0.62125f, 0.83443f, 0.65125f, 0.811404f, 0.67875f, 0.788377f, 0.705f, 0.762061f, 0.72625f, 0.734649f, 0.74625f, 0.703947f, 0.76f, 0.672149f, 0.77125f, 0.639254f, 0.7775f, 0.60636f, 0.78375f, 0.571272f, 0.7875f, 0.538377f, 0.79f, 0.504386f, 0.79f, 0.470395f, 0.7875f, 0.435307f, 0.26f, 0.413377f, 0.3475f, 0.406798f, 0.435f, 0.422149f, 0.74f, 0.413377f, 0.6525f, 0.406798f, 0.565f, 0.422149f, 0.45625f, 0.480263f, 0.54375f, 0.479167f, 0.5f, 0.463816f, 0.5f, 0.510965f, 0.5f, 0.558114f, 0.5f, 0.60636f, 0.43625f, 0.575658f, 0.41875f, 0.614035f, 0.445f, 0.630482f, 0.47125f, 0.635965f, 0.5f, 0.644737f, 0.5275f, 0.635965f, 0.5575f, 0.630482f, 0.58125f, 0.614035f, 0.56375f, 0.575658f, 0.31125f, 0.464912f, 0.32125f, 0.458333f, 0.335f, 0.449561f, 0.35f, 0.445175f, 0.36875f, 0.445175f, 0.385f, 0.446272f, 0.4f, 0.45614f, 0.4125f, 0.463816f, 0.42375f, 0.47807f, 0.40875f, 0.479167f, 0.39625f, 0.479167f, 0.38f, 0.480263f, 0.36375f, 0.480263f, 0.34875f, 0.47807f, 0.335f, 0.474781f, 0.32375f, 0.471491f, 0.3675f, 0.461623f, 0.57625f, 0.47807f, 0.5875f, 0.463816f, 0.6f, 0.45614f, 0.615f, 0.446272f, 0.63125f, 0.445175f, 0.65f, 0.445175f, 0.665f, 0.449561f, 0.67875f, 0.458333f, 0.68875f, 0.464912f, 0.67625f, 0.471491f, 0.665f, 0.474781f, 0.65125f, 0.47807f, 0.63625f, 0.480263f, 0.62f, 0.480263f, 0.60375f, 0.479167f, 0.59125f, 0.479167f, 0.6325f, 0.461623f, 0.3625f, 0.686404f, 0.405f, 0.684211f, 0.4725f, 0.679825f, 0.49875f, 0.684211f, 0.5275f, 0.680921f, 0.5825f, 0.683114f, 0.6375f, 0.686404f, 0.6025f, 0.722588f, 0.56f, 0.75f, 0.5f, 0.759868f, 0.44f, 0.75f, 0.3975f, 0.722588f, 0.3825f, 0.690789f, 0.40875f, 0.694079f, 0.4325f, 0.696272f, 0.4675f, 0.701754f, 0.5f, 0.701754f, 0.53375f, 0.700658f, 0.56875f, 0.696272f, 0.5925f, 0.694079f, 0.6175f, 0.690789f, 0.59625f, 0.70614f, 0.5725f, 0.718202f, 0.53625f, 0.726974f, 0.5f, 0.729167f, 0.46375f, 0.726974f, 0.4275f, 0.718202f, 0.40375f, 0.70614f, 0.3175f, 0.609649f, 0.6825f, 0.609649f };
        RESHAPE_MATERIAL_POINTS_LENGTH = TuSDKReshapeFaceModel.b.length;
        c = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 65, 67, 42, 70, 68, 78, 79, 43, 44, 45, 46, 80, 82, 47, 48, 49, 50, 51, 83, 81 };
        d = new int[] { 0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39 };
    }
}
