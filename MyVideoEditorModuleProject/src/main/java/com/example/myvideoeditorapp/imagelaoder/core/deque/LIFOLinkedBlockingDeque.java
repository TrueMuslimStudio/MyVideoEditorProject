package com.example.myvideoeditorapp.imagelaoder.core.deque;

import java.util.concurrent.LinkedBlockingDeque;

public class LIFOLinkedBlockingDeque<T> extends LinkedBlockingDeque<T> {
    private static final long serialVersionUID = -4114786347960826192L;

    public LIFOLinkedBlockingDeque() {
    }

    public boolean offer(T e) {
        return super.offerFirst(e);
    }

    public T remove() {
        return super.removeFirst();
    }
}

