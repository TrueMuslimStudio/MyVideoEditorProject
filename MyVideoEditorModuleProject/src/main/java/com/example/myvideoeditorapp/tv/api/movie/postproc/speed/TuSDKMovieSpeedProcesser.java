// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.api.movie.postproc.speed;

import android.media.MediaMetadataRetriever;
import android.text.TextUtils;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.tv.api.postpro.TuSDKPostProcess;
import com.example.myvideoeditorapp.tv.core.common.TuSDKMediaDataSource;
import com.example.myvideoeditorapp.tv.core.common.TuSDKMediaUtils;
import com.example.myvideoeditorapp.tv.core.decoder.TuSDKVideoInfo;

import java.io.File;
import java.util.ArrayList;

final class TuSDKMovieSpeedProcesser extends TuSDKPostProcess
{
    public final boolean process(final TuSDKMediaDataSource tuSDKMediaDataSource, final File file, final SpeedMode speedMode) {
        if (tuSDKMediaDataSource == null || !tuSDKMediaDataSource.isValid()) {
            TLog.e("%s : Invalid data source", new Object[] { this });
            return false;
        }
        final TuSDKVideoInfo videoInfo = TuSDKMediaUtils.getVideoInfo(tuSDKMediaDataSource);
        if (videoInfo == null) {
            TLog.e("%s : Invalid data source", new Object[] { this });
            return false;
        }
        this.a(videoInfo, tuSDKMediaDataSource);
        final ArrayList<PostProcessArg> list = new ArrayList<PostProcessArg>();
        if (videoInfo.fps > 0) {
            list.add(new PostProcessArg("-r", String.valueOf(videoInfo.fps)));
        }
        if (videoInfo.bitrate > 0) {
            list.add(new PostProcessArg("-b:v", String.valueOf(videoInfo.bitrate) + "K"));
        }
        list.add(new PostProcessArg("-filter:v", speedMode.a()));
        list.add(new PostProcessArg("-filter:a", speedMode.b()));
        return this.process(tuSDKMediaDataSource, file, list);
    }
    
    private void a(final TuSDKVideoInfo tuSDKVideoInfo, final TuSDKMediaDataSource tuSDKMediaDataSource) {
        final MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        if (!TextUtils.isEmpty((CharSequence)tuSDKMediaDataSource.getFilePath())) {
            mediaMetadataRetriever.setDataSource(tuSDKMediaDataSource.getFilePath());
        }
        else {
            mediaMetadataRetriever.setDataSource(TuSdkContext.context(), tuSDKMediaDataSource.getFileUri());
        }
        if (tuSDKVideoInfo.degree <= 0) {
            final String metadata = mediaMetadataRetriever.extractMetadata(24);
            if (!TextUtils.isEmpty((CharSequence)metadata)) {
                tuSDKVideoInfo.setVideoRotation(Integer.parseInt(metadata));
            }
        }
        if (tuSDKVideoInfo.bitrate <= 0) {
            final String metadata2 = mediaMetadataRetriever.extractMetadata(20);
            if (!TextUtils.isEmpty((CharSequence)metadata2)) {
                tuSDKVideoInfo.bitrate = Integer.parseInt(metadata2);
            }
        }
    }
    
    public enum SpeedMode
    {
        FAST_1(0.5f, 2.0f), 
        FAST_2(0.333f, 3.5f), 
        FAST_3(0.25f, 4.0f), 
        SLOW_1(1.5f, 0.75f), 
        SLOW_2(1.75f, 0.625f), 
        SLOW_3(2.0f, 0.5f);
        
        private float a;
        private float b;
        
        private SpeedMode(final float a, final float b) {
            this.a = a;
            this.b = b;
        }
        
        private String a() {
            return "setpts=" + this.a + "*PTS";
        }
        
        private String b() {
            final StringBuilder sb = new StringBuilder();
            float b = this.b;
            while (true) {
                while (b != b % 2.0f) {
                    b -= 2.0f;
                    sb.append("atempo").append("=").append(2).append(",");
                    if (b <= 0.0f) {
                        sb.deleteCharAt(sb.length() - 1);
                        return sb.toString();
                    }
                }
                sb.append("atempo").append("=").append(b).append(",");
                continue;
            }
        }
    }
}
