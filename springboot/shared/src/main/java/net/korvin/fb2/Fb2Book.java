package net.korvin.fb2;

import net.j7.ebook.entity.ebook.Book;
import net.korvin.api.StaxModel;
import net.korvin.entities.Tag;
import net.korvin.entities.TagParser;
import net.korvin.entities.XmlTag;

import javax.xml.stream.XMLStreamReader;

import static net.korvin.utils.ObjectPool.on;

public class Fb2Book implements StaxModel {

    XmlTag model;
    {
        modelReader();
    }

    public String rootTag() {
        return "FictionBook";
    }

    public void modelReader() {
        Tag struct =
            Tag.of("FictionBook",
                Tag.of("description",
                   Tag.of("title-info/genre", this::readGenre),
                   Tag.of("title-info/lang", this::readGenre),
                   Tag.of("title-info/book-title", this::readGenre),
                   Tag.of("title-info/author", this::readGenre)),
                Tag.of("body"),
                Tag.of("binary")
            );

        model = struct.buildModel();
        //System.out.println(model);
    }

    public static void main(String[] args) {
        Fb2Book book = new Fb2Book();
        book.modelReader();
    }

    public TagParser readGenre(String keyParser, Book model) {
        return on.get(keyParser /*"genreParser"*/, $ -> new TagParser() {
            @Override
            public void start(XMLStreamReader reader) {
               // System.out.println("(READ)" + reader.getName().getLocalPart());
            }
            public void chars(XMLStreamReader reader) {
                System.out.println(keyParser+"::: " + reader.getText());
            }
        });
    }

    @Override
    public XmlTag getModel() {
        return model;
    }
}

/*
        Tag ttt =
                Tag.of("FictionBook",
                Tag.of("description",
                        Tag.of("block1/aaa", this::readGenre),
                        Tag.of("title-info", Tag.of("yy", this::readGenre)),
                        Tag.of("title-info", this::readGenre),
                        Tag.of("title-info", Tag.of("zz", this::readGenre)),
                        Tag.of("block2", this::readGenre)
                        )
                        ,
                Tag.of("description/title-info/xx", this::readGenre),
                Tag.of("description/block1/bbb", this::readGenre),
                Tag.of("description/title-info/other", this::readGenre)
         );

 */