// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer;

import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaFormat;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;
import com.example.myvideoeditorapp.kore.media.codec.video.TuSdkVideoInfo;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import android.media.MediaFormat;
import android.annotation.TargetApi;

@TargetApi(16)
public class AVAssetTrack
{
    public static final int MIN_VIDEO_FRAME_RATE = 5;
    private AVAsset a;
    private MediaFormat b;
    private AVMediaType c;
    private int d;
    private AVTimeRange e;
    
    public AVAssetTrack(final AVAsset a, final MediaFormat b, final AVMediaType c, final int d) {
        this.a = a;
        this.c = c;
        this.d = d;
        this.b = b;
    }
    
    public AVTimeRange timeRange() {
        return this.e;
    }
    
    public AVMediaType mediaType() {
        return this.c;
    }
    
    public MediaFormat mediaFormat() {
        return this.b;
    }
    
    public AVAsset getAsset() {
        return this.a;
    }
    
    public int getTrackID() {
        return this.d;
    }
    
    public void setTimeRange(final AVTimeRange e) {
        this.e = e;
    }
    
    public TuSdkSize naturalSize() {
        final TuSdkVideoInfo tuSdkVideoInfo = new TuSdkVideoInfo(this.b);
        if (tuSdkVideoInfo.sps == null || tuSdkVideoInfo.sps.dar_width == 0 || tuSdkVideoInfo.sps.dar_height == 0) {
            return TuSdkSize.create(this.b.getInteger("width"), this.b.getInteger("height"));
        }
        return TuSdkSize.create(tuSdkVideoInfo.sps.dar_width, tuSdkVideoInfo.sps.dar_height);
    }
    
    public TuSdkSize presentSize() {
        if (this.orientation() == ImageOrientation.Up || this.orientation() == ImageOrientation.Down) {
            return this.naturalSize();
        }
        final TuSdkSize naturalSize = this.naturalSize();
        return TuSdkSize.create(naturalSize.height, naturalSize.width);
    }
    
    public long minFrameDuration() {
        if (this.b.containsKey("frame-rate")) {
            return (long)(1.0f / this.b.getInteger("frame-rate") * 1000.0f);
        }
        return 16L;
    }
    
    public ImageOrientation orientation() {
        return TuSdkMediaFormat.getVideoRotation(this.getAsset().metadataRetriever());
    }
    
    public long durationTimeUs() {
        if (this.b != null && this.b.containsKey("durationUs")) {
            return TuSdkMediaFormat.getKeyDurationUs(this.b) - this.minFrameDuration() * 10L;
        }
        return TuSdkMediaFormat.getKeyDuration(this.getAsset().metadataRetriever()) * 1000L - this.minFrameDuration();
    }
    
    public boolean lowFrameRateVideo() {
        return this.mediaType() != AVMediaType.AVMediaTypeAudio && this.minFrameDuration() >= 200L;
    }
}
