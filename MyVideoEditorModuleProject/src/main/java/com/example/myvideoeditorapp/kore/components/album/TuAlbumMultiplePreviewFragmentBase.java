// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.album;

import android.view.ViewGroup;

import com.example.myvideoeditorapp.kore.activity.TuFragment;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;

public class TuAlbumMultiplePreviewFragmentBase extends TuFragment
{
    @Override
    protected void loadView(final ViewGroup viewGroup) {
    }
    
    @Override
    protected void viewDidLoad(final ViewGroup viewGroup) {
        StatisticsManger.appendComponent(ComponentActType.photoListPreviewFragment);
    }
}
