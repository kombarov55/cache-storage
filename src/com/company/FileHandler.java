package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileHandler {

    private String cacheDir;

    public FileHandler(String cacheDir) {
        this.cacheDir = cacheDir;
    }

    public void writeData(String data, int hashcode) throws IOException {
        Files.write(
                Paths.get(cacheDir + "/" + hashcode),
                data.getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }

    public String readData(int hashcode) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(cacheDir + "/" + hashcode));

        return new String(bytes);
    }

    public void setupDir() throws IOException {
        File dir = new File(cacheDir);

        dir.mkdir();
        dir.deleteOnExit();

    }

}
