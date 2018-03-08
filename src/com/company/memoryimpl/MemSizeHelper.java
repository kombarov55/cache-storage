package com.company.memoryimpl;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Подсчёт размера объектов. Создавался на основе https://habrahabr.ru/post/134102/
 */
public class MemSizeHelper {

    private static final String SYSTEM_ARCH = System.getProperty("os.arch");
    private static final int HEADER_SIZE = SYSTEM_ARCH.contains("32") ? 8 : 16;
    private static final int ARRAY_LENGTH_VAR_SIZE = 4;
    private static final int REF_SIZE = SYSTEM_ARCH.contains("32") ? 4 : 8;

    private static final Map<String, Integer> PRIMITIVE_SIZES = new HashMap<>();
    static {
        PRIMITIVE_SIZES.put("boolean", 1);
        PRIMITIVE_SIZES.put("byte", 1);
        PRIMITIVE_SIZES.put("short", 2);
        PRIMITIVE_SIZES.put("char", 2);
        PRIMITIVE_SIZES.put("int", 4);
        PRIMITIVE_SIZES.put("float", 4);
        PRIMITIVE_SIZES.put("double", 8);
        PRIMITIVE_SIZES.put("long", 8);
    }

    private MemSizeHelper() { }

    public static int getObjectSize(Object obj) throws Exception {

        int totalSize = HEADER_SIZE;
        Class<?> type = obj.getClass();

        if (type.isArray()) {
            return getArraySize(obj);
        }

        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);

            if (Modifier.isStatic(field.getModifiers())) continue;
            
            if (field.getType().isPrimitive()) {
                totalSize += PRIMITIVE_SIZES.get(field.getType().getSimpleName());
            } else {
                totalSize += REF_SIZE;
                totalSize += getObjectSize(field.get(obj));
            }
        }

        return justificate(totalSize);
    }


    private static int getArraySize(Object array) throws Exception {
        int totalSize = HEADER_SIZE + ARRAY_LENGTH_VAR_SIZE;
        int arrayLength = Array.getLength(array);
        
        if (arrayLength == 0) return totalSize;

        String typeName = array.getClass().getSimpleName();
        String elementName = typeName.substring(0, typeName.length() - 2);

        Integer elementSize = PRIMITIVE_SIZES.get(elementName);
        boolean notAPrimitive = elementSize == null;
        if (notAPrimitive) {
            elementSize = getObjectSize(Array.get(array, 0));
        }

        return justificate(totalSize + elementSize * arrayLength);
    }


    /**
     * Выравнивание размера. Размер всегда кратен 8, при несоответстии он дополняется до этой кратности.
     * @param size размер объекта.
     * @return выровненный размер объекта.
     */
    private static int justificate(int size) {
        int residual = size % 8;
        return residual == 0 ?
                size :
                size - residual + 8;

    }

}
