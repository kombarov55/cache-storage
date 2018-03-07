package com.company;

import com.company.filesystemimpl.FilesystemCacheStorage;
import com.company.memoryimpl.MemoryCacheStorage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Входной класс для получения реализации кеширования.
 */
public class Cache {

    private static final String PROP_FILE_PATH = "./conf/cache.properties";

    private static int level;
    private static String cacheDir;

    private static long cacheSize;

    private static CacheStorage instance;

    private Cache() { }

    public static CacheStorage getInstance() {
        if (instance == null) {
            instance = instantiate();
        }

        return instance;
    }

    private static CacheStorage instantiate() {
        try {
            loadConfig(PROP_FILE_PATH);
            validateConfig();

            switch (level) {
                case 1:
                    return new MemoryCacheStorage(cacheSize);
                case 2:
                    return new FilesystemCacheStorage(cacheSize, cacheDir);
                default:
                    return new MemoryCacheStorage(cacheSize);
            }


        } catch (IOException e) {
            System.err.println(Cache.class.getCanonicalName() + ": Не удалось считать конфигурацию из " + PROP_FILE_PATH);
            e.printStackTrace();
        }
        return null;
    }

    private static void loadConfig(String path) throws IOException {
        Properties p = new Properties();
        p.load(new FileInputStream(path));

        level = Integer.parseInt((String) p.getOrDefault("level", "1"));
        cacheDir = (String) p.getOrDefault("cache-dir", "./cache");
        cacheSize = Utils.resolveSize((String) p.getOrDefault("cache-size", "10Kb"));
    }

    private static void validateConfig() {
        if (level != 1 && level != 2) throw new RuntimeException("Уровень кеша должен быть 1 или 2");
    }
}
