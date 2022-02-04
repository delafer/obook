package net.korvin.fb2;

import net.j7.ebook.entity.ebook.Book;
import net.korvin.api.StaxModel;
import net.korvin.entities.Tag;
import net.korvin.entities.TagOld;
import net.korvin.entities.TagParser;

import javax.xml.stream.XMLStreamReader;

import static net.korvin.utils.ObjectPool.on;

public class Fb2Book implements StaxModel {

    TagOld model =
    TagOld.of("FictionBook",
        TagOld.of("description",
            TagOld.of("title-info",
                TagOld.of("genre", Fb2Book::readGenre),
                TagOld.of("lang", Fb2Book::readLang))
        ),
        TagOld.of("body"),
        TagOld.of("binary")
    );

    public static TagParser readGenre(String keyParser, Book model) {
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

    public static TagParser readLang(String keyParser, Book model) {
        return on.get(keyParser, $ -> new TagParser() {
        });
    }

    public static TagParser readOther(String keyParser, Book model) {
        return on.get(keyParser, $ -> new TagParser(){});
    }

    public Tag getModel() {
        /* return this.model;*/
        return null;
    }

}
