// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget.paintdraw;

import android.graphics.Color;
import java.util.ArrayList;
import java.util.Iterator;
import com.example.myvideoeditorapp.kore.TuSdkContext;
import android.util.AttributeSet;
import android.content.Context;
import android.view.View;

import java.util.List;
import com.example.myvideoeditorapp.kore.view.TuSdkRelativeLayout;

public abstract class PaintBarViewBase extends TuSdkRelativeLayout
{
    private List<PaintData> a;
    private List<String> b;
    private boolean c;
    
    public abstract <T extends View> T getTableView();
    
    protected abstract void notifySelectedPaint(final PaintData p0);
    
    protected abstract void refreshPaintDatas();
    
    public PaintBarViewBase(final Context context) {
        super(context);
        this.c = true;
    }
    
    public PaintBarViewBase(final Context context, final AttributeSet set) {
        super(context, set);
        this.c = true;
    }
    
    public PaintBarViewBase(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.c = true;
    }

    public void loadPaints() {
        List var1 = null;
        if (this.a == null || this.a.size() == 0) {
            var1 = this.buildPaintItems();
            this.a = var1;
        }

        PaintData var2 = null;
        int var3 = this.a(PaintData.PaintType.Color);
        if (var3 == -1) {
            if (!var1.isEmpty()) {
                var2 = (PaintData)var1.get(1);
            }
        } else {
            var2 = (PaintData)var1.get(var3);
        }

        if (this.getTableView() != null) {
            ((PaintTableViewInterface)this.getTableView()).setModeList(var1);
            var3 = var1.indexOf(var2);
            ((PaintTableViewInterface)this.getTableView()).setSelectedPosition(var3, true);
            ((PaintTableViewInterface)this.getTableView()).scrollToPosition(var3);
        }

        this.notifySelectedPaint(var2);
    }
    
    private int a(final PaintData.PaintType paintType) {
        int index = -1;
        if (this.c) {
            final String loadSharedCache = TuSdkContext.sharedPreferences().loadSharedCache(String.format("lsq_lastpaint_%s", paintType));
            if (loadSharedCache != null) {
                for (final PaintData paintData : this.a) {
                    if (((Integer)paintData.getData()).equals(Integer.valueOf(loadSharedCache))) {
                        index = this.a.indexOf(paintData);
                        break;
                    }
                }
            }
        }
        return index;
    }
    
    protected List<PaintData> buildPaintItems() {
        final ArrayList<PaintData> list = new ArrayList<PaintData>();
        if (this.b == null || this.b.isEmpty()) {
            this.a(list);
        }
        else {
            try {
                final Iterator<String> iterator = this.b.iterator();
                while (iterator.hasNext()) {
                    list.add(new PaintData(Color.parseColor((String)iterator.next()), PaintData.PaintType.Color));
                }
            }
            catch (Exception ex) {
                list.clear();
                this.b.clear();
                this.a(list);
            }
        }
        return list;
    }
    
    private void a(final List<PaintData> list) {
        list.add(new PaintData(Color.parseColor("#f9f9f9"), PaintData.PaintType.Color));
        list.add(new PaintData(Color.parseColor("#2b2b2b"), PaintData.PaintType.Color));
        list.add(new PaintData(Color.parseColor("#ff1d12"), PaintData.PaintType.Color));
        list.add(new PaintData(Color.parseColor("#fbf606"), PaintData.PaintType.Color));
        list.add(new PaintData(Color.parseColor("#14e213"), PaintData.PaintType.Color));
        list.add(new PaintData(Color.parseColor("#199bff"), PaintData.PaintType.Color));
        list.add(new PaintData(Color.parseColor("#8c06ff"), PaintData.PaintType.Color));
    }
    
    protected void addColorItem(final PaintData paintData) {
        this.a.add(paintData);
        this.refreshPaintDatas();
    }
    
    protected void addBrushGroup(final List<String> b) {
        this.b = b;
    }
    
    public void clearColors() {
        if (this.a == null) {
            return;
        }
        this.a.clear();
        this.refreshPaintDatas();
    }

    public void selectPaint(PaintData var1, PaintDrawBarItemCellBase var2, int var3) {
        ((PaintTableViewInterface)this.getTableView()).changeSelectedPosition(var3);
        ((PaintTableViewInterface)this.getTableView()).smoothScrollByCenter(var2);
        if (var1 != null) {
            this.a(var1);
        }

    }
    
    private void a(final PaintData paintData) {
        if (paintData == null) {
            return;
        }
        final String value = String.valueOf(paintData.getData());
        if (!this.c) {
            return;
        }
        TuSdkContext.sharedPreferences().saveSharedCache(String.format("lsq_lastpaint_%s", paintData.getPaintType()), value);
    }
    
    public List<PaintData> getCurrentColors() {
        return this.a;
    }
    
    public boolean isSaveLastPaint() {
        return this.c;
    }
    
    public void setSaveLastPaint(final boolean c) {
        this.c = c;
    }
}
