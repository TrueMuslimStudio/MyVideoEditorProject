package com.example.myvideoeditorapp.kore.utils.image;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build.VERSION;
import android.widget.ImageView;

import com.example.myvideoeditorapp.kore.secret.TuSdkImageNative;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.AssetsHelper;
import com.example.myvideoeditorapp.kore.utils.ContextUtils;
import com.example.myvideoeditorapp.kore.utils.FileHelper;
import com.example.myvideoeditorapp.kore.utils.RectHelper;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkGPU;
import com.example.myvideoeditorapp.kore.utils.sqllite.ImageSqlInfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapHelper {
    public BitmapHelper() {
    }

    public static Options getDefaultOptions() {
        return getDefaultOptions(false);
    }

    public static Options getDefaultOptions(boolean var0) {
        Options var1 = new Options();
        var1.inJustDecodeBounds = false;
        var1.inDither = false;
        if (VERSION.SDK_INT < 21) {
            a(var1);
        }

        var1.inPreferredConfig = var0 ? Config.ARGB_8888 : Config.RGB_565;
        return var1;
    }

    private static void a(Options var0) {
        var0.inPurgeable = true;
        var0.inInputShareable = true;
    }

    public static TuSdkSize getBitmapSize(File var0) {
        if (var0 != null && var0.exists() && var0.isFile()) {
            Options var1 = getDefaultOptions();
            var1.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(var0.getAbsolutePath(), var1);
            TuSdkSize var2 = new TuSdkSize(var1.outWidth, var1.outHeight);
            return var2;
        } else {
            return null;
        }
    }

    public static Bitmap getAssetsBitmap(Context var0, String var1) {
        InputStream var2 = AssetsHelper.getAssetsStream(var0, var1);
        return var2 == null ? null : BitmapFactory.decodeStream(var2);
    }

    public static Bitmap getRawBitmap(Context var0, int var1) {
        InputStream var2 = ContextUtils.getRawStream(var0, var1);
        return var2 == null ? null : BitmapFactory.decodeStream(var2);
    }

    public static Bitmap getBitmapFormRaw(Context var0, int var1) {
        InputStream var2 = ContextUtils.getRawStream(var0, var1);
        if (var2 == null) {
            return null;
        } else {
            Bitmap var3 = BitmapFactory.decodeStream(var2);
            Bitmap var4 = Bitmap.createBitmap(var3.getWidth(), var3.getHeight(), Config.ARGB_8888);
            Canvas var5 = new Canvas(var4);
            Paint var6 = new Paint();
            var5.drawBitmap(var3, 0.0F, 0.0F, var6);
            return var4;
        }
    }

    public static Drawable getResDrawable(Context var0, int var1) {
        if (var0 != null && var1 != 0) {
            Drawable var2 = var0.getResources().getDrawable(var1);
            if (var2 != null) {
                var2.setBounds(0, 0, var2.getMinimumWidth(), var2.getMinimumHeight());
            }

            return var2;
        } else {
            return null;
        }
    }

    public static Bitmap getDrawableBitmap(ImageView var0) {
        if (var0 == null) {
            return null;
        } else {
            Drawable var1 = var0.getDrawable();
            return var1 != null && var1 instanceof BitmapDrawable ? ((BitmapDrawable)var1).getBitmap() : null;
        }
    }

    public static Bitmap changeColor(Bitmap var0, int var1) {
        if (var0 == null) {
            return null;
        } else {
            Bitmap var2 = Bitmap.createBitmap(var0.getWidth(), var0.getHeight(), Config.ARGB_8888);
            Canvas var3 = new Canvas(var2);
            Paint var4 = new Paint();
            var4.setColor(var1);
            Bitmap var5 = var0.extractAlpha();
            var3.drawBitmap(var5, 0.0F, 0.0F, var4);
            return var2;
        }
    }

    public static Bitmap changeAlpha(Bitmap var0, float var1) {
        if (var0 == null) {
            return null;
        } else {
            Bitmap var2 = Bitmap.createBitmap(var0.getWidth(), var0.getHeight(), Config.ARGB_8888);
            Canvas var3 = new Canvas(var2);
            Paint var4 = new Paint();
            var4.setAlpha((int)(255.0F * var1));
            var3.drawBitmap(var0, 0.0F, 0.0F, var4);
            return var2;
        }
    }

    public static Bitmap getBitmap(File var0) {
        return getBitmap(var0, true);
    }

    public static Bitmap getBitmap(File var0, boolean var1) {
        if (var0 != null && var0.exists()) {
            Options var2 = getDefaultOptions(var1);
            Bitmap var3 = decodeFileDescriptor(var0, var2);
            return var3;
        } else {
            return null;
        }
    }

    public static Bitmap getBitmap(File var0, float var1) {
        return getBitmap(var0, var1, false);
    }

    public static Bitmap getBitmap(File var0, float var1, boolean var2) {
        if (var1 >= 1.0F) {
            return getBitmap(var0);
        } else if (var0 != null && var0.exists() && !(var1 <= 0.0F)) {
            Options var3 = getDefaultOptions(var2);
            var3.inJustDecodeBounds = true;
            Bitmap var4 = BitmapFactory.decodeFile(var0.getAbsolutePath(), var3);
            TuSdkSize var5 = new TuSdkSize((int)((float)var3.outWidth * var1), (int)((float)var3.outHeight * var1));
            var3.inSampleSize = a(var3, var5, true);
            var3.inJustDecodeBounds = false;
            var4 = decodeFileDescriptor(var0, var3);
            return imageResize(var4, var5, false);
        } else {
            return null;
        }
    }

    private static int a(Options var0, TuSdkSize var1, boolean var2) {
        if (var1 == null) {
            return 1;
        } else {
            TuSdkSize var3 = TuSdkSize.create(var0.outWidth, var0.outHeight);
            float var4 = 1.0F;
            if (var2) {
                var4 = (float)var3.maxSide() / (float)var1.maxSide();
            } else {
                var4 = (float)var3.minSide() / (float)var1.maxSide();
            }

            int var5 = (int)Math.floor((double)var4);
            if (var5 < 1) {
                var5 = 1;
            }

            return var5;
        }
    }

    public static Bitmap getBitmap(File var0, TuSdkSize var1) {
        return getBitmap(var0, var1, false);
    }

    public static Bitmap getBitmap(File var0, TuSdkSize var1, boolean var2) {
        return getBitmap(var0, var1, 0, true, var2);
    }

    public static Bitmap getBitmap(ImageSqlInfo var0) {
        return getBitmap(var0, false);
    }

    public static Bitmap getBitmap(ImageSqlInfo var0, boolean var1) {
        if (var0 == null) {
            return null;
        } else {
            File var2 = new File(var0.path);
            Bitmap var3 = getBitmap(var2, var1);
            var3 = imageRotaing(var3, ImageOrientation.getValue(var0.orientation, false));
            return var3;
        }
    }

    public static Bitmap getBitmap(ImageSqlInfo var0, int var1) {
        return getBitmap(var0, new TuSdkSize(var1, var1), true);
    }

    public static Bitmap getBitmap(ImageSqlInfo var0, TuSdkSize var1) {
        return getBitmap(var0, var1, true);
    }

    public static Bitmap getBitmap(ImageSqlInfo var0, int var1, boolean var2) {
        return getBitmap(var0, new TuSdkSize(var1, var1), var2);
    }

    public static Bitmap getBitmap(ImageSqlInfo var0, TuSdkSize var1, boolean var2) {
        if (var0 != null && var0.path != null) {
            File var3 = new File(var0.path);
            return getBitmap(var3, var1, var0.orientation, var2);
        } else {
            return null;
        }
    }

    public static Bitmap getBitmap(File var0, TuSdkSize var1, int var2, boolean var3) {
        return getBitmap(var0, var1, var2, var3, false);
    }

    public static Bitmap getBitmap(ImageSqlInfo var0, boolean var1, int var2) {
        return getBitmap(var0, var1, new TuSdkSize(var2, var2));
    }

    public static Bitmap getBitmap(ImageSqlInfo var0, boolean var1, TuSdkSize var2) {
        return getBitmap(var0, var1, var2, true);
    }

    public static Bitmap getBitmap(ImageSqlInfo var0, boolean var1, TuSdkSize var2, boolean var3) {
        if (var0 != null && var0.path != null) {
            File var4 = new File(var0.path);
            return getBitmap(var4, var2, var0.orientation, var3, var1);
        } else {
            return null;
        }
    }

    public static Bitmap getBitmap(File var0, TuSdkSize var1, int var2, boolean var3, boolean var4) {
        if (var0 != null && var0.exists()) {
            Options var5 = getDefaultOptions(var4);
            var5.inJustDecodeBounds = true;
            var5.inSampleSize = a(var5, var1, var3);
            var5.inJustDecodeBounds = false;
            Bitmap var6 = decodeFileDescriptor(var0, var5);
            return imageResize(var6, var1, var3, ImageOrientation.getValue(var2, false));
        } else {
            return null;
        }
    }

    public static Bitmap createOvalImage(int var0, int var1, int var2) {
        Bitmap var3 = Bitmap.createBitmap(var0, var1, Config.ARGB_8888);
        Canvas var4 = new Canvas(var3);
        Paint var5 = new Paint();
        var5.setAntiAlias(true);
        var4.drawARGB(0, 0, 0, 0);
        var5.setColor(var2);
        RectF var6 = new RectF();
        var6.left = 0.0F;
        var6.top = 0.0F;
        var6.right = (float)var0;
        var6.bottom = (float)var1;
        var4.drawOval(var6, var5);
        return var3;
    }

    public static Bitmap decodeFileDescriptor(File var0, Options var1) {
        if (var0 != null && var0.exists()) {
            FileInputStream var2 = null;
            Bitmap var3 = null;

            try {
                var2 = new FileInputStream(var0);
                var3 = BitmapFactory.decodeStream(var2, (Rect)null, var1);
            } catch (OutOfMemoryError var9) {
                TLog.e(var9, "decodeFileDescriptor: %s", new Object[]{var9.getMessage()});
            } catch (FileNotFoundException var10) {
                TLog.e(var10, "decodeFileDescriptor: %s", new Object[]{var10.getMessage()});
            } finally {
                FileHelper.safeClose(var2);
            }

            return var3;
        } else {
            return null;
        }
    }

    public static Bitmap imageDecode(byte[] var0, boolean var1) {
        if (var0 == null) {
            return null;
        } else {
            ByteArrayInputStream var2 = new ByteArrayInputStream(var0);
            Bitmap var3 = BitmapFactory.decodeStream(var2, (Rect)null, getDefaultOptions(var1));
            return var3;
        }
    }

    public static void recycled(Bitmap var0) {
        if (var0 != null && !var0.isRecycled()) {
            var0.recycle();
        }
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap var0, int var1) {
        Bitmap var2 = Bitmap.createBitmap(var0.getWidth(), var0.getHeight(), Config.ARGB_8888);
        Canvas var3 = new Canvas(var2);
        int var4 = -12434878;
        Paint var5 = new Paint();
        Rect var6 = new Rect(0, 0, var0.getWidth(), var0.getHeight());
        RectF var7 = new RectF(var6);
        float var8 = (float)var1;
        var5.setAntiAlias(true);
        var3.drawARGB(0, 0, 0, 0);
        var5.setColor(-12434878);
        var3.drawRoundRect(var7, var8, var8, var5);
        var5.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        var3.drawBitmap(var0, var6, var6, var5);
        return var2;
    }

    @TargetApi(26)
    public static Bitmap mergeAbove(Bitmap var0, Bitmap var1) {
        if (var0 != null && var1 != null) {
            if (var1.getHeight() > var0.getHeight() && var1.getWidth() > var0.getWidth()) {
                var0 = imageScale(var0, var1.getWidth(), var1.getHeight());
            }

            int var2 = (var1.getHeight() - var0.getHeight()) / 2;
            int var3 = (var1.getWidth() - var0.getWidth()) / 2;
            Bitmap var4 = Bitmap.createBitmap(var1.getWidth(), var1.getHeight(), Config.ARGB_8888);
            Canvas var5 = new Canvas(var4);
            var5.drawBitmap(var0, (float)var3, (float)var2, (Paint)null);
            var5.drawBitmap(var1, 0.0F, 0.0F, (Paint)null);
            var5.save();
            var5.restore();
            return var4;
        } else {
            return null;
        }
    }

    public static Bitmap imageScale(Bitmap var0, int var1, int var2) {
        int var3 = var0.getWidth();
        int var4 = var0.getHeight();
        float var5 = (float)var1 / (float)var3;
        float var6 = (float)var2 / (float)var4;
        Matrix var7 = new Matrix();
        var7.postScale(var5, var6);
        Bitmap var8 = Bitmap.createBitmap(var0, 0, 0, var3, var4, var7, true);
        return var8;
    }

    public static Bitmap imageScale(Bitmap var0, float var1) {
        if (var0 != null && var1 != 1.0F && !(var1 <= 0.0F)) {
            Matrix var2 = new Matrix();
            var2.postScale(var1, var1);
            Bitmap var3 = Bitmap.createBitmap(var0, 0, 0, var0.getWidth(), var0.getHeight(), var2, true);
            return var3;
        } else {
            return var0;
        }
    }

    public static Bitmap imageLimit(Bitmap var0, int var1) {
        TuSdkSize var2 = TuSdkSize.create(var0);
        if (var2 == null) {
            return var0;
        } else {
            if (var1 > 0) {
                var0 = imageScale(var0, (float)var1 / (float)var2.maxSide());
            }

            return var0;
        }
    }

    public static Bitmap imageRotaing(Bitmap var0, ImageOrientation var1) {
        if (var0 != null && var1 != null && var1 != ImageOrientation.Up) {
            Matrix var2 = a(var1, 1.0F);
            var0 = Bitmap.createBitmap(var0, 0, 0, var0.getWidth(), var0.getHeight(), var2, true);
            return var0;
        } else {
            return var0;
        }
    }

    public static ImageOrientation getImageOrientation(String var0) {
        ExifInterface var1 = getExifInterface(var0);
        ImageOrientation var2 = ImageOrientation.Up;
        if (var1 != null) {
            int var3 = var1.getAttributeInt("Orientation", 1);
            var2 = ImageOrientation.getValue(var3);
        }

        return var2;
    }

    public static ExifInterface getExifInterface(String var0) {
        if (var0 == null) {
            return null;
        } else {
            try {
                ExifInterface var1 = new ExifInterface(var0);
                return var1;
            } catch (IOException var2) {
                TLog.e(var2);
                return null;
            }
        }
    }

    public static Bitmap imageCorpResize(Bitmap var0, TuSdkSize var1, ImageOrientation var2, boolean var3) {
        return imageCorpResize(var0, var1, var1 != null ? var1.minMaxRatio() : 1.0F, var2, var3);
    }

    public static Bitmap imageCorpResize(Bitmap var0, TuSdkSize var1, float var2, ImageOrientation var3, boolean var4) {
        if (var1 != null && var2 == 0.0F) {
            var2 = var1.minMaxRatio();
        }

        return imageResize(var0, var1, false, var2, var4, var3);
    }

    public static Bitmap imageResize(Bitmap var0, TuSdkSize var1) {
        return imageResize(var0, var1, false);
    }

    public static Bitmap imageResize(Bitmap var0, TuSdkSize var1, boolean var2) {
        return imageResize(var0, var1, var2, TuSdkSize.create(var0).minMaxRatio(), false);
    }

    public static Bitmap imageResize(Bitmap var0, TuSdkSize var1, boolean var2, float var3, boolean var4) {
        return imageResize(var0, var1, var2, var3, var4, ImageOrientation.Up);
    }

    public static Bitmap imageResize(Bitmap var0, TuSdkSize var1, boolean var2, ImageOrientation var3) {
        return var0 == null ? var0 : imageResize(var0, var1, var2, TuSdkSize.create(var0).minMaxRatio(), false, var3);
    }

    public static Bitmap imageResize(Bitmap var0, TuSdkSize var1, boolean var2, float var3, boolean var4, ImageOrientation var5) {
        RectF var6 = RectHelper.computerCenterRectF(TuSdkSize.create(var0), var3);
        return imageResize(var0, var1, var6, var2, var4, var5);
    }

    public static Bitmap imageResize(Bitmap var0, TuSdkSize var1, RectF var2, boolean var3, boolean var4, ImageOrientation var5) {
        if (var0 != null && var0.getWidth() >= 1 && var0.getHeight() >= 1) {
            Rect var6 = null;
            if (var2 != null) {
                var6 = RectHelper.fixedRectF(TuSdkSize.create(var0), var2);
            }

            if (var6 == null || var6.width() < 1 || var6.height() < 1) {
                var6 = new Rect(0, 0, var0.getWidth(), var0.getHeight());
            }

            float var7 = a(var0, var1, var3);
            if (!var4 && var7 > 1.0F) {
                var7 = 1.0F;
            }

            Matrix var8 = a(var5, var7);
            if (var6.width() > 0 && var6.height() > 0) {
                try {
                    var0 = Bitmap.createBitmap(var0, var6.left, var6.top, var6.width(), var6.height(), var8, true);
                } catch (Exception var10) {
                    TLog.e("create Bitmap failed,return orginal Bitmap", new Object[0]);
                }

                return var0;
            } else {
                TLog.e("Image width and height must be > 0", new Object[0]);
                return null;
            }
        } else {
            return var0;
        }
    }

    private static float a(Bitmap var0, TuSdkSize var1, boolean var2) {
        if (var0 != null && var1 != null) {
            TuSdkSize var3 = TuSdkSize.create(var0);
            float var4 = (float)var1.maxSide() / (float)var3.maxSide();
            float var5 = (float)var1.minSide() / (float)var3.minSide();
            float var6;
            if (var2) {
                var6 = Math.min(var4, var5);
            } else {
                var6 = Math.max(var4, var5);
            }

            return var6;
        } else {
            return 1.0F;
        }
    }

    public static TuSdkSize computerScaleSize(Bitmap var0, TuSdkSize var1, boolean var2, boolean var3) {
        if (var0 != null && var0.getWidth() >= 1 && var0.getHeight() >= 1) {
            float var4 = a(var0, var1, false);
            if (!var3 && var4 > 1.0F) {
                var4 = 1.0F;
            }

            TuSdkSize var5 = TuSdkSize.create((int)((float)var0.getWidth() * var4), (int)((float)var0.getHeight() * var4));
            var5 = var5.evenSize();
            return var5;
        } else {
            return null;
        }
    }

    private static Matrix a(ImageOrientation var0, float var1) {
        if (var0 == null) {
            var0 = ImageOrientation.Up;
        }

        Matrix var2 = new Matrix();
        var2.setRotate((float)var0.getDegree());
        switch(var0) {
            case UpMirrored:
            case DownMirrored:
                var2.preScale(-var1, var1);
                break;
            case LeftMirrored:
            case RightMirrored:
                var2.preScale(var1, -var1);
                break;
            default:
                var2.preScale(var1, var1);
        }

        return var2;
    }

    public static Bitmap imageCorp(Bitmap var0, float var1) {
        return imageCorpResize(var0, TuSdkSize.create(var0), var1, ImageOrientation.Up, false);
    }

    public static Bitmap imageCorp(Bitmap var0, RectF var1, ImageOrientation var2) {
        return var1 == null ? var0 : imageCorp(var0, var1, (TuSdkSize)null, var2);
    }

    public static Bitmap imageCorp(Bitmap var0, RectF var1, TuSdkSize var2, ImageOrientation var3) {
        if (var0 == null) {
            return null;
        } else {
            RectF var4 = RectHelper.fixedCorpPrecentRect(var1, var3);
            if (var4 == null) {
                return var0;
            } else {
                TuSdkSize var5 = TuSdkSize.create(var0);
                RectF var6 = new RectF((float)var5.width * var4.left, (float)var5.height * var4.top, (float)var5.width * var4.right, (float)var5.height * var4.bottom);
                if (var2 != null) {
                    float var7 = (float)var2.width / ((float)var5.width * var4.width());
                    var2 = new TuSdkSize((int)Math.ceil((double)((float)var5.width * var7)), (int)Math.ceil((double)((float)var5.height * var7)));
                }

                return imageResize(var0, var2, var6, true, false, var3);
            }
        }
    }

    public static boolean saveBitmap(File var0, Bitmap var1, int var2) {
        if (var0 != null && var1 != null) {
            if (var0.exists()) {
                var0.delete();
            }

            return TuSdkGPU.isSupporTurbo() ? a(var0, var1, var2) : b(var0, var1, var2);
        } else {
            return false;
        }
    }

    public static byte[] bitmap2byteArrayTurbo(Bitmap var0, int var1) {
        if (var0 == null) {
            return null;
        } else {
            ByteArrayOutputStream var2 = new ByteArrayOutputStream();
            if (TuSdkGPU.isSupporTurbo()) {
                boolean var3 = a(var2, var0, var1);
                return var3 ? var2.toByteArray() : null;
            } else {
                return bitmap2byteArray(var0, var1);
            }
        }
    }

    private static boolean a(File var0, Bitmap var1, int var2) {
        boolean var3 = false;
        FileOutputStream var4 = null;

        try {
            var4 = new FileOutputStream(var0);
            var3 = TuSdkImageNative.imageCompress(var1, var4, var2, true);
            var4.flush();
        } catch (FileNotFoundException var10) {
            TLog.e(var10, "File not found: %s", new Object[]{var0.getPath()});
        } catch (IOException var11) {
            TLog.e(var11, "Error accessing file: %s", new Object[]{var0.getPath()});
        } finally {
            FileHelper.safeClose(var4);
        }

        return var3;
    }

    private static boolean a(ByteArrayOutputStream var0, Bitmap var1, int var2) {
        boolean var3 = false;

        try {
            var3 = TuSdkImageNative.imageCompress(var1, var0, var2, true);
            var0.flush();
        } catch (IOException var5) {
            TLog.e(var5, "Error accessing outputStream", new Object[0]);
        }

        return var3;
    }

    private static boolean b(File var0, Bitmap var1, int var2) {
        return a(var0, var1, var2, CompressFormat.JPEG);
    }

    private static boolean a(File var0, Bitmap var1, int var2, CompressFormat var3) {
        boolean var4 = false;
        FileOutputStream var5 = null;

        try {
            var5 = new FileOutputStream(var0);
            var4 = var1.compress(var3, var2, var5);
            var5.flush();
        } catch (FileNotFoundException var11) {
            TLog.e(var11, "File not found: %s", new Object[]{var0.getPath()});
        } catch (IOException var12) {
            TLog.e(var12, "Error accessing file: %s", new Object[]{var0.getPath()});
        } finally {
            FileHelper.safeClose(var5);
        }

        return var4;
    }

    public static boolean saveBitmapAsWebP(File var0, Bitmap var1, int var2) {
        return VERSION.SDK_INT < 14 ? saveBitmap(var0, var1, var2) : c(var0, var1, var2);
    }

    @TargetApi(14)
    private static boolean c(File var0, Bitmap var1, int var2) {
        if (var0 != null && var1 != null) {
            if (var0.exists()) {
                var0.delete();
            }

            boolean var3 = false;
            FileOutputStream var4 = null;

            try {
                var4 = new FileOutputStream(var0);
                var1.compress(CompressFormat.WEBP, var2, var4);
                var4.flush();
                var3 = true;
            } catch (FileNotFoundException var10) {
                TLog.e(var10, "File not found: %s", new Object[]{var0.getPath()});
            } catch (IOException var11) {
                TLog.e(var11, "Error accessing file: %s", new Object[]{var0.getPath()});
            } finally {
                FileHelper.safeClose(var4);
            }

            return var3;
        } else {
            return false;
        }
    }

    public static boolean saveBitmapAsPNG(File var0, Bitmap var1, int var2) {
        return a(var0, var1, var2, CompressFormat.PNG);
    }

    public static InputStream bitmap2InputStream(Bitmap var0, int var1) {
        return bitmap2InputStream(var0, var1, (StringBuilder)null);
    }

    public static InputStream bitmap2InputStream(Bitmap var0, int var1, StringBuilder var2) {
        byte[] var3 = bitmap2byteArray(var0, var1);
        if (var2 != null) {
            var2.append(FileHelper.toHexString(var3));
        }

        ByteArrayInputStream var4 = new ByteArrayInputStream(var3);
        return var4;
    }

    public static byte[] bitmap2byteArray(Bitmap var0, int var1) {
        ByteArrayOutputStream var2 = new ByteArrayOutputStream();
        var0.compress(CompressFormat.JPEG, var1, var2);
        byte[] var3 = var2.toByteArray();

        try {
            var2.close();
        } catch (IOException var5) {
            TLog.e(var5, "bitmap2byteArray: %s", new Object[]{var1});
        }

        return var3;
    }

    public static byte[] bitmap2byteArrayMaxByte(Bitmap var0, int var1) {
        if (var0 == null) {
            return null;
        } else {
            int var2 = 100;

            do {
                byte[] var3 = bitmap2byteArray(var0, var2);
                var2 -= 5;
                if (var3 == null) {
                    break;
                }

                if (var3.length <= var1) {
                    return var3;
                }
            } while(var1 > 0 && var2 > 0);

            return null;
        }
    }
}
