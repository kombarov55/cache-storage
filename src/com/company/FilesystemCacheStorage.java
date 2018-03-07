package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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
            writeToFileStorage(data, obj.hashCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object get(int hashcode) {
        try {
            String data = readFromFileStorage(hashcode);

            return marshaller.demarshalize(data);
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

    private void writeToFileStorage(String data, int hashcode) throws IOException {
        Files.write(
                Paths.get(cacheDir + "/" + hashcode),
                data.getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }

    private String readFromFileStorage(int hashcode) throws IOException {
        return Files.readAllLines(Paths.get(cacheDir + "/" + hashcode)).get(0);
    }
}
