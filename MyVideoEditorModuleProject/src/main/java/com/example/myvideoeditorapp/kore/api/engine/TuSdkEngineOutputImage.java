// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.api.engine;

import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.type.ColorFormatType;

import com.example.myvideoeditorapp.kore.seles.SelesContext;
import java.util.List;
import com.example.myvideoeditorapp.kore.type.ColorFormatType;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;

public interface TuSdkEngineOutputImage
{
    void release();
    
    void willProcessFrame(final long p0);
    
    void setEngineRotation(final TuSdkEngineOrientation p0);
    
    int getTerminalTexture();
    
    SelesFramebuffer getTerminalFrameBuffer();
    
    void setYuvOutputImageFormat(final ColorFormatType p0);
    
    List<SelesContext.SelesInput> getInputs();
    
    void setEnableOutputYUVData(final boolean p0);
    
    void snatchFrame(final byte[] p0);
}
