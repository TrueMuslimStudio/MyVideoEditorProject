// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.utils.sqllite;

import com.example.myvideoeditorapp.kore.exif.ExifInterface;

import java.io.IOException;
import java.io.Serializable;
import android.content.Intent;
import com.example.myvideoeditorapp.kore.utils.image.ExifHelper;
import java.io.OutputStream;
import java.io.Closeable;
import com.example.myvideoeditorapp.kore.utils.FileHelper;
import java.io.FileNotFoundException;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.secret.TuSdkImageNative;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkGPU;
import com.example.myvideoeditorapp.kore.utils.ContextUtils;
import com.example.myvideoeditorapp.kore.utils.image.AlbumHelper;
import android.annotation.TargetApi;
import com.example.myvideoeditorapp.kore.utils.TuSdkDate;
import android.os.Build;
import android.content.ContentValues;
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import java.util.HashSet;
import java.util.HashMap;
import android.provider.MediaStore;
import java.util.ArrayList;
import android.database.Cursor;
import android.content.ContentResolver;
import java.io.File;
import android.net.Uri;
import android.content.Context;
import android.annotation.SuppressLint;

public class ImageSqlHelper
{
    @SuppressLint({ "InlinedApi" })
    public static final String[] PHOTOJECTIONS_JELLY_BEAN;
    public static final String[] PHOTOJECTIONS_LOW;
    public static final String[] PHOTOJECTIONS_VIDEO;
    public static final String[] PHOTOJECTIONS;
    
    public static File getLocalImageFile(final Context context, final Uri uri) {
        final ImageSqlInfo imageInfo = getImageInfo(context, uri);
        if (imageInfo == null) {
            return null;
        }
        return new File(imageInfo.path);
    }
    
    public static ImageSqlInfo getImageInfo(final Context context, final Uri uri) {
        return getImageInfo(context, uri, ImageSqlHelper.PHOTOJECTIONS, null, null, null);
    }
    
    public static ImageSqlInfo getImageInfo(final ContentResolver contentResolver, final Uri uri) {
        if (contentResolver == null || uri == null) {
            return null;
        }
        return getImageInfo(contentResolver.query(uri, ImageSqlHelper.PHOTOJECTIONS, (String)null, (String[])null, (String)null));
    }
    
    public static ImageSqlInfo getVideoInfo(final ContentResolver contentResolver, final Uri uri) {
        if (contentResolver == null || uri == null) {
            return null;
        }
        return getImageInfo(contentResolver.query(uri, ImageSqlHelper.PHOTOJECTIONS_VIDEO, (String)null, (String[])null, (String)null));
    }
    
    public static ImageSqlInfo getImageInfo(final Context context, final Uri uri, final String[] array, final String s, final String[] array2, final String s2) {
        return getImageInfo(getWithCursorLoader(context, uri, array, s, array2, s2));
    }
    
    public static ImageSqlInfo getImageInfo(final Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        ImageSqlInfo imageSqlInfo = null;
        if (cursor.moveToFirst()) {
            imageSqlInfo = new ImageSqlInfo(cursor);
        }
        cursor.close();
        return imageSqlInfo;
    }
    
    public static Cursor getWithCursorLoader(final Context context, final Uri uri, final String[] array, final String s, final String[] array2, final String s2) {
        if (context == null || uri == null) {
            return null;
        }
        return context.getContentResolver().query(uri, array, s, (String[])null, s2);
    }

    @SuppressLint("Range")
    public static ArrayList<AlbumSqlInfo> getAlbumList(Context var0) {
        if (var0 == null) {
            return null;
        } else {
            Uri var1 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] var2 = new String[]{"bucket_id", "bucket_display_name", "COUNT(*) AS bucket_total"};
            String[] var3 = new String[]{"bucket_id", "bucket_display_name"};
            String var4 = "1) GROUP BY bucket_id-- (";
            String var5 = "bucket_display_name ASC";
            Cursor var6 = getWithCursorLoader(var0, var1, a() ? var2 : var3, a() ? var4 : null, (String[])null, var5);
            if (var6 != null && var6.moveToFirst()) {
                if (!a()) {
                    HashMap var13 = new HashMap();

                    while(!var6.isAfterLast()) {
                        @SuppressLint("Range") long var14 = var6.getLong(var6.getColumnIndex("bucket_id"));
                        Long var10 = (Long)var13.get(var14);
                        if (var10 == null) {
                            var10 = 1L;
                        } else {
                            var10 = var10 + 1L;
                        }

                        var13.put(var14, var10);
                        var6.moveToNext();
                    }

                    if (!var6.moveToFirst()) {
                        return null;
                    } else {
                        ArrayList var15 = new ArrayList();

                        for(HashSet var9 = new HashSet(); !var6.isAfterLast(); var6.moveToNext()) {
                            if (!var9.contains(var6.getLong(var6.getColumnIndex("bucket_id")))) {
                                AlbumSqlInfo var16 = new AlbumSqlInfo(var6, a());
                                var16.cover = getAlbumCoverInfo(var0, var16.id);
                                var16.total = Math.toIntExact((Long)var13.get(var16.id));
                                var15.add(var16);
                                var9.add(var16.id);
                            }
                        }

                        var6.close();
                        AlbumSqlInfo.sortTitle(var15);
                        return var15;
                    }
                } else {
                    ArrayList var7 = new ArrayList();

                    while(!var6.isAfterLast()) {
                        AlbumSqlInfo var8 = new AlbumSqlInfo(var6);
                        var8.cover = getAlbumCoverInfo(var0, var8.id);
                        var7.add(var8);
                        var6.moveToNext();
                    }

                    var6.close();
                    AlbumSqlInfo.sortTitle(var7);
                    return var7;
                }
            } else {
                return null;
            }
        }
    }
    
    public static ArrayList<ImageSqlInfo> getPhotoList(final Context context, final long n) {
        return getPhotoList(context, n, PhotoSortDescriptor.Date_Modified);
    }
    
    public static ArrayList<ImageSqlInfo> getPhotoList(final Context context, final long lng, final PhotoSortDescriptor photoSortDescriptor) {
        if (context == null) {
            return null;
        }
        return getPhotoList(getWithCursorLoader(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ImageSqlHelper.PHOTOJECTIONS, "bucket_id=" + lng, null, photoSortDescriptor.key + (photoSortDescriptor.desc ? " DESC" : " ASC")));
    }
    
    public static ArrayList<ImageSqlInfo> getPhotoList(final ContentResolver contentResolver, final boolean b) {
        if (contentResolver == null) {
            return null;
        }
        return getPhotoList(contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ImageSqlHelper.PHOTOJECTIONS, (String)null, (String[])null, "date_modified" + (b ? " DESC" : " ASC")));
    }
    
    public static ArrayList<ImageSqlInfo> getPhotoList(final Cursor cursor) {
        if (cursor == null || !cursor.moveToFirst()) {
            return null;
        }
        final ArrayList<ImageSqlInfo> list = new ArrayList<ImageSqlInfo>();
        while (!cursor.isAfterLast()) {
            list.add(new ImageSqlInfo(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    
    public static ImageSqlInfo getAlbumCoverInfo(final Context context, final long lng) {
        if (context == null || lng == 0L) {
            return null;
        }
        return getImageInfo(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ImageSqlHelper.PHOTOJECTIONS, "bucket_id=" + lng, null, "date_modified DESC");
    }
    
    public static Bitmap getThumbnail(final Context context, final ImageSqlInfo imageSqlInfo, final int n) {
        if (context == null || imageSqlInfo == null || n == 0) {
            return null;
        }
        return BitmapHelper.imageRotaing(MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), imageSqlInfo.id, n, (BitmapFactory.Options)null), ImageOrientation.getValue(imageSqlInfo.orientation, false));
    }
    
    public static ContentValues getDefaultContentValues(final Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        final ContentValues commonContentValues = getCommonContentValues();
        if (Build.VERSION.SDK_INT > 15) {
            a(bitmap, commonContentValues);
        }
        return commonContentValues;
    }
    
    public static ContentValues getCommonContentValues() {
        final long timeInMillis = TuSdkDate.create().getTimeInMillis();
        final ContentValues contentValues = new ContentValues();
        contentValues.put("datetaken", Long.valueOf(timeInMillis));
        contentValues.put("date_modified", Long.valueOf(timeInMillis / 1000L));
        contentValues.put("date_added", Long.valueOf(timeInMillis / 1000L));
        return contentValues;
    }
    
    @TargetApi(16)
    private static void a(final Bitmap bitmap, final ContentValues contentValues) {
        contentValues.put("width", Integer.valueOf(bitmap.getWidth()));
        contentValues.put("height", Integer.valueOf(bitmap.getHeight()));
    }
    
    public static ContentValues build(final Bitmap bitmap, File albumFile, final String s) {
        final ContentValues defaultContentValues = getDefaultContentValues(bitmap);
        if (defaultContentValues == null) {
            return null;
        }
        if (albumFile == null && Build.VERSION.SDK_INT > 18) {
            albumFile = AlbumHelper.getAlbumFile();
        }
        if (albumFile != null) {
            defaultContentValues.put("_data", albumFile.getPath());
        }
        if (s != null) {
            defaultContentValues.put("description", s);
        }
        defaultContentValues.put("_size", Integer.valueOf(bitmap.getByteCount()));
        return defaultContentValues;
    }
    
    public static ImageSqlInfo saveJpgToAblum(final Context context, final Bitmap bitmap, final int n, final File file) {
        return saveJpgToAblum(context, bitmap, n, build(bitmap, file, ContextUtils.getAppName(context)));
    }
    
    public static ImageSqlInfo saveMp4ToAlbum(final Context context, final File file) {
        final String appName = ContextUtils.getAppName(context);
        final ContentValues commonContentValues = getCommonContentValues();
        if (commonContentValues == null) {
            return null;
        }
        if (file != null) {
            commonContentValues.put("_data", file.getPath());
        }
        if (appName != null) {
            commonContentValues.put("description", appName);
        }
        commonContentValues.put("mime_type", "video/mp4");
        return getVideoInfo(context.getContentResolver(), context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, commonContentValues));
    }
    
    public static ImageSqlInfo saveMp4ToAlbum(final Context context, final ContentValues contentValues) {
        final String appName = ContextUtils.getAppName(context);
        final ContentValues commonContentValues = getCommonContentValues();
        commonContentValues.put("title", appName);
        commonContentValues.put("mime_type", "video/mp4");
        if (contentValues != null) {
            commonContentValues.putAll(contentValues);
        }
        return getVideoInfo(context.getContentResolver(), context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, commonContentValues));
    }
    
    public static ImageSqlInfo saveJpgToAblum(final Context context, final Bitmap bitmap, final int n, final ContentValues contentValues) {
        if (context == null) {
            return null;
        }
        final Uri saveJpgToAblum = saveJpgToAblum(bitmap, context.getContentResolver(), n, contentValues);
        if (saveJpgToAblum == null) {
            return null;
        }
        return getImageInfo(context.getContentResolver(), saveJpgToAblum);
    }
    
    public static Uri saveJpgToAblum(final Bitmap bitmap, final ContentResolver contentResolver, final int n, final ContentValues contentValues) {
        if (bitmap == null || contentResolver == null) {
            return null;
        }
        if (TuSdkGPU.isSupporTurbo()) {
            return a(bitmap, contentResolver, n, contentValues);
        }
        return b(bitmap, contentResolver, n, contentValues);
    }
    
    private static Uri a(final Bitmap bitmap, final ContentResolver contentResolver, final int n, ContentValues defaultContentValues) {
        if (bitmap == null || contentResolver == null) {
            return null;
        }
        if (defaultContentValues == null) {
            defaultContentValues = getDefaultContentValues(bitmap);
        }
        defaultContentValues.put("mime_type", "image/jpeg");
        final Uri insert = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, defaultContentValues);
        if (insert == null) {
            return null;
        }
        OutputStream openOutputStream = null;
        try {
            openOutputStream = contentResolver.openOutputStream(insert);
            if (!TuSdkImageNative.imageCompress(bitmap, openOutputStream, n, true)) {
                TLog.e("saveJpgToAblum faild: %s", insert);
            }
        }
        catch (FileNotFoundException ex) {
            TLog.e(ex, "saveJpgToAblum: %s", insert);
        }
        finally {
            FileHelper.safeClose(openOutputStream);
        }
        return insert;
    }
    
    private static Uri b(final Bitmap bitmap, final ContentResolver contentResolver, final int n, final ContentValues contentValues) {
        final Uri insert = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        if (insert == null) {
            return null;
        }
        OutputStream openOutputStream = null;
        try {
            openOutputStream = contentResolver.openOutputStream(insert);
            bitmap.compress(Bitmap.CompressFormat.JPEG, n, openOutputStream);
        }
        catch (FileNotFoundException ex) {
            TLog.e(ex, "saveJpgToAblum: %s", insert);
        }
        finally {
            FileHelper.safeClose(openOutputStream);
        }
        return insert;
    }
    
    public static void notifyRefreshAblum(final Context context, final ImageSqlInfo imageSqlInfo) throws IOException {
        if (context == null || imageSqlInfo == null || imageSqlInfo.path == null) {
            return;
        }
        final ExifInterface exifInterface = ExifHelper.getExifInterface(new File(imageSqlInfo.path));
        final Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(new File(imageSqlInfo.path)));
        intent.putExtra("width", imageSqlInfo.size.width);
        intent.putExtra("height", imageSqlInfo.size.height);
        intent.putExtra("bucket_id", imageSqlInfo.albumId);
        intent.putExtra("date_added", imageSqlInfo.createDate.getTimeInMillis());
        intent.putExtra("_display_name", imageSqlInfo.name);
        intent.putExtra("mime_type", "jpg");
        if (exifInterface.getLongitude() != null) {
            intent.putExtra("longitude", (Serializable)Double.valueOf(exifInterface.getLongitude()));
        }
        if (exifInterface.getLatitude() != null) {
            intent.putExtra("latitude", (Serializable)Double.valueOf(exifInterface.getLatitude()));
        }
        intent.putExtra("orientation", imageSqlInfo.orientation);
        intent.putExtra("_size", imageSqlInfo.length);
        context.sendBroadcast(intent);
    }
    
    private static boolean a() {
        return Build.VERSION.SDK_INT < 29;
    }
    
    static {
        PHOTOJECTIONS_JELLY_BEAN = new String[] { "_id", "orientation", "_data", "date_modified", "bucket_id", "_size", "width", "height" };
        PHOTOJECTIONS_LOW = new String[] { "_id", "orientation", "_data", "date_modified", "bucket_id", "_size" };
        PHOTOJECTIONS_VIDEO = new String[] { "_id", "_data", "date_modified", "bucket_id", "_size", "width", "height" };
        if (Build.VERSION.SDK_INT < 16) {
            PHOTOJECTIONS = ImageSqlHelper.PHOTOJECTIONS_LOW;
        }
        else {
            PHOTOJECTIONS = ImageSqlHelper.PHOTOJECTIONS_JELLY_BEAN;
        }
    }

    public static enum PhotoSortDescriptor {
        Date_Added("date_added", true),
        Date_Modified("date_modified", true),
        Customize;

        public String key;
        public boolean desc;

        private PhotoSortDescriptor(String var3, boolean var4) {
            this.key = var3;
            this.desc = var4;
        }

        private PhotoSortDescriptor() {
        }

        public PhotoSortDescriptor setKey(String var1) {
            this.key = var1;
            return this;
        }

        public PhotoSortDescriptor setDesc(boolean var1) {
            this.desc = var1;
            return this;
        }
    }
}
