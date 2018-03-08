package com.company.memoryimpl;

import com.company.Cache;
import com.company.MemSizeHelper;

import java.util.HashMap;
import java.util.Map;

public class MemoryCache implements Cache {

    private Map<Integer, Object> cache = new HashMap<>();

    private final long maxCacheSize;

    private long currentCacheSize = 0;

    public MemoryCache(long maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }

    @Override
    public void put(int id, Object obj) {
        try {
            int objSize = MemSizeHelper.getObjectSize(obj);
            if (memoryCheck(objSize)) {
                cache.put(id, obj);
                currentCacheSize += objSize;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(Object obj) {
        put(obj.hashCode(), obj);
    }

    @Override
    public <T> T get(int id, Class<T> type) {
        return type.cast(cache.get(id));
    }

    @Override
    public boolean remove(int id) {
        Object deletedObj = cache.remove(id);

        if (deletedObj == null) return false;

        try {
            currentCacheSize -= MemSizeHelper.getObjectSize(deletedObj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;


    }

    @Override
    public void clear() {
        cache.clear();
        currentCacheSize = 0;
    }

    //TODO: реалзизовать
    private boolean memoryCheck(int size) {
        return (currentCacheSize + size) < maxCacheSize;
    }
}
