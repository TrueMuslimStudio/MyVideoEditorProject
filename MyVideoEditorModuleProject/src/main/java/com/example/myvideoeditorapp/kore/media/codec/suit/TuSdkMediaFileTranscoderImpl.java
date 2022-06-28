// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.media.MediaCodec;

import com.example.myvideoeditorapp.kore.api.extend.TuSdkAudioRender;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkMediaProgress;
import com.example.myvideoeditorapp.kore.media.codec.decoder.TuSdkDecoderListener;
import com.example.myvideoeditorapp.kore.media.codec.decoder.TuSdkMediaFileDecoder;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkEncoderListener;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkMediaFileSuitEncoderBase;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkVideoSurfaceEncoderListener;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkVideoSurfaceEncoderListenerImpl;
import com.example.myvideoeditorapp.kore.media.codec.exception.TuSdkTaskExitException;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkCodecCapabilities;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkMediaFileTrascoderSync;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCropBuilderImpl;
import com.example.myvideoeditorapp.kore.seles.sources.SelesSurfaceReceiver;
import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

@TargetApi(18)
public class TuSdkMediaFileTranscoderImpl extends TuSdkMediaFileSuitEncoderBase implements TuSdkMediaFileTranscoder
{
    private final TuSdkMediaFileTrascoderSync a;
    private final List<TuSdkMediaDataSource> b;
    private SelesSurfaceReceiver c;
    private TuSdkMediaDataSource d;
    private TuSdkMediaFileDecoder e;
    private TuSdkVideoSurfaceEncoderListener f;
    private TuSdkDecoderListener g;
    private TuSdkDecoderListener h;
    private TuSdkEncoderListener i;
    
    public TuSdkMediaFileTranscoderImpl() {
        this.a = new TuSdkMediaFileTrascoderSync();
        this.b = new ArrayList<TuSdkMediaDataSource>(5);
        this.f = new TuSdkVideoSurfaceEncoderListenerImpl() {
            @Override
            public void onSurfaceCreated(final GL10 gl10, final EGLConfig eglConfig) {
                if (TuSdkMediaFileTranscoderImpl.this.c == null) {
                    return;
                }
                TuSdkMediaFileTranscoderImpl.this.c.initInGLThread();
            }
            
            @Override
            public void onEncoderDrawFrame(final long n, final boolean b) {
                TuSdkMediaFileTranscoderImpl.this.a.syncVideoEncodecDrawFrame(n, b, TuSdkMediaFileTranscoderImpl.this.c, TuSdkMediaFileTranscoderImpl.this.mEncoder.getVideoEncoder());
            }
            
            @Override
            public void onEncoderUpdated(final MediaCodec.BufferInfo bufferInfo) {
                TuSdkMediaFileTranscoderImpl.this.a(false);
            }
            
            @Override
            public void onEncoderCompleted(final Exception ex) {
                if (ex == null) {
                    TLog.d("%s encodec Video updatedToEOS", "TuSdkMediaFileTranscoderImpl");
                    TuSdkMediaFileTranscoderImpl.this.a(true);
                }
                else {
                    TLog.e(ex, "%s VideoEncoderListener thread catch exception, The thread will exit.", "TuSdkMediaFileTranscoderImpl");
                }
                TuSdkMediaFileTranscoderImpl.this.a(ex);
            }
        };
        this.g = new TuSdkDecoderListener() {
            @Override
            public void onDecoderUpdated(final MediaCodec.BufferInfo bufferInfo) {
            }
            
            @Override
            public void onDecoderCompleted(final Exception ex) throws IOException {
                if (ex != null) {
                    TuSdkMediaFileTranscoderImpl.this.a(ex);
                    return;
                }
                TLog.d("%s VideoDecoderListenerprocess buffer stream end", "TuSdkMediaFileTranscoderImpl");
                TuSdkMediaFileTranscoderImpl.this.h();
                TuSdkMediaFileTranscoderImpl.this.c();
            }
        };
        this.h = new TuSdkDecoderListener() {
            @Override
            public void onDecoderUpdated(final MediaCodec.BufferInfo bufferInfo) {
            }
            
            @Override
            public void onDecoderCompleted(final Exception ex) throws IOException {
                if (ex != null && ex instanceof TuSdkTaskExitException) {
                    TuSdkMediaFileTranscoderImpl.this.a(ex);
                    return;
                }
                if (ex != null) {
                    TLog.e(ex, "%s AudioDecoderListener catch a exception, skip audio and ignore.", "TuSdkMediaFileTranscoderImpl");
                }
                if (!TuSdkMediaFileTranscoderImpl.this.a.isAudioDecodeCrashed()) {
                    TLog.d("%s AudioDecoderListener process buffer stream end", "TuSdkMediaFileTranscoderImpl");
                }
                TuSdkMediaFileTranscoderImpl.this.e();
            }
        };
        this.i = new TuSdkEncoderListener() {
            @Override
            public void onEncoderUpdated(final MediaCodec.BufferInfo bufferInfo) {
                TuSdkCodecCapabilities.logBufferInfo("AudioEncoderListener updated", bufferInfo);
            }
            
            @Override
            public void onEncoderCompleted(final Exception ex) {
                if (ex == null) {
                    TLog.d("%s encodec Audio updatedToEOS", "TuSdkMediaFileTranscoderImpl");
                    TuSdkMediaFileTranscoderImpl.this.a(true);
                }
                else {
                    TLog.e(ex, "%s AudioEncoderListener thread catch exception, The thread will exit.", "TuSdkMediaFileTranscoderImpl");
                }
                TuSdkMediaFileTranscoderImpl.this.a(ex);
            }
        };
    }
    
    @Override
    public void addInputDataSource(final TuSdkMediaDataSource tuSdkMediaDataSource) {
        if (this.mState != -1) {
            TLog.w("%s addInputDataSource need before run: %s", "TuSdkMediaFileTranscoderImpl", tuSdkMediaDataSource);
            return;
        }
        if (tuSdkMediaDataSource == null || !tuSdkMediaDataSource.isValid()) {
            TLog.w("%s addInputDataSource not exists: %s", "TuSdkMediaFileTranscoderImpl", tuSdkMediaDataSource);
            return;
        }
        this.b.add(tuSdkMediaDataSource);
    }
    
    @Override
    public void addInputDataSouces(final List<TuSdkMediaDataSource> list) {
        if (list == null || list.size() < 1) {
            TLog.w("%s addInputDataSouces need least 1 item.", "TuSdkMediaFileTranscoderImpl");
            return;
        }
        final Iterator<TuSdkMediaDataSource> iterator = list.iterator();
        while (iterator.hasNext()) {
            this.addInputDataSource(iterator.next());
        }
    }
    
    @Override
    public void setAudioRender(final TuSdkAudioRender tuSdkAudioRender) {
        super.setAudioRender(tuSdkAudioRender);
        if (this.e != null) {
            this.e.setAudioRender(tuSdkAudioRender);
        }
    }
    
    @Override
    public boolean run(final TuSdkMediaProgress tuSdkMediaProgress) throws IOException {
        if (this.b.size() < 1) {
            TLog.w("%s run need a input file path.", "TuSdkMediaFileTranscoderImpl");
            return false;
        }
        return super.run(tuSdkMediaProgress);
    }
    
    @Override
    public void stop() {
        if (this.mState == 1) {
            TLog.w("%s already stoped.", "TuSdkMediaFileTranscoderImpl");
            return;
        }
        this.mState = 1;
        this.d();
        this.f();
        if (this.c != null) {
            this.c.destroy();
            this.c = null;
        }
        this.mEncoder.release();
        this.a.release();
    }

    private void a(boolean var1) {
        final float var2 = var1 ? 1.0F : this.a.calculateProgress();
        final int var3 = this.a.lastIndex();
        ThreadHelper.post(new Runnable() {
            public void run() {
                if (TuSdkMediaFileTranscoderImpl.this.mProgress != null) {
                    TuSdkMediaFileTranscoderImpl.this.mProgress.onProgress(var2, TuSdkMediaFileTranscoderImpl.this.d, var3, TuSdkMediaFileTranscoderImpl.this.a.total());
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
        this.a.setBenchmarkEnd();
        ThreadHelper.post(new Runnable() {
            @Override
            public void run() {
                TuSdkMediaFileTranscoderImpl.this.stop();
                if (TuSdkMediaFileTranscoderImpl.this.mProgress == null) {
                    return;
                }
                try {
                    TuSdkMediaFileTranscoderImpl.this.mProgress.onCompleted(ex, TuSdkMediaFileTranscoderImpl.this.mEncoder.getOutputDataSource(), TuSdkMediaFileTranscoderImpl.this.a.total());
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        });
        TLog.d("%s runCompleted: %f / %f", "TuSdkMediaFileTranscoderImpl", this.a.benchmarkUs() / 1000000.0f, this.a.totalDurationUs() / 1000000.0f);
    }
    
    @Override
    protected boolean _init() throws IOException {
        this.a.setTotal(this.b.size());
        if (!this.a()) {
            TLog.w("%s init Encodec Environment failed.", "TuSdkMediaFileTranscoderImpl");
            return false;
        }
        this.b();
        return true;
    }
    
    private boolean a() {
        final SelesVerticeCoordinateCropBuilderImpl textureCoordinateBuilder = new SelesVerticeCoordinateCropBuilderImpl(false);
        textureCoordinateBuilder.setOutputSize(this.mEncoder.getOutputSize());
        (this.c = new SelesSurfaceReceiver()).setTextureCoordinateBuilder(textureCoordinateBuilder);
        this.c.addTarget(this.mEncoder.getFilterBridge(), 0);
        this.c.setSurfaceTextureListener((SurfaceTexture.OnFrameAvailableListener)new SurfaceTexture.OnFrameAvailableListener() {
            public void onFrameAvailable(final SurfaceTexture surfaceTexture) {
                TuSdkMediaFileTranscoderImpl.this.mEncoder.requestVideoRender(TuSdkMediaFileTranscoderImpl.this.a.lastVideoDecodecTimestampNs());
            }
        });
        this.a.addAudioEncodecOperation(this.mEncoder.getAudioOperation());
        this.mEncoder.setSurfaceRender(this.mSurfaceRender);
        this.mEncoder.setAudioRender(this.mAudioRender);
        this.mEncoder.setMediaSync(this.a);
        this.mEncoder.setListener(this.f, this.i);
        return this.mEncoder.prepare(null);
    }
    
    private void b() throws IOException {
        if (this.mState != 0 || !this.a.syncDecodecNext()) {
            return;
        }
        this.mEncoder.requestVideoKeyFrame();
        this.d = this.b.get(this.a.lastIndex());
        (this.e = new TuSdkMediaFileDecoder(true, this.mEncoder.hasAudioEncoder())).setMediaDataSource(this.d);
        this.e.setMediaSync(this.a);
        this.e.setSurfaceReceiver(this.c);
        this.e.setAudioRender(this.mAudioRender);
        this.e.setListener(this.g, this.h);
        this.e.prepare();
        if (!this.e.isVideoStared()) {
            this.c();
            return;
        }
        if (this.mEncoder.hasAudioEncoder() && !this.e.isAudioStared()) {
            this.e();
        }
    }
    
    private void c() throws IOException {
        if (this.a.isVideoDecodeCompleted()) {
            return;
        }
        if (this.a.isLast()) {
            this.mEncoder.signalVideoEndOfInputStream();
        }
        this.d();
        this.a.syncVideoDecodeCompleted();
        this.b();
    }
    
    private void d() {
        if (this.e == null) {
            return;
        }
        this.e.releaseVideoDecoder();
    }
    
    private void e() throws IOException {
        if (this.a.isAudioDecodeCompleted()) {
            return;
        }
        if (!this.a.isAudioDecodeCrashed()) {
            this.g();
        }
        this.f();
        this.a.syncAudioDecodeCompleted();
        this.b();
    }
    
    private void f() {
        if (this.e == null) {
            return;
        }
        this.e.releaseAudioDecoder();
    }
    
    private void g() throws IOException {
        if (this.a.isLast()) {
            this.mEncoder.signalAudioEndOfInputStream(this.a.totalDurationUs());
        }
    }
    
    private void h() throws IOException {
        if (!this.a.isAudioDecodeCrashed()) {
            return;
        }
        this.mEncoder.autoFillAudioMuteData(this.a.lastVideoEndTimeUs(), this.a.totalDurationUs(), this.a.isLast());
    }
}
