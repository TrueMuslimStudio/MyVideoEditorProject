// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.video.editor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.secret.SdkValid;
import java.util.ArrayList;

public class TuSDKMediaEffectsDataManager
{
    private TuSdkMediaEffectLinkedMap a;
    private TuSDKMediaEffectsManagerDelegate b;
    
    public TuSDKMediaEffectsDataManager() {
        this.a = new TuSdkMediaEffectLinkedMap();
    }

    public boolean addMediaEffect(TuSdkMediaEffectData var1) {
        ArrayList var2 = new ArrayList();
        switch(var1.getMediaEffectType()) {
            case TuSdkMediaEffectDataTypeComic:
                if (!SdkValid.shared.videoEditorComicEffectsSupport()) {
                    TLog.e("You are not allowed to use conmic effect, please see http://tusdk.com", new Object[0]);
                    return false;
                }

                this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeCosmetic);
                this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeFilter);
                this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeComic);
                this.a.putMediaEffectData(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeComic, var1);
                var2.add(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeComic);
                break;
            case TuSdkMediaEffectDataTypeFilter:
                if (!((List)this.a.get(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeFilter)).contains(var1)) {
                    this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeComic);
                    this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeFilter);
                    this.a.putMediaEffectData(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeFilter, var1);
                    var2.add(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeFilter);
                }
                break;
            case TuSdkMediaEffectDataTypeAudio:
                if (!this.a()) {
                    return false;
                }

                this.a.putMediaEffectData(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeAudio, var1);
                var2.add(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeAudio);
                break;
            case TuSdkMediaEffectDataTypeSticker:
                if (!this.a(var1)) {
                    return false;
                }

                this.a.putMediaEffectData(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeSticker, var1);
                var2.add(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeSticker);
                break;
            case TuSdkMediaEffectDataTypeParticle:
                if (!SdkValid.shared.videoEditorParticleEffectsFilterEnabled()) {
                    TLog.e("You are not allowed to use editor particle effect, please see http://tusdk.com", new Object[0]);
                    return false;
                }

                if (!((List)this.a.get(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeParticle)).contains(var1)) {
                    this.a.putMediaEffectData(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeParticle, var1);
                    var2.add(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeParticle);
                }
                break;
            case TuSdkMediaEffectDataTypeScene:
                if (!SdkValid.shared.videoEditorEffectsfilterEnabled()) {
                    TLog.e("You are not allowed to use editor scene effect, please see http://tusdk.com", new Object[0]);
                    return false;
                }

                if (!((List)this.a.get(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeScene)).contains(var1)) {
                    this.a.putMediaEffectData(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeScene, var1);
                    var2.add(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeScene);
                }
                break;
            case TuSdkMediaEffectDataTypeStickerAudio:
                if (!SdkValid.shared.videoEditorMusicEnabled()) {
                    TLog.e("You are not allowed to use editor music, please see http://tusdk.com", new Object[0]);
                    return false;
                }

                if (!SdkValid.shared.videoEditorStickerEnabled()) {
                    TLog.e("You are not allowed to use editor sticker, please see http://tusdk.com", new Object[0]);
                    return false;
                }

                this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeStickerAudio);
                this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeMonsterFace);
                if (!this.a(var1)) {
                    return false;
                }

                if (!this.a()) {
                    return false;
                }

                this.a.putMediaEffectData(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeSticker, ((TuSdkMediaStickerAudioEffectData)var1).getMediaStickerEffectData());
                var2.add(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeStickerAudio);
                break;
            case TuSdkMediaEffectDataTypeText:
                if (!SdkValid.shared.videoEditorTextEffectsEnabled()) {
                    TLog.e("You are not allowed to use editor text, please see http://tusdk.com", new Object[0]);
                    return false;
                }

                this.a.putMediaEffectData(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeText, var1);
                var2.add(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeText);
                break;
            case TuSdkMediEffectDataTypeStickerImage:
                if (!SdkValid.shared.videoEditorTextEffectsEnabled()) {
                    TLog.e("You are not allowed to use editor text, please see http://tusdk.com", new Object[0]);
                    return false;
                }

                this.a.putMediaEffectData(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediEffectDataTypeStickerImage, var1);
                var2.add(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediEffectDataTypeStickerImage);
                break;
            case TuSdkMediaEffectDataTypePlasticFace:
                this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypePlasticFace);
                this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeMonsterFace);
                this.a.putMediaEffectData(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypePlasticFace, var1);
                var2.add(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypePlasticFace);
                break;
            case TuSdkMediaEffectDataTypeReshape:
                this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeReshape);
                this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeMonsterFace);
                this.a.putMediaEffectData(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeReshape, var1);
                var2.add(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeReshape);
                break;
            case TuSdkMediaEffectDataTypeSkinFace:
                this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeSkinFace);
                this.a.putMediaEffectData(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeSkinFace, var1);
                var2.add(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeSkinFace);
                break;
            case TuSdkMediaEffectDataTypeMonsterFace:
                if (!SdkValid.shared.videoEditorMonsterFaceSupport()) {
                    TLog.e("You are not allowed to use monster face effect , please see https://tutucloud.com", new Object[0]);
                    return false;
                }

                this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeMonsterFace);
                this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypePlasticFace);
                this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeReshape);
                this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeStickerAudio);
                this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeSticker);
                this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeCosmetic);
                this.a.putMediaEffectData(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeMonsterFace, var1);
                var2.add(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeMonsterFace);
                break;
            case TuSdkMediaEffectDataTypeTransition:
                if (!SdkValid.shared.videoEditorTransitionEffectsSupport()) {
                    TLog.e("You are not allowed to use editor transition effect, please see http://tusdk.com", new Object[0]);
                    return false;
                }

                if (!((List)this.a.get(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeTransition)).contains(var1)) {
                    this.a.putMediaEffectData(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeTransition, var1);
                    var2.add(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeTransition);
                }
                break;
            case TuSdkMediaEffectDataTypeDynamicSticker:
                if (!SdkValid.shared.videoEditorMusicEnabled()) {
                    TLog.e("You are not allowed to use editor dynamic sticker, please see http://tusdk.com", new Object[0]);
                    return false;
                }

                if (!SdkValid.shared.videoEditorStickerEnabled()) {
                    TLog.e("You are not allowed to use editor dynamic sticker, please see http://tusdk.com", new Object[0]);
                    return false;
                }

                this.a.putMediaEffectData(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeDynamicSticker, var1);
                var2.add(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeDynamicSticker);
                break;
            case TuSdkMediaEffectDataTypeCosmetic:
                if (!SdkValid.shared.CosmeticEnabled()) {
                    TLog.e("You are not allowed to use cosmetic, please see http://tusdk.com", new Object[0]);
                    return false;
                }

                if (((List)this.a.get(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeCosmetic)).size() <= 0) {
                    this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeMonsterFace);
                    this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeComic);
                    this.a.putMediaEffectData(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeCosmetic, var1);
                    var2.add(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeCosmetic);
                }
                break;
            default:
                TLog.e("unkwon MediaEffectDataType %s", new Object[]{var1.getMediaEffectType()});
                return false;
        }

        if (var2.size() > 0 && this.b != null) {
            this.b.mediaEffectsManager(this, var2);
        }

        return true;
    }
    
    private boolean a() {
        if (!SdkValid.shared.videoEditorMusicEnabled()) {
            TLog.e("You are not allowed to use editor music, please see http://tusdk.com", new Object[0]);
            return false;
        }
        this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeAudio);
        this.a.clearByType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeAudio);
        return true;
    }

    private boolean a(TuSdkMediaEffectData var1) {
        if (!SdkValid.shared.videoEditorStickerEnabled()) {
            TLog.e("You are not allowed to use editor sticker, please see http://tusdk.com", new Object[0]);
            return false;
        } else if (((List)this.a.get(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeSticker)).contains(var1)) {
            return false;
        } else {
            this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeSticker);
            this.removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeMonsterFace);
            return true;
        }
    }
    
    public boolean removeMediaEffect(final TuSdkMediaEffectData tuSdkMediaEffectData) {
        final boolean deleteMediaEffectData = this.a.deleteMediaEffectData(tuSdkMediaEffectData.getMediaEffectType(), tuSdkMediaEffectData);
        tuSdkMediaEffectData.setIsApplied(false);
        if (this.b != null) {
            this.b.mediaEffectsManager(this, (ArrayList)Arrays.asList(tuSdkMediaEffectData.getMediaEffectType()));
        }
        return deleteMediaEffectData;
    }

    public void removeMediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType var1) {
        List var2 = (List)this.a.get(var1);
        Iterator var3 = var2.iterator();

        while(var3.hasNext()) {
            TuSdkMediaEffectData var4 = (TuSdkMediaEffectData)var3.next();
            var4.setIsApplied(false);
        }

        this.a.clearByType(var1);
        if (this.b != null) {
            this.b.mediaEffectsManager(this, (ArrayList)Arrays.asList(var1));
        }

    }
    
    public void removeAllMediaEffect() {
        this.resetAllMediaEffects();
        final ArrayList<TuSdkMediaEffectData.TuSdkMediaEffectDataType> list = new ArrayList<TuSdkMediaEffectData.TuSdkMediaEffectDataType>();
        for (final Map.Entry<TuSdkMediaEffectData.TuSdkMediaEffectDataType, List<TuSdkMediaEffectData>> entry : this.a.entrySet()) {
            final TuSdkMediaEffectData.TuSdkMediaEffectDataType e = entry.getKey();
            final List<TuSdkMediaEffectData> list2 = entry.getValue();
            list.add(e);
            final Iterator<TuSdkMediaEffectData> iterator2 = list2.iterator();
            while (iterator2.hasNext()) {
                iterator2.next().setIsApplied(false);
            }
            list2.clear();
            this.a.clearByType(e);
        }
        if (this.b != null) {
            this.b.mediaEffectsManager(this, list);
        }
    }

    public <T extends TuSdkMediaEffectData> List<T> mediaEffectsWithType(TuSdkMediaEffectData.TuSdkMediaEffectDataType var1) {
        return (List)this.a.get(var1);
    }
    
    public LinkedList<TuSdkMediaEffectData> getApplyMediaEffectDataList(final TuSdkMediaEffectData.TuSdkMediaEffectDataType[] array) {
        return this.a.getApplyMediaEffectDataList(array);
    }
    
    public void resetAllMediaEffects() {
        this.a.resetMediaEffects();
    }
    
    public TuSdkMediaEffectLinkedMap.TuSdkMediaEffectApply seekTime(final long n) {
        return this.a.seekTimeUs(n);
    }
    
    public List<TuSdkMediaEffectData> getAllMediaEffectData() {
        return this.a.getAllMediaEffectData();
    }

    public boolean hasMediaAudioEffects() {
        return ((List)this.a.get(TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeAudio)).size() > 0;
    }
    public void setManagerDelegate(final TuSDKMediaEffectsManagerDelegate b) {
        this.b = b;
    }
    
    public interface TuSDKMediaEffectsManagerDelegate
    {
        void mediaEffectsManager(final TuSDKMediaEffectsDataManager p0, final ArrayList<TuSdkMediaEffectData.TuSdkMediaEffectDataType> p1);
    }
}
