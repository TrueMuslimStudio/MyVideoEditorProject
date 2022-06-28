// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.video.editor;

import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaTimeSliceEntity;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaTimeSlice;
import java.util.List;

public class TuSdkMediaReversalTimeEffect extends TuSdkMediaTimeEffect
{
    @Override
    public List<TuSdkMediaTimeSlice> getTimeSlickList() {
        this.mEffectTimeLine.reversTimeLine();
        return (List<TuSdkMediaTimeSlice>)this.mEffectTimeLine.getTimeEffectList();
    }
    
    @Override
    public long getCurrentInputTimeUs() {
        return this.mCurrentInputTimeUs;
    }
    
    @Override
    public long calcOutputTimeUs(final long n, final TuSdkMediaTimeSliceEntity tuSdkMediaTimeSliceEntity, final List<TuSdkMediaTimeSliceEntity> list) {
        this.mCurrentInputTimeUs = this.getInputTotalTimeUs() - n;
        if (this.mCurrentInputTimeUs < 0L) {
            this.mCurrentInputTimeUs = 0L;
        }
        return this.mCurrentInputTimeUs;
    }
    
    @Override
    public long calcSeekOutputUs(final long n) {
        return 0L;
    }
}
