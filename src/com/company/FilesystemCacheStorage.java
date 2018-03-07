package com.company;

public class FilesystemCacheStorage implements CacheStorage {

    private final String filename = "cache";
    private final boolean persistent;
    private final String cacheDir;

    FilesystemCacheStorage(boolean persistent, String cacheDir) {
        this.persistent = persistent;
        this.cacheDir = cacheDir;
    }

    @Override
    public void put(Object obj) {

    }

    @Override
    public Object get(int hashcode) {
        return null;
    }

}
