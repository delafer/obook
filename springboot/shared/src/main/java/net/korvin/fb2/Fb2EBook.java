package net.korvin.fb2;

import net.j7.ebook.entity.ebook.Book;
import net.korvin.entities.Tag;
import net.korvin.entities.TagParser;

import javax.xml.stream.XMLStreamReader;

import static net.korvin.utils.ObjectPool.on;

public class Fb2EBook {

    public String rootTag() {
        return "FictionBook";
    }

    public void modelReader() {

        Tag.of("FictionBook/description/title-info", this::readGenre);

        String a = "";
        a += "2";
    }

    public TagParser readGenre(String keyParser, Book model) {
        return on.get(keyParser /*"genreParser"*/, $ -> new TagParser() {
            @Override
            public void start(XMLStreamReader reader) {
                System.out.println(":::" + reader.getName().getLocalPart());
            }

            public void chars(XMLStreamReader reader) {
                System.out.println("genre: " + reader.getText());
            }
        });
    }
}
