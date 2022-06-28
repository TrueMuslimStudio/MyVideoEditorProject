// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.sticker;

import android.view.ViewGroup;

import com.example.myvideoeditorapp.kore.activity.TuOnlineFragment;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.network.TuSdkDownloadItem;
import com.example.myvideoeditorapp.kore.network.TuSdkDownloadTask;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.type.DownloadTaskStatus;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerData;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerLocalPackage;

public abstract class TuStickerOnlineFragmentBase extends TuOnlineFragment
{
    private StickerLocalPackage.StickerPackageDelegate a = new StickerLocalPackage.StickerPackageDelegate() {
        public void onStickerPackageStatusChanged(StickerLocalPackage var1, TuSdkDownloadItem var2, DownloadTaskStatus var3) {
            TuStickerOnlineFragmentBase.this.notifyOnlineData(var2);
        }
    };

    public TuStickerOnlineFragmentBase() {
    }
    
    protected abstract void onHandleSelected(final StickerData p0);
    
    protected abstract void onHandleDetail(final long p0);
    
    @Override
    protected void loadView(final ViewGroup viewGroup) {
        this.getWebview();
        this.setOnlineType(TuSdkDownloadTask.DownloadTaskType.TypeSticker.getAct());
        StatisticsManger.appendComponent(ComponentActType.editStickerOnlineFragment);
    }
    
    @Override
    protected void viewDidLoad(final ViewGroup viewGroup) {
        super.viewDidLoad(viewGroup);
        StickerLocalPackage.shared().appenDelegate(this.a);
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        StickerLocalPackage.shared().removeDelegate(this.a);
    }
    
    @Override
    protected String getPageFinishedData() {
        return StickerLocalPackage.shared().getAllDatas().toString();
    }
    
    @Override
    protected void onResourceDownload(final long n, final String s, final String s2) {
        StickerLocalPackage.shared().download(n, s, s2);
    }
    
    @Override
    protected void onResourceCancelDownload(final long n) {
        StickerLocalPackage.shared().cancelDownload(n);
    }
    
    @Override
    protected void handleSelected(final String[] array) {
        if (array.length < 4) {
            return;
        }
        final StickerData sticker = StickerLocalPackage.shared().getSticker(Long.parseLong(array[3]));
        if (sticker == null) {
            return;
        }
        this.onHandleSelected(sticker);
    }
    
    @Override
    protected void handleDetail(final String[] array) {
        if (array.length < 3) {
            return;
        }
        this.onHandleDetail(Long.parseLong(array[2]));
    }
}
