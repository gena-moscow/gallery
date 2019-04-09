package org.genamoscow;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class NotFoundException extends Exception {
    public NotFoundException(Class clazz, String... searchParamsMap) {
        super(NotFoundException.getMessage(clazz.getSimpleName(), getMap(String.class, String.class, searchParamsMap)));
    }

    private static String getMessage(String entity, Map<String, String> searchParams) {
        return StringUtils.capitalize(entity) + " not found for parameters " + searchParams;
    }

    private static <K, V> Map<K, V> getMap(
            Class<K> keyType, Class<V> valueType, String... entries) {
        if (entries.length % 2 == 1)
            throw new IllegalArgumentException("Invalid entries");
        return IntStream.range(0, entries.length / 2).map(i -> i * 2)
                .collect(HashMap::new,
                         (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
                         Map::putAll);
    }
}
