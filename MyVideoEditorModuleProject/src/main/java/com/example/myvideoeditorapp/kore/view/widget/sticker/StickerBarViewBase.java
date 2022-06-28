// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget.sticker;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import com.example.myvideoeditorapp.kore.type.DownloadTaskStatus;
import com.example.myvideoeditorapp.kore.network.TuSdkDownloadItem;
import android.util.AttributeSet;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import java.util.List;
import com.example.myvideoeditorapp.kore.view.TuSdkLinearLayout;

public abstract class StickerBarViewBase extends TuSdkLinearLayout
{
    private StickerLocalPackage.StickerPackageDelegate a;
    private List<StickerCategory> b;
    private int c;
    
    protected abstract View buildCateButton(final StickerCategory p0, final int p1, final LayoutParams p2);
    
    protected abstract void selectCateButton(final Integer p0);
    
    protected abstract void refreshCateDatas();
    
    public StickerBarViewBase(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.a = new StickerLocalPackage.StickerPackageDelegate() {
            @Override
            public void onStickerPackageStatusChanged(final StickerLocalPackage stickerLocalPackage, final TuSdkDownloadItem tuSdkDownloadItem, final DownloadTaskStatus downloadTaskStatus) {
                if (tuSdkDownloadItem == null || downloadTaskStatus == null) {
                    return;
                }
                switch (downloadTaskStatus.ordinal()) {
                    case 1:
                    case 2: {
                        StickerBarViewBase.this.refreshCateDatas();
                        break;
                    }
                }
            }
        };
        this.c = -1;
    }
    
    public StickerBarViewBase(final Context context, final AttributeSet set) {
        super(context, set);
        this.a = new StickerLocalPackage.StickerPackageDelegate() {
            @Override
            public void onStickerPackageStatusChanged(final StickerLocalPackage stickerLocalPackage, final TuSdkDownloadItem tuSdkDownloadItem, final DownloadTaskStatus downloadTaskStatus) {
                if (tuSdkDownloadItem == null || downloadTaskStatus == null) {
                    return;
                }
                switch (downloadTaskStatus.ordinal()) {
                    case 1:
                    case 2: {
                        StickerBarViewBase.this.refreshCateDatas();
                        break;
                    }
                }
            }
        };
        this.c = -1;
    }
    
    public StickerBarViewBase(final Context context) {
        super(context);
        this.a = new StickerLocalPackage.StickerPackageDelegate() {
            @Override
            public void onStickerPackageStatusChanged(final StickerLocalPackage stickerLocalPackage, final TuSdkDownloadItem tuSdkDownloadItem, final DownloadTaskStatus downloadTaskStatus) {
                if (tuSdkDownloadItem == null || downloadTaskStatus == null) {
                    return;
                }
                switch (downloadTaskStatus.ordinal()) {
                    case 1:
                    case 2: {
                        StickerBarViewBase.this.refreshCateDatas();
                        break;
                    }
                }
            }
        };
        this.c = -1;
    }
    
    @Override
    public void loadView() {
        StickerLocalPackage.shared().appenDelegate(this.a);
    }
    
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        StickerLocalPackage.shared().removeDelegate(this.a);
    }
    
    public void loadCategories(List<StickerCategory> categories) {
        this.b = new ArrayList<StickerCategory>();
        final StickerCategory stickerCategory = new StickerCategory();
        stickerCategory.name = "lsq_sticker_cate_all";
        stickerCategory.extendType = StickerCategory.StickerCategoryExtendType.ExtendTypeAll;
        this.b.add(stickerCategory);
        if (categories == null || categories.size() == 0) {
            categories = StickerLocalPackage.shared().getCategories();
        }
        this.b.addAll(categories);
        this.a();
        this.selectCateIndex(0);
    }
    
    private void a() {
        if (this.b == null) {
            return;
        }
        final LayoutParams layoutParams = new LayoutParams(0, -1, 1.0f);
        int n = 0;
        final Iterator<StickerCategory> iterator = this.b.iterator();
        while (iterator.hasNext()) {
            this.buildCateButton(iterator.next(), n, layoutParams);
            ++n;
        }
    }
    
    protected void selectCateIndex(final int n) {
        if (this.c == n || this.b == null || this.b.size() <= n) {
            return;
        }
        this.c = n;
        this.selectCateButton(n);
        this.refreshCateDatas();
    }
    
    protected StickerCategory getCurrentCate() {
        if (this.c < 0 || this.b == null || this.b.size() <= this.c) {
            return null;
        }
        return this.b.get(this.c);
    }

    protected List<StickerData> getStickerDatas(long var1) {
        StickerCategory var3 = StickerLocalPackage.shared().getCategory(var1);
        if (var3 != null && var3.datas != null) {
            ArrayList var4 = new ArrayList();
            Iterator var5 = var3.datas.iterator();

            while(var5.hasNext()) {
                StickerGroup var6 = (StickerGroup)var5.next();
                if (var6.stickers != null) {
                    var4.addAll(var6.stickers);
                }
            }

            return var4;
        } else {
            return null;
        }
    }

    protected List<StickerData> getAllStickerDatas() {
        if (this.b == null) {
            return null;
        } else {
            ArrayList var1 = new ArrayList();
            Iterator var2 = this.b.iterator();

            while(var2.hasNext()) {
                StickerCategory var3 = (StickerCategory)var2.next();
                if (var3.extendType == null) {
                    List var4 = this.getStickerDatas(var3.id);
                    if (var4 != null) {
                        var1.addAll(var4);
                    }
                }
            }

            return var1;
        }
    }
}
