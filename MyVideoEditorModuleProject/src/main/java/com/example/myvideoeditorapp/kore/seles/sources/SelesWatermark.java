// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.sources;

import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import android.graphics.Bitmap;
import com.example.myvideoeditorapp.kore.utils.TuSdkWaterMarkOption;

public interface SelesWatermark
{
    void setWaterPostion(final TuSdkWaterMarkOption.WaterMarkPosition p0);
    
    void setScale(final float p0);
    
    void setPadding(final float p0);
    
    void setImage(final Bitmap p0, final boolean p1);
    
    void drawInGLThread(final long p0, final TuSdkSize p1, final ImageOrientation p2);
    
    void destroy();
}
