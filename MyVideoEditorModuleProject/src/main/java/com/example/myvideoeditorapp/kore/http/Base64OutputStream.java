// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.http;

import com.example.myvideoeditorapp.kore.utils.Base64Coder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.FilterOutputStream;

public class Base64OutputStream extends FilterOutputStream
{
    private static final byte[] a;
    private final Base64Coder.Coder b;
    private final int c;
    private byte[] d;
    private int e;
    
    public Base64OutputStream(final OutputStream outputStream, final int n) {
        this(outputStream, n, true);
    }
    
    public Base64OutputStream(final OutputStream out, final int c, final boolean b) {
        super(out);
        this.d = null;
        this.e = 0;
        this.c = c;
        if (b) {
            this.b = new Base64Coder.Encoder(c, null);
        }
        else {
            this.b = new Base64Coder.Decoder(c, null);
        }
    }
    
    @Override
    public void write(final int n) throws IOException {
        if (this.d == null) {
            this.d = new byte[1024];
        }
        if (this.e >= this.d.length) {
            this.a(this.d, 0, this.e, false);
            this.e = 0;
        }
        this.d[this.e++] = (byte)n;
    }
    
    private void a() throws IOException {
        if (this.e > 0) {
            this.a(this.d, 0, this.e, false);
            this.e = 0;
        }
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        if (n2 <= 0) {
            return;
        }
        this.a();
        this.a(array, n, n2, false);
    }
    
    @Override
    public void close() throws IOException {
        IOException ex = null;
        this.a();
        this.a(Base64OutputStream.a, 0, 0, true);
        try {
            if ((this.c & 0x10) == 0x0) {
                this.out.close();
            }
            else {
                this.out.flush();
            }
        }
        catch (IOException ex3) {
            if (ex != null) {
                ex = ex3;
            }
        }
        if (ex != null) {
            throw ex;
        }
    }
    
    private void a(final byte[] array, final int n, final int n2, final boolean b) throws IOException {
        this.b.output = this.a(this.b.output, this.b.maxOutputSize(n2));
        if (!this.b.process(array, n, n2, b)) {
            throw new Base64DataException("bad base-64");
        }
        this.out.write(this.b.output, 0, this.b.op);
    }
    
    private byte[] a(final byte[] array, final int n) {
        if (array == null || array.length < n) {
            return new byte[n];
        }
        return array;
    }
    
    static {
        a = new byte[0];
    }
    
    public static class Base64DataException extends IOException
    {
        public Base64DataException(final String message) {
            super(message);
        }
    }
}
