// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.album;

import android.view.ViewGroup;

import com.example.myvideoeditorapp.kore.activity.TuComponentFragment;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.utils.sqllite.AlbumSqlInfo;
import com.example.myvideoeditorapp.kore.utils.sqllite.ImageSqlInfo;

public abstract class TuPhotoListFragmentBase extends TuComponentFragment
{
    private AlbumSqlInfo a;
    
    public AlbumSqlInfo getAlbumInfo() {
        return this.a;
    }
    
    public void setAlbumInfo(final AlbumSqlInfo a) {
        this.a = a;
    }
    
    public abstract void notifySelectedPhoto(final ImageSqlInfo p0);
    
    @Override
    protected void loadView(final ViewGroup viewGroup) {
    }
    
    @Override
    protected void viewDidLoad(final ViewGroup viewGroup) {
        StatisticsManger.appendComponent(ComponentActType.photoListFragment);
    }
}
