// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.filter;

import android.view.View;
import android.view.ViewGroup;

import com.example.myvideoeditorapp.kore.activity.TuFilterResultFragment;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterOption;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.seles.tusdk.filters.colors.TuSDKColorAdjustmentFilter;
import com.example.myvideoeditorapp.kore.view.widget.ParameterConfigViewInterface;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class TuMutibleEditAdjustFragmentBase extends TuFilterResultFragment
{
    private int a;
    
    public TuMutibleEditAdjustFragmentBase() {
        this.a = -1;
    }
    
    protected abstract void setConfigViewShowState(final boolean p0);
    
    protected abstract View buildActionButton(final String p0, final int p1);
    
    @Override
    protected void loadView(final ViewGroup viewGroup) {
        StatisticsManger.appendComponent(ComponentActType.editAdjustFragment);
        this.setFilterWrap(this.a());
        super.loadView(viewGroup);
        this.buildActionButtons();
    }
    
    protected void buildActionButtons() {
        final SelesParameters filterParameter = this.getFilterParameter();
        if (filterParameter == null || filterParameter.size() == 0) {
            return;
        }
        int n = 0;
        final Iterator<String> iterator = filterParameter.getArgKeys().iterator();
        while (iterator.hasNext()) {
            this.buildActionButton(iterator.next(), n);
            ++n;
        }
    }
    
    protected void handleConfigCompeleteButton() {
        this.setConfigViewShowState(false);
    }

    protected void handleAction(Integer var1) {
        this.a = var1;
        if (this.getConfigView() != null) {
            SelesParameters var2 = this.getFilterParameter();
            if (var2.size() > this.a) {
                String var3 = (String)var2.getArgKeys().get(this.a);
                if (var3 != null) {
                    ArrayList var4 = new ArrayList();
                    var4.add(var3);
                    ((ParameterConfigViewInterface)this.getConfigView()).setParams(var4, 0);
                    this.setConfigViewShowState(true);
                }
            }
        }
    }
    
    public int getCurrentAction() {
        return this.a;
    }
    
    @Override
    public void onParameterConfigDataChanged(final ParameterConfigViewInterface parameterConfigViewInterface, final int n, final float n2) {
        super.onParameterConfigDataChanged(parameterConfigViewInterface, this.a, n2);
    }
    
    @Override
    public void onParameterConfigRest(final ParameterConfigViewInterface parameterConfigViewInterface, final int n) {
        super.onParameterConfigRest(parameterConfigViewInterface, this.a);
    }
    
    @Override
    public float readParameterValue(final ParameterConfigViewInterface parameterConfigViewInterface, final int n) {
        return super.readParameterValue(parameterConfigViewInterface, this.a);
    }
    
    private FilterWrap a() {
        final FilterOption filterOption = new FilterOption() {
            @Override
            public SelesOutInput getFilter() {
                return new TuSDKColorAdjustmentFilter();
            }
        };
        filterOption.id = Long.MAX_VALUE;
        filterOption.canDefinition = true;
        filterOption.isInternal = true;
        return FilterWrap.creat(filterOption);
    }
}
