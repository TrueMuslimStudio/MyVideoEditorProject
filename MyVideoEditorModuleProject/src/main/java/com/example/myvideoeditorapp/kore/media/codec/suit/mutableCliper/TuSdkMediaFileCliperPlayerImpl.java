// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.mutableCliper;

import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.Surface;

import androidx.annotation.RequiresApi;

import com.example.myvideoeditorapp.kore.api.extend.TuSdkAudioRender;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkFilterBridge;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkMediaPlayerListener;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkSurfaceDraw;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioInfo;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioPitch;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioPitchHardImpl;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioResample;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioResampleHardImpl;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioTrack;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioTrackImpl;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVAssetTrackCodecDecoder;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVAssetTrackSampleBufferOutput;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVMediaSyncClock;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVMediaType;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCropBuilderImpl;
import com.example.myvideoeditorapp.kore.seles.sources.SelesSurfaceReceiver;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.RectHelper;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;


import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVAssetTrack;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVMediaProcessQueue;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVSampleBuffer;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioPitchSync;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkAudioResampleSync;
import com.example.myvideoeditorapp.kore.media.codec.video.TuSdkVideoInfo;
import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;
import com.google.firebase.firestore.util.Assert;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class TuSdkMediaFileCliperPlayerImpl implements TuSdkMediaFileCliperPlayer
{
    private TuSdkMediaPlayerStatus a;
    private AudioRender b;
    private VideoRender c;
    private long d;
    private SelesSurfaceReceiver e;
    private TuSdkFilterBridge f;
    private TuSdkMediaPlayerListener g;
    private boolean h;
    private boolean i;
    private TuSdkMediaCliperSource j;
    private AVAssetTrackCodecDecoder k;
    private TuSdkMediaCliperSource l;
    private AVAssetTrackCodecDecoder m;
    private AVMediaSyncClock n;
    private TuSdkAudioTrack o;
    private TuSdkAudioPitch p;
    private TuSdkAudioResample q;
    private TuSdkMediaCliperSource.TuSdkMediaCliperSourceDelegate r;
    private GLSurfaceView.Renderer s;
    
    public TuSdkMediaFileCliperPlayerImpl() {
        this.a = TuSdkMediaPlayerStatus.Unknown;
        this.b = new AudioRender();
        this.c = new VideoRender();
        this.d = -1L;
        this.f = new TuSdkFilterBridge();
        this.r = new TuSdkMediaCliperSource.TuSdkMediaCliperSourceDelegate() {
            @Override
            public void didAddSourceItem(final TuSdkMediaTrackItem tuSdkMediaTrackItem) {
                switch (tuSdkMediaTrackItem.track().mediaType().ordinal()) {
                    case 1: {
                        TuSdkMediaFileCliperPlayerImpl.this.h();
                        break;
                    }
                    case 2: {
                        TuSdkMediaFileCliperPlayerImpl.this.i();
                        break;
                    }
                }
            }
            
            @Override
            public void ddiRemoveSourceIem(final TuSdkMediaTrackItem tuSdkMediaTrackItem) {
                switch (tuSdkMediaTrackItem.track().mediaType().ordinal()) {
                    case 1: {
                        TuSdkMediaFileCliperPlayerImpl.this.h();
                        break;
                    }
                    case 2: {
                        TuSdkMediaFileCliperPlayerImpl.this.i();
                        break;
                    }
                }
            }
        };
        this.s = (GLSurfaceView.Renderer)new GLSurfaceView.Renderer() {
            public void onSurfaceCreated(final GL10 gl10, final EGLConfig eglConfig) {
                GLES20.glDisable(2929);
                TuSdkMediaFileCliperPlayerImpl.this.initInGLThread();
            }
            
            public void onSurfaceChanged(final GL10 gl10, final int n, final int n2) {
                GLES20.glViewport(0, 0, n, n2);
            }
            
            public void onDrawFrame(final GL10 gl10) {
                TuSdkMediaFileCliperPlayerImpl.this.newFrameReadyInGLThread();
            }
        };
        this.n = new AVMediaSyncClock();
        (this.j = new TuSdkMediaCliperSource()).setDelegate(this.r);
        (this.k = new AVAssetTrackCodecDecoder(this.j)).addTarget(this.c);
        (this.l = new TuSdkMediaCliperSource()).setDelegate(this.r);
        (this.m = new AVAssetTrackCodecDecoder(this.l)).addTarget(this.b);
    }
    
    @Override
    public final void setMediaDataSource(final TuSdkMediaDataSource tuSdkMediaDataSource) {
    }
    
    @Override
    public void appendTrackItem(final TuSdkMediaTrackItem tuSdkMediaTrackItem, final Callback<Boolean> callback) {
        if (tuSdkMediaTrackItem == null || tuSdkMediaTrackItem.track() == null) {
            if (callback != null) {
                callback.onHandled(false);
            }
            return;
        }
        this.c.a(new Runnable() {
            @Override
            public void run() {
                TuSdkMediaFileCliperPlayerImpl.this.d();
                switch (tuSdkMediaTrackItem.track().mediaType().ordinal()) {
                    case 1: {
                        TuSdkMediaFileCliperPlayerImpl.this.j.appendTrackItem(tuSdkMediaTrackItem);
                        break;
                    }
                    case 2: {
                        TuSdkMediaFileCliperPlayerImpl.this.l.appendTrackItem(tuSdkMediaTrackItem);
                        break;
                    }
                }
                if (callback != null) {
                    callback.onHandled(true);
                }
            }
        });
    }
    
    @Override
    public void insertTrackItem(final TuSdkMediaTrackItem tuSdkMediaTrackItem, final int n, final Callback<Boolean> callback) {
        if (tuSdkMediaTrackItem == null || tuSdkMediaTrackItem.track() == null) {
            if (callback != null) {
                callback.onHandled(false);
            }
            return;
        }
        this.c.a(new Runnable() {
            @Override
            public void run() {
                TuSdkMediaFileCliperPlayerImpl.this.d();
                switch (tuSdkMediaTrackItem.track().mediaType().ordinal()) {
                    case 1: {
                        TuSdkMediaFileCliperPlayerImpl.this.j.insertTrackItem(n, tuSdkMediaTrackItem);
                        break;
                    }
                    case 2: {
                        TuSdkMediaFileCliperPlayerImpl.this.l.insertTrackItem(n, tuSdkMediaTrackItem);
                        break;
                    }
                }
                if (callback != null) {
                    callback.onHandled(true);
                }
            }
        });
    }
    
    @Override
    public void removeTrackItem(final TuSdkMediaTrackItem tuSdkMediaTrackItem, final Callback<Boolean> callback) {
        if (tuSdkMediaTrackItem == null || tuSdkMediaTrackItem.track() == null) {
            if (callback != null) {
                callback.onHandled(false);
            }
            return;
        }
        this.c.a(new Runnable() {
            @Override
            public void run() {
                if (TuSdkMediaFileCliperPlayerImpl.this.j.items().size() == 1) {
                    TLog.i("The number of video tracks cannot be 0.", new Object[0]);
                    if (callback != null) {
                        callback.onHandled(false);
                    }
                    return;
                }
                TuSdkMediaFileCliperPlayerImpl.this.d();
                switch (tuSdkMediaTrackItem.track().mediaType().ordinal()) {
                    case 1: {
                        TuSdkMediaFileCliperPlayerImpl.this.j.removeTrackItem(tuSdkMediaTrackItem);
                        break;
                    }
                    case 2: {
                        TuSdkMediaFileCliperPlayerImpl.this.l.removeTrackItem(tuSdkMediaTrackItem);
                        break;
                    }
                }
                if (callback != null) {
                    callback.onHandled(true);
                }
            }
        });
    }
    
    @Override
    public void removeTrackItem(final int n, final AVMediaType avMediaType, final Callback<TuSdkMediaTrackItem> callback) {
        this.c.a(new Runnable() {
            @Override
            public void run() {
                if (TuSdkMediaFileCliperPlayerImpl.this.j.items().size() == 1) {
                    TLog.i("The number of video tracks cannot be 0.", new Object[0]);
                    if (callback != null) {
                        callback.onHandled(null);
                    }
                    return;
                }
                TuSdkMediaFileCliperPlayerImpl.this.d();
                TuSdkMediaTrackItem tuSdkMediaTrackItem = null;
                switch (avMediaType.ordinal()) {
                    case 1: {
                        tuSdkMediaTrackItem = TuSdkMediaFileCliperPlayerImpl.this.j.removeTrackItem(n);
                        break;
                    }
                    case 2: {
                        tuSdkMediaTrackItem = TuSdkMediaFileCliperPlayerImpl.this.l.removeTrackItem(n);
                        break;
                    }
                }
                TuSdkMediaFileCliperPlayerImpl.this.f();
                if (callback != null) {
                    callback.onHandled(tuSdkMediaTrackItem);
                }
            }
        });
    }
    
    @Override
    public List<TuSdkMediaTrackItem> trackItemsWithMediaType(final AVMediaType avMediaType) {
        switch (avMediaType.ordinal()) {
            case 1: {
                return this.j.items();
            }
            case 2: {
                return this.l.items();
            }
            default: {
                return null;
            }
        }
    }
    
    public void setEnableClip(final boolean enableClip) {
        if (this.e != null) {
            this.e.setEnableClip(enableClip);
        }
    }
    
    public void setOutputSize(final TuSdkSize outputSize) {
        if (this.e != null) {
            this.e.setOutputSize(outputSize);
        }
    }
    
    @Override
    public void setSurfaceDraw(final TuSdkSurfaceDraw surfaceDraw) {
        if (this.f != null) {
            this.f.setSurfaceDraw(surfaceDraw);
        }
    }
    
    @Override
    public void setAudioRender(final TuSdkAudioRender tuSdkAudioRender) {
    }
    
    @Override
    public void setListener(final TuSdkMediaPlayerListener g) {
        this.g = g;
    }
    
    @Override
    public TuSdkFilterBridge getFilterBridge() {
        return this.f;
    }
    
    @Override
    public GLSurfaceView.Renderer getExtenalRenderer() {
        return this.s;
    }
    
    @Override
    public boolean load(final boolean h) {
        if (this.a != TuSdkMediaPlayerStatus.Unknown) {
            TLog.w("%s repeated loading is not allowed.", "TuSdkMediaMutableFilePlayerImpl");
            return false;
        }
        this.h = h;
        return this.g();
    }
    
    @RequiresApi(api = 14)
    private boolean a() {
        while (!this.e.isInited()) {}
        final SurfaceTexture requestSurfaceTexture = this.e.requestSurfaceTexture();
        final Surface outputSurface = new Surface(requestSurfaceTexture);
        requestSurfaceTexture.setOnFrameAvailableListener((SurfaceTexture.OnFrameAvailableListener)new SurfaceTexture.OnFrameAvailableListener() {
            public void onFrameAvailable(final SurfaceTexture surfaceTexture) {
                TuSdkMediaFileCliperPlayerImpl.this.g.onFrameAvailable();
            }
        });
        this.k.setOutputSurface(outputSurface);
        return true;
    }
    
    private boolean b() {
        if (this.i) {
            return false;
        }
        this.c.a(new Runnable() {
            @Override
            public void run() {
                TuSdkMediaFileCliperPlayerImpl.this.c.a();
            }
        });
        return true;
    }
    
    @Override
    public void initInGLThread() {
        if (this.e == null) {
            TLog.w("%s initInGLThread need after load, before release.", "TuSdkMediaMutableFilePlayerImpl");
            return;
        }
        this.e.initInGLThread();
        this.c.a(new Runnable() {
            @RequiresApi(api = 14)
            @Override
            public void run() {
                TuSdkMediaFileCliperPlayerImpl.this.a();
            }
        });
    }
    
    @Override
    public void newFrameReadyInGLThread() {
        if (this.e == null) {
            TLog.w("%s newFrameReadyInGLThread need after load, before release." + this.e, "TuSdkMediaMutableFilePlayerImpl");
            return;
        }
        this.e.updateSurfaceTexImage(this.e.getSurfaceTexTimestampNs());
    }
    
    @Override
    public void release() {
        if (this.a == TuSdkMediaPlayerStatus.Unknown) {
            TLog.w("%s already released.", "TuSdkMediaMutableFilePlayerImpl");
            return;
        }
        this.c.a(new Runnable() {
            @Override
            public void run() {
                if (TuSdkMediaFileCliperPlayerImpl.this.e != null) {
                    TuSdkMediaFileCliperPlayerImpl.this.e.destroy();
                    TuSdkMediaFileCliperPlayerImpl.this.e = null;
                }
                if (TuSdkMediaFileCliperPlayerImpl.this.f != null) {
                    TuSdkMediaFileCliperPlayerImpl.this.f.destroy();
                    TuSdkMediaFileCliperPlayerImpl.this.f = null;
                }
                TuSdkMediaFileCliperPlayerImpl.this.c.release();
            }
        });
        this.b.b(new Runnable() {
            @Override
            public void run() {
                TuSdkMediaFileCliperPlayerImpl.this.b.release();
            }
        });
        this.a = TuSdkMediaPlayerStatus.Unknown;
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.release();
        super.finalize();
    }
    
    private void a(final long n) {
        ThreadHelper.post(new Runnable() {
            @Override
            public void run() {
                if (TuSdkMediaFileCliperPlayerImpl.this.d != -1L && Math.abs(n - TuSdkMediaFileCliperPlayerImpl.this.d) > 500000L) {
                    return;
                }
                TuSdkMediaFileCliperPlayerImpl.this.d = -1L;
                TuSdkMediaFileCliperPlayerImpl.this.g.onProgress(TuSdkMediaFileCliperPlayerImpl.this.k.outputTimeUs(), null, TuSdkMediaFileCliperPlayerImpl.this.k.durationTimeUs());
            }
        });
    }
    
    private void c() {
        this.d();
        this.f();
    }
    
    private void a(final TuSdkMediaPlayerStatus a) {
        if (this.a == a) {
            return;
        }
        this.a = a;
        if (this.g == null) {
            return;
        }
        ThreadHelper.post(new Runnable() {
            @Override
            public void run() {
                TuSdkMediaFileCliperPlayerImpl.this.g.onStateChanged(a.ordinal());
            }
        });
    }
    
    @Override
    public long durationUs() {
        if (this.k == null) {
            return 0L;
        }
        return this.k.durationTimeUs();
    }
    
    @Override
    public long elapsedUs() {
        if (this.k == null) {
            return 0L;
        }
        return this.k.outputTimeUs();
    }
    
    @Override
    public boolean isSupportPrecise() {
        return false;
    }
    
    @Override
    public boolean isPause() {
        return this.a != TuSdkMediaPlayerStatus.Playing;
    }
    
    @Override
    public void pause() {
        this.c.a(new Runnable() {
            @Override
            public void run() {
                TuSdkMediaFileCliperPlayerImpl.this.d();
            }
        });
    }
    
    private void d() {
        if (this.a != TuSdkMediaPlayerStatus.Playing) {
            return;
        }
        this.a(TuSdkMediaPlayerStatus.ReadyToPlay);
        this.n.stop();
        this.b.a();
    }
    
    @Override
    public void resume() {
        this.e();
    }
    
    private void e() {
        if (this.k == null) {
            return;
        }
        if ((this.a == TuSdkMediaPlayerStatus.Playing || this.d > 0L) && Math.abs(this.d - this.durationUs()) > 50000L) {
            return;
        }
        this.a(TuSdkMediaPlayerStatus.Playing);
        this.c.a(new Runnable() {
            @Override
            public void run() {
                TuSdkMediaFileCliperPlayerImpl.this.n.start();
                if (TuSdkMediaFileCliperPlayerImpl.this.k.renderOutputBuffer()) {
                    if (TuSdkMediaFileCliperPlayerImpl.this.b != null) {
                        TuSdkMediaFileCliperPlayerImpl.this.b.a(new Runnable() {
                            @Override
                            public void run() {
                                TuSdkMediaFileCliperPlayerImpl.this.b.render();
                            }
                        });
                    }
                    TuSdkMediaFileCliperPlayerImpl.this.c.render();
                }
            }
        });
    }
    
    @Override
    public void reset() {
        this.f();
    }
    
    private void f() {
        if (this.k == null || this.a == TuSdkMediaPlayerStatus.Unknown) {
            return;
        }
        this.a(TuSdkMediaPlayerStatus.ReadyToPlay);
        this.d = -1L;
        this.b.a(new Runnable() {
            @Override
            public void run() {
                TuSdkMediaFileCliperPlayerImpl.this.b.reset();
            }
        });
        this.c.reset();
        this.i = false;
        this.a(this.j.currentItem());
        this.b();
    }
    
    @Override
    public void setSpeed(final float f) {
        this.c.b(new Runnable() {
            @Override
            public void run() {
                if (TuSdkMediaFileCliperPlayerImpl.this.speed() == f) {
                    return;
                }
                TuSdkMediaFileCliperPlayerImpl.this.n.setSpeed(f);
                if (TuSdkMediaFileCliperPlayerImpl.this.b != null) {
                    TuSdkMediaFileCliperPlayerImpl.this.b.a(f);
                }
            }
        });
    }
    
    @Override
    public float speed() {
        return this.n.getSpeed();
    }
    
    @Override
    public void setReverse(final boolean b) {
        TLog.e("%s \uff1a Sorry, reverse mode is not supported", this);
    }
    
    @Override
    public boolean isReverse() {
        return false;
    }
    
    public boolean supportSeek() {
        return this.j != null && this.j.supportSeek();
    }
    
    @Override
    public long seekToPercentage(float n) {
        if (!this.supportSeek()) {
            TLog.e("The video video does not support seek, may be too low frame rate.", new Object[0]);
            return -1L;
        }
        if (n < 0.0f) {
            n = 0.0f;
        }
        else if (n > 1.0f) {
            n = 1.0f;
        }
        final long n2 = (long)(n * this.durationUs());
        this.seekTo(n2);
        return n2;
    }
    
    @Override
    public void seekTo(final long d) {
        if (!this.supportSeek()) {
            TLog.e("The video video does not support seek, may be too low frame rate.", new Object[0]);
            return;
        }
        if (this.k == null) {
            return;
        }
        if (this.a == TuSdkMediaPlayerStatus.Unknown) {
            return;
        }
        this.d = d;
        this.c.c(new Runnable() {
            @Override
            public void run() {
                TuSdkMediaFileCliperPlayerImpl.this.d();
                TuSdkMediaFileCliperPlayerImpl.this.k.seekTo(d, true);
                TuSdkMediaFileCliperPlayerImpl.this.b.b(new Runnable() {
                    @Override
                    public void run() {
                        TuSdkMediaFileCliperPlayerImpl.this.b.b();
                        TuSdkMediaFileCliperPlayerImpl.this.m.seekTo(d, true);
                        TuSdkMediaFileCliperPlayerImpl.this.d = -1L;
                    }
                });
            }
        });
    }
    
    private boolean g() {
        (this.e = new SelesSurfaceReceiver()).setTextureCoordinateBuilder(new SelesVerticeCoordinateCropBuilderImpl(false));
        this.e.addTarget(this.f, 0);
        return true;
    }
    
    private void h() {
        if (this.j.currentItem() == null) {
            return;
        }
        while (!this.e.isInited()) {}
        this.e.setOutputSize(this.preferredOutputSize());
        this.a(this.j.currentItem());
        this.b();
    }
    
    private void a(final TuSdkMediaTrackItem tuSdkMediaTrackItem) {
        if (tuSdkMediaTrackItem != null && tuSdkMediaTrackItem.outputRatio() > 0.0f) {
            this.e.setCropRect(tuSdkMediaTrackItem.cropRectF());
            final RectF computerCenterRectF = RectHelper.computerCenterRectF(this.e.outputFrameSize(), tuSdkMediaTrackItem.outputRatio());
            final float n = computerCenterRectF.left / computerCenterRectF.width() / 2.0f;
            final float n2 = computerCenterRectF.top / computerCenterRectF.height() / 2.0f;
            this.e.setCanvasRect(new RectF(n, n2, 1.0f - n, 1.0f - n2));
        }
        else {
            this.e.setCropRect(new RectF(0.0f, 0.0f, 1.0f, 1.0f));
            this.e.setCanvasRect(new RectF(0.0f, 0.0f, 1.0f, 1.0f));
        }
    }
    
    public TuSdkSize preferredOutputSize() {
        final List<TuSdkMediaTrackItem> items = this.j.items();
        if (items.size() == 0) {
            return null;
        }
        return items.get(0).track().presentSize();
    }
    
    private void i() {
        if (this.l.inputTrack() == null) {
            return;
        }
        final TuSdkAudioInfo tuSdkAudioInfo = new TuSdkAudioInfo(this.l.inputTrack().mediaFormat());
        if (this.o == null) {
            (this.o = new TuSdkAudioTrackImpl(tuSdkAudioInfo)).play();
        }
        if (this.q == null) {
            (this.q = new TuSdkAudioResampleHardImpl(tuSdkAudioInfo)).setMediaSync(this.b);
        }
        if (this.p == null) {
            (this.p = new TuSdkAudioPitchHardImpl(tuSdkAudioInfo)).changeSpeed(this.n.getSpeed());
            this.p.setMediaSync(this.b);
        }
    }
    
    private class AudioRender extends InternalRender implements AVAssetTrackSampleBufferOutput.AVAssetTrackSampleBufferInput, TuSdkAudioPitchSync, TuSdkAudioResampleSync
    {
        private void a() {
            if (TuSdkMediaFileCliperPlayerImpl.this.o == null) {
                return;
            }
            TuSdkMediaFileCliperPlayerImpl.this.o.pause();
        }
        
        private void b() {
            if (TuSdkMediaFileCliperPlayerImpl.this.o != null) {
                TuSdkMediaFileCliperPlayerImpl.this.o.flush();
            }
            if (TuSdkMediaFileCliperPlayerImpl.this.q != null) {
                TuSdkMediaFileCliperPlayerImpl.this.q.reset();
            }
        }
        
        @Override
        public void render() {
            if (TuSdkMediaFileCliperPlayerImpl.this.m == null || TuSdkMediaFileCliperPlayerImpl.this.a != TuSdkMediaPlayerStatus.Playing) {
                TLog.i(" audio play paused Status \uff1a %s", TuSdkMediaFileCliperPlayerImpl.this.a);
                return;
            }
            if (TuSdkMediaFileCliperPlayerImpl.this.o != null) {
                TuSdkMediaFileCliperPlayerImpl.this.o.play();
            }
            if (TuSdkMediaFileCliperPlayerImpl.this.m.renderOutputBuffers()) {
                this.a(new Runnable() {
                    @Override
                    public void run() {
                        AudioRender.this.render();
                    }
                });
            }
        }
        
        @Override
        public void newFrameReady(final AVSampleBuffer avSampleBuffer) throws IOException {
            if (avSampleBuffer.isDecodeOnly()) {
                return;
            }
            if (TuSdkMediaFileCliperPlayerImpl.this.a != TuSdkMediaPlayerStatus.Playing) {
                return;
            }
            TuSdkMediaFileCliperPlayerImpl.this.n.lock(avSampleBuffer.renderTimeUs(), 0L);
            avSampleBuffer.info().presentationTimeUs = avSampleBuffer.renderTimeUs();
            TuSdkMediaFileCliperPlayerImpl.this.q.queueInputBuffer(avSampleBuffer.buffer(), avSampleBuffer.info());
        }
        
        @Override
        public void outputFormatChaned(final MediaFormat mediaFormat, final AVAssetTrack avAssetTrack) {
            final TuSdkAudioInfo tuSdkAudioInfo = new TuSdkAudioInfo(mediaFormat);
            TuSdkMediaFileCliperPlayerImpl.this.q.reset();
            TuSdkMediaFileCliperPlayerImpl.this.q.changeFormat(tuSdkAudioInfo);
        }
        
        private void a(final float n) {
            this.b(new Runnable() {
                @Override
                public void run() {
                    if (TuSdkMediaFileCliperPlayerImpl.this.o != null) {
                        TuSdkMediaFileCliperPlayerImpl.this.o.flush();
                    }
                    if (TuSdkMediaFileCliperPlayerImpl.this.p != null) {
                        TuSdkMediaFileCliperPlayerImpl.this.p.reset();
                        TuSdkMediaFileCliperPlayerImpl.this.p.changeSpeed(n);
                    }
                }
            });
        }
        
        @Override
        public void syncAudioResampleOutputBuffer(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) throws IOException {
            TuSdkMediaFileCliperPlayerImpl.this.p.queueInputBuffer(byteBuffer, bufferInfo);
        }
        
        @Override
        public void syncAudioPitchOutputBuffer(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) {
            byteBuffer.position(bufferInfo.offset);
            byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
            TuSdkMediaFileCliperPlayerImpl.this.o.write(byteBuffer);
        }
        
        @Override
        public void reset() {
            if (TuSdkMediaFileCliperPlayerImpl.this.m == null || TuSdkMediaFileCliperPlayerImpl.this.a == TuSdkMediaPlayerStatus.Unknown) {
                return;
            }
            TuSdkMediaFileCliperPlayerImpl.this.m.reset();
        }
    }
    
    private abstract class InternalRender
    {
        AVMediaProcessQueue b;
        
        private InternalRender() {
            this.b = new AVMediaProcessQueue();
        }
        
        void a(final Runnable runnable) {
            this.b.runAsynchronouslyOnProcessingQueue(runnable);
        }
        
        void b(final Runnable runnable) {
            this.b.runSynchronouslyOnProcessingQueue(runnable);
        }
        
        void c(final Runnable runnable) {
            this.b.clearAll();
            this.b.runAsynchronouslyOnProcessingQueue(runnable);
        }
        
        public abstract void render();
        
        public abstract void reset();
        
        public void release() {
            this.b.quit();
            this.reset();
        }
    }
    
    public enum TuSdkMediaPlayerStatus
    {
        Unknown, 
        Failed, 
        ReadyToPlay, 
        Playing, 
        Completed;
    }
    
    private class VideoRender extends InternalRender implements AVAssetTrackSampleBufferOutput.AVAssetTrackSampleBufferInput
    {
        @Override
        public void newFrameReady(final AVSampleBuffer avSampleBuffer) {
            if (avSampleBuffer != null && avSampleBuffer.isDecodeOnly()) {
                return;
            }
            TuSdkMediaFileCliperPlayerImpl.this.i = true;
            TuSdkMediaFileCliperPlayerImpl.this.n.lock(avSampleBuffer.renderTimeUs(), 0L);
            TuSdkMediaFileCliperPlayerImpl.this.a(avSampleBuffer.renderTimeUs());
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
            TuSdkMediaFileCliperPlayerImpl.this.e.setInputSize(tuSdkVideoInfo2.codecSize);
            TuSdkMediaFileCliperPlayerImpl.this.e.setPreCropRect(tuSdkVideoInfo2.codecCrop);
            TuSdkMediaFileCliperPlayerImpl.this.e.setInputRotation(TuSdkMediaFileCliperPlayerImpl.this.j.currentItem().outputOrientation());
            TuSdkMediaFileCliperPlayerImpl.this.a(TuSdkMediaFileCliperPlayerImpl.this.j.currentItem());
        }
        
        @Override
        public void render() {
            if (TuSdkMediaFileCliperPlayerImpl.this.k == null || TuSdkMediaFileCliperPlayerImpl.this.a != TuSdkMediaPlayerStatus.Playing) {
                TLog.i("%s : play paused", this);
                return;
            }
            if (TuSdkMediaFileCliperPlayerImpl.this.k.renderOutputBuffers()) {
                this.a(new Runnable() {
                    @Override
                    public void run() {
                        VideoRender.this.render();
                    }
                });
            }
            else {
                TLog.i("%s : play done", this);
                TuSdkMediaFileCliperPlayerImpl.this.f();
            }
        }
        
        private void a() {
            if (TuSdkMediaFileCliperPlayerImpl.this.k == null || TuSdkMediaFileCliperPlayerImpl.this.a == TuSdkMediaPlayerStatus.Playing) {
                return;
            }
            if (TuSdkMediaFileCliperPlayerImpl.this.i) {
                TuSdkMediaFileCliperPlayerImpl.this.a(TuSdkMediaPlayerStatus.ReadyToPlay);
                if (!TuSdkMediaFileCliperPlayerImpl.this.h) {
                    TuSdkMediaFileCliperPlayerImpl.this.resume();
                }
                return;
            }
            if (TuSdkMediaFileCliperPlayerImpl.this.k.renderOutputBuffer()) {
                this.a(new Runnable() {
                    @Override
                    public void run() {
                        VideoRender.this.a();
                    }
                });
            }
            else {
                TuSdkMediaFileCliperPlayerImpl.this.c();
            }
        }
        
        @Override
        public void reset() {
            TuSdkMediaFileCliperPlayerImpl.this.n.stop();
            TuSdkMediaFileCliperPlayerImpl.this.k.reset();
        }
    }
}
