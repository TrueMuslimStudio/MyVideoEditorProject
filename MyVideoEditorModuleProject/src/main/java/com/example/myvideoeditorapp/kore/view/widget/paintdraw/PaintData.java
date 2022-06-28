// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget.paintdraw;

import java.io.Serializable;
import com.example.myvideoeditorapp.kore.utils.json.JsonBaseBean;

public class PaintData extends JsonBaseBean implements Serializable
{
    private Object a;
    private PaintType b;
    
    public PaintData(final int i, final PaintType b) {
        this.b = b;
        this.a = i;
    }
    
    public Object getData() {
        return this.a;
    }
    
    public void setData(final Object a) {
        this.a = a;
    }
    
    public PaintType getPaintType() {
        return this.b;
    }
    
    public void setPaintType(final PaintType b) {
        this.b = b;
    }
    
    public enum PaintType
    {
        Color, 
        Image;
    }
}
