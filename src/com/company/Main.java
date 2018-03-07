package com.company;

public class Main {

    public static class TestClass {
        int a = 5;
        int b = 10;
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.io.tmpdir"));

        CacheStorage cache = Cache.getInstance();



        TestClass c = new TestClass();

        cache.put(c);
        TestClass cc = cache.get(c.hashCode(), TestClass.class);


    }
}
