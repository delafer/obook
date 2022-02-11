package net.korvin.entities;

import net.korvin.api.StaxModel;
import net.korvin.entities.parsers.TagParser;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public abstract class AbstractBook implements StaxModel {

    private ConcurrentHashMap<String, TagParser> map = new ConcurrentHashMap<>();

    public <E extends TagParser>E get(String key, Function<String, E> mappingFunction) {
        //map.clear();
        //return (E) map.computeIfAbsent(key, mappingFunction);
        return mappingFunction.apply(key);
    }

}
