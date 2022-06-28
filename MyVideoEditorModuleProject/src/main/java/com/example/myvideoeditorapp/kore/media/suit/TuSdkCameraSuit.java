// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.suit;

import android.media.MediaFormat;

import com.example.myvideoeditorapp.kore.media.camera.TuSdkCamera;
import com.example.myvideoeditorapp.kore.media.camera.TuSdkCameraImpl;
import com.example.myvideoeditorapp.kore.media.record.TuSdkCameraRecorder;
import com.example.myvideoeditorapp.kore.media.record.TuSdkMediaRecordHub;

public class TuSdkCameraSuit
{
    public static TuSdkCamera createCamera() {
        return new TuSdkCameraImpl();
    }
    
    public static TuSdkMediaRecordHub cameraRecorder(final MediaFormat outputVideoFormat, final MediaFormat outputAudioFormat, final TuSdkCamera tuSdkCamera, final TuSdkMediaRecordHub.TuSdkMediaRecordHubListener recordListener) {
        final TuSdkCameraRecorder tuSdkCameraRecorder = new TuSdkCameraRecorder();
        tuSdkCameraRecorder.setOutputVideoFormat(outputVideoFormat);
        tuSdkCameraRecorder.setOutputAudioFormat(outputAudioFormat);
        tuSdkCameraRecorder.appendRecordSurface(tuSdkCamera);
        tuSdkCameraRecorder.setRecordListener(recordListener);
        return tuSdkCameraRecorder;
    }
}
