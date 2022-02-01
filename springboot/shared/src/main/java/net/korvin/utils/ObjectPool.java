package net.korvin.utils;

import net.korvin.entities.TagParser;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class ObjectPool {

    public static final ObjectPool on = new ObjectPool();

    static ObjectPool on() {
        return on;
    }

    private ConcurrentHashMap<String, TagParser> map = new ConcurrentHashMap<>();

    public <E extends TagParser>E get(String key, Function<String, E> mappingFunction) {
        return (E) map.computeIfAbsent(key, mappingFunction);
    }

}
