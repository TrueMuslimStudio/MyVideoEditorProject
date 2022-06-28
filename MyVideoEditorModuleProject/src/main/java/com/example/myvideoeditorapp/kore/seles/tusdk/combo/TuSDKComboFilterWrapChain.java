// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.combo;

import android.graphics.RectF;
import java.util.Iterator;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterLocalPackage;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutput;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import java.util.List;
import java.util.LinkedList;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;

public class TuSDKComboFilterWrapChain extends FilterWrap implements SelesParameters.FilterFacePositionInterface
{
    protected LinkedList<FilterWrap> mFilterWrapList;
    private Object a;
    private List<SelesContext.SelesInput> b;
    private SelesContext.SelesInput c;
    private SelesOutput d;
    
    public TuSDKComboFilterWrapChain() {
        this.mFilterWrapList = new LinkedList<FilterWrap>();
        this.a = new Object();
        this.b = new LinkedList<SelesContext.SelesInput>();
        this.changeOption(FilterLocalPackage.shared().option("None"));
    }
    
    public void addFilterWrap(final FilterWrap e) {
        if (e == null) {
            return;
        }
        synchronized (this.a) {
            this.mFilterWrapList.add(e);
            this.a();
        }
    }
    
    public void insertFilterWrap(final FilterWrap filterWrap, final int index) {
        if (index < 0 || filterWrap == null) {
            return;
        }
        this.a(filterWrap);
        synchronized (this.a) {
            if (index >= this.mFilterWrapList.size()) {
                this.mFilterWrapList.addLast(filterWrap);
            }
            else {
                this.mFilterWrapList.add(index, filterWrap);
            }
            this.a();
        }
    }
    
    private void a(final FilterWrap filterWrap) {
    }
    
    public void removeFilterWrap(final FilterWrap filterWrap) {
        if (filterWrap == null || !this.mFilterWrapList.contains(filterWrap)) {
            return;
        }
        synchronized (this.a) {
            this.mFilterWrapList.remove(filterWrap);
            this.a();
        }
    }
    
    public void addTerminalNode(final SelesContext.SelesInput selesInput) {
        if (selesInput == null) {
            return;
        }
        this.b.add(selesInput);
        synchronized (this.a) {
            if (this.mFilterWrapList.isEmpty()) {
                this.d.addTarget(selesInput, 0);
            }
            else {
                this.mFilterWrapList.getLast().addTarget(selesInput, 0);
            }
        }
    }
    
    public void addOffscreenRotate(final SelesContext.SelesInput c) {
        if (c == null) {
            return;
        }
        this.c = c;
        synchronized (this.a) {
            this.addTarget(c, 0);
        }
    }
    
    public void removeAllFilterWrapNode() {
        this.mFilterWrapList.clear();
        this.a();
    }
    
    public FilterWrap getFirstFilterWarp() {
        if (this.mFilterWrapList.isEmpty()) {
            return null;
        }
        return this.mFilterWrapList.getFirst();
    }
    
    @Override
    public TuSDKComboFilterWrapChain clone() {
        final TuSDKComboFilterWrapChain tuSDKComboFilterWrapChain = new TuSDKComboFilterWrapChain();
        if (tuSDKComboFilterWrapChain != null) {
            tuSDKComboFilterWrapChain.mFilterWrapList = this.mFilterWrapList;
        }
        return tuSDKComboFilterWrapChain;
    }
    
    @Override
    public void updateFaceFeatures(final FaceAligment[] array, final float n) {
        if (this.mFilterWrapList == null || this.mFilterWrapList.size() <= 0) {
            return;
        }
        for (final FilterWrap filterWrap : this.mFilterWrapList) {
            if (filterWrap instanceof SelesParameters.FilterFacePositionInterface) {
                ((SelesParameters.FilterFacePositionInterface)filterWrap).updateFaceFeatures(array, n);
            }
        }
    }
    
    private void a() {
        if (this.d == null) {
            return;
        }
        this.d.removeAllTargets();
        if (this.c != null) {
            this.d.addTarget(this.c, 0);
        }
        final Iterator<FilterWrap> iterator = this.mFilterWrapList.iterator();
        while (iterator.hasNext()) {
            iterator.next().getLastFilter().removeAllTargets();
        }
        if (this.mFilterWrapList.size() > 0) {
            this.d.addTarget(this.mFilterWrapList.getFirst().getFilter(), 0);
            for (int i = 0; i < this.mFilterWrapList.size() - 1; ++i) {
                this.mFilterWrapList.get(i).addTarget(this.mFilterWrapList.get(i + 1).getFilter(), 0);
            }
            final Iterator<SelesContext.SelesInput> iterator2 = this.b.iterator();
            while (iterator2.hasNext()) {
                this.mFilterWrapList.getLast().addTarget(iterator2.next(), 0);
            }
        }
        else {
            final Iterator<SelesContext.SelesInput> iterator3 = this.b.iterator();
            while (iterator3.hasNext()) {
                this.d.addTarget(iterator3.next(), 0);
            }
        }
    }
    
    public int getFilterWrapListSize() {
        if (this.mFilterWrapList == null) {
            return 0;
        }
        return this.mFilterWrapList.size();
    }
    
    @Override
    public void destroy() {
        super.destroy();
    }
    
    public void setDisplayRect(final RectF rectF, final float n) {
        if (this.mFilterWrapList.size() == 0) {
            return;
        }
        for (final FilterWrap filterWrap : this.mFilterWrapList) {
            if (filterWrap instanceof Face2DComboFilterWrap) {
                ((Face2DComboFilterWrap)filterWrap).setDisplayRect(rectF, n);
            }
        }
    }
    
    public void setHeaderNode(final SelesOutput d) {
        this.d = d;
        this.a();
    }
}
