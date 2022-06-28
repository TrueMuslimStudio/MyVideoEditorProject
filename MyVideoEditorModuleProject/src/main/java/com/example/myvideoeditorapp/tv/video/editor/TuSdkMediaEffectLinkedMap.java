// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.video.editor;

import java.util.Map;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TuSdkMediaEffectLinkedMap extends ConcurrentHashMap<TuSdkMediaEffectData.TuSdkMediaEffectDataType, List<TuSdkMediaEffectData>>
{
    private LinkedList<TuSdkMediaEffectData> a;
    private long b;

    public TuSdkMediaEffectLinkedMap() {
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


    public void putMediaEffectData(TuSdkMediaEffectData.TuSdkMediaEffectDataType var1, TuSdkMediaEffectData var2) {
        ((List)this.get(var1)).add(var2);
        this.a.add(var2);
    }


    public boolean deleteMediaEffectData(TuSdkMediaEffectData.TuSdkMediaEffectDataType var1, TuSdkMediaEffectData var2) {
        boolean var3 = ((List)this.get(var1)).remove(var2);
        this.a.remove(var2);
        return var3;
    }
    public void clearByType(TuSdkMediaEffectData.TuSdkMediaEffectDataType var1) {
        List var2 = (List)this.get(var1);
        if (var2 != null) {
            this.a.removeAll(var2);
        }

        ((List)this.get(var1)).clear();
    }
    
    @Override
    public void clear() {
        super.clear();
        this.a.clear();
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

        this.a.clear();
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
        final long n2 = (atTimeRange.getEndTimeUS() == Long.MAX_VALUE) ? atTimeRange.getEndTimeUS() : (atTimeRange.getEndTimeUS() + this.b);
        return atTimeRange.getStartTimeUS() <= n && n2 >= n;
    }

    public LinkedList<TuSdkMediaEffectData> getApplyMediaEffectDataList(TuSdkMediaEffectData.TuSdkMediaEffectDataType[] var1) {
        LinkedList var2 = new LinkedList();
        TuSdkMediaEffectData.TuSdkMediaEffectDataType[] var3 = var1;
        int var4 = var1.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            TuSdkMediaEffectData.TuSdkMediaEffectDataType var6 = var3[var5];
            List var7 = (List)this.get(var6);
            Iterator var8 = var7.iterator();

            while(var8.hasNext()) {
                TuSdkMediaEffectData var9 = (TuSdkMediaEffectData)var8.next();
                if (var9 instanceof TuSdkMediaStickerAudioEffectData) {
                    var2.add(((TuSdkMediaStickerAudioEffectData)var9).getMediaStickerEffectData());
                    var2.add(((TuSdkMediaStickerAudioEffectData)var9).getMediaAudioEffectData());
                } else {
                    var2.add(var9);
                }
            }
        }

        LinkedList var10 = (LinkedList)this.a.clone();
        var10.removeAll(var2);
        var2.addAll(var10);
        return var2;
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
