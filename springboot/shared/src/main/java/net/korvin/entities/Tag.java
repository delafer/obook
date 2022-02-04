package net.korvin.entities;

import lombok.Setter;
import net.j7.ebook.entity.ebook.Book;
import net.korvin.utils.TagStack;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Tag {

    static Map<TagStack, BiFunction<String, Book, TagParser>> parsers = new ConcurrentHashMap<>();

    TagStack xpath;
    public BiFunction<String, Book, TagParser>  consumer;

    @Setter
    Tag parent;

    public void init() {}

    private Tag(String tagPath, BiFunction<String, Book, TagParser> consumer) {
        this.xpath = TagStack.parseTags(tagPath);
        this.consumer = consumer;

    }

    public static TagParser parser(TagStack xpath, String tag, Book book) {
        BiFunction<String, Book, TagParser> entry = Tag.parsers.get(xpath);
        return entry != null ? entry.apply(tag, book) : null;
    }

    public static Tag of(String tag, BiFunction<String, Book, TagParser>  consumer) {
        return new Tag(tag, consumer);
    }

    @Override
    public String toString() {
        return "Tag{"+ xpath +"}";
    }
}
