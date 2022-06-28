// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.audio;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaFormat;

import com.example.myvideoeditorapp.kore.media.codec.TuSdkDecodecOperation;
import com.example.myvideoeditorapp.kore.media.codec.TuSdkMediaExtractor;
import com.example.myvideoeditorapp.kore.media.codec.decoder.TuSdkMediaFileExtractor;
import com.example.myvideoeditorapp.kore.media.codec.exception.TuSdkNoMediaTrackException;
import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaUtils;
import com.example.myvideoeditorapp.kore.utils.StringHelper;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.image.AlbumHelper;
import com.example.myvideoeditorapp.tv.core.common.TuSDKMediaUtils;
import com.example.myvideoeditorapp.tv.video.editor.TuSDKMovieWriter;
import com.example.myvideoeditorapp.tv.video.editor.TuSDKMovieWriterInterface;
import com.example.myvideoeditorapp.tv.video.editor.TuSdkTimeRange;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedList;

public class TuSdkAudioRecordCuter
{
    private int a;
    private TuSdkMediaFileExtractor b;
    private TuSdkAudioRecordCutOperation c;
    private TuSdkTimeRange d;
    private long e;
    private LinkedList<TuSdkTimeRange> f;
    private String g;
    private String h;
    private File i;
    private OnAudioRecordCuterListener j;
    
    public TuSdkAudioRecordCuter() {
        this.a = -1;
        this.c = new TuSdkAudioRecordCutOperation();
        this.b = new TuSdkMediaFileExtractor().setDecodecOperation((TuSdkDecodecOperation)this.c);
    }
    
    public void setInputPath(final String g) {
        this.g = g;
    }
    
    public void setOutputPath(final String h) {
        this.h = h;
    }
    
    public void setOnAudioRecordCuterListener(final OnAudioRecordCuterListener j) {
        this.j = j;
    }
    
    public File getOutputFile() {
        if (this.i == null) {
            if (this.h == null) {
                this.i = new File(AlbumHelper.getAblumPath(), String.format("lsq_audio_%s.aac", StringHelper.timeStampString()));
            }
            else {
                this.i = new File(this.h);
            }
        }
        return this.i;
    }
    
    public void start() {
        if (this.b == null) {
            return;
        }
        if (this.g == null || "".equals(this.g)) {
            TLog.e("%s  input path is invalid", new Object[] { "RecordCuter" });
            return;
        }
        this.b.setDataSource(this.g);
        this.b.play();
    }
    
    public void releas() {
        if (this.b != null) {
            this.b.release();
        }
        if (this.f != null) {
            this.f.clear();
        }
        this.b = null;
    }
    
    public void setOutputTimeRangeList(final LinkedList<TuSdkTimeRange> f) {
        this.f = f;
        if (f == null) {
            return;
        }
        final Iterator<TuSdkTimeRange> iterator = f.iterator();
        while (iterator.hasNext()) {
            this.e += iterator.next().durationTimeUS();
        }
    }
    
    class TuSdkAudioRecordCutOperation implements TuSdkDecodecOperation
    {
        private int b;
        private MediaFormat c;
        private TuSDKMovieWriter d;
        private long e;
        private long f;
        
        TuSdkAudioRecordCutOperation() {
            this.b = 0;
            this.f = 0L;
        }
        
        public void flush() {
        }
        
        @TargetApi(16)
        public boolean decodecInit(final TuSdkMediaExtractor tuSdkMediaExtractor) {
            this.b = TuSdkMediaUtils.getMediaTrackIndex(tuSdkMediaExtractor, "audio/");
            if (this.b < 0) {
                this.decodecException((Exception)new TuSdkNoMediaTrackException(String.format("%s decodecInit can not find media track: %s", "RecordCuter", "audio/")));
                TLog.e("%s Audio decodecInit mTrackIndex reulst false", new Object[] { "RecordCuter" });
                return false;
            }
            this.c = tuSdkMediaExtractor.getTrackFormat(this.b);
            tuSdkMediaExtractor.selectTrack(this.b);
            (this.d = new TuSDKMovieWriter(TuSdkAudioRecordCuter.this.getOutputFile().getPath(), TuSDKMovieWriterInterface.MovieWriterOutputFormat.MPEG_4)).addAudioTrack(this.c);
            this.d.start();
            if (this.c.containsKey("max-input-size")) {
                final int integer = this.c.getInteger("max-input-size");
                TuSdkAudioRecordCuter.this.a = ((integer > TuSdkAudioRecordCuter.this.a) ? integer : TuSdkAudioRecordCuter.this.a);
            }
            if (TuSdkAudioRecordCuter.this.a < 0) {
                TuSdkAudioRecordCuter.this.a = 1048576;
            }
            this.e = TuSDKMediaUtils.getAudioInterval(1024, this.c);
            TuSdkAudioRecordCuter.this.d = TuSdkAudioRecordCuter.this.f.getFirst();
            tuSdkMediaExtractor.seekTo(TuSdkAudioRecordCuter.this.d.getStartTimeUS() + 100L, 1);
            return true;
        }
        
        @TargetApi(16)
        public boolean decodecProcessUntilEnd(final TuSdkMediaExtractor tuSdkMediaExtractor) {
            final ByteBuffer allocate = ByteBuffer.allocate(TuSdkAudioRecordCuter.this.a);
            final int sampleData = tuSdkMediaExtractor.readSampleData(allocate, 0);
            if (sampleData > 0) {
                switch (this.a(tuSdkMediaExtractor, tuSdkMediaExtractor.getSampleTime())) {
                    case 1: {
                        final MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                        bufferInfo.set(0, sampleData, this.f, tuSdkMediaExtractor.getSampleFlags());
                        this.d.writeAudioSampleData(allocate, bufferInfo);
                        this.f += this.e;
                        this.a();
                        break;
                    }
                    case 2: {
                        return false;
                    }
                    case 3: {
                        this.f = TuSdkAudioRecordCuter.this.e;
                        return true;
                    }
                }
                tuSdkMediaExtractor.advance();
                return false;
            }
            this.f = TuSdkAudioRecordCuter.this.e;
            this.a();
            return true;
        }
        
        private void a() {
            if (TuSdkAudioRecordCuter.this.j == null) {
                return;
            }
            float n = this.f / (float)TuSdkAudioRecordCuter.this.e;
            if (n >= 1.0f) {
                n = 1.0f;
            }
            TuSdkAudioRecordCuter.this.j.onProgressChanged(n, this.f, TuSdkAudioRecordCuter.this.e);
        }
        
        public void decodecRelease() {
            this.d.stop();
            if (TuSdkAudioRecordCuter.this.j != null) {
                TuSdkAudioRecordCuter.this.j.onComplete(TuSdkAudioRecordCuter.this.getOutputFile());
            }
        }
        
        public void decodecException(final Exception ex) {
            TLog.e((Throwable)ex);
        }
        
        private int a(final TuSdkMediaExtractor tuSdkMediaExtractor, final long n) {
            if (tuSdkMediaExtractor == null) {
                return 3;
            }
            if (TuSdkAudioRecordCuter.this.f == null) {
                return 1;
            }
            if (TuSdkAudioRecordCuter.this.d.contains(n)) {
                return 1;
            }
            TuSdkAudioRecordCuter.this.f.removeFirst();
            if (TuSdkAudioRecordCuter.this.f.size() == 0) {
                return 3;
            }
            TuSdkAudioRecordCuter.this.d = TuSdkAudioRecordCuter.this.f.getFirst();
            tuSdkMediaExtractor.seekTo(TuSdkAudioRecordCuter.this.d.getStartTimeUS(), 1);
            return 2;
        }
    }
    
    public interface OnAudioRecordCuterListener
    {
        void onProgressChanged(final float p0, final long p1, final long p2);
        
        void onComplete(final File p0);
    }
}
