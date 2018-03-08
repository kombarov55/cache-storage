package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {



    public static void main(String[] args) {

        System.out.println(">>>>>>>>>>>>>>>>");
        System.out.println("Тестирование");

        System.out.println("Инициализация");
        Cache cache = CacheStorage.getInstance();
        System.out.println("Инициализация успешна");

        System.out.println("Создание и установка в кеш тестового объекта");

        TestClass c = new TestClass();
        c.xs.add(new TestClass());
        c.xs.add(new TestClass());

        cache.put(c);
        System.out.println("Объект успешно положен в кеш");

        System.out.println("Размер кеша: " + cache.size());

        System.out.println("Объект " + (cache.contains(c.hashCode()) ? "" : "не") + "был положен в кеш");

        System.out.println("Получение объекта из кеша: " + cache.get(c.hashCode(), TestClass.class));

        cache.remove(c.hashCode());

        System.out.println("Объект " + (!cache.contains(c.hashCode()) ? "" : "не") + "был удалён в кеш");

        System.out.println("Заполнение кеша 100 тестовыми объектами");
        for (int i = 0; i < 100; i++) {
            cache.put(new TestClass());
        }

        System.out.println("Размер кеша: " + cache.size());

        System.out.println("Очистка кеша");
        cache.clear();
        System.out.println("Кеш успешно очищен");
        System.out.println(">>>>>>>>>>>>>>>>");
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
