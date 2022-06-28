// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.secret;

import android.widget.ImageView;

import com.example.myvideoeditorapp.kore.seles.tusdk.FilterGroup;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterOption;
import com.example.myvideoeditorapp.kore.task.ImageViewTaskWare;

public class FilterThumbTaskImageWare extends ImageViewTaskWare
{
    public FilterGroup group;
    public FilterOption option;
    public FilterThumbTaskType taskType;
    
    public FilterThumbTaskImageWare(final ImageView imageView, final FilterThumbTaskType taskType, final FilterOption option, final FilterGroup group) {
        this.setImageView(imageView);
        this.taskType = taskType;
        this.option = option;
        this.group = group;
    }
    
    public enum FilterThumbTaskType
    {
        TypeGroupThumb, 
        TypeFilterThumb;
    }
}
