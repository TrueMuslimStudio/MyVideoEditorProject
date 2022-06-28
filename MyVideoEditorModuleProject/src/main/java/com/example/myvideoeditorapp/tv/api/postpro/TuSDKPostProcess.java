// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.api.postpro;

import android.net.Uri;
import android.text.TextUtils;

import com.example.myvideoeditorapp.kore.TuSdk;
import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.tv.api.TuSDKPostProcessJNI;
import com.example.myvideoeditorapp.tv.core.common.TuSDKMediaDataSource;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class TuSDKPostProcess
{
    protected final boolean process(TuSDKMediaDataSource var1, File var2, List<PostProcessArg> var3) {
        if (var3 != null && var3.size() != 0) {
            if (var1 != null && var1.isValid()) {
                if (var2 == null) {
                    TLog.e("%s :Please set a file output path.", new Object[]{this});
                    return false;
                } else if (var1.getFile().getAbsolutePath().equals(var2.getAbsolutePath())) {
                    TLog.e("%s :Please set a valid output.", new Object[]{this});
                    return false;
                } else {
                    ArrayList var4 = new ArrayList();
                    File var5 = null;
                    if (var1.getFilePath() != null) {
                        var4.add(new PostProcessArg("-i", var1.getFilePath()));
                    } else {
                        var5 = new File(TuSdk.getAppTempPath() + "/" + System.currentTimeMillis());
                        this.a(var1.getFileUri(), var5);
                        if (!var5.exists()) {
                            return false;
                        }

                        var4.add(new PostProcessArg("-i", var5.getAbsolutePath()));
                    }

                    var4.addAll(var3);
                    var4.add(new PostProcessArg((String)null, var2.getAbsolutePath()));
                    boolean var6 = this.process(var4);
                    if (var5 != null) {
                        var5.delete();
                    }

                    return var6;
                }
            } else {
                TLog.e("%s : Please set valid data source.", new Object[]{this});
                return false;
            }
        } else {
            TLog.e("%s : Please set input parameters.", new Object[]{this});
            return false;
        }
    }
    
    private void a(final Uri uri, final File file) {
        InputStream openInputStream = null;
        FilterOutputStream filterOutputStream = null;
        try {
            openInputStream = TuSdkContext.context().getContentResolver().openInputStream(uri);
            filterOutputStream = new BufferedOutputStream(new FileOutputStream(file, false));
            final byte[] b = new byte[1024];
            openInputStream.read(b);
            do {
                filterOutputStream.write(b);
            } while (openInputStream.read(b) != -1);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            try {
                if (openInputStream != null) {
                    openInputStream.close();
                }
                if (filterOutputStream != null) {
                    filterOutputStream.close();
                }
            }
            catch (IOException ex2) {
                ex2.printStackTrace();
            }
        }
        finally {
            try {
                if (openInputStream != null) {
                    openInputStream.close();
                }
                if (filterOutputStream != null) {
                    filterOutputStream.close();
                }
            }
            catch (IOException ex3) {
                ex3.printStackTrace();
            }
        }
    }
    
    protected boolean process(final List<PostProcessArg> list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        final ArrayList<String> list2 = new ArrayList<String>();
        list2.add("ffmpeg");
        for (int i = 0; i < list.size(); ++i) {
            final PostProcessArg postProcessArg = list.get(i);
            if (!TextUtils.isEmpty((CharSequence)postProcessArg.getKey())) {
                list2.add(list.get(i).getKey());
            }
            if (!TextUtils.isEmpty((CharSequence)postProcessArg.getValue())) {
                list2.add(list.get(i).getValue());
            }
        }
        return TuSDKPostProcessJNI.runVideoCommands(list2.toArray(new String[list2.size()]));
    }
    
    public static class PostProcessArg
    {
        private String a;
        private String b;
        
        public PostProcessArg(final String a, final String b) {
            this.a = a;
            this.b = b;
        }
        
        public String getKey() {
            return this.a;
        }
        
        public String getValue() {
            return this.b;
        }
    }
}
