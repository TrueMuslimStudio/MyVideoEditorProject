// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.filter;

import android.view.ViewGroup;

import com.example.myvideoeditorapp.kore.activity.TuOnlineFragment;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.network.TuSdkDownloadItem;
import com.example.myvideoeditorapp.kore.network.TuSdkDownloadTask;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterLocalPackage;
import com.example.myvideoeditorapp.kore.type.DownloadTaskStatus;
import com.example.myvideoeditorapp.kore.view.widget.filter.GroupFilterItemViewInterface;


public abstract class TuFilterOnlineFragmentBase extends TuOnlineFragment
{
    private GroupFilterItemViewInterface.GroupFilterAction a;
    private FilterLocalPackage.FilterLocalPackageDelegate b;
    
     public TuFilterOnlineFragmentBase() {
            this.a = GroupFilterItemViewInterface.GroupFilterAction.ActionNormal;
            this.b = new FilterLocalPackage.FilterLocalPackageDelegate() {
                public void onFilterPackageStatusChanged(FilterLocalPackage var1, TuSdkDownloadItem var2, DownloadTaskStatus var3) {
                    TuFilterOnlineFragmentBase.this.notifyOnlineData(var2);
                }
            };
        }
    
    protected abstract void onHandleSelected(final long p0);
    
    protected abstract void onHandleDetail(final long p0);
    
    public GroupFilterItemViewInterface.GroupFilterAction getAction() {
        if (this.a == null) {
            this.a = GroupFilterItemViewInterface.GroupFilterAction.ActionNormal;
        }
        return this.a;
    }
    
    public void setAction(final GroupFilterItemViewInterface.GroupFilterAction a) {
        this.a = a;
    }
    
    @Override
    protected void loadView(final ViewGroup viewGroup) {
        this.getWebview();
        this.setOnlineType(TuSdkDownloadTask.DownloadTaskType.TypeFilter.getAct());
        this.setArgs("action=" + this.a.getValue());
        StatisticsManger.appendComponent(ComponentActType.editFilterOnlineFragment);
    }
    
    @Override
    protected void viewDidLoad(final ViewGroup viewGroup) {
        super.viewDidLoad(viewGroup);
        FilterLocalPackage.shared().appenDelegate(this.b);
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FilterLocalPackage.shared().removeDelegate(this.b);
    }
    
    @Override
    protected String getPageFinishedData() {
        return FilterLocalPackage.shared().getAllDatas().toString();
    }
    
    @Override
    protected void onResourceDownload(final long n, final String s, final String s2) {
        FilterLocalPackage.shared().download(n, s, s2);
    }
    
    @Override
    protected void onResourceCancelDownload(final long n) {
        FilterLocalPackage.shared().cancelDownload(n);
    }
    
    @Override
    protected void handleSelected(final String[] array) {
        if (array.length < 3) {
            return;
        }
        this.onHandleSelected(Long.parseLong(array[2]));
    }
    
    @Override
    protected void handleDetail(final String[] array) {
        if (array.length < 3) {
            return;
        }
        this.onHandleDetail(Long.parseLong(array[2]));
    }
}
