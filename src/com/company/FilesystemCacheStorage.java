package com.company;

import java.io.IOException;

public class FilesystemCacheStorage implements CacheStorage {

    private final boolean persistent;

    private Marshaller marshaller = new Marshaller();
    private FileHandler fileHandler;

    FilesystemCacheStorage(boolean persistent, String cacheDir) throws IOException {
        this.persistent = persistent;
        fileHandler = new FileHandler(cacheDir);

        fileHandler.setupDir();
    }

    @Override
    public void put(Object obj) {
        try {
            String data = marshaller.marshalize(obj);
            fileHandler.writeData(data, obj.hashCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T get(int hashcode, Class<T> type) {
        try {

            String data = fileHandler.readData(hashcode);

            return marshaller.demarshalize(data, type);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean remove(int hashcode) {
        return false;
    }

    @Override
    public void clear() {

    }



}
