// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.secret;

import android.text.TextUtils;

import com.example.myvideoeditorapp.kore.http.HttpHeader;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpEngine;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpHandler;
import com.example.myvideoeditorapp.kore.utils.ReflectUtils;
import com.example.myvideoeditorapp.kore.utils.json.JsonHelper;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TuSDKOnlineStickerFetcher
{
    private TuSDKOnlineStickerFetcherDelegate a;
    private TuSdkHttpHandler b;
    
    public TuSDKOnlineStickerFetcher() {
        this.b = new TuSdkHttpHandler() {
            @Override
            public void onSuccess(final int n, final List<HttpHeader> list, final String s) {
                try {
                    if (TextUtils.isEmpty((CharSequence)s)) {
                        TuSDKOnlineStickerFetcher.this.a();
                        return;
                    }
                    final JSONArray jsonArray = new JSONArray(s);
                    final ArrayList list2 = new ArrayList<StickerGroup>(jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        final JSONObject jsonObject = JsonHelper.getJSONObject(jsonArray, i);
                        if (jsonObject != null) {
                            final StickerGroup e = ReflectUtils.classInstance(StickerGroup.class);
                            if (e != null) {
                                e.setJson(jsonObject);
                                list2.add(e);
                            }
                        }
                    }
                    TuSDKOnlineStickerFetcher.this.a(list2);
                }
                catch (JSONException ex) {
                    TuSDKOnlineStickerFetcher.this.a();
                }
            }
            
            @Override
            public void onFailure(final int n, final List<HttpHeader> list, final String s, final Throwable t) {
                TuSDKOnlineStickerFetcher.this.a();
            }
            
            @Override
            protected void onRequestedSucceed(final TuSdkHttpHandler tuSdkHttpHandler) {
            }
        };
    }
    
    public TuSDKOnlineStickerFetcher setDelegate(final TuSDKOnlineStickerFetcherDelegate a) {
        this.a = a;
        return this;
    }
    
    public TuSDKOnlineStickerFetcherDelegate getDelegate() {
        return this.a;
    }
    
    public final void fetchStickerGroupWithCursor(final int n, final boolean b) {
        if (!SdkValid.shared.isVaild()) {
            return;
        }
        TuSdkHttpEngine.webAPIEngine().get(this.a(n, b), null, true, this.b);
    }
    
    private String a(final int i, final boolean b) {
        final StringBuilder sb = new StringBuilder("/stickerGroup/index");
        sb.append("?id=1").append("&devid=").append(TuSdkHttpEngine.shared().getDevId()).append("&uuid=").append(TuSdkHttpEngine.shared().uniqueDeviceID());
        if (i > 0) {
            sb.append("&cursor=").append(i);
        }
        if (b) {
            sb.append("&is_smart=1");
        }
        return sb.toString();
    }
    
    private void a(final List<StickerGroup> list) {
        if (this.a == null) {
            return;
        }
        this.a.onFetchCompleted(list);
    }
    
    private void a() {
        if (this.a == null) {
            return;
        }
        this.a.onFetchFailed();
    }
    
    public interface TuSDKOnlineStickerFetcherDelegate
    {
        void onFetchFailed();
        
        void onFetchCompleted(final List<StickerGroup> p0);
    }
}
