// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore;

import org.json.JSONArray;
import com.example.myvideoeditorapp.kore.utils.json.JsonHelper;
import org.json.JSONObject;
import java.util.HashMap;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterGroup;
import java.util.ArrayList;
import com.example.myvideoeditorapp.kore.utils.json.DataBase;
import java.io.Serializable;
import com.example.myvideoeditorapp.kore.utils.json.JsonBaseBean;
import com.example.myvideoeditorapp.kore.view.widget.smudge.BrushGroup;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerCategory;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerGroup;

public class TuSdkConfigs extends JsonBaseBean implements Serializable
{
    public static final int APP_TYPE_Image = 1;
    public static final int APP_TYPE_LIVE = 64;
    public static final int APP_TYPE_SHORT_VIDEO = 128;
    public static final int APP_TYPE_VIDEO_EVA = 8192;
    @DataBase("app_type")
    public int appType;
    @DataBase("filterGroups")
    public ArrayList<FilterGroup> filterGroups;
    @DataBase("stickerCategories")
    public ArrayList<StickerCategory> stickerCategories;
    @DataBase("stickerGroups")
    public ArrayList<StickerGroup> stickerGroups;
    @DataBase("brushGroups")
    public ArrayList<BrushGroup> brushGroups;
    @DataBase("master")
    public String master;
    @DataBase("masters")
    public HashMap<String, String> masters;
    
    public TuSdkConfigs() {
    }
    
    public TuSdkConfigs(final JSONObject jsonObject) {
        this.deserialize(jsonObject);
    }
    
    public void deserialize(final JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        this.master = jsonObject.optString("master", (String)null);
        this.masters = JsonHelper.toHashMap(JsonHelper.getJSONObject(jsonObject, "masters"));
        this.appType = jsonObject.optInt("app_type");
        final JSONArray jsonArray = JsonHelper.getJSONArray(jsonObject, "filterGroups");
        if (jsonArray != null && jsonArray.length() > 0) {
            this.filterGroups = new ArrayList<FilterGroup>(jsonArray.length());
            for (int i = 0; i < jsonArray.length(); ++i) {
                this.filterGroups.add(new FilterGroup(jsonArray.optJSONObject(i)));
            }
        }
        final JSONArray jsonArray2 = JsonHelper.getJSONArray(jsonObject, "stickerCategories");
        if (jsonArray2 != null && jsonArray2.length() > 0) {
            this.stickerCategories = new ArrayList<StickerCategory>(jsonArray2.length());
            for (int j = 0; j < jsonArray2.length(); ++j) {
                this.stickerCategories.add(new StickerCategory(jsonArray2.optJSONObject(j)));
            }
        }
        final JSONArray jsonArray3 = JsonHelper.getJSONArray(jsonObject, "stickerGroups");
        if (jsonArray3 != null && jsonArray3.length() > 0) {
            this.stickerGroups = new ArrayList<StickerGroup>(jsonArray3.length());
            for (int k = 0; k < jsonArray3.length(); ++k) {
                this.stickerGroups.add(new StickerGroup(jsonArray3.optJSONObject(k)));
            }
        }
        final JSONArray jsonArray4 = JsonHelper.getJSONArray(jsonObject, "brushGroups");
        if (jsonArray4 != null && jsonArray4.length() > 0) {
            this.brushGroups = new ArrayList<BrushGroup>(jsonArray4.length());
            for (int l = 0; l < jsonArray4.length(); ++l) {
                this.brushGroups.add(new BrushGroup(jsonArray4.optJSONObject(l)));
            }
        }
    }
}
