// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.camera;

import android.graphics.Bitmap;

import com.example.myvideoeditorapp.kore.TuSdkResult;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import com.example.myvideoeditorapp.kore.seles.sources.SelesPicture;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;

import java.io.IOException;

public interface TuSdkCameraShot
{
    void configure(final TuSdkCameraBuilder p0);
    
    void changeStatus(final TuSdkCamera.TuSdkCameraStatus p0);
    
    void setDetectionImageFace(final TuSdkCameraShotFaceFaceAligment p0);
    
    void setDetectionShotFilter(final TuSdkCameraShotFilter p0);
    
    void takeJpegPicture(final TuSdkResult p0, final TuSdkCameraShotResultListener p1);
    
    boolean isAutoReleaseAfterCaptured();
    
    void processData(final TuSdkResult p0) throws IOException;
    
    public interface TuSdkCameraShotFilter
    {
        SelesOutInput getFilters(final FaceAligment[] p0, final SelesPicture p1);
    }
    
    public interface TuSdkCameraShotFaceFaceAligment
    {
        FaceAligment[] detectionImageFace(final Bitmap p0, final ImageOrientation p1);
    }
    
    public interface TuSdkCameraShotResultListener
    {
        void onShotResule(final byte[] p0) throws IOException;
    }
    
    public interface TuSdkCameraShotListener
    {
        void onCameraWillShot(final TuSdkResult p0);
        
        void onCameraShotFailed(final TuSdkResult p0);
        
        void onCameraShotData(final TuSdkResult p0);
        
        void onCameraShotBitmap(final TuSdkResult p0);
    }
}
