// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.api.engine;

import android.graphics.RectF;
import android.opengl.GLES20;

import com.example.myvideoeditorapp.kore.api.engine.TuSdkEngine;
import com.example.myvideoeditorapp.kore.api.engine.TuSdkEngineImpl;
import com.example.myvideoeditorapp.kore.api.engine.TuSdkEngineInputImage;
import com.example.myvideoeditorapp.kore.api.engine.TuSdkEngineInputSurfaceImpl;
import com.example.myvideoeditorapp.kore.api.engine.TuSdkEngineInputTextureImpl;
import com.example.myvideoeditorapp.kore.api.engine.TuSdkEngineInputYUVDataImpl;
import com.example.myvideoeditorapp.kore.api.engine.TuSdkEngineOrientation;
import com.example.myvideoeditorapp.kore.api.engine.TuSdkEngineOrientationImpl;
import com.example.myvideoeditorapp.kore.api.engine.TuSdkEngineOutputImage;
import com.example.myvideoeditorapp.kore.api.engine.TuSdkEngineOutputImageImpl;
import com.example.myvideoeditorapp.kore.api.engine.TuSdkEngineProcessor;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCorpBuilder;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.type.ColorFormatType;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.hardware.CameraConfigs;
import com.example.myvideoeditorapp.kore.utils.hardware.InterfaceOrientation;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;
import com.example.myvideoeditorapp.kore.utils.monitor.TuSdkMonitor;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerGroup;
import com.example.myvideoeditorapp.tv.api.video.preproc.filter.TuSDKVideoProcesser;
import com.example.myvideoeditorapp.tv.video.editor.TuSDKMediaEffectsDataManager;
import com.example.myvideoeditorapp.tv.video.editor.TuSdkMediaEffectData;

import java.util.List;

public class TuSdkFilterEngineImpl implements TuSdkFilterEngine
{
    private TuSdkEngine a;
    private TuSdkEngineOrientation b;
    private TuSdkEngineVideoProcessorImpl c;
    private TuSdkEngineOutputImage d;
    private TuSdkFilterEngineListener e;
    private int f;
    private int g;
    
    @Override
    public void setListener(final TuSdkFilterEngineListener e) {
        this.e = e;
        if (this.c != null) {
            this.c.setFilterChangedListener(this.e);
        }
    }
    
    public TuSdkFilterEngineImpl(final boolean b) {
        TLog.dump("TuSdkFilterEngine create() managedGLLifecycle : %s", new Object[] { b });
        this.a = (TuSdkEngine)new TuSdkEngineImpl(b);
        (this.b = (TuSdkEngineOrientation)new TuSdkEngineOrientationImpl()).setHorizontallyMirrorFrontFacingCamera(true);
        this.a.setEngineOrientation(this.b);
        this.a.setEngineInputImage((TuSdkEngineInputImage)new TuSdkEngineInputYUVDataImpl());
        this.c = new TuSdkEngineVideoProcessorImpl();
        this.a.setEngineProcessor((TuSdkEngineProcessor)this.c);
        this.d = (TuSdkEngineOutputImage)new TuSdkEngineOutputImageImpl();
        this.a.setEngineOutputImage(this.d);
    }
    
    public TuSdkFilterEngineImpl(final boolean b, final boolean b2) {
        TLog.dump("TuSdkFilterEngine create()  isOESTexture : %s  managedGLLifecycle : %s", new Object[] { b, b2 });
        this.a = (TuSdkEngine)new TuSdkEngineImpl(b2);
        (this.b = (TuSdkEngineOrientation)new TuSdkEngineOrientationImpl()).setHorizontallyMirrorFrontFacingCamera(true);
        this.a.setEngineOrientation(this.b);
        this.a.setEngineInputImage((TuSdkEngineInputImage)(b ? new TuSdkEngineInputSurfaceImpl() : new TuSdkEngineInputTextureImpl()));
        this.c = new TuSdkEngineVideoProcessorImpl();
        this.a.setEngineProcessor((TuSdkEngineProcessor)this.c);
        this.d = (TuSdkEngineOutputImage)new TuSdkEngineOutputImageImpl();
        this.a.setEngineOutputImage(this.d);
    }
    
    @Override
    public CameraConfigs.CameraFacing getCameraFacing() {
        if (this.b == null) {
            return CameraConfigs.CameraFacing.Back;
        }
        return this.b.getCameraFacing();
    }
    
    @Override
    public void setCameraFacing(final CameraConfigs.CameraFacing cameraFacing) {
        if (this.b == null) {
            return;
        }
        this.b.setCameraFacing(cameraFacing);
    }
    
    @Override
    public void setInterfaceOrientation(final InterfaceOrientation interfaceOrientation) {
        if (this.b == null) {
            return;
        }
        this.b.setInterfaceOrientation(interfaceOrientation);
    }
    
    @Override
    public void setInputImageOrientation(final ImageOrientation inputOrientation) {
        if (this.b == null) {
            return;
        }
        this.b.setInputOrientation(inputOrientation);
    }
    
    @Override
    public void setOutputImageOrientation(final ImageOrientation outputOrientation) {
        if (this.b == null) {
            return;
        }
        this.b.setOutputOrientation(outputOrientation);
    }
    
    @Override
    public void setCordinateBuilder(final SelesVerticeCoordinateCorpBuilder inputTextureCoordinateBuilder) {
        if (this.a == null) {
            return;
        }
        this.a.setInputTextureCoordinateBuilder(inputTextureCoordinateBuilder);
    }
    
    @Override
    public TuSdkSize getOutputImageSize() {
        return this.b.getOutputSize();
    }
    
    @Override
    public void release() {
        if (this.a != null) {
            this.c.release();
            this.a.release();
            this.a = null;
            TLog.dump("TuSdkFilterEngine release()", new Object[0]);
        }
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.release();
        super.finalize();
    }
    
    @Override
    public void onSurfaceCreated() {
        if (this.a == null) {
            return;
        }
        this.a.prepareInGlThread();
    }
    
    @Override
    public void onSurfaceChanged(final int f, final int g) {
        this.f = f;
        this.g = g;
    }
    
    @Override
    public void setDisplayRect(final RectF rectF, final float n) {
        this.c.setDisplayRect(rectF, n);
    }
    
    @Override
    public void setEnableLiveSticker(final boolean b) {
        if (this.c == null) {
            return;
        }
        this.c.setEnableLiveSticker(b);
        this.c.setEnableFacePlastic(b);
    }
    
    @Override
    public void setEnableFaceDetection(final boolean enableFacePlastic) {
        if (this.c == null) {
            return;
        }
        this.c.setEnableFacePlastic(enableFacePlastic);
    }
    
    @Override
    public void setDetectScale(final float detectScale) {
        if (this.c == null) {
            return;
        }
        this.c.setDetectScale(detectScale);
    }
    
    @Override
    public void switchFilter(final String s) {
        if (this.c == null) {
            return;
        }
        this.c.switchFilter(s);
    }
    
    @Override
    public void removeAllLiveSticker() {
        if (this.c == null) {
            return;
        }
        this.c.removeAllLiveSticker();
    }
    
    @Override
    public void showGroupSticker(final StickerGroup stickerGroup) {
        if (this.c == null) {
            return;
        }
        this.c.showGroupSticker(stickerGroup);
    }
    
    @Override
    public void setEnableOutputYUVData(final boolean enableOutputYUVData) {
        if (this.d == null) {
            return;
        }
        this.d.setEnableOutputYUVData(enableOutputYUVData);
    }
    
    @Override
    public void setYuvOutputImageFormat(final ColorFormatType yuvOutputImageFormat) {
        if (this.d == null) {
            return;
        }
        this.d.setYuvOutputImageFormat(yuvOutputImageFormat);
    }
    
    @Override
    public void setYuvOutputOrienation(final ImageOrientation yuvOutputOrienation) {
        if (this.b == null) {
            return;
        }
        this.b.setYuvOutputOrienation(yuvOutputOrienation);
    }
    
    @Override
    public void processFrame(final byte[] array, final int n, final int n2, final long n3) {
        if (this.a == null) {
            return;
        }
        this.a.processFrame(array, n, n2, n3);
    }
    
    @Override
    public int processFrame(final int n, final int n2, final int n3, final long n4) {
        return this.processFrame(n, true, n2, n3, n4);
    }
    
    @Override
    public int processFrame(final int n, final boolean b, final int n2, final int n3, final long n4) {
        if (this.a == null) {
            return n;
        }
        GLES20.glFinish();
        this.a.processFrame(n, n2, n3, n4);
        GLES20.glFinish();
        final int n5 = b ? n : this.d.getTerminalTexture();
        if (b) {
            final SelesFramebuffer terminalFrameBuffer = this.d.getTerminalFrameBuffer();
            if (terminalFrameBuffer == null) {
                return n;
            }
            terminalFrameBuffer.activateFramebuffer();
            GLES20.glBindTexture(3553, n);
            GLES20.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, n2, n3);
            GLES20.glBindFramebuffer(36160, 0);
            GLES20.glBindTexture(3553, 0);
        }
        TuSdkMonitor.glMonitor().checkGLFrameImage(" Engine processFrame  texture ", n2, n3);
        TuSdkMonitor.glMonitor().frameCountIncrement();
        return n5;
    }
    
    @Override
    public void snatchFrame(final byte[] array) {
        if (this.d == null) {
            return;
        }
        this.d.snatchFrame(array);
    }
    
    @Override
    public InterfaceOrientation getDeviceOrient() {
        if (this.b == null) {
            return null;
        }
        return this.b.getDeviceOrient();
    }
    
    @Override
    public void takeShot() {
        if (this.c == null) {
            return;
        }
        this.c.takeShot();
    }
    
    @Override
    public void setOriginalCaptureOrientation(final boolean originalCaptureOrientation) {
        if (this.b == null) {
            return;
        }
        this.b.setOriginalCaptureOrientation(originalCaptureOrientation);
    }
    
    @Override
    public void setOutputCaptureMirrorEnabled(final boolean outputCaptureMirrorEnabled) {
        if (this.b == null) {
            return;
        }
        this.b.setOutputCaptureMirrorEnabled(outputCaptureMirrorEnabled);
    }
    
    @Override
    public void asyncProcessPictureData(final byte[] array, final InterfaceOrientation interfaceOrientation) {
        if (this.c == null && this.c.getImageEngine() == null) {
            return;
        }
        if (this.c.getLiveStickerPlayController() != null) {
            this.c.getLiveStickerPlayController().pauseAllStickers();
        }
        this.c.getImageEngine().asyncProcessPictureData(array, interfaceOrientation);
    }
    
    @Override
    public FaceAligment[] getFaceFeatures() {
        if (this.c == null) {
            return null;
        }
        return this.c.getFaceFeatures();
    }
    
    @Override
    public void setFaceDetectionDelegate(final TuSdkEngineProcessor.TuSdkVideoProcesserFaceDetectionDelegate faceDetectionDelegate) {
        if (this.c == null) {
            return;
        }
        this.c.setFaceDetectionDelegate(faceDetectionDelegate);
    }
    
    @Override
    public float getDeviceAngle() {
        if (this.b == null) {
            return 0.0f;
        }
        return this.b.getDeviceAngle();
    }
    
    @Override
    public void addTerminalNode(final SelesContext.SelesInput selesInput) {
        if (this.c == null) {
            return;
        }
        this.c.addTerminalNode(selesInput);
    }
    
    @Override
    public void setMediaEffectDelegate(final TuSDKVideoProcesser.TuSDKVideoProcessorMediaEffectDelegate mediaEffectDelegate) {
        if (this.c == null) {
            return;
        }
        this.c.setMediaEffectDelegate(mediaEffectDelegate);
    }
    
    @Override
    public void setManagerDelegate(final TuSDKMediaEffectsDataManager.TuSDKMediaEffectsManagerDelegate dataManagerDelegate) {
        if (this.c == null) {
            return;
        }
        this.c.setDataManagerDelegate(dataManagerDelegate);
    }
    
    @Override
    public boolean addMediaEffectData(final TuSdkMediaEffectData tuSdkMediaEffectData) {
        return this.c != null && this.c.addMediaEffectData(tuSdkMediaEffectData);
    }
    
    @Override
    public boolean removeMediaEffectData(final TuSdkMediaEffectData tuSdkMediaEffectData) {
        return this.c != null && this.c.removeMediaEffectData(tuSdkMediaEffectData);
    }
    
    @Override
    public <T extends TuSdkMediaEffectData> List<T> mediaEffectsWithType(final TuSdkMediaEffectData.TuSdkMediaEffectDataType tuSdkMediaEffectDataType) {
        if (this.c == null) {
            return null;
        }
        return this.c.mediaEffectsWithType(tuSdkMediaEffectDataType);
    }
    
    @Override
    public List<TuSdkMediaEffectData> getAllMediaEffectData() {
        if (this.c == null) {
            return null;
        }
        return this.c.getAllMediaEffectData();
    }
    
    @Override
    public void removeMediaEffectsWithType(final TuSdkMediaEffectData.TuSdkMediaEffectDataType tuSdkMediaEffectDataType) {
        if (this.c == null) {
            return;
        }
        this.c.removeMediaEffectsWithType(tuSdkMediaEffectDataType);
    }
    
    @Override
    public void removeAllMediaEffects() {
        if (this.c == null) {
            return;
        }
        this.c.removeAllMediaEffects();
    }
    
    @Override
    public boolean hasMediaAudioEffects() {
        if (this.c == null) {
            return false;
        }
        final List<TuSdkMediaEffectData> mediaEffectsWithType = this.c.mediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeAudio);
        return mediaEffectsWithType != null && mediaEffectsWithType.size() > 0;
    }
    
    @Override
    public void setFilterChangedListener(final TuSdkFilterEngineListener filterChangedListener) {
        if (this.c == null) {
            return;
        }
        this.c.setFilterChangedListener(filterChangedListener);
    }
    
    @Override
    public boolean isGroupStickerUsed(final StickerGroup stickerGroup) {
        return this.c != null && this.c.getLiveStickerPlayController() != null && this.c.getLiveStickerPlayController().isGroupStickerUsed(stickerGroup);
    }
}
