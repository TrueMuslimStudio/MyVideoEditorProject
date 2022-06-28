// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.album;

import android.view.ViewGroup;

import com.example.myvideoeditorapp.kore.activity.TuComponentFragment;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.task.AlbumTaskManager;
import com.example.myvideoeditorapp.kore.utils.sqllite.AlbumSqlInfo;

import java.util.List;

public abstract class TuAlbumListFragmentBase extends TuComponentFragment
{
    private boolean a;
    private String b;
    
    public boolean isDisableAutoSkipToPhotoList() {
        return this.a;
    }
    
    public void setDisableAutoSkipToPhotoList(final boolean a) {
        this.a = a;
    }
    
    public String getSkipAlbumName() {
        return this.b;
    }
    
    public void setSkipAlbumName(final String b) {
        this.b = b;
        if (b != null) {
            this.setDisableAutoSkipToPhotoList(false);
        }
    }
    
    public abstract List<AlbumSqlInfo> getGroups();
    
    public abstract void notifySelectedGroup(final AlbumSqlInfo p0);
    
    @Override
    protected void loadView(final ViewGroup viewGroup) {
    }
    
    @Override
    protected void viewDidLoad(final ViewGroup viewGroup) {
        StatisticsManger.appendComponent(ComponentActType.albumListFragment);
    }
    
    @Override
    public void onDestroyView() {
        AlbumTaskManager.shared.resetQueues();
        super.onDestroyView();
    }
    
    protected void autoSelectedAblumGroupAction(final List<AlbumSqlInfo> list) {
        if (this.isDisableAutoSkipToPhotoList() || list == null) {
            return;
        }
        AlbumSqlInfo albumSqlInfo = null;
        for (final AlbumSqlInfo albumSqlInfo2 : list) {
            if (albumSqlInfo2.total < 1) {
                continue;
            }
            if (this.getSkipAlbumName() != null && albumSqlInfo2.title.equalsIgnoreCase(this.getSkipAlbumName())) {
                albumSqlInfo = albumSqlInfo2;
                break;
            }
            if (!"Camera".equalsIgnoreCase(albumSqlInfo2.title) || (albumSqlInfo != null && albumSqlInfo.total >= albumSqlInfo2.total)) {
                continue;
            }
            albumSqlInfo = albumSqlInfo2;
        }
        if (albumSqlInfo != null) {
            this.notifySelectedGroup(albumSqlInfo);
        }
    }
}
