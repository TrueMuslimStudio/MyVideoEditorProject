// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.reshape;

import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import com.example.myvideoeditorapp.kore.utils.calc.PointCalc;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import android.graphics.PointF;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;

public class TuSDKPlasticFaceModel
{
    private float e;
    private TuSdkSize f;
    private PointF[] g;
    float a;
    float b;
    float c;
    TuSDKPlasticArg[] d;
    private PointF[] h;
    private PointF i;
    private PointF j;
    private PointF k;
    private PointF l;
    private PointF m;
    private PointF n;
    private PointF o;
    private PointF p;
    private PointF q;
    private PointF r;
    private PointF s;
    private float t;
    private float u;
    private float v;
    public static final int PLASTIC_FACE_POINTS_COUNT = 95;
    public static final int TRIANGLE_SIZE = 3;
    public static final int FACE_TRIANGLES_COUNT = 180;
    private static final int[] w;
    private static final int[][] x;
    private static final int[][] y;
    private static final int[][] z;
    private static final int[][] A;
    private static final int[][] B;
    private static final int[][] C;
    private static final int[][] D;
    private static final int[][] E;
    
    public TuSDKPlasticFaceModel(final FaceAligment faceAligment, final TuSdkSize tuSdkSize) {
        this.e = 1.0E-4f;
        this.g = new PointF[106];
        this.d = new TuSDKPlasticArg[TuSDKPlasticType.TuSDK_PLASTIC_PARS_COUNT.ordinal()];
        this.h = new PointF[95];
        if (tuSdkSize.width == 0 || tuSdkSize.height == 0 || faceAligment == null || faceAligment.getOrginMarks() == null || faceAligment.getOrginMarks().length == 0) {
            return;
        }
        this.a(faceAligment, tuSdkSize);
        this.a();
    }
    
    private void a(final FaceAligment faceAligment, final TuSdkSize f) {
        this.f = f;
        final PointF[] orginMarks = faceAligment.getOrginMarks();
        for (int i = 0; i < 106; ++i) {
            this.g[i] = PointCalc.real(orginMarks[i], this.f);
        }
        this.a = faceAligment.roll;
        this.b = faceAligment.yaw;
        this.c = faceAligment.pitch;
        this.q = PointCalc.extensionPercentage(this.g[49], PointCalc.crossPoint(this.g[37], this.g[68], this.g[38], this.g[67]), 1.0f);
        this.m = PointCalc.extensionPercentage(this.g[16], this.q, 0.25f);
        this.n = PointCalc.extensionPercentage(this.q, this.g[16], 0.25f);
        final PointF vertical = PointCalc.vertical(this.n, this.m, this.g[4]);
        final PointF vertical2 = PointCalc.vertical(this.n, this.m, this.g[28]);
        final float distance = PointCalc.distance(vertical, this.g[4]);
        final float distance2 = PointCalc.distance(vertical2, this.g[28]);
        final float n = distance * 0.75f;
        final float n2 = distance2 * 0.75f;
        final float n3 = Math.abs(distance - distance2) * 0.25f;
        float n4;
        float n5;
        if (distance < distance2) {
            n4 = n + n3;
            n5 = n2 - n3;
        }
        else {
            n4 = n - n3;
            n5 = n2 + n3;
        }
        this.o = PointCalc.extension(vertical, this.g[4], n4);
        this.p = PointCalc.extension(vertical2, this.g[28], n5);
        this.i = new PointF(this.o.x - (vertical.x - this.m.x), this.o.y - (vertical.y - this.m.y));
        this.j = new PointF(this.o.x - (vertical.x - this.n.x), this.o.y - (vertical.y - this.n.y));
        this.k = new PointF(this.p.x - (vertical2.x - this.m.x), this.p.y - (vertical2.y - this.m.y));
        this.l = new PointF(this.p.x - (vertical2.x - this.n.x), this.p.y - (vertical2.y - this.n.y));
        for (int j = 0; j < 17; ++j) {
            this.h[TuSDKPlasticFaceModel.x[j][0]] = this.g[TuSDKPlasticFaceModel.x[j][1]];
        }
        for (int k = 0; k < 9; ++k) {
            this.h[TuSDKPlasticFaceModel.y[k][0]] = this.g[TuSDKPlasticFaceModel.y[k][1]];
            this.h[TuSDKPlasticFaceModel.z[k][0]] = this.g[TuSDKPlasticFaceModel.z[k][1]];
        }
        for (int l = 0; l < 8; ++l) {
            this.h[TuSDKPlasticFaceModel.A[l][0]] = this.g[TuSDKPlasticFaceModel.A[l][1]];
            this.h[TuSDKPlasticFaceModel.B[l][0]] = this.g[TuSDKPlasticFaceModel.B[l][1]];
        }
        for (int n6 = 0; n6 < 15; ++n6) {
            this.h[TuSDKPlasticFaceModel.C[n6][0]] = this.g[TuSDKPlasticFaceModel.C[n6][1]];
        }
        for (int n7 = 0; n7 < 20; ++n7) {
            this.h[TuSDKPlasticFaceModel.D[n7][0]] = this.g[TuSDKPlasticFaceModel.D[n7][1]];
        }
        this.h[TuSDKPlasticFaceModel.w[0]] = this.q;
        this.h[TuSDKPlasticFaceModel.w[1]] = this.i;
        this.h[TuSDKPlasticFaceModel.w[2]] = PointCalc.extensionPercentage(this.j, this.i, -0.35f);
        this.h[TuSDKPlasticFaceModel.w[3]] = PointCalc.extensionPercentage(this.i, this.j, -0.35f);
        this.h[TuSDKPlasticFaceModel.w[4]] = this.j;
        this.h[TuSDKPlasticFaceModel.w[5]] = this.l;
        this.h[TuSDKPlasticFaceModel.w[6]] = PointCalc.extensionPercentage(this.k, this.l, -0.35f);
        this.h[TuSDKPlasticFaceModel.w[7]] = PointCalc.extensionPercentage(this.l, this.k, -0.35f);
        this.h[TuSDKPlasticFaceModel.w[8]] = this.k;
        this.r = new PointF(0.0f, 0.0f);
        this.s = new PointF(0.0f, 0.0f);
        for (int n8 = 0; n8 < 8; ++n8) {
            final PointF r = this.r;
            r.x += this.h[TuSDKPlasticFaceModel.A[n8][0]].x / 8.0f;
            final PointF r2 = this.r;
            r2.y += this.h[TuSDKPlasticFaceModel.A[n8][0]].y / 8.0f;
            final PointF s = this.s;
            s.x += this.h[TuSDKPlasticFaceModel.B[n8][0]].x / 8.0f;
            final PointF s2 = this.s;
            s2.y += this.h[TuSDKPlasticFaceModel.B[n8][0]].y / 8.0f;
        }
        this.t = PointCalc.distance(this.g[TuSDKPlasticFaceModel.C[0][1]], this.g[TuSDKPlasticFaceModel.C[6][1]]);
        final PointF pointF = this.h[TuSDKPlasticFaceModel.C[6][0]];
        final float distance3 = PointCalc.distance(pointF, this.h[TuSDKPlasticFaceModel.x[3][0]]);
        final float distance4 = PointCalc.distance(pointF, this.h[TuSDKPlasticFaceModel.x[13][0]]);
        if (distance3 > distance4) {
            this.u = 1.0f;
            this.v = 1.0f * distance4 / distance3;
        }
        else {
            this.v = 1.0f;
            this.u = 1.0f * distance3 / distance4;
        }
    }
    
    private void a() {
        for (final TuSDKPlasticType a : TuSDKPlasticType.values()) {
            if (a != TuSDKPlasticType.TuSDK_PLASTIC_PARS_COUNT) {
                final TuSDKPlasticArg tuSDKPlasticArg = new TuSDKPlasticArg();
                tuSDKPlasticArg.a = a;
                switch (a.ordinal()) {
                    case 1: {
                        tuSDKPlasticArg.b = 0.05f;
                        tuSDKPlasticArg.c = false;
                        break;
                    }
                    case 2: {
                        tuSDKPlasticArg.b = 0.1f;
                        tuSDKPlasticArg.c = true;
                        break;
                    }
                    case 3:
                    case 4:
                    case 5:
                    case 6: {
                        tuSDKPlasticArg.b = 0.2f;
                        tuSDKPlasticArg.c = true;
                        break;
                    }
                    case 7: {
                        tuSDKPlasticArg.b = 0.02f;
                        tuSDKPlasticArg.c = false;
                        break;
                    }
                    case 8: {
                        tuSDKPlasticArg.b = 0.2f;
                        tuSDKPlasticArg.c = false;
                        break;
                    }
                    case 9: {
                        tuSDKPlasticArg.b = 0.15f;
                        tuSDKPlasticArg.c = true;
                        break;
                    }
                    case 10: {
                        tuSDKPlasticArg.b = 5.0f;
                        tuSDKPlasticArg.c = false;
                        break;
                    }
                    case 11: {
                        tuSDKPlasticArg.b = 0.05f;
                        tuSDKPlasticArg.c = false;
                        break;
                    }
                    case 12: {
                        tuSDKPlasticArg.b = 0.04f;
                        tuSDKPlasticArg.c = false;
                        break;
                    }
                    case 13: {
                        tuSDKPlasticArg.b = 0.1f;
                        tuSDKPlasticArg.c = true;
                        break;
                    }
                    case 14: {
                        tuSDKPlasticArg.b = 0.1f;
                        tuSDKPlasticArg.c = true;
                        break;
                    }
                    case 15: {
                        tuSDKPlasticArg.b = 0.2f;
                        tuSDKPlasticArg.c = true;
                        break;
                    }
                    case 16: {
                        tuSDKPlasticArg.b = 0.075f;
                        tuSDKPlasticArg.c = true;
                        break;
                    }
                    case 17: {
                        tuSDKPlasticArg.b = 0.1f;
                        tuSDKPlasticArg.c = false;
                        break;
                    }
                    case 18: {
                        tuSDKPlasticArg.b = 0.1f;
                        tuSDKPlasticArg.c = false;
                        break;
                    }
                    case 19: {
                        tuSDKPlasticArg.b = 0.15f;
                        tuSDKPlasticArg.c = false;
                        break;
                    }
                    case 20: {
                        tuSDKPlasticArg.b = 0.1f;
                        tuSDKPlasticArg.c = false;
                        break;
                    }
                }
                this.d[a.ordinal()] = tuSDKPlasticArg;
            }
        }
    }
    
    private float a(final float n, final TuSDKPlasticType tuSDKPlasticType) {
        final TuSDKPlasticType[] values = TuSDKPlasticType.values();
        for (int length = values.length, i = 0; i < length; ++i) {
            if (tuSDKPlasticType == values[i]) {
                return this.d[tuSDKPlasticType.ordinal()].b * n;
            }
        }
        return 0.0f;
    }
    
    public void calcForeheadHeight(final float n) {
        final float a = this.a(n, TuSDKPlasticType.TuSDK_FOREHEAD_HEIGHT);
        if (Math.abs(a) < this.e) {
            return;
        }
        this.h[TuSDKPlasticFaceModel.w[0]] = PointCalc.extensionPercentage(this.h[TuSDKPlasticFaceModel.C[0][0]], this.q, a);
    }
    
    public void calcFaceSmall(final float n) {
        final float a = -this.a(n, TuSDKPlasticType.TuSDK_FACE_SMALL);
        if (Math.abs(a) < this.e) {
            return;
        }
        for (int i = 2; i < 8; ++i) {
            final int n2 = i;
            this.h[TuSDKPlasticFaceModel.x[n2][0]] = PointCalc.extensionPercentage(PointCalc.vertical(this.r, this.s, this.h[TuSDKPlasticFaceModel.x[n2][0]]), this.h[TuSDKPlasticFaceModel.x[n2][0]], a);
            final int n3 = 16 - n2;
            this.h[TuSDKPlasticFaceModel.x[n3][0]] = PointCalc.extensionPercentage(PointCalc.vertical(this.r, this.s, this.h[TuSDKPlasticFaceModel.x[n3][0]]), this.h[TuSDKPlasticFaceModel.x[n3][0]], a);
        }
        final PointF pointF = this.h[TuSDKPlasticFaceModel.x[8][0]];
        this.h[TuSDKPlasticFaceModel.x[8][0]] = PointCalc.extensionPercentage(PointCalc.vertical(this.r, this.s, pointF), pointF, a);
        for (int j = 0; j < 20; ++j) {
            this.h[TuSDKPlasticFaceModel.D[j][0]] = PointCalc.extensionPercentage(PointCalc.vertical(this.r, this.s, this.h[TuSDKPlasticFaceModel.D[j][0]]), this.h[TuSDKPlasticFaceModel.D[j][0]], a);
        }
        for (int k = 0; k < 15; ++k) {
            if (k != 0 && k != 9) {
                if (k != 10) {
                    this.h[TuSDKPlasticFaceModel.C[k][0]] = PointCalc.extensionPercentage(PointCalc.vertical(this.r, this.s, this.h[TuSDKPlasticFaceModel.C[k][0]]), this.h[TuSDKPlasticFaceModel.C[k][0]], a);
                }
            }
        }
    }
    
    public void calcCheekThin(final float n) {
        final float a = -this.a(n, TuSDKPlasticType.TuSDK_CHEEK_THIN);
        if (Math.abs(a) < this.e) {
            return;
        }
        final float n2 = a * this.u;
        final float n3 = a * this.v;
        final PointF center = PointCalc.center(this.h[TuSDKPlasticFaceModel.C[0][0]], this.h[TuSDKPlasticFaceModel.C[6][0]]);
        final float[] array = { 0.05f, 0.15f, 0.25f, 0.35f, 0.5f, 0.5f, 0.45f, 0.35f, 0.35f };
        for (int i = 0; i < 8; ++i) {
            final int n4 = i;
            this.h[TuSDKPlasticFaceModel.x[n4][0]] = PointCalc.extensionPercentage(center, this.h[TuSDKPlasticFaceModel.x[n4][0]], n2 * array[i]);
            final int n5 = 16 - n4;
            this.h[TuSDKPlasticFaceModel.x[n5][0]] = PointCalc.extensionPercentage(center, this.h[TuSDKPlasticFaceModel.x[n5][0]], n3 * array[i]);
        }
        this.h[TuSDKPlasticFaceModel.x[8][0]] = PointCalc.extensionPercentage(center, this.h[TuSDKPlasticFaceModel.x[8][0]], (n2 + n3) * 0.5f * array[8]);
        final PointF percentage = PointCalc.percentage(this.h[TuSDKPlasticFaceModel.C[0][0]], this.h[TuSDKPlasticFaceModel.C[6][0]], 0.6f);
        for (int j = 0; j < 20; ++j) {
            this.h[TuSDKPlasticFaceModel.D[j][0]] = PointCalc.extensionPercentage(percentage, this.h[TuSDKPlasticFaceModel.D[j][0]], a * 0.3f);
        }
        for (int k = 0; k < 15; ++k) {
            if (k != 0 && k != 1 && k != 9) {
                if (k != 10) {
                    this.h[TuSDKPlasticFaceModel.C[k][0]] = PointCalc.extensionPercentage(percentage, this.h[TuSDKPlasticFaceModel.C[k][0]], a * 0.35f);
                }
            }
        }
    }
    
    public void calcCheekNarrow(final float n) {
        final float a = -this.a(n, TuSDKPlasticType.TuSDK_CHEEK_NARROW);
        if (Math.abs(a) < this.e) {
            return;
        }
        final float n2 = a * this.u;
        final float n3 = a * this.v;
        final PointF center = PointCalc.center(this.h[TuSDKPlasticFaceModel.C[0][0]], this.h[TuSDKPlasticFaceModel.C[6][0]]);
        final float[] array = { 0.1f, 0.2f, 0.225f, 0.225f, 0.225f, 0.2f, 0.175f, 0.15f };
        for (int i = 0; i < 8; ++i) {
            final int n4 = i;
            this.h[TuSDKPlasticFaceModel.x[n4][0]] = PointCalc.extensionPercentage(center, this.h[TuSDKPlasticFaceModel.x[n4][0]], n2 * array[i]);
            final int n5 = 16 - n4;
            this.h[TuSDKPlasticFaceModel.x[n5][0]] = PointCalc.extensionPercentage(center, this.h[TuSDKPlasticFaceModel.x[n5][0]], n3 * array[i]);
        }
    }
    
    public void calcCheekBoneNarrow(final float n) {
        final float a = -this.a(n, TuSDKPlasticType.TuSDK_CHEEKBONE_NARROW);
        if (Math.abs(a) < this.e) {
            return;
        }
        final float n2 = a * this.u;
        final float n3 = a * this.v;
        final PointF center = PointCalc.center(this.h[TuSDKPlasticFaceModel.C[0][0]], this.h[TuSDKPlasticFaceModel.C[6][0]]);
        final float[] array = { 0.1f, 0.15f, 0.15f, 0.1f };
        for (int i = 0; i < 4; ++i) {
            final int n4 = i;
            this.h[TuSDKPlasticFaceModel.x[n4][0]] = PointCalc.extensionPercentage(center, this.h[TuSDKPlasticFaceModel.x[n4][0]], n2 * array[i]);
            final int n5 = 16 - i;
            this.h[TuSDKPlasticFaceModel.x[n5][0]] = PointCalc.extensionPercentage(center, this.h[TuSDKPlasticFaceModel.x[n5][0]], n3 * array[i]);
        }
    }
    
    public void calcCheekLowerBoneNarrow(final float n) {
        final float a = -this.a(n, TuSDKPlasticType.TuSDK_CHEEKLOWERBONE_NARROW);
        if (Math.abs(a) < this.e) {
            return;
        }
        final float n2 = a * this.u;
        final float n3 = a * this.v;
        final PointF center = PointCalc.center(this.h[TuSDKPlasticFaceModel.C[0][0]], this.h[TuSDKPlasticFaceModel.C[6][0]]);
        final float[] array = { 0.05f, 0.15f, 0.2f, 0.15f };
        for (int i = 0; i < 4; ++i) {
            final int n4 = i + 2;
            this.h[TuSDKPlasticFaceModel.x[n4][0]] = PointCalc.extensionPercentage(center, this.h[TuSDKPlasticFaceModel.x[n4][0]], n2 * array[i]);
            final int n5 = 16 - n4;
            this.h[TuSDKPlasticFaceModel.x[n5][0]] = PointCalc.extensionPercentage(center, this.h[TuSDKPlasticFaceModel.x[n5][0]], n3 * array[i]);
        }
    }
    
    public void calcBrowThickness(final float n) {
        final float a = this.a(n, TuSDKPlasticType.TuSDK_BROW_THICKNESS);
        if (Math.abs(a) < this.e) {
            return;
        }
        final PointF center = PointCalc.center(this.h[TuSDKPlasticFaceModel.y[1][0]], this.h[TuSDKPlasticFaceModel.y[8][0]]);
        this.h[TuSDKPlasticFaceModel.y[1][0]] = PointCalc.extensionPercentage(center, this.h[TuSDKPlasticFaceModel.y[1][0]], a);
        this.h[TuSDKPlasticFaceModel.y[8][0]] = PointCalc.extensionPercentage(center, this.h[TuSDKPlasticFaceModel.y[8][0]], a);
        final PointF center2 = PointCalc.center(this.h[TuSDKPlasticFaceModel.y[2][0]], this.h[TuSDKPlasticFaceModel.y[7][0]]);
        this.h[TuSDKPlasticFaceModel.y[2][0]] = PointCalc.extensionPercentage(center2, this.h[TuSDKPlasticFaceModel.y[2][0]], a);
        this.h[TuSDKPlasticFaceModel.y[7][0]] = PointCalc.extensionPercentage(center2, this.h[TuSDKPlasticFaceModel.y[7][0]], a);
        final PointF center3 = PointCalc.center(this.h[TuSDKPlasticFaceModel.y[3][0]], this.h[TuSDKPlasticFaceModel.y[6][0]]);
        this.h[TuSDKPlasticFaceModel.y[3][0]] = PointCalc.extensionPercentage(center3, this.h[TuSDKPlasticFaceModel.y[3][0]], a);
        this.h[TuSDKPlasticFaceModel.y[6][0]] = PointCalc.extensionPercentage(center3, this.h[TuSDKPlasticFaceModel.y[6][0]], a);
        final PointF center4 = PointCalc.center(this.h[TuSDKPlasticFaceModel.y[4][0]], this.h[TuSDKPlasticFaceModel.y[5][0]]);
        this.h[TuSDKPlasticFaceModel.y[4][0]] = PointCalc.extensionPercentage(center4, this.h[TuSDKPlasticFaceModel.y[4][0]], a);
        this.h[TuSDKPlasticFaceModel.y[5][0]] = PointCalc.extensionPercentage(center4, this.h[TuSDKPlasticFaceModel.y[5][0]], a);
        final PointF center5 = PointCalc.center(this.h[TuSDKPlasticFaceModel.z[0][0]], this.h[TuSDKPlasticFaceModel.z[8][0]]);
        this.h[TuSDKPlasticFaceModel.z[0][0]] = PointCalc.extensionPercentage(center5, this.h[TuSDKPlasticFaceModel.z[0][0]], a);
        this.h[TuSDKPlasticFaceModel.z[8][0]] = PointCalc.extensionPercentage(center5, this.h[TuSDKPlasticFaceModel.z[8][0]], a);
        final PointF center6 = PointCalc.center(this.h[TuSDKPlasticFaceModel.z[1][0]], this.h[TuSDKPlasticFaceModel.z[7][0]]);
        this.h[TuSDKPlasticFaceModel.z[1][0]] = PointCalc.extensionPercentage(center6, this.h[TuSDKPlasticFaceModel.z[1][0]], a);
        this.h[TuSDKPlasticFaceModel.z[7][0]] = PointCalc.extensionPercentage(center6, this.h[TuSDKPlasticFaceModel.z[7][0]], a);
        final PointF center7 = PointCalc.center(this.h[TuSDKPlasticFaceModel.z[2][0]], this.h[TuSDKPlasticFaceModel.z[6][0]]);
        this.h[TuSDKPlasticFaceModel.z[2][0]] = PointCalc.extensionPercentage(center7, this.h[TuSDKPlasticFaceModel.z[2][0]], a);
        this.h[TuSDKPlasticFaceModel.z[6][0]] = PointCalc.extensionPercentage(center7, this.h[TuSDKPlasticFaceModel.z[6][0]], a);
        final PointF center8 = PointCalc.center(this.h[TuSDKPlasticFaceModel.z[3][0]], this.h[TuSDKPlasticFaceModel.z[5][0]]);
        this.h[TuSDKPlasticFaceModel.z[3][0]] = PointCalc.extensionPercentage(center8, this.h[TuSDKPlasticFaceModel.z[3][0]], a);
        this.h[TuSDKPlasticFaceModel.z[5][0]] = PointCalc.extensionPercentage(center8, this.h[TuSDKPlasticFaceModel.z[5][0]], a);
    }
    
    public void calcBrowHeight(final float n) {
        final float a = this.a(n, TuSDKPlasticType.TuSDK_BROW_HEIGHT);
        if (Math.abs(a) < this.e) {
            return;
        }
        final float n2 = -a * this.t;
        for (int i = 0; i < 9; ++i) {
            final PointF pointF = this.h[TuSDKPlasticFaceModel.y[i][0]];
            this.h[TuSDKPlasticFaceModel.y[i][0]] = PointCalc.extension(PointCalc.vertical(this.i, this.k, pointF), pointF, n2);
            final PointF pointF2 = this.h[TuSDKPlasticFaceModel.z[i][0]];
            this.h[TuSDKPlasticFaceModel.z[i][0]] = PointCalc.extension(PointCalc.vertical(this.i, this.k, pointF2), pointF2, n2);
        }
    }
    
    public void calcEyeEnlarge(final float a) {
        final float a2 = this.a(a, TuSDKPlasticType.TuSDK_EYE_ENLARGE);
        if (Math.abs(a) < this.e) {
            return;
        }
        final PointF[] array = new PointF[8];
        for (int i = 0; i < 8; ++i) {
            array[i] = this.h[TuSDKPlasticFaceModel.A[i][0]];
        }
        final float distance = PointCalc.distance(array[2], array[6]);
        final PointF center = PointCalc.center(array[0], array[4]);
        final float distance2 = PointCalc.distance(center, array[2]);
        final float distance3 = PointCalc.distance(center, array[6]);
        if (distance2 < distance) {
            if (distance3 < distance) {
                final PointF crossPoint = PointCalc.crossPoint(array[1], array[5], array[3], array[7]);
                this.h[TuSDKPlasticFaceModel.A[0][0]] = PointCalc.extensionPercentage(crossPoint, array[0], a2);
                this.h[TuSDKPlasticFaceModel.A[4][0]] = PointCalc.extensionPercentage(crossPoint, array[4], a2);
                final PointF crossPoint2 = PointCalc.crossPoint(array[1], array[6], array[2], array[7]);
                this.h[TuSDKPlasticFaceModel.A[1][0]] = PointCalc.extensionPercentage(crossPoint2, array[1], a2 * 2.0f);
                this.h[TuSDKPlasticFaceModel.A[7][0]] = PointCalc.extensionPercentage(crossPoint2, array[7], a2 * 2.0f);
                final PointF crossPoint3 = PointCalc.crossPoint(array[1], array[5], array[3], array[7]);
                this.h[TuSDKPlasticFaceModel.A[2][0]] = PointCalc.extensionPercentage(crossPoint3, array[2], a2 * 2.25f);
                this.h[TuSDKPlasticFaceModel.A[6][0]] = PointCalc.extensionPercentage(crossPoint3, array[6], a2 * 2.25f);
                final PointF crossPoint4 = PointCalc.crossPoint(array[3], array[6], array[2], array[5]);
                this.h[TuSDKPlasticFaceModel.A[3][0]] = PointCalc.extensionPercentage(crossPoint4, array[3], a2 * 2.0f);
                this.h[TuSDKPlasticFaceModel.A[5][0]] = PointCalc.extensionPercentage(crossPoint4, array[5], a2 * 2.0f);
            }
        }
        final PointF[] array2 = new PointF[8];
        for (int j = 0; j < 8; ++j) {
            array2[j] = this.h[TuSDKPlasticFaceModel.B[j][0]];
        }
        final float distance4 = PointCalc.distance(array2[2], array2[6]);
        final PointF center2 = PointCalc.center(array2[0], array2[4]);
        final float distance5 = PointCalc.distance(center2, array2[2]);
        final float distance6 = PointCalc.distance(center2, array2[6]);
        if (distance5 < distance4) {
            if (distance6 < distance4) {
                final PointF crossPoint5 = PointCalc.crossPoint(array2[1], array2[5], array2[3], array2[7]);
                this.h[TuSDKPlasticFaceModel.B[0][0]] = PointCalc.extensionPercentage(crossPoint5, array2[0], a2);
                this.h[TuSDKPlasticFaceModel.B[4][0]] = PointCalc.extensionPercentage(crossPoint5, array2[4], a2);
                final PointF crossPoint6 = PointCalc.crossPoint(array2[1], array2[6], array2[2], array2[7]);
                this.h[TuSDKPlasticFaceModel.B[1][0]] = PointCalc.extensionPercentage(crossPoint6, array2[1], a2 * 2.0f);
                this.h[TuSDKPlasticFaceModel.B[7][0]] = PointCalc.extensionPercentage(crossPoint6, array2[7], a2 * 2.0f);
                final PointF crossPoint7 = PointCalc.crossPoint(array2[1], array2[5], array2[3], array2[7]);
                this.h[TuSDKPlasticFaceModel.B[2][0]] = PointCalc.extensionPercentage(crossPoint7, array2[2], a2 * 2.25f);
                this.h[TuSDKPlasticFaceModel.B[6][0]] = PointCalc.extensionPercentage(crossPoint7, array2[6], a2 * 2.25f);
                final PointF crossPoint8 = PointCalc.crossPoint(array2[3], array2[6], array2[2], array2[5]);
                this.h[TuSDKPlasticFaceModel.B[3][0]] = PointCalc.extensionPercentage(crossPoint8, array2[3], a2 * 2.0f);
                this.h[TuSDKPlasticFaceModel.B[5][0]] = PointCalc.extensionPercentage(crossPoint8, array2[5], a2 * 2.0f);
            }
        }
    }
    
    public void calcEyeAngle(final float n) {
        final float a = this.a(n, TuSDKPlasticType.TuSDK_EYE_ANGLE);
        if (Math.abs(a) < this.e) {
            return;
        }
        final float n2 = (float)(a * 3.141592653589793 / 180.0);
        final PointF pointF = new PointF(0.0f, 0.0f);
        pointF.x = PointCalc.center(this.h[TuSDKPlasticFaceModel.A[0][0]], this.h[TuSDKPlasticFaceModel.A[4][0]]).x;
        pointF.y = PointCalc.center(this.h[TuSDKPlasticFaceModel.A[2][0]], this.h[TuSDKPlasticFaceModel.A[6][0]]).y;
        final PointF pointF2 = new PointF(0.0f, 0.0f);
        pointF2.x = PointCalc.center(this.h[TuSDKPlasticFaceModel.B[0][0]], this.h[TuSDKPlasticFaceModel.B[4][0]]).x;
        pointF2.y = PointCalc.center(this.h[TuSDKPlasticFaceModel.B[2][0]], this.h[TuSDKPlasticFaceModel.B[6][0]]).y;
        for (int i = 0; i < 8; ++i) {
            this.h[TuSDKPlasticFaceModel.A[i][0]] = PointCalc.rotate(this.h[TuSDKPlasticFaceModel.A[i][0]], pointF, n2);
            this.h[TuSDKPlasticFaceModel.B[i][0]] = PointCalc.rotate(this.h[TuSDKPlasticFaceModel.B[i][0]], pointF2, -n2);
        }
    }
    
    public void calcEyeDistance(final float n) {
        final float a = this.a(n, TuSDKPlasticType.TuSDK_EYE_DISTANCE) * this.t;
        if (Math.abs(a) < this.e) {
            return;
        }
        final float n2 = a * this.u;
        final float n3 = a * this.v;
        for (int i = 0; i < 8; ++i) {
            final PointF pointF = this.h[TuSDKPlasticFaceModel.A[i][0]];
            this.h[TuSDKPlasticFaceModel.A[i][0]] = PointCalc.extension(PointCalc.vertical(this.m, this.n, pointF), pointF, n2);
            final PointF pointF2 = this.h[TuSDKPlasticFaceModel.B[i][0]];
            this.h[TuSDKPlasticFaceModel.B[i][0]] = PointCalc.extension(PointCalc.vertical(this.m, this.n, pointF2), pointF2, n3);
        }
    }
    
    public void calcEyeHeight(final float n) {
        final float a = this.a(n, TuSDKPlasticType.TuSDK_EYE_HEIGHT) * this.t;
        if (Math.abs(a) < this.e) {
            return;
        }
        final PointF pointF = this.h[TuSDKPlasticFaceModel.x[3][0]];
        final PointF pointF2 = this.h[TuSDKPlasticFaceModel.x[13][0]];
        for (int i = 0; i < 8; ++i) {
            final PointF pointF3 = this.h[TuSDKPlasticFaceModel.A[i][0]];
            this.h[TuSDKPlasticFaceModel.A[i][0]] = PointCalc.extension(PointCalc.vertical(pointF, pointF2, pointF3), pointF3, a);
            final PointF pointF4 = this.h[TuSDKPlasticFaceModel.B[i][0]];
            this.h[TuSDKPlasticFaceModel.B[i][0]] = PointCalc.extension(PointCalc.vertical(pointF, pointF2, pointF4), pointF4, a);
        }
    }
    
    public void calcEyeInnerConerOpen(final float n) {
        final float a = -this.a(n, TuSDKPlasticType.TuSDK_EYE_INNER_CONER);
        if (Math.abs(a) < this.e) {
            return;
        }
        final PointF pointF = this.h[TuSDKPlasticFaceModel.C[0][0]];
        final PointF pointF2 = this.h[TuSDKPlasticFaceModel.C[3][0]];
        final PointF[] array = new PointF[8];
        for (int i = 0; i < 8; ++i) {
            array[i] = this.h[TuSDKPlasticFaceModel.A[i][0]];
        }
        final float distance = PointCalc.distance(array[2], array[6]);
        final PointF center = PointCalc.center(array[0], array[4]);
        final float distance2 = PointCalc.distance(center, array[2]);
        final float distance3 = PointCalc.distance(center, array[6]);
        final float distance4 = PointCalc.distance(array[0], array[4]);
        if (distance2 < distance) {
            if (distance3 < distance) {
                this.h[TuSDKPlasticFaceModel.A[4][0]] = PointCalc.extension(PointCalc.vertical(pointF, pointF2, array[4]), array[4], a * distance4);
            }
        }
        final PointF[] array2 = new PointF[8];
        for (int j = 0; j < 8; ++j) {
            array2[j] = this.h[TuSDKPlasticFaceModel.B[j][0]];
        }
        final float distance5 = PointCalc.distance(array2[2], array2[6]);
        final PointF center2 = PointCalc.center(array2[0], array2[4]);
        final float distance6 = PointCalc.distance(center2, array2[2]);
        final float distance7 = PointCalc.distance(center2, array2[6]);
        final float distance8 = PointCalc.distance(array2[0], array2[4]);
        if (distance6 < distance5) {
            if (distance7 < distance5) {
                this.h[TuSDKPlasticFaceModel.B[0][0]] = PointCalc.extension(PointCalc.vertical(pointF, pointF2, array2[0]), array2[0], a * distance8);
            }
        }
    }
    
    public void calcEyeOuterConerOpen(final float n) {
        final float a = this.a(n, TuSDKPlasticType.TuSDK_EYE_OUTER_CONER);
        if (Math.abs(a) < this.e) {
            return;
        }
        final PointF pointF = this.h[TuSDKPlasticFaceModel.C[0][0]];
        final PointF pointF2 = this.h[TuSDKPlasticFaceModel.C[3][0]];
        final PointF[] array = new PointF[8];
        for (int i = 0; i < 8; ++i) {
            array[i] = this.h[TuSDKPlasticFaceModel.A[i][0]];
        }
        final float distance = PointCalc.distance(array[2], array[6]);
        final PointF center = PointCalc.center(array[0], array[4]);
        final float distance2 = PointCalc.distance(center, array[2]);
        final float distance3 = PointCalc.distance(center, array[6]);
        final float distance4 = PointCalc.distance(array[0], array[4]);
        if (distance2 < distance) {
            if (distance3 < distance) {
                this.h[TuSDKPlasticFaceModel.A[0][0]] = PointCalc.extension(PointCalc.vertical(pointF, pointF2, array[0]), array[0], a * distance4);
            }
        }
        final PointF[] array2 = new PointF[8];
        for (int j = 0; j < 8; ++j) {
            array2[j] = this.h[TuSDKPlasticFaceModel.B[j][0]];
        }
        final float distance5 = PointCalc.distance(array2[2], array2[6]);
        final PointF center2 = PointCalc.center(array2[0], array2[4]);
        final float distance6 = PointCalc.distance(center2, array2[2]);
        final float distance7 = PointCalc.distance(center2, array2[6]);
        final float distance8 = PointCalc.distance(array2[0], array2[4]);
        if (distance6 < distance5) {
            if (distance7 < distance5) {
                this.h[TuSDKPlasticFaceModel.B[4][0]] = PointCalc.extension(PointCalc.vertical(pointF, pointF2, array2[4]), array2[4], a * distance8);
            }
        }
    }
    
    public void calcNoseWidth(final float n) {
        final float a = -this.a(n, TuSDKPlasticType.TuSDK_NOSE_WIDTH);
        if (Math.abs(a) < this.e) {
            return;
        }
        final PointF pointF = this.h[TuSDKPlasticFaceModel.C[0][0]];
        final PointF pointF2 = this.h[TuSDKPlasticFaceModel.C[3][0]];
        for (int i = 0; i < 15; ++i) {
            if (i != 0 && i != 1 && i != 2 && i != 3) {
                if (i != 6) {
                    final PointF pointF3 = this.h[TuSDKPlasticFaceModel.C[i][0]];
                    this.h[TuSDKPlasticFaceModel.C[i][0]] = PointCalc.extensionPercentage(PointCalc.vertical(pointF, pointF2, pointF3), pointF3, a);
                }
            }
        }
    }
    
    public void calcNoseHeight(final float n) {
        final float a = this.a(n, TuSDKPlasticType.TuSDK_NOSE_HEIGHT);
        if (Math.abs(a) < this.e) {
            return;
        }
        for (int i = 0; i < 15; ++i) {
            if (i != 0 && i != 9) {
                if (i != 10) {
                    final PointF pointF = this.h[TuSDKPlasticFaceModel.C[i][0]];
                    this.h[TuSDKPlasticFaceModel.C[i][0]] = PointCalc.extensionPercentage(PointCalc.vertical(this.r, this.s, pointF), pointF, a);
                }
            }
        }
    }
    
    public void calcMouthWidth(final float n) {
        final float a = this.a(n, TuSDKPlasticType.TuSDK_MOUTH_WIDTH);
        if (Math.abs(a) < this.e) {
            return;
        }
        final PointF pointF = this.h[TuSDKPlasticFaceModel.D[3][0]];
        final PointF pointF2 = this.h[TuSDKPlasticFaceModel.D[9][0]];
        for (int i = 0; i < 20; ++i) {
            if (i != 3 && i != 9 && i != 14) {
                if (i != 18) {
                    final PointF pointF3 = this.h[TuSDKPlasticFaceModel.D[i][0]];
                    this.h[TuSDKPlasticFaceModel.D[i][0]] = PointCalc.extensionPercentage(PointCalc.vertical(pointF, pointF2, pointF3), pointF3, a);
                }
            }
        }
    }
    
    public void calcMouthSize(final float n) {
        final float a = this.a(n, TuSDKPlasticType.TuSDK_MOUTH_SIZE);
        if (Math.abs(a) < this.e) {
            return;
        }
        final PointF pointF = new PointF(0.0f, 0.0f);
        for (int i = 0; i < 20; ++i) {
            final PointF pointF2 = pointF;
            pointF2.x += this.h[TuSDKPlasticFaceModel.D[i][0]].x / 20.0f;
            final PointF pointF3 = pointF;
            pointF3.y += this.h[TuSDKPlasticFaceModel.D[i][0]].y / 20.0f;
        }
        for (int j = 0; j < 20; ++j) {
            this.h[TuSDKPlasticFaceModel.D[j][0]] = PointCalc.extensionPercentage(pointF, this.h[TuSDKPlasticFaceModel.D[j][0]], a);
        }
    }
    
    public void calcLipsThickness(final float n) {
        final float a = this.a(n, TuSDKPlasticType.TuSDK_LIPS_THICKNESS);
        if (Math.abs(a) < this.e) {
            return;
        }
        final PointF pointF = this.h[TuSDKPlasticFaceModel.D[0][0]];
        final PointF pointF2 = this.h[TuSDKPlasticFaceModel.D[6][0]];
        for (int i = 0; i < 5; ++i) {
            final int n2 = i + 1;
            final PointF pointF3 = this.h[TuSDKPlasticFaceModel.D[n2][0]];
            final PointF vertical = PointCalc.vertical(pointF, pointF2, pointF3);
            this.h[TuSDKPlasticFaceModel.D[n2][0]] = PointCalc.extension(vertical, pointF3, a * PointCalc.distance(vertical, pointF3));
            final int n3 = i + 7;
            final PointF pointF4 = this.h[TuSDKPlasticFaceModel.D[n3][0]];
            final PointF vertical2 = PointCalc.vertical(pointF, pointF2, pointF4);
            this.h[TuSDKPlasticFaceModel.D[n3][0]] = PointCalc.extension(vertical2, pointF4, a * PointCalc.distance(vertical2, pointF4));
        }
    }
    
    public void calcPhiltrumThickness(final float n) {
        final float a = this.a(n, TuSDKPlasticType.TuSDK_PHILTRUM_THICKNESS);
        if (Math.abs(a) < this.e) {
            return;
        }
        final float n2 = a * PointCalc.distance(this.h[TuSDKPlasticFaceModel.C[6][0]], this.h[TuSDKPlasticFaceModel.D[3][0]]);
        for (int i = 4; i < 12; ++i) {
            final PointF pointF = this.h[TuSDKPlasticFaceModel.x[i][0]];
            this.h[TuSDKPlasticFaceModel.x[i][0]] = PointCalc.extension(PointCalc.vertical(this.r, this.s, pointF), pointF, n2);
        }
        for (int j = 0; j < 20; ++j) {
            final PointF pointF2 = this.h[TuSDKPlasticFaceModel.D[j][0]];
            this.h[TuSDKPlasticFaceModel.D[j][0]] = PointCalc.extension(PointCalc.vertical(this.r, this.s, pointF2), pointF2, n2);
        }
    }
    
    public void calcChinThickness(final float n) {
        final float a = this.a(n, TuSDKPlasticType.TuSDK_CHIN_THICKNESS);
        if (Math.abs(a) < this.e) {
            return;
        }
        final PointF pointF = this.h[TuSDKPlasticFaceModel.C[6][0]];
        this.h[TuSDKPlasticFaceModel.x[5][0]] = PointCalc.extensionPercentage(pointF, this.h[TuSDKPlasticFaceModel.x[5][0]], a * 0.1f);
        this.h[TuSDKPlasticFaceModel.x[6][0]] = PointCalc.extensionPercentage(pointF, this.h[TuSDKPlasticFaceModel.x[6][0]], a * 0.25f);
        this.h[TuSDKPlasticFaceModel.x[7][0]] = PointCalc.extensionPercentage(pointF, this.h[TuSDKPlasticFaceModel.x[7][0]], a * 0.45f);
        this.h[TuSDKPlasticFaceModel.x[8][0]] = PointCalc.extensionPercentage(pointF, this.h[TuSDKPlasticFaceModel.x[8][0]], a * 0.7f);
        this.h[TuSDKPlasticFaceModel.x[9][0]] = PointCalc.extensionPercentage(pointF, this.h[TuSDKPlasticFaceModel.x[9][0]], a * 0.45f);
        this.h[TuSDKPlasticFaceModel.x[10][0]] = PointCalc.extensionPercentage(pointF, this.h[TuSDKPlasticFaceModel.x[10][0]], a * 0.25f);
        this.h[TuSDKPlasticFaceModel.x[11][0]] = PointCalc.extensionPercentage(pointF, this.h[TuSDKPlasticFaceModel.x[11][0]], a * 0.1f);
    }

    public List<PointF> getPoints() {
        ArrayList var1 = new ArrayList();
        var1.addAll(Arrays.asList(this.h));
        return var1;
    }
    
    public float[] getPoints(final boolean b) {
        final float[] array = new float[190];
        if (b) {
            for (int i = 0; i < 95; ++i) {
                final PointF normalize = PointCalc.normalize(this.h[i], this.f);
                array[i * 2] = normalize.x * 2.0f - 1.0f;
                array[i * 2 + 1] = normalize.y * 2.0f - 1.0f;
            }
        }
        else {
            for (int j = 0; j < 95; ++j) {
                final PointF normalize2 = PointCalc.normalize(this.h[j], this.f);
                array[j * 2] = normalize2.x;
                array[j * 2 + 1] = normalize2.y;
            }
        }
        return array;
    }
    
    static {
        w = new int[] { 86, 87, 88, 89, 90, 91, 92, 93, 94 };
        x = new int[][] { { 0, 0 }, { 1, 2 }, { 2, 4 }, { 3, 6 }, { 4, 8 }, { 5, 10 }, { 6, 12 }, { 7, 14 }, { 8, 16 }, { 9, 18 }, { 10, 20 }, { 11, 22 }, { 12, 24 }, { 13, 26 }, { 14, 28 }, { 15, 30 }, { 16, 32 } };
        y = new int[][] { { 17, 33 }, { 18, 34 }, { 19, 35 }, { 20, 36 }, { 21, 37 }, { 51, 67 }, { 50, 66 }, { 49, 65 }, { 48, 64 } };
        z = new int[][] { { 22, 38 }, { 23, 39 }, { 24, 40 }, { 25, 41 }, { 26, 42 }, { 55, 71 }, { 54, 70 }, { 53, 69 }, { 52, 68 } };
        A = new int[][] { { 36, 52 }, { 37, 53 }, { 56, 72 }, { 38, 54 }, { 39, 55 }, { 40, 56 }, { 57, 73 }, { 41, 57 } };
        B = new int[][] { { 42, 58 }, { 43, 59 }, { 58, 75 }, { 44, 60 }, { 45, 61 }, { 46, 62 }, { 59, 76 }, { 47, 63 } };
        C = new int[][] { { 27, 43 }, { 28, 44 }, { 29, 45 }, { 30, 46 }, { 31, 47 }, { 32, 48 }, { 33, 49 }, { 34, 50 }, { 35, 51 }, { 60, 78 }, { 61, 79 }, { 62, 80 }, { 63, 81 }, { 64, 82 }, { 65, 83 } };
        D = new int[][] { { 66, 84 }, { 67, 85 }, { 68, 86 }, { 69, 87 }, { 70, 88 }, { 71, 89 }, { 72, 90 }, { 73, 91 }, { 74, 92 }, { 75, 93 }, { 76, 94 }, { 77, 95 }, { 78, 96 }, { 79, 97 }, { 80, 98 }, { 81, 99 }, { 82, 100 }, { 83, 101 }, { 84, 102 }, { 85, 103 } };
        E = new int[][] { { 5, 10 }, { 6, 12 }, { 7, 14 }, { 8, 16 }, { 9, 18 }, { 10, 20 }, { 11, 22 } };
    }
}
