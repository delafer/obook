package net.j7.myparser.xmlstruct;

import java.util.List;
import java.util.function.Function;

public class Tag implements Block {

    Name name;
    List<Block> content;
    Tag parent;
    Attributes attrs;
    boolean selfClosing;

    public void addOutside(Tag tag) {}
    public void addInside(Tag tag) {}
    public void addText(String text) {}
    public void setText(String text) {}

    public Tag getParent(Function<Tag, Boolean> check, int maxDeep) { return null; }
    public boolean hasParent(Function<Tag, Boolean> check, int maxDeep) { return null != getParent(check, maxDeep); }

    public Tag getChild(Function<Tag, Boolean> check, int maxDeep) { return null; }
    public boolean hasChild(Function<Tag, Boolean> check, int maxDeep) { return null != getChild(check, maxDeep); }

    public Tag remove() {
        return parent;
    };

    public CharSequence render() { return null; };
}
