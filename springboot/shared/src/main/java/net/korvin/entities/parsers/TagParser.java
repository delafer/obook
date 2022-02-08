package net.korvin.entities.parsers;

import net.j7.ebook.entity.ebook.Book;
import net.korvin.entities.PoolObject;
import net.korvin.utils.TagStack.TagStack;

import javax.xml.stream.XMLStreamReader;

import static javax.xml.stream.XMLStreamConstants.*;

public abstract class TagParser extends PoolObject {
    {System.out.println("Instantiated: "+this.getClass().getSimpleName());}
    Book model;
    TagParser parent;


    public boolean allowChilds() { return true; }

    public void process(final int eventType, final XMLStreamReader reader,  final TagStack tagPath) {
        //System.out.println("tagPath: "+tagPath+" :"+tagPath.contains(TagStack.parseTags("author/last-name")));

        switch (eventType) {
            case START_ELEMENT -> start(reader, tagPath);
            case END_ELEMENT -> end(reader, tagPath);
            case CHARACTERS -> chars(reader, tagPath);
        };

    }

    public void start(final XMLStreamReader reader, TagStack tagPath) {};
    public void end(final XMLStreamReader reader, TagStack tagPath) {}
    public void chars(final XMLStreamReader reader, TagStack tagPath) {};
    public void release() {};
}
