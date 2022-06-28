// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget.filter;

import java.util.List;
import android.app.Activity;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkCorePatch;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;
import com.example.myvideoeditorapp.kore.struct.ViewSize;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.content.Context;
import android.view.View;

import com.example.myvideoeditorapp.kore.view.TuSdkRelativeLayout;

public abstract class GroupFilterBaseView extends TuSdkRelativeLayout
{
    private boolean a;
    private boolean b;
    private int c;
    private int d;
    private int e;
    private int f;
    private boolean g;
    private boolean h;
    private Class<?> i;
    private boolean j;
    private boolean k;
    private GroupFilterBarInterface.GroupFilterBarDelegate l;
    
    public GroupFilterBaseView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.g = true;
        this.l = new GroupFilterBarInterface.GroupFilterBarDelegate() {
            @Override
            public boolean onGroupFilterSelected(final GroupFilterBarInterface groupFilterBarInterface, final GroupFilterItemViewInterface groupFilterItemViewInterface, final GroupFilterItem groupFilterItem) {
                return GroupFilterBaseView.this.onDispatchGroupFilterSelected(groupFilterBarInterface, groupFilterItemViewInterface, groupFilterItem);
            }
        };
    }
    
    public GroupFilterBaseView(final Context context, final AttributeSet set) {
        super(context, set);
        this.g = true;
        this.l = new GroupFilterBarInterface.GroupFilterBarDelegate() {
            @Override
            public boolean onGroupFilterSelected(final GroupFilterBarInterface groupFilterBarInterface, final GroupFilterItemViewInterface groupFilterItemViewInterface, final GroupFilterItem groupFilterItem) {
                return GroupFilterBaseView.this.onDispatchGroupFilterSelected(groupFilterBarInterface, groupFilterItemViewInterface, groupFilterItem);
            }
        };
    }
    
    public GroupFilterBaseView(final Context context) {
        super(context);
        this.g = true;
        this.l = new GroupFilterBarInterface.GroupFilterBarDelegate() {
            @Override
            public boolean onGroupFilterSelected(final GroupFilterBarInterface groupFilterBarInterface, final GroupFilterItemViewInterface groupFilterItemViewInterface, final GroupFilterItem groupFilterItem) {
                return GroupFilterBaseView.this.onDispatchGroupFilterSelected(groupFilterBarInterface, groupFilterItemViewInterface, groupFilterItem);
            }
        };
    }
    
    public boolean isStateHidden() {
        return this.a;
    }
    
    public void setStateHidden(final boolean a) {
        this.a = a;
    }
    
    public int getGroupFilterCellWidth() {
        return this.c;
    }

    public void setGroupFilterCellWidth(int var1) {
        this.c = var1;
        if (this.getGroupFilterBar() != null) {
            ((GroupFilterBarInterface)this.getGroupFilterBar()).setGroupFilterCellWidth(this.getGroupFilterCellWidth());
        }

    }
    
    public int getGroupTableCellLayoutId() {
        return this.d;
    }

    public void setGroupTableCellLayoutId(int var1) {
        this.d = var1;
        if (this.getGroupFilterBar() != null) {
            ((GroupFilterBarInterface)this.getGroupFilterBar()).setGroupTableCellLayoutId(this.getGroupTableCellLayoutId());
        }

    }
    
    public int getFilterTableCellLayoutId() {
        return this.e;
    }

    public void setFilterTableCellLayoutId(int var1) {
        this.e = var1;
        if (this.getGroupFilterBar() != null) {
            ((GroupFilterBarInterface)this.getGroupFilterBar()).setFilterTableCellLayoutId(this.getFilterTableCellLayoutId());
        }

    }
    
    public int getFilterBarHeight() {
        return this.f;
    }

    public void setFilterBarHeight(int var1) {
        this.f = var1;
        if (this.getFilterBarHeight() > 0 && this.getGroupFilterBar() != null) {
            ((GroupFilterBarInterface)this.getGroupFilterBar()).setHeight(this.getFilterBarHeight());
        }

    }

    public void setThumbImage(Bitmap var1) {
        if (this.getGroupFilterBar() != null) {
            if (var1 != null) {
                ViewSize var2 = ViewSize.create(this.getGroupFilterBar());
                if (this.f > 0) {
                    var2.height = this.f;
                }

                if (this.c > 0) {
                    var2.width = this.c;
                } else {
                    var2.width = var2.height;
                }

                var1 = BitmapHelper.imageResize(var1, var2);
            }

            ((GroupFilterBarInterface)this.getGroupFilterBar()).setThumbImage(var1);
        }
    }

    public void setRenderFilterThumb(boolean var1) {
        if (TuSdkCorePatch.applyThumbRenderPatch()) {
            var1 = false;
        }

        if (this.getGroupFilterBar() != null) {
            ((GroupFilterBarInterface)this.getGroupFilterBar()).setRenderFilterThumb(var1);
        }

    }
    
    public boolean isEnableFilterConfig() {
        return this.b;
    }

    public void setEnableFilterConfig(boolean var1) {
        this.b = var1;
        if (this.getGroupFilterBar() != null) {
            ((GroupFilterBarInterface)this.getGroupFilterBar()).setEnableFilterConfig(var1);
        }
    }

    public boolean isEnableNormalFilter() {
        return this.g;
    }

    public void setEnableNormalFilter(boolean var1) {
        this.g = var1;
        if (this.getGroupFilterBar() != null) {
            ((GroupFilterBarInterface)this.getGroupFilterBar()).setEnableNormalFilter(var1);
        }
    }

    public boolean isEnableOnlineFilter() {
        return this.h;
    }

    public void setEnableOnlineFilter(boolean var1) {
        this.h = var1;
        if (this.getGroupFilterBar() != null) {
            ((GroupFilterBarInterface)this.getGroupFilterBar()).setEnableOnlineFilter(var1);
        }
    }

    public Class<?> getOnlineFragmentClazz() {
        return this.i;
    }

    public void setOnlineFragmentClazz(Class<?> var1) {
        this.i = var1;
        if (this.getGroupFilterBar() != null) {
            ((GroupFilterBarInterface)this.getGroupFilterBar()).setOnlineFragmentClazz(var1);
        }
    }

    public boolean isEnableHistory() {
        return this.j;
    }

    public void setEnableHistory(boolean var1) {
        this.j = var1;
        if (this.getGroupFilterBar() != null) {
            ((GroupFilterBarInterface)this.getGroupFilterBar()).setEnableHistory(var1);
        }
    }

    public boolean isDisplaySubtitles() {
        return this.k;
    }

    public void setDisplaySubtitles(boolean var1) {
        this.k = var1;
    }

    public void setActivity(Activity var1) {
        if (this.getGroupFilterBar() != null) {
            ((GroupFilterBarInterface)this.getGroupFilterBar()).setActivity(var1);
        }
    }
    
    public abstract <T extends View> T getFilterTitleView();
    
    public abstract <T extends View> T getGroupFilterBar();
    
    protected void configGroupFilterBar(final GroupFilterBarInterface groupFilterBarInterface, final GroupFilterItemViewInterface.GroupFilterAction action) {
        if (groupFilterBarInterface == null) {
            return;
        }
        groupFilterBarInterface.setAction(action);
        groupFilterBarInterface.setDelegate(this.l);
    }

    public void setFilterGroup(List<String> var1) {
        if (this.getGroupFilterBar() != null) {
            ((GroupFilterBarInterface)this.getGroupFilterBar()).setFilterGroup(var1);
        }

    }

    public void setAutoSelectGroupDefaultFilter(boolean var1) {
        if (this.getGroupFilterBar() != null) {
            ((GroupFilterBarInterface)this.getGroupFilterBar()).setAutoSelectGroupDefaultFilter(var1);
        }

    }

    public void setSaveLastFilter(boolean var1) {
        if (this.getGroupFilterBar() != null) {
            ((GroupFilterBarInterface)this.getGroupFilterBar()).setSaveLastFilter(var1);
        }

    }
    
    @Override
    public void loadView() {
        super.loadView();
        this.getGroupFilterBar();
        this.showViewIn(this.getFilterTitleView(), false);
    }
    
    protected abstract boolean onDispatchGroupFilterSelected(final GroupFilterBarInterface p0, final GroupFilterItemViewInterface p1, final GroupFilterItem p2);
    
    protected boolean notifyTitle(final GroupFilterItemViewInterface groupFilterItemViewInterface, final GroupFilterItem groupFilterItem) {
        if (groupFilterItemViewInterface == null || !groupFilterItemViewInterface.isSelected()) {
            this.notifyTitle(groupFilterItem);
            return true;
        }
        return false;
    }

    protected void notifyTitle(GroupFilterItem var1) {
        if (this.getFilterTitleView() != null && this.isDisplaySubtitles()) {
            if (var1.type == GroupFilterItem.GroupFilterItemType.TypeFilter) {
                ((FilterSubtitleViewInterface)this.getFilterTitleView()).setFilter(var1.filterOption);
            }

        }
    }

    public void loadFilters() {
        if (this.getGroupFilterBar() != null) {
            ((GroupFilterBarInterface)this.getGroupFilterBar()).loadFilters();
        }

    }

    public abstract void setDefaultShowState(boolean var1);

    public void exitRemoveState() {
        if (this.getGroupFilterBar() != null) {
            ((GroupFilterBarInterface)this.getGroupFilterBar()).exitRemoveState();
        }
    }
}
