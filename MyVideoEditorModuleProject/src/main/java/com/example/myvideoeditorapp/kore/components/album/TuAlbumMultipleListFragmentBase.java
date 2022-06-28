// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.album;

import android.view.ViewGroup;

import com.example.myvideoeditorapp.kore.activity.TuComponentFragment;
import com.example.myvideoeditorapp.kore.activity.TuFragment;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.task.AlbumTaskManager;
import com.example.myvideoeditorapp.kore.utils.ReflectUtils;
import com.example.myvideoeditorapp.kore.utils.sqllite.AlbumSqlInfo;

import java.util.ArrayList;
import java.util.List;

public abstract class TuAlbumMultipleListFragmentBase extends TuComponentFragment
{
    private String a;
    
    public String getSkipAlbumName() {
        return this.a;
    }
    
    public void setSkipAlbumName(final String a) {
        this.a = a;
    }
    
    public abstract List<AlbumSqlInfo> getGroups();
    
    public abstract void notifySelectedGroup(final AlbumSqlInfo p0);
    
    public abstract Class<?> getPreviewFragmentClazz();
    
    public abstract int getPreviewFragmentLayoutId();
    
    @Override
    protected void loadView(final ViewGroup viewGroup) {
    }
    
    @Override
    protected void viewDidLoad(final ViewGroup viewGroup) {
        StatisticsManger.appendComponent(ComponentActType.albumMultipleListFragment);
    }
    
    @Override
    public void onDestroyView() {
        AlbumTaskManager.shared.resetQueues();
        super.onDestroyView();
    }
    
    public void autoSelectedAblumGroupAction(final ArrayList<AlbumSqlInfo> list) {
        if (list == null) {
            return;
        }
        AlbumSqlInfo albumSqlInfo = null;
        for (final AlbumSqlInfo albumSqlInfo2 : list) {
            if (albumSqlInfo2.total < 1) {
                continue;
            }
            if (this.a != null && albumSqlInfo2.title.equalsIgnoreCase(this.a)) {
                albumSqlInfo = albumSqlInfo2;
                break;
            }
        }
        if (albumSqlInfo == null && list.size() > 0) {
            albumSqlInfo = list.get(0);
        }
        this.notifySelectedGroup(albumSqlInfo);
    }
    
    protected <T extends TuFragment> T getPreviewFragmentInstance() {
        final TuFragment tuFragment = ReflectUtils.classInstance(this.getPreviewFragmentClazz());
        if (tuFragment == null) {
            return null;
        }
        tuFragment.setRootViewLayoutId(this.getPreviewFragmentLayoutId());
        return (T)tuFragment;
    }
}
