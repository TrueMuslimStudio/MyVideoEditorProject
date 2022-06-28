// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.video;

import com.example.myvideoeditorapp.kore.utils.sqllite.ImageSqlInfo;
import com.example.myvideoeditorapp.tv.core.decoder.TuSDKVideoInfo;

import java.io.File;

public class TuSDKVideoResult
{
    public File videoPath;
    public ImageSqlInfo videoSqlInfo;
    public TuSDKVideoInfo videoInfo;
    @Deprecated
    public int duration;
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("videoPath : " + this.videoPath).append("\n").append("videoInfo : " + this.videoInfo);
        return sb.toString();
    }
}
