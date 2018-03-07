package com.company.filesystemimpl;

import com.company.CacheStorage;

import java.io.IOException;

public class FilesystemCacheStorage implements CacheStorage {

    private final long maxCacheSize;
    private final boolean persistent;

    private FileHandler fileHandler;

    public FilesystemCacheStorage(long maxCacheSize, String cacheDir, boolean persistent) {
        this.maxCacheSize = maxCacheSize;
        this.persistent = persistent;

        fileHandler = new FileHandler(cacheDir);
        fileHandler.setupDir();
    }

    @Override
    public void put(Object obj, long id) {
        try {
            String data = Marshaller.marshalize(obj);
            fileHandler.writeData(data, id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(Object obj) {
        put(obj, obj.hashCode());
    }

    @Override
    public <T> T get(int id, Class<T> type) {
        try {
            String data = fileHandler.readData(id);
            return Marshaller.demarshalize(data, type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean remove(int id) {
        return fileHandler.removeFile(id);
    }

    @Override
    public void clear() {
        fileHandler.clearDir();
    }

    private boolean memoryCheck() throws IOException {
        return fileHandler.getDirSize() > maxCacheSize;
    }


}
