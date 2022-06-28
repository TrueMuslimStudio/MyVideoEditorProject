// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.utils.hardware;

import com.example.myvideoeditorapp.kore.utils.StringHelper;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ReflectUtils;
import android.annotation.SuppressLint;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.Hashtable;

public class TuSdkGPU
{
    private static int a;
    private static int b;
    private static String c;
    private static GpuType d;
    private static boolean e;
    private static boolean f;
    private static Hashtable<String, ArrayList<GpuType>> g;
    private static int[] h;
    
    public static int getMaxTextureSize() {
        return TuSdkGPU.a;
    }
    
    public static int getMaxTextureOptimizedSize() {
        return TuSdkGPU.b;
    }
    
    public static String getGpuInfo() {
        return TuSdkGPU.c;
    }
    
    public static GpuType getGpuType() {
        if (TuSdkGPU.d == null) {
            TuSdkGPU.d = new GpuType(0, 2000, 0, 2);
        }
        return TuSdkGPU.d;
    }
    
    public static boolean isSupporTurbo() {
        return TuSdkGPU.f;
    }
    
    public static boolean isLiveStickerSupported() {
        return getGpuType().getPerformance() >= 3;
    }
    
    public static boolean isFaceBeautySupported() {
        return getGpuType().getPerformance() >= 3;
    }
    
    public static void init(final int a, final String c) {
        if (TuSdkGPU.e || c == null) {
            return;
        }
        TuSdkGPU.e = true;
        a();
        TuSdkGPU.a = a;
        a(TuSdkGPU.c = c);
    }

    private static void a() {
        ArrayList var0 = null;
        g.put("Mali", var0 = new ArrayList());
        var0.add(new GpuTypeMali(300, 2000, 0, 2));
        var0.add(new GpuTypeMali(400, 2000, 0, 2));
        var0.add(new GpuTypeMali(400, 3000, 4, 3));
        var0.add(new GpuTypeMali(450, 4000, 4, 5));
        var0.add(new GpuTypeMali(604, 3000, 4, 3));
        var0.add(new GpuTypeMali(622, 2800, 4, 2));
        var0.add(new GpuTypeMali(624, 4000, 4, 4));
        var0.add(new GpuTypeMali(628, 4000, 6, 4));
        var0.add(new GpuTypeMali(760, 4000, 6, 4));
        var0.add(new GpuTypeMali(880, 4000, 6, 5));
        var0.add(new GpuTypeMali(7100, 4000, 6, 5));
        var0.add(new GpuTypeMali(7200, 4000, 6, 5));
        g.put("Adreno", var0 = new ArrayList());
        var0.add(new GpuTypeAdreno(130, 2000, 0, 2));
        var0.add(new GpuTypeAdreno(200, 2000, 0, 2));
        var0.add(new GpuTypeAdreno(203, 2000, 0, 2));
        var0.add(new GpuTypeAdreno(205, 2000, 0, 2));
        var0.add(new GpuTypeAdreno(220, 2000, 0, 2));
        var0.add(new GpuTypeAdreno(225, 2000, 0, 2));
        var0.add(new GpuTypeAdreno(302, 2000, 0, 2));
        var0.add(new GpuTypeAdreno(304, 2000, 0, 2));
        var0.add(new GpuTypeAdreno(305, 2000, 0, 2));
        var0.add(new GpuTypeAdreno(306, 2000, 0, 2));
        var0.add(new GpuTypeAdreno(320, 4000, 0, 2));
        var0.add(new GpuTypeAdreno(330, 4000, 0, 4));
        var0.add(new GpuTypeAdreno(405, 4000, 0, 3));
        var0.add(new GpuTypeAdreno(418, 4000, 0, 4));
        var0.add(new GpuTypeAdreno(420, 4000, 0, 4));
        var0.add(new GpuTypeAdreno(430, 4000, 0, 5));
        var0.add(new GpuTypeAdreno(505, 4000, 0, 3));
        var0.add(new GpuTypeAdreno(506, 4000, 0, 3));
        var0.add(new GpuTypeAdreno(510, 4000, 0, 4));
        var0.add(new GpuTypeAdreno(512, 4000, 0, 5));
        var0.add(new GpuTypeAdreno(530, 4000, 0, 5));
        var0.add(new GpuTypeAdreno(540, 4000, 0, 5));
        var0.add(new GpuTypeAdreno(630, 4000, 0, 5));
        g.put("PowerVR", var0 = new ArrayList());
        var0.add(new GpuTypePowerVR(530, 2000, 0, 2));
        var0.add(new GpuTypePowerVR(531, 2000, 0, 2));
        var0.add(new GpuTypePowerVR(535, 2000, 0, 2));
        var0.add(new GpuTypePowerVR(540, 2000, 0, 2));
        var0.add(new GpuTypePowerVR(543, 2000, 4, 3));
        var0.add(new GpuTypePowerVR(544, 3000, 0, 3));
        var0.add(new GpuTypePowerVR(544, 3000, 3, 4));
        var0.add(new GpuTypePowerVR(6200, 4000, 0, 5));
        var0.add(new GpuTypePowerVR(7400, 4000, 4, 5));
        var0.add(new GpuTypePowerVR(8100, 3500, 1, 5));
        var0.add(new GpuTypePowerVR(8200, 4000, 1, 5));
        g.put("Nvidia", var0 = new ArrayList());
        var0.add(new GpuTypeNvidia(3, 2500, 0, 4));
        g.put("Immersion", var0 = new ArrayList());
        var0.add(new GpuTypeImmersion(16, 3000, 0, 2));
        g.put("Vivante", var0 = new ArrayList());
        var0.add(new GpuTypeVivante(1000, 2000, 0, 2));
        var0.add(new GpuTypeVivante(2000, 2000, 0, 2));
        var0.add(new GpuTypeVivante(4000, 4000, 0, 2));
    }
    
    @SuppressLint({ "DefaultLocale" })
    private static void a(String lowerCase) {
        if (lowerCase == null) {
            return;
        }
        lowerCase = lowerCase.toLowerCase();
        boolean b = false;
        for (final Map.Entry<String, ArrayList<GpuType>> entry : TuSdkGPU.g.entrySet()) {
            if (lowerCase.contains(entry.getKey().toLowerCase())) {
                a(entry.getKey(), entry.getValue(), lowerCase);
                b = true;
                break;
            }
        }
        if (!b) {
            b(lowerCase);
        }
        b();
    }
    
    private static void a(final String s, final ArrayList<GpuType> list, final String s2) {
        final GpuType gpuType = (GpuType)ReflectUtils.classInstance(list.get(0).getClass());
        gpuType.matchInfo(s2);
        GpuType d = null;
        for (int i = list.size() - 1; i > -1; --i) {
            final GpuType gpuType2 = list.get(i);
            if (gpuType.getCode() >= gpuType2.getCode()) {
                d = gpuType2;
                break;
            }
        }
        if (d == null) {
            d = list.get(0);
        }
        TuSdkGPU.d = d;
    }
    
    private static void b(final String s) {
        if (s == null) {
            return;
        }
        if (s.contains("gc1000")) {
            TuSdkGPU.d = new GpuTypeVivante(1000, 2000, 0, 2);
        }
    }
    
    private static void b() {
        if (TuSdkGPU.d == null) {
            return;
        }
        if (TuSdkGPU.a > 0) {
            TuSdkGPU.d.setSize(Math.min(TuSdkGPU.d.getSize(), TuSdkGPU.a));
        }
        TuSdkGPU.b = TuSdkGPU.d.b;
        if (!(TuSdkGPU.d instanceof GpuTypeNvidia) || TuSdkGPU.d.getCode() > 2) {
            TuSdkGPU.f = true;
        }
        TLog.d("GPU info: %s %s", TuSdkGPU.c, TuSdkGPU.d);
    }
    
    public static boolean lowPerformance() {
        return getGpuType() instanceof GpuTypeNvidia || getGpuType() instanceof GpuTypeImmersion || getGpuType() instanceof GpuTypeVivante;
    }
    
    static {
        TuSdkGPU.b = 2000;
        TuSdkGPU.d = new GpuType(0, 2000, 0, 2);
        TuSdkGPU.g = new Hashtable<String, ArrayList<GpuType>>();
        TuSdkGPU.h = new int[] { 505, 506, 530, 540 };
    }
    
    public static class GpuTypeVivante extends GpuType
    {
        public GpuTypeVivante() {
        }
        
        public GpuTypeVivante(final int n, final int n2, final int n3, final int n4) {
            super(n, n2, n3, n4);
        }
        
        @Override
        public void matchInfo(final String s) {
            super.matchInfo(s);
            this.setCode(StringHelper.parserInt(StringHelper.matchString(s, "([0-9]+)")));
        }
    }
    
    public static class GpuType
    {
        private int a;
        private int b;
        private int c;
        private int d;
        
        public GpuType() {
        }
        
        public GpuType(final int a, final int b, final int c, final int d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
        
        public int getCode() {
            return this.a;
        }
        
        public void setCode(final int a) {
            this.a = a;
        }
        
        public int getSize() {
            return this.b;
        }
        
        public void setSize(final int b) {
            this.b = b;
        }
        
        public int getMp() {
            return this.c;
        }
        
        public void setMp(final int c) {
            this.c = c;
        }
        
        public int getPerformance() {
            return this.d;
        }
        
        public void setPerformance(final int d) {
            this.d = d;
        }
        
        public void matchInfo(final String s) {
            this.c = StringHelper.parserInt(StringHelper.matchString(s, "mp([0-9]+)"));
        }
        
        @Override
        public String toString() {
            return String.format("%s - {code: %s, mp: %s, size: %s, pf: %s}", this.getClass().getSimpleName(), this.a, this.c, this.b, this.d);
        }
    }
    
    public static class GpuTypeImmersion extends GpuType
    {
        public GpuTypeImmersion() {
        }
        
        public GpuTypeImmersion(final int n, final int n2, final int n3, final int n4) {
            super(n, n2, n3, n4);
        }
        
        @Override
        public void matchInfo(final String s) {
            super.matchInfo(s);
            this.setCode(StringHelper.parserInt(StringHelper.matchString(s, "\\.?([0-9]+)")));
        }
    }
    
    public static class GpuTypeNvidia extends GpuType
    {
        public GpuTypeNvidia() {
        }
        
        public GpuTypeNvidia(final int n, final int n2, final int n3, final int n4) {
            super(n, n2, n3, n4);
        }
        
        @Override
        public void matchInfo(final String s) {
            if (s == null) {
                return;
            }
            if (s.contains("Tegra")) {
                this.setCode(1);
                this.setSize(2000);
                this.setPerformance(2);
            }
        }
    }
    
    public static class GpuTypeAdreno extends GpuType
    {
        public GpuTypeAdreno() {
        }
        
        public GpuTypeAdreno(final int n, final int n2, final int n3, final int n4) {
            super(n, n2, n3, n4);
        }
        
        @Override
        public void matchInfo(final String s) {
            super.matchInfo(s);
            this.setCode(StringHelper.parserInt(StringHelper.matchString(s, "[ ]([0-9]+)")));
        }
    }
    
    public static class GpuTypePowerVR extends GpuType
    {
        public GpuTypePowerVR() {
        }
        
        public GpuTypePowerVR(final int n, final int n2, final int n3, final int n4) {
            super(n, n2, n3, n4);
        }
        
        @Override
        public void matchInfo(final String s) {
            super.matchInfo(s);
            if (s.indexOf("sgx") != -1) {
                StringHelper.matchString(s, "sgx[ ]*([0-9]+)");
            }
            String s2;
            if (s.indexOf("marlowe") != -1) {
                s2 = "7400";
                this.setMp(4);
            }
            else if (s.indexOf("rogue") != -1) {
                s2 = StringHelper.matchString(s, "rogue[ ]*g[a-z]*([0-9]+)");
            }
            else {
                s2 = StringHelper.matchString(s, "[ ]*g[a-z]*([0-9]+)");
            }
            this.setCode(StringHelper.parserInt(s2));
        }
    }
    
    public static class GpuTypeMali extends GpuType
    {
        public GpuTypeMali() {
        }
        
        public GpuTypeMali(final int n, final int n2, final int n3, final int n4) {
            super(n, n2, n3, n4);
        }
        
        @Override
        public void matchInfo(final String s) {
            super.matchInfo(s);
            int parserInt = StringHelper.parserInt(StringHelper.matchString(s, "-[a-z]*([0-9]+)"));
            if (StringHelper.matchString(s, "-g([0-9]+)") != null) {
                parserInt *= 100;
            }
            this.setCode(parserInt);
        }
    }
}
