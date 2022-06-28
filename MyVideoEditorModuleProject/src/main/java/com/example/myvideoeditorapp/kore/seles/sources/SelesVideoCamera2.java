// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.sources;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Surface;

import androidx.core.app.ActivityCompat;

import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.ContextUtils;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.hardware.Camera2Helper;
import com.example.myvideoeditorapp.kore.utils.hardware.CameraConfigs;
import com.example.myvideoeditorapp.kore.utils.hardware.InterfaceOrientation;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;

@TargetApi(21)
public abstract class SelesVideoCamera2 extends SelesVideoCamera2Base {
    private CameraManager b;
    private String c;
    private CameraCharacteristics d;
    protected final Handler mHandler;
    private HandlerThread e;
    private CameraConfigs.CameraFacing f;
    private Surface g;
    private SelesVideoCamera2Engine h;

    public String getCameraId() {
        return this.c;
    }

    public CameraCharacteristics getCameraCharacter() {
        return this.d;
    }

    public Surface getPreviewSurface() {
        return this.g;
    }

    public CameraConfigs.CameraFacing cameraPosition() {
        return Camera2Helper.cameraPosition(this.getCameraCharacter());
    }

    public boolean isFrontFacingCameraPresent() {
        return this.cameraPosition() == CameraConfigs.CameraFacing.Front;
    }

    public boolean isBackFacingCameraPresent() {
        return this.cameraPosition() == CameraConfigs.CameraFacing.Back;
    }

    public SelesVideoCamera2(final Context context, final CameraConfigs.CameraFacing cameraFacing) {
        super(context);
        this.h = new SelesVideoCamera2Engine() {
            @Override
            public boolean canInitCamera() {
                SelesVideoCamera2.this.c = Camera2Helper.firstCameraId(SelesVideoCamera2.this.getContext(), SelesVideoCamera2.this.f);
                if (SelesVideoCamera2.this.c == null) {
                    TLog.e("The device can not find any camera2 info: %s", SelesVideoCamera2.this.f);
                    return false;
                }
                return true;
            }

            @Override
            public boolean onInitCamera() {
                SelesVideoCamera2.this.d = Camera2Helper.cameraCharacter(SelesVideoCamera2.this.b, SelesVideoCamera2.this.c);
                if (SelesVideoCamera2.this.d == null) {
                    TLog.e("The device can not find init camera2: %s", SelesVideoCamera2.this.c);
                    return false;
                }
                SelesVideoCamera2.this.onInitConfig(SelesVideoCamera2.this.d);
                return true;
            }

            @Override
            public TuSdkSize previewOptimalSize() {
                return SelesVideoCamera2.this.computerPreviewOptimalSize();
            }

            @Override
            public void onCameraWillOpen(final SurfaceTexture surfaceTexture) {
                if (surfaceTexture == null) {
                    return;
                }
                SelesVideoCamera2.this.g = new Surface(surfaceTexture);
                try {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    SelesVideoCamera2.this.b.openCamera(SelesVideoCamera2.this.c, SelesVideoCamera2.this.getCameraStateCallback(), SelesVideoCamera2.this.mHandler);
                }
                catch (CameraAccessException ex) {
                    TLog.e((Throwable)ex, "SelesVideoCamera2 asyncInitCamera", new Object[0]);
                }
            }
            
            @Override
            public void onCameraStarted() {
            }
            
            @Override
            public ImageOrientation previewOrientation() {
                return SelesVideoCamera2.this.b();
            }
        };
        this.f = ((cameraFacing == null) ? CameraConfigs.CameraFacing.Back : cameraFacing);
        this.b = Camera2Helper.cameraManager(context);
        (this.e = new HandlerThread("TuSDK_L_Camera")).start();
        this.mHandler = new Handler(this.e.getLooper());
        super.setCameraEngine(this.h);
    }
    
    @Deprecated
    @Override
    public void setCameraEngine(final SelesVideoCamera2Engine selesVideoCamera2Engine) {
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.a();
    }
    
    private void a() {
        if (this.e == null) {
            return;
        }
        try {
            this.e.quitSafely();
            this.e.join();
            this.e = null;
        }
        catch (InterruptedException ex) {
            TLog.e(ex, "release Handler error", new Object[0]);
        }
    }
    
    protected void onInitConfig(final CameraCharacteristics cameraCharacteristics) {
    }
    
    @Override
    protected void onCameraStarted() {
        super.onCameraStarted();
    }
    
    public void rotateCamera() {
        final int cameraCounts = Camera2Helper.cameraCounts(this.getContext());
        if (!this.isCapturing() || cameraCounts < 2) {
            return;
        }
        this.f = ((this.f == CameraConfigs.CameraFacing.Front) ? CameraConfigs.CameraFacing.Back : CameraConfigs.CameraFacing.Front);
        this.startCameraCapture();
    }
    
    protected abstract CameraDevice.StateCallback getCameraStateCallback();
    
    protected abstract TuSdkSize computerPreviewOptimalSize();
    
    private ImageOrientation b() {
        return computerOutputOrientation(this.getContext(), this.d, this.isHorizontallyMirrorRearFacingCamera(), this.isHorizontallyMirrorFrontFacingCamera(), this.getOutputImageOrientation());
    }
    
    public static ImageOrientation computerOutputOrientation(final Context context, final CameraCharacteristics cameraCharacteristics, final boolean b, final boolean b2, final InterfaceOrientation interfaceOrientation) {
        return computerOutputOrientation(cameraCharacteristics, ContextUtils.getInterfaceRotation(context), b, b2, interfaceOrientation);
    }
    
    public static ImageOrientation computerOutputOrientation(final CameraCharacteristics cameraCharacteristics, InterfaceOrientation portrait, final boolean b, final boolean b2, InterfaceOrientation portrait2) {
        if (portrait == null) {
            portrait = InterfaceOrientation.Portrait;
        }
        if (portrait2 == null) {
            portrait2 = InterfaceOrientation.Portrait;
        }
        int intValue = 0;
        int n = 1;
        if (cameraCharacteristics != null) {
            intValue = (int)cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
            n = (((int)cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == 1) ? 1 : 0);
        }
        int degree = portrait.getDegree();
        if (portrait2 != null) {
            degree += portrait2.getDegree();
        }
        if (n != 0) {
            final InterfaceOrientation withDegrees = InterfaceOrientation.getWithDegrees(intValue - degree);
            if (b) {
                switch (withDegrees.ordinal()) {
                    case 1: {
                        return ImageOrientation.DownMirrored;
                    }
                    case 2: {
                        return ImageOrientation.LeftMirrored;
                    }
                    case 3: {
                        return ImageOrientation.RightMirrored;
                    }
                    default: {
                        return ImageOrientation.UpMirrored;
                    }
                }
            }
            else {
                switch (withDegrees.ordinal()) {
                    case 1: {
                        return ImageOrientation.Up;
                    }
                    case 2: {
                        return ImageOrientation.Left;
                    }
                    case 3: {
                        return ImageOrientation.Right;
                    }
                    default: {
                        return ImageOrientation.Down;
                    }
                }
            }
        }
        else {
            final InterfaceOrientation withDegrees2 = InterfaceOrientation.getWithDegrees(intValue + degree);
            if (b2) {
                switch (withDegrees2.ordinal()) {
                    case 1: {
                        return ImageOrientation.UpMirrored;
                    }
                    case 2: {
                        return ImageOrientation.LeftMirrored;
                    }
                    case 3: {
                        return ImageOrientation.RightMirrored;
                    }
                    default: {
                        return ImageOrientation.DownMirrored;
                    }
                }
            }
            else {
                switch (withDegrees2.ordinal()) {
                    case 1: {
                        return ImageOrientation.Down;
                    }
                    case 2: {
                        return ImageOrientation.Left;
                    }
                    case 3: {
                        return ImageOrientation.Right;
                    }
                    default: {
                        return ImageOrientation.Up;
                    }
                }
            }
        }
    }
}
