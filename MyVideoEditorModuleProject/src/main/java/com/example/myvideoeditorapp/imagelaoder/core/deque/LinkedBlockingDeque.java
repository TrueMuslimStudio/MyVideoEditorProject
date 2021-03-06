package com.example.myvideoeditorapp.imagelaoder.core.deque;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingDeque<E> extends AbstractQueue<E> implements BlockingDeque<E>, Serializable {
    private static final long serialVersionUID = -387911632671998426L;
    transient Node<E> first;
    transient Node<E> last;
    private transient int count;
    private final int capacity;
    final ReentrantLock lock;
    private final Condition notEmpty;
    private final Condition notFull;

    public LinkedBlockingDeque() {
        this(2147483647);
    }

    public LinkedBlockingDeque(int capacity) {
        this.lock = new ReentrantLock();
        this.notEmpty = this.lock.newCondition();
        this.notFull = this.lock.newCondition();
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.capacity = capacity;
        }
    }

    public LinkedBlockingDeque(Collection<? extends E> c) {
        this(2147483647);
        ReentrantLock lock = this.lock;
        lock.lock();

        try {
            Iterator var4 = c.iterator();

            while (var4.hasNext()) {
                E e = (E) var4.next();
                if (e == null) {
                    throw new NullPointerException();
                }

                if (!this.linkLast(new Node(e))) {
                    throw new IllegalStateException("Deque full");
                }
            }
        } finally {
            lock.unlock();
        }

    }

    private boolean linkFirst(Node<E> node) {
        if (this.count >= this.capacity) {
            return false;
        } else {
            Node<E> f = this.first;
            node.next = f;
            this.first = node;
            if (this.last == null) {
                this.last = node;
            } else {
                f.prev = node;
            }

            ++this.count;
            this.notEmpty.signal();
            return true;
        }
    }

    private boolean linkLast(Node<E> node) {
        if (this.count >= this.capacity) {
            return false;
        } else {
            Node<E> l = this.last;
            node.prev = l;
            this.last = node;
            if (this.first == null) {
                this.first = node;
            } else {
                l.next = node;
            }

            ++this.count;
            this.notEmpty.signal();
            return true;
        }
    }

    private E unlinkFirst() {
        Node<E> f = this.first;
        if (f == null) {
            return null;
        } else {
            Node<E> n = f.next;
            E item = f.item;
            f.item = null;
            f.next = f;
            this.first = n;
            if (n == null) {
                this.last = null;
            } else {
                n.prev = null;
            }

            --this.count;
            this.notFull.signal();
            return item;
        }
    }

    private E unlinkLast() {
        Node<E> l = this.last;
        if (l == null) {
            return null;
        } else {
            Node<E> p = l.prev;
            E item = l.item;
            l.item = null;
            l.prev = l;
            this.last = p;
            if (p == null) {
                this.first = null;
            } else {
                p.next = null;
            }

            --this.count;
            this.notFull.signal();
            return item;
        }
    }

    void unlink(Node<E> x) {
        Node<E> p = x.prev;
        Node<E> n = x.next;
        if (p == null) {
            this.unlinkFirst();
        } else if (n == null) {
            this.unlinkLast();
        } else {
            p.next = n;
            n.prev = p;
            x.item = null;
            --this.count;
            this.notFull.signal();
        }

    }

    public void addFirst(E e) {
        if (!this.offerFirst(e)) {
            throw new IllegalStateException("Deque full");
        }
    }

    public void addLast(E e) {
        if (!this.offerLast(e)) {
            throw new IllegalStateException("Deque full");
        }
    }

    public boolean offerFirst(E e) {
        if (e == null) {
            throw new NullPointerException();
        } else {
            Node<E> node = new Node(e);
            ReentrantLock lock = this.lock;
            lock.lock();

            boolean var5;
            try {
                var5 = this.linkFirst(node);
            } finally {
                lock.unlock();
            }

            return var5;
        }
    }

    public boolean offerLast(E e) {
        if (e == null) {
            throw new NullPointerException();
        } else {
            Node<E> node = new Node(e);
            ReentrantLock lock = this.lock;
            lock.lock();

            boolean var5;
            try {
                var5 = this.linkLast(node);
            } finally {
                lock.unlock();
            }

            return var5;
        }
    }

    public void putFirst(E e) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        } else {
            Node<E> node = new Node(e);
            ReentrantLock lock = this.lock;
            lock.lock();

            try {
                while (!this.linkFirst(node)) {
                    this.notFull.await();
                }
            } finally {
                lock.unlock();
            }

        }
    }

    public void putLast(E e) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        } else {
            Node<E> node = new Node(e);
            ReentrantLock lock = this.lock;
            lock.lock();

            try {
                while (!this.linkLast(node)) {
                    this.notFull.await();
                }
            } finally {
                lock.unlock();
            }

        }
    }

    public boolean offerFirst(E e, long timeout, TimeUnit unit) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        } else {
            Node<E> node = new Node(e);
            long nanos = unit.toNanos(timeout);
            ReentrantLock lock = this.lock;
            lock.lockInterruptibly();

            while (true) {
                try {
                    if (this.linkFirst(node)) {
                        return true;
                    }

                    if (nanos > 0L) {
                        nanos = this.notFull.awaitNanos(nanos);
                        continue;
                    }
                } finally {
                    lock.unlock();
                }

                return false;
            }
        }
    }

    public boolean offerLast(E e, long timeout, TimeUnit unit) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        } else {
            Node<E> node = new Node(e);
            long nanos = unit.toNanos(timeout);
            ReentrantLock lock = this.lock;
            lock.lockInterruptibly();

            try {
                while (!this.linkLast(node)) {
                    if (nanos <= 0L) {
                        return false;
                    }

                    nanos = this.notFull.awaitNanos(nanos);
                }
            } finally {
                lock.unlock();
            }

            return true;
        }
    }

    public E removeFirst() {
        E x = this.pollFirst();
        if (x == null) {
            throw new NoSuchElementException();
        } else {
            return x;
        }
    }

    public E removeLast() {
        E x = this.pollLast();
        if (x == null) {
            throw new NoSuchElementException();
        } else {
            return x;
        }
    }

    public E pollFirst() {
        ReentrantLock lock = this.lock;
        lock.lock();

        Object var3;
        try {
            var3 = this.unlinkFirst();
        } finally {
            lock.unlock();
        }

        return (E) var3;
    }

    public E pollLast() {
        ReentrantLock lock = this.lock;
        lock.lock();

        Object var3;
        try {
            var3 = this.unlinkLast();
        } finally {
            lock.unlock();
        }

        return (E) var3;
    }

    public E takeFirst() throws InterruptedException {
        ReentrantLock lock = this.lock;
        lock.lock();

        Object var4;
        try {
            Object x;
            while ((x = this.unlinkFirst()) == null) {
                this.notEmpty.await();
            }

            var4 = x;
        } finally {
            lock.unlock();
        }

        return (E) var4;
    }

    public E takeLast() throws InterruptedException {
        ReentrantLock lock = this.lock;
        lock.lock();

        Object var4;
        try {
            Object x;
            while ((x = this.unlinkLast()) == null) {
                this.notEmpty.await();
            }

            var4 = x;
        } finally {
            lock.unlock();
        }

        return (E) var4;
    }

    public E pollFirst(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        ReentrantLock lock = this.lock;
        lock.lockInterruptibly();

        while (true) {
            try {
                Object x;
                if ((x = this.unlinkFirst()) != null) {
                    Object var9 = x;
                    return (E) var9;
                }

                if (nanos > 0L) {
                    nanos = this.notEmpty.awaitNanos(nanos);
                    continue;
                }
            } finally {
                lock.unlock();
            }

            return null;
        }
    }

    public E pollLast(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        ReentrantLock lock = this.lock;
        lock.lockInterruptibly();

        Object var9;
        try {
            Object x;
            while ((x = this.unlinkLast()) == null) {
                if (nanos <= 0L) {
                    return null;
                }

                nanos = this.notEmpty.awaitNanos(nanos);
            }

            var9 = x;
        } finally {
            lock.unlock();
        }

        return (E) var9;
    }

    public E getFirst() {
        E x = this.peekFirst();
        if (x == null) {
            throw new NoSuchElementException();
        } else {
            return x;
        }
    }

    public E getLast() {
        E x = this.peekLast();
        if (x == null) {
            throw new NoSuchElementException();
        } else {
            return x;
        }
    }

    public E peekFirst() {
        ReentrantLock lock = this.lock;
        lock.lock();

        Object var3;
        try {
            var3 = this.first == null ? null : this.first.item;
        } finally {
            lock.unlock();
        }

        return (E) var3;
    }

    public E peekLast() {
        ReentrantLock lock = this.lock;
        lock.lock();

        Object var3;
        try {
            var3 = this.last == null ? null : this.last.item;
        } finally {
            lock.unlock();
        }

        return (E) var3;
    }

    public boolean removeFirstOccurrence(Object o) {
        if (o == null) {
            return false;
        } else {
            ReentrantLock lock = this.lock;
            lock.lock();

            try {
                for (Node p = this.first; p != null; p = p.next) {
                    if (o.equals(p.item)) {
                        this.unlink(p);
                        return true;
                    }
                }

                return false;
            } finally {
                lock.unlock();
            }
        }
    }

    public boolean removeLastOccurrence(Object o) {
        if (o == null) {
            return false;
        } else {
            ReentrantLock lock = this.lock;
            lock.lock();

            try {
                for (Node p = this.last; p != null; p = p.prev) {
                    if (o.equals(p.item)) {
                        this.unlink(p);
                        return true;
                    }
                }
            } finally {
                lock.unlock();
            }

            return false;
        }
    }

    public boolean add(E e) {
        this.addLast(e);
        return true;
    }

    public boolean offer(E e) {
        return this.offerLast(e);
    }

    public void put(E e) throws InterruptedException {
        this.putLast(e);
    }

    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        return this.offerLast(e, timeout, unit);
    }

    public E remove() {
        return this.removeFirst();
    }

    public E poll() {
        return this.pollFirst();
    }

    public E take() throws InterruptedException {
        return this.takeFirst();
    }

    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        return this.pollFirst(timeout, unit);
    }

    public E element() {
        return this.getFirst();
    }

    public E peek() {
        return this.peekFirst();
    }

    public int remainingCapacity() {
        ReentrantLock lock = this.lock;
        lock.lock();

        int var3;
        try {
            var3 = this.capacity - this.count;
        } finally {
            lock.unlock();
        }

        return var3;
    }

    public int drainTo(Collection<? super E> c) {
        return this.drainTo(c, 2147483647);
    }

    public int drainTo(Collection<? super E> c, int maxElements) {
        if (c == null) {
            throw new NullPointerException();
        } else if (c == this) {
            throw new IllegalArgumentException();
        } else {
            ReentrantLock lock = this.lock;
            lock.lock();

            try {
                int n = Math.min(maxElements, this.count);

                for (int i = 0; i < n; ++i) {
                    c.add(this.first.item);
                    this.unlinkFirst();
                }

                int var7 = n;
                return var7;
            } finally {
                lock.unlock();
            }
        }
    }

    public void push(E e) {
        this.addFirst(e);
    }

    public E pop() {
        return this.removeFirst();
    }

    public boolean remove(Object o) {
        return this.removeFirstOccurrence(o);
    }

    public int size() {
        ReentrantLock lock = this.lock;
        lock.lock();

        int var3;
        try {
            var3 = this.count;
        } finally {
            lock.unlock();
        }

        return var3;
    }

    public boolean contains(Object o) {
        if (o == null) {
            return false;
        } else {
            ReentrantLock lock = this.lock;
            lock.lock();

            try {
                for (Node p = this.first; p != null; p = p.next) {
                    if (o.equals(p.item)) {
                        return true;
                    }
                }
            } finally {
                lock.unlock();
            }

            return false;
        }
    }

    public Object[] toArray() {
        ReentrantLock lock = this.lock;
        lock.lock();

        try {
            Object[] a = new Object[this.count];
            int k = 0;

            for (Node p = this.first; p != null; p = p.next) {
                a[k++] = p.item;
            }

            Object[] var6 = a;
            return var6;
        } finally {
            lock.unlock();
        }
    }

    public <T> T[] toArray(T[] a) {
        ReentrantLock lock = this.lock;
        lock.lock();

        Object[] var6;
        try {
            if (a.length < this.count) {
                a = (T[]) Array.newInstance(a.getClass().getComponentType(), this.count);
            }

            int k = 0;

            for (Node p = this.first; p != null; p = p.next) {
                a[k++] = (T) p.item;
            }

            if (a.length > k) {
                a[k] = null;
            }

            var6 = a;
        } finally {
            lock.unlock();
        }

        return (T[]) var6;
    }

    public String toString() {
        ReentrantLock lock = this.lock;
        lock.lock();

        try {
            Node<E> p = this.first;
            if (p == null) {
                return "[]";
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append('[');

                while (true) {
                    E e = p.item;
                    sb.append(e == this ? "(this Collection)" : e);
                    p = p.next;
                    if (p == null) {
                        String var6 = sb.append(']').toString();
                        return var6;
                    }

                    sb.append(',').append(' ');
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void clear() {
        ReentrantLock lock = this.lock;
        lock.lock();

        try {
            Node n;
            for (Node f = this.first; f != null; f = n) {
                f.item = null;
                n = f.next;
                f.prev = null;
                f.next = null;
            }

            this.first = this.last = null;
            this.count = 0;
            this.notFull.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public Iterator<E> iterator() {
        return new Itr((Itr) null);
    }

    public Iterator<E> descendingIterator() {
        return new DescendingItr((DescendingItr) null);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        ReentrantLock lock = this.lock;
        lock.lock();

        try {
            s.defaultWriteObject();

            for (Node p = this.first; p != null; p = p.next) {
                s.writeObject(p.item);
            }

            s.writeObject((Object) null);
        } finally {
            lock.unlock();
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.count = 0;
        this.first = null;
        this.last = null;
        while (true) {
            E item = (E) s.readObject();
            if (item == null) {
                return;
            }

            this.add(item);
        }
    }

    private abstract class AbstractItr implements Iterator<E> {
        Node<E> next;
        E nextItem;
        private Node<E> lastRet;

        abstract Node<E> firstNode();

        abstract Node<E> nextNode(Node<E> var1);

        AbstractItr() {
            ReentrantLock lock = LinkedBlockingDeque.this.lock;
            lock.lock();

            try {
                this.next = this.firstNode();
                this.nextItem = this.next == null ? null : this.next.item;
            } finally {
                lock.unlock();
            }

        }

        private Node<E> succ(Node<E> n) {
            while (true) {
                Node<E> s = this.nextNode(n);
                if (s == null) {
                    return null;
                }

                if (s.item != null) {
                    return s;
                }

                if (s == n) {
                    return this.firstNode();
                }

                n = s;
            }
        }

        void advance() {
            ReentrantLock lock = LinkedBlockingDeque.this.lock;
            lock.lock();

            try {
                this.next = this.succ(this.next);
                this.nextItem = this.next == null ? null : this.next.item;
            } finally {
                lock.unlock();
            }

        }

        public boolean hasNext() {
            return this.next != null;
        }

        public E next() {
            if (this.next == null) {
                throw new NoSuchElementException();
            } else {
                this.lastRet = this.next;
                E x = this.nextItem;
                this.advance();
                return x;
            }
        }

        public void remove() {
            Node<E> n = this.lastRet;
            if (n == null) {
                throw new IllegalStateException();
            } else {
                this.lastRet = null;
                ReentrantLock lock = LinkedBlockingDeque.this.lock;
                lock.lock();

                try {
                    if (n.item != null) {
                        LinkedBlockingDeque.this.unlink(n);
                    }
                } finally {
                    lock.unlock();
                }

            }
        }
    }

    private class DescendingItr extends AbstractItr {
        private DescendingItr(DescendingItr descendingItr) {
            super();
        }

        Node<E> firstNode() {
            return LinkedBlockingDeque.this.last;
        }

        Node<E> nextNode(Node<E> n) {
            return n.prev;
        }
    }

    private class Itr extends AbstractItr {
        private Itr(Itr itr) {
            super();
        }

        Node<E> firstNode() {
            return LinkedBlockingDeque.this.first;
        }

        Node<E> nextNode(Node<E> n) {
            return n.next;
        }
    }

    static final class Node<E> {
        E item;
        Node<E> prev;
        Node<E> next;

        Node(E x) {
            this.item = x;
        }
    }
}
