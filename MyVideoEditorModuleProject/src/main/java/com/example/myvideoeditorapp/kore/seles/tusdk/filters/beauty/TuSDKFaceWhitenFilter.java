// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.beauty;

import android.opengl.GLES20;
import com.example.myvideoeditorapp.kore.utils.TLog;
import java.util.Iterator;
import com.example.myvideoeditorapp.kore.seles.sources.SelesPicture;
import java.util.List;
import com.example.myvideoeditorapp.kore.secret.SdkValid;
import java.util.ArrayList;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;

public class TuSDKFaceWhitenFilter extends SelesFilter implements SelesParameters.FilterParameterInterface
{
    private int b;
    private int c;
    private float d;
    SelesFramebuffer a;
    
    public TuSDKFaceWhitenFilter() {
        super("-sbeautyf5");
    }
    
    @Override
    protected void onInitOnGLThread() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("69a05c2166a8d04f10eee4f1ad4b6108");
        final List<SelesPicture> internalTextures = SdkValid.shared.readInternalTextures(list);
        final Iterator<SelesPicture> iterator = internalTextures.iterator();
        while (iterator.hasNext()) {
            iterator.next().runOnDrawSync();
        }
        this.a = internalTextures.get(0).framebufferForOutput();
        this.b = this.mFilterProgram.uniformIndex("uWhiten");
        this.c = this.mFilterProgram.uniformIndex("inputImageTexture2");
    }
    
    @Override
    protected void initializeAttributes() {
        this.mFilterProgram.addAttribute("position");
        this.mFilterProgram.addAttribute("inputTextureCoordinate");
    }
    
    @Override
    protected void inputFramebufferBindTexture() {
        super.inputFramebufferBindTexture();
        if (this.a == null) {
            TLog.e("whiten filter error: table color is null,please update resource package", new Object[0]);
            return;
        }
        GLES20.glActiveTexture(33987);
        GLES20.glBindTexture(3553, this.a.getTexture());
        GLES20.glUniform1i(this.c, 3);
        GLES20.glUniform1f(this.b, this.d);
    }
    
    public void setWhiten(final float d) {
        this.d = d;
    }
}
