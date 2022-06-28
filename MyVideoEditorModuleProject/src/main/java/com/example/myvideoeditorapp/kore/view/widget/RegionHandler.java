// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget;

import android.graphics.RectF;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;

public interface RegionHandler
{
    void setRatio(final float p0);
    
    float getRatio();
    
    void setWrapSize(final TuSdkSize p0);
    
    TuSdkSize getWrapSize();
    
    RectF getRectPercent();
    
    RectF getCenterRectPercent();
    
    void setOffsetTopPercent(final float p0);
    
    float getOffsetTopPercent();
    
    RectF changeWithRatio(final float p0, final RegionChangerListener p1);
    
    public interface RegionChangerListener
    {
        void onRegionChanged(final RectF p0);
    }
}
