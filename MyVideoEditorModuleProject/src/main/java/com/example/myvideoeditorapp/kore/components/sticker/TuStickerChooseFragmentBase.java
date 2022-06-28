// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.sticker;

import android.view.ViewGroup;

import com.example.myvideoeditorapp.kore.activity.TuComponentFragment;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.network.TuSdkDownloadItem;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.type.DownloadTaskStatus;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerCategory;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerGroup;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerLocalPackage;

import java.util.List;

public abstract class TuStickerChooseFragmentBase extends TuComponentFragment
{
    private List<StickerCategory> a;
    private StickerLocalPackage.StickerPackageDelegate b;
    
    public TuStickerChooseFragmentBase() {
        this.b = new StickerLocalPackage.StickerPackageDelegate() {
            @Override
            public void onStickerPackageStatusChanged(final StickerLocalPackage stickerLocalPackage, final TuSdkDownloadItem tuSdkDownloadItem, final DownloadTaskStatus downloadTaskStatus) {
                if (tuSdkDownloadItem == null || downloadTaskStatus == null) {
                    return;
                }
                switch (downloadTaskStatus.ordinal()) {
                    case 1:
                    case 2: {
                        TuStickerChooseFragmentBase.this.reloadStickers();
                        break;
                    }
                }
            }
        };
    }
    
    public List<StickerCategory> getCategories() {
        if (this.a == null) {
            this.a = StickerLocalPackage.shared().getCategories();
        }
        return this.a;
    }
    
    public void setCategories(final List<StickerCategory> a) {
        this.a = a;
    }
    
    protected StickerCategory getCategory(final int n) {
        if (this.getCategories() == null || n < 0 || n >= this.a.size()) {
            return null;
        }
        return this.a.get(n);
    }
    
    @Override
    protected void loadView(final ViewGroup viewGroup) {
    }
    
    @Override
    protected void viewDidLoad(final ViewGroup viewGroup) {
        StickerLocalPackage.shared().appenDelegate(this.b);
        StatisticsManger.appendComponent(ComponentActType.editStickerLocalFragment);
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        StickerLocalPackage.shared().removeDelegate(this.b);
    }
    
    protected void removeStickerGroup(final StickerGroup stickerGroup) {
        if (stickerGroup == null) {
            return;
        }
        StickerLocalPackage.shared().removeDownloadWithIdt(stickerGroup.groupId);
    }
    
    protected void reloadStickers() {
        this.setCategories(StickerLocalPackage.shared().getCategories(this.getCategories()));
    }
}
