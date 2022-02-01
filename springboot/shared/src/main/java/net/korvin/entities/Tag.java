package net.korvin.entities;

import lombok.Setter;
import net.j7.ebook.entity.ebook.Book;
import net.korvin.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tag {

    static Map<String, BiFunction<String, Book, TagParser>> parsers = new ConcurrentHashMap<>();

    String tag;
    public BiFunction<String, Book, TagParser>  consumer;
    Map<String, Tag> children = new HashMap<>();

    @Setter
    Tag parent;

    private Tag(String tag, BiFunction<String, Book, TagParser> consumer) {
        this.tag = tag;
        this.consumer = consumer;

    }

    public void init() {
        if (null != consumer) {
            Tag.parsers.put(this.path(), consumer);
        }
        children.values().forEach(Tag::init);
    }

    private Tag(String tag, Tag... structs) {
        this.tag = tag;
        this.children = Stream.of(structs).collect(Collectors.toMap(Tag::getTag, Function.identity()));
        Stream.of(structs).forEach((next) -> next.setParent(this));
    }

    private String getTag() {
        return this.tag;
    }

//    public Tag childTag(String tagName) {
//        return children.get(tagName);
//    }

    public static TagParser parser(String path, String tag, Book book) {
        BiFunction<String, Book, TagParser> entry = Tag.parsers.get(path);
        return entry != null ? entry.apply(tag, book) : null;
    }


    public static Tag of(String tag, Tag... structs) {
        return new Tag(tag, structs);
    }
    public static Tag of(String tag, BiFunction<String, Book, TagParser>  consumer) {
        return new Tag(tag, consumer);
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
