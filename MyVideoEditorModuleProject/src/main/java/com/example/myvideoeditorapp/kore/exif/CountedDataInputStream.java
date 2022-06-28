// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.exif;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.ByteOrder;
import java.io.EOFException;
import android.annotation.SuppressLint;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.io.FilterInputStream;

class CountedDataInputStream extends FilterInputStream
{
    private final byte[] b;
    private final ByteBuffer c;
    private int d;
    private int e;
    static final /* synthetic */ boolean a;
    
    protected CountedDataInputStream(final InputStream in) {
        super(in);
        this.b = new byte[8];
        this.c = ByteBuffer.wrap(this.b);
        this.d = 0;
        this.e = 0;
    }
    
    public void setEnd(final int e) {
        this.e = e;
    }
    
    public int getEnd() {
        return this.e;
    }
    
    public int getReadByteCount() {
        return this.d;
    }
    
    @Override
    public int read(byte[] var1) throws IOException {
        int var2 = this.in.read(var1);
        this.d += var2 >= 0 ? var2 : 0;
        return var2;
    }
    
    @Override
    public int read() {
        int read = 0;
        try {
            read = this.in.read();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.d += ((read >= 0) ? 1 : 0);
        return read;
    }

    public int read(byte[] var1, int var2, int var3) throws IOException {
        int var4 = this.in.read(var1, var2, var3);
        this.d += var4 >= 0 ? var4 : 0;
        return var4;
    }
    
    @Override
    public long skip(final long n) {
        long skip = 0;
        try {
            skip = this.in.skip(n);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.d += (int)skip;
        return skip;
    }
    
    @SuppressLint({ "Assert" })
    public void skipTo(final long n) throws EOFException {
        final long n2 = n - this.d;
        if (!CountedDataInputStream.a && n2 < 0L) {
            throw new AssertionError();
        }
        this.skipOrThrow(n2);
    }
    
    public void skipOrThrow(final long n) throws EOFException {
        if (this.skip(n) != n) {
            throw new EOFException();
        }
    }
    
    public ByteOrder getByteOrder() {
        return this.c.order();
    }
    
    public void setByteOrder(final ByteOrder bo) {
        this.c.order(bo);
    }
    
    public int readUnsignedShort() throws IOException {
        return this.readShort() & 0xFFFF;
    }
    
    public short readShort() throws IOException {
        this.readOrThrow(this.b, 0, 2);
        this.c.rewind();
        return this.c.getShort();
    }
    
    public byte readByte() throws IOException {
        this.readOrThrow(this.b, 0, 1);
        this.c.rewind();
        return this.c.get();
    }
    
    public int readUnsignedByte() throws IOException {
        this.readOrThrow(this.b, 0, 1);
        this.c.rewind();
        return this.c.get() & 0xFF;
    }
    
    public void readOrThrow(final byte[] array, final int n, final int n2) throws IOException {
        if (this.read(array, n, n2) != n2) {
            throw new EOFException();
        }
    }
    
    public long readUnsignedInt() throws IOException {
        return (long)this.readInt() & 0xFFFFFFFFL;
    }
    
    public int readInt() throws IOException {
        this.readOrThrow(this.b, 0, 4);
        this.c.rewind();
        return this.c.getInt();
    }
    
    public long readLong() throws IOException {
        this.readOrThrow(this.b, 0, 8);
        this.c.rewind();
        return this.c.getLong();
    }
    
    public String readString(final int n) throws IOException {
        final byte[] bytes = new byte[n];
        this.readOrThrow(bytes);
        try {
            return new String(bytes, "UTF8");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public void readOrThrow(final byte[] array) throws IOException {
        this.readOrThrow(array, 0, array.length);
    }
    
    public String readString(final int n, final Charset charset) throws IOException {
        final byte[] bytes = new byte[n];
        this.readOrThrow(bytes);
        return new String(bytes, charset);
    }
    
    static {
        a = !CountedDataInputStream.class.desiredAssertionStatus();
    }
}
