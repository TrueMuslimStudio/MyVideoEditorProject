// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.camera;

import com.example.myvideoeditorapp.kore.face.FaceAligment;
import android.graphics.Bitmap;
import com.example.myvideoeditorapp.kore.exif.ExifInterface;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.utils.RectHelper;
import com.example.myvideoeditorapp.kore.seles.sources.SelesPicture;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;
import com.example.myvideoeditorapp.kore.utils.image.ExifHelper;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.TuSdkResult;
import com.example.myvideoeditorapp.kore.TuSdkContext;
import android.hardware.Camera;
import android.media.MediaPlayer;

import java.io.IOException;


public class TuSdkCameraShotImpl implements TuSdkCameraShot
{
    private boolean a;
    private boolean b;
    private boolean c;
    private int d;
    private MediaPlayer e;
    private TuSdkCameraShotListener f;
    private TuSdkCameraShotFaceFaceAligment g;
    private TuSdkCameraShotFilter h;
    private Camera.ShutterCallback i;
    private TuSdkCameraBuilder j;
    
    public TuSdkCameraShotImpl() {
        this.i = (Camera.ShutterCallback)new Camera.ShutterCallback() {
            public void onShutter() {
            }
        };
    }
    
    @Override
    public boolean isAutoReleaseAfterCaptured() {
        return this.a;
    }
    
    public void setAutoReleaseAfterCaptured(final boolean a) {
        this.a = a;
    }
    
    public boolean isOutputImageData() {
        return this.b;
    }
    
    public void setOutputImageData(final boolean b) {
        this.b = b;
    }
    
    public boolean isDisableCaptureSound() {
        return this.c;
    }
    
    public void setDisableCaptureSound(final boolean c) {
        this.c = c;
    }
    
    public int getCaptureSoundRawId() {
        return this.d;
    }
    
    public void setCaptureSoundRawId(final int d) {
        this.d = d;
        if (this.e != null) {
            this.e.release();
            this.e = null;
        }
        if (this.d != 0) {
            this.setDisableCaptureSound(true);
            this.e = MediaPlayer.create(TuSdkContext.context(), this.d);
        }
    }
    
    private void a() {
        if (this.e == null || !this.c) {
            return;
        }
        this.e.start();
    }
    
    public void setShotListener(final TuSdkCameraShotListener f) {
        this.f = f;
    }
    
    public void setShutterCallback(final Camera.ShutterCallback i) {
        this.i = i;
    }
    
    public Camera.ShutterCallback getShutterCallback() {
        if (this.isDisableCaptureSound()) {
            return null;
        }
        return this.i;
    }
    
    @Override
    public void takeJpegPicture(final TuSdkResult tuSdkResult, final TuSdkCameraShotResultListener tuSdkCameraShotResultListener) {
        if (this.j == null || this.j.getOrginCamera() == null) {
            TLog.w("%s takeJpegPicture need OrginCamera.", "TuSdkCameraShotImpl");
            return;
        }
        final Camera orginCamera = this.j.getOrginCamera();
        if (this.f != null) {
            this.f.onCameraWillShot(tuSdkResult);
        }
        ThreadHelper.runThread(new Runnable() {
            @Override
            public void run() {
                try {
                    orginCamera.takePicture(TuSdkCameraShotImpl.this.getShutterCallback(), (Camera.PictureCallback)null, (Camera.PictureCallback)new Camera.PictureCallback() {
                        public void onPictureTaken(final byte[] array, final Camera camera) {
                            TuSdkCameraShotImpl.this.a();
                            try {
                                tuSdkCameraShotResultListener.onShotResule(array);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                }
                catch (RuntimeException ex) {
                    TLog.e(ex, "%s takeJpegPicture failed.", "TuSdkCameraShotImpl");
                    try {
                        tuSdkCameraShotResultListener.onShotResule(null);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });
    }
    
    @Override
    public void processData(final TuSdkResult tuSdkResult) throws IOException {
        if (tuSdkResult.imageData == null) {
            if (this.f != null) {
                this.f.onCameraShotFailed(tuSdkResult);
            }
            return;
        }
        tuSdkResult.metadata = ExifHelper.getExifInterface(tuSdkResult.imageData);
        if (this.b) {
            if (this.f != null) {
                this.f.onCameraShotData(tuSdkResult);
            }
            return;
        }
        ThreadHelper.runThread(new Runnable() {
            @Override
            public void run() {
                Bitmap image = BitmapHelper.imageDecode(tuSdkResult.imageData, true);
                if (image == null) {
                    TLog.e("%s conver bitmap failed, result: %s", "TuSdkCameraShotImpl", tuSdkResult);
                    if (TuSdkCameraShotImpl.this.f != null) {
                        TuSdkCameraShotImpl.this.f.onCameraShotFailed(tuSdkResult);
                    }
                    return;
                }
                if (tuSdkResult.imageOrientation != ImageOrientation.Up || tuSdkResult.imageOrientation != ImageOrientation.UpMirrored) {
                    image = BitmapHelper.imageResize(image, tuSdkResult.outputSize, false, tuSdkResult.imageOrientation);
                    tuSdkResult.imageOrientation = ImageOrientation.Up;
                }
                tuSdkResult.imageData = null;
                tuSdkResult.image = image;
                FaceAligment[] detectionImageFace = null;
                if (TuSdkCameraShotImpl.this.g != null) {
                    detectionImageFace = TuSdkCameraShotImpl.this.g.detectionImageFace(image, tuSdkResult.imageOrientation);
                }
                final TuSdkSize computerScaleSize = BitmapHelper.computerScaleSize(image, tuSdkResult.outputSize, false, false);
                final SelesPicture selesPicture = new SelesPicture(image, false);
                selesPicture.setScaleSize(computerScaleSize);
                if (tuSdkResult.imageSizeRatio == 0.0f) {
                    tuSdkResult.imageSizeRatio = tuSdkResult.outputSize.minMaxRatio();
                }
                selesPicture.setOutputRect(RectHelper.computerMinMaxSideInRegionRatio(selesPicture.getScaleSize(), tuSdkResult.imageSizeRatio));
                selesPicture.setInputRotation(tuSdkResult.imageOrientation);
                if (TuSdkCameraShotImpl.this.h != null) {
                    selesPicture.addTarget(TuSdkCameraShotImpl.this.h.getFilters(detectionImageFace, selesPicture), 0);
                }
                selesPicture.processImage();
                tuSdkResult.image = selesPicture.imageFromCurrentlyProcessedOutput();
                tuSdkResult.outputSize = TuSdkSize.create(tuSdkResult.image);
                if (tuSdkResult.metadata == null) {
                    tuSdkResult.metadata.setTagValue(ExifInterface.TAG_IMAGE_WIDTH, tuSdkResult.outputSize.width);
                    tuSdkResult.metadata.setTagValue(ExifInterface.TAG_IMAGE_LENGTH, tuSdkResult.outputSize.height);
                    tuSdkResult.metadata.setTagValue(ExifInterface.TAG_ORIENTATION, tuSdkResult.imageOrientation.getExifOrientation());
                }
                if (TuSdkCameraShotImpl.this.f != null) {
                    TuSdkCameraShotImpl.this.f.onCameraShotBitmap(tuSdkResult);
                }
            }
        });
    }
    
    @Override
    public void configure(final TuSdkCameraBuilder j) {
        if (j == null) {
            TLog.e("%s configure builder is empty.", "TuSdkCameraShotImpl");
            return;
        }
        this.j = j;
    }
    
    @Override
    public void changeStatus(final TuSdkCamera.TuSdkCameraStatus tuSdkCameraStatus) {
    }
    
    @Override
    public void setDetectionImageFace(final TuSdkCameraShotFaceFaceAligment g) {
        this.g = g;
    }
    
    @Override
    public void setDetectionShotFilter(final TuSdkCameraShotFilter h) {
        this.h = h;
    }
}
