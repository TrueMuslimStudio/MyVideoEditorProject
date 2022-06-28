// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit;

import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkMediaFileDirectorSync;


public class TuSdkMediaFileDirectorImpl extends TuSdkMediaFileCuterImpl
{
    public TuSdkMediaFileDirectorImpl() {
        super(new TuSdkMediaFileDirectorSync());
    }
}
