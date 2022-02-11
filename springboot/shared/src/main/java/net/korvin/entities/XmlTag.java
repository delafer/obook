package net.korvin.entities;

import net.j7.ebook.entity.ebook.Book;
import net.korvin.entities.parsers.TagParser;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class XmlTag {
    Map<String, XmlTag> children = new TreeMap<>();
    XmlTag parent;
    String tag;
    BiFunction<String, Book, TagParser> consumer;
    public XmlTag(String tag) {
        this(tag, null);
    }

    public XmlTag(String tag, XmlTag parent) {
        this.tag = tag;
        this.parent = parent;
    }

    public XmlTag getTag(String tag) {
        return children.get(tag);
    }

    public XmlTag addChild(String tag) {

//            XTag xtag = children.get(tag);
//            if (null == xtag) {
//                xtag = new XTag(tag);
//                xtag.parent = this;
//                children.put(tag, xtag);
//            }

        return children.computeIfAbsent(tag, arg -> new XmlTag(arg, this));
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(tag);
        if (null != children && !children.isEmpty()) {
            sb.append('[');
            int i = 0;
            for (XmlTag child : children.values()) {
                if (i++>0) sb.append(',');
                sb.append(child.toString());
            }
            sb.append(']');
        }
        return sb.toString();
    }

    private Supplier<BiFunction<String, Book, TagParser>> lazyConsumer = () -> {
        BiFunction<String, Book, TagParser> val = initConsumer(true);
        lazyConsumer = () -> val;
        return val;
    };

    BiFunction<String, Book, TagParser> initConsumer(boolean root) {
        return (consumer != null) ? consumer : parent != null ? parent.initConsumer(false) : null;
    }


    public BiFunction<String, Book, TagParser> getConsumer() {
        return initConsumer(true); //lazyConsumer.get();
    }

    public TagParser getParser(String keyParser, Book model) {
        BiFunction<String, Book, TagParser> consumer = getConsumer();
        return consumer != null ? consumer.apply(keyParser, model) : null;
    }

}