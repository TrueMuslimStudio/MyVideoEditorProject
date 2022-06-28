// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget.filter;

import com.example.myvideoeditorapp.kore.activity.TuSdkFragment;
import com.example.myvideoeditorapp.kore.components.TuSdkHelperComponent;
import com.example.myvideoeditorapp.kore.components.filter.TuFilterOnlineFragmentInterface;
import com.example.myvideoeditorapp.kore.type.DownloadTaskStatus;
import com.example.myvideoeditorapp.kore.network.TuSdkDownloadItem;
import com.example.myvideoeditorapp.kore.utils.ReflectUtils;
import androidx.fragment.app.Fragment;
import com.example.myvideoeditorapp.kore.utils.TLog;
import android.app.Activity;
import java.util.Collection;
import com.example.myvideoeditorapp.kore.utils.StringHelper;
import androidx.core.view.ViewPropertyAnimatorListener;

import android.view.View;
import android.view.animation.Interpolator;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import androidx.core.view.ViewCompat;
import com.example.myvideoeditorapp.kore.view.TuSdkViewHelper;
import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterGroup;
import java.util.ArrayList;
import java.util.Iterator;
import android.graphics.Bitmap;
import com.example.myvideoeditorapp.kore.task.FiltersTempTask;
import android.util.AttributeSet;
import android.content.Context;
import com.example.myvideoeditorapp.kore.task.FilterTaskInterface;
import java.util.List;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterOption;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterLocalPackage;
import com.example.myvideoeditorapp.kore.view.TuSdkRelativeLayout;

public abstract class GroupFilterBarBase extends TuSdkRelativeLayout implements FilterLocalPackage.FilterLocalPackageDelegate, TuFilterOnlineFragmentInterface.TuFilterOnlineFragmentDelegate, GroupFilterBarInterface, GroupFilterGroupViewBase.GroupFilterGroupViewDelegate
{
    private GroupFilterItemViewInterface.GroupFilterAction a;
    private FilterOption b;
    private long c;
    private GroupFilterBarDelegate d;
    private List<String> e;
    private boolean f;
    private boolean g;
    private boolean h;
    private boolean i;
    private boolean j;
    private boolean k;
    private boolean l;
    private int m;
    private List<String> n;
    private FilterTaskInterface o;
    private boolean p;
    private boolean q;
    private Class<?> r;
    private int s;
    private TuSdkHelperComponent t;
    
    public GroupFilterBarBase(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.j = true;
        this.m = 20;
    }
    
    public GroupFilterBarBase(final Context context, final AttributeSet set) {
        super(context, set);
        this.j = true;
        this.m = 20;
    }
    
    public GroupFilterBarBase(final Context context) {
        super(context);
        this.j = true;
        this.m = 20;
    }
    
    @Override
    protected void initView() {
        super.initView();
        this.o = new FiltersTempTask();
    }
    
    public abstract <T extends View> T getGroupTable();
    
    public abstract <T extends View> T getFilterTable();
    
    public GroupFilterItemViewInterface.GroupFilterAction getAction() {
        return this.a;
    }
    
    @Override
    public void setAction(GroupFilterItemViewInterface.GroupFilterAction var1) {
        this.a = var1;
        if (this.getGroupTable() != null) {
            ((GroupFilterTableViewInterface)this.getGroupTable()).setAction(var1);
            ((GroupFilterTableViewInterface)this.getGroupTable()).setFilterTask(this.o);
        }

        if (this.getFilterTable() != null) {
            ((GroupFilterTableViewInterface)this.getFilterTable()).setAction(var1);
            ((GroupFilterTableViewInterface)this.getFilterTable()).setFilterTask(this.o);
        }

    }
    
    public boolean isSaveLastFilter() {
        return this.f;
    }
    
    @Override
    public void setSaveLastFilter(final boolean f) {
        this.f = f;
    }
    
    public boolean isAutoSelectGroupDefaultFilter() {
        return this.h;
    }
    
    @Override
    public void setAutoSelectGroupDefaultFilter(final boolean h) {
        this.h = h;
    }
    
    public GroupFilterBarDelegate getDelegate() {
        return this.d;
    }
    
    @Override
    public void setDelegate(final GroupFilterBarDelegate d) {
        this.d = d;
    }
    
    protected void notifyDelegate(final FilterOption filterOption) {
        if (filterOption == null) {
            return;
        }
        this.notifyDelegate(GroupFilterItem.createWithFilter(filterOption), null);
    }
    
    protected boolean notifyDelegate(final GroupFilterItem groupFilterItem, final GroupFilterItemViewBase groupFilterItemViewBase) {
        return this.d == null || this.d.onGroupFilterSelected(this, groupFilterItemViewBase, groupFilterItem);
    }
    
    @Override
    public void setFilterGroup(final List<String> list) {
        this.e = list;
        this.o.setFilerNames(list);
    }
    
    @Override
    public void setThumbImage(final Bitmap inputImage) {
        this.o.setInputImage(inputImage);
    }
    
    public boolean isEnableFilterConfig() {
        return this.i;
    }
    
    @Override
    public void setEnableFilterConfig(boolean var1) {
        this.i = var1;
        if (this.getGroupTable() != null) {
            ((GroupFilterTableViewInterface)this.getGroupTable()).setDisplaySelectionIcon(var1);
        }

        if (this.getFilterTable() != null) {
            ((GroupFilterTableViewInterface)this.getFilterTable()).setDisplaySelectionIcon(var1);
        }

    }
    
    public boolean isEnableNormalFilter() {
        return this.j;
    }
    
    @Override
    public void setEnableNormalFilter(final boolean j) {
        this.j = j;
    }
    
    public final boolean isEnableOnlineFilter() {
        return this.k;
    }
    
    @Override
    public final void setEnableOnlineFilter(boolean var1) {
        this.k = var1;
        if (this.getGroupTable() != null) {
            ((GroupFilterTableViewInterface)this.getGroupTable()).setGroupDelegate(var1 ? this : null);
        }
    }
    
    public boolean isEnableHistory() {
        return this.l;
    }
    
    @Override
    public void setEnableHistory(final boolean l) {
        this.l = l;
    }
    
    public Class<?> getOnlineFragmentClazz() {
        return this.r;
    }
    
    @Override
    public void setOnlineFragmentClazz(final Class<?> r) {
        this.r = r;
    }
    
    public boolean isLoadFilters() {
        return this.p;
    }
    
    @Override
    public boolean isRenderFilterThumb() {
        return this.o.isRenderFilterThumb();
    }
    
    @Override
    public void setRenderFilterThumb(final boolean renderFilterThumb) {
        this.o.setRenderFilterThumb(renderFilterThumb);
    }
    
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.t = null;
        FilterLocalPackage.shared().removeDelegate(this);
        this.o.resetQueues();
    }
    
    @Override
    public void loadFilters(final FilterOption b) {
        this.b = b;
        this.g = (b != null);
        this.loadFilters();
    }
    
    @Override
    public void loadFilters() {
        this.e();
        this.q = (this.e == null || this.e.isEmpty());
        if (this.q) {
            this.a();
            FilterLocalPackage.shared().appenDelegate(this);
        }
        else {
            this.c();
        }
        this.p = true;
    }
    
    private void a() {
        final GroupFilterTableViewInterface groupFilterTableViewInterface = this.getGroupTable();
        if (groupFilterTableViewInterface == null) {
            return;
        }
        final int[] array = { 0 };
        groupFilterTableViewInterface.setModeList(this.a(array));
        groupFilterTableViewInterface.setSelectedPosition(array[0]);
        groupFilterTableViewInterface.scrollToPosition(array[0] - 2);
    }
    
    private List<GroupFilterItem> a(final int[] array) {
        final List<GroupFilterItem> b = this.b();
        FilterOption b2 = null;
        if (this.g) {
            b2 = this.b;
        }
        else if (this.f) {
            b2 = this.d();
        }
        int n = -1;
        for (final GroupFilterItem groupFilterItem : b) {
            ++n;
            if (groupFilterItem.type == GroupFilterItem.GroupFilterItemType.TypeFilter) {
                array[0] = n;
            }
            else {
                if (b2 == null || groupFilterItem.filterGroup == null) {
                    continue;
                }
                if (groupFilterItem.type != GroupFilterItem.GroupFilterItemType.TypeGroup) {
                    continue;
                }
                if (groupFilterItem.filterGroup.groupId != b2.groupId) {
                    continue;
                }
                array[0] = n;
                this.b = b2;
                if (this.f) {
                    this.notifyDelegate(b2);
                }
                this.a(b2);
            }
        }
        return b;
    }
    
    private List<GroupFilterItem> b() {
        final ArrayList<GroupFilterItem> list = new ArrayList<GroupFilterItem>();
        if (this.isEnableHistory()) {
            list.add(GroupFilterItem.create(GroupFilterItem.GroupFilterItemType.TypeHistory));
        }
        if (this.isEnableNormalFilter()) {
            list.add(GroupFilterItem.create(GroupFilterItem.GroupFilterItemType.TypeFilter));
        }
        final List<FilterGroup> groupsByAtionScen = FilterLocalPackage.shared().getGroupsByAtionScen(1);
        if (groupsByAtionScen != null) {
            for (final FilterGroup filterGroup : groupsByAtionScen) {
                if (filterGroup.disableRuntime && this.getAction() == GroupFilterItemViewInterface.GroupFilterAction.ActionCamera) {
                    continue;
                }
                list.add(GroupFilterItem.createWithGroup(filterGroup));
                this.o.appendFilterCode(FilterLocalPackage.shared().getGroupDefaultFilterCode(filterGroup));
            }
        }
        this.o.start();
        if (this.isEnableOnlineFilter()) {
            list.add(GroupFilterItem.create(GroupFilterItem.GroupFilterItemType.TypeOnline));
        }
        return list;
    }
    
    protected void onGroupItemSeleced(final GroupFilterItem groupFilterItem, final GroupFilterItemViewBase groupFilterItemViewBase, final int n) {
        if (this.getFilterTable() == null) {
            return;
        }
        if (groupFilterItem.isInActingType) {
            this.exitRemoveState();
            return;
        }
        final boolean notifyDelegate = this.notifyDelegate(groupFilterItem, groupFilterItemViewBase);
        if (groupFilterItem.type == GroupFilterItem.GroupFilterItemType.TypeFilter) {
            this.a(groupFilterItem, groupFilterItemViewBase, n);
        }
        if (!notifyDelegate) {
            return;
        }
        switch (groupFilterItem.type.ordinal()) {
            case 1:
            case 2: {
                this.b(groupFilterItem, groupFilterItemViewBase, n);
                break;
            }
            case 3: {
                this.f();
                break;
            }
        }
    }

    private void a(GroupFilterItem var1, GroupFilterItemViewBase var2, int var3) {
        if (!var2.isSelected()) {
            ((GroupFilterTableViewInterface)this.getGroupTable()).changeSelectedPosition(var3);
        }

        this.b = null;
        this.a((FilterOption)null);
    }
    
    private void b(final GroupFilterItem groupFilterItem, final GroupFilterItemViewBase groupFilterItemViewBase, final int n) {
        int n2 = 0;
        this.s = 0;
        if (groupFilterItemViewBase != null) {
            n2 = (TuSdkContext.getScreenSize().width - groupFilterItemViewBase.getWidth()) / 2;
            this.s = TuSdkViewHelper.locationInWindowLeft((View)groupFilterItemViewBase) - n2;
        }
        this.a(groupFilterItem, groupFilterItemViewBase, n, n2);
        this.showFilterTable(this.s, true);
    }
    
    protected void handleBackAction() {
        this.showFilterTable(this.s, false);
    }

    private void a(GroupFilterItem var1, GroupFilterItemViewBase var2, int var3, int var4) {
        if (var2 == null || !var2.isSelected() || this.b != null) {
            ((GroupFilterTableViewInterface)this.getGroupTable()).changeSelectedPosition(var3);
            List var5 = null;
            long var6 = -1L;
            if (var1.type == GroupFilterItem.GroupFilterItemType.TypeHistory) {
                var5 = FilterLocalPackage.shared().getFilters(this.e());
            } else if (var1.type == GroupFilterItem.GroupFilterItemType.TypeGroup) {
                var5 = FilterLocalPackage.shared().getGroupFilters(var1.filterGroup);
                var6 = var1.filterGroup.defaultFilterId;
            }

            if (var5 != null) {
                ArrayList var8 = new ArrayList();
                var8.add(GroupFilterItem.create(GroupFilterItem.GroupFilterItemType.TypeHolder));
                boolean var9 = false;
                if (this.b != null) {
                    var6 = this.b.id;
                    var9 = this.g;
                    this.b = null;
                } else if (var6 > -1L) {
                    var9 = this.h;
                }

                int var10 = 0;
                int var11 = 0;
                FilterOption var12 = null;
                Iterator var13 = var5.iterator();

                while(true) {
                    FilterOption var14;
                    do {
                        if (!var13.hasNext()) {
                            this.o.start();
                            if (var11 > 0) {
                                var9 = true;
                                var10 = var11;
                            } else if (var12 != null) {
                                if (this.f || this.h) {
                                    this.notifyDelegate(var12);
                                }

                                this.a(var12);
                            }

                            ((GroupFilterTableViewInterface)this.getFilterTable()).setModeList(var8);
                            if (!this.f && !this.h && !var9) {
                                ((GroupFilterTableViewInterface)this.getFilterTable()).setSelectedPosition(-1);
                                ((GroupFilterTableViewInterface)this.getFilterTable()).scrollToPosition(0);
                            } else {
                                ((GroupFilterTableViewInterface)this.getFilterTable()).setSelectedPosition(var10);
                                ((GroupFilterTableViewInterface)this.getFilterTable()).scrollToPositionWithOffset(var10, var4);
                            }

                            return;
                        }

                        var14 = (FilterOption)var13.next();
                    } while(var14.disableRuntime && this.getAction() == GroupFilterItemViewInterface.GroupFilterAction.ActionCamera);

                    if (var14.id == this.c) {
                        var11 = var8.size();
                    }

                    if (var14.id == var6) {
                        var10 = var8.size();
                        var12 = var14;
                    }

                    var8.add(GroupFilterItem.createWithFilter(var14));
                    this.o.appendFilterCode(var14.code);
                }
            }
        }
    }

    private void c() {
        this.showViewIn(this.getGroupTable(), false);
        if (this.getFilterTable() != null) {
            List var1 = FilterLocalPackage.shared().getFilters(this.e);
            if (var1 != null && !var1.isEmpty()) {
                this.showViewIn(this.getFilterTable(), true);
                ArrayList var2 = new ArrayList();
                int var3 = var2.size();
                FilterOption var4 = null;
                if (this.g) {
                    var4 = this.b;
                } else {
                    var4 = this.d();
                }

                if (this.isEnableNormalFilter()) {
                    var2.add(GroupFilterItem.create(GroupFilterItem.GroupFilterItemType.TypeFilter));
                }

                Iterator var5 = var1.iterator();

                while(true) {
                    FilterOption var6;
                    do {
                        if (!var5.hasNext()) {
                            this.o.start();
                            ((GroupFilterTableViewInterface)this.getFilterTable()).setModeList(var2);
                            ((GroupFilterTableViewInterface)this.getFilterTable()).setSelectedPosition(var3);
                            ((GroupFilterTableViewInterface)this.getFilterTable()).scrollToPosition(var3 - 2);
                            return;
                        }

                        var6 = (FilterOption)var5.next();
                    } while(var6.disableRuntime && this.getAction() == GroupFilterItemViewInterface.GroupFilterAction.ActionCamera);

                    FilterGroup var7 = FilterLocalPackage.shared().getFilterGroup(var6.groupId);
                    if (var7.canUseForAtionScenType(1)) {
                        if (var4 != null && var6.id == var4.id) {
                            var3 = var2.size();
                            if (this.f) {
                                this.notifyDelegate(var6);
                            }

                            this.a(var6);
                        }

                        var2.add(GroupFilterItem.createWithFilter(var6));
                    }
                }
            }
        }
    }

    protected void onFilterItemSeleced(GroupFilterItem var1, GroupFilterItemViewBase var2, int var3) {
        if (this.notifyDelegate(var1, var2)) {
            if (!var2.isSelected()) {
                ((GroupFilterTableViewInterface)this.getFilterTable()).changeSelectedPosition(var3);
                ((GroupFilterTableViewInterface)this.getFilterTable()).smoothScrollByCenter(var2);
            }

            this.a(var1.filterOption);
            this.b(var1.filterOption);
        }
    }
    
    protected void showFilterTable(final int n, final boolean b) {
        this.showViewIn(this.getFilterTable(), true);
        float n2 = 1.0f;
        int n3 = 0;
        Object listener = null;
        Object interpolator;
        if (b) {
            ViewCompat.setTranslationX(this.getFilterTable(), (float)n);
            ViewCompat.setScaleX(this.getFilterTable(), 0.0f);
            n2 = 0.0f;
            interpolator = new OvershootInterpolator(1.0f);
        }
        else {
            interpolator = new AnticipateInterpolator(1.0f);
            n3 = n;
            listener = new ViewPropertyAnimatorListenerAdapter() {
                public void onAnimationEnd(final View view) {
                    view.setVisibility(View.INVISIBLE);
                }
            };
        }
        ViewCompat.animate(this.getGroupTable()).alpha(n2).setDuration(50L);
        ViewCompat.animate(this.getFilterTable()).scaleX(1.0f - n2).translationX((float)n3).setDuration(220L).setInterpolator((Interpolator)interpolator).setListener((ViewPropertyAnimatorListener)listener);
    }
    
    private FilterOption d() {
        if (!this.f) {
            return FilterLocalPackage.shared().option(null);
        }
        return FilterLocalPackage.shared().option(TuSdkContext.sharedPreferences().loadSharedCache(String.format("lsq_lastfilter_%s", this.a)));
    }
    
    private void a(final FilterOption filterOption) {
        this.c = 0L;
        String code = null;
        if (filterOption != null) {
            code = filterOption.code;
            this.c = filterOption.id;
        }
        if (!this.f) {
            return;
        }
        TuSdkContext.sharedPreferences().saveSharedCache(String.format("lsq_lastfilter_%s", this.a), code);
    }
    
    private void b(final FilterOption filterOption) {
        String code = null;
        if (filterOption != null) {
            code = filterOption.code;
        }
        if (StringHelper.isBlank(code) || !this.l) {
            return;
        }
        if (this.n == null) {
            this.n = new ArrayList<String>(this.m);
        }
        this.n.remove(code);
        this.n.add(0, code);
        if (this.n.size() > this.m) {
            this.n = new ArrayList<String>(this.n.subList(0, this.m));
        }
        TuSdkContext.sharedPreferences().saveSharedCacheObject(String.format("lsq_filter_history_%s", this.a), this.n);
    }
    
    private List<String> e() {
        if (!this.l) {
            return null;
        }
        if (this.n != null) {
            return this.n;
        }
        this.n = TuSdkContext.sharedPreferences().loadSharedCacheObject(String.format("lsq_filter_history_%s", this.a));
        if (this.n == null) {
            this.n = new ArrayList<String>(this.m);
        }
        return this.n;
    }
    
    @Override
    public void setActivity(final Activity activity) {
        if (activity == null) {
            return;
        }
        this.t = new TuSdkHelperComponent(activity);
    }
    
    private void f() {
        if (this.t == null || this.r == null) {
            TLog.w("You need set OnlineFragmentClazz: %s", this.getClass());
            return;
        }
        if (!TuFilterOnlineFragmentInterface.class.isAssignableFrom(this.r) || !Fragment.class.isAssignableFrom(this.r)) {
            TLog.w("You set OnlineFragmentClazz(%s) is not allow TuFilterOnlineFragmentInterface(%s) or Fragment(%s) in %s", this.r, TuFilterOnlineFragmentInterface.class.isAssignableFrom(this.r), Fragment.class.isAssignableFrom(this.r), this.getClass());
            return;
        }
        final TuFilterOnlineFragmentInterface tuFilterOnlineFragmentInterface = ReflectUtils.classInstance(this.r);
        if (tuFilterOnlineFragmentInterface == null) {
            return;
        }
        tuFilterOnlineFragmentInterface.setAction(this.getAction());
        tuFilterOnlineFragmentInterface.setDelegate(this);
        this.t.presentModalNavigationActivity((Fragment)tuFilterOnlineFragmentInterface);
    }
    
    @Override
    public void onFilterPackageStatusChanged(final FilterLocalPackage filterLocalPackage, final TuSdkDownloadItem tuSdkDownloadItem, final DownloadTaskStatus downloadTaskStatus) {
        if (tuSdkDownloadItem == null || downloadTaskStatus == null || this.e != null) {
            return;
        }
        switch (downloadTaskStatus.ordinal()) {
            case 1: {
                this.a(tuSdkDownloadItem);
                break;
            }
            case 2: {
                this.b(tuSdkDownloadItem);
                break;
            }
        }
    }
    
    private void a(final TuSdkDownloadItem tuSdkDownloadItem) {
        final GroupFilterTableViewInterface groupFilterTableViewInterface = this.getGroupTable();
        if (groupFilterTableViewInterface == null) {
            return;
        }
        final List<GroupFilterItem> b = this.b();
        final int a = this.a(b, tuSdkDownloadItem.id);
        if (a < 0) {
            TLog.w("This filter group [ %s ] could not be used in Camere component", FilterLocalPackage.shared().getGroupNameKey(tuSdkDownloadItem.id));
            return;
        }
        groupFilterTableViewInterface.setModeList(b);
        groupFilterTableViewInterface.getAdapter().notifyItemInserted(a);
    }
    
    private void b(final TuSdkDownloadItem tuSdkDownloadItem) {
        final GroupFilterTableViewInterface groupFilterTableViewInterface = this.getGroupTable();
        if (groupFilterTableViewInterface == null) {
            return;
        }
        final List<GroupFilterItem> g = this.g();
        final int a = this.a(groupFilterTableViewInterface.getModeList(), tuSdkDownloadItem.id);
        final int selectedPosition = groupFilterTableViewInterface.getSelectedPosition();
        groupFilterTableViewInterface.setModeList(g);
        groupFilterTableViewInterface.getAdapter().notifyItemRemoved(a);
        int a2;
        if (selectedPosition == -1 || selectedPosition == a) {
            a2 = this.a(g);
            this.b = null;
            this.notifyDelegate(GroupFilterItem.create(GroupFilterItem.GroupFilterItemType.TypeFilter), null);
            this.a((FilterOption)null);
        }
        else if (selectedPosition > a) {
            a2 = selectedPosition - 1;
        }
        else {
            a2 = selectedPosition;
        }
        if (a2 > -1) {
            groupFilterTableViewInterface.setSelectedPosition(a2, false);
        }
    }
    
    private int a(final List<GroupFilterItem> list, final long n) {
        if (list == null) {
            return -1;
        }
        int n2 = -1;
        for (final GroupFilterItem groupFilterItem : list) {
            ++n2;
            if (groupFilterItem.filterGroup != null && groupFilterItem.filterGroup.groupId == n) {
                return n2;
            }
        }
        return -1;
    }
    
    private int a(final List<GroupFilterItem> list) {
        int n = -1;
        if (list == null) {
            return n;
        }
        for (final GroupFilterItem groupFilterItem : list) {
            ++n;
            if (groupFilterItem.type == GroupFilterItem.GroupFilterItemType.TypeFilter) {
                return n;
            }
        }
        return n;
    }
    
    private List<GroupFilterItem> g() {
        final List<GroupFilterItem> b = this.b();
        final Iterator<GroupFilterItem> iterator = b.iterator();
        while (iterator.hasNext()) {
            iterator.next().isInActingType = true;
        }
        return b;
    }
    
    @Override
    public void onGroupFilterGroupViewLongClick(final GroupFilterGroupViewBase groupFilterGroupViewBase) {
        this.a(true);
    }
    
    @Override
    public void onGroupFilterGroupViewRemove(final GroupFilterGroupViewBase groupFilterGroupViewBase) {
        if (groupFilterGroupViewBase == null || groupFilterGroupViewBase.getModel() == null || groupFilterGroupViewBase.getModel().filterGroup == null) {
            return;
        }
        FilterLocalPackage.shared().removeDownloadWithIdt(groupFilterGroupViewBase.getModel().filterGroup.groupId);
    }
    
    @Override
    public void exitRemoveState() {
        this.a(false);
    }
    
    private void a(final boolean isInActingType) {
        final GroupFilterTableViewInterface groupFilterTableViewInterface = this.getGroupTable();
        if (groupFilterTableViewInterface == null || groupFilterTableViewInterface.getModeList() == null) {
            return;
        }
        final Iterator<GroupFilterItem> iterator = groupFilterTableViewInterface.getModeList().iterator();
        while (iterator.hasNext()) {
            iterator.next().isInActingType = isInActingType;
        }
        groupFilterTableViewInterface.reloadData();
    }
    
    @Override
    public void onTuFilterOnlineFragmentSelected(final TuFilterOnlineFragmentInterface tuFilterOnlineFragmentInterface, final long n) {
        if (tuFilterOnlineFragmentInterface != null && tuFilterOnlineFragmentInterface instanceof TuSdkFragment) {
            ((TuSdkFragment)tuFilterOnlineFragmentInterface).dismissActivityWithAnim();
        }
        this.getHandler().postDelayed((Runnable)new Runnable() {
            @Override
            public void run() {
                GroupFilterBarBase.this.a(n);
            }
        }, 10L);
    }

    private void a(long var1) {
        if (var1 > 0L && this.getGroupTable() != null) {
            List var3 = ((GroupFilterTableViewInterface)this.getGroupTable()).getModeList();
            if (var3 != null && !var3.isEmpty()) {
                int var4 = this.a(var3, var1);
                if (var4 != -1) {
                    GroupFilterItem var5 = (GroupFilterItem)var3.get(var4);
                    this.onGroupItemSeleced(var5, (GroupFilterItemViewBase)null, var4);
                    ((GroupFilterTableViewInterface)this.getGroupTable()).scrollToPosition(var4 - 2);
                }
            }
        }
    }}
