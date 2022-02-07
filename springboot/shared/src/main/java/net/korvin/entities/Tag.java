package net.korvin.entities;

import net.j7.ebook.entity.ebook.Book;
import net.korvin.utils.StringIterator;

import java.util.Iterator;
import java.util.Map;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tag {


    @Override
    public String toString() {
        return new StringJoiner(", ", Tag.class.getSimpleName() + "[", "]")
                .add("path='" + path + "'")
                .add("consumer=" + consumer)
                .add("children=" + children.size())
                .toString();
    }
    //    static Map<TagStack, BiFunction<String, Book, TagParser>> parsers = new ConcurrentHashMap<>();

    String path;
    BiFunction<String, Book, TagParser>  consumer;
    Map<String, Tag> children = new TreeMap<>();

    public void init() {}

    private Tag(String tagPath, BiFunction<String, Book, TagParser> consumer) {
        this.path = tagPath;
        this.consumer = consumer;
    }

    private Tag(String tagPath, Tag... structs) {
        this.path = tagPath;
        this.children = Stream.of(structs).collect(Collectors.toMap(Tag::getPath, Function.identity(), Tag::mergeFunction));
    }

    private String getPath() {
        return this.path;
    }

//    public static TagParser parser(TagStack xpath, String tag, Book book) {
//        BiFunction<String, Book, TagParser> entry = Tag.parsers.get(xpath);
//        return entry != null ? entry.apply(tag, book) : null;
//    }

    public static Tag of(String tag, BiFunction<String, Book, TagParser>  consumer) {
        return new Tag(tag, consumer);
    }

    public static Tag of(String tag, Tag... structs) {
        return new Tag(tag, structs);
    }

    public XmlTag buildModel() {
        XmlTag child = this.buildTree(null);
        XmlTag ret = new XmlTag("");
        ret.children.put(child.tag, child);
        return ret;
    }

    protected XmlTag buildTree(XmlTag root) {
        XmlTag base = getTreeBlock(root, this);
        for (Map.Entry<String, Tag> next : this.children.entrySet()) {
            //System.out.println(":::"+next.getKey());
            getTreeBlock(base, next.getValue());
            next.getValue().buildTree(base);
        }
        return base;
    }

    private static XmlTag getTreeBlock(XmlTag root, Tag tagArg) {
        XmlTag xtag = root, ret = null;
        Iterator<String> it = StringIterator.path(tagArg.path);

        while (it.hasNext()) {
            String next = it.next();
            xtag = xtag == null ? new XmlTag(next, xtag) : xtag.addChild(next);
            if (null == ret) ret = xtag;
        }
        xtag.consumer = tagArg.consumer;
        //tagArg.buildTree(root);
        //xtagTree.parent = xtag;
        return ret;
    }

    private static Tag mergeFunction(Tag x, Tag y) {
        //java.lang.IllegalStateException
        if (y.consumer != null) {
            if (x.consumer != null)
                throw new IllegalStateException("Multiple processors for same tag are not allowed.");
            x.consumer = y.consumer;
        }
        if (y.children != null) {
            if (x.children == null) {
                x.children = y.children;
            } else {
                x.children.putAll(y.children);
            }
        }
        return x;
    }


}
