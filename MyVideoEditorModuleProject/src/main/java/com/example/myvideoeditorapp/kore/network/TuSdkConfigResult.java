// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.network;

import com.example.myvideoeditorapp.kore.utils.json.DataBase;
import com.example.myvideoeditorapp.kore.utils.json.JsonBaseBean;

import java.io.Serializable;
import java.util.Calendar;

public class TuSdkConfigResult extends JsonBaseBean implements Serializable
{
    @DataBase("last_updated")
    public Calendar lastUpdatedDate;
    @DataBase("next_request")
    public Calendar nextCheckDate;
    @DataBase("master_key")
    public String masterKey;
}
