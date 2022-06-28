// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import java.util.Calendar;
import com.example.myvideoeditorapp.kore.utils.DateHelper;

public class TuDateHelper extends DateHelper
{
    public static String timestampSNS(final Calendar calendar) {
        return DateHelper.timestampSNS(calendar, TuSdkContext.getString("lsq_date_seconds_ago"), TuSdkContext.getString("lsq_date_minutes_ago"), TuSdkContext.getString("lsq_date_hours_ago"));
    }
}
