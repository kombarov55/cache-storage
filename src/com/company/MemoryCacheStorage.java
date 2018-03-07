package com.company;

import java.util.HashMap;
import java.util.Map;

public class MemoryCacheStorage implements CacheStorage {

    private Map<Integer, Object> cache = new HashMap<>();

    private long cacheSize;

    MemoryCacheStorage(long cacheSize) {
        this.cacheSize = cacheSize;
    }

    @Override
    public void put(Object obj) {
        if (memoryCheck()) {
            cache.put(obj.hashCode(), obj);
        }
    }

    @Override
    public Object get(int hashcode) {
        return cache.get(hashcode);
    }

    @Override
    public boolean remove(int hashcode) {
        return cache.remove(hashcode) != null;
    }

    @Override
    public void clear() {
        cache.clear();
    }

    //TODO: реалзизовать
    private boolean memoryCheck() {
        return true;
    }
}
