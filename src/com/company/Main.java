package com.company;

import java.io.File;

public class Main {

    public static void main(String[] args) {
       CacheStorage cache = Cache.getInstance();

       cache.put(new File("asdf"));

    }
}
