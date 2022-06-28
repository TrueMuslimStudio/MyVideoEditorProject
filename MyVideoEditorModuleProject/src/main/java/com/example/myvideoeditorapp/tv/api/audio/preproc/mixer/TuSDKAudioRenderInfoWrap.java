// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.api.audio.preproc.mixer;

import com.example.myvideoeditorapp.kore.utils.TLog;
import android.media.MediaFormat;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioInfo;
import android.annotation.SuppressLint;
import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;
import com.example.myvideoeditorapp.tv.core.common.TuSDKMediaUtils;

@SuppressLint({ "InlinedApi" })
public class TuSDKAudioRenderInfoWrap extends TuSdkMediaDataSource
{
    private TuSdkAudioInfo a;
    
    public static TuSDKAudioRenderInfoWrap createWithMediaFormat(final MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            return null;
        }
        final TuSDKAudioRenderInfoWrap tuSDKAudioRenderInfoWrap = new TuSDKAudioRenderInfoWrap();
        tuSDKAudioRenderInfoWrap.a = new TuSdkAudioInfo(mediaFormat);
        return tuSDKAudioRenderInfoWrap;
    }
    
    public static TuSDKAudioRenderInfoWrap createWithMediaDataSource(final TuSdkMediaDataSource tuSdkMediaDataSource) {
        return createWithAudioInfo(TuSDKMediaUtils.getAudioInfo(tuSdkMediaDataSource));
    }
    
    public static TuSDKAudioRenderInfoWrap createWithAudioInfo(final TuSdkAudioInfo a) {
        if (a == null) {
            TLog.e("%s audioInfo is null  ! ", new Object[] { "TuSDKAudioRenderInfoWrap" });
            return null;
        }
        final TuSDKAudioRenderInfoWrap tuSDKAudioRenderInfoWrap = new TuSDKAudioRenderInfoWrap();
        tuSDKAudioRenderInfoWrap.a = a;
        return tuSDKAudioRenderInfoWrap;
    }
    
    private int a() {
        return this.a.bitWidth;
    }
    
    public int bytesCountOfTime(final int n) {
        return n * this.a() * this.a.sampleRate * this.a.channelCount / 8;
    }
    
    public int bytesCountOfTimeUs(final long n) {
        return (int)(n / 1000000L * this.a() * this.a.sampleRate * this.a.channelCount / 8L);
    }
    
    public long getAudioBytesPerSample() {
        return ((this.a.sampleRate <= 0) ? 44100 : this.a.sampleRate) * this.a() / 8;
    }
    
    public long frameTimeUsWithAudioSize(final int n) {
        return 1000000 * (n / this.a.channelCount) / this.getAudioBytesPerSample();
    }
    
    public long getFrameInterval() {
        return 1024000000 / ((this.a.sampleRate <= 0) ? 44100 : this.a.sampleRate);
    }
    
    public TuSdkAudioInfo getRealAudioInfo() {
        return this.a;
    }
    
    public String toString() {
        return this.a.toString();
    }
}
