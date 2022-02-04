package net.korvin.utils;

import java.util.*;

public class TagStackMap<E> {

    private Map<String, Map<TagStack, E>> tagsMap = new HashMap<>();

    public void put(TagStack keyArg, E value) {
        String key = keyArg.peek();
        Map<TagStack, E> tagStacks = tagsMap.computeIfAbsent(key, $ -> new HashMap<>());
    }


}
