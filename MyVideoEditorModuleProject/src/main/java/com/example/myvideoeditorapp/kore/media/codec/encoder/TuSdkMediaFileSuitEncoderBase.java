// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.encoder;

import android.graphics.RectF;
import android.media.MediaFormat;

import com.example.myvideoeditorapp.kore.api.extend.TuSdkAudioRender;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkFilterBridge;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkMediaProgress;
import com.example.myvideoeditorapp.kore.api.extend.TuSdkSurfaceRender;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioInfo;
import com.example.myvideoeditorapp.kore.seles.sources.SelesWatermark;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;

import java.io.IOException;

public abstract class TuSdkMediaFileSuitEncoderBase
{
    protected static final int TRANS_STATE_UNINITIALIZED = -1;
    protected static final int TRANS_STATE_STARTED = 0;
    protected static final int TRANS_STATE_STOPPED = 1;
    protected final TuSdkMediaFileEncoder mEncoder;
    protected TuSdkMediaProgress mProgress;
    protected int mState;
    protected TuSdkSurfaceRender mSurfaceRender;
    protected TuSdkAudioRender mAudioRender;
    
    public TuSdkMediaFileSuitEncoderBase() {
        this.mEncoder = new TuSdkMediaFileEncoder();
        this.mState = -1;
    }
    
    public void setOutputFilePath(final String outputFilePath) {
        this.mEncoder.setOutputFilePath(outputFilePath);
    }
    
    public int setOutputVideoFormat(final MediaFormat outputVideoFormat) {
        return this.mEncoder.setOutputVideoFormat(outputVideoFormat);
    }
    
    public int setOutputAudioFormat(final MediaFormat outputAudioFormat) {
        return this.mEncoder.setOutputAudioFormat(outputAudioFormat);
    }
    
    public TuSdkAudioInfo getOutputAudioInfo() {
        return this.mEncoder.getOutputAudioInfo();
    }
    
    public void setSurfaceRender(final TuSdkSurfaceRender tuSdkSurfaceRender) {
        this.mSurfaceRender = tuSdkSurfaceRender;
        this.mEncoder.setSurfaceRender(tuSdkSurfaceRender);
    }
    
    public void setAudioRender(final TuSdkAudioRender tuSdkAudioRender) {
        this.mAudioRender = tuSdkAudioRender;
        this.mEncoder.setAudioRender(tuSdkAudioRender);
    }
    
    public void setWatermark(final SelesWatermark watermark) {
        this.mEncoder.setWatermark(watermark);
    }
    
    public void setOutputOrientation(final ImageOrientation outputOrientation) {
        this.mEncoder.setOutputOrientation(outputOrientation);
    }
    
    public void setCanvasRect(final RectF canvasRect) {
        this.mEncoder.setCanvasRect(canvasRect);
    }
    
    public TuSdkFilterBridge getFilterBridge() {
        return this.mEncoder.getFilterBridge();
    }
    
    public void setFilterBridge(final TuSdkFilterBridge filterBridge) {
        this.mEncoder.setFilterBridge(filterBridge);
    }
    
    public void disconnect() {
        this.mEncoder.disconnect();
    }
    
    public boolean run(final TuSdkMediaProgress mProgress) throws IOException {
        if (this.mState != -1) {
            TLog.w("%s run can not after initialized.", "TuSdkMediaFileSuitEncoderBase");
            return false;
        }
        if (this.mEncoder.getOutputDataSource() == null) {
            TLog.w("%s run need a output file path.", "TuSdkMediaFileSuitEncoderBase");
            return false;
        }
        if (!this.mEncoder.hasVideoEncoder()) {
            TLog.w("%s run need set Output Video Format.", "TuSdkMediaFileSuitEncoderBase");
            return false;
        }
        if (!this.mEncoder.hasAudioEncoder()) {
            TLog.w("%s run can not find Output Audio Format, then ignore audio.", "TuSdkMediaFileSuitEncoderBase");
        }
        this.mProgress = mProgress;
        this.mState = 0;
        return this._init();
    }
    
    public void stop() {
        if (this.mState == 1) {
            TLog.w("%s already stoped.", "TuSdkMediaFileSuitEncoderBase");
            return;
        }
        this.mState = 1;
        this.mEncoder.release();
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.stop();
        super.finalize();
    }
    
    protected abstract boolean _init() throws IOException;
}
