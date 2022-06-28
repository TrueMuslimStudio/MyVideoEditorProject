// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.api.engine;

import com.example.myvideoeditorapp.kore.seles.sources.SelesOutput;
import android.graphics.RectF;

import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCorpBuilder;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutput;

import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCorpBuilder;

public interface TuSdkEngineInputImage
{
    void release();
    
    void setEngineRotation(final TuSdkEngineOrientation p0);
    
    void bindEngineProcessor(final TuSdkEngineProcessor p0);
    
    void setTextureCoordinateBuilder(final SelesVerticeCoordinateCorpBuilder p0);
    
    void setPreCropRect(final RectF p0);
    
    void processFrame(final int p0, final int p1, final int p2, final byte[] p3, final long p4);
    
    SelesOutput getOutput();
}
