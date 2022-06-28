// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.api.engine;

import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutput;

public interface TuSdkEngineProcessor
{
    void release();
    
    void setEngineRotation(final TuSdkEngineOrientation p0);
    
    void bindEngineOutput(final TuSdkEngineOutputImage p0);
    
    void willProcessFrame(final long p0);
    
    SelesContext.SelesInput getInput();
    
    void setHeaderNode(final SelesOutput p0);
    
    public interface TuSdkVideoProcesserFaceDetectionDelegate
    {
        void onFaceDetectionResult(final TuSdkVideoProcesserFaceDetectionResultType p0, final int p1);
    }
    
    public enum TuSdkVideoProcesserFaceDetectionResultType
    {
        TuSDKVideoProcesserFaceDetectionResultTypeFaceDetected, 
        TuSDKVideoProcesserFaceDetectionResultTypeNoFaceDetected;
    }
}
