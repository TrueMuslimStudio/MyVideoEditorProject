// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.mutableCliper;

import com.example.myvideoeditorapp.kore.utils.RectHelper;
import android.graphics.RectF;

import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVAssetTrack;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVTimeRange;
import com.example.myvideoeditorapp.kore.utils.RectHelper;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;

import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVTimeRange;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVAssetTrack;

public class TuSdkMediaTrackItem
{
    private AVAssetTrack a;
    private AVTimeRange b;
    private ImageOrientation c;
    private float d;
    private TuSdkMediaTrackItemDelegate e;
    
    public TuSdkMediaTrackItem(final AVAssetTrack a, final AVTimeRange timeRange) {
        this.d = 0.0f;
        this.a = a;
        this.setTimeRange(timeRange);
    }
    
    public TuSdkMediaTrackItem(final AVAssetTrack avAssetTrack) {
        this(avAssetTrack, null);
    }
    
    public AVAssetTrack track() {
        return this.a;
    }
    
    public ImageOrientation videoOrientation() {
        if (this.a == null) {
            return null;
        }
        return this.a.orientation();
    }
    
    public float outputRatio() {
        return this.d;
    }
    
    public void setOutputRatio(final float d) {
        this.d = d;
    }
    
    public ImageOrientation outputOrientation() {
        if (this.c == null) {
            this.c = this.videoOrientation();
        }
        return this.c;
    }
    
    public RectF cropRectF() {
        final RectF computerCenterRectF = RectHelper.computerCenterRectF(this.track().presentSize(), this.outputRatio());
        final float n = computerCenterRectF.left / computerCenterRectF.width() / 2.0f;
        final float n2 = computerCenterRectF.top / computerCenterRectF.height() / 2.0f;
        return new RectF(n, n2, 1.0f - n, 1.0f - n2);
    }
    
    public void setOutputOrientation(final ImageOrientation c) {
        this.c = c;
    }
    
    public AVTimeRange timeRange() {
        return this.b;
    }
    
    public void setTimeRange(final AVTimeRange b) {
        this.b = b;
        this.a();
    }
    
    void a(final TuSdkMediaTrackItemDelegate e) {
        this.e = e;
    }
    
    private void a() {
        if (this.e == null) {
            return;
        }
        this.e.timeRangeDidChanged(this);
    }
    
    interface TuSdkMediaTrackItemDelegate
    {
        void timeRangeDidChanged(final TuSdkMediaTrackItem p0);
    }
}
