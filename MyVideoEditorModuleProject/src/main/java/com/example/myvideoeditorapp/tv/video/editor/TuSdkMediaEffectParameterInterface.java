// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.video.editor;

import java.util.List;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;

public interface TuSdkMediaEffectParameterInterface
{
    SelesParameters.FilterArg getFilterArg(final String p0);
    
    List<SelesParameters.FilterArg> getFilterArgs();
    
    void submitParameter(final String p0, final float p1);
    
    void submitParameter(final int p0, final float p1);
    
    void submitParameters();
    
    void resetParameters();
}
