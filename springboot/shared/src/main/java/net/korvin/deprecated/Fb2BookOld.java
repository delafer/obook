package net.korvin.deprecated;

import net.j7.ebook.entity.ebook.Book;
import net.korvin.api.StaxModel;
import net.korvin.entities.parsers.TagParser;
import net.korvin.entities.XmlTag;
import net.korvin.utils.TagStack.TagStack;

import javax.xml.stream.XMLStreamReader;

import static net.korvin.utils.ObjectPool.on;

public class Fb2BookOld implements StaxModel {

    TagOld model =
    TagOld.of("FictionBook",
        TagOld.of("description",
            TagOld.of("title-info",
                TagOld.of("genre", Fb2BookOld::readGenre),
                TagOld.of("lang", Fb2BookOld::readLang))
        ),
        TagOld.of("body"),
        TagOld.of("binary")
    );

    public static TagParser readGenre(String keyParser, Book model) {
        return on.get(keyParser /*"genreParser"*/, $ -> new TagParser() {
            @Override
            public void start(XMLStreamReader reader, TagStack tagPath) {
                System.out.println(":x:" + reader.getName().getLocalPart());
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

    public XmlTag getModel() {
        /* return this.model;*/
        return null;
    }

}
