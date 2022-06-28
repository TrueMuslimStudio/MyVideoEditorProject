package com.example.myvideoeditorapp.imagelaoder.cache;

public class HashCodeFileNameGenerator implements FileNameGenerator {
    public HashCodeFileNameGenerator() {
    }

    public String generate(String imageUri) {
        return String.valueOf(imageUri.hashCode());
    }
}
