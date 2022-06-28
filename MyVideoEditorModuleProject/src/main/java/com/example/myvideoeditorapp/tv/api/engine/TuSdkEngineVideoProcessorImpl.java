// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.api.engine;

import android.graphics.Bitmap;
import android.graphics.RectF;

import com.example.myvideoeditorapp.kore.api.engine.TuSdkEngineOrientation;
import com.example.myvideoeditorapp.kore.api.engine.TuSdkEngineOutputImage;
import com.example.myvideoeditorapp.kore.api.engine.TuSdkEngineProcessor;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.secret.SdkValid;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutput;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterManager;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.seles.tusdk.combo.TuSDKComboFilterWrapChain;
import com.example.myvideoeditorapp.kore.sticker.LiveStickerPlayController;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.utils.hardware.InterfaceOrientation;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkGPU;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerGroup;
import com.example.myvideoeditorapp.tv.api.video.preproc.filter.TuSDKVideoProcesser;
import com.example.myvideoeditorapp.tv.core.detector.FrameDetectProcessor;
import com.example.myvideoeditorapp.tv.video.editor.TuSDKMediaEffectsDataManager;
import com.example.myvideoeditorapp.tv.video.editor.TuSDKMediaEffectsManager;
import com.example.myvideoeditorapp.tv.video.editor.TuSdkMediaEffectData;
import com.example.myvideoeditorapp.tv.video.editor.TuSdkMediaFilterEffectData;
import com.example.myvideoeditorapp.tv.video.editor.TuSdkMediaParticleEffectData;
import com.example.myvideoeditorapp.tv.video.editor.TuSdkMediaSceneEffectData;
import com.example.myvideoeditorapp.tv.video.editor.TuSdkMediaStickerEffectData;
import com.example.myvideoeditorapp.tv.video.editor.TuSdkTimeRange;


import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TuSdkEngineVideoProcessorImpl implements TuSdkEngineProcessor
{
    private TuSdkEngineOrientation a;
    private TuSdkEngineOutputImage b;
    private TuSdkImageEngine c;
    private FrameDetectProcessor d;
    private FaceAligment[] e;
    private long f;
    private boolean g;
    private boolean h;
    private boolean i;
    private TuSDKComboFilterWrapChain j;
    private TuSDKMediaEffectsManager k;
    private TuSDKVideoProcesser.TuSDKVideoProcessorMediaEffectDelegate l;
    private TuSDKMediaEffectsDataManager.TuSDKMediaEffectsManagerDelegate m;
    private TuSdkFilterEngine.TuSdkFilterEngineListener n;
    private TuSdkVideoProcesserFaceDetectionDelegate o;
    private TuSDKMediaEffectsManager.OnFilterChangeListener p;
    private TuSdkImageEngine.TuSdkPictureDataCompletedListener q;
    private FrameDetectProcessor.FrameDetectProcessorDelegate r;
    
    public void setEngineRotation(final TuSdkEngineOrientation a) {
        if (a == null) {
            return;
        }
        this.a = a;
        this.b();
    }
    
    public void bindEngineOutput(final TuSdkEngineOutputImage b) {
        if (b == null) {
            return;
        }
        this.b = b;
        if (this.k != null) {
            final List inputs = this.b.getInputs();
            if (inputs == null || inputs.size() < 1) {
                TLog.d("%s bindEngineOutput has not output", new Object[] { "TuSdkEngineVideoProcessorImpl" });
                return;
            }
            final Iterator<SelesContext.SelesInput> iterator = inputs.iterator();
            while (iterator.hasNext()) {
                this.k.addTerminalNode(iterator.next());
            }
            this.b();
        }
    }
    
    public void setMediaEffectDelegate(final TuSDKVideoProcesser.TuSDKVideoProcessorMediaEffectDelegate l) {
        this.l = l;
        if (this.k != null) {
            this.k.setMediaEffectDelegate(this.l);
        }
    }
    
    public void setDataManagerDelegate(final TuSDKMediaEffectsDataManager.TuSDKMediaEffectsManagerDelegate m) {
        this.m = m;
        if (this.k != null) {
            this.k.setManagerDelegate(this.m);
        }
    }
    
    public void setFilterChangedListener(final TuSdkFilterEngine.TuSdkFilterEngineListener n) {
        this.n = n;
    }
    
    public TuSdkImageEngine getImageEngine() {
        if (this.j == null || this.a == null) {
            TLog.w("%s getImageEngine need setEngineRotation first or has released.", new Object[] { "TuSdkEngineVideoProcessorImpl" });
            return null;
        }
        if (this.c == null) {
            this.c = new TuSdkImageEngineImpl();
        }
        this.c.setFaceAligments(this.e);
        this.c.setEngineRotation(this.a);
        this.c.setFilter((FilterWrap)this.j.clone());
        this.c.setListener(this.q);
        return this.c;
    }
    
    public void setDetectScale(final float detectScale) {
        FrameDetectProcessor.setDetectScale(detectScale);
    }
    
    public void setEnableLiveSticker(final boolean g) {
        if (!TuSdkGPU.isLiveStickerSupported() && g) {
            TLog.w("%s setEnableLiveSticker Sorry, face feature is not supported on this device.", new Object[] { "TuSdkEngineVideoProcessorImpl" });
            return;
        }
        this.g = g;
        this.b();
    }
    
    public void setEnableFacePlastic(final boolean h) {
        if (!TuSdkGPU.isLiveStickerSupported() && h) {
            TLog.w("%s setEnableFacePlastic Sorry, face feature is not supported on this device.", new Object[] { "TuSdkEngineVideoProcessorImpl" });
            return;
        }
        this.h = h;
        this.b();
    }
    
    private boolean a() {
        return this.g || this.h;
    }
    
    public void setSyncOutput(final boolean b) {
        this.i = b;
        if (this.d != null) {
            this.d.setSyncOutput(b);
        }
    }
    
    public SelesContext.SelesInput getInput() {
        return (SelesContext.SelesInput)this.j.getLastFilter();
    }
    
    public void setHeaderNode(final SelesOutput headerNode) {
        this.j.setHeaderNode(headerNode);
    }
    
    public void setFaceDetectionDelegate(final TuSdkVideoProcesserFaceDetectionDelegate o) {
        this.o = o;
    }

    public TuSdkEngineVideoProcessorImpl() {
        this.k.setMediaEffectDelegate(this.l);
        this.k.setManagerDelegate(this.m);
        this.j = this.k.getFilterWrapChain();
    }
    
    public void release() {
        if (this.k != null) {
            this.k.release();
            this.k = null;
        }
        if (this.j != null) {
            this.j.destroy();
            this.j = null;
        }
        if (this.d != null) {
            this.d.setEnabled(false);
            this.d.destroy();
            this.d = null;
        }
        if (this.c != null) {
            this.c.release();
            this.c = null;
        }
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.release();
        super.finalize();
    }
    
    public void willProcessFrame(final long n) {
        if (this.a == null) {
            TLog.d("%s willProcessFrame need setEngineRotation first.", new Object[] { "TuSdkEngineVideoProcessorImpl" });
            return;
        }
        if (this.b == null) {
            TLog.d("%s willProcessFrame need bindEngineOutput first.", new Object[] { "TuSdkEngineVideoProcessorImpl" });
            return;
        }
        this.b.willProcessFrame(n);
        if (this.d != null) {
            this.d.setInputTextureSize(this.a.getOutputSize());
            this.d.setInterfaceOrientation(this.a.getInterfaceOrientation());
        }
        this.k.updateEffectTimeLine(n / 1000L, this.p);
    }
    
    private void b() {
        if (this.a == null) {
            return;
        }
        final boolean a = this.a();
        if (!a && this.d == null) {
            return;
        }
        if (a) {
            if (this.d == null) {
                (this.d = new FrameDetectProcessor(TuSdkGPU.getGpuType().getPerformance())).setSyncOutput(this.i);
                this.d.setDelegate(this.r);
                this.d.setEnabled(true);
                this.j.addOffscreenRotate((SelesContext.SelesInput)this.d.getSelesRotateShotOutput());
            }
            this.f = System.currentTimeMillis();
        }
        else {
            this.a(null, 0.0f);
        }
        if (this.j instanceof SelesParameters.FilterStickerInterface) {
            ((SelesParameters.FilterStickerInterface)this.j).setStickerVisibility(this.g);
        }
        if (this.d != null) {
            this.d.setEnabled(a);
        }
    }
    
    private void a(final FaceAligment[] array, final TuSdkSize tuSdkSize, final float n, final boolean b) {
        this.a(array, n);
    }
    
    private void c() {
        if (this.j == null || this.a == null) {
            return;
        }
        this.j.rotationTextures(this.a.getDeviceOrient());
    }
    
    private void a(final FaceAligment[] e, final float deviceAngle) {
        if (this.a()) {
            if (e == null || e.length == 0) {
                this.a(TuSdkVideoProcesserFaceDetectionResultType.TuSDKVideoProcesserFaceDetectionResultTypeNoFaceDetected, 0);
            }
            else {
                this.a(TuSdkVideoProcesserFaceDetectionResultType.TuSDKVideoProcesserFaceDetectionResultTypeFaceDetected, e.length);
            }
        }
        this.e = e;
        if (this.a != null) {
            this.a.setDeviceAngle(deviceAngle);
        }
        if (this.j != null && this.j instanceof SelesParameters.FilterFacePositionInterface) {
            this.j.updateFaceFeatures(e, deviceAngle);
        }
    }
    
    public FaceAligment[] getFaceFeatures() {
        return this.e;
    }
    
    public void addTerminalNode(final SelesContext.SelesInput selesInput) {
        if (this.j == null) {
            return;
        }
        this.j.addTerminalNode(selesInput);
    }
    
    private void a(final TuSdkVideoProcesserFaceDetectionResultType tuSdkVideoProcesserFaceDetectionResultType, final int n) {
        if (this.o == null) {
            return;
        }
        this.o.onFaceDetectionResult(tuSdkVideoProcesserFaceDetectionResultType, n);
    }
    
    public LiveStickerPlayController getLiveStickerPlayController() {
        if (this.k == null) {
            return null;
        }
        return this.k.getLiveStickerPlayController();
    }
    
    public void removeAllLiveSticker() {
        if (this.k == null) {
            return;
        }
        this.k.removeAllLiveSticker();
    }
    
    public void showGroupSticker(final StickerGroup stickerGroup) {
        if (this.k == null) {
            return;
        }
        if (!this.g) {
            TLog.e("%s Please set setEnableLiveSticker to true before use live sticker", new Object[] { "TuSdkEngineVideoProcessorImpl" });
            return;
        }
        final TuSdkMediaStickerEffectData tuSdkMediaStickerEffectData = new TuSdkMediaStickerEffectData(stickerGroup);
        tuSdkMediaStickerEffectData.setAtTimeRange(TuSdkTimeRange.makeRange(0.0f, Float.MAX_VALUE));
        this.k.addMediaEffectData(tuSdkMediaStickerEffectData);
    }
    
    public void setDisplayRect(final RectF rectF, final float n) {
        if (this.j == null) {
            return;
        }
        this.j.setDisplayRect(rectF, n);
    }
    
    public final synchronized void switchFilter(final String s) {
        if (s == null) {
            return;
        }
        if (!this.a(s)) {
            return;
        }
        this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeParticle);
        this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeScene);
        this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeFilter);
        if (FilterManager.shared().isSceneEffectFilter(s)) {
            final TuSdkMediaSceneEffectData tuSdkMediaSceneEffectData = new TuSdkMediaSceneEffectData(s);
            tuSdkMediaSceneEffectData.setAtTimeRange(TuSdkTimeRange.makeRange(0.0f, Float.MAX_VALUE));
            this.addMediaEffectData(tuSdkMediaSceneEffectData);
        }
        else if (FilterManager.shared().isParticleEffectFilter(s)) {
            final TuSdkMediaParticleEffectData tuSdkMediaParticleEffectData = new TuSdkMediaParticleEffectData(s);
            tuSdkMediaParticleEffectData.setAtTimeRange(TuSdkTimeRange.makeRange(0.0f, Float.MAX_VALUE));
            this.addMediaEffectData(tuSdkMediaParticleEffectData);
        }
        else {
            final TuSdkMediaFilterEffectData tuSdkMediaFilterEffectData = new TuSdkMediaFilterEffectData(s);
            tuSdkMediaFilterEffectData.setAtTimeRange(TuSdkTimeRange.makeRange(0.0f, Float.MAX_VALUE));
            this.addMediaEffectData(tuSdkMediaFilterEffectData);
        }
    }
    
    private final boolean a(final String s) {
        if (FilterManager.shared().isSceneEffectFilter(s) && !SdkValid.shared.videoEditorEffectsfilterEnabled()) {
            TLog.e("You are not allowed to use effect filter, please see http://tusdk.com", new Object[0]);
            return false;
        }
        if (FilterManager.shared().isParticleEffectFilter(s) && !SdkValid.shared.videoEditorParticleEffectsFilterEnabled()) {
            TLog.e("You are not allowed to use effect filter, please see http://tusdk.com", new Object[0]);
            return false;
        }
        return true;
    }
    
    public final boolean addMediaEffectData(final TuSdkMediaEffectData tuSdkMediaEffectData) {
        return this.k != null && this.k.addMediaEffectData(tuSdkMediaEffectData);
    }
    
    public boolean removeMediaEffectData(final TuSdkMediaEffectData tuSdkMediaEffectData) {
        if (tuSdkMediaEffectData == null) {
            return false;
        }
        if (this.l != null) {
            this.l.didRemoveMediaEffect(Arrays.asList(tuSdkMediaEffectData));
        }
        return this.k.removeMediaEffectData(tuSdkMediaEffectData);
    }
    
    public void removeMediaEffectsWithType(final TuSdkMediaEffectData.TuSdkMediaEffectDataType tuSdkMediaEffectDataType) {
        if (this.k == null) {
            return;
        }
        final List<TuSdkMediaEffectData> mediaEffectsWithType = this.k.mediaEffectsWithType(tuSdkMediaEffectDataType);
        if (this.l != null && mediaEffectsWithType != null && mediaEffectsWithType.size() > 0) {
            this.l.didRemoveMediaEffect(mediaEffectsWithType);
        }
        this.k.removeMediaEffectsWithType(tuSdkMediaEffectDataType);
    }
    
    public <T extends TuSdkMediaEffectData> List<T> mediaEffectsWithType(final TuSdkMediaEffectData.TuSdkMediaEffectDataType tuSdkMediaEffectDataType) {
        if (this.k == null) {
            return null;
        }
        return this.k.mediaEffectsWithType(tuSdkMediaEffectDataType);
    }
    
    public List<TuSdkMediaEffectData> getAllMediaEffectData() {
        if (this.k == null) {
            return null;
        }
        return this.k.getAllMediaEffectData();
    }
    
    public void removeAllMediaEffects() {
        if (this.k == null) {
            return;
        }
        this.k.removeAllMediaEffects();
    }
    
    public void takeShot() {
        if (this.j == null) {
            return;
        }
        final Bitmap captureVideoImage = this.j.captureVideoImage();
        if (this.n != null) {
            this.n.onPreviewScreenShot(captureVideoImage);
        }
    }
}
