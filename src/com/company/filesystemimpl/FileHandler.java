package com.company.filesystemimpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Операции с файлами
 */
public class FileHandler {

    private String cacheDir;

    FileHandler(String cacheDir) {
        this.cacheDir = cacheDir;
    }

    public void writeData(String data, long hashcode) throws IOException {
        Files.write(
                Paths.get(cacheDir + "/" + hashcode),
                data.getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }

    public String readData(int hashcode) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(cacheDir + "/" + hashcode));

        return new String(bytes);
    }

    public boolean removeFile(int hashcode) {
        return new File(cacheDir + "/" + hashcode).delete();
    }

    public void setupDir() {
        File dir = new File(cacheDir);

        dir.mkdir();
        dir.deleteOnExit();

    }

    public void clearDir() {
    }
}
