package com.company;

import com.company.filesystemimpl.FilesystemCache;
import com.company.memoryimpl.MemoryCache;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Входной класс для получения реализации кеширования.
 */
public class CacheStorage {

    private static final String PROP_FILE_PATH = "./conf/cache.properties";

    private static int level;
    private static String cacheDir;
    private static boolean persistent;
    private static long maxCacheSize;

    private static Cache instance;

    private CacheStorage() { }

    public static Cache getInstance() {
        if (instance == null) {
            instance = instantiate();
        }

        return instance;
    }

    private static Cache instantiate() {
        try {
            loadConfig(PROP_FILE_PATH);
            validateConfig();

            switch (level) {
                case 1:
                    return new MemoryCache(maxCacheSize, persistent);
                case 2:
                    return new FilesystemCache(maxCacheSize, cacheDir, persistent);
                default:
                    return new MemoryCache(maxCacheSize, persistent);
            }


        } catch (IOException e) {
            System.err.println(CacheStorage.class.getCanonicalName() + ": Не удалось считать конфигурацию из " + PROP_FILE_PATH);
            e.printStackTrace();
        }
        return null;
    }

    private static void loadConfig(String path) throws IOException {
        Properties p = new Properties();
        p.load(new FileInputStream(path));

        level = Integer.parseInt((String) p.getOrDefault("level", "1"));
        cacheDir = (String) p.getOrDefault("cache-dir", "./cache");
        persistent = Boolean.parseBoolean((String) p.getOrDefault("persistent", "false"));
        maxCacheSize = SizeStrResolver.resolveSize((String) p.getOrDefault("max-cache-size", "10Kb"));
    }

    private static void validateConfig() {
        if (level != 1 && level != 2) throw new RuntimeException("Уровень кеша должен быть 1 или 2");
    }
}
