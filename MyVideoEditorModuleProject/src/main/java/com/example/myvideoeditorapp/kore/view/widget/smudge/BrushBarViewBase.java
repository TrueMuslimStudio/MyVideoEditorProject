// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget.smudge;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import java.util.Iterator;
import java.util.ArrayList;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.secret.SdkValid;
import com.example.myvideoeditorapp.kore.type.DownloadTaskStatus;
import com.example.myvideoeditorapp.kore.network.TuSdkDownloadItem;
import android.util.AttributeSet;
import android.content.Context;
import android.view.View;

import java.util.List;
import com.example.myvideoeditorapp.kore.view.TuSdkRelativeLayout;

public abstract class BrushBarViewBase extends TuSdkRelativeLayout
{
    private BrushLocalPackage.BrushLocalPackageDelegate a;
    private BrushTableViewInterface.BrushAction b;
    private List<String> c;
    private boolean d;
    
    public abstract <T extends View> T getTableView();
    
    protected abstract void notifySelectedBrush(final BrushData p0);
    
    protected abstract void refreshBrushDatas();
    
    public BrushBarViewBase(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.a = new BrushLocalPackage.BrushLocalPackageDelegate() {
            @Override
            public void onBrushPackageStatusChanged(final BrushLocalPackage brushLocalPackage, final TuSdkDownloadItem tuSdkDownloadItem, final DownloadTaskStatus downloadTaskStatus) {
                if (tuSdkDownloadItem == null || downloadTaskStatus == null) {
                    return;
                }
                switch (downloadTaskStatus.ordinal()) {
                    case 1:
                    case 2: {
                        BrushBarViewBase.this.refreshBrushDatas();
                        break;
                    }
                }
            }
        };
        this.d = true;
    }
    
    public BrushBarViewBase(final Context context, final AttributeSet set) {
        super(context, set);
        this.a = new BrushLocalPackage.BrushLocalPackageDelegate() {
            @Override
            public void onBrushPackageStatusChanged(final BrushLocalPackage brushLocalPackage, final TuSdkDownloadItem tuSdkDownloadItem, final DownloadTaskStatus downloadTaskStatus) {
                if (tuSdkDownloadItem == null || downloadTaskStatus == null) {
                    return;
                }
                switch (downloadTaskStatus.ordinal()) {
                    case 1:
                    case 2: {
                        BrushBarViewBase.this.refreshBrushDatas();
                        break;
                    }
                }
            }
        };
        this.d = true;
    }
    
    public BrushBarViewBase(final Context context) {
        super(context);
        this.a = new BrushLocalPackage.BrushLocalPackageDelegate() {
            @Override
            public void onBrushPackageStatusChanged(final BrushLocalPackage brushLocalPackage, final TuSdkDownloadItem tuSdkDownloadItem, final DownloadTaskStatus downloadTaskStatus) {
                if (tuSdkDownloadItem == null || downloadTaskStatus == null) {
                    return;
                }
                switch (downloadTaskStatus.ordinal()) {
                    case 1:
                    case 2: {
                        BrushBarViewBase.this.refreshBrushDatas();
                        break;
                    }
                }
            }
        };
        this.d = true;
    }
    
    @Override
    public void loadView() {
        BrushLocalPackage.shared().appenDelegate(this.a);
    }
    
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        BrushLocalPackage.shared().removeDelegate(this.a);
    }

    public void loadBrushes() {
        if (!SdkValid.shared.smudgeEnabled()) {
            TLog.e("You are not allowed to use the smudge feature, please see http://tusdk.com", new Object[0]);
        } else {
            List var1;
            if (this.c != null && this.c.size() != 0) {
                var1 = BrushLocalPackage.shared().getBrushWithCodes(this.c);
                var1.add(0, BrushLocalPackage.shared().getEeaserBrush());
            } else {
                var1 = this.buildBrushItems();
            }

            BrushData var2 = this.a();
            int var3 = -1;
            if (var2 != null) {
                var3 = var1.indexOf(var2);
            }

            if ((var3 == -1 || var2.code.equals("Eraser")) && var1.size() > 1) {
                var2 = (BrushData)var1.get(1);
            }

            if (this.getTableView() != null) {
                ((BrushTableViewInterface)this.getTableView()).setModeList(var1);
                var3 = var1.indexOf(var2);
                ((BrushTableViewInterface)this.getTableView()).setSelectedPosition(var3, true);
                ((BrushTableViewInterface)this.getTableView()).scrollToPosition(var3);
            }

            this.notifySelectedBrush(var2);
        }
    }
    
    protected List<BrushData> buildBrushItems() {
        final ArrayList<BrushData> list = new ArrayList<BrushData>();
        list.add(BrushLocalPackage.shared().getEeaserBrush());
        final List<String> codes = BrushLocalPackage.shared().getCodes();
        if (codes != null && codes.size() > 0) {
            final Iterator<String> iterator = codes.iterator();
            while (iterator.hasNext()) {
                list.add(BrushLocalPackage.shared().getBrushWithCode(iterator.next()));
            }
        }
        return list;
    }

    public void selectBrush(BrushData var1, BrushBarItemCellBase var2, int var3) {
        ((BrushTableViewInterface)this.getTableView()).changeSelectedPosition(var3);
        ((BrushTableViewInterface)this.getTableView()).smoothScrollByCenter(var2);
        if (var1 != null) {
            this.a(var1);
        }

    }
    
    private BrushData a() {
        if (!this.d) {
            return null;
        }
        final String loadSharedCache = TuSdkContext.sharedPreferences().loadSharedCache(String.format("lsq_lastbrush_%s", this.b));
        if (loadSharedCache != null) {
            return BrushLocalPackage.shared().getBrushWithCode(loadSharedCache);
        }
        return null;
    }
    
    private void a(final BrushData brushData) {
        if (brushData == null) {
            return;
        }
        final String code = brushData.code;
        if (!this.d) {
            return;
        }
        TuSdkContext.sharedPreferences().saveSharedCache(String.format("lsq_lastbrush_%s", this.b), code);
    }
    
    public BrushTableViewInterface.BrushAction getAction() {
        return this.b;
    }
    
    public void setAction(final BrushTableViewInterface.BrushAction b) {
        this.b = b;
    }
    
    public List<String> getBrushGroup() {
        return this.c;
    }
    
    public void setBrushGroup(final List<String> c) {
        this.c = c;
    }
    
    public boolean isSaveLastBrush() {
        return this.d;
    }
    
    public void setSaveLastBrush(final boolean d) {
        this.d = d;
    }
}
