// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.network;

import com.example.myvideoeditorapp.kore.TuSdk;
import com.example.myvideoeditorapp.kore.type.DownloadTaskStatus;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.json.DataBase;
import com.example.myvideoeditorapp.kore.utils.json.JsonBaseBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;

public class TuSdkDownloadItem extends JsonBaseBean implements Serializable
{
    @DataBase("id")
    public long id;
    @DataBase("key")
    public String key;
    @DataBase("status")
    public int status;
    @DataBase("progress")
    public float progress;
    public String userId;
    public TuSdkDownloadTask.DownloadTaskType type;
    public String fileName;
    public String fileId;
    
    public DownloadTaskStatus getStatus() {
        return DownloadTaskStatus.getType(this.status);
    }
    
    public void setStatus(final DownloadTaskStatus downloadTaskStatus) {
        if (downloadTaskStatus != null) {
            this.status = downloadTaskStatus.getFlag();
        }
        else {
            this.status = 0;
        }
    }
    
    public File localDownloadPath() {
        if (this.fileName == null) {
            this.fileName = String.format("tusdk_%s_%s.gsce", this.type.getAct(), this.id);
        }
        return new File(TuSdk.getAppDownloadPath(), this.fileName);
    }
    
    public File localTempPath() {
        return new File(TuSdk.getAppTempPath(), String.format("_download_tusdk_%s_%s.tmp", this.type.getAct(), this.id));
    }
    
    public JSONObject getStatusChangeData() {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt("fun", (Object)"statusChange");
            jsonObject.putOpt("type", (Object)this.type.getAct());
            jsonObject.putOpt("item", (Object)this.buildJson());
        }
        catch (JSONException ex) {
            TLog.e((Throwable)ex, "StickerLocalPackage getAllDatas", new Object[0]);
        }
        return jsonObject;
    }
}
