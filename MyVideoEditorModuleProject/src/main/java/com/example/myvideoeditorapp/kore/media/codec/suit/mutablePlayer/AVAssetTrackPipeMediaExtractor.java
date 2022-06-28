// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer;

import java.util.ArrayList;
import java.util.Iterator;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.google.firebase.firestore.util.Assert;

import java.util.List;

public class AVAssetTrackPipeMediaExtractor implements AVAssetTrackExtractor
{
    private TrackStreamPipeTimeline a;
    
    public AVAssetTrackPipeMediaExtractor(final List<AVAssetTrack> list) {
        this.a = new TrackStreamPipeTimeline(list);
    }
    
    public int inputTrackCount() {
        return this.a.c.size();
    }
    
    @Override
    public AVSampleBuffer readSampleBuffer(final int n) {
        final AVSampleBuffer sampleBuffer = this.a.d.readSampleBuffer(n);
        if (sampleBuffer == null && !this.isOutputDone()) {
            if (this.a.d.nextNode() != null) {
                return new AVSampleBuffer(this.a.d.nextNode().inputTrack().mediaFormat());
            }
            this.advance();
        }
        return sampleBuffer;
    }
    
    @Override
    public boolean setTimeRange(final AVTimeRange avTimeRange) {
        if (this.lowFrameRateVideo() && this.a.c.size() > 0) {
            TLog.e("The input video source contains low-frame rate video, and seek is not supported for the video source.", new Object[0]);
            return false;
        }
        this.a.a(avTimeRange);
        return true;
    }
    
    @Override
    public boolean seekTo(final long n, final int n2) {
        return this.a.a(n, n2);
    }
    
    @Override
    public boolean advance() {
        if (this.a.d == null) {
            TLog.w("advance no data", new Object[0]);
            return false;
        }
        return this.a.d.advance();
    }
    
    @Override
    public boolean isDecodeOnly(final long n) {
        return n >= 0L && this.a.d != null && this.a.d.isDecodeOnly(n);
    }
    
    @Override
    public boolean isOutputDone() {
        for (TrackStreamPipeTimeline.TrackStreamPipeNode trackStreamPipeNode = this.a.d; trackStreamPipeNode != null; trackStreamPipeNode = trackStreamPipeNode.nextNode()) {
            if (!trackStreamPipeNode.isOutputDone()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public long durationTimeUs() {
        return this.a.d();
    }
    
    @Override
    public long outputTimeUs() {
        if (this.a.d == null) {
            return 0L;
        }
        return this.a.d.d + this.a.d.outputTimeUs();
    }
    
    @Override
    public long calOutputTimeUs(final long n) {
        if (this.a.d == null) {
            return 0L;
        }
        return this.a.d.c(n);
    }
    
    public long calOutputTimeUs(final long n, final boolean b) {
        if (this.a.d == null) {
            return 0L;
        }
        if (b) {
            return this.a.e.c(n);
        }
        return this.a.d.c(n);
    }
    
    @Override
    public boolean lowFrameRateVideo() {
        final Iterator<TrackStreamPipeTimeline.TrackStreamPipeNode> iterator = this.a.c.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().lowFrameRateVideo()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean supportSeek() {
        return this.a.c.size() == 1 || !this.lowFrameRateVideo();
    }
    
    @Override
    public void setAlwaysCopiesSampleData(final boolean alwaysCopiesSampleData) {
        for (TrackStreamPipeTimeline.TrackStreamPipeNode trackStreamPipeNode = this.a.d; trackStreamPipeNode != null; trackStreamPipeNode = trackStreamPipeNode.nextNode()) {
            trackStreamPipeNode.setAlwaysCopiesSampleData(alwaysCopiesSampleData);
        }
    }
    
    @Override
    public AVAssetTrack inputTrack() {
        final TrackStreamPipeTimeline.TrackStreamPipeNode b = this.a.d;
        if (b != null) {
            return b.inputTrack();
        }
        return null;
    }
    
    @Override
    public void reset() {
        this.a.c();
    }
    
    @Override
    public boolean step() {
        TLog.e("type %s next step", this.inputTrack().mediaType());
        return this.a.b();
    }
    
    private class TrackStreamPipeTimeline
    {
        private List<AVAssetTrack> b;
        private List<TrackStreamPipeNode> c;
        private TrackStreamPipeNode d;
        private TrackStreamPipeNode e;
        
        public TrackStreamPipeTimeline(final List<AVAssetTrack> b) {
            this.b = b;
            this.a();
            this.d = this.c.get(0);
            this.e = this.d;
        }
        
        private boolean a(final long n, final int n2) {
            for (final TrackStreamPipeNode d : this.c) {
                d.reset();
                if (d.a(n)) {
                    this.d = d;
                    return d.seekTo(d.b(n), n2);
                }
            }
            return false;
        }
        
        private void a() {
            this.c = new ArrayList<TrackStreamPipeNode>(2);
            TrackStreamPipeNode trackStreamPipeNode = null;
            long b = 0L;
            long c = 0L;
            final Iterator<AVAssetTrack> iterator = this.b.iterator();
            while (iterator.hasNext()) {
                final TrackStreamPipeNode trackStreamPipeNode2 = new TrackStreamPipeNode(iterator.next());
                if (trackStreamPipeNode == null) {
                    trackStreamPipeNode = trackStreamPipeNode2;
                }
                else {
                    trackStreamPipeNode.c = trackStreamPipeNode2;
                    trackStreamPipeNode = trackStreamPipeNode2;
                }
                trackStreamPipeNode2.d = b;
                trackStreamPipeNode2.e = b + trackStreamPipeNode2.durationTimeUs();
                b = trackStreamPipeNode2.e;
                trackStreamPipeNode2.f = c;
                trackStreamPipeNode2.g = c + trackStreamPipeNode2.inputTrack().durationTimeUs();
                c = trackStreamPipeNode2.g;
                this.c.add(trackStreamPipeNode2);
            }
        }
        
        private void a(final AVTimeRange avTimeRange) {
            final List<TrackStreamPipeNode> c = this.c;
            long b = 0L;
            for (final TrackStreamPipeNode trackStreamPipeNode : c) {
                final AVTimeRange a = trackStreamPipeNode.a(avTimeRange);
                if (a == null) {
                    trackStreamPipeNode.b = false;
                }
                else {
                    trackStreamPipeNode.b = true;
                    final long durationUs = a.durationUs();
                    trackStreamPipeNode.setTimeRange(a);
                    trackStreamPipeNode.d = b;
                    trackStreamPipeNode.e = b + durationUs;
                    b = trackStreamPipeNode.e;
                }
            }
            this.d = null;
            for (final TrackStreamPipeNode d : this.c) {
                if (d.b) {
                    this.d = d;
                    break;
                }
            }
            if (this.d == null) {
                this.a();
                this.d = this.c.get(0);
                TLog.e("Please set a valid cropping time.", new Object[0]);
            }
        }
        
        private boolean b() {
            if (this.d == null) {
                return false;
            }
            final TrackStreamPipeNode nextNode = this.d.nextNode();
            if (nextNode == null) {
                return false;
            }
            nextNode.reset();
            this.e = this.d;
            this.d = nextNode;
            return true;
        }
        
        private void c() {
            this.d = null;
            final Iterator<TrackStreamPipeNode> iterator = this.c.iterator();
            while (iterator.hasNext()) {
                iterator.next().reset();
            }
            if (this.c.size() > 0) {
                this.d = this.c.get(0);
            }
        }
        
        private long d() {
            long n = 0L;
            for (final TrackStreamPipeNode trackStreamPipeNode : this.c) {
                n += (trackStreamPipeNode.b ? trackStreamPipeNode.durationTimeUs() : 0L);
            }
            return n;
        }
        
        public class TrackStreamPipeNode extends AVAssetTrackMediaExtractor
        {
            private boolean b;
            private TrackStreamPipeNode c;
            private long d;
            private long e;
            private long f;
            private long g;
            
            public TrackStreamPipeNode(final AVAssetTrack avAssetTrack) {
                super(avAssetTrack);
                this.b = true;
                this.d = -1L;
                this.e = -1L;
                this.f = -1L;
                this.g = -1L;
            }
            
            public TrackStreamPipeNode nextNode() {
                if (this.b && this.c != null && this.c.b) {
                    return this.c;
                }
                return null;
            }
            
            private boolean a(final long n) {
                return n >= this.d && n <= this.e;
            }
            
            private long b(final long n) {
                return n - this.d + this.timeRange().startUs();
            }
            
            private long c(final long n) {
                return Math.max(0L, this.d + n - this.timeRange().startUs());
            }
            
            private AVTimeRange a(final AVTimeRange avTimeRange) {
                final AVTimeRange avTimeRangeMake = AVTimeRange.AVTimeRangeMake(this.f, this.g - this.f);
                if (this.f >= avTimeRange.endUs()) {
                    return null;
                }
                final long a = this.g - this.f;
                if (avTimeRange.startUs() <= this.f && avTimeRange.endUs() >= this.g) {
                    return AVTimeRange.AVTimeRangeMake(0L, a);
                }
                if (!avTimeRangeMake.containsTimeUs(avTimeRange.startUs()) && !avTimeRangeMake.containsTimeUs(avTimeRange.endUs())) {
                    return null;
                }
                final long max = Math.max(avTimeRange.startUs() - this.f, 0L);
                long n = Math.min(a, avTimeRange.endUs() - max);
                if (avTimeRange.endUs() <= this.g) {
                    n = Math.min(n, avTimeRange.endUs() - this.f - max);
                }
                final long min = Math.min(this.g - max, n);
                if (min <= 0L) {
                    return null;
                }
                return AVTimeRange.AVTimeRangeMake(max, min);
            }
        }
    }
}
