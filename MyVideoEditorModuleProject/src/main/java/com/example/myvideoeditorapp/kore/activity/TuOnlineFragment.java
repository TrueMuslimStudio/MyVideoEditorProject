// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.activity;

import android.annotation.SuppressLint;
import android.webkit.JavascriptInterface;
import com.example.myvideoeditorapp.kore.network.TuSdkDownloadItem;
import com.example.myvideoeditorapp.kore.type.OnlineCommandAction;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpEngine;
import com.example.myvideoeditorapp.kore.utils.StringHelper;
import com.example.myvideoeditorapp.kore.view.widget.TuSdkNavigatorBar;
import android.view.ViewGroup;
import android.graphics.Bitmap;

import com.example.myvideoeditorapp.kore.network.TuSdkDownloadItem;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpEngine;
import com.example.myvideoeditorapp.kore.type.OnlineCommandAction;
import com.example.myvideoeditorapp.kore.utils.StringHelper;
import com.example.myvideoeditorapp.kore.view.TuSdkWebView;
import com.example.myvideoeditorapp.kore.view.widget.TuSdkNavigatorBar;

import com.example.myvideoeditorapp.kore.view.TuSdkWebView;

public abstract class TuOnlineFragment extends TuFragment
{
    private long a;
    private String b;
    private String c;
    private boolean d;
    private TuSdkWebView.TuSdkWebViewAdapter e;
    
    public TuOnlineFragment() {
        this.e = new TuSdkWebView.TuSdkWebViewAdapter() {
            @Override
            public void onPageStarted(final TuSdkWebView tuSdkWebView, final String s, final Bitmap bitmap) {
                TuOnlineFragment.this.d = false;
            }
            
            @Override
            public void onPageFinished(final TuSdkWebView tuSdkWebView, final String s) {
                TuOnlineFragment.this.d = true;
                tuSdkWebView.setWebPageUrl("javascript:clientBridge.getHandlers().onTuSdkSend(" + TuOnlineFragment.this.getPageFinishedData() + ");");
            }
            
            @Override
            public void onReceivedTitle(final TuSdkWebView tuSdkWebView, final String title) {
                if (title == null) {
                    return;
                }
                TuOnlineFragment.this.setTitle(title);
            }
        };
    }
    
    public abstract TuSdkWebView getWebview();
    
    public long getDetailDataId() {
        return this.a;
    }
    
    public void setDetailDataId(final long a) {
        this.a = a;
    }
    
    public String getArgs() {
        return this.c;
    }
    
    public void setArgs(final String c) {
        this.c = c;
    }
    
    public String getOnlineType() {
        return this.b;
    }
    
    public void setOnlineType(final String b) {
        this.b = b;
    }
    
    @Override
    protected void viewDidLoad(final ViewGroup viewGroup) {
        this.a(this.getWebview());
    }
    
    @Override
    protected void navigatorBarLoaded(final TuSdkNavigatorBar tuSdkNavigatorBar) {
        super.navigatorBarLoaded(tuSdkNavigatorBar);
        this.setIsSupportSlideBack(false);
    }
    
    @Override
    public void navigatorBarRightAction(final TuSdkNavigatorBar.NavigatorBarButtonInterface navigatorBarButtonInterface) {
        this.dismissActivityWithAnim();
    }
    
    @SuppressLint("JavascriptInterface")
    private void a(final TuSdkWebView tuSdkWebView) {
        if (tuSdkWebView == null) {
            return;
        }
        tuSdkWebView.setAdapter(this.e);
        String s;
        if (this.getDetailDataId() > 0L) {
            s = String.format("/%s/item?id=%s", this.getOnlineType(), this.getDetailDataId());
        }
        else if (!StringHelper.isBlank(this.getArgs())) {
            s = String.format("/%s/index?%s", this.getOnlineType(), this.getArgs());
        }
        else {
            s = String.format("/%s/index", this.getOnlineType());
        }
        tuSdkWebView.setWebPageUrl(TuSdkHttpEngine.shared().getWebUrl(s, true));
        tuSdkWebView.addJavascriptInterface((Object)new TuSdkOnlineInteface(), "tusdkBridge");
    }
    
    protected abstract String getPageFinishedData();

    private void a(String var1) {
        if (!StringHelper.isEmpty(var1)) {
            String[] var2 = var1.split("/");
            if (var2.length >= 2 && this.getOnlineType().equalsIgnoreCase(var2[0])) {
                OnlineCommandAction var3 = OnlineCommandAction.getType(Integer.parseInt(var2[1]));
                switch(var3) {
                    case ActionDefault:
                        this.handleDownload(var2);
                        break;
                    case ActionCancel:
                        this.handleCancel(var2);
                        break;
                    case ActionSelect:
                        this.handleSelected(var2);
                        break;
                    case ActionDetail:
                        this.handleDetail(var2);
                }

            }
        }
    }
    
    protected void handleDownload(final String[] array) {
        if (array.length < 5) {
            return;
        }
        this.onResourceDownload(Long.parseLong(array[2]), array[3], array[4]);
    }
    
    protected abstract void onResourceDownload(final long p0, final String p1, final String p2);
    
    protected void handleCancel(final String[] array) {
        if (array.length < 3) {
            return;
        }
        this.onResourceCancelDownload(Long.parseLong(array[2]));
    }
    
    protected abstract void onResourceCancelDownload(final long p0);
    
    protected void handleSelected(final String[] array) {
    }
    
    protected void handleDetail(final String[] array) {
    }
    
    protected void notifyOnlineData(final TuSdkDownloadItem tuSdkDownloadItem) {
        if (tuSdkDownloadItem == null || !this.d) {
            return;
        }
        this.getWebview().setWebPageUrl("javascript:clientBridge.getHandlers().onTuSdkSend(" + tuSdkDownloadItem.getStatusChangeData().toString() + ");");
    }
    
    private class TuSdkOnlineInteface
    {
        @JavascriptInterface
        public void onTuSdkPush(final String s) {
            TuOnlineFragment.this.a(s);
        }
    }
}
