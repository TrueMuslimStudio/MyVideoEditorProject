// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.egl;

import com.example.myvideoeditorapp.kore.utils.hardware.HardwareHelper;

import java.util.HashMap;
import java.util.Map;

public class SelesVirtualDisplayPatch
{
    private static HashMap<String, String> a;
    
    public static boolean isNeedVirtualDisplayPatch() {
        for (final Map.Entry<String, String> entry : SelesVirtualDisplayPatch.a.entrySet()) {
            if (HardwareHelper.isMatchDeviceModelAndManuFacturer(entry.getKey(), entry.getValue())) {
                return true;
            }
        }
        return false;
    }
    
    static {
        (SelesVirtualDisplayPatch.a = new HashMap<String, String>()).put("MP1605", "Meitu");
        SelesVirtualDisplayPatch.a.put("MHA-AL00", "HUAWEI");
        SelesVirtualDisplayPatch.a.put("BKL-AL00", "HUAWEI");
        SelesVirtualDisplayPatch.a.put("GM1910", "OnePlus");
    }
}
