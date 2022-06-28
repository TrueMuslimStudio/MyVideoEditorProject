// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.utils.calc;

import com.example.myvideoeditorapp.kore.utils.TLog;
import java.util.ArrayList;
import java.util.List;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import android.graphics.RectF;
import android.graphics.PointF;

public class PointCalc
{
    public static float distance(final PointF pointF, final PointF pointF2) {
        return (float)Math.sqrt(Math.pow(pointF.x - pointF2.x, 2.0) + Math.pow(pointF.y - pointF2.y, 2.0));
    }
    
    public static PointF center(final PointF pointF, final PointF pointF2) {
        final PointF pointF3 = new PointF();
        pointF3.x = (pointF.x + pointF2.x) * 0.5f;
        pointF3.y = (pointF.y + pointF2.y) * 0.5f;
        return pointF3;
    }
    
    public static PointF crossPoint(final PointF pointF, final PointF pointF2, final PointF pointF3, final PointF pointF4) {
        final PointF pointF5 = new PointF();
        pointF5.set((pointF.x + pointF2.x + pointF3.x + pointF4.x) * 0.25f, (pointF.y + pointF2.y + pointF3.y + pointF4.y) * 0.25f);
        final float n = pointF2.x - pointF.x;
        final float n2 = pointF3.x - pointF4.x;
        final float n3 = pointF2.y - pointF.y;
        final float n4 = pointF3.y - pointF4.y;
        final float n5 = pointF3.x - pointF.x;
        final float n6 = pointF3.y - pointF.y;
        final float a = n * n4 - n2 * n3;
        if (Math.abs(a) < 1.0E-6) {
            return pointF5;
        }
        final float n7 = (n4 * n5 - n2 * n6) / a;
        final float n8 = (-n3 * n5 + n * n6) / a;
        if (0.0f > n7 || n7 > 1.0f || 0.0f < n8 || n8 > 1.0f) {
            return pointF5;
        }
        pointF5.x = pointF.x + n7 * (pointF2.x - pointF.x);
        pointF5.y = pointF.y + n7 * (pointF2.y - pointF.y);
        return pointF5;
    }
    
    public static PointF extension(final PointF pointF, final PointF pointF2, final float n) {
        final PointF pointF3 = new PointF();
        final float n2 = n / distance(pointF, pointF2);
        pointF3.set(pointF2.x + (pointF2.x - pointF.x) * n2, pointF2.y + (pointF2.y - pointF.y) * n2);
        return pointF3;
    }
    
    public static PointF extensionPercentage(final PointF pointF, final PointF pointF2, final float n) {
        final PointF pointF3 = new PointF();
        pointF3.set(pointF2.x + (pointF2.x - pointF.x) * n, pointF2.y + (pointF2.y - pointF.y) * n);
        return pointF3;
    }
    
    public static PointF percentage(final PointF pointF, final PointF pointF2, final float n) {
        final PointF pointF3 = new PointF();
        pointF3.set(pointF.x + (pointF2.x - pointF.x) * n, pointF.y + (pointF2.y - pointF.y) * n);
        return pointF3;
    }
    
    public static RectF circle(final PointF pointF, final PointF pointF2, final PointF pointF3) {
        final RectF rectF = new RectF();
        final float n = pointF.x - pointF2.x;
        final float n2 = pointF.y - pointF2.y;
        final float n3 = (float)((Math.pow(pointF.x, 2.0) - Math.pow(pointF2.x, 2.0) + Math.pow(pointF.y, 2.0) - Math.pow(pointF2.y, 2.0)) / 2.0);
        final float n4 = pointF3.x - pointF2.x;
        final float n5 = pointF3.y - pointF2.y;
        final float n6 = (float)((Math.pow(pointF3.x, 2.0) - Math.pow(pointF2.x, 2.0) + Math.pow(pointF3.y, 2.0) - Math.pow(pointF2.y, 2.0)) / 2.0);
        final float n7 = n * n5 - n4 * n2;
        float n8 = 0.0f;
        float x;
        float y;
        if (n7 == 0.0f) {
            x = pointF.x;
            y = pointF.y;
        }
        else {
            x = (n3 * n5 - n6 * n2) / n7;
            y = (n * n6 - n4 * n3) / n7;
            n8 = (float)Math.sqrt(Math.pow(x - pointF.x, 2.0) + Math.pow(y - pointF.y, 2.0));
        }
        rectF.set(x, y, n8, n8);
        return rectF;
    }
    
    public static PointF rotate(final PointF pointF, final PointF pointF2, final float n) {
        final PointF pointF3 = new PointF();
        final float distance = distance(pointF, pointF2);
        final float n2 = (float)(Math.sin(n) * distance);
        final float n3 = (float)(Math.cos(n) * distance);
        pointF3.set((pointF2.x * n2 / n3 + pointF.x * n3 / n2 + pointF.y - pointF2.y) / (n2 / n3 + n3 / n2), (pointF2.y * n2 / n3 + pointF.y * n3 / n2 + pointF2.x - pointF.x) / (n2 / n3 + n3 / n2));
        return pointF3;
    }
    
    public static PointF vertical(final PointF pointF, final PointF pointF2, final PointF pointF3) {
        final PointF pointF4 = new PointF();
        if (pointF.y == pointF2.y) {
            pointF4.x = pointF3.x;
            pointF4.y = pointF.y;
        }
        else if (pointF.x == pointF2.x) {
            pointF4.x = pointF.x;
            pointF4.y = pointF3.y;
        }
        else {
            final float n = (pointF.y - pointF2.y) / (pointF.x - pointF2.x);
            final float n2 = pointF.y - n * pointF.x;
            final float n3 = -1.0f / n;
            final float n4 = pointF3.y - n3 * pointF3.x;
            final float x = (n4 - n2) / (n - n3);
            final float y = n3 * x + n4;
            pointF4.x = x;
            pointF4.y = y;
        }
        return pointF4;
    }
    
    public static PointF minus(final PointF pointF, final PointF pointF2) {
        final PointF pointF3 = new PointF();
        pointF3.set(pointF.x - pointF2.x, pointF.y - pointF2.y);
        return pointF3;
    }
    
    public static PointF add(final PointF pointF, final PointF pointF2) {
        final PointF pointF3 = new PointF();
        pointF3.set(pointF.x + pointF2.x, pointF.y + pointF2.y);
        return pointF3;
    }
    
    public static PointF real(final PointF pointF, final TuSdkSize tuSdkSize) {
        final PointF pointF2 = new PointF();
        pointF2.set(pointF.x * tuSdkSize.width, pointF.y * tuSdkSize.height);
        return pointF2;
    }
    
    public static PointF normalize(final PointF pointF, final TuSdkSize tuSdkSize) {
        final PointF pointF2 = new PointF();
        pointF2.set(pointF.x / tuSdkSize.width, pointF.y / tuSdkSize.height);
        return pointF2;
    }
    
    public static float smoothstep(final float n, final float n2, final float n3) {
        if (n3 < n) {
            return 0.0f;
        }
        if (n3 >= n2) {
            return 1.0f;
        }
        final float n4 = (n3 - n) / (n2 - n);
        return n4 * n4 * (3.0f - 2.0f * n4);
    }
    
    public static List<PointF> pointerInsert(final PointF pointF, final PointF pointF2, final PointF pointF3, final int n, final boolean b) {
        Object o = new ArrayList<PointF>();
        final RectF circle = circle(pointF, pointF2, pointF3);
        final PointF pointF4 = new PointF(circle.left, circle.top);
        final float right = circle.right;
        if (right == 0.0f) {
            for (int i = 1; i < n + 1; ++i) {
                ((List<PointF>)o).add(new PointF(pointF.x + 1.0f * (pointF2.x - pointF.x) * i / (n + 1), pointF.y + 1.0f * (pointF2.y - pointF.y) * i / (n + 1)));
            }
        }
        else {
            float n2 = (float)(Math.asin(distance(pointF, new PointF((pointF.x + pointF2.x) * 0.5f, (pointF.y + pointF2.y) * 0.5f)) / right) * 2.0);
            final float n3 = (pointF2.x - pointF.x) * (pointF3.y - pointF.y) - (pointF2.y - pointF.y) * (pointF3.x - pointF.x);
            if (n3 > 0.0f) {
                n2 = -n2;
            }
            else if (n3 < 0.0f) {}
            for (int j = 1; j < n + 1; ++j) {
                final PointF pointF5 = new PointF();
                final float n4 = n2 * j / (n + 1);
                pointF5.x = (float)(pointF4.x + (pointF.x - pointF4.x) * Math.cos(n4) + (pointF.y - pointF4.y) * Math.sin(n4));
                pointF5.y = (float)(pointF4.y + (pointF.y - pointF4.y) * Math.cos(n4) - (pointF.x - pointF4.x) * Math.sin(n4));
                ((List<PointF>)o).add(pointF5);
            }
        }
        if (b) {
            final ArrayList<Object> list = new ArrayList<Object>();
            for (int k = ((List)o).size() - 1; k >= 0; --k) {
                list.add(((List<PointF>)o).get(k));
            }
            o = list;
        }
        return (List<PointF>)o;
    }
    
    public static PointF pointerInsert(final PointF pointF, final PointF pointF2, final PointF pointF3) {
        final List<PointF> pointerInsert = pointerInsert(pointF, pointF2, pointF3, 1, false);
        if (pointerInsert.size() == 0) {
            TLog.e("PointCalc pointerInsert return null.", new Object[0]);
            return null;
        }
        return pointerInsert.get(0);
    }
    
    public static PointF boundCornerPoint(final PointF pointF, final PointF pointF2, final PointF pointF3) {
        final PointF pointF4 = new PointF();
        final float n = pointF.x - pointF2.x;
        final float n2 = pointF.y - pointF2.y;
        pointF4.x = (n * n * pointF2.x + n2 * n2 * pointF3.x + n * n2 * (pointF2.y - pointF3.y)) / (n * n + n2 * n2);
        pointF4.y = (n * n * pointF3.y + n2 * n2 * pointF2.y + n * n2 * (pointF2.x - pointF3.x)) / (n * n + n2 * n2);
        return pointF4;
    }
    
    public static List<PointF> footPoint(final PointF pointF, final PointF pointF2, final float n) {
        final float n2 = pointF.x - pointF2.x;
        final float n3 = pointF.y - pointF2.y;
        final float n4 = (float)(pointF.x + n * n3 / Math.sqrt(n2 * n2 + n3 * n3));
        final float n5 = (float)(pointF.y - n * n2 / Math.sqrt(n2 * n2 + n3 * n3));
        final float n6 = (float)(pointF.x - n * n3 / Math.sqrt(n2 * n2 + n3 * n3));
        final float n7 = (float)(pointF.y + n * n2 / Math.sqrt(n2 * n2 + n3 * n3));
        final ArrayList<PointF> list = new ArrayList<PointF>();
        if (n7 > n5) {
            list.add(new PointF(n4, n5));
            list.add(new PointF(n6, n7));
        }
        else if (n5 > n7) {
            list.add(new PointF(n6, n7));
            list.add(new PointF(n4, n5));
        }
        else if (n6 > n4) {
            list.add(new PointF(n4, n5));
            list.add(new PointF(n6, n7));
        }
        else {
            list.add(new PointF(n6, n7));
            list.add(new PointF(n4, n5));
        }
        return list;
    }
    
    public static List<PointF> verticalPointsByDistance(final PointF pointF, final PointF pointF2, final float n) {
        PointF extension = new PointF();
        PointF extension2 = new PointF();
        if (pointF.y == pointF2.y) {
            extension.x = pointF.x;
            extension.y = pointF.y - n;
            extension2.x = pointF.x;
            extension2.y = pointF.y + n;
        }
        else if (pointF.x == pointF2.x) {
            extension.x = pointF.x - n;
            extension.y = pointF.y;
            extension2.x = pointF.x + n;
            extension2.y = pointF.y;
        }
        else {
            final float n2 = (pointF.y - pointF2.y) / (pointF.x - pointF2.x);
            final float n3 = pointF.y - n2 * pointF.x;
            final float n4 = -1.0f / n2;
            final float n5 = pointF.y - n4 * pointF.x;
            final PointF pointF3 = new PointF();
            pointF3.x = pointF.x + 1.0f;
            pointF3.y = n4 * pointF3.x + n5;
            extension = extension(pointF, pointF3, n - distance(pointF, pointF3));
            pointF3.x = pointF.x - 1.0f;
            pointF3.y = n4 * pointF3.x + n5;
            extension2 = extension(pointF, pointF3, n - distance(pointF, pointF3));
        }
        final ArrayList<PointF> list = new ArrayList<PointF>();
        list.add(extension);
        list.add(extension2);
        return list;
    }
}
