package com.company.filesystemimpl;

import com.company.Cache;
import com.company.MemSizeHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class FilesystemCache implements Cache {

    private final long maxCacheSize;
    private long currentCacheSize = 0;

    private FileHandler fileHandler;


    public FilesystemCache(long maxCacheSize, String cacheDir) {
        this.maxCacheSize = maxCacheSize;
        fileHandler = new FileHandler(cacheDir);
        clear();
        fileHandler.setupDir();
    }


    public boolean put(int id, Object obj) {
        try {
            String data = Marshaller.marshalize(obj);

            int objectSize = MemSizeHelper.getObjectSize(data);

            if (memoryCheck(objectSize)) {
                fileHandler.writeData(data, id);
                currentCacheSize += objectSize;

                return true;
            } else {
                return false;
            }



        } catch (IOException e) {
            return false;
        }
    }


    public boolean put(Object obj) {
        return put(obj.hashCode(), obj);
    }


    public <T> T get(int id, Class<T> type) {
        try {
            String data = fileHandler.readData(id);
            return Marshaller.demarshalize(data, type);
        } catch (IOException e) {
            if (e instanceof NoSuchFileException) {
                return null;
            }
            throw new RuntimeException("Error while reading object from filesystem", e);
        }
    }


    public boolean remove(int id) {
        currentCacheSize -= fileHandler.getFileSize(id);
        return fileHandler.removeFile(id);
    }

    @Override
    public int size() {
        return fileHandler.getFilesCount();
    }

    public void clear() {
        try {
            fileHandler.clearDir();
            fileHandler.setupDir();
            currentCacheSize = 0;
        } catch (Exception e) {
            throw new RuntimeException("Error while clearing cache directory", e);
        }
    }


    private boolean memoryCheck(int objectSize) {
        return currentCacheSize + objectSize < maxCacheSize;
    }


}
