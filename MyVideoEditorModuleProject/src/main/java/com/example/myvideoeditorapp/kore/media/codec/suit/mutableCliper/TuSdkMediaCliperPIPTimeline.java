// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.mutableCliper;

import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVAssetTrackMediaExtractor;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class TuSdkMediaCliperPIPTimeline implements TuSdkMediaTrackItem.TuSdkMediaTrackItemDelegate
{
    private List<Node> a;
    private Node b;
    
    public TuSdkMediaCliperPIPTimeline() {
        this.a = new ArrayList<Node>(2);
    }
    
    public int size() {
        return this.a.size();
    }
    
    public Node currentNode() {
        return this.b;
    }
    
    public List<Node> nodes() {
        return this.a;
    }
    
    public void add(final TuSdkMediaTrackItem tuSdkMediaTrackItem) {
        final Node node = new Node(tuSdkMediaTrackItem);
        if (this.a.size() > 0) {
            this.a.get(this.a.size() - 1).d = node;
        }
        this.a.add(node);
        tuSdkMediaTrackItem.a(this);
        this.a();
    }
    
    public void add(final int a, final TuSdkMediaTrackItem tuSdkMediaTrackItem) {
        if (this.a.size() == 0) {
            this.add(tuSdkMediaTrackItem);
            return;
        }
        this.a.add(Math.min(a, this.a.size() - 1), new Node(tuSdkMediaTrackItem));
        tuSdkMediaTrackItem.a(this);
        this.a();
    }
    
    public boolean removeTrackItem(final TuSdkMediaTrackItem tuSdkMediaTrackItem) {
        int n = -1;
        for (int i = 0; i < this.a.size(); ++i) {
            if (this.a.get(i).item() == tuSdkMediaTrackItem) {
                n = i;
                break;
            }
        }
        return this.removeTrackItem(n) != null;
    }
    
    public TuSdkMediaTrackItem removeTrackItem(final int n) {
        if (n >= 0 && n < this.a.size()) {
            final TuSdkMediaTrackItem item = this.a.remove(n).item();
            this.a();
            return item;
        }
        return null;
    }
    
    private void a() {
        Node node = null;
        long outputEndTimeUs = 0L;
        long a = 0L;
        for (final Node node2 : this.a) {
            node2.d = null;
            if (node == null) {
                node = node2;
            }
            else {
                node.d = node2;
                node = node2;
            }
            node2.outputStartTimeUs = outputEndTimeUs;
            node2.outputEndTimeUs = outputEndTimeUs + node2.durationTimeUs();
            outputEndTimeUs = node2.outputEndTimeUs;
            node2.e = a;
            node2.f = a + node2.inputTrack().durationTimeUs();
            a = node2.f;
            node2.refresh();
        }
        if (this.a.size() == 0) {
            this.b = null;
        }
        if (this.a.size() > 0 && (this.b == null || !this.a.contains(this.b))) {
            this.b = this.a.get(0);
        }
    }
    
    public boolean seekTo(final long n, final int n2) {
        for (final Node b : this.a) {
            b.reset();
            if (b.a(n)) {
                this.b = b;
                return b.seekTo(b.b(n), n2);
            }
        }
        return false;
    }
    
    public boolean moveToNextNode() {
        if (this.b == null) {
            return false;
        }
        final Node nextNode = this.b.nextNode();
        if (nextNode == null) {
            return false;
        }
        nextNode.reset();
        this.b = nextNode;
        return true;
    }
    
    public void reset() {
        this.b = null;
        final Iterator<Node> iterator = this.a.iterator();
        while (iterator.hasNext()) {
            iterator.next().reset();
        }
        if (this.a.size() > 0) {
            this.b = this.a.get(0);
        }
    }
    
    public long durationTimeUs() {
        long n = 0L;
        for (final Node node : this.a) {
            n += (node.c ? node.durationTimeUs() : 0L);
        }
        return n;
    }
    
    @Override
    public void timeRangeDidChanged(final TuSdkMediaTrackItem tuSdkMediaTrackItem) {
        this.a();
    }
    
    public class Node extends AVAssetTrackMediaExtractor
    {
        private TuSdkMediaTrackItem b;
        private boolean c;
        private Node d;
        public long outputStartTimeUs;
        public long outputEndTimeUs;
        private long e;
        private long f;
        
        public Node(final TuSdkMediaTrackItem b) {
            super(b.track());
            this.c = true;
            this.outputStartTimeUs = -1L;
            this.outputEndTimeUs = -1L;
            this.e = -1L;
            this.f = -1L;
            this.b = b;
            this.refresh();
        }
        
        public TuSdkMediaTrackItem item() {
            return this.b;
        }
        
        public void refresh() {
            this.setTimeRange(this.b.timeRange());
        }
        
        public Node nextNode() {
            if (this.c && this.d != null && this.d.c) {
                return this.d;
            }
            return null;
        }
        
        private boolean a(final long n) {
            return n >= this.outputStartTimeUs && n <= this.outputEndTimeUs;
        }
        
        private long b(final long n) {
            return n - this.outputStartTimeUs + this.timeRange().startUs();
        }
        
        public long outputTimeUs(final long n) {
            return Math.max(0L, this.outputStartTimeUs + n - this.timeRange().startUs());
        }
    }
}
