// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.seles.sources;

import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import java.util.List;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;
import com.example.myvideoeditorapp.tv.video.editor.TuSdkMediaEffectData;

public interface TuSdkEditorEffector
{
    void setInputImageOrientation(final ImageOrientation p0);
    
    void setOutputImageOrientation(final ImageOrientation p0);
    
    void setFilterChangeListener(final TuSdkEffectorFilterChangeListener p0);
    
    boolean addMediaEffectData(final TuSdkMediaEffectData p0);
    
     <T extends TuSdkMediaEffectData> List<T> mediaEffectsWithType(final TuSdkMediaEffectData.TuSdkMediaEffectDataType p0);
    
    List<TuSdkMediaEffectData> getAllMediaEffectData();
    
    void removeMediaEffectData(final TuSdkMediaEffectData p0);
    
    void removeMediaEffectsWithType(final TuSdkMediaEffectData.TuSdkMediaEffectDataType p0);
    
    void removeAllMediaEffect();
    
    void destroy();
    
    public interface TuSdkEffectorFilterChangeListener
    {
        void onFilterChanged(final FilterWrap p0);
    }
}
