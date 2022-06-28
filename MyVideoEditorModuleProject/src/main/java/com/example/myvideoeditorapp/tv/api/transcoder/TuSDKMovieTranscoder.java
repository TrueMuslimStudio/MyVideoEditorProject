// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.api.transcoder;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.media.MediaCodec;
import android.opengl.EGL14;
import android.opengl.GLES20;
import android.view.Surface;

import com.example.myvideoeditorapp.kore.TuSdk;
import com.example.myvideoeditorapp.kore.secret.SdkValid;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.seles.SelesGLProgram;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCorpBuilder;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCropBuilderImpl;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutput;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.StringHelper;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;
import com.example.myvideoeditorapp.tv.api.video.preproc.filter.TuSDKMediaRecoder;
import com.example.myvideoeditorapp.tv.core.common.TuSDKMediaDataSource;
import com.example.myvideoeditorapp.tv.core.decoder.TuSDKMediaDecoder;
import com.example.myvideoeditorapp.tv.core.decoder.TuSDKMovieSurfaceDecoder;
import com.example.myvideoeditorapp.tv.core.decoder.TuSDKVideoInfo;
import com.example.myvideoeditorapp.tv.core.delegate.TuSDKVideoSaveDelegate;
import com.example.myvideoeditorapp.tv.core.delegate.TuSDKVideoSurfaceDecodeDelegate;
import com.example.myvideoeditorapp.tv.core.encoder.TuSDKVideoDataEncoderDelegate;
import com.example.myvideoeditorapp.tv.core.encoder.video.TuSDKHardVideoDataEncoder;
import com.example.myvideoeditorapp.tv.core.encoder.video.TuSDKVideoEncoderSetting;
import com.example.myvideoeditorapp.tv.core.gl.EGLConfigAttrs;
import com.example.myvideoeditorapp.tv.core.gl.EGLContextAttrs;
import com.example.myvideoeditorapp.tv.core.gl.EglCore;
import com.example.myvideoeditorapp.tv.core.seles.video.SelesSyncSurfaceTextureEncoder;
import com.example.myvideoeditorapp.tv.core.video.TuSDKVideoResult;
import com.example.myvideoeditorapp.tv.video.editor.TuSdkTimeRange;

import java.io.File;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.Queue;

@TargetApi(16)
public class TuSDKMovieTranscoder extends SelesOutput
{
    private static final float[] a;
    private static final float[] b;
    protected ImageOrientation mOutputRotation;
    protected ImageOrientation mInputRotation;
    private boolean c;
    private FloatBuffer d;
    private FloatBuffer e;
    private SelesGLProgram f;
    private int g;
    private int h;
    private int i;
    private EglCore j;
    private SurfaceTexture k;
    private Surface l;
    private int m;
    private final Queue<Runnable> n;
    private boolean o;
    private TuSDKMediaDataSource p;
    private TuSDKMovieSurfaceDecoder q;
    private TuSdkTimeRange r;
    private TuSDKVideoInfo s;
    private SelesSyncSurfaceTextureEncoder t;
    private TuSDKVideoEncoderSetting u;
    private TuSDKMediaRecoder v;
    private File w;
    private TuSDKVideoSaveDelegate x;
    private SelesVerticeCoordinateCorpBuilder y;
    private TuSDKVideoSurfaceDecodeDelegate z;
    private TuSDKMediaRecoder.TuSDKMediaRecoderDelegate A;
    
    public TuSDKMovieTranscoder(final TuSDKMediaDataSource videoDataSource) {
        this.m = -1;
        this.y = (SelesVerticeCoordinateCorpBuilder)new SelesVerticeCoordinateCropBuilderImpl(false);
        this.z = new TuSDKVideoSurfaceDecodeDelegate() {
            @Override
            public void onDecoderError(final TuSDKMediaDecoder.TuSDKMediaDecoderError tuSDKMediaDecoderError) {
                if (TuSDKMovieTranscoder.this.x != null) {
                    TuSDKMovieTranscoder.this.x.onSaveResult(null);
                }
            }
            
            @Override
            public void onVideoInfoReady(final TuSDKVideoInfo tuSDKVideoInfo) {
                TuSDKMovieTranscoder.this.s = tuSDKVideoInfo;
                TuSDKMovieTranscoder.this.mInputRotation = tuSDKVideoInfo.videoOrientation;
                TuSDKMovieTranscoder.this.mInputTextureSize = TuSdkSize.create(tuSDKVideoInfo.width, tuSDKVideoInfo.height);
                TuSDKMovieTranscoder.this.y.setPreCropRect(tuSDKVideoInfo.codecCrop);
            }
            
            @Override
            public void onProgressChanged(final long n, final float n2) {
                if (TuSDKMovieTranscoder.this.x == null) {
                    return;
                }
                ThreadHelper.post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        if (TuSDKMovieTranscoder.this.getTimeRange() != null && TuSDKMovieTranscoder.this.getTimeRange().isValid()) {
                            TuSDKMovieTranscoder.this.x.onProgressChaned(Math.min(1.0f, n / (float)TuSDKMovieTranscoder.this.getTimeRange().getEndTimeUS()));
                        }
                        else {
                            TuSDKMovieTranscoder.this.x.onProgressChaned(Math.min(1.0f, n2));
                        }
                    }
                });
            }
            
            @Override
            public void onVideoDecoderNewFrameAvailable(final int n, final MediaCodec.BufferInfo bufferInfo) {
                TuSDKMovieTranscoder.this.runPendingOnDrawTasks();
                if (TuSDKMovieTranscoder.this.getTimeRange() != null && TuSDKMovieTranscoder.this.getTimeRange().isValid()) {
                    if (bufferInfo.presentationTimeUs < TuSDKMovieTranscoder.this.getTimeRange().getStartTimeUS()) {
                        return;
                    }
                    if (bufferInfo.presentationTimeUs >= TuSDKMovieTranscoder.this.getTimeRange().getEndTimeUS()) {
                        TuSDKMovieTranscoder.this.q.stop();
                        this.onDecoderComplete();
                        return;
                    }
                }
                TuSDKMovieTranscoder.this.k.updateTexImage();
                TuSDKMovieTranscoder.this.a(TuSDKMovieTranscoder.this.m);
                TuSDKMovieTranscoder.this.updateTargetsForVideoCameraUsingCacheTexture(TuSDKMovieTranscoder.this.q.getComputePresentationTimeUs());
            }
            
            @Override
            public void onDecoderComplete() {
                TuSDKMovieTranscoder.this.stopRecording();
            }
        };
        this.A = new TuSDKMediaRecoder.TuSDKMediaRecoderDelegate() {
            @Override
            public void onMediaRecoderProgressChanged(final float n) {
            }
            
            @Override
            public void onMediaRecoderStateChanged(final TuSDKMediaRecoder.State state) {
                if (TuSDKMovieTranscoder.this.x != null && state == TuSDKMediaRecoder.State.RecordCompleted) {
                    final TuSDKVideoResult tuSDKVideoResult = new TuSDKVideoResult();
                    tuSDKVideoResult.videoPath = TuSDKMovieTranscoder.this.v.getOutputFilePath();
                    tuSDKVideoResult.videoInfo = TuSDKMovieTranscoder.this.s;
                    TuSDKMovieTranscoder.this.x.onSaveResult(tuSDKVideoResult);
                }
            }
        };
        this.setVideoDataSource(videoDataSource);
        this.m = this.d();
        this.k = new SurfaceTexture(this.m);
        this.l = new Surface(this.k);
        this.c = true;
        this.n = new LinkedList<Runnable>();
        this.mOutputRotation = ImageOrientation.Up;
        this.mInputRotation = ImageOrientation.Up;
    }
    
    public TuSDKMovieTranscoder setSaveDelegate(final TuSDKVideoSaveDelegate x) {
        this.x = x;
        return this;
    }
    
    public TuSDKMovieTranscoder setVideoDataSource(final TuSDKMediaDataSource p) {
        if (this.isProcessing()) {
            TLog.d("Please set 'moviePath' before processing", new Object[0]);
            return this;
        }
        this.p = p;
        return this;
    }
    
    public TuSDKMovieTranscoder setOutputFile(final File w) {
        this.w = w;
        return this;
    }
    
    public File getOutputFile() {
        if (this.w == null) {
            this.w = new File(TuSdk.getAppTempPath(), String.format("lsq_%s.mp4", StringHelper.timeStampString()));
        }
        return this.w;
    }
    
    protected final TuSdkSize getOutputSize() {
        if (this.u.videoSize != null) {
            return this.u.videoSize;
        }
        if (this.mInputTextureSize == null || this.getVideoInfo() == null) {
            return TuSdkSize.create(0);
        }
        TuSdkSize videoSize = this.mInputTextureSize;
        if (this.mInputTextureSize.minSide() >= 1080) {
            videoSize = TuSdkSize.create((int)(this.mInputTextureSize.width * 0.5), (int)(this.mInputTextureSize.height * 0.5));
        }
        if (this.mInputRotation.isTransposed()) {
            videoSize = TuSdkSize.create(videoSize.height, videoSize.width);
        }
        return this.u.videoSize = videoSize;
    }
    
    protected TuSDKVideoEncoderSetting getVideoEncoderSetting() {
        if (this.u == null) {
            this.u = TuSDKVideoEncoderSetting.getDefaultRecordSetting();
            this.u.videoSize = this.getOutputSize();
        }
        return this.u;
    }
    
    public final TuSDKMovieTranscoder setVideoEncoderSetting(final TuSDKVideoEncoderSetting u) {
        this.u = u;
        if (!SdkValid.shared.videoEditorResolutionEnabled() && u != null && !u.videoSize.equals((Object)TuSDKVideoEncoderSetting.getDefaultRecordSetting().videoSize)) {
            TLog.e("You are not allowed to change video editor resolution, please see http://tusdk.com", new Object[0]);
            u.videoSize = TuSDKVideoEncoderSetting.getDefaultRecordSetting().videoSize;
        }
        if (!SdkValid.shared.videoEditorBitrateEnabled() && u != null && !u.videoQuality.equals(TuSDKVideoEncoderSetting.getDefaultRecordSetting().videoQuality)) {
            TLog.e("You are not allowed to change video editor bitrate, please see http://tusdk.com", new Object[0]);
            u.videoQuality = TuSDKVideoEncoderSetting.getDefaultRecordSetting().videoQuality;
        }
        return this;
    }
    
    private void a() {
        if (this.f != null) {
            return;
        }
        this.runOnDraw(new Runnable() {
            @Override
            public void run() {
                TuSDKMovieTranscoder.this.j = new EglCore();
                if (!TuSDKMovieTranscoder.this.j.createGLESWithSurface(new EGLConfigAttrs(), new EGLContextAttrs(), new SurfaceTexture(1))) {
                    TLog.d("createGLESWithSurface failed", new Object[0]);
                    return;
                }
                SelesContext.createEGLContext(SelesContext.currentEGLContext());
                TuSDKMovieTranscoder.this.f = SelesContext.program("attribute vec4 position;attribute vec4 inputTextureCoordinate;varying vec2 textureCoordinate;void main(){    gl_Position = position;    textureCoordinate = inputTextureCoordinate.xy;}", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 textureCoordinate;\nuniform samplerExternalOES inputImageTexture;\nvoid main() {\n    gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}\n");
                if (!TuSDKMovieTranscoder.this.f.isInitialized()) {
                    TuSDKMovieTranscoder.this.f.addAttribute("position");
                    TuSDKMovieTranscoder.this.f.addAttribute("inputTextureCoordinate");
                    if (!TuSDKMovieTranscoder.this.f.link()) {
                        TLog.i("Program link log: %s", new Object[] { TuSDKMovieTranscoder.this.f.getProgramLog() });
                        TLog.i("Fragment shader compile log: %s", new Object[] { TuSDKMovieTranscoder.this.f.getFragmentShaderLog() });
                        TLog.i("Vertex link log: %s", new Object[] { TuSDKMovieTranscoder.this.f.getVertexShaderLog() });
                        TuSDKMovieTranscoder.this.f = null;
                        TLog.e("Filter shader link failed: %s", new Object[] { this.getClass() });
                        return;
                    }
                }
                TuSDKMovieTranscoder.this.g = TuSDKMovieTranscoder.this.f.attributeIndex("position");
                TuSDKMovieTranscoder.this.h = TuSDKMovieTranscoder.this.f.attributeIndex("inputTextureCoordinate");
                TuSDKMovieTranscoder.this.i = TuSDKMovieTranscoder.this.f.uniformIndex("inputImageTexture");
                SelesContext.setActiveShaderProgram(TuSDKMovieTranscoder.this.f);
                GLES20.glEnableVertexAttribArray(TuSDKMovieTranscoder.this.g);
                GLES20.glEnableVertexAttribArray(TuSDKMovieTranscoder.this.h);
            }
        });
    }
    
    private FloatBuffer b() {
        if (this.d == null) {
            this.d = SelesFilter.buildBuffer(TuSDKMovieTranscoder.a);
        }
        return this.d;
    }
    
    private FloatBuffer c() {
        if (this.e == null) {
            this.e = SelesFilter.buildBuffer(TuSDKMovieTranscoder.b);
        }
        return this.e;
    }
    
    public void addTarget(final SelesContext.SelesInput selesInput, final int n) {
        super.addTarget(selesInput, n);
        if (selesInput != null) {
            selesInput.setInputRotation(this.mOutputRotation, n);
        }
    }
    
    private int d() {
        final int[] array = { 0 };
        GLES20.glGenTextures(1, array, 0);
        GLES20.glBindTexture(36197, array[0]);
        GLES20.glTexParameteri(36197, 10241, 9729);
        GLES20.glTexParameteri(36197, 10240, 9729);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        return array[0];
    }
    
    private SelesFramebuffer e() {
        final SelesFramebuffer fetchFramebuffer = SelesContext.sharedFramebufferCache().fetchFramebuffer(SelesFramebuffer.SelesFramebufferMode.FBO_AND_TEXTURE, this.getOutputSize());
        fetchFramebuffer.disableReferenceCounting();
        return fetchFramebuffer;
    }
    
    private void a(final int n) {
        this.y.calculate(this.mInputTextureSize, this.mInputRotation, this.b(), this.c());
        SelesContext.setActiveShaderProgram(this.f);
        this.f();
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(16384);
        GLES20.glActiveTexture(33986);
        GLES20.glBindTexture(36197, n);
        GLES20.glUniform1i(this.i, 2);
        GLES20.glVertexAttribPointer(this.g, 2, 5126, false, 0, (Buffer)this.b());
        GLES20.glVertexAttribPointer(this.h, 2, 5126, false, 0, (Buffer)this.c());
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glBindTexture(36197, 0);
        GLES20.glBindFramebuffer(36160, 0);
    }
    
    private void f() {
        if (this.c || this.mOutputFramebuffer == null) {
            this.mOutputFramebuffer = this.e();
            this.c = false;
        }
        this.mOutputFramebuffer.activateFramebuffer();
    }
    
    protected void updateTargetsForVideoCameraUsingCacheTexture(final long n) {
        for (int i = 0; i < this.mTargets.size(); ++i) {
            final SelesContext.SelesInput selesInput = this.mTargets.get(i);
            if (selesInput.isEnabled()) {
                final int intValue = this.mTargetTextureIndices.get(i);
                selesInput.setInputRotation(this.mOutputRotation, intValue);
                if (selesInput != this.getTargetToIgnoreForUpdates()) {
                    selesInput.setInputSize(this.getOutputSize(), intValue);
                    selesInput.setCurrentlyReceivingMonochromeInput(selesInput.wantsMonochromeInput());
                }
                selesInput.setInputFramebuffer(this.mOutputFramebuffer, intValue);
            }
        }
        for (int j = 0; j < this.mTargets.size(); ++j) {
            final SelesContext.SelesInput selesInput2 = this.mTargets.get(j);
            if (selesInput2.isEnabled()) {
                if (selesInput2 != this.getTargetToIgnoreForUpdates()) {
                    selesInput2.newFrameReady(n, (int)this.mTargetTextureIndices.get(j));
                }
            }
        }
    }
    
    private void g() {
        if (this.k() == null) {
            return;
        }
        this.addTarget((SelesContext.SelesInput)this.t);
    }
    
    public TuSdkTimeRange getTimeRange() {
        return this.r;
    }
    
    public void setTimeRange(final TuSdkTimeRange r) {
        this.r = r;
    }
    
    private TuSDKMovieSurfaceDecoder h() {
        if (this.q == null) {
            (this.q = new TuSDKMovieSurfaceDecoder(this.p)).setLooping(false);
            this.q.setVideoDelegate(this.z);
        }
        return this.q;
    }

    private void i(){
        SelesSyncSurfaceTextureEncoder var1 = new SelesSyncSurfaceTextureEncoder(this.j) {
            @Override
            public void setDelegate(TuSDKVideoDataEncoderDelegate p0) {

            }

            protected void prepareEncoder(TuSDKVideoEncoderSetting var1) {
                this.mVideoEncoder = new TuSDKHardVideoDataEncoder();
                this.mVideoEncoder.initCodec(var1);
            }
        };
        TuSDKVideoEncoderSetting var2 = this.getVideoEncoderSetting();
        if (var2.videoSize == null) {
            var2.videoSize = this.getOutputSize();
        }

        if (var2.videoQuality == null) {
            var2.videoQuality = TuSDKVideoEncoderSetting.VideoQuality.RECORD_MEDIUM2.setBitrate(this.getVideoInfo().bitrate).setFps(Math.max(20, this.getVideoInfo().fps));
        }

        var2.bitrateMode = this.getVideoInfo().getBestBitrateMode();
        if (this.getVideoInfo().profile >= 66) {
            var2.videoQuality.setBitrate(var2.videoQuality.getBitrate() * 2);
        }

        var1.setVideoEncoderSetting(var2);
        this.t = var1;
    }
    
    private void j() {
        if (this.q != null) {
            this.q.setVideoDelegate(null);
            this.q.destroy();
            this.q = null;
        }
    }
    
    public TuSDKVideoInfo getVideoInfo() {
        return this.s;
    }
    
    protected void runPendingOnDrawTasks() {
        this.a(this.n);
    }
    
    protected boolean isOnDrawTasksEmpty() {
        boolean empty = false;
        synchronized (this.n) {
            empty = this.n.isEmpty();
        }
        return empty;
    }
    
    private void a(final Queue<Runnable> queue) {
        synchronized (queue) {
            while (!queue.isEmpty()) {
                queue.poll().run();
            }
        }
    }
    
    protected void runOnDraw(final Runnable runnable) {
        this.n.add(runnable);
    }
    
    protected void onDestroy() {
        this.j();
        SdkValid.shared.checkAppAuth();
    }
    
    private TuSDKMediaRecoder k() {
        if (this.v == null) {
            (this.v = new TuSDKMediaRecoder()).setSelesSurfaceEncoder(this.t);
            this.v.setVideoEncoderSetting(this.getVideoEncoderSetting()).setOutputFilePath(this.getOutputFile()).setDelegate(this.A).setMute(true);
        }
        return this.v;
    }
    
    protected boolean isProcessing() {
        return this.o;
    }
    
    @TargetApi(17)
    public void startRecording() {
        if (this.o) {
            return;
        }
        this.o = true;
        this.a();
        this.runOnDraw(new Runnable() {
            @Override
            public void run() {
                TuSDKMovieTranscoder.this.i();
                TuSDKMovieTranscoder.this.g();
                TuSDKMovieTranscoder.this.k().startRecording(EGL14.eglGetCurrentContext(), TuSDKMovieTranscoder.this.k);
            }
        });
        this.h().prepare(this.l, null, false);
        this.h().start();
    }
    
    public void stopRecording() {
        this.o = false;
        if (this.q != null) {
            this.q.stop();
        }
        if (this.v != null) {
            this.v.stopRecording();
        }
    }
    
    public void cancelRecording() {
        this.o = false;
        if (this.q != null) {
            this.q.stop();
        }
        if (this.v != null) {
            this.v.cancelRecording();
        }
    }
    
    public boolean isRecording() {
        return this.v != null && this.v.isRecording();
    }
    
    public boolean isPaused() {
        return this.v != null && this.v.isPaused();
    }
    
    static {
        a = new float[] { -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f };
        b = new float[] { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f };
    }
}
