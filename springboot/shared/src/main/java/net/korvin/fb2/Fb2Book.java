package net.korvin.fb2;

import net.j7.ebook.entity.ebook.Author;
import net.korvin.entities.AbstractBook;
import net.korvin.entities.BookCage;
import net.korvin.entities.Tag;
import net.korvin.entities.XmlTag;
import net.korvin.entities.parsers.Attr;
import net.korvin.entities.parsers.TagParser;
import net.korvin.entities.parsers.TextTagParser;
import net.korvin.utils.TagStack.TagStack;

import javax.xml.stream.XMLStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;

public class Fb2Book extends AbstractBook {
    XmlTag model;

    public Fb2Book() {
        super();
        modelReader();
    }

    public String rootTag() {
        return "FictionBook";
    }

    public void modelReader() {
        Tag struct =
            Tag.of("FictionBook",
                Tag.of("description",
                    Tag.of("title-info/genre", this::addGenre),
                    Tag.of("title-info/author", this::addAuthors),
                    Tag.of("title-info/book-title", this::addTitle),
                    Tag.of("title-info/annotation", this::addAnnotation)
                ),
                Tag.of("body"),
                Tag.of("description/title-info/coverpage/image", this::readCover),
                Tag.of("binary", this::readBinary)
            );
        model = struct.buildModel(holder);
//        System.out.println(model);
    }

    private TagParser readCover(String s, BookCage that) {
        return new TagParser() {
            @Override
            public void start(XMLStreamReader reader, TagStack tagPath) {
                Attr atrs = new Attr(reader);
                System.out.println("Image cover: "+atrs.get("href"));
            }
        };
    }

    private TagParser addAuthors(String tag, BookCage that) {
        return new TagParser() {
            StringBuilder buff = new StringBuilder();
            Author author;
            public void chars(XMLStreamReader reader, TagStack tagPath) {
                buff.append(reader.getText().trim());
            }
            public void start(XMLStreamReader reader, TagStack tagPath) {
                //System.out.println("start: "+tagPath.peek());
                if (Objects.equals(tagPath.peek(), "author")) {
                    author = that.book.getAuthors().newAuthor();
                } else {
                    buff.setLength(0);
                }
            }
            public void end(XMLStreamReader reader, TagStack tagPath) {
                //System.out.println("end: "+tagPath.peek());
                switch (tagPath.peek()) {
                    case "first-name" -> author.setForename(buff.toString());
                    case "last-name" -> author.setSurname(buff.toString());
                    case "middle-name" ->  author.setMidname(buff.toString());
                }

            }
        };
    }

    private TagParser readBinary(String tag, BookCage that) {
        return new TagParser() {
            public void start(XMLStreamReader reader, TagStack tagPath) {
                Attr attr = new Attr(reader);
                System.out.println(attr.get("id")+ "  "+attr.get("content-type"));
                sb = new StringBuilder();
            }
            static int count = 0;
            StringBuilder sb;
            @Override
            public void chars(XMLStreamReader reader, TagStack tagPath) {
                sb.append(reader.getText().trim());
            }

            @Override
            public void end(XMLStreamReader reader, TagStack tagPath) {
                count++;
                String text = sb.toString().replace("\n", "");

                try {
                    byte[] decodedBytes = Base64.getDecoder().decode(text);
                    Path path = Paths.get("A:\\img\\image"+count+".jpg");
                    Files.write(path, decodedBytes);
                } catch (Throwable e) {
                    e.printStackTrace();
                    System.out.println("["+text+"]");
                }
            }
        };
    }

    @Override
    public XmlTag getModel() {
        return model;
    }


    private TagParser addGenre(String tag, BookCage that) {
        return new TextTagParser() {
            public void setValue(String value) {
                that.book.getGenres().addGenre(value);
            }
        };
    }

    private TagParser addTitle(String tag, BookCage that) {
        return new TextTagParser() {
            public void setValue(String value) {
                that.book.setTitle(value);
        }};
    }

    private TagParser addAnnotation(String tag, BookCage that) {
        return new TextTagParser() {
            public void setValue(String value) {
                that.book.setDescription(value);
            }};
    }
}

