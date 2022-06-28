// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer;

import com.example.myvideoeditorapp.kore.media.codec.video.TuSdkVideoInfo;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioPitchHardImpl;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioResampleHardImpl;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioResample;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioPitch;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioResampleSync;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioPitchSync;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioInfo;

import java.io.IOException;
import java.nio.ByteBuffer;
import com.example.myvideoeditorapp.kore.media.codec.TuSdkMediaMuxer;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioEncodecSyncBase;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkVideoEncodecSyncBase;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkVideoEncodecSync;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioEncodecSync;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkMediaFileEncoder;
import java.util.Collection;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import android.view.Surface;
import android.graphics.SurfaceTexture;
import android.opengl.EGLContext;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkMediaEncodecSync;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkMediaProgress;
import android.graphics.RectF;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaFormat;
import android.media.MediaFormat;
import java.util.Iterator;
import java.util.Arrays;
import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;
import java.util.ArrayList;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkCodecCapabilities;
import com.example.myvideoeditorapp.kore.utils.TLog;
import android.media.MediaCodec;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkEncodeSurface;
import com.example.myvideoeditorapp.kore.media.record.TuSdkRecordSurface;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkVideoSurfaceEncoderListenerImpl;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCropBuilderImpl;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkEncoderListener;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkVideoSurfaceEncoderListener;
import com.example.myvideoeditorapp.kore.seles.sources.SelesSurfaceReceiver;
import java.util.List;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaTimeSlice;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCorpBuilder;
import android.annotation.TargetApi;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkMediaFileSuitEncoderBase;

@TargetApi(18)
public class TuSdkMediaFilesCuterImpl extends TuSdkMediaFileSuitEncoderBase implements TuSdkMediaFilesCuter
{
    private final TuSdkMediaFilesCuterSync a;
    private final SelesVerticeCoordinateCorpBuilder b;
    private AudioRender c;
    private VideoRender d;
    private TuSdkMediaTimeSlice e;
    private List<AVAsset> f;
    private boolean g;
    private AVAssetTrackOutputSouce h;
    private AVAssetTrackCodecDecoder i;
    private AVAssetTrackOutputSouce j;
    private AVAssetTrackCodecDecoder k;
    private Object l;
    private long m;
    private boolean n;
    private SelesSurfaceReceiver o;
    private TuSdkVideoSurfaceEncoderListener p;
    private TuSdkEncoderListener q;
    
    public TuSdkMediaFilesCuterImpl() {
        this.a = new TuSdkMediaFilesCuterSync();
        this.b = new SelesVerticeCoordinateCropBuilderImpl(false);
        this.c = new AudioRender();
        this.d = new VideoRender();
        this.l = new Object();
        this.m = 0L;
        this.n = false;
        this.p = new TuSdkVideoSurfaceEncoderListenerImpl() {
            @Override
            public void onSurfaceCreated(final GL10 gl10, final EGLConfig eglConfig) {
                TuSdkMediaFilesCuterImpl.this.initInGLThread();
            }
            
            @Override
            public void onEncoderDrawFrame(final long n, final boolean b) {
                TuSdkMediaFilesCuterImpl.this.a.syncVideoEncodecDrawFrame(TuSdkMediaFilesCuterImpl.this.a(TuSdkMediaFilesCuterImpl.this.i.outputTimeUs()) * 1000L, false, TuSdkMediaFilesCuterImpl.this.o, TuSdkMediaFilesCuterImpl.this.mEncoder.getVideoEncoder());
            }
            
            @Override
            public void onEncoderUpdated(final MediaCodec.BufferInfo bufferInfo) {
                if (TLog.LOG_VIDEO_ENCODEC_INFO) {
                    TuSdkCodecCapabilities.logBufferInfo("VideoEncoderListener updated", bufferInfo);
                }
                TuSdkMediaFilesCuterImpl.this.a(false);
            }
            
            @Override
            public void onEncoderCompleted(final Exception ex) {
                if (ex == null) {
                    TLog.d("%s encodec Video updatedToEOS", "TuSdkMediaFileCuterImpl");
                    TuSdkMediaFilesCuterImpl.this.a(false);
                }
                else {
                    TLog.e(ex, "%s VideoEncoderListener thread catch exception, The thread will exit.", "TuSdkMediaFileCuterImpl");
                }
                TuSdkMediaFilesCuterImpl.this.a(ex);
            }
        };
        this.q = new TuSdkEncoderListener() {
            @Override
            public void onEncoderUpdated(final MediaCodec.BufferInfo bufferInfo) {
                if (TLog.LOG_AUDIO_ENCODEC_INFO) {
                    TuSdkCodecCapabilities.logBufferInfo("AudioEncoderListener updated", bufferInfo);
                }
            }
            
            @Override
            public void onEncoderCompleted(final Exception ex) {
                if (ex == null) {
                    TLog.d("%s encodec Audio updatedToEOS", "TuSdkMediaFileCuterImpl");
                    TuSdkMediaFilesCuterImpl.this.a(false);
                }
                else {
                    TLog.e(ex, "%s AudioEncoderListener thread catch exception, The thread will exit.", "TuSdkMediaFileCuterImpl");
                }
                TuSdkMediaFilesCuterImpl.this.a(ex);
            }
        };
        this.f = new ArrayList<AVAsset>(2);
        this.e = new TuSdkMediaTimeSlice(0L, Long.MAX_VALUE);
    }
    
    public final int maxInputSize() {
        return 9;
    }
    
    @Override
    public final void setMediaDataSource(final TuSdkMediaDataSource tuSdkMediaDataSource) {
        this.setMediaDataSources(Arrays.asList(tuSdkMediaDataSource));
    }
    
    @Override
    public final void setMediaDataSources(final List<TuSdkMediaDataSource> list) {
        if (list == null || list.size() == 0) {
            TLog.w("%s setMediaDataSource not exists: %s", "TuSdkMediaFileCuterImpl", list);
            return;
        }
        if (list.size() > this.maxInputSize()) {
            TLog.w("The maximum number of video supported is %d", this.maxInputSize());
        }
        for (final TuSdkMediaDataSource tuSdkMediaDataSource : (list.size() > this.maxInputSize()) ? list.subList(0, 8) : list) {
            if (!tuSdkMediaDataSource.isValid()) {
                TLog.e("%s :This data source is invalid", tuSdkMediaDataSource);
            }
            else {
                final AVAssetDataSource avAssetDataSource = new AVAssetDataSource(tuSdkMediaDataSource);
                if (avAssetDataSource.tracksWithMediaType(AVMediaType.AVMediaTypeVideo).size() != avAssetDataSource.tracksWithMediaType(AVMediaType.AVMediaTypeAudio).size()) {
                    continue;
                }
                this.f.add(avAssetDataSource);
            }
        }
        this.d();
    }
    
    private boolean a() {
        return this.getOutputAudioInfo() != null && this.g;
    }
    
    public void setEnableAudioCheck(final boolean n) {
        this.n = n;
    }
    
    @Override
    public int setOutputAudioFormat(final MediaFormat outputAudioFormat) {
        if (this.f.size() == 0) {
            TLog.e("SetOutputAudioFormat must be called after entering a valid file.", new Object[0]);
            return -1;
        }
        if (this.g && outputAudioFormat != null) {
            final int audioChannelCount = TuSdkMediaFormat.getAudioChannelCount(outputAudioFormat);
            final int audioChannelCount2 = TuSdkMediaFormat.getAudioChannelCount(this.j.inputTrack().mediaFormat());
            final int audioSampleRate = TuSdkMediaFormat.getAudioSampleRate(outputAudioFormat);
            final int audioSampleRate2 = TuSdkMediaFormat.getAudioSampleRate(this.j.inputTrack().mediaFormat());
            if (audioChannelCount != audioChannelCount2 || audioSampleRate != audioSampleRate2) {
                outputAudioFormat.setInteger("channel-count", audioChannelCount2);
                TLog.e("The number of audio channels is not supported when changing speed.", new Object[0]);
            }
            return super.setOutputAudioFormat(outputAudioFormat);
        }
        TLog.i("The input video file's not find an audio track", new Object[0]);
        return -1;
    }
    
    private void b() {
        this.mEncoder.requestVideoKeyFrame();
        this.d.a(new Runnable() {
            @Override
            public void run() {
                TuSdkMediaFilesCuterImpl.this.d.a();
            }
        });
        if (!this.n && this.a()) {
            this.c.a(new Runnable() {
                @Override
                public void run() {
                    try {
                        TuSdkMediaFilesCuterImpl.this.c.a();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }
    
    @Override
    public void setCropRect(final RectF cropRect) {
        if (cropRect == null) {
            return;
        }
        this.b.setCropRect(cropRect);
    }
    
    @Override
    public void setTimeSlice(final TuSdkMediaTimeSlice e) {
        if (this.mState == 1) {
            TLog.w("%s already stoped.", "TuSdkMediaFileCuterImpl");
            return;
        }
        if (e == null || e.startUs < 0L) {
            TLog.e("%s Invalid slice. %s", "TuSdkMediaFileCuterImpl", e);
            return;
        }
        if (e.isReverse()) {
            TLog.e("%s Reverse slicing is not supported now %s", "TuSdkMediaFileCuterImpl", e);
            return;
        }
        this.e = e;
        final AVTimeRange avTimeRangeMake = AVTimeRange.AVTimeRangeMake(e.startUs, e.endUs - e.startUs);
        if (this.h != null) {
            this.h.setTimeRange(avTimeRangeMake);
        }
        if (this.j != null) {
            this.j.setTimeRange(avTimeRangeMake);
        }
    }
    
    @Override
    public boolean run(final TuSdkMediaProgress tuSdkMediaProgress) throws IOException {
        if (this.f == null || this.f.size() == 0) {
            TLog.w("%s run need a input file path.", "TuSdkMediaFileCuterImpl");
            return false;
        }
        return super.run(tuSdkMediaProgress);
    }
    
    @Override
    public void stop() {
        if (this.mState == 1) {
            TLog.w("%s already stoped.", "TuSdkMediaFileCuterImpl");
            return;
        }
        this.d.b(new Runnable() {
            @Override
            public void run() {
                TuSdkMediaFilesCuterImpl.this.mState = 1;
                if (TuSdkMediaFilesCuterImpl.this.o != null) {
                    TuSdkMediaFilesCuterImpl.this.o.destroy();
                    TuSdkMediaFilesCuterImpl.this.o = null;
                }
                TuSdkMediaFilesCuterImpl.this.mEncoder.release();
                TuSdkMediaFilesCuterImpl.this.a.release();
            }
        });
        this.d.release();
        this.c.release();
    }
    
    private void a(final boolean b) {
        ThreadHelper.post(new Runnable() {
            final /* synthetic */ float a = b ? 1.0f : TuSdkMediaFilesCuterImpl.this.a.calculateProgress();
            
            @Override
            public void run() {
                if (TuSdkMediaFilesCuterImpl.this.mProgress == null) {
                    return;
                }
                if (TuSdkMediaFilesCuterImpl.this.h.inputTrack() == null) {
                    return;
                }
                if (TuSdkMediaFilesCuterImpl.this.h.inputTrack().getAsset() instanceof AVAssetDataSource) {
                    final AVAssetDataSource avAssetDataSource = (AVAssetDataSource)TuSdkMediaFilesCuterImpl.this.h.inputTrack().getAsset();
                    TuSdkMediaFilesCuterImpl.this.mProgress.onProgress(this.a, avAssetDataSource.dataSource(), TuSdkMediaFilesCuterImpl.this.f.indexOf(avAssetDataSource.dataSource()), TuSdkMediaFilesCuterImpl.this.f.size());
                }
                else {
                    TuSdkMediaFilesCuterImpl.this.mProgress.onProgress(this.a, null, -1, TuSdkMediaFilesCuterImpl.this.f.size());
                }
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
        this.stop();
        ThreadHelper.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TuSdkMediaFilesCuterImpl.this.mProgress == null) {
                    return;
                }
                try {
                    TuSdkMediaFilesCuterImpl.this.mProgress.onCompleted(ex, TuSdkMediaFilesCuterImpl.this.mEncoder.getOutputDataSource(), 1);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        }, 1000L);
        TLog.d("%s runCompleted: %f / %f", "TuSdkMediaFileCuterImpl", this.a.benchmarkUs() / 1000000.0f, this.a.totalDurationUs() / 1000000.0f);
    }
    
    @Override
    protected boolean _init() {
        if (!this.c()) {
            TLog.w("%s init Encodec Environment failed.", "TuSdkMediaFileCuterImpl");
            return false;
        }
        return true;
    }
    
    private boolean c() {
        this.b.setOutputSize(this.mEncoder.getOutputSize());
        (this.o = new SelesSurfaceReceiver()).setTextureCoordinateBuilder(this.b);
        this.o.addTarget(this.mEncoder.getFilterBridge(), 0);
        this.mEncoder.setSurfaceRender(this.mSurfaceRender);
        this.mEncoder.setAudioRender(this.mAudioRender);
        this.mEncoder.setMediaSync(this.a);
        this.mEncoder.setListener(this.p, this.q);
        return this.mEncoder.prepare(null);
    }
    
    protected void initInGLThread() {
        if (this.o == null) {
            return;
        }
        this.o.initInGLThread();
        this.o.setSurfaceTextureListener((SurfaceTexture.OnFrameAvailableListener)new SurfaceTexture.OnFrameAvailableListener() {
            public void onFrameAvailable(final SurfaceTexture surfaceTexture) {
                TuSdkMediaFilesCuterImpl.this.mEncoder.requestVideoRender(TuSdkMediaFilesCuterImpl.this.a(TuSdkMediaFilesCuterImpl.this.i.outputTimeUs()));
            }
        });
        final Surface outputSurface = new Surface(this.o.requestSurfaceTexture());
        if (this.i != null) {
            this.i.setOutputSurface(outputSurface);
        }
        this.b();
    }
    
    @Override
    public TuSdkSize preferredOutputSize() {
        final List<AVAssetTrack> a = this.a(AVMediaType.AVMediaTypeVideo);
        if (a.size() == 0) {
            return null;
        }
        final TuSdkSize presentSize = a.get(0).presentSize();
        if (Math.min(presentSize.width, presentSize.height) >= 1080) {
            return TuSdkSize.create((int)(presentSize.width * 0.5f), (int)(presentSize.height * 0.5f));
        }
        return presentSize;
    }
    
    public void setOutputRatio(final float outputRatio, final boolean enableClip) {
        if (this.o != null) {
            this.o.setOutputRatio(outputRatio);
            this.o.setEnableClip(enableClip);
        }
    }
    
    public void setOutputSize(final TuSdkSize outputSize, final boolean enableClip) {
        if (this.o != null) {
            this.o.setOutputSize(outputSize);
            this.o.setEnableClip(enableClip);
        }
    }

    private List<AVAssetTrack> a(AVMediaType var1) {
        ArrayList var2 = new ArrayList();
        Iterator var3 = this.f.iterator();

        while(var3.hasNext()) {
            AVAsset var4 = (AVAsset)var3.next();
            List var5 = var4.tracksWithMediaType(var1);
            var2.addAll(var5);
        }

        return var2;
    }


    private void d() {
        ArrayList var1 = new ArrayList(1);
        ArrayList var2 = new ArrayList(1);
        Iterator var3 = this.f.iterator();

        while(var3.hasNext()) {
            AVAsset var4 = (AVAsset)var3.next();
            List var5 = var4.tracksWithMediaType(AVMediaType.AVMediaTypeVideo);
            List var6 = var4.tracksWithMediaType(AVMediaType.AVMediaTypeAudio);
            if (var5.size() > 0 && var6.size() > 0) {
                var1.addAll(var5);
                var2.addAll(var6);
            }
        }

        if (var1.size() == 0) {
            TLog.e("%s No video tracks are available in the data source.", new Object[]{this});
        } else {
            AVTimeRange var7 = null;
            if (this.e.startUs >= 0L && this.e.endUs > this.e.startUs) {
                var7 = AVTimeRange.AVTimeRangeMake(this.e.startUs, this.e.endUs - this.e.startUs);
            }

            this.h = new AVAssetTrackPipeMediaExtractor(var1);
            boolean var8 = this.h.setTimeRange(var7);
            if (!var8) {
                TuSdkMediaTimeSlice var9 = new TuSdkMediaTimeSlice(0L, 9223372036854775807L);
                var9.speed = this.e.speed;
                this.e = var9;
            }

            this.i = new AVAssetTrackCodecDecoder(this.h);
            this.i.addTarget(this.d);
            this.g = var2.size() > 0;
            if (this.g) {
                this.j = new AVAssetTrackPipeMediaExtractor(var2);
                if (var8) {
                    this.j.setTimeRange(var7);
                }

                this.k = new AVAssetTrackCodecDecoder(this.j);
                this.k.addTarget(this.c);
            }

        }
    }
    
    private void e() {
        this.mEncoder.signalVideoEndOfInputStream();
        if (this.n && this.a()) {
            this.c.a(new Runnable() {
                @Override
                public void run() {
                    try {
                        TuSdkMediaFilesCuterImpl.this.c.a();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }
    
    private void f() throws IOException {
        this.mEncoder.signalAudioEndOfInputStream(this.a(this.k.outputTimeUs()));
    }
    
    private long a(final long n) {
        return (long)(1.0f / this.e.speed * n);
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
            return TuSdkMediaFilesCuterImpl.this.h.durationTimeUs();
        }
        
        @Override
        public float calculateProgress() {
            float a = 0.0f;
            if (this.totalDurationUs() > 0L) {
                a = (TuSdkMediaFilesCuterImpl.this.i.outputTimeUs() / (float)TuSdkMediaFilesCuterImpl.this.i.durationTimeUs() + (TuSdkMediaFilesCuterImpl.this.a() ? (TuSdkMediaFilesCuterImpl.this.k.outputTimeUs() / (float)TuSdkMediaFilesCuterImpl.this.k.durationTimeUs()) : 1.0f)) / 2.0f;
            }
            return Math.min(Math.max(a, 0.0f), 1.0f);
        }
        
        @Override
        public boolean isEncodecCompleted() {
            return this.isVideoEncodeCompleted() && this.isAudioEncodeCompleted();
        }
        
        public boolean isAudioEncodeCompleted() {
            return this.e == null || !TuSdkMediaFilesCuterImpl.this.a() || this.e.isAudioEncodeCompleted();
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
            synchronized (TuSdkMediaFilesCuterImpl.this.l) {
                TuSdkMediaFilesCuterImpl.this.l.notify();
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
                TuSdkMediaFilesCuterImpl.this.m = this.mFrameIntervalUs;
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
                return TuSdkMediaFilesCuterImpl.this.i.isDecodeCompleted();
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
            if (TuSdkMediaFilesCuterImpl.this.k == null || TuSdkMediaFilesCuterImpl.this.mState != 0) {
                TLog.i("%s : The export session terminated unexpectedly, probably because the user forcibly stopped the session.", this);
                return;
            }
            if (TuSdkMediaFilesCuterImpl.this.k.renderOutputBuffers()) {
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
                TuSdkMediaFilesCuterImpl.this.f();
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
            if (TuSdkMediaFilesCuterImpl.this.k == null) {
                return;
            }
            this.c.reset();
            TuSdkMediaFilesCuterImpl.this.k.reset();
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
            private long d;
            private long e;
            
            public boolean queueInputBuffer(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) throws IOException {
                return this.c.queueInputBuffer(byteBuffer, bufferInfo);
            }
            
            public void changeFormat(final TuSdkAudioInfo tuSdkAudioInfo) {
                if (this.c == null) {
                    final TuSdkAudioInfo outputAudioInfo = TuSdkMediaFilesCuterImpl.this.getOutputAudioInfo();
                    (this.c = new TuSdkAudioResampleHardImpl(outputAudioInfo)).changeFormat(tuSdkAudioInfo);
                    this.c.setMediaSync(this);
                    (this.b = new TuSdkAudioPitchHardImpl(outputAudioInfo)).changeSpeed(TuSdkMediaFilesCuterImpl.this.e.speed);
                    this.b.setMediaSync(this);
                }
                else {
                    this.c.changeFormat(tuSdkAudioInfo);
                }
            }
            
            @Override
            public void syncAudioResampleOutputBuffer(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) throws IOException {
                final TuSdkAudioInfo outputAudioInfo = TuSdkMediaFilesCuterImpl.this.getOutputAudioInfo();
                if (bufferInfo.presentationTimeUs > 0L && this.d <= 0L && TuSdkMediaFilesCuterImpl.this.e != null && TuSdkMediaFilesCuterImpl.this.e.speed != 1.0f && outputAudioInfo != null) {
                    final long d = (long)(bufferInfo.presentationTimeUs / TuSdkMediaFilesCuterImpl.this.e.speed - 1024000000 / outputAudioInfo.sampleRate);
                    if (d > 0L) {
                        this.d = d;
                    }
                }
                this.b.queueInputBuffer(byteBuffer, bufferInfo);
            }
            
            @Override
            public void syncAudioPitchOutputBuffer(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) throws IOException {
                byteBuffer.position(bufferInfo.offset);
                byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
                this.e += this.d;
                if (TuSdkMediaFilesCuterImpl.this.n && bufferInfo.presentationTimeUs > TuSdkMediaFilesCuterImpl.this.a(TuSdkMediaFilesCuterImpl.this.i.outputTimeUs()) - TuSdkMediaFilesCuterImpl.this.m) {
                    return;
                }
                if (TuSdkMediaFilesCuterImpl.this.mEncoder.getAudioOperation() != null) {
                    while (!ThreadHelper.isInterrupted() && TuSdkMediaFilesCuterImpl.this.mEncoder.getAudioOperation().writeBuffer(byteBuffer, bufferInfo) == 0) {}
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
                TuSdkMediaFilesCuterImpl.this.mEncoder.requestVideoRender(TuSdkMediaFilesCuterImpl.this.a(TuSdkMediaFilesCuterImpl.this.i.outputTimeUs()));
            }
            try {
                synchronized (TuSdkMediaFilesCuterImpl.this.l) {
                    TuSdkMediaFilesCuterImpl.this.l.wait(500L);
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
            TuSdkMediaFilesCuterImpl.this.o.setInputSize(tuSdkVideoInfo2.codecSize);
            TuSdkMediaFilesCuterImpl.this.o.setPreCropRect(tuSdkVideoInfo2.codecCrop);
            TuSdkMediaFilesCuterImpl.this.o.setInputRotation(avAssetTrack.orientation());
        }
        
        private void a(final Runnable runnable) {
            this.b.runAsynchronouslyOnProcessingQueue(runnable);
        }
        
        private void b(final Runnable runnable) {
            this.b.runSynchronouslyOnProcessingQueue(runnable);
        }
        
        private void a() {
            if (TuSdkMediaFilesCuterImpl.this.i == null || TuSdkMediaFilesCuterImpl.this.mState != 0) {
                TLog.i("%s :The export session terminated unexpectedly, probably because the user forcibly stopped the session.", this);
                return;
            }
            if (TuSdkMediaFilesCuterImpl.this.i.renderOutputBuffers()) {
                this.a(new Runnable() {
                    @Override
                    public void run() {
                        VideoRender.this.a();
                    }
                });
            }
            else {
                TuSdkMediaFilesCuterImpl.this.e();
                TLog.i("%s : play done", this);
            }
        }
        
        private void b() {
            if (TuSdkMediaFilesCuterImpl.this.i == null) {
                return;
            }
            TuSdkMediaFilesCuterImpl.this.i.reset();
        }
        
        public void release() {
            this.b();
            this.b.quit();
        }
    }
}
