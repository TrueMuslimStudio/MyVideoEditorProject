//
// Decompiled by Procyon v0.5.36
//

package com.example.myvideoeditorapp.kore.secret;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.text.TextPaint;
import android.text.TextUtils;

import com.example.myvideoeditorapp.kore.TuSdkBundle;
import com.example.myvideoeditorapp.kore.TuSdkConfigs;
import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.network.TuSdkAuthInfo;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpEngine;
import com.example.myvideoeditorapp.kore.seles.sources.SelesPicture;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.utils.NativeLibraryHelper;
import com.example.myvideoeditorapp.kore.utils.StringHelper;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.json.JsonHelper;

import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SdkValid {
    public static final boolean isInit;
    public static final SdkValid shared;
    private TuSdkConfigs a;
    private String b;
    private boolean c;

    private SdkValid() {
    }

    private synchronized boolean b() {
        return this.c;
    }

    private synchronized void a(boolean var1) {
        this.c = var1;
    }

    String a() {
        return this.b;
    }

    public TuSdkConfigs geTuSdkConfigs() {
        return this.a;
    }

    public boolean isVaild() {
        return jniHasValidWithDevType();
    }

    public boolean isExpired() {
        return this.imageServiceExpire() && this.videoServiceExpire() && this.evaServiceExpire();
    }

    public boolean imageServiceExpire() {
        return jniImageServiceExpire();
    }

    public boolean videoServiceExpire() {
        return jniVideoServiceExpire();
    }

    public boolean evaServiceExpire() {
        return jniEvaServiceExpire();
    }

    public long imageServiceExpireSeconds() {
        return jniImageServiceExpireSeconds();
    }

    public long videoServiceExpireSeconds() {
        return jniVideoServiceExpireSeconds();
    }

    public long evaServiceExpireSeconds() {
        return jniEvaServiceExpireSeconds();
    }

    public int appType() {
        if (this.a == null) {
            jniAppType();
        }

        if (this.a.appType <= 0) {
            this.a.appType = jniAppType();
        }

        return this.a.appType;
    }

    public String getDeveloperId() {
        return jniDeveloperID();
    }

    public int maxImageSide() {
        return jniCheckAuthor(20001);
    }

    public int maxStickers() {
        return jniCheckAuthor(20002);
    }

    public int localFilterCount() {
        return jniCheckAuthor(20003);
    }

    public int localStickerCount() {
        return jniCheckAuthor(20004);
    }

    public boolean renderFilterThumb() {
        return jniCheckAuthor(20006) > 0;
    }

    public boolean smudgeEnabled() {
        return jniCheckAuthor(20007) > 0;
    }

    public boolean paintEnabled() {
        return jniCheckAuthor(20008) > 0;
    }

    public boolean wipeFilterEnabled() {
        return jniCheckAuthor(20007) > 0;
    }

    public boolean hdrFilterEnabled() {
        return jniCheckAuthor(20009) > 0;
    }

    public int beautyLevel() {
        return jniCheckAuthor(20010);
    }

    public boolean videoRecordEnabled() {
        return jniCheckAuthor(30000) > 0;
    }

    public boolean videoDurationEnabled() {
        return jniCheckAuthor(30001) > 0;
    }

    public boolean videoEditEnabled() {
        return jniCheckAuthor(30002) > 0;
    }

    public boolean videoRecordContinuousEnabled() {
        return jniCheckAuthor(30003) > 0;
    }

    public boolean videoCameraShotEnabled() {
        return jniCheckAuthor(30004) > 0;
    }

    public boolean videoCameraStickerEnabled() {
        return jniCheckAuthor(30005) > 0;
    }

    public boolean videoCameraBitrateEnabled() {
        return jniCheckAuthor(30006) > 0;
    }

    public boolean videoCameraMonsterFaceSupport() {
        return jniCheckAuthor(30017) > 0;
    }

    public boolean videoEditorMusicEnabled() {
        return jniCheckAuthor(30007) > 0;
    }

    public boolean videoEditorStickerEnabled() {
        return jniCheckAuthor(30008) > 0;
    }

    public boolean videoEditorFilterEnabled() {
        return jniCheckAuthor(30009) > 0;
    }

    public boolean videoEditorBitrateEnabled() {
        return jniCheckAuthor(30010) > 0;
    }

    public boolean videoEditorResolutionEnabled() {
        return jniCheckAuthor(30011) > 0;
    }

    public boolean videoEditorEffectsfilterEnabled() {
        return jniCheckAuthor(30012) > 0;
    }

    public boolean videoEditorParticleEffectsFilterEnabled() {
        return jniCheckAuthor(30013) > 0;
    }

    public boolean videoEditorTextEffectsEnabled() {
        return jniCheckAuthor(30014) > 0;
    }

    public boolean CosmeticEnabled() {
        return jniCheckAuthor(20011) > 0;
    }

    public boolean videoEditorComicEffectsSupport() {
        return jniCheckAuthor(30015) > 0;
    }

    public boolean videoEditorMonsterFaceSupport() {
        return jniCheckAuthor(30016) > 0;
    }

    public boolean videoEditorTransitionEffectsSupport() {
        return jniCheckAuthor(30018) > 0;
    }

    public boolean videoImageComposeSupport() {
        return jniCheckAuthor(30019) > 0;
    }

    public boolean audioPitchEffectsSupport() {
        return jniCheckAuthor(50000) > 0;
    }

    public boolean audioResampleEffectsSupport() {
        return jniCheckAuthor(50001) > 0;
    }

    public boolean videoStreamEnabled() {
        return jniCheckAuthor(30020) > 0;
    }

    public boolean filterAPIEnabled() {
        return jniFilterAPIEnabled();
    }

    public boolean filterAPIValidWithID(long var1) {
        return jniFilterAPIValidWithID(var1);
    }

    public boolean evaReplaceTxt() {
        return jniCheckAuthor(40000) > 0;
    }

    public boolean evaReplaceImg() {
        return jniCheckAuthor(40001) > 0;
    }

    public boolean evaReplaceVideo() {
        return jniCheckAuthor(40002) > 0;
    }

    public boolean evaReplaceAudio() {
        return jniCheckAuthor(40003) > 0;
    }

    public boolean evaWipeCopyright() {
        return jniCheckAuthor(40004) > 0;
    }

    public boolean evaExportBitratet() {
        return jniCheckAuthor(40005) > 0;
    }

    public boolean evaExportResolution() {
        return jniCheckAuthor(40006) > 0;
    }

    public boolean evaExportAddMarkimage() {
        return jniCheckAuthor(40007) > 0;
    }

    public FilterWrap getFilterWrapWithCode(String var1) {
        return jniGetFilterWrapWithCode(var1);
    }

    public boolean sdkValid(Context var1, String var2, String var3) {
        if (var2 != null && var1 != null) {
            this.b = var2;
            if (jniInit(var1, var2)) {
                this.b(var3);
            }

            return this.isVaild();
        } else {
            return false;
        }
    }

    public boolean sdkValid() {
        return jniPassDoubleValid();
    }

    private void b(String var1) {
        if (this.a == null) {
            String var2 = TuSdkBundle.sdkBundleOther("lsq_tusdk_configs.json");
            String var3 = TuSdkContext.getAssetsText(var2);
            this.a = new TuSdkConfigs(JsonHelper.json(var3));
            if (this.a == null) {
                this.a = null;
                TLog.e("Configuration not found! Please see: http://tusdk.com/docs/android/get-started", new Object[0]);
            } else {
                String var4 = null;
                if (StringHelper.isNotBlank(var1) && this.a.masters != null) {
                    var4 = (String)this.a.masters.get(var1);
                }

                this.a.masters = null;
                if (var4 == null && StringHelper.isNotBlank(this.a.master)) {
                    var4 = this.a.master;
                }

                if (!StringHelper.isBlank(var4) && var4.trim().length() >= 11) {
                    var4 = var4.trim();
                    this.a.master = var4;
                    boolean var5 = jniLoadDevelopInfo(var4);
                    if (!var5 || this.imageServiceExpire() && this.imageServiceExpireSeconds() > 0L || this.videoServiceExpire() && this.videoServiceExpireSeconds() > 0L || this.evaServiceExpire() && this.evaServiceExpireSeconds() > 0L) {
                        TuSdkAuth.LocalAuthInfo var6 = TuSdkAuth.shared().localAuthInfo();
                        if (var6 != null && var6.remoteAuthInfo != null && var6.remoteAuthInfo.isValid()) {
                            var5 = jniLoadDevelopInfo(var6.remoteAuthInfo.masterKey);
                        }
                    }

                    if (!var5) {
                        this.a = null;
                        TLog.e("Incorrect master key! Please see: http://tusdk.com/docs/help/package-name-and-app-key", new Object[0]);
                    }

                } else {
                    this.a = null;
                    TLog.e("Master key not found! Please see: http://tusdk.com/docs/android/get-started", new Object[0]);
                }
            }
        }
    }

    private String a(long var1, String var3, SdkResourceType var4) {
        if (var3 != null && StringHelper.isNotBlank(var3)) {
            String var5 = shared.decodeMaster(var3);
            JSONObject var6 = JsonHelper.json(var5);
            if (var6 == null) {
                return null;
            } else {
                JSONArray var7 = null;

                try {
                    int var8;
                    switch(var4) {
                        case ResourceSticker:
                            var7 = var6.getJSONArray("stickerGroups");
                        case ResourceFilter:
                        case ResourceBrush:
                        case ResourceVideoFilter:
                        default:
                            if (var7 == null) {
                                return null;
                            }

                            var8 = 0;
                    }

                    while(var8 < var7.length()) {
                        JSONObject var9 = (JSONObject)var7.get(var8);
                        if (var9.getLong("id") == var1) {
                            return var9.getString("valid_key");
                        }

                        ++var8;
                    }
                } catch (JSONException var10) {
                    var10.printStackTrace();
                }

                return null;
            }
        } else {
            return null;
        }
    }

    public String stickerGroupValidKey(long var1, String var3) {
        return this.a(var1, var3, SdkResourceType.ResourceSticker);
    }

    public void vaildAndDraw(Canvas var1) {
        if (jniCheckAuthor(20000) <= 0) {
            float var2 = (float)TuSdkContext.sp2px(10);
            TextPaint var3 = new TextPaint(1);
            var3.setTextSize(var2);
            var3.setColor(1090519039);
            var3.setShadowLayer(2.0F, 1.0F, 1.0F, 1073741824);
            FontMetricsInt var4 = var3.getFontMetricsInt();
            var3.setTextAlign(Align.LEFT);
            var1.drawText("Technology by TuSDK", (float)var4.bottom, (float)var1.getHeight() - var2 + (float)var4.bottom, var3);
        }
    }

    public void checkAppAuth() {
        if (!this.b() && TuSdkHttpEngine.shared() != null) {
            TuSdkAuth.shared().requestRemoteAuthInfo(new TuSdkAuth.AuthInfoCallback() {
                public void onAuthInfo(TuSdkAuthInfo var1) {
                    SdkValid.this.a(false);
                    if (var1 != null && var1.isValid()) {
                        boolean var2 = SdkValid.jniLoadDevelopInfo(var1.masterKey);
                        if (!var2) {
                            TLog.e("Error while parsing lastest master config from sesrver", new Object[0]);
                            StatisticsManger.appendComponent(ComponentActType.updateAppAuthActionFail);
                            if (SdkValid.this.a != null) {
                                SdkValid.jniLoadDevelopInfo(SdkValid.this.a.master);
                            }
                        } else {
                            StatisticsManger.appendComponent(ComponentActType.updateAppAuthActionSuccess);
                        }

                    }
                }
            });
        }
    }

    public boolean loadFilterConfig(String var1) {
        return StringHelper.isBlank(var1) ? false : jniLoadFilterConfig(var1);
    }

    public String loadFilterGroup(String var1, String var2) {
        return this.a(var1, var2, SdkResourceType.ResourceFilter, SdkResourceType.ResourceVideoFilter);
    }

    public String loadStickerGroup(String var1, String var2) {
        return this.a(var1, var2, SdkResourceType.ResourceSticker);
    }

    public String loadString(String var1) {
        return jniLoadText(var1);
    }

    public String loadBrushGroup(String var1, String var2) {
        return this.a(var1, var2, SdkResourceType.ResourceBrush);
    }

    private String a(String var1, String var2, SdkResourceType var3) {
        return !StringHelper.isBlank(var1) && var3 != null ? jniLoadResource(var1, var2, var3.type(), 0) : null;
    }

    private String a(String var1, String var2, SdkResourceType var3, SdkResourceType var4) {
        return !StringHelper.isBlank(var1) && var3 != null ? jniLoadResource(var1, var2, var3.type(), var4.type()) : null;
    }

    public void removeFilterGroup(long var1) {
        this.a(var1, SdkResourceType.ResourceFilter);
    }

    public void removeStickerGroup(long var1) {
        this.a(var1, SdkResourceType.ResourceSticker);
    }

    public void removeBrushGroup(long var1) {
        this.a(var1, SdkResourceType.ResourceBrush);
    }

    private void a(long var1, SdkResourceType var3) {
        if (var1 >= 1L && var3 != null) {
            jniRemoveResource(var1, var3.type());
        }
    }

    public Bitmap readFilterThumb(long var1, long var3) {
        return this.a(var1, var3, SdkResourceType.ResourceFilter);
    }

    public Bitmap readStickerThumb(long var1, long var3) {
        return this.a(var1, var3, SdkResourceType.ResourceSticker);
    }

    public Bitmap readBrushThumb(long var1, long var3) {
        return this.a(var1, var3, SdkResourceType.ResourceBrush);
    }

    public int readEvaEnableFunctions() {
        return jniCheckAuthor(40008);
    }

    private Bitmap a(long var1, long var3, SdkResourceType var5) {
        return var1 >= 1L && var5 != null ? jniReadThumb(TuSdkContext.context(), var1, var3, var5.type()) : null;
    }

    public List<SelesPicture> readInternalTextures(List<String> var1) {
        if (var1 != null && var1.size() != 0) {
            SelesPicture[] var2 = jniReadInternalTextures(TuSdkContext.context(), var1.toArray());
            return var2 != null && var2.length != 0 ? Arrays.asList(var2) : null;
        } else {
            return null;
        }
    }

    public List<SelesPicture> readTextures(long var1, List<String> var3) {
        if (var1 >= 1L && var3 != null && var3.size() != 0) {
            SelesPicture[] var4 = jniReadTextures(TuSdkContext.context(), var1, var3.toArray());
            return var4 != null && var4.length != 0 ? Arrays.asList(var4) : null;
        } else {
            return null;
        }
    }

    public String compileShader(String var1, int var2, int[] var3) {
        return var1 != null && var3 != null && var3.length != 0 ? jniCompileShader(var1.trim(), var2, var3) : null;
    }

    public Bitmap readSticker(long var1, String var3) {
        return var1 >= 1L && var3 != null ? jniReadSticker(TuSdkContext.context(), var1, var3) : null;
    }

    public Bitmap readBrush(long var1, String var3) {
        return var1 >= 1L && var3 != null ? jniReadBrush(TuSdkContext.context(), var1, var3) : null;
    }

    public void saveLogStash(String var1, String var2) {
        jniSaveLogStashFile(var1, var2);
    }

    public String decodeMaster(String var1) {
        return jniDecodeMaster(var1);
    }

    public boolean checkEvaFileOwner(String var1) {
        String[] var2 = this.a().split("-");
        return TextUtils.equals(var1, var2[var2.length - 1]);
    }

    private static native boolean jniInit(Context var0, String var1);

    private static native String jniDeveloperID();

    private static native boolean jniLoadDevelopInfo(String var0);

    private static native String jniDecodeMaster(String var0);

    private static native boolean jniLoadFilterConfig(String var0);

    private static native String jniLoadResource(String var0, String var1, int var2, int var3);

    private static native String jniLoadText(String var0);

    private static native void jniRemoveResource(long var0, int var2);

    private static native Bitmap jniReadThumb(Context var0, long var1, long var3, int var5);

    private static native SelesPicture[] jniReadInternalTextures(Context var0, Object[] var1);

    private static native SelesPicture[] jniReadTextures(Context var0, long var1, Object[] var3);

    private static native String jniCompileShader(String var0, int var1, int[] var2);

    private static native Bitmap jniReadSticker(Context var0, long var1, String var3);

    private static native Bitmap jniReadBrush(Context var0, long var1, String var3);

    private static native boolean jniHasValidWithDevType();

    private static native int jniAppType();

    private static native boolean jniImageServiceExpire();

    private static native boolean jniVideoServiceExpire();

    private static native boolean jniEvaServiceExpire();

    private static native long jniImageServiceExpireSeconds();

    private static native long jniVideoServiceExpireSeconds();

    private static native long jniEvaServiceExpireSeconds();

    private static native boolean jniPassDoubleValid();

    private static native int jniCheckAuthor(int var0);

    private static native boolean jniFilterAPIEnabled();

    private static native boolean jniFilterAPIValidWithID(long var0);

    private static native FilterWrap jniGetFilterWrapWithCode(String var0);

    private static native void jniSaveLogStashFile(String var0, String var1);

    static {
        NativeLibraryHelper.shared().loadLibrary(NativeLibraryHelper.NativeLibType.LIB_CORE);
        isInit = true;
        shared = new SdkValid();
    }

    public static enum SdkResourceType {
        ResourceFilter(1),
        ResourceSticker(2),
        ResourceBrush(3),
        ResourceVideoFilter(4);

        private int a;

        private SdkResourceType(int var3) {
            this.a = var3;
        }

        public int type() {
            return this.a;
        }
    }
}
