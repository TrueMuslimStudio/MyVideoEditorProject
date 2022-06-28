// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.decoder;

import android.media.MediaCodec;
import android.os.AsyncTask;

import com.example.myvideoeditorapp.kore.TuSdk;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioInfo;
import com.example.myvideoeditorapp.kore.media.codec.decoder.TuSdkDecoderListener;
import com.example.myvideoeditorapp.kore.utils.StringHelper;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.tv.api.audio.preproc.mixer.TuSDKAudioRenderEntry;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TuSDKAudioDecoderTaskManager
{
    private Set<_AsyncDecoderTask> a;
    private List<TuSDKAudioRenderEntry> b;
    private TuSdkAudioInfo c;
    private TuSDKAudioDecoderTaskStateListener d;
    private State e;
    
    public TuSDKAudioDecoderTaskManager() {
        this.a = new HashSet<_AsyncDecoderTask>(3);
        this.e = State.Idle;
    }
    
    public void setAudioEntry(final List<TuSDKAudioRenderEntry> b) {
        this.b = b;
    }
    
    public void setDelegate(final TuSDKAudioDecoderTaskStateListener d) {
        this.d = d;
    }
    
    public void setTrunkAudioInfo(final TuSdkAudioInfo c) {
        this.c = c;
    }
    
    private void a(final State e) {
        if (this.d == null || this.e == e) {
            return;
        }
        this.e = e;
        ThreadHelper.post((Runnable)new Runnable() {
            @Override
            public void run() {
                TuSDKAudioDecoderTaskManager.this.d.onStateChanged(e);
            }
        });
    }
    
    public State getState() {
        return this.e;
    }

    public void start() {
        this.a.clear();
        if (this.b == null || this.b.size() == 0) {
            this.a(State.Complete);
        }

        Iterator var1 = this.b.iterator();

        while(var1.hasNext()) {
            TuSDKAudioRenderEntry var2 = (TuSDKAudioRenderEntry)var1.next();
            _AsyncDecoderTask var3 = new _AsyncDecoderTask(var2);
            var3.execute(new Void[0]);
            this.a.add(var3);
        }

    }
    
    public void cancel() {
        if (this.a == null || this.a.size() == 0) {
            return;
        }
        final Iterator<_AsyncDecoderTask> iterator = this.a.iterator();
        while (iterator.hasNext()) {
            iterator.next().cancel();
        }
        this.a.clear();
    }
    
    private TuSdkAudioInfo a() {
        if (this.c != null) {
            return this.c;
        }
        if (this.b == null || this.b.size() == 0) {
            return null;
        }
        for (final TuSDKAudioRenderEntry tuSDKAudioRenderEntry : this.b) {
            if (tuSDKAudioRenderEntry != null && tuSDKAudioRenderEntry.isTrunk()) {
                return tuSDKAudioRenderEntry.getRawInfo().getRealAudioInfo();
            }
        }
        return this.b.get(0).getRawInfo().getRealAudioInfo();
    }
    
    private String a(final TuSDKAudioRenderEntry tuSDKAudioRenderEntry, final int i, final int j) {
        return TuSdk.getAppTempPath() + "/" + StringHelper.md5(tuSDKAudioRenderEntry.getFingerprint("sample=" + i + "channel=" + j));
    }
    
    private boolean b(final TuSDKAudioRenderEntry tuSDKAudioRenderEntry, final int sampleRate, final int channelCount) {
        final String a = this.a(tuSDKAudioRenderEntry, sampleRate, channelCount);
        if (a == null) {
            return false;
        }
        final File file = new File(a);
        if (!file.exists() || !file.canRead() || !file.isFile()) {
            return false;
        }
        if (file.length() == 0L) {
            file.delete();
            return false;
        }
        tuSDKAudioRenderEntry.getRawInfo().setPath(a);
        tuSDKAudioRenderEntry.getRawInfo().getRealAudioInfo().sampleRate = sampleRate;
        tuSDKAudioRenderEntry.getRawInfo().getRealAudioInfo().channelCount = channelCount;
        return true;
    }
    
    private class _AsyncDecoderTask extends AsyncTask<Void, Double, TuSDKAudioRenderEntry> implements TuSdkDecoderListener
    {
        private TuSDKAudioRenderEntry b;
        private TuSDKAudioRenderDecoder c;
        private String d;
        private volatile boolean e;
        private boolean f;
        
        public _AsyncDecoderTask(final TuSDKAudioRenderEntry b) {
            this.e = false;
            this.f = false;
            this.b = b;
        }
        
        protected TuSDKAudioRenderEntry doInBackground(final Void... array) {
            if (this.b.getRawInfo() != null && this.b.getRawInfo().getMediaDataType() != null && this.b.getRawInfo().isValid()) {
                return this.b;
            }
            final TuSdkAudioInfo b = TuSDKAudioDecoderTaskManager.this.a();
            final TuSdkAudioInfo tuSdkAudioInfo = (b == null) ? this.b.getRawInfo().getRealAudioInfo() : b;
            if (tuSdkAudioInfo == null) {
                this.e = false;
                return this.b;
            }
            if (TuSDKAudioDecoderTaskManager.this.b(this.b, tuSdkAudioInfo.sampleRate, tuSdkAudioInfo.channelCount)) {
                this.e = false;
                return this.b;
            }
            this.d = TuSDKAudioDecoderTaskManager.this.a(this.b, tuSdkAudioInfo.sampleRate, tuSdkAudioInfo.channelCount);
            this.b.getRawInfo().setPath(this.d);
            this.e = true;
            (this.c = new TuSDKAudioRenderDecoder(this.b, tuSdkAudioInfo, this.d)).setDecodeListener((TuSdkDecoderListener)this);
            this.c.start();
            return this.b;
        }
        
        protected void onPreExecute() {
            super.onPreExecute();
            TuSDKAudioDecoderTaskManager.this.a(State.Decoding);
        }
        
        public void cancel() {
            if (this.isCancelled()) {
                return;
            }
            if (this.c != null) {
                this.c.setPause();
            }
            this.cancel(true);
        }
        
        protected void onCancelled() {
            super.onCancelled();
            if (this.c != null) {
                this.c.setPause();
            }
            TuSDKAudioDecoderTaskManager.this.a.remove(this);
            if (TuSDKAudioDecoderTaskManager.this.a.size() == 0) {
                TuSDKAudioDecoderTaskManager.this.a(State.Cancelled);
            }
        }

        protected void onCancelled(TuSDKAudioRenderEntry var1) {
            super.onCancelled(var1);
            TuSDKAudioDecoderTaskManager.this.a.remove(this);
            if (this.c != null) {
                this.c.setPause();
            }

            this.f = false;
            if (TuSDKAudioDecoderTaskManager.this.a.size() == 0) {
                TuSDKAudioDecoderTaskManager.this.a(State.Cancelled);
            }

        }
        protected void onPostExecute(TuSDKAudioRenderEntry var1) {
            super.onPostExecute(var1);
            if (!this.e && !this.f) {
                TuSDKAudioDecoderTaskManager.this.a.remove(this);
                if (TuSDKAudioDecoderTaskManager.this.a.size() == 0) {
                    TuSDKAudioDecoderTaskManager.this.a(State.Complete);
                }

            }
        }
        public void onDecoderUpdated(final MediaCodec.BufferInfo bufferInfo) {
            this.f = true;
        }
        
        public void onDecoderCompleted(final Exception ex) {
            if (!this.e) {
                return;
            }
            this.c.release();
            TuSDKAudioDecoderTaskManager.this.a.remove(this);
            this.f = false;
            if (TuSDKAudioDecoderTaskManager.this.a.size() == 0) {
                TuSDKAudioDecoderTaskManager.this.a(State.Complete);
            }
        }
    }
    
    public enum State
    {
        Idle, 
        Decoding, 
        Complete, 
        Cancelled;
    }
    
    public interface TuSDKAudioDecoderTaskStateListener
    {
        void onStateChanged(final State p0);
    }
}
