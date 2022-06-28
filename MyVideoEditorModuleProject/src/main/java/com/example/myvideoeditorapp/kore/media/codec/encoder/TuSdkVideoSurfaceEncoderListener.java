// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.encoder;

import com.example.myvideoeditorapp.kore.seles.egl.SelesRenderer;


public interface TuSdkVideoSurfaceEncoderListener extends TuSdkEncoderListener, SelesRenderer
{
    void onEncoderDrawFrame(final long p0, final boolean p1);
}
