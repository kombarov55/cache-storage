package com.company.filesystemimpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Операции с файлами
 */
public class FileHandler {

    private String cacheDir;

    FileHandler(String cacheDir) {
        this.cacheDir = cacheDir;
    }

    public void writeData(String data, int hashcode) throws IOException {
        Files.write(
                Paths.get(buildFileRelativeLink(cacheDir, hashcode)),
                data.getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }

    public String readData(int hashcode) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(buildFileRelativeLink(cacheDir, hashcode)));

        return new String(bytes);
    }

    public boolean removeFile(int hashcode) {
        return new File(buildFileRelativeLink(cacheDir, hashcode)).delete();
    }

    public void setupDir() {
        File dir = new File(cacheDir);

        dir.mkdir();
        dir.deleteOnExit();

    }

    public int getFilesCount() {
        return new File(cacheDir).list().length;
    }

    public long getFileSize(int id) {
        return new File(buildFileRelativeLink(cacheDir, id)).length();
    }

    public void clearDir() throws IOException {
        Path dirPath = Paths.get(cacheDir);

        if (Files.exists(dirPath)) {
            Files.walkFileTree(dirPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                    Files.delete(path);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    private String buildFileRelativeLink(String cacheDir, int id) {
        return cacheDir + "/" + id;
    }
}
