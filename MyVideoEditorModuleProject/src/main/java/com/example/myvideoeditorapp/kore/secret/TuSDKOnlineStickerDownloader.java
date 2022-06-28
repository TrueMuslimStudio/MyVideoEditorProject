// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.secret;

import com.example.myvideoeditorapp.kore.http.HttpHeader;
import com.example.myvideoeditorapp.kore.network.TuSdkDownloadItem;
import com.example.myvideoeditorapp.kore.network.TuSdkDownloadManger;
import com.example.myvideoeditorapp.kore.network.TuSdkDownloadTask;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpEngine;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpHandler;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpParams;
import com.example.myvideoeditorapp.kore.type.DownloadTaskStatus;
import com.example.myvideoeditorapp.kore.utils.json.JsonHelper;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerGroup;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerLocalPackage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TuSDKOnlineStickerDownloader implements StickerLocalPackage.StickerPackageDelegate
{
    private Set<Long> a;
    private TuSDKOnlineStickerDownloaderDelegate b;
    
    public TuSDKOnlineStickerDownloader() {
        this.a = new HashSet<Long>(10);
    }
    
    public TuSDKOnlineStickerDownloader setDelegate(final TuSDKOnlineStickerDownloaderDelegate b) {
        this.b = b;
        if (b == null) {
            StickerLocalPackage.shared().removeDelegate(this);
        }
        else {
            StickerLocalPackage.shared().appenDelegate(this);
        }
        return this;
    }
    
    public TuSDKOnlineStickerDownloaderDelegate getDelegate() {
        return this.b;
    }
    
    public final void downloadStickerGroup(final StickerGroup stickerGroup) {
        if (!SdkValid.shared.isVaild()) {
            return;
        }
        if (stickerGroup == null || this.isDownloaded(stickerGroup.groupId)) {
            return;
        }
        this.a(stickerGroup);
    }
    
    public final void removeStickerGroup(final long n) {
        if (!SdkValid.shared.isVaild()) {
            return;
        }
        StickerLocalPackage.shared().removeDownloadWithIdt(n);
    }
    
    public final boolean isDownloaded(final long n) {
        return SdkValid.shared.isVaild() && StickerLocalPackage.shared().containsGroupId(n);
    }
    
    public final boolean isDownloading(final long n) {
        return SdkValid.shared.isVaild() && TuSdkDownloadManger.ins.isDownloading(TuSdkDownloadTask.DownloadTaskType.TypeSticker, n);
    }
    
    public final boolean containsTask(final long l) {
        return SdkValid.shared.isVaild() && (this.a.contains(l) || TuSdkDownloadManger.ins.containsTask(TuSdkDownloadTask.DownloadTaskType.TypeSticker, l));
    }
    
    private void a(final StickerGroup stickerGroup) {
        final TuSdkHttpHandler tuSdkHttpHandler = new TuSdkHttpHandler() {
            @Override
            public void onSuccess(final int n, final List<HttpHeader> list, final String s) {
                TuSDKOnlineStickerDownloader.this.a.remove(stickerGroup.groupId);
                final JSONObject json = JsonHelper.json(s);
                if (json == null) {
                    return;
                }
                try {
                    StickerLocalPackage.shared().download(json.getLong("id"), json.getString("key"), json.getString("dl_key"));
                }
                catch (JSONException ex) {
                    ex.printStackTrace();
                    TuSDKOnlineStickerDownloader.this.a(stickerGroup.groupId);
                }
            }
            
            @Override
            protected void onRequestedSucceed(final TuSdkHttpHandler tuSdkHttpHandler) {
                TuSDKOnlineStickerDownloader.this.a.remove(stickerGroup.groupId);
            }
            
            @Override
            protected void onRequestedFailed(final TuSdkHttpHandler tuSdkHttpHandler) {
                TuSDKOnlineStickerDownloader.this.a(stickerGroup.groupId);
                TuSDKOnlineStickerDownloader.this.a.remove(stickerGroup.groupId);
            }
        };
        this.a.add(stickerGroup.groupId);
        final TuSdkHttpParams tuSdkHttpParams = new TuSdkHttpParams();
        tuSdkHttpParams.add("id", String.valueOf(stickerGroup.groupId));
        tuSdkHttpParams.add("devid", TuSdkHttpEngine.shared().getDevId());
        TuSdkHttpEngine.webAPIEngine().post("/sticker/validKey", tuSdkHttpParams, true, tuSdkHttpHandler);
    }
    
    @Override
    public void onStickerPackageStatusChanged(final StickerLocalPackage stickerLocalPackage, final TuSdkDownloadItem tuSdkDownloadItem, final DownloadTaskStatus downloadTaskStatus) {
        if (this.b == null) {
            return;
        }
        this.b.onDownloadProgressChanged(tuSdkDownloadItem.id, tuSdkDownloadItem.progress, downloadTaskStatus);
    }
    
    private void a(final long n) {
        if (this.b == null) {
            return;
        }
        this.b.onDownloadProgressChanged(n, 0.0f, DownloadTaskStatus.StatusDownFailed);
    }
    
    public void destroy() {
        this.b = null;
    }
    
    public interface TuSDKOnlineStickerDownloaderDelegate
    {
        void onDownloadProgressChanged(final long p0, final float p1, final DownloadTaskStatus p2);
    }
}
