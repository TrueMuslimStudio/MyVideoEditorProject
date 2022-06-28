package com.example.myvideoeditorapp.tv.core.detector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.example.myvideoeditorapp.kore.TuSdkBundle;
import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.utils.NativeLibraryHelper;
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;

import java.nio.Buffer;

public class TuSdkFaceDetector {
    public static final String BUILD_VERSION = "3.0.5-202101141553";
    private static boolean a;

    public TuSdkFaceDetector() {
    }

    public static void setMarkSence(boolean var0) {
        if (var0 != a) {
            a = var0;
        }

    }

    public static void init() {
        initJNI(TuSdkContext.context(), TuSdkBundle.sdkBundleModel("lsq_tutucloud_dl_landmark_6.0.0.dpm"), TuSdkBundle.sdkBundleModel("lsq_tutucloud_dl_eye_1.0.0.dpm"), TuSdkBundle.sdkBundleModel("lsq_tutucloud_dl_mouth_1.0.0.dpm"));
    }

    public static void setDetectScale(float var0) {
        setDetectScaleJNI(var0);
    }

    public static FaceAligment[] markFace(Bitmap var0, boolean var1) {
        return markFace(var0, var1, 1);
    }

    public static FaceAligment[] markFace(Bitmap var0, boolean var1, int var2) {
        if (var0 != null && !var0.isRecycled()) {
            Bitmap var3 = a(var0);
            FaceAligment[] var4 = markFaceJNI(var3, a, var2);
            BitmapHelper.recycled(var3);
            return var4;
        } else {
            return null;
        }
    }

    private static Bitmap a(Bitmap var0) {
        if (var0.getConfig() == Bitmap.Config.RGB_565 && var0.getWidth() <= 512 && var0.getHeight() <= 512) {
            return var0;
        } else {
            float var1 = 512.0F / (float)Math.max(var0.getWidth(), var0.getHeight());
            int var2 = (int)((float)var0.getWidth() * var1);
            int var3 = (int)((float)var0.getHeight() * var1);
            Bitmap var4 = Bitmap.createBitmap(var2, var3, Bitmap.Config.RGB_565);
            Canvas var5 = new Canvas(var4);
            Paint var6 = new Paint();
            Matrix var7 = new Matrix();
            var7.postScale(var1, var1);
            var5.drawBitmap(var0, var7, var6);
            return var4;
        }
    }

    public static FaceAligment[] markFaceVideo(int var0, int var1, double var2, boolean var4, Buffer var5) {
        if (var0 >= 4 && var1 >= 4) {
            FaceAligment[] var6 = markFaceWithBufferJNI(var0, var1, var2, a, var5);
            return var6;
        } else {
            return null;
        }
    }

    public static FaceAligment[] markFaceGrayImage(int var0, int var1, int var2, double var3, boolean var5, byte[] var6) {
        if (var0 >= 1 && var1 >= 1 && var6 != null && var6.length == var0 * var1) {
            FaceAligment[] var7 = markFaceWithGrayImageJNI(var0, var1, var2, var3, var5, var6);
            return var7;
        } else {
            return null;
        }
    }

    private static native void initJNI(Context var0, String var1, String var2, String var3);

    private static native void setDetectScaleJNI(float var0);

    private static native FaceAligment[] markFaceJNI(Bitmap var0, boolean var1, int var2);

    private static native FaceAligment[] markFaceWithBufferJNI(int var0, int var1, double var2, boolean var4, Buffer var5);

    private static native FaceAligment[] markFaceWithGrayImageJNI(int var0, int var1, int var2, double var3, boolean var5, byte[] var6);

    static {
        NativeLibraryHelper.shared().loadLibrary(NativeLibraryHelper.NativeLibType.LIB_FACE);
        a = false;
    }
}
