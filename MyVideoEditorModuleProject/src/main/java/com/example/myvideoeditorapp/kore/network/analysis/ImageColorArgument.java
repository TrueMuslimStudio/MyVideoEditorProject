// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.network.analysis;

import com.example.myvideoeditorapp.kore.utils.json.DataBase;
import com.example.myvideoeditorapp.kore.utils.json.JsonBaseBean;

import java.io.Serializable;

public class ImageColorArgument extends JsonBaseBean implements Serializable
{
    @DataBase("maxR")
    public float maxR;
    @DataBase("maxG")
    public float maxG;
    @DataBase("maxB")
    public float maxB;
    @DataBase("maxY")
    public float maxY;
    @DataBase("minR")
    public float minR;
    @DataBase("minG")
    public float minG;
    @DataBase("minB")
    public float minB;
    @DataBase("minY")
    public float minY;
    @DataBase("midR")
    public float midR;
    @DataBase("midG")
    public float midG;
    @DataBase("midB")
    public float midB;
}
