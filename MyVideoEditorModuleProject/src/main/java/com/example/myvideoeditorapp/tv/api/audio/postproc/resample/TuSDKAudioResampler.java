// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.api.audio.postproc.resample;

import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.tv.api.postpro.TuSDKPostProcess;
import com.example.myvideoeditorapp.tv.core.common.TuSDKMediaDataSource;

import java.io.File;
import java.util.ArrayList;

public class TuSDKAudioResampler extends TuSDKPostProcess
{
    public final boolean process(final TuSDKMediaDataSource tuSDKMediaDataSource, final File file, final int i, final int j) {
        final ArrayList<PostProcessArg> list = new ArrayList<PostProcessArg>(5);
        if (i > 0) {
            list.add(new PostProcessArg("-ar", String.valueOf(i)));
        }
        if (j > 0) {
            list.add(new PostProcessArg("-ac", String.valueOf(j)));
        }
        if (list.size() == 0) {
            TLog.e("%s : Invalid parameter", new Object[] { this });
            return false;
        }
        list.add(new PostProcessArg("-f", "wav"));
        return super.process(tuSDKMediaDataSource, file, list);
    }
}
