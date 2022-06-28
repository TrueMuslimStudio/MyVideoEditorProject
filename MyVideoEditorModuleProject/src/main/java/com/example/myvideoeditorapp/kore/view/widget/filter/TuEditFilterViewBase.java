// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget.filter;

import java.util.Iterator;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import android.util.AttributeSet;
import android.content.Context;
import java.util.List;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;

public abstract class TuEditFilterViewBase extends GroupFilterBaseView
{
    private FilterWrap a;
    
    protected abstract void setConfigViewParams(final List<String> p0);
    
    protected abstract void showConfigView(final boolean p0);
    
    protected abstract boolean onFilterSelected(final GroupFilterItem p0);
    
    public TuEditFilterViewBase(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
    }
    
    public TuEditFilterViewBase(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    public TuEditFilterViewBase(final Context context) {
        super(context);
    }
    
    protected void handleCancelAction() {
        if (this.a == null) {
            return;
        }
        final SelesParameters filterParameter = this.a.getFilterParameter();
        if (filterParameter == null) {
            return;
        }
        final Iterator<SelesParameters.FilterArg> iterator = filterParameter.getArgs().iterator();
        while (iterator.hasNext()) {
            iterator.next().reset();
        }
        this.requestRender();
    }
    
    @Override
    protected boolean onDispatchGroupFilterSelected(final GroupFilterBarInterface groupFilterBarInterface, final GroupFilterItemViewInterface groupFilterItemViewInterface, final GroupFilterItem groupFilterItem) {
        if (groupFilterItem.type == GroupFilterItem.GroupFilterItemType.TypeFilter) {
            if (this.notifyTitle(groupFilterItemViewInterface, groupFilterItem)) {
                return this.onFilterSelected(groupFilterItem);
            }
            if (this.isEnableFilterConfig() && groupFilterItem.filterOption != null && groupFilterItem.filterOption.canDefinition) {
                this.showConfigView(true);
            }
        }
        return true;
    }
    
    public final void setFilter(final FilterWrap a) {
        if (a == null || a.equals(this.a)) {
            return;
        }
        this.a = a;
        final SelesParameters filterParameter = this.a.getFilterParameter();
        if (filterParameter == null || filterParameter.size() == 0) {
            return;
        }
        this.setConfigViewParams(filterParameter.getArgKeys());
    }
    
    protected void requestRender() {
        if (this.a != null) {
            this.a.submitFilterParameter();
        }
    }
    
    protected final SelesParameters.FilterArg getFilterArg(final int n) {
        if (this.a == null || n < 0) {
            return null;
        }
        final SelesParameters filterParameter = this.a.getFilterParameter();
        if (filterParameter == null || n >= filterParameter.size()) {
            return null;
        }
        return filterParameter.getArgs().get(n);
    }
}
