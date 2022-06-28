// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.camera;

import android.hardware.Camera;
import android.content.Context;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.listener.TuSdkOrientationEventListener;
import com.example.myvideoeditorapp.kore.utils.ContextUtils;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.hardware.InterfaceOrientation;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;

public class TuSdkCameraOrientationImpl implements TuSdkCameraOrientation
{
    private ImageOrientation a;
    private InterfaceOrientation b;
    private boolean c;
    private boolean d;
    private boolean e;
    private final TuSdkOrientationEventListener f;
    private TuSdkCameraBuilder g;
    
    public InterfaceOrientation getOutputImageOrientation() {
        return this.b;
    }
    
    public void setOutputImageOrientation(final InterfaceOrientation b) {
        if (b == null) {
            return;
        }
        this.b = b;
    }
    
    public boolean isHorizontallyMirrorFrontFacingCamera() {
        return this.c;
    }
    
    public void setHorizontallyMirrorFrontFacingCamera(final boolean c) {
        this.c = c;
    }
    
    public boolean isHorizontallyMirrorRearFacingCamera() {
        return this.d;
    }
    
    public void setHorizontallyMirrorRearFacingCamera(final boolean d) {
        this.d = d;
    }
    
    public boolean isDisableMirrorFrontFacing() {
        return this.e;
    }
    
    public void setDisableMirrorFrontFacing(final boolean e) {
        this.e = e;
    }
    
    @Override
    public ImageOrientation previewOrientation() {
        return this.a;
    }
    
    public TuSdkCameraOrientationImpl() {
        this.a = ImageOrientation.Up;
        this.b = InterfaceOrientation.Portrait;
        this.f = new TuSdkOrientationEventListener(TuSdkContext.context());
    }
    
    public void setDeviceOrientListener(final TuSdkOrientationEventListener.TuSdkOrientationDelegate tuSdkOrientationDelegate, final TuSdkOrientationEventListener.TuSdkOrientationDegreeDelegate tuSdkOrientationDegreeDelegate) {
        this.f.setDelegate(tuSdkOrientationDelegate, tuSdkOrientationDegreeDelegate);
    }
    
    @Override
    public void configure(final TuSdkCameraBuilder g) {
        if (g == null) {
            TLog.e("%s configure builder is empty.", "TuSdkCameraOrientationImpl");
            return;
        }
        this.g = g;
        this.setHorizontallyMirrorRearFacingCamera(true);
        this.f.enable();
        this.a = this.a();
    }
    
    @Override
    public void changeStatus(final TuSdkCamera.TuSdkCameraStatus tuSdkCameraStatus) {
        if (tuSdkCameraStatus == TuSdkCamera.TuSdkCameraStatus.CAMERA_START_PREVIEW || tuSdkCameraStatus == TuSdkCamera.TuSdkCameraStatus.CAMERA_PREPARE_SHOT) {
            this.f.enable();
        }
        else {
            this.f.disable();
        }
    }
    
    private ImageOrientation a() {
        if (this.g == null) {
            return ImageOrientation.Up;
        }
        return computerOutputOrientation(TuSdkContext.context(), this.g.getInfo(), this.isHorizontallyMirrorRearFacingCamera(), this.isHorizontallyMirrorFrontFacingCamera(), this.getOutputImageOrientation());
    }
    
    @Override
    public ImageOrientation captureOrientation() {
        if (this.g == null) {
            return ImageOrientation.Up;
        }
        final boolean b = this.isHorizontallyMirrorFrontFacingCamera() && !this.isDisableMirrorFrontFacing();
        InterfaceOrientation interfaceOrientation = this.f.getOrien();
        if ((!this.g.isBackFacingCameraPresent() && !b) || (this.g.isBackFacingCameraPresent() && this.isHorizontallyMirrorRearFacingCamera())) {
            interfaceOrientation = InterfaceOrientation.getWithDegrees(this.f.getDeviceAngle());
        }
        return computerOutputOrientation(this.g.getInfo(), interfaceOrientation, this.isHorizontallyMirrorRearFacingCamera(), b, this.getOutputImageOrientation());
    }
    
    public static ImageOrientation computerOutputOrientation(final Context context, final Camera.CameraInfo cameraInfo, final boolean b, final boolean b2, final InterfaceOrientation interfaceOrientation) {
        return computerOutputOrientation(cameraInfo, ContextUtils.getInterfaceRotation(context), b, b2, interfaceOrientation);
    }

    public static ImageOrientation computerOutputOrientation(Camera.CameraInfo var0, InterfaceOrientation var1, boolean var2, boolean var3, InterfaceOrientation var4) {
        if (var1 == null) {
            var1 = InterfaceOrientation.Portrait;
        }

        if (var4 == null) {
            var4 = InterfaceOrientation.Portrait;
        }

        int var5 = 0;
        boolean var6 = true;
        if (var0 != null) {
            var5 = var0.orientation;
            var6 = var0.facing == 0;
        }

        int var7 = var1.getDegree();
        if (var4 != null) {
            var7 += var4.getDegree();
        }

        InterfaceOrientation var8;
        if (var6) {
            var8 = InterfaceOrientation.getWithDegrees(var5 - var7);
            if (var2) {
                switch(var8) {
                    case PortraitUpsideDown:
                        return ImageOrientation.DownMirrored;
                    case LandscapeLeft:
                        return ImageOrientation.LeftMirrored;
                    case LandscapeRight:
                        return ImageOrientation.RightMirrored;
                    case Portrait:
                    default:
                        return ImageOrientation.UpMirrored;
                }
            } else {
                switch(var8) {
                    case PortraitUpsideDown:
                        return ImageOrientation.Up;
                    case LandscapeLeft:
                        return ImageOrientation.Left;
                    case LandscapeRight:
                        return ImageOrientation.Right;
                    case Portrait:
                    default:
                        return ImageOrientation.Down;
                }
            }
        } else {
            var8 = InterfaceOrientation.getWithDegrees(var5 + var7);
            if (var3) {
                switch(var8) {
                    case PortraitUpsideDown:
                        return ImageOrientation.UpMirrored;
                    case LandscapeLeft:
                        return ImageOrientation.LeftMirrored;
                    case LandscapeRight:
                        return ImageOrientation.RightMirrored;
                    case Portrait:
                    default:
                        return ImageOrientation.DownMirrored;
                }
            } else {
                switch(var8) {
                    case PortraitUpsideDown:
                        return ImageOrientation.Down;
                    case LandscapeLeft:
                        return ImageOrientation.Left;
                    case LandscapeRight:
                        return ImageOrientation.Right;
                    case Portrait:
                    default:
                        return ImageOrientation.Up;
                }
            }
        }
    }
}
