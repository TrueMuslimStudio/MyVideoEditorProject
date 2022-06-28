// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.video.editor;

import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.tusdk.combo.TuSDKComboFilterWrapChain;
import com.example.myvideoeditorapp.kore.seles.tusdk.cosmetic.CosmeticSticker;
import com.example.myvideoeditorapp.kore.seles.tusdk.cosmetic.TuSDKCosmeticImage;
import com.example.myvideoeditorapp.kore.seles.tusdk.dynamicSticker.DynamicStickerPlayController;
import com.example.myvideoeditorapp.kore.seles.tusdk.dynamicSticker.TuSDKDynamicStickerImage;
import com.example.myvideoeditorapp.kore.seles.tusdk.textSticker.TuSdkImage2DSticker;
import com.example.myvideoeditorapp.kore.sticker.CosmeticStickerPlayController;
import com.example.myvideoeditorapp.kore.sticker.LiveStickerPlayController;
import com.example.myvideoeditorapp.kore.sticker.StickerPositionInfo;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerCategory;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerDynamicData;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerGroup;
import com.example.myvideoeditorapp.tv.api.video.preproc.filter.TuSDKVideoProcesser;
import com.example.myvideoeditorapp.tv.core.detector.TuSdkFaceDetector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class TuSDKMediaEffectsManagerImpl implements TuSDKMediaEffectsManager
{
    private TuSDKMediaEffectsDataManager a;
    private TuSDKComboFilterWrapChain b;
    private LiveStickerPlayController c;
    private CosmeticStickerPlayController d;
    private DynamicStickerPlayController e;
    private boolean f;
    private static final TuSdkMediaEffectData.TuSdkMediaEffectDataType[] g;
    private TuSDKVideoProcesser.TuSDKVideoProcessorMediaEffectDelegate h;
    
    public TuSDKMediaEffectsManagerImpl() {
        this.f = true;
        this.b = new TuSDKComboFilterWrapChain();
        this.a = new TuSDKMediaEffectsDataManager();
    }
    
    @Override
    public TuSDKComboFilterWrapChain getFilterWrapChain() {
        return this.b;
    }
    
    @Override
    public void setMediaEffectDelegate(final TuSDKVideoProcesser.TuSDKVideoProcessorMediaEffectDelegate h) {
        this.h = h;
    }
    
    @Override
    public void setManagerDelegate(final TuSDKMediaEffectsDataManager.TuSDKMediaEffectsManagerDelegate managerDelegate) {
        this.a.setManagerDelegate(managerDelegate);
    }
    
    @Override
    public boolean addMediaEffectData(final TuSdkMediaEffectData tuSdkMediaEffectData) {
        if (tuSdkMediaEffectData == null) {
            return false;
        }
        this.a(tuSdkMediaEffectData);
        return this.a.addMediaEffect(tuSdkMediaEffectData);
    }
    
    @Override
    public void addTerminalNode(final SelesContext.SelesInput selesInput) {
        if (this.b == null || selesInput == null) {
            return;
        }
        this.b.addTerminalNode(selesInput);
    }
    
    private void a(final TuSdkMediaEffectData tuSdkMediaEffectData) {
        if (tuSdkMediaEffectData instanceof TuSdkMediaStickerEffectData || tuSdkMediaEffectData instanceof TuSdkMediaStickerAudioEffectData || tuSdkMediaEffectData instanceof TuSdkMediaLiveStickerEffectData) {
            this.f = true;
        }
        if (tuSdkMediaEffectData.getMediaEffectType() == TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeSticker || tuSdkMediaEffectData.getMediaEffectType() == TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeStickerAudio) {
            this.a.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeSticker);
            this.removeAllLiveSticker();
        }
    }
    
    @Override
    public <T extends TuSdkMediaEffectData> List<T> mediaEffectsWithType(final TuSdkMediaEffectData.TuSdkMediaEffectDataType tuSdkMediaEffectDataType) {
        return this.a.mediaEffectsWithType(tuSdkMediaEffectDataType);
    }
    
    @Override
    public List<TuSdkMediaEffectData> getAllMediaEffectData() {
        return this.a.getApplyMediaEffectDataList(TuSDKMediaEffectsManagerImpl.g);
    }
    
    @Override
    public boolean removeMediaEffectData(final TuSdkMediaEffectData tuSdkMediaEffectData) {
        if (tuSdkMediaEffectData == null) {
            TLog.e("remove TuSdkMediaEffectData must be not null !!", new Object[0]);
            return false;
        }
        this.b.removeFilterWrap(tuSdkMediaEffectData.getFilterWrap());
        if (tuSdkMediaEffectData instanceof TuSdkMediaLiveStickerEffectData) {
            this.e.removeSticker(((TuSdkMediaLiveStickerEffectData)tuSdkMediaEffectData).getStickerData().getStickerData());
        }
        return this.a.removeMediaEffect(tuSdkMediaEffectData);
    }
    
    @Override
    public void removeMediaEffectsWithType(final TuSdkMediaEffectData.TuSdkMediaEffectDataType tuSdkMediaEffectDataType) {
        final Iterator<TuSdkMediaEffectData> iterator = this.a.mediaEffectsWithType(tuSdkMediaEffectDataType).iterator();
        while (iterator.hasNext()) {
            this.b.removeFilterWrap(iterator.next().getFilterWrap());
        }
        this.a.removeMediaEffectsWithType(tuSdkMediaEffectDataType);
        if (tuSdkMediaEffectDataType == TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeSticker) {
            this.removeAllLiveSticker();
        }
    }
    
    @Override
    public void removeAllMediaEffects() {
        this.removeAllLiveSticker();
        if (this.b != null) {
            this.b.removeAllFilterWrapNode();
        }
        this.a.removeAllMediaEffect();
        this.f = true;
    }
    
    @Override
    public void updateEffectTimeLine(final long n, final OnFilterChangeListener onFilterChangeListener) {
        final TuSdkMediaEffectLinkedMap.TuSdkMediaEffectApply seekTime = this.a.seekTime(n);
        final List<TuSdkMediaEffectData> a = seekTime.a;
        for (final TuSdkMediaEffectData tuSdkMediaEffectData : seekTime.b) {
            switch (tuSdkMediaEffectData.getMediaEffectType().ordinal()) {
                case 1:
                case 2: {
                    ((TuSdkMediaTileEffectDataBase)tuSdkMediaEffectData).getStickerData().setEnabled(false);
                    break;
                }
                case 3: {
                    this.a((TuSdkMediaStickerEffectData)tuSdkMediaEffectData, false);
                    break;
                }
                case 4: {
                    ((TuSdkMediaParticleEffectData)tuSdkMediaEffectData).resetParticleFilter();
                    break;
                }
                case 5: {
                    this.a(((TuSdkMediaStickerAudioEffectData)tuSdkMediaEffectData).getMediaStickerEffectData(), false);
                    break;
                }
                case 6: {
                    tuSdkMediaEffectData.setIsApplied(true);
                    break;
                }
                case 7: {
                    this.a((TuSdkMediaLiveStickerEffectData)tuSdkMediaEffectData, false);
                    break;
                }
            }
            if (tuSdkMediaEffectData.isApplied()) {
                this.b.removeFilterWrap(tuSdkMediaEffectData.getFilterWrap());
                tuSdkMediaEffectData.setIsApplied(false);
            }
        }
        for (final TuSdkMediaEffectData tuSdkMediaEffectData2 : a) {
            switch (tuSdkMediaEffectData2.getMediaEffectType().ordinal()) {
                case 4: {
                    this.a((TuSdkMediaParticleEffectData)tuSdkMediaEffectData2, n);
                    continue;
                }
                case 1: {
                    this.applyTextStickerData((TuSdkMediaTileEffectDataBase)tuSdkMediaEffectData2, TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeText);
                    continue;
                }
                case 2: {
                    this.applyTextStickerData((TuSdkMediaTileEffectDataBase)tuSdkMediaEffectData2, TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediEffectDataTypeStickerImage);
                    continue;
                }
                case 3: {
                    this.a((TuSdkMediaStickerEffectData)tuSdkMediaEffectData2);
                    continue;
                }
                case 8: {
                    this.a((TuSdkMediaCosmeticEffectData)tuSdkMediaEffectData2);
                    continue;
                }
                case 9: {
                    this.a((TuSdkMediaAudioEffectData)tuSdkMediaEffectData2);
                    continue;
                }
                case 10: {
                    this.a((TuSdkMediaFilterEffectData)tuSdkMediaEffectData2, onFilterChangeListener);
                    continue;
                }
                case 11: {
                    this.a((TuSdkMediaSceneEffectData)tuSdkMediaEffectData2);
                    continue;
                }
                case 12: {
                    this.a((TuSdkMediaComicEffectData)tuSdkMediaEffectData2);
                    continue;
                }
                case 13: {
                    this.a((TuSdkMediaPlasticFaceEffect)tuSdkMediaEffectData2);
                    continue;
                }
                case 14: {
                    this.a((TuSdkMediaReshapeEffect)tuSdkMediaEffectData2);
                    continue;
                }
                case 15: {
                    this.a((TuSdkMediaSkinFaceEffect)tuSdkMediaEffectData2);
                    continue;
                }
                case 16: {
                    this.a((TuSDKMediaMonsterFaceEffect)tuSdkMediaEffectData2);
                    continue;
                }
                case 6: {
                    this.a((TuSdkMediaTransitionEffectData)tuSdkMediaEffectData2);
                    continue;
                }
                case 7: {
                    this.a((TuSdkMediaLiveStickerEffectData)tuSdkMediaEffectData2);
                    continue;
                }
                default: {
                    TLog.w("apply not find effect %s", new Object[] { tuSdkMediaEffectData2.getMediaEffectType() });
                    continue;
                }
            }
        }
    }
    
    private void a() {
        this.b.removeAllFilterWrapNode();
        for (final TuSdkMediaEffectData tuSdkMediaEffectData : this.a.getApplyMediaEffectDataList(TuSDKMediaEffectsManagerImpl.g)) {
            if (tuSdkMediaEffectData.isApplied() && this.b(tuSdkMediaEffectData)) {
                this.b.addFilterWrap(tuSdkMediaEffectData.getFilterWrap());
            }
        }
    }
    
    private boolean b(final TuSdkMediaEffectData tuSdkMediaEffectData) {
        return tuSdkMediaEffectData.getFilterWrap().getFilter() != null && tuSdkMediaEffectData.getFilterWrap().getLastFilter() != null;
    }
    
    private void a(final OnFilterChangeListener onFilterChangeListener, final TuSdkMediaEffectData tuSdkMediaEffectData) {
        if (onFilterChangeListener == null) {
            return;
        }
        onFilterChangeListener.onFilterChanged(tuSdkMediaEffectData.getFilterWrap());
    }
    
    private void c(final TuSdkMediaEffectData tuSdkMediaEffectData) {
        if (this.h == null) {
            return;
        }
        this.h.didApplyingMediaEffect(tuSdkMediaEffectData);
    }
    
    private void a(final TuSdkMediaParticleEffectData tuSdkMediaParticleEffectData, final long n) {
        if (tuSdkMediaParticleEffectData == null) {
            return;
        }
        if (!tuSdkMediaParticleEffectData.isApplied()) {
            tuSdkMediaParticleEffectData.setIsApplied(true);
            tuSdkMediaParticleEffectData.resetParticleFilter();
            tuSdkMediaParticleEffectData.getFilterWrap().setParticleSize(tuSdkMediaParticleEffectData.getSize());
            tuSdkMediaParticleEffectData.getFilterWrap().setParticleColor(tuSdkMediaParticleEffectData.getColor());
            this.a();
            this.c(tuSdkMediaParticleEffectData);
        }
        tuSdkMediaParticleEffectData.getFilterWrap().updateParticleEmitPosition(tuSdkMediaParticleEffectData.getPointF(n));
    }
    
    private void a(final TuSdkMediaFilterEffectData tuSdkMediaFilterEffectData, final OnFilterChangeListener onFilterChangeListener) {
        if (tuSdkMediaFilterEffectData == null || tuSdkMediaFilterEffectData.isApplied()) {
            return;
        }
        tuSdkMediaFilterEffectData.setIsApplied(true);
        this.a();
        this.a(onFilterChangeListener, tuSdkMediaFilterEffectData);
        this.c(tuSdkMediaFilterEffectData);
    }
    
    private void a(final TuSdkMediaSceneEffectData tuSdkMediaSceneEffectData) {
        if (tuSdkMediaSceneEffectData == null || tuSdkMediaSceneEffectData.isApplied()) {
            return;
        }
        tuSdkMediaSceneEffectData.setIsApplied(true);
        this.a();
        this.c(tuSdkMediaSceneEffectData);
    }
    
    private void a(final TuSdkMediaComicEffectData tuSdkMediaComicEffectData) {
        if (tuSdkMediaComicEffectData == null || tuSdkMediaComicEffectData.isApplied()) {
            return;
        }
        tuSdkMediaComicEffectData.setIsApplied(true);
        this.a();
        this.c(tuSdkMediaComicEffectData);
    }
    
    private void a(final TuSdkMediaPlasticFaceEffect tuSdkMediaPlasticFaceEffect) {
        if (tuSdkMediaPlasticFaceEffect == null || tuSdkMediaPlasticFaceEffect.isApplied()) {
            return;
        }
        tuSdkMediaPlasticFaceEffect.setIsApplied(true);
        this.a();
        this.c(tuSdkMediaPlasticFaceEffect);
    }
    
    private void a(final TuSdkMediaReshapeEffect tuSdkMediaReshapeEffect) {
        if (tuSdkMediaReshapeEffect == null) {
            return;
        }
        TuSdkFaceDetector.setMarkSence(true);
        if (tuSdkMediaReshapeEffect.isApplied()) {
            return;
        }
        tuSdkMediaReshapeEffect.setIsApplied(true);
        this.a();
        this.c(tuSdkMediaReshapeEffect);
    }
    
    private void a(final TuSdkMediaSkinFaceEffect tuSdkMediaSkinFaceEffect) {
        if (tuSdkMediaSkinFaceEffect == null || tuSdkMediaSkinFaceEffect.isApplied()) {
            return;
        }
        tuSdkMediaSkinFaceEffect.setIsApplied(true);
        this.a();
        this.c(tuSdkMediaSkinFaceEffect);
    }
    
    private void a(final TuSDKMediaMonsterFaceEffect tuSDKMediaMonsterFaceEffect) {
        if (tuSDKMediaMonsterFaceEffect == null || tuSDKMediaMonsterFaceEffect.isApplied()) {
            return;
        }
        tuSDKMediaMonsterFaceEffect.setIsApplied(true);
        this.a();
        this.c(tuSDKMediaMonsterFaceEffect);
    }
    
    private void a(final TuSdkMediaTransitionEffectData tuSdkMediaTransitionEffectData) {
        if (tuSdkMediaTransitionEffectData == null || tuSdkMediaTransitionEffectData.isApplied()) {
            return;
        }
        tuSdkMediaTransitionEffectData.setIsApplied(true);
        this.a();
        this.c(tuSdkMediaTransitionEffectData);
    }
    
    protected void applyTextStickerData(final TuSdkMediaTileEffectDataBase tuSdkMediaTileEffectDataBase, final TuSdkMediaEffectData.TuSdkMediaEffectDataType tuSdkMediaEffectDataType) {
        if (tuSdkMediaTileEffectDataBase.getFilterWrap() == null || tuSdkMediaTileEffectDataBase.isApplied()) {
            return;
        }
        tuSdkMediaTileEffectDataBase.getStickerData().setEnabled(true);
        tuSdkMediaTileEffectDataBase.getStickerData().reset();
        final ArrayList<TuSdkImage2DSticker> list = new ArrayList<TuSdkImage2DSticker>();
        final Iterator<TuSdkMediaEffectData> iterator = this.a.mediaEffectsWithType(tuSdkMediaEffectDataType).iterator();
        while (iterator.hasNext()) {
            list.add(((TuSdkMediaTileEffectDataBase)iterator.next()).getStickerData());
        }
        tuSdkMediaTileEffectDataBase.getFilterWrap().updateTileStickers((List)list);
        tuSdkMediaTileEffectDataBase.setIsApplied(true);
        this.a();
        this.c(tuSdkMediaTileEffectDataBase);
    }
    
    private void a(final TuSdkMediaStickerEffectData tuSdkMediaStickerEffectData) {
        if (tuSdkMediaStickerEffectData == null) {
            return;
        }
        if (this.f) {
            this.showGroupSticker(tuSdkMediaStickerEffectData);
            this.f = false;
        }
        if (!tuSdkMediaStickerEffectData.isApplied()) {
            this.a(tuSdkMediaStickerEffectData, true);
            tuSdkMediaStickerEffectData.setIsApplied(true);
            this.a();
            this.c(tuSdkMediaStickerEffectData);
        }
    }
    
    private void a(final TuSdkMediaCosmeticEffectData tuSdkMediaCosmeticEffectData) {
        if (tuSdkMediaCosmeticEffectData == null) {
            return;
        }
        this.showCosmetic(tuSdkMediaCosmeticEffectData);
        if (!tuSdkMediaCosmeticEffectData.isApplied()) {
            tuSdkMediaCosmeticEffectData.setIsApplied(true);
            this.a();
            this.c(tuSdkMediaCosmeticEffectData);
        }
    }
    
    private void a(final TuSdkMediaLiveStickerEffectData tuSdkMediaLiveStickerEffectData) {
        if (tuSdkMediaLiveStickerEffectData == null || tuSdkMediaLiveStickerEffectData.getFilterWrap() == null || tuSdkMediaLiveStickerEffectData.isApplied()) {
            return;
        }
        this.showLiveSticker(tuSdkMediaLiveStickerEffectData);
        if (!tuSdkMediaLiveStickerEffectData.isApplied()) {
            this.a(tuSdkMediaLiveStickerEffectData, true);
            tuSdkMediaLiveStickerEffectData.setIsApplied(true);
            this.a();
            this.c(tuSdkMediaLiveStickerEffectData);
        }
    }
    
    private void a(final TuSdkMediaAudioEffectData tuSdkMediaAudioEffectData) {
        if (tuSdkMediaAudioEffectData == null || !tuSdkMediaAudioEffectData.isVaild()) {
            return;
        }
        this.c(tuSdkMediaAudioEffectData);
    }
    
    @Deprecated
    @Override
    public void removeAllLiveSticker() {
        if (this.c != null) {
            this.c.removeAllStickers();
            this.f = true;
        }
    }
    
    @Override
    public void showGroupSticker(final TuSdkMediaStickerEffectData tuSdkMediaStickerEffectData) {
        final StickerGroup stickerGroup = tuSdkMediaStickerEffectData.getStickerGroup();
        if (stickerGroup == null || stickerGroup.stickers == null || stickerGroup.categoryId != StickerCategory.StickerCategoryType.StickerCategorySmart.getValue()) {
            TLog.e("Only live sticker could be used here", new Object[0]);
            return;
        }
        if (stickerGroup.stickers.size() > 5) {
            TLog.e("Too many live stickers in the group, please try to remove some stickers first.", new Object[0]);
            return;
        }
        if (this.c == null) {
            this.c = new LiveStickerPlayController(SelesContext.currentEGLContext());
        }
        this.c.showGroupSticker(stickerGroup);
        this.b(tuSdkMediaStickerEffectData);
    }
    
    @Override
    public void showCosmetic(final TuSdkMediaCosmeticEffectData tuSdkMediaCosmeticEffectData) {
        if (this.d == null) {
            this.d = new CosmeticStickerPlayController();
        }
        if (this.d != null && this.b != null && tuSdkMediaCosmeticEffectData.getFilterWrap() instanceof SelesParameters.FilterCosmeticInterface) {
            final SelesParameters.FilterCosmeticInterface filterCosmeticInterface = (SelesParameters.FilterCosmeticInterface)tuSdkMediaCosmeticEffectData.getFilterWrap();
            final Queue<CosmeticSticker> cosmeticStickerQueue = tuSdkMediaCosmeticEffectData.cosmeticStickerQueue();
            tuSdkMediaCosmeticEffectData.setIsApplied(true);
            while (true) {
                final CosmeticSticker cosmeticSticker = cosmeticStickerQueue.poll();
                if (cosmeticSticker == null) {
                    break;
                }
                tuSdkMediaCosmeticEffectData.setIsApplied(false);
                this.d.runTask(cosmeticSticker);
                final CosmeticSticker.CosmeticStickerState state = cosmeticSticker.state;
                final StickerPositionInfo.StickerPositionType type = cosmeticSticker.type;
                if (state == CosmeticSticker.CosmeticStickerState.Close) {
                    if (type == StickerPositionInfo.StickerPositionType.CosFacial) {
                        filterCosmeticInterface.closeCosmeticFacial();
                    }
                    else if (type == StickerPositionInfo.StickerPositionType.CosLipGloss) {
                        filterCosmeticInterface.closeCosmeticLip();
                    }
                    else if (type == StickerPositionInfo.StickerPositionType.CosEyeShadow) {
                        filterCosmeticInterface.closeCosmeticEyeshadow();
                    }
                    else if (type == StickerPositionInfo.StickerPositionType.CosEyeLine) {
                        filterCosmeticInterface.closeCosmeticEyeline();
                    }
                    else if (type == StickerPositionInfo.StickerPositionType.CosEyeLash) {
                        filterCosmeticInterface.closeCosmeticEyelash();
                    }
                    else if (type == StickerPositionInfo.StickerPositionType.CosBrows) {
                        filterCosmeticInterface.closeCosmeticEyebrow();
                    }
                    else if (type == StickerPositionInfo.StickerPositionType.CosBlush) {
                        filterCosmeticInterface.closeCosmeticBlush();
                    }
                }
                if (state == CosmeticSticker.CosmeticStickerState.Update) {
                    final TuSDKCosmeticImage tuSDKCosmeticImage = this.d.getCosmeticImage().get(cosmeticSticker.type);
                    if (cosmeticSticker.type == StickerPositionInfo.StickerPositionType.CosFacial) {
                        filterCosmeticInterface.updateCosmeticFacial(tuSDKCosmeticImage);
                    }
                    else if (cosmeticSticker.type == StickerPositionInfo.StickerPositionType.CosLipGloss) {
                        filterCosmeticInterface.updateCosmeticLip(cosmeticSticker.lipType, cosmeticSticker.lipColor);
                    }
                    else if (type == StickerPositionInfo.StickerPositionType.CosEyeShadow) {
                        filterCosmeticInterface.updateCosmeticEyeshadow(tuSDKCosmeticImage);
                    }
                    else if (type == StickerPositionInfo.StickerPositionType.CosEyeLine) {
                        filterCosmeticInterface.updateCosmeticEyeline(tuSDKCosmeticImage);
                    }
                    else if (type == StickerPositionInfo.StickerPositionType.CosEyeLash) {
                        filterCosmeticInterface.updateCosmeticEyelash(tuSDKCosmeticImage);
                    }
                    else if (type == StickerPositionInfo.StickerPositionType.CosBrows) {
                        filterCosmeticInterface.updateCosmeticEyebrow(tuSDKCosmeticImage);
                    }
                    else if (type == StickerPositionInfo.StickerPositionType.CosBlush) {
                        filterCosmeticInterface.updateCosmeticBlush(tuSDKCosmeticImage);
                    }
                }
                TuSdkFaceDetector.setMarkSence(filterCosmeticInterface.active());
            }
            if (!tuSdkMediaCosmeticEffectData.isApplied()) {
                filterCosmeticInterface.refreshRelation();
            }
        }
    }
    
    public void showLiveSticker(final TuSdkMediaLiveStickerEffectData tuSdkMediaLiveStickerEffectData) {
        final StickerDynamicData stickerData = tuSdkMediaLiveStickerEffectData.getStickerData();
        if (stickerData == null || stickerData.getStickerData() == null || stickerData.getStickerData().positionInfo.resourceList.size() <= 0) {
            TLog.e("Only live sticker could be used here", new Object[0]);
            return;
        }
        if (this.e == null) {
            this.e = new DynamicStickerPlayController(SelesContext.currentEGLContext());
        }
        this.e.showGroupSticker(stickerData);
        this.a(tuSdkMediaLiveStickerEffectData, this.e);
    }
    
    private void b(final TuSdkMediaStickerEffectData tuSdkMediaStickerEffectData) {
        if (this.c != null && this.b != null && tuSdkMediaStickerEffectData.getFilterWrap() instanceof SelesParameters.FilterStickerInterface) {
            ((SelesParameters.FilterStickerInterface)tuSdkMediaStickerEffectData.getFilterWrap()).updateStickers(this.c.getStickers());
            boolean b = false;
            for (final TuSdkMediaEffectData tuSdkMediaEffectData : this.a.mediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeSticker)) {
                if (tuSdkMediaEffectData instanceof TuSdkMediaStickerEffectData && tuSdkMediaEffectData.isApplied()) {
                    b = true;
                    break;
                }
            }
            this.a(tuSdkMediaStickerEffectData, b);
        }
    }
    
    private void a(final TuSdkMediaLiveStickerEffectData tuSdkMediaLiveStickerEffectData, final DynamicStickerPlayController dynamicStickerPlayController) {
        if (this.e != null && this.b != null && tuSdkMediaLiveStickerEffectData.getFilterWrap() instanceof SelesParameters.FilterStickerInterface) {
            final ArrayList<TuSDKDynamicStickerImage> list = new ArrayList<TuSDKDynamicStickerImage>();
            list.add(dynamicStickerPlayController.getStickerImageBy(tuSdkMediaLiveStickerEffectData.getStickerData().getStickerData()));
            ((SelesParameters.FilterStickerInterface)tuSdkMediaLiveStickerEffectData.getFilterWrap()).updateDynamicStickers((List)list);
            boolean b = false;
            for (final TuSdkMediaEffectData tuSdkMediaEffectData : this.a.mediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeDynamicSticker)) {
                if (tuSdkMediaEffectData instanceof TuSdkMediaLiveStickerEffectData && tuSdkMediaEffectData.isApplied()) {
                    b = true;
                    break;
                }
            }
            this.a(tuSdkMediaLiveStickerEffectData, b);
        }
    }
    
    private void a(final TuSdkMediaStickerEffectData tuSdkMediaStickerEffectData, final boolean stickerVisibility) {
        if (this.c != null && this.b != null && tuSdkMediaStickerEffectData.getFilterWrap() instanceof SelesParameters.FilterStickerInterface) {
            ((SelesParameters.FilterStickerInterface)tuSdkMediaStickerEffectData.getFilterWrap()).setStickerVisibility(stickerVisibility);
        }
    }
    
    private void a(final TuSdkMediaLiveStickerEffectData tuSdkMediaLiveStickerEffectData, final boolean stickerVisibility) {
        if (this.e != null && this.b != null && tuSdkMediaLiveStickerEffectData.getFilterWrap() instanceof SelesParameters.FilterStickerInterface) {
            ((SelesParameters.FilterStickerInterface)tuSdkMediaLiveStickerEffectData.getFilterWrap()).setStickerVisibility(stickerVisibility);
        }
    }
    
    @Override
    public LiveStickerPlayController getLiveStickerPlayController() {
        return this.c;
    }
    
    @Override
    public void release() {
        this.removeAllLiveSticker();
        this.removeAllMediaEffects();
        if (this.c != null) {
            this.c.destroy();
            this.c = null;
        }
        if (this.d != null) {
            this.d.destroy();
            this.d = null;
        }
        if (this.b != null) {
            this.b.destroy();
            this.b = null;
        }
    }
    
    static {
        g = new TuSdkMediaEffectData.TuSdkMediaEffectDataType[] { TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeSkinFace, TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeParticle, TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeTransition, TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeScene, TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeReshape, TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeCosmetic, TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypePlasticFace, TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeMonsterFace, TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeSticker, TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeStickerAudio, TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeComic, TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeFilter };
    }
}
