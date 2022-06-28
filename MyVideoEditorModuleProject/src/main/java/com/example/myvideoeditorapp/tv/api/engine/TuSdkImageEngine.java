// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.api.engine;

import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import java.nio.IntBuffer;
import com.example.myvideoeditorapp.kore.utils.hardware.InterfaceOrientation;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.api.engine.TuSdkEngineOrientation;
import com.example.myvideoeditorapp.kore.face.FaceAligment;

public interface TuSdkImageEngine
{
    void release();
    
    void setFaceAligments(final FaceAligment[] p0);
    
    void setEngineRotation(final TuSdkEngineOrientation p0);
    
    void setFilter(final FilterWrap p0);
    
    void setListener(final TuSdkPictureDataCompletedListener p0);
    
    void asyncProcessPictureData(final byte[] p0);
    
    void asyncProcessPictureData(final byte[] p0, final InterfaceOrientation p1);
    
    public interface TuSdkPictureDataCompletedListener
    {
        void onPictureDataCompleted(final IntBuffer p0, final TuSdkSize p1);
    }
}
