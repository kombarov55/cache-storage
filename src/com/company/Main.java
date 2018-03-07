package com.company;

public class Main {

    public static class TestClass {
        int a = 5;
        int b = 10;

        String str = "asdfasdfasdf";

        @Override
        public String toString() {
            return new StringBuilder("{a=").append(a)
                    .append(",\tb=").append(b)
                    .append(",\tstr=").append(str)
                    .append("}")
                    .toString();
        }
    }

    public static void main(String[] args) {

        Cache cache = CacheStorage.getInstance();



        TestClass c = new TestClass();

        cache.put(new TestClass());
        cache.put(new TestClass());
        cache.put(new TestClass());
        cache.put(new TestClass());
        cache.put(new TestClass());
        cache.put(new TestClass());
        cache.put(c);

        TestClass cc = cache.get(c.hashCode(), TestClass.class);

        System.out.println(cc);

        cache.clear();

        cache.put(c);
        System.out.println(cache.get(c.hashCode(), TestClass.class));
    }
}
