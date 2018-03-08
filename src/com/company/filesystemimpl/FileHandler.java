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

    public void writeData(String data, Object id) throws IOException {
        Files.write(
                Paths.get(buildFileRelativeLink(cacheDir, id)),
                data.getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }

    public String readData(Object id) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(buildFileRelativeLink(cacheDir, id)));

        return new String(bytes);
    }

    public boolean removeFile(Object id) {
        return new File(buildFileRelativeLink(cacheDir, id)).delete();
    }

    public void setupDir() {
        File dir = new File(cacheDir);

        dir.mkdir();
        dir.deleteOnExit();

    }

    public int getFilesCount() {
        return new File(cacheDir).list().length;
    }

    public long getFileSize(Object id) {
        return new File(buildFileRelativeLink(cacheDir, id)).length();
    }

    public boolean fileExists(Object id) {
        return new File(buildFileRelativeLink(cacheDir, id)).exists();
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

    private String buildFileRelativeLink(String cacheDir, Object id) {
        return cacheDir + "/" + id;
    }
}
