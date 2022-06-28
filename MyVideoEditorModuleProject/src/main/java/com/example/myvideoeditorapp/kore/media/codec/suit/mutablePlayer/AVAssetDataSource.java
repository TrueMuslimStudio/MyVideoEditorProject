// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer;

import android.media.MediaMetadataRetriever;
import java.io.IOException;
import android.os.Build;
import java.util.Map;
import com.example.myvideoeditorapp.kore.utils.TLog;
import java.io.File;
import android.media.MediaExtractor;
import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;
import android.annotation.TargetApi;

@TargetApi(16)
public class AVAssetDataSource extends AVAsset
{
    private TuSdkMediaDataSource a;
    
    public AVAssetDataSource(final TuSdkMediaDataSource a) {
        if (a == null || !a.isValid()) {
            return;
        }
        this.a = a;
    }
    
    public TuSdkMediaDataSource dataSource() {
        return this.a;
    }
    
    @Override
    public MediaExtractor createExtractor() {
        if (this.a == null) {
            return null;
        }
        final MediaExtractor mediaExtractor = new MediaExtractor();
        try {
            if (this.a.getMediaDataType() == TuSdkMediaDataSource.TuSdkMediaDataSourceType.PATH) {
                if (!new File(this.a.getPath()).exists()) {
                    TLog.e("%s buildExtractor setDataSource path is incorrect", this);
                    return null;
                }
                if (this.a.getRequestHeaders() != null) {
                    mediaExtractor.setDataSource(this.a.getPath(), (Map)this.a.getRequestHeaders());
                }
                else {
                    mediaExtractor.setDataSource(this.a.getPath());
                }
            }
            else if (this.a.getMediaDataType() == TuSdkMediaDataSource.TuSdkMediaDataSourceType.URI) {
                mediaExtractor.setDataSource(this.a.getContext(), this.a.getUri(), (Map)this.a.getRequestHeaders());
            }
            else if (this.a.getMediaDataType() == TuSdkMediaDataSource.TuSdkMediaDataSourceType.FILE_DESCRIPTOR) {
                mediaExtractor.setDataSource(this.a.getFileDescriptor(), this.a.getFileDescriptorOffset(), this.a.getFileDescriptorLength());
            }
            else if (this.a.getMediaDataType() == TuSdkMediaDataSource.TuSdkMediaDataSourceType.MEDIA_DATA_SOURCE && Build.VERSION.SDK_INT >= 23) {
                mediaExtractor.setDataSource(this.a.getMediaDataSource());
            }
        }
        catch (IOException ex) {
            TLog.e(ex, "%s buildExtractor need setDataSource", this);
            return null;
        }
        return mediaExtractor;
    }
    
    @Override
    public MediaMetadataRetriever metadataRetriever() {
        if (this.a == null) {
            return null;
        }
        return this.a.getMediaMetadataRetriever();
    }
    
    @Override
    public int colorSpace() {
        if (this.a.getMediaDataType() != TuSdkMediaDataSource.TuSdkMediaDataSourceType.PATH) {
            return -1;
        }
        if (!new File(this.a.getPath()).exists()) {
            TLog.e("%s buildExtractor setDataSource path is incorrect", this);
            return -1;
        }
        int intValue = -1;
        try {
            final Class<?> forName = Class.forName("com.example.myvideoeditorapp.kore.common.TuSDKMediaUtils");
            intValue = (int)forName.getMethod("getColorRange", String.class).invoke(forName, this.a.getPath());
        }
        catch (Exception ex) {
            TLog.e(ex);
        }
        return intValue;
    }
}
