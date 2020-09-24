package org.springyoung.common.service;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springyoung.common.redis.cache.CacheKey;
import org.springyoung.core.tool.utils.CollectionUtil;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @ClassName RedisService
 * @Description TODO
 * @Author 小温
 * @Date 2020/9/24 10:08
 * @Version 1.0
 */
@AllArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ValueOperations<String, Object> valueOps;
    private final HashOperations<String, Object, Object> hashOps;
    private final ListOperations<String, Object> listOps;
    private final SetOperations<String, Object> setOps;
    private final ZSetOperations<String, Object> zSetOps;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        Assert.notNull(redisTemplate, "redisTemplate is null");
        this.valueOps = redisTemplate.opsForValue();
        this.hashOps = redisTemplate.opsForHash();
        this.listOps = redisTemplate.opsForList();
        this.setOps = redisTemplate.opsForSet();
        this.zSetOps = redisTemplate.opsForZSet();
    }

    public void set(CacheKey cacheKey, Object value) {
        String key = cacheKey.getKey();
        Duration expire = cacheKey.getExpire();
        if (expire == null) {
            this.set(key, value);
        } else {
            this.setEx(key, value, expire);
        }

    }

    public void set(String key, Object value) {
        this.valueOps.set(key, value);
    }

    public void setEx(String key, Object value, Duration timeout) {
        this.valueOps.set(key, value, timeout);
    }

    public void setEx(String key, Object value, Long seconds) {
        this.valueOps.set(key, value, seconds, TimeUnit.SECONDS);
    }

    @Nullable
    public <T> T get(String key) {
        return (T) this.valueOps.get(key);
    }

    @Nullable
    public <T> T get(String key, Supplier<T> loader) {
        T value = this.get(key);
        if (value != null) {
            return value;
        } else {
            value = loader.get();
            if (value == null) {
                return null;
            } else {
                this.set(key, value);
                return value;
            }
        }
    }

    @Nullable
    public <T> T get(CacheKey cacheKey) {
        return (T) this.valueOps.get(cacheKey.getKey());
    }

    @Nullable
    public <T> T get(CacheKey cacheKey, Supplier<T> loader) {
        String key = cacheKey.getKey();
        T value = this.get(key);
        if (value != null) {
            return value;
        } else {
            value = loader.get();
            if (value == null) {
                return null;
            } else {
                this.set(cacheKey, value);
                return value;
            }
        }
    }

    public Boolean del(String key) {
        return this.redisTemplate.delete(key);
    }

    public Boolean del(CacheKey key) {
        return this.redisTemplate.delete(key.getKey());
    }

    public Long del(String... keys) {
        return this.del((Collection) Arrays.asList(keys));
    }

    public Long del(Collection<String> keys) {
        return this.redisTemplate.delete(keys);
    }

    public Set<String> keys(String pattern) {
        return this.redisTemplate.keys(pattern);
    }

    public void mSet(Object... keysValues) {
        this.valueOps.multiSet(CollectionUtil.toMap(keysValues));
    }

    public List<Object> mGet(String... keys) {
        return this.mGet((Collection) Arrays.asList(keys));
    }

    public List<Object> mGet(Collection<String> keys) {
        return this.valueOps.multiGet(keys);
    }

    public Long decr(String key) {
        return this.valueOps.decrement(key);
    }

    public Long decrBy(String key, long longValue) {
        return this.valueOps.decrement(key, longValue);
    }

    public Long incr(String key) {
        return this.valueOps.increment(key);
    }

    public Long incrBy(String key, long longValue) {
        return this.valueOps.increment(key, longValue);
    }

    public Long getCounter(String key) {
        return Long.valueOf(String.valueOf(this.valueOps.get(key)));
    }

    public Boolean exists(String key) {
        return this.redisTemplate.hasKey(key);
    }

    public String randomKey() {
        return (String) this.redisTemplate.randomKey();
    }

    public void rename(String oldkey, String newkey) {
        this.redisTemplate.rename(oldkey, newkey);
    }

    public Boolean move(String key, int dbIndex) {
        return this.redisTemplate.move(key, dbIndex);
    }

    public Boolean expire(String key, long seconds) {
        return this.redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    public Boolean expire(String key, Duration timeout) {
        return this.expire(key, timeout.getSeconds());
    }

    public Boolean expireAt(String key, Date date) {
        return this.redisTemplate.expireAt(key, date);
    }

    public Boolean expireAt(String key, long unixTime) {
        return this.expireAt(key, new Date(unixTime));
    }

    public Boolean pexpire(String key, long milliseconds) {
        return this.redisTemplate.expire(key, milliseconds, TimeUnit.MILLISECONDS);
    }

    public <T> T getSet(String key, Object value) {
        return (T) this.valueOps.getAndSet(key, value);
    }

    public Boolean persist(String key) {
        return this.redisTemplate.persist(key);
    }

    public String type(String key) {
        return this.redisTemplate.type(key).code();
    }

    public Long ttl(String key) {
        return this.redisTemplate.getExpire(key);
    }

    public Long pttl(String key) {
        return this.redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
    }

    public void hSet(String key, Object field, Object value) {
        this.hashOps.put(key, field, value);
    }

    public void hMset(String key, Map<Object, Object> hash) {
        this.hashOps.putAll(key, hash);
    }

    public <T> T hGet(String key, Object field) {
        return (T) this.hashOps.get(key, field);
    }

    public List hmGet(String key, Object... fields) {
        return this.hmGet(key, (Collection) Arrays.asList(fields));
    }

    public List hmGet(String key, Collection<Object> hashKeys) {
        return this.hashOps.multiGet(key, hashKeys);
    }

    public Long hDel(String key, Object... fields) {
        return this.hashOps.delete(key, fields);
    }

    public Boolean hExists(String key, Object field) {
        return this.hashOps.hasKey(key, field);
    }

    public Map hGetAll(String key) {
        return this.hashOps.entries(key);
    }

    public List hVals(String key) {
        return this.hashOps.values(key);
    }

    public Set<Object> hKeys(String key) {
        return this.hashOps.keys(key);
    }

    public Long hLen(String key) {
        return this.hashOps.size(key);
    }

    public Long hIncrBy(String key, Object field, long value) {
        return this.hashOps.increment(key, field, value);
    }

    public Double hIncrByFloat(String key, Object field, double value) {
        return this.hashOps.increment(key, field, value);
    }

    public <T> T lIndex(String key, long index) {
        return (T) this.listOps.index(key, index);
    }

    public Long lLen(String key) {
        return this.listOps.size(key);
    }

    public <T> T lPop(String key) {
        return (T) this.listOps.leftPop(key);
    }

    public Long lPush(String key, Object... values) {
        return this.listOps.leftPush(key, values);
    }

    public void lSet(String key, long index, Object value) {
        this.listOps.set(key, index, value);
    }

    public Long lRem(String key, long count, Object value) {
        return this.listOps.remove(key, count, value);
    }

    public List lRange(String key, long start, long end) {
        return this.listOps.range(key, start, end);
    }

    public void lTrim(String key, long start, long end) {
        this.listOps.trim(key, start, end);
    }

    public <T> T rPop(String key) {
        return (T) this.listOps.rightPop(key);
    }

    public Long rPush(String key, Object... values) {
        return this.listOps.rightPush(key, values);
    }

    public <T> T rPopLPush(String srcKey, String dstKey) {
        return (T) this.listOps.rightPopAndLeftPush(srcKey, dstKey);
    }

    public Long sAdd(String key, Object... members) {
        return this.setOps.add(key, members);
    }

    public <T> T sPop(String key) {
        return (T) this.setOps.pop(key);
    }

    public Set sMembers(String key) {
        return this.setOps.members(key);
    }

    public boolean sIsMember(String key, Object member) {
        return this.setOps.isMember(key, member);
    }

    public Set sInter(String key, String otherKey) {
        return this.setOps.intersect(key, otherKey);
    }

    public Set sInter(String key, Collection<String> otherKeys) {
        return this.setOps.intersect(key, otherKeys);
    }

    public <T> T sRandMember(String key) {
        return (T) this.setOps.randomMember(key);
    }

    public List sRandMember(String key, int count) {
        return this.setOps.randomMembers(key, (long) count);
    }

    public Long sRem(String key, Object... members) {
        return this.setOps.remove(key, members);
    }

    public Set sUnion(String key, String otherKey) {
        return this.setOps.union(key, otherKey);
    }

    public Set sUnion(String key, Collection<String> otherKeys) {
        return this.setOps.union(key, otherKeys);
    }

    public Set sDiff(String key, String otherKey) {
        return this.setOps.difference(key, otherKey);
    }

    public Set sDiff(String key, Collection<String> otherKeys) {
        return this.setOps.difference(key, otherKeys);
    }

    public Boolean zAdd(String key, Object member, double score) {
        return this.zSetOps.add(key, member, score);
    }

    public Long zAdd(String key, Map<Object, Double> scoreMembers) {
        Set<TypedTuple<Object>> tuples = new HashSet();
        scoreMembers.forEach((k, v) -> {
            tuples.add(new DefaultTypedTuple(k, v));
        });
        return this.zSetOps.add(key, tuples);
    }

    public Long zCard(String key) {
        return this.zSetOps.zCard(key);
    }

    public Long zCount(String key, double min, double max) {
        return this.zSetOps.count(key, min, max);
    }

    public Double zIncrBy(String key, Object member, double score) {
        return this.zSetOps.incrementScore(key, member, score);
    }

    public Set zRange(String key, long start, long end) {
        return this.zSetOps.range(key, start, end);
    }

    public Set zRevrange(String key, long start, long end) {
        return this.zSetOps.reverseRange(key, start, end);
    }

    public Set zRangeByScore(String key, double min, double max) {
        return this.zSetOps.rangeByScore(key, min, max);
    }

    public Long zRank(String key, Object member) {
        return this.zSetOps.rank(key, member);
    }

    public Long zRevrank(String key, Object member) {
        return this.zSetOps.reverseRank(key, member);
    }

    public Long zRem(String key, Object... members) {
        return this.zSetOps.remove(key, members);
    }

    public Double zScore(String key, Object member) {
        return this.zSetOps.score(key, member);
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return this.redisTemplate;
    }

    public ValueOperations<String, Object> getValueOps() {
        return this.valueOps;
    }

    public HashOperations<String, Object, Object> getHashOps() {
        return this.hashOps;
    }

    public ListOperations<String, Object> getListOps() {
        return this.listOps;
    }

    public SetOperations<String, Object> getSetOps() {
        return this.setOps;
    }

    public ZSetOperations<String, Object> getZSetOps() {
        return this.zSetOps;
    }

}
