// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.edit;

import android.view.ViewGroup;

import com.example.myvideoeditorapp.kore.TuSdkResult;
import com.example.myvideoeditorapp.kore.activity.TuImageResultFragment;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.secret.SdkValid;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;

import java.util.ArrayList;
import java.util.List;

public abstract class TuEditFragmentBase extends TuImageResultFragment
{
    private List<TuEditActionType> a;
    
    @Override
    protected void notifyProcessing(final TuSdkResult tuSdkResult) {
    }
    
    @Override
    protected boolean asyncNotifyProcessing(final TuSdkResult tuSdkResult) {
        return false;
    }
    
    @Override
    protected void loadView(final ViewGroup viewGroup) {
    }
    
    @Override
    protected void viewDidLoad(final ViewGroup viewGroup) {
        StatisticsManger.appendComponent(ComponentActType.multipleEditFragment);
    }
    
    private List<TuEditActionType> a() {
        final ArrayList<TuEditActionType> list = new ArrayList<TuEditActionType>();
        list.add(TuEditActionType.TypeCuter);
        list.add(TuEditActionType.TypeTurn);
        if (SdkValid.shared.wipeFilterEnabled()) {
            list.add(TuEditActionType.TypeWipeFilter);
        }
        list.add(TuEditActionType.TypeAperture);
        return list;
    }
    
    protected List<TuEditActionType> getModules() {
        if (this.a == null || this.a.size() == 0) {
            this.a = this.a();
        }
        final List<TuEditActionType> a = this.a();
        a.retainAll(this.a);
        return a;
    }
    
    public void setModules(final List<TuEditActionType> a) {
        this.a = a;
    }
}
