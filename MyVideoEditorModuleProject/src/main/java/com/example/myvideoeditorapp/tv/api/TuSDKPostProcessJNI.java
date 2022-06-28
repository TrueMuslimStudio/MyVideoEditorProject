// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.api;

import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioInfo;
import com.example.myvideoeditorapp.kore.utils.NativeLibraryHelper;
import com.example.myvideoeditorapp.tv.core.decoder.TuSDKVideoInfo;

public class TuSDKPostProcessJNI
{
    public static boolean runVideoCommands(final String[] array) {
        return runVideoCommandsJNI(array) == 0;
    }
    
    private static native int runVideoCommandsJNI(final String[] p0);
    
    public static native void readVideoInfo(final String p0, final TuSDKVideoInfo p1);
    
    public static native void readAudioInfo(final String p0, final TuSdkAudioInfo p1);
    
    public static native int fastStart(final String p0, final String p1);
    
    static {
        NativeLibraryHelper.shared().loadLibrary(NativeLibraryHelper.NativeLibType.LIB_VIDEO);
    }
}
