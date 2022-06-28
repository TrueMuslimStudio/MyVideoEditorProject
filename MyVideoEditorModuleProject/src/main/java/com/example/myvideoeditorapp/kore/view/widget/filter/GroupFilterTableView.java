// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget.filter;

import android.view.View;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import com.example.myvideoeditorapp.kore.task.FilterTaskInterface;
import com.example.myvideoeditorapp.kore.view.recyclerview.TuSdkTableView;

public class GroupFilterTableView extends TuSdkTableView<GroupFilterItem, GroupFilterItemViewBase> implements GroupFilterTableViewInterface
{
    private GroupFilterItemViewInterface.GroupFilterAction a;
    private boolean b;
    private int c;
    private GroupFilterGroupViewBase.GroupFilterGroupViewDelegate d;
    private FilterTaskInterface e;
    
    public GroupFilterTableView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
    }
    
    public GroupFilterTableView(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    public GroupFilterTableView(final Context context) {
        super(context);
    }
    
    public int getGroupFilterCellWidth() {
        return this.c;
    }
    
    @Override
    public void setGroupFilterCellWidth(final int c) {
        this.c = c;
    }
    
    public GroupFilterItemViewInterface.GroupFilterAction getAction() {
        return this.a;
    }
    
    @Override
    public void setAction(final GroupFilterItemViewInterface.GroupFilterAction a) {
        this.a = a;
    }
    
    public boolean isDisplaySelectionIcon() {
        return this.b;
    }
    
    @Override
    public void setDisplaySelectionIcon(final boolean b) {
        this.b = b;
    }
    
    public GroupFilterGroupViewBase.GroupFilterGroupViewDelegate getGroupDelegate() {
        return this.d;
    }
    
    @Override
    public void setGroupDelegate(final GroupFilterGroupViewBase.GroupFilterGroupViewDelegate d) {
        this.d = d;
    }
    
    @Override
    public void setFilterTask(final FilterTaskInterface e) {
        this.e = e;
    }
    
    @Override
    public void loadView() {
        super.loadView();
        this.setHasFixedSize(true);
    }
    
    @Override
    protected void onViewCreated(final GroupFilterItemViewBase groupFilterItemViewBase, final ViewGroup viewGroup, final int n) {
        groupFilterItemViewBase.setAction(this.getAction());
        groupFilterItemViewBase.setDisplaySelectionIcon(this.isDisplaySelectionIcon());
        groupFilterItemViewBase.setFilterTask(this.e);
        if (this.getGroupFilterCellWidth() > 0) {
            groupFilterItemViewBase.setWidth(this.getGroupFilterCellWidth());
        }
        if (groupFilterItemViewBase instanceof GroupFilterGroupViewBase) {
            ((GroupFilterGroupViewBase)groupFilterItemViewBase).setDelegate(this.getGroupDelegate());
        }
    }
    
    @Override
    protected void onViewBinded(final GroupFilterItemViewBase groupFilterItemViewBase, final int n) {
    }
}
