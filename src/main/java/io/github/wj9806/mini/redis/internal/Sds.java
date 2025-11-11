package io.github.wj9806.mini.redis.internal;

import io.github.wj9806.mini.redis.structure.RedisBytes;

public class Sds {

    private byte[] bytes;
    private int len;
    private int alloc;

    private static final int SDS_MAX_PREALLOC = 1024 * 1024;

    public Sds(byte[] bytes) {
        this.len = bytes.length;
        this.alloc = calculateAlloc(bytes.length);
        this.bytes = new byte[this.alloc];
        System.arraycopy(bytes, 0, this.bytes, 0, bytes.length);
    }

    private int calculateAlloc(int length) {
        return length <= SDS_MAX_PREALLOC ? Math.max(length * 2, 8) : length + SDS_MAX_PREALLOC;
    }

    @Override
    public String toString() {
        return new String(this.bytes, RedisBytes.CHARSET);
    }

    public byte[] getBytes() {
        byte[] result = new byte[len];
        System.arraycopy(this.bytes, 0, result, 0, this.len);
        return result;
    }

    public int getLength() {
        return this.len;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public void clear() {
        this.len = 0;
    }

    public Sds append(byte[] b) {
        int newLen = this.len + b.length;
        if (newLen > this.alloc) {
            int newAlloc = calculateAlloc(newLen);
            byte[] newBytes = new byte[newAlloc];
            System.arraycopy(this.bytes, 0, newBytes, 0, this.len);
            this.bytes = newBytes;
            this.alloc = newAlloc;
            this.len = newLen;
        } else {
            System.arraycopy(b, 0, this.bytes, this.len, b.length);
        }
        return this;
    }

    public Sds append(String s) {
        return append(s.getBytes());
    }
}
