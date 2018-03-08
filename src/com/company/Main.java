package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Cache cache = CacheStorage.getInstance();


        cache.put(new TestClass());
        cache.put(new TestClass());

        TestClass c = new TestClass();
        c.xs.add(new TestClass());
        c.xs.add(new TestClass());

        cache.put(c);

        System.out.println(cache.size());
        System.out.println(cache.get(c.hashCode(), TestClass.class));

        for (int i = 0; i < 100; i++) {
            cache.put(new TestClass());
            System.out.println(cache.size());
        }

        cache.clear();
        cache.put(c);
        System.out.println(cache.get(c.hashCode(), TestClass.class));

        cache.remove(c.hashCode());

        System.out.println(cache.get(c.hashCode(), TestClass.class));
    }


    public static class TestClass {
        int a = 5;
        int b = 10;
        String str = "asdfasdfasdf";
        List<TestClass> xs = new ArrayList<>();
        List<String> strings = Arrays.asList("asdf", "asdf", "asdf");

        @Override
        public String toString() {
            return "TestClass{" +
                    "a=" + a +
                    ", b=" + b +
                    ", str='" + str + '\'' +
                    ", xs=" + xs +
                    ", strings=" + strings +
                    '}';
        }

    }
}
