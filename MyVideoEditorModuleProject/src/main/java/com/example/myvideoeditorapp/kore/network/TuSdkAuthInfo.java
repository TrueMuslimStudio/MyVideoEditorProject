// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.network;

import com.example.myvideoeditorapp.kore.utils.StringHelper;
import com.example.myvideoeditorapp.kore.utils.json.DataBase;
import com.example.myvideoeditorapp.kore.utils.json.JsonBaseBean;


import java.io.Serializable;
import java.util.Calendar;

public class TuSdkAuthInfo extends JsonBaseBean implements Serializable
{
    @DataBase("last_updated")
    public Calendar lastUpdatedDate;
    @DataBase("next_request")
    public Calendar nextCheckDate;
    @DataBase("service_expire")
    public Calendar service_expire;
    @DataBase("master_key")
    public String masterKey;
    
    public boolean isValid() {
        return StringHelper.isNotBlank(this.masterKey) && this.masterKey.trim().length() > 11;
    }
    
    @Override
    public String toString() {
        return "masterKey : " + this.masterKey + " nextCheckDate :" + this.nextCheckDate + " lastUpdatedDate :" + this.lastUpdatedDate;
    }
}
