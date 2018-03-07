package com.company;

/**
 * Интерфейс кеша.
 */
public interface CacheStorage {

    /**
     * Положить объект в кеш.
     * @param obj объект.
     */
    void put(Object obj);

    /**
     * Получить объект из кеша.
     * @param hashcode хеш-код объекта
     * @return ссылка на объект если он есть, или null если его нет в кеше.
     */
    Object get(int hashcode);

    /**
     * Удалить объект из кеша.
     *
     * @param hashcode хеш-код объекта
     * @return был или не был закеширован объект
     */
    boolean remove(int hashcode);

    /**
     * Очистить кеш.
     */
    void clear();
}
