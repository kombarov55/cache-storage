package com.company;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Входной класс для получения реализации кеширования.
 */
public class Cache {

    private static final String PROP_FILE_PATH = "./conf/cache.properties";

    private static int level = 1;
    private static String cacheDir;
    private static boolean persistent;
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

            return level == 2 ?
                    new FilesystemCacheStorage(persistent, cacheDir) :
                    new MemoryCacheStorage(cacheSize);

        } catch (IOException e) {
            System.err.println(Cache.class.getCanonicalName() + ": Не удалось считать конфигурацию из " + PROP_FILE_PATH);
            e.printStackTrace();
        }
        return null;
    }

    private static void loadConfig(String path) throws IOException {
        Properties p = new Properties();
        p.load(new FileInputStream(path));

        level = Integer.parseInt(Utils.getWithDefault(p.get("level"), "1"));
        cacheDir = Utils.getWithDefault(p.get("cache-dir"), "./cache");
        persistent = Boolean.parseBoolean(Utils.getWithDefault(p.get("persistent"), "false"));

        String cacheSizeStr = (Utils.getWithDefault(p.get("cache-size"), "" + 1024 * 10));
        cacheSize = Utils.resolveSize(cacheSizeStr);
    }

    private static void validateConfig() {
        if (level != 1 && level != 2) throw new RuntimeException("Уровень кеша должен быть 1 или 2");
    }
}
