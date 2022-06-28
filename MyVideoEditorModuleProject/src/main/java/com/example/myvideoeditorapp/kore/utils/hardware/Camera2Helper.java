// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.utils.hardware;

import com.example.myvideoeditorapp.kore.view.TuSdkViewHelper;
import com.example.myvideoeditorapp.kore.TuSdkContext;
import android.os.Build;
import android.util.Range;
import android.graphics.RectF;
import android.graphics.Point;
import com.example.myvideoeditorapp.kore.utils.RectHelper;
import android.hardware.camera2.params.Face;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import android.hardware.camera2.params.MeteringRectangle;
import android.util.Size;
import android.graphics.Rect;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;
import android.graphics.PointF;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraAccessException;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ContextUtils;
import android.hardware.camera2.CameraManager;
import android.content.Context;
import android.annotation.TargetApi;

@TargetApi(21)
public class Camera2Helper
{
    public static CameraManager cameraManager(final Context context) {
        return ContextUtils.getSystemService(context, "camera");
    }
    
    public static boolean canSupportCamera(final Context context) {
        return (boolean)(ContextUtils.hasSystemFeature(context, "android.hardware.camera") && cameraCounts(context) > 0);
    }
    
    public static String[] cameraIds(final Context context) {
        return cameraIds(cameraManager(context));
    }
    
    public static String[] cameraIds(final CameraManager cameraManager) {
        if (cameraManager == null) {
            return null;
        }
        try {
            return cameraManager.getCameraIdList();
        }
        catch (CameraAccessException ex) {
            TLog.e((Throwable)ex, "Get system all camera ids error", new Object[0]);
            return null;
        }
    }
    
    public static int cameraCounts(final Context context) {
        final String[] cameraIds = cameraIds(context);
        if (cameraIds == null) {
            return 0;
        }
        return cameraIds.length;
    }
    
    public static String firstBackCameraId(final Context context) {
        return firstCameraId(context, 1);
    }
    
    public static String firstFrontCameraId(final Context context) {
        return firstCameraId(context, 0);
    }
    
    public static String firstCameraId(final Context context, CameraConfigs.CameraFacing back) {
        if (back == null) {
            back = CameraConfigs.CameraFacing.Back;
        }
        int n = 1;
        switch (back.ordinal()) {
            case 1: {
                n = 0;
                break;
            }
        }
        return firstCameraId(context, n);
    }
    
    public static CameraConfigs.CameraFacing cameraPosition(final CameraCharacteristics cameraCharacteristics) {
        if (cameraCharacteristics == null) {
            return null;
        }
        switch ((int)cameraCharacteristics.get(CameraCharacteristics.LENS_FACING)) {
            case 0: {
                return CameraConfigs.CameraFacing.Front;
            }
            default: {
                return CameraConfigs.CameraFacing.Back;
            }
        }
    }
    
    public static String firstCameraId(final Context context, final int n) {
        final CameraManager cameraManager = cameraManager(context);
        final String[] cameraIds = cameraIds(context);
        if (cameraIds == null) {
            return null;
        }
        String s = null;
        final String[] array = cameraIds;
        for (int length = array.length, n2 = 0; n2 < length && (int)cameraCharacter(cameraManager, s = array[n2]).get(CameraCharacteristics.LENS_FACING) != n; ++n2) {}
        return s;
    }
    
    public static CameraCharacteristics cameraCharacter(final CameraManager cameraManager, final String s) {
        if (cameraManager == null || s == null) {
            return null;
        }
        try {
            return cameraManager.getCameraCharacteristics(s);
        }
        catch (CameraAccessException ex) {
            TLog.e((Throwable)ex, "Get Camera Character error: %s", s);
            return null;
        }
    }
    
    public static StreamConfigurationMap streamConfigurationMap(final CameraCharacteristics cameraCharacteristics) {
        return (StreamConfigurationMap)cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
    }
    
    public static <T> void mergerBuilder(final CaptureRequest.Builder builder, final CaptureRequest.Builder builder2, final CaptureRequest.Key<T> key) {
        if (builder == null || builder2 == null || key == null) {
            return;
        }
        final Object value = builder.get((CaptureRequest.Key)key);
        if (value != null) {
            builder2.set((CaptureRequest.Key)key, value);
        }
    }
    
    public static int supportHardwareLevel(final CameraCharacteristics cameraCharacteristics) {
        if (cameraCharacteristics == null) {
            return -1;
        }
        final Integer n = (Integer)cameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
        if (n == null) {
            return -1;
        }
        return n;
    }
    
    public static boolean hardwareOnlySupportLegacy(final Context context) {
        return hardwareOnlySupportLegacy(cameraCharacter(cameraManager(context), firstBackCameraId(context)));
    }
    
    public static boolean hardwareOnlySupportLegacy(final CameraCharacteristics cameraCharacteristics) {
        final int supportHardwareLevel = supportHardwareLevel(cameraCharacteristics);
        return supportHardwareLevel < 0 || supportHardwareLevel == 2;
    }
    
    public static boolean canSupportFlash(final Context context) {
        return ContextUtils.hasSystemFeature(context, "android.hardware.camera.flash");
    }
    
    public static boolean supportFlash(final CameraCharacteristics cameraCharacteristics) {
        return cameraCharacteristics != null && (boolean)cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
    }
    
    public static CameraConfigs.CameraFlash getFlashMode(final CaptureRequest.Builder builder) {
        CameraConfigs.CameraFlash cameraFlash = CameraConfigs.CameraFlash.Off;
        if (builder == null) {
            return CameraConfigs.CameraFlash.Off;
        }
        final Integer n = (Integer)builder.get(CaptureRequest.CONTROL_AE_MODE);
        final Integer n2 = (Integer)builder.get(CaptureRequest.FLASH_MODE);
        if (n == null) {
            return cameraFlash;
        }
        if (n2 != null) {
            switch (n2) {
                case 1: {
                    cameraFlash = CameraConfigs.CameraFlash.On;
                    break;
                }
                case 2: {
                    cameraFlash = CameraConfigs.CameraFlash.Torch;
                    break;
                }
            }
        }
        switch (n) {
            case 2: {
                cameraFlash = CameraConfigs.CameraFlash.Auto;
                break;
            }
            case 4: {
                cameraFlash = CameraConfigs.CameraFlash.RedEye;
                break;
            }
            case 3: {
                cameraFlash = CameraConfigs.CameraFlash.Always;
                break;
            }
            case 0: {
                cameraFlash = CameraConfigs.CameraFlash.Off;
                break;
            }
        }
        return cameraFlash;
    }

    public static void setFlashMode(CaptureRequest.Builder var0, CameraConfigs.CameraFlash var1) {
        if (var0 != null && var1 != null) {
            var0.set(CaptureRequest.CONTROL_MODE, 1);
            switch(var1) {
                case Off:
                    var0.set(CaptureRequest.CONTROL_AE_MODE, 1);
                    var0.set(CaptureRequest.FLASH_MODE, 0);
                    break;
                case Auto:
                    var0.set(CaptureRequest.CONTROL_AE_MODE, 2);
                    break;
                case On:
                    var0.set(CaptureRequest.CONTROL_AE_MODE, 1);
                    var0.set(CaptureRequest.FLASH_MODE, 1);
                    break;
                case Torch:
                    var0.set(CaptureRequest.CONTROL_AE_MODE, 1);
                    var0.set(CaptureRequest.FLASH_MODE, 2);
                    break;
                case Always:
                    var0.set(CaptureRequest.CONTROL_AE_MODE, 3);
                    break;
                case RedEye:
                    var0.set(CaptureRequest.CONTROL_AE_MODE, 4);
            }

        }
    }
    
    public static boolean canSupportAutofocus(final Context context) {
        return ContextUtils.hasSystemFeature(context, "android.hardware.camera.autofocus");
    }
    
    public static boolean canSupportAutofocus(final CameraCharacteristics cameraCharacteristics) {
        if (cameraCharacteristics == null) {
            return false;
        }
        final int[] array = (int[])cameraCharacteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);
        return array != null && array.length != 0 && (array.length != 1 || array[0] != 0);
    }
    
    public static boolean canSupportAutofocus(final CameraCharacteristics cameraCharacteristics, final int n) {
        if (cameraCharacteristics == null) {
            return false;
        }
        final int[] array = (int[])cameraCharacteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);
        if (array == null || array.length == 0) {
            return false;
        }
        final int[] array2 = array;
        for (int length = array2.length, i = 0; i < length; ++i) {
            if (n == array2[i]) {
                return true;
            }
        }
        return false;
    }
    
    public static int swichFocusMode(CameraConfigs.CameraAutoFocus off) {
        if (off == null) {
            off = CameraConfigs.CameraAutoFocus.Off;
        }
        switch (off.ordinal()) {
            case 1: {
                return 1;
            }
            case 2: {
                return 2;
            }
            case 3: {
                return 3;
            }
            case 4: {
                return 4;
            }
            case 5: {
                return 5;
            }
            default: {
                return 0;
            }
        }
    }
    
    public static CameraConfigs.CameraAutoFocus focusModeType(final CaptureRequest.Builder builder) {
        final CameraConfigs.CameraAutoFocus off = CameraConfigs.CameraAutoFocus.Off;
        if (builder == null) {
            return off;
        }
        final Integer n = (Integer)builder.get(CaptureRequest.CONTROL_AF_MODE);
        if (n == null) {
            return off;
        }
        switch (n) {
            case 1: {
                return CameraConfigs.CameraAutoFocus.Auto;
            }
            case 2: {
                return CameraConfigs.CameraAutoFocus.Macro;
            }
            case 3:
            case 4: {
                return CameraConfigs.CameraAutoFocus.ContinuousPicture;
            }
            case 5: {
                return CameraConfigs.CameraAutoFocus.EDOF;
            }
            default: {
                return off;
            }
        }
    }

    public static void setFocusMode(CameraCharacteristics var0, CaptureRequest.Builder var1, CameraConfigs.CameraAutoFocus var2, PointF var3, ImageOrientation var4) {
        if (var0 != null && var1 != null && canSupportAutofocus(var0)) {
            int var5 = swichFocusMode(var2);
            if (canSupportAutofocus(var0, var5)) {
                var1.set(CaptureRequest.CONTROL_AF_MODE, var5);
            }

            setFocusPoint(var0, var1, var3, var4);
        }
    }

    public static void setFocusPoint(CameraCharacteristics var0, CaptureRequest.Builder var1, PointF var2, ImageOrientation var3) {
        if (var0 != null && var1 != null && var2 != null) {
            Rect var4 = (Rect)var0.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
            Size var5 = (Size)var0.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE);
            PointF var6 = new PointF(var2.x, var2.y);
            if (var3 == null) {
                var3 = ImageOrientation.Up;
            }

            switch(var3) {
                case Right:
                    var6.x = var2.y;
                    var6.y = 1.0F - var2.x;
                    break;
                case Down:
                    var6.x = 1.0F - var2.x;
                    var6.y = 1.0F - var2.y;
                    break;
                case Left:
                    var6.x = 1.0F - var2.y;
                    var6.y = var2.x;
                    break;
                case UpMirrored:
                    var6.x = 1.0F - var2.x;
                    var6.y = var2.y;
                    break;
                case RightMirrored:
                    var6.x = 1.0F - var2.y;
                    var6.y = 1.0F - var2.x;
                    break;
                case DownMirrored:
                    var6.x = var2.x;
                    var6.y = 1.0F - var2.y;
                    break;
                case LeftMirrored:
                    var6.x = var2.y;
                    var6.y = var2.x;
            }

            int var7 = (int)((float)Math.min(var4.width(), var4.height()) * 0.03F);
            int var8 = var4.left + (int)(var6.x * (float)var4.width()) + var7;
            int var9 = var4.top + (int)(var6.y * (float)var4.width()) + var7;
            Rect var10 = new Rect(var8 - var7 * 2, var9 - var7 * 2, var8, var9);
            var10 = fixFocusRange(var10, var5);
            MeteringRectangle var11 = new MeteringRectangle(var10, 500);
            MeteringRectangle[] var12 = new MeteringRectangle[]{var11};
            var1.set(CaptureRequest.CONTROL_AF_TRIGGER, 2);
            var1.set(CaptureRequest.CONTROL_AF_REGIONS, var12);
            var1.set(CaptureRequest.CONTROL_AF_TRIGGER, 1);
            var1.set(CaptureRequest.CONTROL_AE_REGIONS, var12);
            var1.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, 1);
        }
    }
    
    public static Rect fixFocusRange(final Rect rect, final Size size) {
        final Rect rect2 = new Rect(rect);
        if (rect.left < 0) {
            rect2.left = 0;
            rect2.right = rect.width();
        }
        else if (rect.right > size.getWidth()) {
            rect2.right = size.getWidth();
            rect2.left = rect2.right - rect.width();
        }
        if (rect.top < 0) {
            rect2.top = 0;
            rect2.bottom = rect.height();
        }
        else if (rect.bottom > size.getHeight()) {
            rect2.bottom = size.getHeight();
            rect2.top = rect2.bottom - rect.height();
        }
        return rect2;
    }
    
    public static TuSdkSize createSize(final Size size) {
        if (size != null) {
            return new TuSdkSize(size.getWidth(), size.getHeight());
        }
        return null;
    }
    
    public static Size previewOptimalSize(final Context context, final Size[] a, int n, float n2) {
        if (a == null) {
            return null;
        }
        final TuSdkSize screenSize = ContextUtils.getScreenSize(context);
        if (screenSize == null) {
            return null;
        }
        final List<Size> sortSizeList = sortSizeList(Arrays.asList(a));
        if (sortSizeList == null || sortSizeList.isEmpty()) {
            return null;
        }
        Size size = null;
        if (n < 1) {
            return sortSizeList.get(0);
        }
        if (n2 <= 0.0f || n2 > 1.0f) {
            n2 = 1.0f;
        }
        final double floor = Math.floor(screenSize.maxSide() * n2);
        if (floor < n) {
            n = (int)floor;
        }
        final List<Size> matchRatioSizes = getMatchRatioSizes(screenSize.getRatio(), sortSizeList);
        if (!matchRatioSizes.isEmpty()) {
            size = matchRatioSizes.get(0);
            for (final Size size2 : matchRatioSizes) {
                if (size2.getWidth() != size2.getHeight() && size2.getWidth() <= n) {
                    if (size2.getHeight() > n) {
                        continue;
                    }
                    size = size2;
                    break;
                }
            }
        }
        if (size == null) {
            size = sortSizeList.get(sortSizeList.size() - 1);
            for (final Size size3 : sortSizeList) {
                if (size3.getWidth() <= n) {
                    if (size3.getHeight() > n) {
                        continue;
                    }
                    size = size3;
                    break;
                }
            }
        }
        return size;
    }
    
    public static Size pictureOptimalSize(final Context context, final Size[] a, TuSdkSize tuSdkSize) {
        if (a == null) {
            return null;
        }
        final TuSdkSize screenSize = ContextUtils.getScreenSize(context);
        if (screenSize == null) {
            return null;
        }
        final List<Size> sortSizeList = sortSizeList(Arrays.asList(a));
        if (sortSizeList == null || sortSizeList.isEmpty()) {
            return null;
        }
        if (tuSdkSize == null) {
            tuSdkSize = screenSize;
        }
        Size size = getNearestSize(getMatchRatioSizes(tuSdkSize.getRatio(), sortSizeList), tuSdkSize);
        if (size == null) {
            size = getNearestSize(sortSizeList, tuSdkSize);
        }
        if (size == null) {
            size = sortSizeList.get(sortSizeList.size() - 1);
        }
        return size;
    }

    public static List<Size> sortSizeList(List<Size> var0) {
        if (var0 != null && var0.size() != 0) {
            Collections.sort(var0, new Comparator<Size>() {
                public int compare(Size var1, Size var2) {
                    if (var1.getWidth() < var2.getWidth()) {
                        return 1;
                    } else if (var1.getWidth() > var2.getWidth()) {
                        return -1;
                    } else if (var1.getHeight() < var2.getHeight()) {
                        return 1;
                    } else {
                        return var1.getHeight() > var2.getHeight() ? -1 : 0;
                    }
                }
            });
            return var0;
        } else {
            return var0;
        }
    }
    
    public static List<Size> getMatchRatioSizes(final int n, final List<Size> list) {
        final ArrayList<Size> list2 = new ArrayList<Size>();
        if (n == 0 || list == null) {
            return list2;
        }
        for (final Size size : list) {
            if (matchRatio(n, size)) {
                list2.add(size);
            }
        }
        return list2;
    }
    
    public static boolean matchRatio(final int n, final Size size) {
        return Math.abs(n - getSizeRatio(size.getWidth(), size.getHeight())) < 2;
    }
    
    public static int getSizeRatio(final int n, final int n2) {
        return (int)Math.floor(Math.max(n, n2) / (float)Math.min(n, n2) * 10.0f);
    }
    
    public static Size getNearestSize(final List<Size> list, final TuSdkSize tuSdkSize) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        Size size = null;
        final int size2 = TuSdkGPU.getGpuType().getSize();
        for (final Size size3 : list) {
            final TuSdkSize size4 = createSize(size3);
            if (size4.maxSide() > size2) {
                continue;
            }
            if (tuSdkSize == null) {
                size = size3;
                break;
            }
            if (size4.maxSide() > tuSdkSize.maxSide() && size4.minSide() > tuSdkSize.minSide()) {
                size = size3;
            }
            else {
                if (size4.maxSide() == tuSdkSize.maxSide() || size == null) {
                    size = size3;
                    break;
                }
                break;
            }
        }
        return size;
    }
    
    public static boolean canSupportFaceDetection(final CameraCharacteristics cameraCharacteristics) {
        if (cameraCharacteristics == null) {
            return false;
        }
        final Integer n = (Integer)cameraCharacteristics.get(CameraCharacteristics.STATISTICS_INFO_MAX_FACE_COUNT);
        if (n == null || n == 0) {
            return false;
        }
        final int[] array = (int[])cameraCharacteristics.get(CameraCharacteristics.STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES);
        return array != null && array.length != 0 && (array.length != 1 || array[0] != 0);
    }
    
    public static List<TuSdkFace> transforFaces(final CameraCharacteristics cameraCharacteristics, final TuSdkSize tuSdkSize, final Face[] a, final ImageOrientation imageOrientation) {
        if (cameraCharacteristics == null || tuSdkSize == null || a == null || a.length == 0) {
            return null;
        }
        final Rect rect = (Rect)cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        final TuSdkSize create = TuSdkSize.create(rect.width(), rect.height());
        final ArrayList<TuSdkFace> list = new ArrayList<TuSdkFace>(a.length);
        final Rect rectWithAspectRatioInsideRect = RectHelper.makeRectWithAspectRatioInsideRect(tuSdkSize, new Rect(0, 0, create.width, create.height));
        final Iterator<Face> iterator = a(Arrays.asList(a), create.center()).iterator();
        while (iterator.hasNext()) {
            list.add(transforFace(iterator.next(), rectWithAspectRatioInsideRect, imageOrientation));
        }
        return list;
    }

    private static List<Face> a(List<Face> var0, final Point var1) {
        if (var0 != null && var0.size() != 0) {
            Collections.sort(var0, new Comparator<Face>() {
                public int compare(Face var1x, Face var2) {
                    if (var1x.getBounds() != null && var2.getBounds() != null && !var1x.getBounds().equals(var2.getBounds())) {
                        Point var3 = new Point(var1x.getBounds().centerX(), var1x.getBounds().centerY());
                        Point var4 = new Point(var2.getBounds().centerX(), var2.getBounds().centerY());
                        return RectHelper.computerPotintDistance(var3, var1) > RectHelper.computerPotintDistance(var4, var1) ? 1 : -1;
                    } else {
                        return 0;
                    }
                }
            });
            return var0;
        } else {
            return var0;
        }
    }
    public static TuSdkFace transforFace(final Face face, final Rect rect, final ImageOrientation imageOrientation) {
        if (face == null || rect == null) {
            return null;
        }
        final TuSdkFace tuSdkFace = new TuSdkFace();
        tuSdkFace.id = face.getId();
        tuSdkFace.score = face.getScore();
        if (face.getBounds() != null) {
            tuSdkFace.rect = new RectF();
            tuSdkFace.rect.left = (face.getBounds().left - rect.left) / (float)rect.width();
            tuSdkFace.rect.right = (face.getBounds().right - rect.left) / (float)rect.width();
            tuSdkFace.rect.top = (face.getBounds().top - rect.top) / (float)rect.height();
            tuSdkFace.rect.bottom = (face.getBounds().bottom - rect.top) / (float)rect.height();
        }
        tuSdkFace.leftEye = a(face.getLeftEyePosition(), rect);
        tuSdkFace.rightEye = a(face.getRightEyePosition(), rect);
        tuSdkFace.mouth = a(face.getMouthPosition(), rect);
        TuSdkFace.convertOrientation(tuSdkFace, imageOrientation);
        return tuSdkFace;
    }
    
    private static PointF a(final Point point, final Rect rect) {
        if (point == null || rect == null) {
            return null;
        }
        final PointF pointF = new PointF();
        pointF.x = (point.x - rect.left) / (float)rect.width();
        pointF.y = (point.y - rect.top) / (float)rect.height();
        return pointF;
    }
    
    public static void logCameraCharacter(final CameraCharacteristics cameraCharacteristics) {
        if (cameraCharacteristics == null) {
            return;
        }
        for (final CameraCharacteristics.Key key : cameraCharacteristics.getKeys()) {
            if (key.equals((Object)CameraCharacteristics.COLOR_CORRECTION_AVAILABLE_ABERRATION_MODES)) {
                a(cameraCharacteristics, (CameraCharacteristics.Key<int[]>)CameraCharacteristics.COLOR_CORRECTION_AVAILABLE_ABERRATION_MODES);
            }
            else if (key.equals((Object)CameraCharacteristics.CONTROL_AE_AVAILABLE_ANTIBANDING_MODES)) {
                a(cameraCharacteristics, (CameraCharacteristics.Key<int[]>)CameraCharacteristics.CONTROL_AE_AVAILABLE_ANTIBANDING_MODES);
            }
            else if (key.equals((Object)CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES)) {
                a(cameraCharacteristics, (CameraCharacteristics.Key<int[]>)CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES);
            }
            else if (key.equals((Object)CameraCharacteristics.CONTROL_AVAILABLE_EFFECTS)) {
                a(cameraCharacteristics, (CameraCharacteristics.Key<int[]>)CameraCharacteristics.CONTROL_AVAILABLE_EFFECTS);
            }
            else if (key.equals((Object)CameraCharacteristics.CONTROL_AVAILABLE_SCENE_MODES)) {
                a(cameraCharacteristics, (CameraCharacteristics.Key<int[]>)CameraCharacteristics.CONTROL_AVAILABLE_SCENE_MODES);
            }
            else if (key.equals((Object)CameraCharacteristics.CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES)) {
                a(cameraCharacteristics, (CameraCharacteristics.Key<int[]>)CameraCharacteristics.CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES);
            }
            else if (key.equals((Object)CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES)) {
                a(cameraCharacteristics, (CameraCharacteristics.Key<int[]>)CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES);
            }
            else if (key.equals((Object)CameraCharacteristics.JPEG_AVAILABLE_THUMBNAIL_SIZES)) {
                final Size[] array = (Size[])cameraCharacteristics.get(CameraCharacteristics.JPEG_AVAILABLE_THUMBNAIL_SIZES);
                if (array == null) {
                    continue;
                }
                final Size[] array2 = array;
                for (int length = array2.length, i = 0; i < length; ++i) {
                    TLog.d("Camera %s: %s", key.getName(), array2[i]);
                }
            }
            else if (key.equals((Object)CameraCharacteristics.NOISE_REDUCTION_AVAILABLE_NOISE_REDUCTION_MODES)) {
                a(cameraCharacteristics, (CameraCharacteristics.Key<int[]>)CameraCharacteristics.NOISE_REDUCTION_AVAILABLE_NOISE_REDUCTION_MODES);
            }
            else if (key.equals((Object)CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)) {
                a(cameraCharacteristics, (CameraCharacteristics.Key<int[]>)CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
            }
            else if (key.equals((Object)CameraCharacteristics.SENSOR_AVAILABLE_TEST_PATTERN_MODES)) {
                a(cameraCharacteristics, (CameraCharacteristics.Key<int[]>)CameraCharacteristics.SENSOR_AVAILABLE_TEST_PATTERN_MODES);
            }
            else if (key.equals((Object)CameraCharacteristics.STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES)) {
                a(cameraCharacteristics, (CameraCharacteristics.Key<int[]>)CameraCharacteristics.STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES);
            }
            else if (key.equals((Object)CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES)) {
                final Range[] array3 = (Range[])cameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
                if (array3 == null) {
                    continue;
                }
                for (final Range range : array3) {
                    TLog.d("Camera %s: [%s - %s]", key.getName(), range.getLower(), range.getUpper());
                }
            }
            else if (key.equals((Object)CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)) {
                final StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap)cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                if (streamConfigurationMap == null) {
                    continue;
                }
                for (final int l : streamConfigurationMap.getOutputFormats()) {
                    final Size[] outputSizes = streamConfigurationMap.getOutputSizes(l);
                    for (int length4 = outputSizes.length, n = 0; n < length4; ++n) {
                        TLog.d("Camera %s: [%s] %s", key.getName(), l, outputSizes[n]);
                    }
                }
            }
            else if (key.equals((Object)CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES)) {
                a(cameraCharacteristics, (CameraCharacteristics.Key<int[]>)CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);
            }
            else {
                TLog.d("Camera %s: %s", key.getName(), cameraCharacteristics.get(key));
            }
        }
    }
    
    private static void a(final CameraCharacteristics cameraCharacteristics, final CameraCharacteristics.Key<int[]> key) {
        if (key == null) {
            return;
        }
        final int[] array = (int[])cameraCharacteristics.get((CameraCharacteristics.Key)key);
        if (array == null) {
            return;
        }
        final int[] array2 = array;
        for (int length = array2.length, i = 0; i < length; ++i) {
            TLog.d("Camera %s: [%s]", key.getName(), array2[i]);
        }
    }
    
    public static boolean showAlertIfNotSupportCamera(final Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            return false;
        }
        if (context == null) {
            return true;
        }
        String s = null;
        if (cameraCounts(context) == 0) {
            s = TuSdkContext.getString("lsq_carema_no_device");
        }
        else if (!canSupportCamera(context)) {
            s = TuSdkContext.getString("lsq_carema_no_access", ContextUtils.getAppName(context));
        }
        if (s == null) {
            return false;
        }
        TuSdkViewHelper.alert(context, TuSdkContext.getString("lsq_carema_alert_title"), s, TuSdkContext.getString("lsq_button_done"));
        return true;
    }
}
