// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.api.engine;

import android.graphics.Bitmap;
import android.graphics.RectF;

import com.example.myvideoeditorapp.kore.api.engine.TuSdkEngineProcessor;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkFilterListener;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCorpBuilder;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.type.ColorFormatType;
import com.example.myvideoeditorapp.kore.utils.hardware.CameraConfigs;
import com.example.myvideoeditorapp.kore.utils.hardware.InterfaceOrientation;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerGroup;
import com.example.myvideoeditorapp.tv.api.video.preproc.filter.TuSDKVideoProcesser;
import com.example.myvideoeditorapp.tv.video.editor.TuSDKMediaEffectsDataManager;
import com.example.myvideoeditorapp.tv.video.editor.TuSdkMediaEffectData;

import java.nio.IntBuffer;
import java.util.List;

public interface TuSdkFilterEngine
{
    void release();
    
    void setListener(final TuSdkFilterEngineListener p0);
    
    void onSurfaceCreated();
    
    void onSurfaceChanged(final int p0, final int p1);
    
    void setDisplayRect(final RectF p0, final float p1);
    
    void setDetectScale(final float p0);
    
    void setEnableLiveSticker(final boolean p0);
    
    void setEnableFaceDetection(final boolean p0);
    
    void setEnableOutputYUVData(final boolean p0);
    
    void setYuvOutputImageFormat(final ColorFormatType p0);
    
    void setYuvOutputOrienation(final ImageOrientation p0);
    
    CameraConfigs.CameraFacing getCameraFacing();
    
    void setCameraFacing(final CameraConfigs.CameraFacing p0);
    
    void setInterfaceOrientation(final InterfaceOrientation p0);
    
    void takeShot();
    
    void setInputImageOrientation(final ImageOrientation p0);
    
    void setOutputImageOrientation(final ImageOrientation p0);
    
    void setCordinateBuilder(final SelesVerticeCoordinateCorpBuilder p0);
    
    TuSdkSize getOutputImageSize();
    
    void switchFilter(final String p0);
    
    void removeAllLiveSticker();
    
    void showGroupSticker(final StickerGroup p0);
    
    void processFrame(final byte[] p0, final int p1, final int p2, final long p3);
    
    int processFrame(final int p0, final int p1, final int p2, final long p3);
    
    int processFrame(final int p0, final boolean p1, final int p2, final int p3, final long p4);
    
    void snatchFrame(final byte[] p0);
    
    InterfaceOrientation getDeviceOrient();
    
    void setOriginalCaptureOrientation(final boolean p0);
    
    void setOutputCaptureMirrorEnabled(final boolean p0);
    
    void asyncProcessPictureData(final byte[] p0, final InterfaceOrientation p1);
    
    FaceAligment[] getFaceFeatures();
    
    void setFaceDetectionDelegate(final TuSdkEngineProcessor.TuSdkVideoProcesserFaceDetectionDelegate p0);
    
    float getDeviceAngle();
    
    void addTerminalNode(final SelesContext.SelesInput p0);
    
    void setMediaEffectDelegate(final TuSDKVideoProcesser.TuSDKVideoProcessorMediaEffectDelegate p0);
    
    void setManagerDelegate(final TuSDKMediaEffectsDataManager.TuSDKMediaEffectsManagerDelegate p0);
    
    boolean addMediaEffectData(final TuSdkMediaEffectData p0);
    
    boolean removeMediaEffectData(final TuSdkMediaEffectData p0);
    
     <T extends TuSdkMediaEffectData> List<T> mediaEffectsWithType(final TuSdkMediaEffectData.TuSdkMediaEffectDataType p0);
    
    List<TuSdkMediaEffectData> getAllMediaEffectData();
    
    void removeMediaEffectsWithType(final TuSdkMediaEffectData.TuSdkMediaEffectDataType p0);
    
    void removeAllMediaEffects();
    
    boolean hasMediaAudioEffects();
    
    void setFilterChangedListener(final TuSdkFilterEngineListener p0);
    
    boolean isGroupStickerUsed(final StickerGroup p0);
    
    public interface TuSdkFilterEngineListener extends TuSdkFilterListener
    {
        void onPictureDataCompleted(final IntBuffer p0, final TuSdkSize p1);
        
        void onPreviewScreenShot(final Bitmap p0);
    }
}
