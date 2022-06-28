// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.api.audio.preproc.mixer;

import com.example.myvideoeditorapp.kore.utils.StringHelper;
import java.io.File;
import android.text.TextUtils;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioInfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;
import com.example.myvideoeditorapp.tv.video.editor.TuSdkTimeRange;

public class TuSDKAudioRenderEntry extends TuSdkMediaDataSource
{
    private TuSDKAudioRenderInfoWrap a;
    private boolean b;
    private TuSdkTimeRange c;
    private boolean d;
    private TuSdkTimeRange e;
    private float f;
    private RandomAccessFile g;
    
    public TuSDKAudioRenderEntry() {
        this.f = 1.0f;
    }
    
    public TuSDKAudioRenderEntry(final TuSdkMediaDataSource tuSdkMediaDataSource) {
        this.f = 1.0f;
        switch (tuSdkMediaDataSource.getMediaDataType().ordinal()) {
            case 1: {
                this.setUri(tuSdkMediaDataSource.getContext(), tuSdkMediaDataSource.getUri(), tuSdkMediaDataSource.getRequestHeaders());
                break;
            }
            case 2: {
                this.setMediaDataSource(tuSdkMediaDataSource.getMediaDataSource());
                break;
            }
            case 3: {
                this.setFileDescriptor(tuSdkMediaDataSource.getFileDescriptor(), tuSdkMediaDataSource.getFileDescriptorOffset(), tuSdkMediaDataSource.getFileDescriptorLength());
                break;
            }
            case 4: {
                this.setPath(tuSdkMediaDataSource.getPath(), tuSdkMediaDataSource.getRequestHeaders());
                break;
            }
        }
    }
    
    public TuSDKAudioRenderEntry(final TuSdkAudioInfo tuSdkAudioInfo) {
        this.f = 1.0f;
        this.a = TuSDKAudioRenderInfoWrap.createWithAudioInfo(tuSdkAudioInfo);
    }
    
    public TuSDKAudioRenderEntry(final TuSDKAudioRenderInfoWrap a) {
        this.f = 1.0f;
        this.a = a;
    }
    
    public TuSDKAudioRenderEntry setRawInfo(final TuSDKAudioRenderInfoWrap a) {
        this.a = a;
        return this;
    }
    
    public TuSDKAudioRenderInfoWrap getRawInfo() {
        if (this.a == null) {
            this.a = TuSDKAudioRenderInfoWrap.createWithMediaDataSource(this);
        }
        return this.a;
    }
    
    public TuSDKAudioRenderEntry setTrunk(final boolean b) {
        this.b = b;
        return this;
    }
    
    public boolean isTrunk() {
        return this.b;
    }
    
    public TuSDKAudioRenderEntry setLooping(final boolean d) {
        this.d = d;
        return this;
    }
    
    public boolean isLooping() {
        return this.d;
    }
    
    public TuSDKAudioRenderEntry setTimeRange(final TuSdkTimeRange c) {
        this.c = c;
        return this;
    }
    
    public TuSdkTimeRange getTimeRange() {
        return this.c;
    }
    
    public boolean validateTimeRange() {
        return this.c != null && this.c.isValid();
    }
    
    public TuSDKAudioRenderEntry setCutTimeRange(final TuSdkTimeRange e) {
        this.e = e;
        return this;
    }
    
    public float getVolume() {
        return this.f;
    }
    
    public TuSDKAudioRenderEntry setVolume(final float f) {
        if (this.f <= 1.0f && this.f >= 0.0f) {
            this.f = f;
        }
        return this;
    }
    
    public int bytesSizeOfTimeRangeStartPosition() {
        if (!this.validateTimeRange() || this.getTimeRange().getStartTime() == 0.0f || this.a == null) {
            return 0;
        }
        return this.a.bytesCountOfTime((int)Math.ceil(this.getTimeRange().getStartTime()));
    }
    
    public int bytesSizeOfTimeRangeEndPosition() {
        if (!this.validateTimeRange() || this.a == null) {
            return 0;
        }
        return this.a.bytesCountOfTime((int)Math.ceil(this.getTimeRange().getEndTime()));
    }
    
    public TuSdkTimeRange getCutTimeRange() {
        return this.e;
    }
    
    public boolean validateCutTimeRange() {
        return this.e != null && this.e.isValid();
    }
    
    public boolean clearDecodeCahceInfo() {
        if (this.a == null || !this.a.isValid()) {
            return false;
        }
        if (!TextUtils.isEmpty((CharSequence)this.a.getPath())) {
            new File(this.a.getPath()).delete();
        }
        this.a = null;
        return false;
    }
    
    public String getFingerprint() {
        return this.getFingerprint(null);
    }
    
    public String getFingerprint(final String str) {
        if (!this.isValid()) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        if (this.validateCutTimeRange()) {
            sb.append(this.getCutTimeRange().getStartTime()).append(this.getCutTimeRange().getEndTime());
        }
        if (!TextUtils.isEmpty((CharSequence)this.getPath())) {
            sb.append(this.getPath());
        }
        else {
            sb.append(this.getUri().getPath());
        }
        if (!TextUtils.isEmpty((CharSequence)str)) {
            sb.append(str);
        }
        return StringHelper.md5(sb.toString());
    }
    
    protected void finalize() throws Throwable {
        super.finalize();
    }
    
    public int readAudioData(final byte[] b) throws IOException {
        if (this.g == null) {
            this.g = new RandomAccessFile(this.getRawInfo().getPath(), "r");
        }
        return this.g.read(b);
    }
    
    public int readAudioDataForTimeUs(final long n, final byte[] b) throws IOException {
        if (this.getTimeRange() != null && !this.getTimeRange().contains(n)) {
            return -1;
        }
        if (this.g == null) {
            this.g = new RandomAccessFile(this.getRawInfo().getPath(), "r");
        }
        this.bytesSizeOfTimeRangeStartPosition();
        return this.g.read(b);
    }
}
