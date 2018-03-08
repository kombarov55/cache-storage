package com.company.filesystemimpl;

import com.company.Cache;
import com.company.MemSizeHelper;

import java.io.IOException;

public class FilesystemCache implements Cache {

    private final long maxCacheSize;
    private long currentCacheSize = 0;

    private FileHandler fileHandler;


    public FilesystemCache(long maxCacheSize, String cacheDir) {
        this.maxCacheSize = maxCacheSize;
        fileHandler = new FileHandler(cacheDir);
        try {
            fileHandler.clearDir();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fileHandler.setupDir();
    }


    public void put(int id, Object obj) {
        try {
            String data = Marshaller.marshalize(obj);

            int objectSize = MemSizeHelper.getObjectSize(data);

            if (memoryCheck(objectSize)) {
                fileHandler.writeData(data, id);
                currentCacheSize += objectSize;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void put(Object obj) {
        try {
            put(obj.hashCode(), obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public <T> T get(int id, Class<T> type) {
        try {
            String data = fileHandler.readData(id);
            return Marshaller.demarshalize(data, type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public boolean remove(int id) {
        return fileHandler.removeFile(id);
    }


    public void clear() {
        try {
            fileHandler.clearDir();
            currentCacheSize = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean memoryCheck(int objectSize) {
        return currentCacheSize + objectSize < maxCacheSize;
    }


}
