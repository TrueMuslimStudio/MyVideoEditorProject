// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk;

import java.util.Iterator;
import java.util.Collection;
import com.example.myvideoeditorapp.kore.type.DownloadTaskStatus;
import com.example.myvideoeditorapp.kore.network.TuSdkDownloadItem;
import com.example.myvideoeditorapp.kore.http.ResponseHandlerInterface;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpEngine;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpParams;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import org.json.JSONObject;
import org.json.JSONException;
import com.example.myvideoeditorapp.kore.utils.json.JsonHelper;
import com.example.myvideoeditorapp.kore.http.HttpHeader;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpHandler;
import com.example.myvideoeditorapp.kore.utils.TLog;
import android.widget.ImageView;

import com.example.myvideoeditorapp.kore.TuSdkConfigs;
import com.example.myvideoeditorapp.kore.http.HttpHeader;
import com.example.myvideoeditorapp.kore.network.TuSdkDownloadItem;
import com.example.myvideoeditorapp.kore.network.TuSdkDownloadManger;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpEngine;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpHandler;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpParams;
import com.example.myvideoeditorapp.kore.secret.FilterAdapter;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import com.example.myvideoeditorapp.kore.seles.sources.SelesPicture;
import com.example.myvideoeditorapp.kore.type.DownloadTaskStatus;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.utils.json.JsonHelper;

import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import com.example.myvideoeditorapp.kore.seles.sources.SelesPicture;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import java.util.ArrayList;
import com.example.myvideoeditorapp.kore.TuSdkConfigs;
import java.util.List;
import com.example.myvideoeditorapp.kore.secret.FilterAdapter;
import com.example.myvideoeditorapp.kore.network.TuSdkDownloadManger;

public class FilterLocalPackage implements TuSdkDownloadManger.TuSdkDownloadMangerDelegate
{
    public static final String NormalFilterCode = "Normal";
    private static FilterLocalPackage a;
    private FilterAdapter b;
    private List<FilterLocalPackageDelegate> c;
    
    public static FilterLocalPackage shared() {
        return FilterLocalPackage.a;
    }
    
    public static FilterLocalPackage init(final TuSdkConfigs tuSdkConfigs) {
        if (FilterLocalPackage.a == null && tuSdkConfigs != null) {
            FilterLocalPackage.a = new FilterLocalPackage(tuSdkConfigs);
        }
        return FilterLocalPackage.a;
    }
    
    public void appenDelegate(final FilterLocalPackageDelegate filterLocalPackageDelegate) {
        if (filterLocalPackageDelegate == null || this.c.contains(filterLocalPackageDelegate)) {
            return;
        }
        this.c.add(filterLocalPackageDelegate);
    }
    
    public void removeDelegate(final FilterLocalPackageDelegate filterLocalPackageDelegate) {
        if (filterLocalPackageDelegate == null) {
            return;
        }
        this.c.remove(filterLocalPackageDelegate);
    }
    
    private FilterLocalPackage(final TuSdkConfigs tuSdkConfigs) {
        this.c = new ArrayList<FilterLocalPackageDelegate>();
        (this.b = new FilterAdapter(tuSdkConfigs)).setDownloadDelegate(this);
    }
    
    public List<String> getCodes() {
        return this.b.getCodes();
    }
    
    public void setInitDelegate(final FilterAdapter.FiltersConfigDelegate initDelegate) {
        this.b.setInitDelegate(initDelegate);
    }
    
    public boolean isInited() {
        return this.b.isInited();
    }
    
    public List<String> verifyCodes(final List<String> list) {
        return this.b.verifyCodes(list);
    }
    
    public List<FilterOption> getFilters(final List<String> list) {
        return this.b.getFilters(list);
    }
    
    public List<FilterOption> getGroupFilters(final FilterGroup filterGroup) {
        return this.b.getGroupFilters(filterGroup);
    }
    
    public FilterGroup getFilterGroup(final long n) {
        return this.b.getFilterGroup(n);
    }
    
    public String getGroupNameKey(final long n) {
        return this.b.getGroupNameKey(n);
    }
    
    public int getGroupType(final long n) {
        return this.b.getGroupType(n);
    }
    
    public int getGroupFiltersType(final long n) {
        return this.b.getGroupFiltersType(n);
    }
    
    public List<FilterGroup> getGroups() {
        return this.b.getGroups();
    }
    
    public List<FilterGroup> getLiveGroups() {
        return this.b.getLiveGroups();
    }
    
    public List<FilterGroup> getGroupsByAtionScen(final int n) {
        return this.b.getGroupsByAtionScen(n);
    }
    
    public String getGroupDefaultFilterCode(final FilterGroup filterGroup) {
        return this.b.getGroupDefaultFilterCode(filterGroup);
    }
    
    public FilterOption option(final String s) {
        return this.b.option(s);
    }
    
    public FilterWrap getFilterWrap(final String s) {
        final FilterOption option = this.option(s);
        final FilterWrap creat = FilterWrap.creat(option);
        StatisticsManger.appendFilter(option);
        return creat;
    }
    
    public List<SelesPicture> loadTextures(final String s) {
        return this.b.loadTextures(s);
    }
    
    public List<SelesPicture> loadInternalTextures(final List<String> list) {
        return this.b.loadInternalTextures(list);
    }
    
    public SelesOutInput createFilter(final FilterOption filterOption) {
        return this.b.createFilter(filterOption);
    }
    
    public void loadGroupThumb(final ImageView imageView, final FilterGroup filterGroup) {
        this.b.loadGroupThumb(imageView, filterGroup);
    }
    
    public void loadGroupDefaultFilterThumb(final ImageView imageView, final FilterGroup filterGroup) {
        this.b.loadGroupDefaultFilterThumb(imageView, filterGroup);
    }
    
    public void loadFilterThumb(final ImageView imageView, final FilterOption filterOption) {
        this.b.loadFilterThumb(imageView, filterOption);
    }
    
    public void cancelLoadImage(final ImageView imageView) {
        this.b.cancelLoadImage(imageView);
    }
    
    public void asyncAppendFilterGroup(final String s, final String s2, final int n, final FilterLocalPackageAppendFileDelegate filterLocalPackageAppendFileDelegate) {
        if (s == null || s2 == null) {
            TLog.e("Parameter file and filterGroupId is invalid.", new Object[0]);
            this.a(filterLocalPackageAppendFileDelegate, s, false);
            return;
        }
        if (this.b.containsGroupId(Long.parseLong(s2))) {
            TLog.e("The filter file is invalid or already exists. [ %s ]", s);
            this.a(filterLocalPackageAppendFileDelegate, s, false);
            return;
        }
        this.a(s2, new TuSdkHttpHandler() {
            @Override
            public void onSuccess(final int n, final List<HttpHeader> list, final String s) {
                final JSONObject json = JsonHelper.json(s);
                if (json == null) {
                    FilterLocalPackage.this.a(filterLocalPackageAppendFileDelegate, s, false);
                    return;
                }
                try {
                    FilterLocalPackage.this.a(filterLocalPackageAppendFileDelegate, s, FilterLocalPackage.this.b.appendFilterGroup(s, json.getString("key"), n));
                }
                catch (JSONException ex) {
                    ex.printStackTrace();
                    TLog.e("Please check that the filter id is valid. [ %s ]", s2);
                    FilterLocalPackage.this.a(filterLocalPackageAppendFileDelegate, s, false);
                }
            }
            
            @Override
            protected void onRequestedSucceed(final TuSdkHttpHandler tuSdkHttpHandler) {
            }
            
            @Override
            protected void onRequestedFailed(final TuSdkHttpHandler tuSdkHttpHandler) {
                super.onRequestedFailed(tuSdkHttpHandler);
                TLog.i("Please check the network status. [ %s ]", tuSdkHttpHandler.getError());
                FilterLocalPackage.this.a(filterLocalPackageAppendFileDelegate, s, false);
            }
        });
    }
    
    private void a(final FilterLocalPackageAppendFileDelegate filterLocalPackageAppendFileDelegate, final String s, final boolean b) {
        if (filterLocalPackageAppendFileDelegate == null) {
            return;
        }
        ThreadHelper.post(new Runnable() {
            @Override
            public void run() {
                filterLocalPackageAppendFileDelegate.onFilterLocalFileAppend(s, b);
            }
        });
    }
    
    private void a(final String s, final TuSdkHttpHandler tuSdkHttpHandler) {
        final TuSdkHttpParams tuSdkHttpParams = new TuSdkHttpParams();
        tuSdkHttpParams.add("id", s);
        tuSdkHttpParams.add("devid", TuSdkHttpEngine.shared().getDevId());
        TuSdkHttpEngine.webAPIEngine().post("/filter/validKey", tuSdkHttpParams, true, tuSdkHttpHandler);
    }
    
    public void download(final long n, final String s, final String s2) {
        this.b.download(n, s, s2);
    }
    
    public void cancelDownload(final long n) {
        this.b.cancelDownload(n);
    }
    
    public void removeDownloadWithIdt(final long n) {
        this.b.removeDownloadWithIdt(n);
    }
    
    public JSONObject getAllDatas() {
        return this.b.getAllDatas();
    }
    
    @Override
    public void onDownloadMangerStatusChanged(final TuSdkDownloadManger tuSdkDownloadManger, final TuSdkDownloadItem tuSdkDownloadItem, final DownloadTaskStatus downloadTaskStatus) {
        final Iterator<FilterLocalPackageDelegate> iterator = new ArrayList<FilterLocalPackageDelegate>(this.c).iterator();
        while (iterator.hasNext()) {
            iterator.next().onFilterPackageStatusChanged(this, tuSdkDownloadItem, downloadTaskStatus);
        }
    }
    
    public interface FilterLocalPackageAppendFileDelegate
    {
        void onFilterLocalFileAppend(final String p0, final boolean p1);
    }
    
    public interface FilterLocalPackageDelegate
    {
        void onFilterPackageStatusChanged(final FilterLocalPackage p0, final TuSdkDownloadItem p1, final DownloadTaskStatus p2);
    }
}
