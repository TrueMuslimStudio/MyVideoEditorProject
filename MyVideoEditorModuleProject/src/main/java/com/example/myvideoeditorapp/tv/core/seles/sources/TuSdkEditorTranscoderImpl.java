// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.seles.sources;

import android.annotation.TargetApi;
import android.graphics.RectF;
import android.media.MediaFormat;

import com.example.myvideoeditorapp.kore.TuSdk;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkMediaProgress;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaFormat;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaTimeSlice;
import com.example.myvideoeditorapp.kore.media.codec.suit.TuSdkMediaFileCuter;
import com.example.myvideoeditorapp.kore.media.codec.suit.TuSdkMediaFileCuterImpl;
import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.FileHelper;
import com.example.myvideoeditorapp.kore.utils.StringHelper;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;
import com.example.myvideoeditorapp.tv.core.common.TuSDKMediaUtils;
import com.example.myvideoeditorapp.tv.core.decoder.TuSDKVideoInfo;
import com.example.myvideoeditorapp.tv.video.editor.TuSdkTimeRange;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TuSdkEditorTranscoderImpl implements TuSdkEditorTranscoder
{
    private TuSdkMediaDataSource a;
    private TuSdkMediaDataSource b;
    private TuSdkMediaFileCuter c;
    private TuSDKVideoInfo d;
    private TuSDKVideoInfo e;
    private TuSdkTimeRange f;
    private RectF g;
    private RectF h;
    private int i;
    private boolean j;
    private List<TuSdkTranscoderProgressListener> k;
    protected File mMovieOutputTempFilePath;
    private TuSdkMediaProgress l;
    
    public TuSdkEditorTranscoderImpl() {
        this.j = true;
        this.k = new ArrayList<TuSdkTranscoderProgressListener>();
        this.l = (TuSdkMediaProgress)new TuSdkMediaProgress() {
            public void onProgress(final float n, final TuSdkMediaDataSource tuSdkMediaDataSource, final int n2, final int n3) {
                if (TuSdkEditorTranscoderImpl.this.k.size() == 0) {
                    return;
                }
                for (final TuSdkTranscoderProgressListener tuSdkTranscoderProgressListener : TuSdkEditorTranscoderImpl.this.k) {
                    if (tuSdkTranscoderProgressListener == null) {
                        continue;
                    }
                    tuSdkTranscoderProgressListener.onProgressChanged(n);
                }
            }
            
            public void onCompleted(Exception ex, final TuSdkMediaDataSource tuSdkMediaDataSource, final int n) throws IOException {
                if (TuSdkEditorTranscoderImpl.this.k.size() == 0) {
                    return;
                }
                TuSdkEditorTranscoderImpl.this.b = tuSdkMediaDataSource;
                if (ex == null && TuSdkEditorTranscoderImpl.this.getOutputVideoInfo() != null) {
                    TuSdkEditorTranscoderImpl.this.a(2);
                    for (final TuSdkTranscoderProgressListener tuSdkTranscoderProgressListener : TuSdkEditorTranscoderImpl.this.k) {
                        if (tuSdkTranscoderProgressListener == null) {
                            continue;
                        }
                        tuSdkTranscoderProgressListener.onLoadComplete(TuSdkEditorTranscoderImpl.this.getOutputVideoInfo(), tuSdkMediaDataSource);
                    }
                }
                else {
                    TuSdkEditorTranscoderImpl.this.a(3);
                    for (final TuSdkTranscoderProgressListener tuSdkTranscoderProgressListener2 : TuSdkEditorTranscoderImpl.this.k) {
                        if (tuSdkTranscoderProgressListener2 == null) {
                            continue;
                        }
                        if (TuSdkEditorTranscoderImpl.this.getOutputVideoInfo() == null) {
                            ex = new IllegalArgumentException(" Get Video Information Anomaly");
                        }
                        tuSdkTranscoderProgressListener2.onError(ex);
                    }
                }
            }
        };
        this.a(0);
    }
    
    protected void setEnableTranscode(final boolean j) {
        this.j = j;
    }
    
    @Override
    public void setVideoDataSource(final TuSdkMediaDataSource a) {
        if (a == null || !a.isValid()) {
            TLog.e("%s media source is invalid !!!", new Object[] { "TuSdkEditorTransCoder" });
            return;
        }
        this.a = a;
    }
    
    @Override
    public TuSdkMediaDataSource getVideoDataSource() {
        return this.a;
    }
    
    @Override
    public void setTimeRange(final TuSdkTimeRange f) {
        this.f = f;
    }
    
    @Override
    public void setCanvasRect(final RectF g) {
        this.g = g;
    }
    
    @Override
    public void setCropRect(final RectF h) {
        this.h = h;
    }
    
    @Override
    public float getVideoOutputDuration() {
        return (float)(this.getInputVideoInfo().durationTimeUs / 1000000L);
    }
    
    @Override
    public long getVideoOutputDurationTimeUs() {
        return this.getInputVideoInfo().durationTimeUs;
    }
    
    @Override
    public float getVideoInputDuration() {
        return (float)(this.getOutputVideoInfo().durationTimeUs / 1000000L);
    }
    
    @Override
    public long getVideoInputDurationTimeUS() {
        return this.getOutputVideoInfo().durationTimeUs;
    }
    
    @Override
    public TuSDKVideoInfo getInputVideoInfo() {
        if (this.d == null) {
            this.d = TuSDKMediaUtils.getVideoInfo(this.a);
        }
        return this.d;
    }
    
    @Override
    public TuSDKVideoInfo getOutputVideoInfo() {
        if (this.e == null) {
            this.e = TuSDKMediaUtils.getVideoInfo(this.b);
        }
        return this.e;
    }
    
    @Override
    public void addTransCoderProgressListener(final TuSdkTranscoderProgressListener tuSdkTranscoderProgressListener) {
        if (tuSdkTranscoderProgressListener == null) {
            return;
        }
        this.k.add(tuSdkTranscoderProgressListener);
    }
    
    @Override
    public void removeTransCoderProgressListener(final TuSdkTranscoderProgressListener tuSdkTranscoderProgressListener) {
        if (tuSdkTranscoderProgressListener == null || this.k.size() == 0) {
            return;
        }
        this.k.remove(tuSdkTranscoderProgressListener);
    }
    
    @Override
    public void removeAllTransCoderProgressListener() {
        if (this.k.size() == 0) {
            return;
        }
        this.k.clear();
    }
    
    @Override
    public void startTransCoder() throws IOException {
        this.a(1);
        if (this.a == null || !this.a.isValid() || this.getInputVideoInfo() == null) {
            TLog.e("%s invalid data source !!! ", new Object[] { "TuSdkEditorTransCoder" });
            return;
        }
        if (!this.j) {
            this.l.onProgress(1.0f, this.a, 0, 1);
            this.l.onCompleted((Exception)null, this.a, 1);
            return;
        }
        (this.c = (TuSdkMediaFileCuter)new TuSdkMediaFileCuterImpl()).setMediaDataSource(this.a);
        this.c.setOutputFilePath(this.getOutputTempFilePath().getPath());
        this.c.setOutputVideoFormat(this.a());
        this.c.setOutputAudioFormat(this.c());
        this.c.setTimeSlice(this.d());
        this.c.setCanvasRect(this.g);
        this.c.setCropRect(this.h);
        this.c.run(this.l);
    }
    
    @Override
    public int getStatus() {
        return this.i;
    }
    
    @Override
    public void stopTransCoder() {
        this.a(4);
        this.c.stop();
    }
    
    @Override
    public void destroy() {
        if (this.c != null) {
            this.c.stop();
        }
        FileHelper.delete(this.mMovieOutputTempFilePath);
        this.k.clear();
        this.l = null;
        this.c = null;
    }
    
    private void a(final int i) {
        this.i = i;
    }
    
    protected File getOutputTempFilePath() {
        if (this.mMovieOutputTempFilePath == null) {
            this.mMovieOutputTempFilePath = new File(TuSdk.getAppTempPath(), String.format("lsq_%s.mp4", StringHelper.timeStampString()));
        }
        return this.mMovieOutputTempFilePath;
    }
    
    private MediaFormat a() {
        int bitrate = this.getInputVideoInfo().bitrate;
        if (this.getInputVideoInfo().profile >= 66) {
            bitrate *= 2;
        }
        return TuSdkMediaFormat.buildSafeVideoEncodecFormat(this.b().width, this.b().height, this.getInputVideoInfo().fps, bitrate, 2130708361, 0, 0);
    }
    
    private TuSdkSize b() {
        if (this.getInputVideoInfo() == null) {
            return TuSdkSize.create(0);
        }
        TuSdkSize tuSdkSize = TuSdkSize.create(this.getInputVideoInfo().width, this.getInputVideoInfo().height);
        if (tuSdkSize.minSide() >= 1080) {
            tuSdkSize = TuSdkSize.create((int)(tuSdkSize.width * 0.5), (int)(tuSdkSize.height * 0.5));
        }
        else if (720 <= tuSdkSize.minSide() && tuSdkSize.minSide() < 1080) {
            tuSdkSize = TuSdkSize.create((int)(tuSdkSize.width * 0.75), (int)(tuSdkSize.height * 0.75));
        }
        if (this.getInputVideoInfo().videoOrientation == ImageOrientation.Right || this.getInputVideoInfo().videoOrientation == ImageOrientation.Left) {
            tuSdkSize = TuSdkSize.create(tuSdkSize.height, tuSdkSize.width);
        }
        return tuSdkSize;
    }
    
    @TargetApi(16)
    private MediaFormat c() {
        return TuSdkMediaFormat.buildSafeAudioEncodecFormat();
    }
    
    private TuSdkMediaTimeSlice d() {
        if (this.f == null || this.f.durationTimeUS() <= 0L) {
            this.f = TuSdkTimeRange.makeTimeUsRange(0L, this.getInputVideoInfo().durationTimeUs);
        }
        return new TuSdkMediaTimeSlice(this.f.getStartTimeUS(), this.f.getEndTimeUS());
    }
}
