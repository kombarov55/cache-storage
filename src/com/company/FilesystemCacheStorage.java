package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FilesystemCacheStorage implements CacheStorage {

    private final boolean persistent;
    private final String cacheDir;

    private Marshaller marshaller = new Marshaller();

    FilesystemCacheStorage(boolean persistent, String cacheDir) {
        this.persistent = persistent;
        this.cacheDir = cacheDir;

        createDirIfNeeded();
    }

    @Override
    public void put(Object obj) {
        String data = marshaller.marshalize(obj);

        try {
            Files.write(
                    Paths.get(cacheDir + "/" + marshaller.pickId(obj)),
                    data.getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object get(int hashcode) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(cacheDir + "/" + hashcode));

            return marshaller.demarshalize(new String(bytes));

        } catch (IOException e) {
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

    private void createDirIfNeeded() {
        File dir = new File(cacheDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

}
