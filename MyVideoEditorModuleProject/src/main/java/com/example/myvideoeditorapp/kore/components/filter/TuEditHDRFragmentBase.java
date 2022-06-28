// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.filter;

import android.graphics.Bitmap;
import android.view.ViewGroup;

import com.example.myvideoeditorapp.kore.activity.TuFilterResultFragment;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.secret.SdkValid;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import com.example.myvideoeditorapp.kore.seles.sources.SelesPicture;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterLocalPackage;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterOption;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.seles.tusdk.filters.colors.TuSDKColorHDRFilter;
import com.example.myvideoeditorapp.kore.utils.TLog;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public abstract class TuEditHDRFragmentBase extends TuFilterResultFragment
{
    private ByteBuffer a;
    private FilterOption.RunTimeTextureDelegate b;
    
    public TuEditHDRFragmentBase() {
        this.b = new FilterOption.RunTimeTextureDelegate() {
            @Override
            public List<SelesPicture> getRunTimeTextures() {
                final ArrayList<SelesPicture> list = new ArrayList<SelesPicture>();
                list.add(new SelesPicture(TuEditHDRFragmentBase.this.a, 256, 64));
                return list;
            }
        };
    }
    
    @Override
    protected void loadView(final ViewGroup viewGroup) {
        super.loadView(viewGroup);
        StatisticsManger.appendComponent(ComponentActType.editHDRFragment);
        this.setFilterWrap(FilterLocalPackage.shared().getFilterWrap(null));
    }
    
    private FilterWrap a() {
        final FilterOption filterOption = new FilterOption(this.b) {
            @Override
            public SelesOutInput getFilter() {
                return new TuSDKColorHDRFilter();
            }
        };
        filterOption.id = Long.MAX_VALUE;
        filterOption.canDefinition = true;
        filterOption.isInternal = true;
        final ArrayList<String> internalTextures = new ArrayList<String>();
        internalTextures.add("d78aa55b64bb63f97bc5feb3c6ba5600");
        filterOption.internalTextures = internalTextures;
        return FilterWrap.creat(filterOption);
    }
    
    @Override
    protected boolean preProcessWithImage(final Bitmap bitmap) {
        if (!SdkValid.shared.hdrFilterEnabled()) {
            TLog.e("You are not allowed to use the HDR feature, please see http://tusdk.com", new Object[0]);
            return false;
        }
        this.a = TuSDKColorHDRFilter.getClipHistBuffer(bitmap);
        return true;
    }
    
    @Override
    protected void postProcessWithImage(final Bitmap bitmap) {
        this.setImageViewFilter(this.a());
        this.refreshConfigView();
        super.postProcessWithImage(bitmap);
    }
}
