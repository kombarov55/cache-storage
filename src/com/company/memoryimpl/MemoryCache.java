package com.company.memoryimpl;

import com.company.Cache;
import com.company.MemSizeHelper;

import java.util.HashMap;
import java.util.Map;

public class MemoryCache implements Cache {

    private Map<String, Object> cache = new HashMap<>();

    private final long maxCacheSize;

    private long currentCacheSize = 0;

    public MemoryCache(long maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }

    @Override
    public boolean put(String id, Object obj) {
        int objSize = MemSizeHelper.getObjectSize(obj);
        if (memoryCheck(objSize)) {
            boolean result = cache.put(id, obj) != null;
            currentCacheSize += objSize;

            return result;
        } else {
            return false;
        }
    }

    @Override
    public boolean put(Object obj) {
        return put("" + obj.hashCode(), obj);
    }

    @Override
    public <T> T get(String id, Class<T> type) {
        return type.cast(cache.get(id));
    }

    @Override
    public boolean remove(String id) {
        Object deletedObj = cache.remove(id);
        if (deletedObj == null) return false;
        currentCacheSize -= MemSizeHelper.getObjectSize(deletedObj);
        return true;
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public void clear() {
        cache.clear();
        currentCacheSize = 0;
    }

    private boolean memoryCheck(int size) {
        return (currentCacheSize + size) < maxCacheSize;
    }
}
