// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit;

import android.media.MediaCodec;
import android.opengl.EGLContext;

import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkEncoderListener;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkMediaFileSuitEncoderBase;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkVideoSurfaceEncoderListener;
import com.example.myvideoeditorapp.kore.media.codec.encoder.TuSdkVideoSurfaceEncoderListenerImpl;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkCodecCapabilities;
import com.example.myvideoeditorapp.kore.media.codec.sync.TuSdkMediaRecorderSync;
import com.example.myvideoeditorapp.kore.seles.egl.SelesVirtualDisplayPatch;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;

import java.io.IOException;
import java.nio.ByteBuffer;

public class TuSdkMediaFileRecorderImpl extends TuSdkMediaFileSuitEncoderBase implements TuSdkMediaFileRecorder
{
    private final TuSdkMediaRecorderSync a;
    private EGLContext b;
    private TuSdkMediaFileRecorderProgress c;
    private TuSdkVideoSurfaceEncoderListener d;
    private TuSdkEncoderListener e;
    
    public TuSdkMediaFileRecorderImpl() {
        this.a = new TuSdkMediaRecorderSync();
        this.d = new TuSdkVideoSurfaceEncoderListenerImpl() {
            @Override
            public void onEncoderDrawFrame(final long n, final boolean b) {
                TuSdkMediaFileRecorderImpl.this.a.syncVideoEncodecDrawFrame(n, b, TuSdkMediaFileRecorderImpl.this.mEncoder.getVideoEncoder());
            }
            
            @Override
            public void onEncoderUpdated(final MediaCodec.BufferInfo bufferInfo) {
                TuSdkMediaFileRecorderImpl.this.a();
            }
            
            @Override
            public void onEncoderCompleted(final Exception ex) {
                if (ex == null) {
                    TLog.d("%s encodec Video updatedToEOS", "TuSdkMediaFileRecorderImpl");
                    TuSdkMediaFileRecorderImpl.this.a();
                }
                else {
                    TLog.e(ex, "%s VideoEncoderListener thread catch exception, The thread will exit.", "TuSdkMediaFileRecorderImpl");
                }
                TuSdkMediaFileRecorderImpl.this.a(ex);
            }
        };
        this.e = new TuSdkEncoderListener() {
            @Override
            public void onEncoderUpdated(final MediaCodec.BufferInfo bufferInfo) {
                if (TLog.LOG_VIDEO_ENCODEC_INFO) {
                    TuSdkCodecCapabilities.logBufferInfo("AudioEncoderListener updated", bufferInfo);
                }
            }
            
            @Override
            public void onEncoderCompleted(final Exception ex) {
                if (ex == null) {
                    TLog.d("%s encodec Audio updatedToEOS", "TuSdkMediaFileRecorderImpl");
                }
                else {
                    TLog.e(ex, "%s AudioEncoderListener thread catch exception, The thread will exit.", "TuSdkMediaFileRecorderImpl");
                }
                TuSdkMediaFileRecorderImpl.this.a(ex);
            }
        };
    }
    
    @Override
    public void release() {
        this.stop();
        this.a.release();
    }
    
    @Override
    public void setRecordProgress(final TuSdkMediaFileRecorderProgress c) {
        this.c = c;
    }
    
    private void a() {
        ThreadHelper.post(new Runnable() {
            @Override
            public void run() {
                if (TuSdkMediaFileRecorderImpl.this.c == null) {
                    return;
                }
                TuSdkMediaFileRecorderImpl.this.c.onProgress(TuSdkMediaFileRecorderImpl.this.a.getVideoEncodecTimeUs(), TuSdkMediaFileRecorderImpl.this.mEncoder.getOutputDataSource());
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
        TLog.d("%s runCompleted: %f", "TuSdkMediaFileRecorderImpl", this.a.getVideoEncodecTimeUs() / 1000000.0f);
        this.stop();
        ThreadHelper.post(new Runnable() {
            @Override
            public void run() {
                if (TuSdkMediaFileRecorderImpl.this.c == null) {
                    return;
                }
                try {
                    TuSdkMediaFileRecorderImpl.this.c.onCompleted(ex, TuSdkMediaFileRecorderImpl.this.mEncoder.getOutputDataSource(), TuSdkMediaFileRecorderImpl.this.a.endOfTimeline());
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        });
    }
    
    @Override
    public void changeSpeed(final float n) {
        this.a.changeSpeed(n);
    }
    
    @Override
    public boolean startRecord(final EGLContext b) throws IOException {
        this.b = b;
        return this.run(this.mProgress);
    }
    
    @Override
    protected boolean _init() {
        if (this.mState == 1) {
            TLog.w("%s init Encodec Environment has released.", "TuSdkMediaFileRecorderImpl");
            return false;
        }
        if (!this.b()) {
            TLog.w("%s init Encodec Environment failed.", "TuSdkMediaFileRecorderImpl");
            return false;
        }
        this.mEncoder.getVideoEncoder().getVirtualDisplay().setSyncRender(SelesVirtualDisplayPatch.isNeedVirtualDisplayPatch());
        return true;
    }
    
    private boolean b() {
        this.mEncoder.setSurfaceRender(this.mSurfaceRender);
        this.mEncoder.setAudioRender(this.mAudioRender);
        this.mEncoder.setMediaSync(this.a);
        this.mEncoder.setListener(this.d, this.e);
        final boolean prepare = this.mEncoder.prepare(this.b, true);
        if (prepare) {
            this.a.setAudioOperation(this.mEncoder.getAudioOperation());
        }
        return prepare;
    }
    
    @Override
    public void stopRecord() {
        if (this.mState == 1) {
            return;
        }
        this.pauseRecord();
        ThreadHelper.runThread(new Runnable() {
            @Override
            public void run() {
                TuSdkMediaFileRecorderImpl.this.mEncoder.signalVideoEndOfInputStream();
                try {
                    TuSdkMediaFileRecorderImpl.this.mEncoder.autoFillAudioMuteData(TuSdkMediaFileRecorderImpl.this.a.getAudioEncodecTimeUs(), Math.max(TuSdkMediaFileRecorderImpl.this.a.getVideoEncodecTimeUs(), TuSdkMediaFileRecorderImpl.this.a.getAudioEncodecTimeUs()), true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    @Override
    public void pauseRecord() {
        if (this.mState == 1) {
            return;
        }
        this.mEncoder.setSurfacePause(true);
        this.a.pauseRecord();
    }
    
    @Override
    public void resumeRecord() {
        if (this.mState == 1) {
            return;
        }
        this.a.resumeRecord();
        this.mEncoder.setSurfacePause(false);
    }
    
    @Override
    public void newFrameReadyInGLThread(final long n) {
        if (this.mState == 1) {
            return;
        }
        this.mEncoder.requestVideoRender(n);
    }
    
    @Override
    public void newFrameReadyWithAudio(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) throws IOException {
        if (this.mState == 1) {
            return;
        }
        this.a.syncAudioEncodecFrame(byteBuffer, bufferInfo);
    }
}
