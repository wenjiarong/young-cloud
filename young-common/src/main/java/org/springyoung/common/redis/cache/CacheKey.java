package org.springyoung.common.redis.cache;

import org.springframework.lang.Nullable;

import java.time.Duration;

public class CacheKey {

    private final String key;
    @Nullable
    private Duration expire;

    public CacheKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    @Nullable
    public Duration getExpire() {
        return this.expire;
    }

    public String toString() {
        return "CacheKey(key=" + this.getKey() + ", expire=" + this.getExpire() + ")";
    }

    public CacheKey(final String key, @Nullable final Duration expire) {
        this.key = key;
        this.expire = expire;
    }

}
