package com.example.myvideoeditorapp.imagelaoder.core.download;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public interface ImageDownloader {
    InputStream getStream(String var1, Object var2) throws IOException;

    public static enum Scheme {
        HTTP("http"),
        HTTPS("https"),
        FILE("file"),
        CONTENT("content"),
        ASSETS("assets"),
        DRAWABLE("drawable"),
        UNKNOWN("");

        private final String scheme;
        private final String uriPrefix;

        private Scheme(String scheme) {
            this.scheme = scheme;
            this.uriPrefix = scheme + "://";
        }

        public static Scheme ofUri(String uri) {
            if (uri != null) {
                Scheme[] var4;
                int var3 = (var4 = values()).length;

                for(int var2 = 0; var2 < var3; ++var2) {
                    Scheme s = var4[var2];
                    if (s.belongsTo(uri)) {
                        return s;
                    }
                }
            }

            return UNKNOWN;
        }

        private boolean belongsTo(String uri) {
            return uri.toLowerCase(Locale.US).startsWith(this.uriPrefix);
        }

        public String wrap(String path) {
            return this.uriPrefix + path;
        }

        public String crop(String uri) {
            if (!this.belongsTo(uri)) {
                throw new IllegalArgumentException(String.format("URI [%1$s] doesn't have expected scheme [%2$s]", uri, this.scheme));
            } else {
                return uri.substring(this.uriPrefix.length());
            }
        }
    }
}

