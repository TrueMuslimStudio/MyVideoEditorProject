// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.filter;

import com.example.myvideoeditorapp.kore.view.widget.filter.GroupFilterItemViewInterface;


public interface TuFilterOnlineFragmentInterface
{
    void setDelegate(final TuFilterOnlineFragmentDelegate p0);
    
    void setAction(final GroupFilterItemViewInterface.GroupFilterAction p0);
    
    void setDetailDataId(final long p0);
    
    public interface TuFilterOnlineFragmentDelegate
    {
        void onTuFilterOnlineFragmentSelected(final TuFilterOnlineFragmentInterface p0, final long p1);
    }
}
