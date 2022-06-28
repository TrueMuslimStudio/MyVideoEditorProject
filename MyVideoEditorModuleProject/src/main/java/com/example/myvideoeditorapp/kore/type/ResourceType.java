// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.type;

public enum ResourceType
{
    anim("anim"), 
    attr("attr"), 
    color("color"), 
    dimen("dimen"), 
    drawable("drawable"), 
    id("id"), 
    layout("layout"), 
    menu("menu"), 
    raw("raw"), 
    string("string"), 
    style("style"), 
    styleable("styleable");
    
    private String a;
    
    private ResourceType(final String a) {
        this.a = a;
    }
    
    public String getKey() {
        return this.a;
    }
}
