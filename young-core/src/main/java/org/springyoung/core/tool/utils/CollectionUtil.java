//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springyoung.core.tool.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

public class CollectionUtil extends CollectionUtils {

    public CollectionUtil() {
    }

    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isNotEmpty(@Nullable Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static <T> boolean contains(@Nullable T[] array, final T element) {
        return array == null ? false : Arrays.stream(array).anyMatch((x) -> {
            return ObjectUtil.nullSafeEquals(x, element);
        });
    }

    public static String[] concat(String[] one, String[] other) {
        return (String[])concat(one, other, String.class);
    }

    public static <T> T[] concat(T[] one, T[] other, Class<T> clazz) {
        T[] target = (T[]) Array.newInstance(clazz, one.length + other.length);
        System.arraycopy(one, 0, target, 0, one.length);
        System.arraycopy(other, 0, target, one.length, other.length);
        return target;
    }

    public static boolean isArray(Object obj) {
        return null == obj ? false : obj.getClass().isArray();
    }

    @SafeVarargs
    public static <E> Set<E> ofImmutableSet(E... es) {
        Objects.requireNonNull(es, "args es is null.");
        return (Set)Arrays.stream(es).collect(Collectors.toSet());
    }

    @SafeVarargs
    public static <E> List<E> ofImmutableList(E... es) {
        Objects.requireNonNull(es, "args es is null.");
        return (List)Arrays.stream(es).collect(Collectors.toList());
    }

    public static <E> List<E> toList(Iterable<E> elements) {
        Objects.requireNonNull(elements, "elements es is null.");
        if (elements instanceof Collection) {
            return new ArrayList((Collection)elements);
        } else {
            Iterator<E> iterator = elements.iterator();
            ArrayList list = new ArrayList();

            while(iterator.hasNext()) {
                list.add(iterator.next());
            }

            return list;
        }
    }

    public static <K, V> Map<K, V> toMap(Object... keysValues) {
        int kvLength = keysValues.length;
        if (kvLength % 2 != 0) {
            throw new IllegalArgumentException("wrong number of arguments for met, keysValues length can not be odd");
        } else {
            Map<Object, Object> keyValueMap = new HashMap(kvLength);

            for(int i = kvLength - 2; i >= 0; i -= 2) {
                Object key = keysValues[i];
                Object value = keysValues[i + 1];
                keyValueMap.put(key, value);
            }

            return (Map<K, V>) keyValueMap;
        }
    }

}
