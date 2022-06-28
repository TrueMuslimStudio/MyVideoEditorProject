// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.mutableCliper;

import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVAssetTrack;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVAssetTrackExtractor;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVSampleBuffer;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVTimeRange;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.google.firebase.firestore.util.Assert;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TuSdkMediaCliperSource implements AVAssetTrackExtractor
{
    private TuSdkMediaCliperPIPTimeline a;
    private TuSdkMediaCliperSourceDelegate b;
    
    public TuSdkMediaCliperSource() {
        this.a = new TuSdkMediaCliperPIPTimeline();
    }
    
    public void setDelegate(final TuSdkMediaCliperSourceDelegate b) {
        this.b = b;
    }
    
    public TuSdkMediaTrackItem currentItem() {
        if (this.a.currentNode() == null) {
            return null;
        }
        return this.a.currentNode().item();
    }
    
    public void appendTrackItem(final TuSdkMediaTrackItem tuSdkMediaTrackItem) {
        this.a.add(tuSdkMediaTrackItem);
        if (this.b != null) {
            this.b.didAddSourceItem(tuSdkMediaTrackItem);
        }
    }
    
    public void insertTrackItem(final int n, final TuSdkMediaTrackItem tuSdkMediaTrackItem) {
        this.a.add(n, tuSdkMediaTrackItem);
        if (this.b != null) {
            this.b.ddiRemoveSourceIem(tuSdkMediaTrackItem);
        }
    }
    
    public void removeTrackItem(final TuSdkMediaTrackItem tuSdkMediaTrackItem) {
        if (this.a.removeTrackItem(tuSdkMediaTrackItem) && this.b != null) {
            this.b.ddiRemoveSourceIem(tuSdkMediaTrackItem);
        }
    }
    
    public TuSdkMediaTrackItem removeTrackItem(final int n) {
        final TuSdkMediaTrackItem removeTrackItem = this.a.removeTrackItem(n);
        if (removeTrackItem != null && this.b != null) {
            this.b.ddiRemoveSourceIem(removeTrackItem);
        }
        return removeTrackItem;
    }
    
    public List<TuSdkMediaTrackItem> items() {
        final ArrayList<TuSdkMediaTrackItem> list = new ArrayList<TuSdkMediaTrackItem>();
        final Iterator<TuSdkMediaCliperPIPTimeline.Node> iterator = this.a.nodes().iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().item());
        }
        return list;
    }

    @Override
    public AVSampleBuffer readSampleBuffer(final int n) {
        final AVSampleBuffer sampleBuffer = this.a.currentNode().readSampleBuffer(n);
        if (sampleBuffer == null && !this.isOutputDone()) {
            if (this.a.currentNode().nextNode() != null) {
                return new AVSampleBuffer(this.a.currentNode().nextNode().inputTrack().mediaFormat());
            }
            this.advance();
        }
        return sampleBuffer;
    }
    
    @Override
    public boolean setTimeRange(final AVTimeRange avTimeRange) {
        return false;
    }
    
    @Override
    public boolean seekTo(final long n, final int n2) {
        return this.a.seekTo(n, n2);
    }
    
    @Override
    public boolean advance() {
        if (this.a.currentNode() == null) {
            TLog.w("advance no data", new Object[0]);
            return false;
        }
        return this.a.currentNode().advance() || this.a.moveToNextNode();
    }
    
    @Override
    public boolean isDecodeOnly(final long n) {
        return n >= 0L && this.a.currentNode() != null && this.a.currentNode().isDecodeOnly(n);
    }
    
    @Override
    public boolean isOutputDone() {
        for (TuSdkMediaCliperPIPTimeline.Node node = this.a.currentNode(); node != null; node = node.nextNode()) {
            if (!node.isOutputDone()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public long durationTimeUs() {
        return this.a.durationTimeUs();
    }
    
    @Override
    public long outputTimeUs() {
        if (this.a.currentNode() == null) {
            return 0L;
        }
        return this.a.currentNode().outputStartTimeUs + this.a.currentNode().outputTimeUs();
    }
    
    @Override
    public long calOutputTimeUs(final long n) {
        if (this.a.currentNode() == null) {
            return 0L;
        }
        return this.a.currentNode().outputTimeUs(n);
    }
    
    @Override
    public boolean lowFrameRateVideo() {
        final Iterator<TuSdkMediaCliperPIPTimeline.Node> iterator = this.a.nodes().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().lowFrameRateVideo()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean supportSeek() {
        return this.a.nodes().size() == 1 || !this.lowFrameRateVideo();
    }
    
    @Override
    public boolean step() {
        return false;
    }
    
    @Override
    public void setAlwaysCopiesSampleData(final boolean alwaysCopiesSampleData) {
        for (TuSdkMediaCliperPIPTimeline.Node node = this.a.currentNode(); node != null; node = node.nextNode()) {
            node.setAlwaysCopiesSampleData(alwaysCopiesSampleData);
        }
    }
    
    @Override
    public AVAssetTrack inputTrack() {
        final TuSdkMediaCliperPIPTimeline.Node currentNode = this.a.currentNode();
        if (currentNode != null) {
            return currentNode.inputTrack();
        }
        return null;
    }
    
    @Override
    public void reset() {
        this.a.reset();
    }
    
    public interface TuSdkMediaCliperSourceDelegate
    {
        void didAddSourceItem(final TuSdkMediaTrackItem p0);
        
        void ddiRemoveSourceIem(final TuSdkMediaTrackItem p0);
    }
}
