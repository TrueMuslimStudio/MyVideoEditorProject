// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.utils.hardware;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TuSdkCorePatch
{
    private static final Map<String, String> a;
    private static final Map<String, String> b;
    
    public static boolean applyThumbRenderPatch() {
        boolean matchDeviceModelAndManuFacturer = false;
        for (final Map.Entry<String, String> entry : TuSdkCorePatch.a.entrySet()) {
            matchDeviceModelAndManuFacturer = HardwareHelper.isMatchDeviceModelAndManuFacturer(entry.getKey(), entry.getValue());
            if (matchDeviceModelAndManuFacturer) {
                break;
            }
        }
        return matchDeviceModelAndManuFacturer;
    }
    
    public static boolean applyDeletedProgramPatch() {
        boolean matchDeviceModelAndManuFacturer = false;
        for (final Map.Entry<String, String> entry : TuSdkCorePatch.b.entrySet()) {
            matchDeviceModelAndManuFacturer = HardwareHelper.isMatchDeviceModelAndManuFacturer(entry.getKey(), entry.getValue());
            if (matchDeviceModelAndManuFacturer) {
                break;
            }
        }
        return matchDeviceModelAndManuFacturer;
    }
    
    static {
        a = new HashMap<String, String>();
        b = new HashMap<String, String>();
        TuSdkCorePatch.a.put("V1732A", "VIVO");
        TuSdkCorePatch.a.put("vivo Y71A", "VIVO");
        TuSdkCorePatch.a.put("SM-J3300", "Samsung");
        TuSdkCorePatch.b.put("Redmi 6", "XIAOMI");
        TuSdkCorePatch.b.put("Redmi 6A", "XIAOMI");
        TuSdkCorePatch.b.put("Redmi Note 3", "XIAOMI");
        TuSdkCorePatch.b.put("V1732A", "VIVO");
        TuSdkCorePatch.b.put("JAT-AL00", "HUAWEI");
        TuSdkCorePatch.b.put("DUA-AL00", "HUAWEI");
        TuSdkCorePatch.b.put("OP486C", "OPPO");
        TuSdkCorePatch.b.put("RMX1941", "realme");
        TuSdkCorePatch.b.put("RMX1945", "realme");
        TuSdkCorePatch.b.put("RMX1943", "realme");
        TuSdkCorePatch.b.put("a10s", "Samsung");
        TuSdkCorePatch.b.put("HWMRD-M1", "HUAWEI");
        TuSdkCorePatch.b.put("HWJAT-M", "HUAWEI");
        TuSdkCorePatch.b.put("HWAMN-M", "HUAWEI");
        TuSdkCorePatch.b.put("HWKSA-M", "HUAWEI");
        TuSdkCorePatch.b.put("X650C", "Infinix");
        TuSdkCorePatch.b.put("X652B", "Infinix");
        TuSdkCorePatch.b.put("X652A", "Infinix");
        TuSdkCorePatch.b.put("X653", "Infinix");
        TuSdkCorePatch.b.put("X653C", "Infinix");
        TuSdkCorePatch.b.put("X650B", "Infinix");
        TuSdkCorePatch.b.put("X626B", "Infinix");
        TuSdkCorePatch.b.put("KC3", "TECNO");
        TuSdkCorePatch.b.put("KC2", "TECNO");
        TuSdkCorePatch.b.put("KC6", "TECNO");
        TuSdkCorePatch.b.put("KC8", "TECNO");
        TuSdkCorePatch.b.put("BB4k", "TECNO");
        TuSdkCorePatch.b.put("Z12", "Symphony");
        TuSdkCorePatch.b.put("L6005", "itel");
        TuSdkCorePatch.b.put("L5503", "itel");
        TuSdkCorePatch.b.put("L6002P", "itel");
        TuSdkCorePatch.b.put("i97", "i97");
        TuSdkCorePatch.b.put("WSP_sprout", "Nokia");
        TuSdkCorePatch.b.put("ROON_sprout", "Nokia");
        TuSdkCorePatch.b.put("ROO_sprout", "Nokia");
        TuSdkCorePatch.b.put("vivo 1904", "vivo");
        TuSdkCorePatch.b.put("vivo 1901", "vivo");
    }
}
