// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit;

import android.graphics.SurfaceTexture;
import android.media.MediaCodec;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.example.myvideoeditorapp.kore.api.extend.TuSdkAudioRender;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkFilterBridge;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkMediaDirectorPlayerListener;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkMediaPlayerListener;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkSurfaceDraw;
import com.example.myvideoeditorapp.kore.media.codec.decoder.TuSdkDecoderListener;
import com.example.myvideoeditorapp.kore.media.codec.decoder.TuSdkMediaFileDecoder;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaFileCuterTimeline;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaTimeline;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkMediaFileDirectorPlayerSync;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCropBuilderImpl;
import com.example.myvideoeditorapp.kore.seles.sources.SelesSurfaceReceiver;
import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;

import com.example.myvideoeditorapp.kore.media.codec.exception.TuSdkTaskExitException;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class TuSdkMediaFileDirectorPlayerImpl implements TuSdkMediaFileDirectorPlayer
{
    private int a;
    private final TuSdkMediaFileDirectorPlayerSync b;
    private TuSdkSurfaceDraw c;
    private TuSdkAudioRender d;
    private TuSdkMediaFileDecoder e;
    private SelesSurfaceReceiver f;
    private TuSdkFilterBridge g;
    private TuSdkMediaPlayerListener h;
    private TuSdkMediaDataSource i;
    private boolean j;
    private long k;
    private int l;
    private GLSurfaceView.Renderer m;
    private TuSdkDecoderListener n;
    private TuSdkDecoderListener o;
    
    public TuSdkMediaFileDirectorPlayerImpl() {
        this.a = -1;
        this.b = new TuSdkMediaFileDirectorPlayerSync();
        this.g = new TuSdkFilterBridge();
        this.k = -1L;
        this.l = 0;
        this.m = (GLSurfaceView.Renderer)new GLSurfaceView.Renderer() {
            public void onSurfaceCreated(final GL10 gl10, final EGLConfig eglConfig) {
                GLES20.glDisable(2929);
                TuSdkMediaFileDirectorPlayerImpl.this.initInGLThread();
            }
            
            public void onSurfaceChanged(final GL10 gl10, final int n, final int n2) {
                GLES20.glViewport(0, 0, n, n2);
            }
            
            public void onDrawFrame(final GL10 gl10) {
                GLES20.glClear(16640);
                TuSdkMediaFileDirectorPlayerImpl.this.newFrameReadyInGLThread();
            }
        };
        this.n = new TuSdkDecoderListener() {
            private long b = -1L;
            
            @Override
            public void onDecoderUpdated(final MediaCodec.BufferInfo bufferInfo) {
                if (TuSdkMediaFileDirectorPlayerImpl.this.b.getSeekToTimeUs() > -1L || this.b == bufferInfo.presentationTimeUs) {
                    return;
                }
                this.b = bufferInfo.presentationTimeUs;
                if (TuSdkMediaFileDirectorPlayerImpl.this.b.isVideoEos()) {
                    this.onDecoderCompleted(null);
                    return;
                }
                TuSdkMediaFileDirectorPlayerImpl.this.a(false);
            }
            
            @Override
            public void onDecoderCompleted(final Exception ex) {
                TuSdkMediaFileDirectorPlayerImpl.this.pause();
                TuSdkMediaFileDirectorPlayerImpl.this.b.syncVideoDecodeCompleted();
                if (ex != null) {
                    TuSdkMediaFileDirectorPlayerImpl.this.a((ex instanceof TuSdkTaskExitException) ? null : ex);
                    return;
                }
                TuSdkMediaFileDirectorPlayerImpl.this.a(true);
                TLog.d("%s VideoDecoderListener process buffer stream end", "TuSdkMediaFileDirectorPlayerImpl");
            }
        };
        this.o = new TuSdkDecoderListener() {
            @Override
            public void onDecoderUpdated(final MediaCodec.BufferInfo bufferInfo) {
            }
            
            @Override
            public void onDecoderCompleted(final Exception ex) {
                if (ex != null) {
                    if (ex instanceof TuSdkTaskExitException) {
                        TuSdkMediaFileDirectorPlayerImpl.this.a(null);
                        return;
                    }
                    TLog.e(ex, "%s AudioDecoderListener catch a exception, skip audio and ignore.", "TuSdkMediaFileDirectorPlayerImpl");
                }
                TuSdkMediaFileDirectorPlayerImpl.this.b.syncAudioDecodeCompleted();
                if (!TuSdkMediaFileDirectorPlayerImpl.this.b.isAudioDecodeCrashed()) {
                    TLog.d("%s AudioDecoderListener process buffer stream end", "TuSdkMediaFileDirectorPlayerImpl");
                }
            }
        };
    }
    
    public void setEffectFrameCalc(final TuSdkMediaFileDirectorPlayerSync.TuSdkEffectFrameCalc effectFrameCalc) {
        this.b.setEffectFrameCalc(effectFrameCalc);
    }
    
    @Override
    public void setMediaDataSource(final TuSdkMediaDataSource i) {
        if (i == null || !i.isValid()) {
            TLog.w("%s setMediaDataSource not exists: %s", "TuSdkMediaFileDirectorPlayerImpl", i);
            return;
        }
        this.i = i;
    }
    
    @Override
    public void setSurfaceDraw(final TuSdkSurfaceDraw tuSdkSurfaceDraw) {
        this.c = tuSdkSurfaceDraw;
        if (this.g != null) {
            this.g.setSurfaceDraw(tuSdkSurfaceDraw);
        }
    }
    
    @Override
    public void setAudioRender(final TuSdkAudioRender tuSdkAudioRender) {
        this.d = tuSdkAudioRender;
        if (this.e != null) {
            this.e.setAudioRender(tuSdkAudioRender);
        }
    }
    
    @Override
    public void setAudioMixerRender(final TuSdkAudioRender mixerRender) {
        this.b.setMixerRender(mixerRender);
    }
    
    public void setProgressOutputMode(final int n) {
        this.l = n;
        this.b.setProgressOutputMode(n);
    }
    
    public TuSdkMediaFileCuterTimeline getTimeLine() {
        return this.b.getTimeline();
    }
    
    public long calcInputTimeUs(final long n) {
        return this.b.calInputTimeUs(n);
    }
    
    @Override
    public void setListener(final TuSdkMediaPlayerListener h) {
        this.h = h;
    }
    
    @Override
    public TuSdkFilterBridge getFilterBridge() {
        return this.g;
    }
    
    @Override
    public GLSurfaceView.Renderer getExtenalRenderer() {
        return this.m;
    }
    
    @Override
    public void setCanvasColor(final float n, final float n2, final float n3, final float n4) {
        if (this.f == null) {
            return;
        }
        this.f.setCanvasColor(n, n2, n3, n4);
    }
    
    @Override
    public void setCanvasColor(final int canvasColor) {
        if (this.f == null) {
            return;
        }
        this.f.setCanvasColor(canvasColor);
    }
    
    @Override
    public boolean load(final boolean j) {
        if (this.a != -1) {
            TLog.w("%s repeated loading is not allowed.", "TuSdkMediaFileDirectorPlayerImpl");
            return false;
        }
        this.j = j;
        this.a = 0;
        return this.b();
    }
    
    @Override
    public void initInGLThread() {
        if (this.f == null || this.a != 0) {
            TLog.w("%s initInGLThread need after load, before release.", "TuSdkMediaFileDirectorPlayerImpl");
            return;
        }
        this.a = 1;
        this.a();
        this.f.initInGLThread();
    }
    
    @Override
    public void newFrameReadyInGLThread() {
        if (this.f == null || this.a != 1) {
            TLog.w("%s newFrameReadyInGLThread need after load, before release.", "TuSdkMediaFileDirectorPlayerImpl");
            return;
        }
        this.f.updateSurfaceTexImage(this.f.getSurfaceTexTimestampNs());
    }
    
    @Override
    public void release() {
        if (this.a == 2) {
            TLog.w("%s already released.", "TuSdkMediaFileDirectorPlayerImpl");
            return;
        }
        this.a = 2;
        this.a();
        this.b.release();
        if (this.f != null) {
            this.f.destroy();
            this.f = null;
        }
        if (this.g != null) {
            this.g.destroy();
            this.g = null;
        }
        if (this.e != null) {
            this.e.release();
            this.e = null;
        }
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.release();
        super.finalize();
    }
    
    private void a() {
        ThreadHelper.post(new Runnable() {
            @Override
            public void run() {
                if (TuSdkMediaFileDirectorPlayerImpl.this.h != null) {
                    TuSdkMediaFileDirectorPlayerImpl.this.h.onStateChanged(TuSdkMediaFileDirectorPlayerImpl.this.b.isPause() ? 1 : 0);
                }
            }
        });
    }
    
    private void a(final boolean b) {
        long outputTimeUs = this.outputTimeUs();
        final long durationUs = this.durationUs();
        this.decodeFrameTimeUs();
        this.inputDurationUs();
        if (durationUs < 1L) {
            return;
        }
        if (this.j) {
            this.j = false;
            this.pause();
            this.b.enableLoadFirstFramePause(this.j);
        }
        if (b) {
            outputTimeUs = durationUs;
        }
        long finalOutputTimeUs = outputTimeUs;
        ThreadHelper.post(new Runnable() {
            @Override
            public void run() {
                if (TuSdkMediaFileDirectorPlayerImpl.this.h == null) {
                    return;
                }
                if (TuSdkMediaFileDirectorPlayerImpl.this.h instanceof TuSdkMediaDirectorPlayerListener) {
                    ((TuSdkMediaDirectorPlayerListener)TuSdkMediaFileDirectorPlayerImpl.this.h).onProgress(finalOutputTimeUs, durationUs, TuSdkMediaFileDirectorPlayerImpl.this.i, TuSdkMediaFileDirectorPlayerImpl.this.b.getTimeline());
                }
                else {
                    TuSdkMediaFileDirectorPlayerImpl.this.h.onProgress(finalOutputTimeUs, TuSdkMediaFileDirectorPlayerImpl.this.i, durationUs);
                }
            }
        });
    }
    
    private void a(final Exception ex) {
        ThreadHelper.post(new Runnable() {
            @Override
            public void run() {
                TuSdkMediaFileDirectorPlayerImpl.this.release();
                if (TuSdkMediaFileDirectorPlayerImpl.this.h == null) {
                    return;
                }
                TuSdkMediaFileDirectorPlayerImpl.this.h.onCompleted(ex, TuSdkMediaFileDirectorPlayerImpl.this.i);
            }
        });
    }
    
    @Override
    public long durationUs() {
        return this.b.totalVideoDurationUs();
    }
    
    public long inputDurationUs() {
        return this.b.totalVideInputDurationUs();
    }
    
    @Override
    public long outputTimeUs() {
        return this.b.outputTimeUs();
    }
    
    @Override
    public void setEnableClip(final boolean enableClip) {
        if (this.f != null) {
            this.f.setEnableClip(enableClip);
        }
    }
    
    @Override
    public TuSdkSize setOutputRatio(final float outputRatio) {
        if (this.f != null) {
            return this.f.setOutputRatio(outputRatio);
        }
        return null;
    }
    
    @Override
    public void setOutputSize(final TuSdkSize outputSize) {
        if (this.f != null) {
            this.f.setOutputSize(outputSize);
        }
    }
    
    public long decodeFrameTimeUs() {
        return this.b.decodeFrameTimeUs();
    }
    
    @Override
    public boolean isPause() {
        return this.a != 1 || this.b.isPause();
    }
    
    @Override
    public void pause() {
        if (this.isPause()) {
            return;
        }
        this.b.setPause();
    }
    
    @Override
    public void resume() {
        if (this.a != 1 || !this.b.isPause()) {
            return;
        }
        if (this.k >= this.b.totalVideoDurationUs() && this.b.getTimeline().isFixTimeSlices()) {
            this.k = 0L;
        }
        if (this.k > -1L) {
            this.b.syncFlushAndSeekto(this.k);
            this.k = -1L;
        }
        else {
            this.b.syncNeedRestart();
        }
        this.a();
        this.b.setPlay();
    }
    
    @Override
    public void reset() throws IOException {
        this.b.setReset();
    }
    
    @Override
    public long seekToPercentage(float n) {
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
    public void seekTo(final long n) {
        this.a(n, 2);
    }
    
    private void a(final long n, final int n2) {
        if (this.a != 1) {
            return;
        }
        this.b.pauseSave();
        final long calInputTimeUs = this.b.calInputTimeUs(n);
        if (calInputTimeUs > -1L && this.e != null) {
            final long seekTo = this.e.seekTo(calInputTimeUs, n2);
            this.k = this.b.calOutputTimeUs(seekTo);
            this.b.syncSeektoTimeUs(seekTo);
        }
        this.b.resumeSave();
    }
    
    @Override
    public void preview(final TuSdkMediaTimeline timeline) throws IOException {
        this.b.setTimeline(timeline);
    }
    
    @Override
    public int setVolume(final float volume) {
        return this.b.setVolume(volume);
    }
    
    private boolean b() {
        (this.f = new SelesSurfaceReceiver()).setTextureCoordinateBuilder(new SelesVerticeCoordinateCropBuilderImpl(false));
        this.f.addTarget(this.g, 0);
        this.f.setSurfaceTextureListener((SurfaceTexture.OnFrameAvailableListener)new SurfaceTexture.OnFrameAvailableListener() {
            public void onFrameAvailable(final SurfaceTexture surfaceTexture) {
                if (TuSdkMediaFileDirectorPlayerImpl.this.h != null) {
                    TuSdkMediaFileDirectorPlayerImpl.this.h.onFrameAvailable();
                }
            }
        });
        this.b.setDirectorPlayerStateCallback(new TuSdkMediaFileDirectorPlayerSync.TuSdkDirectorPlayerStateCallback() {
            @Override
            public void onPauseWait() {
                TuSdkMediaFileDirectorPlayerImpl.this.a();
            }
        });
        this.b.enableLoadFirstFramePause(this.j);
        (this.e = new TuSdkMediaFileDecoder(true, true)).setMediaDataSource(this.i);
        this.e.setMediaSync(this.b);
        this.e.setSurfaceReceiver(this.f);
        this.e.setAudioRender(this.d);
        this.e.setListener(this.n, this.o);
        this.e.prepare();
        if (!this.e.isVideoStared()) {
            this.a(new Exception(String.format("%s VideoFileDecoder start failed", "TuSdkMediaFileDirectorPlayerImpl")));
            return false;
        }
        if (!this.e.isAudioStared()) {
            this.e.releaseAudioDecoder();
            this.b.setHaveAudio(false);
        }
        return true;
    }
}
