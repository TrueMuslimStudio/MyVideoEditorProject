// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.imageToVideo;

import android.media.MediaCodec;

import com.example.myvideoeditorapp.kore.api.extend.TuSdkMediaProgress;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkEncoderListener;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkMediaFileSuitEncoderBase;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkVideoSurfaceEncoderListener;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkVideoSurfaceEncoderListenerImpl;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkCodecCapabilities;
import com.example.myvideoeditorapp.kore.secret.SdkValid;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCorpBuilder;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCropBuilderImpl;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;

import java.io.IOException;
import java.util.LinkedList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public final class TuSdkMediaVideoComposer extends TuSdkMediaFileSuitEncoderBase
{
    private final SelesVerticeCoordinateCorpBuilder a;
    private TuSdkMediaVideoComposeSync b;
    private TuSdkMediaVideoComposProcesser c;
    private LinkedList<TuSdkComposeItem> d;
    private TuSdkMediaVideoComposeConductor e;
    private boolean f;
    private TuSdkVideoSurfaceEncoderListener g;
    private TuSdkEncoderListener h;
    
    public TuSdkMediaVideoComposer() {
        this.a = new SelesVerticeCoordinateCropBuilderImpl(false);
        this.b = new TuSdkMediaVideoComposeSync();
        this.g = new TuSdkVideoSurfaceEncoderListenerImpl() {
            @Override
            public void onSurfaceCreated(final GL10 gl10, final EGLConfig eglConfig) {
                TuSdkMediaVideoComposer.this.c.initInGLThread();
                TuSdkMediaVideoComposer.this.e.run();
                TuSdkMediaVideoComposer.this.mEncoder.requestVideoKeyFrame();
            }
            
            @Override
            public void onEncoderDrawFrame(final long n, final boolean b) {
                TuSdkMediaVideoComposer.this.b.syncVideoEncodecDrawFrame(n, b, TuSdkMediaVideoComposer.this.c, TuSdkMediaVideoComposer.this.mEncoder.getVideoEncoder());
            }
            
            @Override
            public void onEncoderUpdated(final MediaCodec.BufferInfo bufferInfo) {
                TuSdkMediaVideoComposer.this.a(false);
            }
            
            @Override
            public void onEncoderCompleted(final Exception ex) {
                if (ex == null) {
                    TLog.d("%s encodec Video updatedToEOS", "TuSdkMediaVideoComposer");
                    TuSdkMediaVideoComposer.this.a(false);
                }
                else {
                    TLog.e(ex, "%s VideoEncoderListener thread catch exception, The thread will exit.", "TuSdkMediaVideoComposer");
                }
            }
        };
        this.h = new TuSdkEncoderListener() {
            @Override
            public void onEncoderUpdated(final MediaCodec.BufferInfo bufferInfo) {
                if (TLog.LOG_AUDIO_ENCODEC_INFO) {
                    TuSdkCodecCapabilities.logBufferInfo("AudioEncoderListener updated", bufferInfo);
                }
            }
            
            @Override
            public void onEncoderCompleted(final Exception ex) {
                if (ex == null) {
                    TLog.d("%s encodec Audio updatedToEOS", "TuSdkMediaVideoComposer");
                    TuSdkMediaVideoComposer.this.a(false);
                }
                else {
                    TLog.e(ex, "%s AudioEncoderListener thread catch exception, The thread will exit.", "TuSdkMediaVideoComposer");
                }
                TuSdkMediaVideoComposer.this.a(ex);
            }
        };
    }
    
    public void setInputComposList(final LinkedList<TuSdkComposeItem> d) {
        if (d == null || d.size() == 0) {
            TLog.w("%s set input compose item list is invalid", "TuSdkMediaVideoComposer");
            return;
        }
        this.d = d;
    }
    
    @Override
    public final boolean run(final TuSdkMediaProgress tuSdkMediaProgress) throws IOException {
        if (!SdkValid.shared.videoImageComposeSupport()) {
            TLog.e("You are not allowed to use image video compositing , please see https://tutucloud.com", new Object[0]);
            return false;
        }
        if (this.d == null || this.d.size() == 0) {
            TLog.w("%s run need a input file path.", "TuSdkMediaVideoComposer");
            return false;
        }
        return super.run(tuSdkMediaProgress);
    }
    
    @Override
    protected boolean _init() {
        if (!this.a()) {
            TLog.w("%s init Encodec Environment failed.", "TuSdkMediaVideoComposer");
            return false;
        }
        return true;
    }
    
    private boolean a() {
        (this.e = new TuSdkMediaVideoComposeConductor()).setItemList(this.d);
        this.e.setIsAllKeyFrame(this.f);
        this.a.setOutputSize(this.mEncoder.getOutputSize());
        this.a.setEnableClip(false);
        (this.c = new TuSdkMediaVideoComposProcesser()).setTextureCoordinateBuilder(this.a);
        this.c.setCanvasColor(0.0f, 0.0f, 0.0f, 1.0f);
        this.c.addTarget(this.mEncoder.getFilterBridge());
        this.c.setInputRotation(ImageOrientation.Up);
        this.e.setComposProcesser(this.c);
        this.mEncoder.setSurfaceRender(this.mSurfaceRender);
        this.mEncoder.setAudioRender(this.mAudioRender);
        this.mEncoder.setMediaSync(this.b);
        this.mEncoder.setListener(this.g, this.h);
        this.e.setMediaFileEncoder(this.mEncoder);
        return this.mEncoder.prepare(null);
    }
    
    private void a(final boolean b) {
        ThreadHelper.post(new Runnable() {
            final /* synthetic */ float a = b ? 1.0f : TuSdkMediaVideoComposer.this.e.calculateProgress();
            
            @Override
            public void run() {
                if (TuSdkMediaVideoComposer.this.mProgress == null) {
                    return;
                }
                TuSdkMediaVideoComposer.this.mProgress.onProgress(this.a, null, 1, 1);
            }
        });
    }
    
    private void a(final Exception ex) {
        if (ex == null) {
            this.mEncoder.cleanTemp();
        }
        this.a(true);
        ThreadHelper.post(new Runnable() {
            @Override
            public void run() {
                TuSdkMediaVideoComposer.this.stop();
                TuSdkMediaVideoComposer.this.mEncoder.cleanTemp();
                if (TuSdkMediaVideoComposer.this.mProgress == null) {
                    return;
                }
                try {
                    TuSdkMediaVideoComposer.this.mProgress.onCompleted(ex, TuSdkMediaVideoComposer.this.mEncoder.getOutputDataSource(), 1);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        });
    }
    
    @Override
    public void stop() {
        super.stop();
    }
    
    public void setIsAllKeyFrame(final boolean f) {
        this.f = f;
    }
}
