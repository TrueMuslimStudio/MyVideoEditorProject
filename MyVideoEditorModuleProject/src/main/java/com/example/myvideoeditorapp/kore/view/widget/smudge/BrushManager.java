// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget.smudge;

import java.util.List;
import com.example.myvideoeditorapp.kore.TuSdkConfigs;

public class BrushManager
{
    private static BrushManager a;
    private BrushLocalPackage b;
    
    public static BrushManager shared() {
        return BrushManager.a;
    }
    
    public static BrushManager init(final TuSdkConfigs tuSdkConfigs) {
        if (BrushManager.a == null && tuSdkConfigs != null) {
            BrushManager.a = new BrushManager(tuSdkConfigs);
        }
        return BrushManager.a;
    }
    
    public List<String> getBrushNames() {
        return this.b.getCodes();
    }
    
    public boolean isInited() {
        return this.b.isInited();
    }
    
    private BrushManager(final TuSdkConfigs tuSdkConfigs) {
        this.b = BrushLocalPackage.init(tuSdkConfigs);
    }
    
    public BrushData getBrushWithCode(final String s) {
        return this.b.getBrushWithCode(s);
    }
}
