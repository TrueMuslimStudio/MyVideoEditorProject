// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.video.editor;

import java.util.Map;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TuSdkMediaEffectMap extends ConcurrentHashMap<TuSdkMediaEffectData.TuSdkMediaEffectDataType, List<TuSdkMediaEffectData>>
{
    private long a = 66666L;

    public TuSdkMediaEffectMap() {
        TuSdkMediaEffectData.TuSdkMediaEffectDataType[] var1 = TuSdkMediaEffectData.TuSdkMediaEffectDataType.values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            TuSdkMediaEffectData.TuSdkMediaEffectDataType var4 = var1[var3];
            this.put(var4, new ArrayList());
        }

    }

    public void resetMediaEffects() {
        Iterator var1 = this.values().iterator();

        while(var1.hasNext()) {
            List var2 = (List)var1.next();
            Iterator var3 = var2.iterator();

            while(var3.hasNext()) {
                TuSdkMediaEffectData var4 = (TuSdkMediaEffectData)var3.next();
                var4.setIsApplied(false);
            }
        }

    }

    public List<TuSdkMediaEffectData> getAllMediaEffectData() {
        ArrayList var1 = new ArrayList();
        Iterator var2 = this.values().iterator();

        while(var2.hasNext()) {
            List var3 = (List)var2.next();
            var1.addAll(var3);
        }

        return var1;
    }

    public void clearMediaEffects() {
        Iterator var1 = this.values().iterator();

        while(var1.hasNext()) {
            List var2 = (List)var1.next();
            var2.clear();
        }

    }
    
    public TuSdkMediaEffectApply seekTimeUs(final long n) {
        final TuSdkMediaEffectApply tuSdkMediaEffectApply = new TuSdkMediaEffectApply();
        for (final Entry<TuSdkMediaEffectData.TuSdkMediaEffectDataType, List<TuSdkMediaEffectData>> entry : this.entrySet()) {
            final List<TuSdkMediaEffectData> list = entry.getValue();
            if (entry.getKey() == TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeScene) {
                this.a(n, list, tuSdkMediaEffectApply);
            }
            else {
                this.b(n, list, tuSdkMediaEffectApply);
            }
        }
        return tuSdkMediaEffectApply;
    }
    
    private void a(final long n, final List<TuSdkMediaEffectData> list, final TuSdkMediaEffectApply tuSdkMediaEffectApply) {
        final ArrayList<TuSdkMediaEffectData> list2 = new ArrayList<TuSdkMediaEffectData>();
        for (final TuSdkMediaEffectData tuSdkMediaEffectData : list) {
            if (this.a(tuSdkMediaEffectData, n)) {
                list2.add(tuSdkMediaEffectData);
            }
            else {
                tuSdkMediaEffectApply.b.add(tuSdkMediaEffectData);
            }
        }
        for (int i = 0; i < list2.size(); ++i) {
            if (i == list2.size() - 1) {
                tuSdkMediaEffectApply.a.add((TuSdkMediaEffectData)list2.get(i));
            }
            else {
                tuSdkMediaEffectApply.b.add((TuSdkMediaEffectData)list2.get(i));
            }
        }
    }
    
    private void b(final long n, final List<TuSdkMediaEffectData> list, final TuSdkMediaEffectApply tuSdkMediaEffectApply) {
        for (final TuSdkMediaEffectData tuSdkMediaEffectData : list) {
            if (this.a(tuSdkMediaEffectData, n)) {
                if (tuSdkMediaEffectData.getMediaEffectType() == TuSdkMediaEffectData.TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeStickerAudio) {
                    final TuSdkMediaStickerAudioEffectData tuSdkMediaStickerAudioEffectData = (TuSdkMediaStickerAudioEffectData)tuSdkMediaEffectData;
                    tuSdkMediaEffectApply.a.add(tuSdkMediaStickerAudioEffectData.getMediaStickerEffectData());
                    tuSdkMediaEffectApply.a.add(tuSdkMediaStickerAudioEffectData.getMediaAudioEffectData());
                }
                else {
                    tuSdkMediaEffectApply.a.add(tuSdkMediaEffectData);
                }
            }
            else {
                tuSdkMediaEffectData.setIsApplied(false);
                tuSdkMediaEffectApply.b.add(tuSdkMediaEffectData);
            }
        }
    }
    
    private boolean a(final TuSdkMediaEffectData tuSdkMediaEffectData, final long n) {
        if (tuSdkMediaEffectData.getAtTimeRange() == null) {
            return true;
        }
        if (!tuSdkMediaEffectData.validateTimeRange()) {
            return false;
        }
        final TuSdkTimeRange atTimeRange = tuSdkMediaEffectData.getAtTimeRange();
        if (!atTimeRange.isValid()) {
            return false;
        }
        final long n2 = (atTimeRange.getEndTimeUS() == Long.MAX_VALUE) ? atTimeRange.getEndTimeUS() : (atTimeRange.getEndTimeUS() + this.a);
        return atTimeRange.getStartTimeUS() <= n && n2 >= n;
    }
    
    public class TuSdkMediaEffectApply
    {
        List<TuSdkMediaEffectData> a;
        List<TuSdkMediaEffectData> b;
        
        public TuSdkMediaEffectApply() {
            this.a = new ArrayList<TuSdkMediaEffectData>();
            this.b = new ArrayList<TuSdkMediaEffectData>();
        }
    }
}
