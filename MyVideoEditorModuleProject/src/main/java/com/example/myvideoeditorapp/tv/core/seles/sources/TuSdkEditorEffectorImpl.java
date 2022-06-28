// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.seles.sources;

import android.graphics.Bitmap;

import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCorpBuilder;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCropBuilderImpl;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;
import com.example.myvideoeditorapp.tv.api.engine.TuSdkFilterEngine;
import com.example.myvideoeditorapp.tv.api.engine.TuSdkFilterEngineImpl;
import com.example.myvideoeditorapp.tv.video.editor.TuSdkMediaAudioEffectData;
import com.example.myvideoeditorapp.tv.video.editor.TuSdkMediaEffectData;
import com.example.myvideoeditorapp.tv.video.editor.TuSdkMediaFilterEffectData;
import com.example.myvideoeditorapp.tv.video.editor.TuSdkMediaStickerAudioEffectData;
import com.example.myvideoeditorapp.tv.video.editor.TuSdkMediaStickerEffectData;

import java.nio.IntBuffer;
import java.util.List;

public class TuSdkEditorEffectorImpl implements TuSdkEditorEffector
{
    private TuSdkFilterEngine a;
    private TuSdkEffectorFilterChangeListener b;
    private TuSdkEditorAudioMixer c;
    private SelesVerticeCoordinateCorpBuilder d;
    private TuSdkFilterEngine.TuSdkFilterEngineListener e;
    
    public TuSdkEditorEffectorImpl() {
        this.d = (SelesVerticeCoordinateCorpBuilder)new SelesVerticeCoordinateCropBuilderImpl(false);
        this.e = new TuSdkFilterEngine.TuSdkFilterEngineListener() {
            @Override
            public void onPictureDataCompleted(final IntBuffer intBuffer, final TuSdkSize tuSdkSize) {
            }
            
            @Override
            public void onPreviewScreenShot(final Bitmap bitmap) {
            }
            
            public void onFilterChanged(final FilterWrap filterWrap) {
                if (TuSdkEditorEffectorImpl.this.b != null) {
                    TuSdkEditorEffectorImpl.this.b.onFilterChanged(filterWrap);
                }
            }
        };
        (this.a = new TuSdkFilterEngineImpl(false, false)).setCordinateBuilder(this.d);
        this.a.setListener(this.e);
    }
    
    protected void setAudioMixer(final TuSdkEditorAudioMixer c) {
        this.c = c;
    }
    
    @Override
    public void setInputImageOrientation(final ImageOrientation inputImageOrientation) {
        if (this.a == null) {
            return;
        }
        this.a.setInputImageOrientation(inputImageOrientation);
    }
    
    @Override
    public void setOutputImageOrientation(final ImageOrientation outputImageOrientation) {
        if (this.a == null) {
            return;
        }
        this.a.setOutputImageOrientation(outputImageOrientation);
    }
    
    @Override
    public void setFilterChangeListener(final TuSdkEffectorFilterChangeListener b) {
        this.b = b;
    }
    
    protected void onSurfaceCreated() {
        this.a.onSurfaceCreated();
    }
    
    protected void onSurfaceChanged(final int n, final int n2) {
        this.a.onSurfaceChanged(n, n2);
    }
    
    protected int processFrame(final int n, final int n2, final int n3, final long n4) {
        return this.a.processFrame(n, false, n2, n3, n4);
    }
    
    @Override
    public boolean addMediaEffectData(final TuSdkMediaEffectData tuSdkMediaEffectData) {
        boolean b;
        if (tuSdkMediaEffectData instanceof TuSdkMediaStickerAudioEffectData) {
            this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeAudio);
            this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeSticker);
            this.c.clearAllAudioData();
            final TuSdkMediaAudioEffectData mediaAudioEffectData = ((TuSdkMediaStickerAudioEffectData)tuSdkMediaEffectData).getMediaAudioEffectData();
            if (mediaAudioEffectData != null && this.c != null) {
                this.c.addAudioRenderEntry(mediaAudioEffectData.getAudioEntry());
                this.c.loadAudio();
            }
            b = this.a.addMediaEffectData(tuSdkMediaEffectData);
        }
        else if (tuSdkMediaEffectData instanceof TuSdkMediaStickerEffectData) {
            this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeSticker);
            b = this.a.addMediaEffectData(tuSdkMediaEffectData);
        }
        else if (tuSdkMediaEffectData instanceof TuSdkMediaAudioEffectData && this.c != null) {
            this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeSticker);
            this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeAudio);
            this.c.clearAllAudioData();
            this.c.addAudioRenderEntry(((TuSdkMediaAudioEffectData)tuSdkMediaEffectData).getAudioEntry());
            this.c.loadAudio();
            b = this.a.addMediaEffectData(tuSdkMediaEffectData);
        }
        else if (tuSdkMediaEffectData instanceof TuSdkMediaFilterEffectData) {
            this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeFilter);
            b = this.a.addMediaEffectData(tuSdkMediaEffectData);
        }
        else {
            b = this.a.addMediaEffectData(tuSdkMediaEffectData);
        }
        return b;
    }
    
    @Override
    public <T extends TuSdkMediaEffectData> List<T> mediaEffectsWithType(final TuSdkMediaEffectData.TuSdkMediaEffectDataType tuSdkMediaEffectDataType) {
        return this.a.mediaEffectsWithType(tuSdkMediaEffectDataType);
    }
    
    @Override
    public List<TuSdkMediaEffectData> getAllMediaEffectData() {
        return this.a.getAllMediaEffectData();
    }
    
    @Override
    public void removeMediaEffectData(final TuSdkMediaEffectData tuSdkMediaEffectData) {
        if (tuSdkMediaEffectData instanceof TuSdkMediaStickerAudioEffectData || tuSdkMediaEffectData instanceof TuSdkMediaAudioEffectData) {
            if (this.c == null) {
                return;
            }
            this.c.clearAllAudioData();
        }
        this.a.removeMediaEffectData(tuSdkMediaEffectData);
    }
    
    @Override
    public void removeMediaEffectsWithType(final TuSdkMediaEffectData.TuSdkMediaEffectDataType tuSdkMediaEffectDataType) {
        if (tuSdkMediaEffectDataType == TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeStickerAudio || tuSdkMediaEffectDataType == TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeAudio) {
            if (this.c == null) {
                return;
            }
            this.c.clearAllAudioData();
        }
        this.a.removeMediaEffectsWithType(tuSdkMediaEffectDataType);
    }
    
    @Override
    public void removeAllMediaEffect() {
        this.a.removeAllMediaEffects();
    }
    
    @Override
    public void destroy() {
        this.a.release();
    }
}
