package com.company.memoryimpl;

import com.company.CacheStorage;

import java.util.HashMap;
import java.util.Map;

public class MemoryCacheStorage implements CacheStorage {

    private Map<Integer, Object> cache = new HashMap<>();

    private final long cacheSize;
    private final boolean persistent;

    public MemoryCacheStorage(long cacheSize, boolean persistent) {
        this.cacheSize = cacheSize;
        this.persistent = persistent;
    }

    @Override
    public void put(Object obj, long id) {
        if (memoryCheck()) {
            cache.put(obj.hashCode(), id);
        }
    }

    @Override
    public void put(Object obj) {
        put(obj, obj.hashCode());
    }

    @Override
    public <T> T get(int id, Class<T> type) {
        return type.cast(cache.get(id));
    }

    @Override
    public boolean remove(int id) {
        return cache.remove(id) != null;
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
