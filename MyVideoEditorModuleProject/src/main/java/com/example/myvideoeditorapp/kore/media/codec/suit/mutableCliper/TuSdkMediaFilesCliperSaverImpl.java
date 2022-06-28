// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.mutableCliper;

import com.example.myvideoeditorapp.kore.media.codec.video.TuSdkVideoInfo;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioPitchHardImpl;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioResampleHardImpl;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioResample;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioPitch;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioResampleSync;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioPitchSync;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVAssetTrack;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVSampleBuffer;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVMediaProcessQueue;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioInfo;

import java.io.IOException;
import java.nio.ByteBuffer;
import com.example.myvideoeditorapp.kore.media.codec.TuSdkMediaMuxer;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioEncodecSyncBase;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkVideoEncodecSyncBase;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkVideoEncodecSync;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioEncodecSync;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.TuSdkMediaFilesSync;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkMediaFileEncoder;
import android.graphics.RectF;
import com.example.myvideoeditorapp.kore.utils.RectHelper;
import java.util.List;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import android.view.Surface;
import android.graphics.SurfaceTexture;
import android.opengl.EGLContext;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkMediaEncodecSync;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkMediaProgress;
import android.media.MediaFormat;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVAssetTrackSampleBufferOutput;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVAssetTrackOutputSouce;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkCodecCapabilities;
import com.example.myvideoeditorapp.kore.utils.TLog;
import android.media.MediaCodec;

import com.example.myvideoeditorapp.kore.api.extend.TuSdkMediaProgress;
import com.example.myvideoeditorapp.kore.media.codec.TuSdkMediaMuxer;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioInfo;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioPitch;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioPitchHardImpl;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioResample;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioResampleHardImpl;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkEncodeSurface;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkEncoderListener;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkMediaFileSuitEncoderBase;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkVideoSurfaceEncoderListener;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkVideoSurfaceEncoderListenerImpl;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkCodecCapabilities;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVAssetTrack;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVAssetTrackCodecDecoder;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVAssetTrackSampleBufferOutput;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVMediaProcessQueue;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVSampleBuffer;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.TuSdkMediaFilesSync;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioEncodecSync;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioEncodecSyncBase;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioPitchSync;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioResampleSync;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkVideoEncodecSync;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkVideoEncodecSyncBase;
import com.example.myvideoeditorapp.kore.media.codec.video.TuSdkVideoInfo;
import com.example.myvideoeditorapp.kore.media.record.TuSdkRecordSurface;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCorpBuilder;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCropBuilderImpl;
import com.example.myvideoeditorapp.kore.seles.sources.SelesSurfaceReceiver;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.RectHelper;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;

import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkEncodeSurface;
import com.example.myvideoeditorapp.kore.media.record.TuSdkRecordSurface;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkVideoSurfaceEncoderListenerImpl;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCropBuilderImpl;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkEncoderListener;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkVideoSurfaceEncoderListener;
import com.example.myvideoeditorapp.kore.seles.sources.SelesSurfaceReceiver;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVAssetTrackCodecDecoder;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCorpBuilder;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkMediaFileSuitEncoderBase;

public class TuSdkMediaFilesCliperSaverImpl extends TuSdkMediaFileSuitEncoderBase implements TuSdkMediaFilesCliperSaver
{
    private final TuSdkMediaFilesCuterSync a;
    private final SelesVerticeCoordinateCorpBuilder b;
    private AudioRender c;
    private VideoRender d;
    private TuSdkMediaCliperSource e;
    private AVAssetTrackCodecDecoder f;
    private TuSdkMediaCliperSource g;
    private AVAssetTrackCodecDecoder h;
    private Object i;
    private SelesSurfaceReceiver j;
    private TuSdkVideoSurfaceEncoderListener k;
    private TuSdkEncoderListener l;
    private TuSdkMediaCliperSource.TuSdkMediaCliperSourceDelegate m;
    
    public TuSdkMediaFilesCliperSaverImpl() {
        this.a = new TuSdkMediaFilesCuterSync();
        this.b = new SelesVerticeCoordinateCropBuilderImpl(false);
        this.c = new AudioRender();
        this.d = new VideoRender();
        this.i = new Object();
        this.k = new TuSdkVideoSurfaceEncoderListenerImpl() {
            @Override
            public void onSurfaceCreated(final GL10 gl10, final EGLConfig eglConfig) {
                TuSdkMediaFilesCliperSaverImpl.this.initInGLThread();
            }
            
            @Override
            public void onEncoderDrawFrame(final long n, final boolean b) {
                TuSdkMediaFilesCliperSaverImpl.this.a.syncVideoEncodecDrawFrame(TuSdkMediaFilesCliperSaverImpl.this.a(TuSdkMediaFilesCliperSaverImpl.this.f.outputTimeUs()) * 1000L, false, TuSdkMediaFilesCliperSaverImpl.this.j, TuSdkMediaFilesCliperSaverImpl.this.mEncoder.getVideoEncoder());
            }
            
            @Override
            public void onEncoderUpdated(final MediaCodec.BufferInfo bufferInfo) {
                if (TLog.LOG_VIDEO_ENCODEC_INFO) {
                    TuSdkCodecCapabilities.logBufferInfo("VideoEncoderListener updated", bufferInfo);
                }
                TuSdkMediaFilesCliperSaverImpl.this.a(false);
            }
            
            @Override
            public void onEncoderCompleted(final Exception ex) {
                if (ex == null) {
                    TLog.d("%s encodec Video updatedToEOS", "TuSdkMediaFilesCliperSaverImpl");
                    TuSdkMediaFilesCliperSaverImpl.this.a(false);
                }
                else {
                    TLog.e(ex, "%s VideoEncoderListener thread catch exception, The thread will exit.", "TuSdkMediaFilesCliperSaverImpl");
                }
                TuSdkMediaFilesCliperSaverImpl.this.a(ex);
            }
        };
        this.l = new TuSdkEncoderListener() {
            @Override
            public void onEncoderUpdated(final MediaCodec.BufferInfo bufferInfo) {
                if (TLog.LOG_AUDIO_ENCODEC_INFO) {
                    TuSdkCodecCapabilities.logBufferInfo("AudioEncoderListener updated", bufferInfo);
                }
            }
            
            @Override
            public void onEncoderCompleted(final Exception ex) {
                if (ex == null) {
                    TLog.d("%s encodec Audio updatedToEOS", "TuSdkMediaFilesCliperSaverImpl");
                    TuSdkMediaFilesCliperSaverImpl.this.a(false);
                }
                else {
                    TLog.e(ex, "%s AudioEncoderListener thread catch exception, The thread will exit.", "TuSdkMediaFilesCliperSaverImpl");
                }
                TuSdkMediaFilesCliperSaverImpl.this.a(ex);
            }
        };
        this.m = new TuSdkMediaCliperSource.TuSdkMediaCliperSourceDelegate() {
            @Override
            public void didAddSourceItem(final TuSdkMediaTrackItem tuSdkMediaTrackItem) {
                switch (tuSdkMediaTrackItem.track().mediaType().ordinal()) {
                }
            }
            
            @Override
            public void ddiRemoveSourceIem(final TuSdkMediaTrackItem tuSdkMediaTrackItem) {
                switch (tuSdkMediaTrackItem.track().mediaType().ordinal()) {
                }
            }
        };
        (this.e = new TuSdkMediaCliperSource()).setDelegate(this.m);
        (this.f = new AVAssetTrackCodecDecoder(this.e)).addTarget(this.d);
        (this.g = new TuSdkMediaCliperSource()).setDelegate(this.m);
        (this.h = new AVAssetTrackCodecDecoder(this.g)).addTarget(this.c);
    }
    
    private boolean a() {
        return this.getOutputAudioInfo() != null && this.b();
    }
    
    private boolean b() {
        return this.g.items().size() > 0;
    }
    
    @Override
    public int setOutputAudioFormat(final MediaFormat outputAudioFormat) {
        if (this.b() && outputAudioFormat != null) {
            return super.setOutputAudioFormat(outputAudioFormat);
        }
        TLog.i("The input video file's not find an audio track", new Object[0]);
        return -1;
    }
    
    private void c() {
        this.mEncoder.requestVideoKeyFrame();
        this.d.a(new Runnable() {
            @Override
            public void run() {
                TuSdkMediaFilesCliperSaverImpl.this.d.a();
            }
        });
        if (this.a()) {
            this.c.a(new Runnable() {
                @Override
                public void run() {
                    try {
                        TuSdkMediaFilesCliperSaverImpl.this.c.a();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }
    
    @Override
    public boolean run(final TuSdkMediaProgress tuSdkMediaProgress) throws IOException {
        if (this.e.items() == null || this.e.items().size() == 0) {
            TLog.w("%s run need a input file path.", "TuSdkMediaFilesCliperSaverImpl");
            return false;
        }
        return super.run(tuSdkMediaProgress);
    }
    
    @Override
    public void stop() {
        if (this.mState == 1) {
            TLog.w("%s already stoped.", "TuSdkMediaFilesCliperSaverImpl");
            return;
        }
        this.d.b(new Runnable() {
            @Override
            public void run() {
                TuSdkMediaFilesCliperSaverImpl.this.mState = 1;
                if (TuSdkMediaFilesCliperSaverImpl.this.j != null) {
                    TuSdkMediaFilesCliperSaverImpl.this.j.destroy();
                    TuSdkMediaFilesCliperSaverImpl.this.j = null;
                }
                TuSdkMediaFilesCliperSaverImpl.this.mEncoder.release();
                TuSdkMediaFilesCliperSaverImpl.this.a.release();
            }
        });
        this.d.release();
        this.c.release();
    }
    
    private void a(final boolean b) {
        ThreadHelper.post(new Runnable() {
            final /* synthetic */ float a = b ? 1.0f : TuSdkMediaFilesCliperSaverImpl.this.a.calculateProgress();
            
            @Override
            public void run() {
                if (TuSdkMediaFilesCliperSaverImpl.this.mProgress == null) {
                    return;
                }
                TuSdkMediaFilesCliperSaverImpl.this.mProgress.onProgress(this.a, null, -1, 0);
            }
        });
    }
    
    private void a(final Exception ex) {
        if (ex == null) {
            if (!this.a.isEncodecCompleted()) {
                return;
            }
            this.mEncoder.cleanTemp();
        }
        this.a(true);
        this.a.setBenchmarkEnd();
        ThreadHelper.post(new Runnable() {
            @Override
            public void run() {
                TuSdkMediaFilesCliperSaverImpl.this.stop();
                if (TuSdkMediaFilesCliperSaverImpl.this.mProgress == null) {
                    return;
                }
                try {
                    TuSdkMediaFilesCliperSaverImpl.this.mProgress.onCompleted(ex, TuSdkMediaFilesCliperSaverImpl.this.mEncoder.getOutputDataSource(), 1);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        });
        TLog.d("%s runCompleted: %f / %f", "TuSdkMediaFilesCliperSaverImpl", this.a.benchmarkUs() / 1000000.0f, this.a.totalDurationUs() / 1000000.0f);
    }
    
    @Override
    protected boolean _init() {
        if (!this.d()) {
            TLog.w("%s init Encodec Environment failed.", "TuSdkMediaFilesCliperSaverImpl");
            return false;
        }
        return true;
    }
    
    private boolean d() {
        this.b.setOutputSize(this.mEncoder.getOutputSize());
        (this.j = new SelesSurfaceReceiver()).setTextureCoordinateBuilder(this.b);
        this.j.addTarget(this.mEncoder.getFilterBridge(), 0);
        this.mEncoder.setSurfaceRender(this.mSurfaceRender);
        this.mEncoder.setAudioRender(this.mAudioRender);
        this.mEncoder.setMediaSync(this.a);
        this.mEncoder.setListener(this.k, this.l);
        return this.mEncoder.prepare(null);
    }
    
    protected void initInGLThread() {
        if (this.j == null) {
            return;
        }
        this.j.initInGLThread();
        this.j.setSurfaceTextureListener((SurfaceTexture.OnFrameAvailableListener)new SurfaceTexture.OnFrameAvailableListener() {
            public void onFrameAvailable(final SurfaceTexture surfaceTexture) {
                TuSdkMediaFilesCliperSaverImpl.this.mEncoder.requestVideoRender(TuSdkMediaFilesCliperSaverImpl.this.a(TuSdkMediaFilesCliperSaverImpl.this.f.outputTimeUs()));
            }
        });
        final Surface outputSurface = new Surface(this.j.requestSurfaceTexture());
        if (this.f != null) {
            this.f.setOutputSurface(outputSurface);
        }
        this.c();
    }
    
    public TuSdkSize preferredOutputSize() {
        final List<TuSdkMediaTrackItem> items = this.e.items();
        if (items.size() == 0) {
            return null;
        }
        return items.get(0).track().presentSize();
    }
    
    private void a(final TuSdkMediaTrackItem tuSdkMediaTrackItem) {
        if (tuSdkMediaTrackItem != null && tuSdkMediaTrackItem.outputRatio() > 0.0f) {
            this.j.setCropRect(tuSdkMediaTrackItem.cropRectF());
            final RectF computerCenterRectF = RectHelper.computerCenterRectF(this.j.outputFrameSize(), tuSdkMediaTrackItem.outputRatio());
            final float n = computerCenterRectF.left / computerCenterRectF.width() / 2.0f;
            final float n2 = computerCenterRectF.top / computerCenterRectF.height() / 2.0f;
            this.j.setCanvasRect(new RectF(n, n2, 1.0f - n, 1.0f - n2));
        }
        else {
            this.j.setCanvasRect(new RectF(0.0f, 0.0f, 1.0f, 1.0f));
            this.j.setCropRect(new RectF(0.0f, 0.0f, 1.0f, 1.0f));
        }
    }
    
    public void setOutputSize(final TuSdkSize outputSize, final boolean enableClip) {
        if (this.j != null) {
            this.j.setOutputSize(outputSize);
            this.j.setEnableClip(enableClip);
        }
    }
    
    @Override
    public void appendTrackItem(final TuSdkMediaTrackItem tuSdkMediaTrackItem, final TuSdkMediaFileCliperPlayer.Callback<Boolean> callback) {
        switch (tuSdkMediaTrackItem.track().mediaType().ordinal()) {
            case 1: {
                this.e.appendTrackItem(tuSdkMediaTrackItem);
                break;
            }
            case 2: {
                this.g.appendTrackItem(tuSdkMediaTrackItem);
                break;
            }
        }
    }
    
    private void e() {
        this.mEncoder.signalVideoEndOfInputStream();
    }
    
    private void f() throws IOException {
        this.mEncoder.signalAudioEndOfInputStream(this.a(this.h.outputTimeUs()));
    }
    
    private long a(final long n) {
        return (long)(1.0f / 1.0f * n);
    }
    
    public class TuSdkMediaFilesCuterSync implements TuSdkMediaFilesSync
    {
        private long b;
        private long c;
        private boolean d;
        private _AudioEncodecSync e;
        private _VideoEncodecSync f;
        
        public TuSdkMediaFilesCuterSync() {
            this.b = System.nanoTime();
            this.d = false;
        }
        
        @Override
        public TuSdkAudioEncodecSync getAudioEncodecSync() {
            if (this.e == null) {
                this.e = new _AudioEncodecSync();
            }
            return this.e;
        }
        
        @Override
        public TuSdkVideoEncodecSync getVideoEncodecSync() {
            if (this.f == null) {
                this.f = new _VideoEncodecSync();
            }
            return this.f;
        }
        
        @Override
        public void release() {
            if (this.d) {
                return;
            }
            this.d = true;
            if (this.e != null) {
                this.e.release();
                this.e = null;
            }
            if (this.f != null) {
                this.f.release();
                this.f = null;
            }
        }
        
        @Override
        protected void finalize() throws Throwable {
            this.release();
            super.finalize();
        }
        
        @Override
        public long benchmarkUs() {
            return this.c / 1000L;
        }
        
        @Override
        public void setBenchmarkEnd() {
            this.c = System.nanoTime() - this.b;
        }
        
        @Override
        public long totalDurationUs() {
            return TuSdkMediaFilesCliperSaverImpl.this.e.durationTimeUs();
        }
        
        @Override
        public float calculateProgress() {
            float a = 0.0f;
            if (this.totalDurationUs() > 0L) {
                a = (TuSdkMediaFilesCliperSaverImpl.this.f.outputTimeUs() / (float)TuSdkMediaFilesCliperSaverImpl.this.f.durationTimeUs() + (TuSdkMediaFilesCliperSaverImpl.this.a() ? (TuSdkMediaFilesCliperSaverImpl.this.h.outputTimeUs() / (float)TuSdkMediaFilesCliperSaverImpl.this.h.durationTimeUs()) : 1.0f)) / 2.0f;
            }
            return Math.min(Math.max(a, 0.0f), 1.0f);
        }
        
        @Override
        public boolean isEncodecCompleted() {
            return this.isVideoEncodeCompleted() && this.isAudioEncodeCompleted();
        }
        
        public boolean isAudioEncodeCompleted() {
            return this.e == null || !TuSdkMediaFilesCliperSaverImpl.this.a() || this.e.isAudioEncodeCompleted();
        }
        
        public boolean isVideoEncodeCompleted() {
            return this.f == null || this.f.isVideoEncodeCompleted();
        }
        
        @Override
        public void syncVideoEncodecDrawFrame(final long n, final boolean b, final TuSdkRecordSurface tuSdkRecordSurface, final TuSdkEncodeSurface tuSdkEncodeSurface) {
            if (this.f == null) {
                return;
            }
            this.f.syncVideoEncodecDrawFrame(n, b, tuSdkRecordSurface, tuSdkEncodeSurface);
            synchronized (TuSdkMediaFilesCliperSaverImpl.this.i) {
                TuSdkMediaFilesCliperSaverImpl.this.i.notify();
            }
        }
        
        private class _VideoEncodecSync extends TuSdkVideoEncodecSyncBase
        {
            @Override
            public void syncVideoEncodecDrawFrame(final long n, final boolean b, final TuSdkRecordSurface tuSdkRecordSurface, final TuSdkEncodeSurface tuSdkEncodeSurface) {
                if (tuSdkRecordSurface == null || tuSdkEncodeSurface == null || this.mReleased) {
                    return;
                }
                tuSdkRecordSurface.updateSurfaceTexImage();
                if (b) {
                    this.clearLocker();
                    return;
                }
                final long n2 = n / 1000L;
                if (this.needSkip(n2)) {
                    this.unlockVideoTimestampUs(n2);
                    this.mPreviousTimeUs = -1L;
                    this.mFrameIntervalUs = 0L;
                    return;
                }
                if (this.mPreviousTimeUs < 0L) {
                    this.mPreviousTimeUs = n2;
                }
                this.mFrameIntervalUs = n2 - this.mPreviousTimeUs;
                this.mPreviousTimeUs = n2;
                long mLastTimeUs = this.calculateEncodeTimestampUs(this.mFrameRates, this.mFrameCounts);
                if (mLastTimeUs < 1L) {
                    this.renderToEncodec(mLastTimeUs, n2, tuSdkRecordSurface, tuSdkEncodeSurface);
                    return;
                }
                long n3 = mLastTimeUs * 1000L;
                while (mLastTimeUs < n2) {
                    this.lockVideoTimestampUs(mLastTimeUs);
                    this.mLastTimeUs = mLastTimeUs;
                    ++this.mFrameCounts;
                    tuSdkEncodeSurface.duplicateFrameReadyInGLThread(n3);
                    tuSdkEncodeSurface.swapBuffers(n3);
                    mLastTimeUs = this.calculateEncodeTimestampUs(this.mFrameRates, this.mFrameCounts);
                    n3 = mLastTimeUs * 1000L;
                }
                if (this.isLastDecodeFrame(n2)) {
                    this.renderToEncodec(mLastTimeUs, n2, tuSdkRecordSurface, tuSdkEncodeSurface);
                    return;
                }
                if (mLastTimeUs > n2 && this.getInputIntervalUs() > 0L && mLastTimeUs > n2 + this.getInputIntervalUs()) {
                    this.unlockVideoTimestampUs(n2);
                    return;
                }
                this.renderToEncodec(n2, n2, tuSdkRecordSurface, tuSdkEncodeSurface);
            }
            
            @Override
            protected boolean isLastDecodeFrame(final long n) {
                return TuSdkMediaFilesCliperSaverImpl.this.f.isDecodeCompleted();
            }
            
            @Override
            protected boolean needSkip(final long n) {
                return false;
            }
        }
        
        private class _AudioEncodecSync extends TuSdkAudioEncodecSyncBase
        {
            @Override
            public void syncAudioEncodecOutputBuffer(final TuSdkMediaMuxer tuSdkMediaMuxer, final int n, final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) {
                super.syncAudioEncodecOutputBuffer(tuSdkMediaMuxer, n, byteBuffer, bufferInfo);
            }
            
            @Override
            public void syncAudioEncodecInfo(final TuSdkAudioInfo tuSdkAudioInfo) {
                super.syncAudioEncodecInfo(tuSdkAudioInfo);
            }
        }
    }
    
    private class AudioRender implements AVAssetTrackSampleBufferOutput.AVAssetTrackSampleBufferInput
    {
        private AVMediaProcessQueue b;
        private DefaultAduioRender c;
        
        private AudioRender() {
            this.b = new AVMediaProcessQueue();
            this.c = new DefaultAduioRender();
        }
        
        private void a(final Runnable runnable) {
            this.b.runAsynchronouslyOnProcessingQueue(runnable);
        }
        
        private void b(final Runnable runnable) {
            this.b.runSynchronouslyOnProcessingQueue(runnable);
        }
        
        private void a() throws IOException {
            if (TuSdkMediaFilesCliperSaverImpl.this.h == null || TuSdkMediaFilesCliperSaverImpl.this.mState != 0) {
                TLog.i("%s : The export session terminated unexpectedly, probably because the user forcibly stopped the session.", this);
                return;
            }
            if (TuSdkMediaFilesCliperSaverImpl.this.h.renderOutputBuffers()) {
                this.a(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            AudioRender.this.a();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
            else {
                TuSdkMediaFilesCliperSaverImpl.this.f();
            }
        }
        
        @Override
        public void newFrameReady(final AVSampleBuffer avSampleBuffer) throws IOException {
            avSampleBuffer.info().presentationTimeUs = avSampleBuffer.renderTimeUs();
            this.c.queueInputBuffer(avSampleBuffer.buffer(), avSampleBuffer.info());
        }
        
        @Override
        public void outputFormatChaned(final MediaFormat mediaFormat, final AVAssetTrack avAssetTrack) {
            this.c.changeFormat(new TuSdkAudioInfo(mediaFormat));
        }
        
        private void b() {
            if (TuSdkMediaFilesCliperSaverImpl.this.h == null) {
                return;
            }
            this.c.reset();
            TuSdkMediaFilesCliperSaverImpl.this.h.reset();
        }
        
        public void release() {
            this.b(new Runnable() {
                @Override
                public void run() {
                    AudioRender.this.b();
                }
            });
            this.c.release();
            this.b.quit();
        }
        
        private class DefaultAduioRender implements TuSdkAudioPitchSync, TuSdkAudioResampleSync
        {
            private TuSdkAudioPitch b;
            private TuSdkAudioResample c;
            
            public boolean queueInputBuffer(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) throws IOException {
                return this.c.queueInputBuffer(byteBuffer, bufferInfo);
            }
            
            public void changeFormat(final TuSdkAudioInfo tuSdkAudioInfo) {
                if (this.c == null) {
                    final TuSdkAudioInfo outputAudioInfo = TuSdkMediaFilesCliperSaverImpl.this.getOutputAudioInfo();
                    (this.c = new TuSdkAudioResampleHardImpl(outputAudioInfo)).changeFormat(tuSdkAudioInfo);
                    this.c.setMediaSync(this);
                    (this.b = new TuSdkAudioPitchHardImpl(outputAudioInfo)).changeSpeed(1.0f);
                    this.b.setMediaSync(this);
                }
                else {
                    this.c.changeFormat(tuSdkAudioInfo);
                }
            }
            
            @Override
            public void syncAudioResampleOutputBuffer(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) throws IOException {
                this.b.queueInputBuffer(byteBuffer, bufferInfo);
            }
            
            @Override
            public void syncAudioPitchOutputBuffer(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) throws IOException {
                byteBuffer.position(bufferInfo.offset);
                byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
                if (TuSdkMediaFilesCliperSaverImpl.this.mEncoder.getAudioOperation() != null) {
                    while (!ThreadHelper.isInterrupted() && TuSdkMediaFilesCliperSaverImpl.this.mEncoder.getAudioOperation().writeBuffer(byteBuffer, bufferInfo) == 0) {}
                }
            }
            
            @Override
            public void release() {
                if (this.b == null || this.c == null) {
                    return;
                }
                this.b.release();
                this.c.release();
            }
            
            public void reset() {
                if (this.b == null || this.c == null) {
                    return;
                }
                this.b.reset();
                this.c.reset();
            }
        }
    }
    
    private class VideoRender implements AVAssetTrackSampleBufferOutput.AVAssetTrackSampleBufferInput
    {
        private AVMediaProcessQueue b;
        
        VideoRender() {
            this.b = new AVMediaProcessQueue();
        }
        
        @Override
        public void newFrameReady(final AVSampleBuffer avSampleBuffer) {
            if (avSampleBuffer.isRenered()) {
                TuSdkMediaFilesCliperSaverImpl.this.mEncoder.requestVideoRender(TuSdkMediaFilesCliperSaverImpl.this.a(TuSdkMediaFilesCliperSaverImpl.this.f.outputTimeUs()));
            }
            try {
                synchronized (TuSdkMediaFilesCliperSaverImpl.this.i) {
                    TuSdkMediaFilesCliperSaverImpl.this.i.wait(500L);
                }
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        
        @Override
        public void outputFormatChaned(final MediaFormat corp, final AVAssetTrack avAssetTrack) {
            final TuSdkVideoInfo tuSdkVideoInfo = new TuSdkVideoInfo(avAssetTrack.mediaFormat());
            if (tuSdkVideoInfo.sps != null && tuSdkVideoInfo.sps.dar_width != 0 && tuSdkVideoInfo.sps.dar_height != 0) {
                final TuSdkSize create = TuSdkSize.create(tuSdkVideoInfo.sps.dar_width, tuSdkVideoInfo.sps.dar_height);
                corp.setInteger("width", create.width);
                corp.setInteger("height", create.height);
                corp.setInteger("crop-right", create.width);
            }
            final TuSdkVideoInfo tuSdkVideoInfo2 = new TuSdkVideoInfo(corp);
            tuSdkVideoInfo2.setCorp(corp);
            TuSdkMediaFilesCliperSaverImpl.this.j.setInputSize(tuSdkVideoInfo2.codecSize);
            TuSdkMediaFilesCliperSaverImpl.this.j.setPreCropRect(tuSdkVideoInfo2.codecCrop);
            TuSdkMediaFilesCliperSaverImpl.this.j.setInputRotation(TuSdkMediaFilesCliperSaverImpl.this.e.currentItem().outputOrientation());
            TuSdkMediaFilesCliperSaverImpl.this.a(TuSdkMediaFilesCliperSaverImpl.this.e.currentItem());
        }
        
        void a(final Runnable runnable) {
            this.b.runAsynchronouslyOnProcessingQueue(runnable);
        }
        
        void b(final Runnable runnable) {
            this.b.runSynchronouslyOnProcessingQueue(runnable);
        }
        
        private void a() {
            if (TuSdkMediaFilesCliperSaverImpl.this.f == null || TuSdkMediaFilesCliperSaverImpl.this.mState != 0) {
                TLog.i("%s :The export session terminated unexpectedly, probably because the user forcibly stopped the session.", this);
                return;
            }
            if (TuSdkMediaFilesCliperSaverImpl.this.f.renderOutputBuffers()) {
                this.a(new Runnable() {
                    @Override
                    public void run() {
                        VideoRender.this.a();
                    }
                });
            }
            else {
                TuSdkMediaFilesCliperSaverImpl.this.e();
                TLog.i("%s : play done", this);
            }
        }
        
        public void release() {
            this.b.quit();
        }
    }
}
