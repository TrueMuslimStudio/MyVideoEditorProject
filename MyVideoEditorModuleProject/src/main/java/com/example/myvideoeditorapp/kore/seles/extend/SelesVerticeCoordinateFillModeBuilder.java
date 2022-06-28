// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.extend;

import com.example.myvideoeditorapp.kore.seles.output.SelesView;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;

public interface SelesVerticeCoordinateFillModeBuilder extends SelesVerticeCoordinateBuilder
{
    void setFillMode(final SelesView.SelesFillModeType p0);
    
    void setOnDisplaySizeChangeListener(final OnDisplaySizeChangeListener p0);
    
    public interface OnDisplaySizeChangeListener
    {
        void onDisplaySizeChanged(final TuSdkSize p0);
    }
}
