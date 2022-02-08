package net.korvin.deprecated;

import lombok.Setter;
import net.j7.ebook.entity.ebook.Book;
import net.korvin.entities.parsers.TagParser;
import net.korvin.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagOld {

    static Map<String, BiFunction<String, Book, TagParser>> parsers = new ConcurrentHashMap<>();

    String tag;
    public BiFunction<String, Book, TagParser>  consumer;
    Map<String, TagOld> children = new HashMap<>();

    @Setter
    TagOld parent;

    private TagOld(String tag, BiFunction<String, Book, TagParser> consumer) {
        this.tag = tag;
        this.consumer = consumer;

    }

    public void init() {
        if (null != consumer) {
            TagOld.parsers.put(this.path(), consumer);
        }
        children.values().forEach(TagOld::init);
    }

    private TagOld(String tag, TagOld... structs) {
        this.tag = tag;
        this.children = Stream.of(structs).collect(Collectors.toMap(TagOld::getTag, Function.identity()));
        Stream.of(structs).forEach((next) -> next.setParent(this));
    }

    private String getTag() {
        return this.tag;
    }

//    public Tag childTag(String tagName) {
//        return children.get(tagName);
//    }

    public static TagParser parser(String path, String tag, Book book) {
        BiFunction<String, Book, TagParser> entry = TagOld.parsers.get(path);
        return entry != null ? entry.apply(tag, book) : null;
    }


    public static TagOld of(String tag, TagOld... structs) {
        return new TagOld(tag, structs);
    }
    public static TagOld of(String tag, BiFunction<String, Book, TagParser>  consumer) {
        return new TagOld(tag, consumer);
    }

    public String path() {
        if (parent != null && !Utils.isEmpty(parent.tag)) {
            return parent.path() + '/' + tag;
        }
        return tag;
    }

    @Override
    public String toString() {
        return "Tag{"+tag+"}";
    }
}
