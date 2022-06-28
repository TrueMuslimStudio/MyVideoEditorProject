// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.http;

import com.example.myvideoeditorapp.kore.utils.TLog;

import java.io.IOException;
import java.util.Locale;
import java.util.LinkedList;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.io.Serializable;

public class RequestParams implements Serializable
{
    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
    public static final String APPLICATION_JSON = "application/json";
    protected ConcurrentHashMap<String, String> mUrlParams;
    protected final ConcurrentHashMap<String, StreamWrapper> mStreamParams;
    protected final ConcurrentHashMap<String, FileWrapper> mFileParams;
    protected final ConcurrentHashMap<String, List<FileWrapper>> mFileArrayParams;
    protected final ConcurrentHashMap<String, Object> mUrlParamsWithObjects;
    protected boolean mIsRepeatable;
    protected boolean mForceMultipartEntity;
    protected boolean mUseJsonStreamer;
    protected String mElapsedFieldInJsonStreamer;
    protected boolean mAutoCloseInputStreams;
    protected String mContentEncoding;
    
    public RequestParams(ConcurrentHashMap<String, String> mUrlParams) {
        this((Object) null);
        this.mUrlParams = mUrlParams;
    }
    
    public RequestParams(final Map<String, String> map) {
        this.mUrlParams = new ConcurrentHashMap<String, String>();
        this.mStreamParams = new ConcurrentHashMap<String, StreamWrapper>();
        this.mFileParams = new ConcurrentHashMap<String, FileWrapper>();
        this.mFileArrayParams = new ConcurrentHashMap<String, List<FileWrapper>>();
        this.mUrlParamsWithObjects = new ConcurrentHashMap<String, Object>();
        this.mForceMultipartEntity = false;
        this.mElapsedFieldInJsonStreamer = "_elapsed";
        this.mContentEncoding = "UTF-8";
        if (map != null) {
            for (final Map.Entry<String, String> entry : map.entrySet()) {
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }
    
    public RequestParams(final String s, final String s2) {
        this(new HashMap<String, String>() {
            {
                this.put(s, s2);
            }
        });
    }
    
    public RequestParams(final Object... array) {
        this.mUrlParams = new ConcurrentHashMap<String, String>();
        this.mStreamParams = new ConcurrentHashMap<String, StreamWrapper>();
        this.mFileParams = new ConcurrentHashMap<String, FileWrapper>();
        this.mFileArrayParams = new ConcurrentHashMap<String, List<FileWrapper>>();
        this.mUrlParamsWithObjects = new ConcurrentHashMap<String, Object>();
        this.mForceMultipartEntity = false;
        this.mElapsedFieldInJsonStreamer = "_elapsed";
        this.mContentEncoding = "UTF-8";
        final int length = array.length;
        if (length % 2 != 0) {
            throw new IllegalArgumentException("Supplied arguments must be even");
        }
        for (int i = 0; i < length; i += 2) {
            this.put(String.valueOf(array[i]), String.valueOf(array[i + 1]));
        }
    }
    
    public void setContentEncoding(final String mContentEncoding) {
        if (mContentEncoding != null) {
            this.mContentEncoding = mContentEncoding;
        }
        else {
            TLog.d("setContentEncoding called with null attribute", new Object[0]);
        }
    }
    
    public void setForceMultipartEntityContentType(final boolean mForceMultipartEntity) {
        this.mForceMultipartEntity = mForceMultipartEntity;
    }
    
    public void put(final String key, final String value) {
        if (key != null && value != null) {
            this.mUrlParams.put(key, value);
        }
    }
    
    public void put(final String s, final File[] array) throws FileNotFoundException {
        this.put(s, array, null, null);
    }
    
    public void put(final String key, final File[] array, final String s, final String s2) throws FileNotFoundException {
        if (key != null) {
            final ArrayList<FileWrapper> value = new ArrayList<FileWrapper>();
            for (final File file : array) {
                if (file == null || !file.exists()) {
                    throw new FileNotFoundException();
                }
                value.add(new FileWrapper(file, s, s2));
            }
            this.mFileArrayParams.put(key, value);
        }
    }
    
    public void put(final String s, final File file) throws FileNotFoundException {
        this.put(s, file, null, null);
    }
    
    public void put(final String s, final String s2, final File file) throws FileNotFoundException {
        this.put(s, file, null, s2);
    }
    
    public void put(final String s, final File file, final String s2) throws FileNotFoundException {
        this.put(s, file, s2, null);
    }
    
    public void put(final String key, final File file, final String s, final String s2) throws FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException();
        }
        if (key != null) {
            this.mFileParams.put(key, new FileWrapper(file, s, s2));
        }
    }
    
    public void put(final String s, final InputStream inputStream) {
        this.put(s, inputStream, null);
    }
    
    public void put(final String s, final InputStream inputStream, final String s2) {
        this.put(s, inputStream, s2, null);
    }
    
    public void put(final String s, final InputStream inputStream, final String s2, final String s3) {
        this.put(s, inputStream, s2, s3, this.mAutoCloseInputStreams);
    }
    
    public void put(final String key, final InputStream inputStream, final String s, final String s2, final boolean b) {
        if (key != null && inputStream != null) {
            this.mStreamParams.put(key, StreamWrapper.a(inputStream, s, s2, b));
        }
    }
    
    public void put(final String key, final Object value) {
        if (key != null && value != null) {
            this.mUrlParamsWithObjects.put(key, value);
        }
    }
    
    public void put(final String key, final int i) {
        if (key != null) {
            this.mUrlParams.put(key, String.valueOf(i));
        }
    }
    
    public void put(final String key, final long l) {
        if (key != null) {
            this.mUrlParams.put(key, String.valueOf(l));
        }
    }
    
    public void add(final String key, final String s) {
        if (key != null && s != null) {
            Set<String> value = (Set<String>) this.mUrlParamsWithObjects.get(key);
            if (value == null) {
                value = new HashSet<String>();
                this.put(key, value);
            }
            if (value instanceof List) {
                ((List<String>)value).add(s);
            }
            else if (value instanceof Set) {
                value.add(s);
            }
        }
    }
    
    public void remove(final String key) {
        this.mUrlParams.remove(key);
        this.mStreamParams.remove(key);
        this.mFileParams.remove(key);
        this.mUrlParamsWithObjects.remove(key);
        this.mFileArrayParams.remove(key);
    }
    
    public boolean has(final String key) {
        return this.mUrlParams.get(key) != null || this.mStreamParams.get(key) != null || this.mFileParams.get(key) != null || this.mUrlParamsWithObjects.get(key) != null || this.mFileArrayParams.get(key) != null;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final Map.Entry<String, String> entry : this.mUrlParams.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }
        for (final Map.Entry<String, StreamWrapper> entry2 : this.mStreamParams.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(entry2.getKey());
            sb.append("=");
            sb.append("STREAM");
        }
        for (final Map.Entry<String, FileWrapper> entry3 : this.mFileParams.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(entry3.getKey());
            sb.append("=");
            sb.append("FILE");
        }
        for (final Map.Entry<String, List<FileWrapper>> entry4 : this.mFileArrayParams.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(entry4.getKey());
            sb.append("=");
            sb.append("FILES(SIZE=").append(entry4.getValue().size()).append(")");
        }
        for (final URLEncodedUtils.BasicNameValuePair basicNameValuePair : this.a(null, this.mUrlParamsWithObjects)) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(basicNameValuePair.getName());
            sb.append("=");
            sb.append(basicNameValuePair.getValue());
        }
        return sb.toString();
    }

    public String toPairString() {
        ConcurrentHashMap var1 = new ConcurrentHashMap();
        Iterator var2 = this.mUrlParams.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry var3 = (Map.Entry)var2.next();
            var1.put(var3.getKey(), var3.getValue());
        }

        List var7 = this.a((String)null, this.mUrlParamsWithObjects);
        Iterator var8 = var7.iterator();

        while(var8.hasNext()) {
            URLEncodedUtils.BasicNameValuePair var4 = (URLEncodedUtils.BasicNameValuePair)var8.next();
            var1.put(var4.getName(), var4.getValue());
        }

        ArrayList var9 = new ArrayList(var1.entrySet());
        Collections.sort(var9, new Comparator<Map.Entry<String, Object>>() {
            public int compare(Map.Entry<String, Object> var1, Map.Entry<String, Object> var2) {
                return ((String)var1.getKey()).toString().compareTo((String)var2.getKey());
            }
        });
        StringBuilder var10 = new StringBuilder();

        for(int var5 = 0; var5 < var9.size(); ++var5) {
            Map.Entry var6 = (Map.Entry)var9.get(var5);
            var10.append((String)var6.getKey()).append(var6.getValue());
        }

        return var10.toString();
    }
    
    public void setHttpEntityIsRepeatable(final boolean mIsRepeatable) {
        this.mIsRepeatable = mIsRepeatable;
    }
    
    public void setUseJsonStreamer(final boolean mUseJsonStreamer) {
        this.mUseJsonStreamer = mUseJsonStreamer;
    }
    
    public void setElapsedFieldInJsonStreamer(final String mElapsedFieldInJsonStreamer) {
        this.mElapsedFieldInJsonStreamer = mElapsedFieldInJsonStreamer;
    }
    
    public void setAutoCloseInputStreams(final boolean mAutoCloseInputStreams) {
        this.mAutoCloseInputStreams = mAutoCloseInputStreams;
    }
    
    public HttpEntity getEntity(final ResponseHandlerInterface responseHandlerInterface) throws IOException {
        if (this.mUseJsonStreamer) {
            return this.a(responseHandlerInterface);
        }
        if (!this.mForceMultipartEntity && this.mStreamParams.isEmpty() && this.mFileParams.isEmpty() && this.mFileArrayParams.isEmpty()) {
            return this.a();
        }
        return this.b(responseHandlerInterface);
    }
    
    private HttpEntity a(final ResponseHandlerInterface responseHandlerInterface) {
        final JsonStreamerEntity jsonStreamerEntity = new JsonStreamerEntity(responseHandlerInterface, !this.mFileParams.isEmpty() || !this.mStreamParams.isEmpty(), this.mElapsedFieldInJsonStreamer);
        for (final Map.Entry<String, String> entry : this.mUrlParams.entrySet()) {
            jsonStreamerEntity.addPart(entry.getKey(), entry.getValue());
        }
        for (final Map.Entry<String, Object> entry2 : this.mUrlParamsWithObjects.entrySet()) {
            jsonStreamerEntity.addPart(entry2.getKey(), entry2.getValue());
        }
        for (final Map.Entry<String, FileWrapper> entry3 : this.mFileParams.entrySet()) {
            jsonStreamerEntity.addPart(entry3.getKey(), entry3.getValue());
        }
        for (final Map.Entry<String, StreamWrapper> entry4 : this.mStreamParams.entrySet()) {
            final StreamWrapper streamWrapper = entry4.getValue();
            if (streamWrapper.inputStream != null) {
                jsonStreamerEntity.addPart(entry4.getKey(), StreamWrapper.a(streamWrapper.inputStream, streamWrapper.name, streamWrapper.contentType, streamWrapper.autoClose));
            }
        }
        return jsonStreamerEntity;
    }
    
    private HttpEntity a() throws UnsupportedEncodingException {
        return new UrlEncodedFormEntity(this.getParamsList(), this.mContentEncoding);
    }
    
    private HttpEntity b(final ResponseHandlerInterface responseHandlerInterface) throws IOException {
        final SimpleMultipartEntity simpleMultipartEntity = new SimpleMultipartEntity(responseHandlerInterface);
        simpleMultipartEntity.setIsRepeatable(this.mIsRepeatable);
        for (final Map.Entry<String, String> entry : this.mUrlParams.entrySet()) {
            simpleMultipartEntity.addPartWithCharset(entry.getKey(), entry.getValue(), this.mContentEncoding);
        }
        for (final URLEncodedUtils.BasicNameValuePair basicNameValuePair : this.a(null, this.mUrlParamsWithObjects)) {
            simpleMultipartEntity.addPartWithCharset(basicNameValuePair.getName(), basicNameValuePair.getValue(), this.mContentEncoding);
        }
        for (final Map.Entry<String, StreamWrapper> entry2 : this.mStreamParams.entrySet()) {
            final StreamWrapper streamWrapper = entry2.getValue();
            if (streamWrapper.inputStream != null) {
                simpleMultipartEntity.addPart(entry2.getKey(), streamWrapper.name, streamWrapper.inputStream, streamWrapper.contentType);
            }
        }
        for (final Map.Entry<String, FileWrapper> entry3 : this.mFileParams.entrySet()) {
            final FileWrapper fileWrapper = entry3.getValue();
            simpleMultipartEntity.addPart(entry3.getKey(), fileWrapper.file, fileWrapper.contentType, fileWrapper.customFileName);
        }
        for (final Map.Entry<String, List<FileWrapper>> entry4 : this.mFileArrayParams.entrySet()) {
            for (final FileWrapper fileWrapper2 : entry4.getValue()) {
                simpleMultipartEntity.addPart(entry4.getKey(), fileWrapper2.file, fileWrapper2.contentType, fileWrapper2.customFileName);
            }
        }
        return simpleMultipartEntity;
    }

    protected List<URLEncodedUtils.BasicNameValuePair> getParamsList() {
        LinkedList var1 = new LinkedList();
        Iterator var2 = this.mUrlParams.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry var3 = (Map.Entry)var2.next();
            var1.add(new URLEncodedUtils.BasicNameValuePair((String)var3.getKey(), (String)var3.getValue()));
        }

        var1.addAll(this.a((String)null, this.mUrlParamsWithObjects));
        return var1;
    }
    
    private List<URLEncodedUtils.BasicNameValuePair> a(final String s, final Object o) {
        final LinkedList<URLEncodedUtils.BasicNameValuePair> list = new LinkedList<URLEncodedUtils.BasicNameValuePair>();
        if (o instanceof Map) {
            final Map map = (Map)o;
            final ArrayList list2 = new ArrayList<String>(map.keySet());
            if (list2.size() > 0 && list2.get(0) instanceof Comparable) {
                Collections.sort((List<Comparable>)list2);
            }
            for (final Object next : list2) {
                if (next instanceof String) {
                    final Object value = map.get(next);
                    if (value == null) {
                        continue;
                    }
                    list.addAll((Collection<? extends URLEncodedUtils.BasicNameValuePair>) this.a((s == null) ? (String) next : String.format(Locale.US, "%s[%s]", s, next), value));
                }
            }
        }
        else if (o instanceof List) {
            final List list3 = (List)o;
            for (int size = list3.size(), i = 0; i < size; ++i) {
                list.addAll((Collection<? extends URLEncodedUtils.BasicNameValuePair>) this.a(String.format(Locale.US, "%s[%d]", s, i), list3.get(i)));
            }
        }
        else if (o instanceof Object[]) {
            final Object[] array = (Object[])o;
            for (int length = array.length, j = 0; j < length; ++j) {
                list.addAll((Collection<? extends URLEncodedUtils.BasicNameValuePair>) this.a(String.format(Locale.US, "%s[%d]", s, j), array[j]));
            }
        }
        else if (o instanceof Set) {
            final Iterator<Object> iterator2 = (Iterator<Object>)((Set)o).iterator();
            while (iterator2.hasNext()) {
                list.addAll((Collection<? extends URLEncodedUtils.BasicNameValuePair>) this.a(s, iterator2.next()));
            }
        }
        else {
            list.add(new URLEncodedUtils.BasicNameValuePair(s, o.toString()));
        }
        return list;
    }
    
    protected String getParamString() {
        return URLEncodedUtils.format(this.getParamsList(), this.mContentEncoding);
    }
    
    public static class StreamWrapper
    {
        public final InputStream inputStream;
        public final String name;
        public final String contentType;
        public final boolean autoClose;
        
        public StreamWrapper(final InputStream inputStream, final String name, final String contentType, final boolean autoClose) {
            this.inputStream = inputStream;
            this.name = name;
            this.contentType = contentType;
            this.autoClose = autoClose;
        }
        
        static StreamWrapper a(final InputStream inputStream, final String s, final String s2, final boolean b) {
            return new StreamWrapper(inputStream, s, (s2 == null) ? "application/octet-stream" : s2, b);
        }
    }
    
    public static class FileWrapper implements Serializable
    {
        public final File file;
        public final String contentType;
        public final String customFileName;
        
        public FileWrapper(final File file, final String contentType, final String customFileName) {
            this.file = file;
            this.contentType = contentType;
            this.customFileName = customFileName;
        }
    }
}
