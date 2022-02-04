package net.korvin.entities;

import net.j7.ebook.entity.ebook.Book;
import net.korvin.utils.TagStack;

import javax.xml.stream.XMLStreamReader;

import static javax.xml.stream.XMLStreamConstants.*;

public abstract class TagParser extends PoolObject {
    {System.out.println("Instantiated: "+this.getClass().getSimpleName());}
    Book model;
    TagParser parent;

    TagStack path;

    public void process(final int eventType, final XMLStreamReader reader) {
        switch (eventType) {
            case START_ELEMENT -> start(reader);
            case END_ELEMENT -> end(reader);
            case CHARACTERS -> chars(reader);
        };

    }

    TagStack path() {
        if (null == path) path = new TagStack();
        return path;
    }

    public void start(final XMLStreamReader reader) {};
    public void end(final XMLStreamReader reader) {}
    public void chars(final XMLStreamReader reader) {};
    public void release() {};
}
