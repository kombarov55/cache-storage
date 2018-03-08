package com.company;

/**
 * Интерфейс кеша.
 */
public interface Cache {

    /**
     * Положить объект в кеш.
     *
     * @param obj объект.
     * @param id идентификатор.
     *
     */
    boolean put(Object id, Object obj);

    /**
     * Положить объект в кеш. В качестве идентификатора будет использоваться hashcode
     *
     * @param obj объект.
     * @return был ли объект положен в кеш
     */
    boolean put(Object obj);

    /**
     * Получить объект из кеша.
     * @param id идентификатор объекта
     * @param type тип объекта, который необходимо получить
     * @return ссылка на объект если он есть, или null если его нет в кеше.
     */
    <T> T get(Object id, Class<T> type);

    /**
     * Удалить объект из кеша.
     *
     * @param id идентификатор объекта.
     * @return был или не был закеширован объект.
     */
    boolean remove(Object id);

    /**
     * Содержит ли кеш в себе объект с переданным идентификатором
     * @param id идентификатор
     * @return да / нет
     */
    boolean contains(Object id);

    /**
     * Количество объектов в кеше.
     *
     * @return Количество объектов в кеше.
     */
    int size();

    /**
     * Очистить кеш.
     */
    void clear();
}
