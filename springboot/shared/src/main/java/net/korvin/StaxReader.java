package net.korvin;

import com.fasterxml.aalto.stax.InputFactoryImpl;
import net.j7.ebook.entity.ebook.Book;
import net.korvin.entities.AbstractBook;
import net.korvin.entities.XmlTag;
import net.korvin.entities.parsers.TagParser;
import net.korvin.utils.TagStack.TagStack;
import org.codehaus.stax2.XMLInputFactory2;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.TransformerException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;
import java.util.function.Supplier;

import static javax.xml.stream.XMLStreamConstants.*;

public abstract class StaxReader {


    private Supplier<XMLInputFactory2> factory = () -> {
        XMLInputFactory2 val = getFactory();
        factory = () -> val;
        return val;
    };


    public void process(String fileName) throws IOException, TransformerException, XMLStreamException {
        FileInputStream fis = new FileInputStream(fileName);
        process(fis);
        fis.close();
    }

    protected XMLInputFactory2 getFactory()
    {
        XMLInputFactory2 f = (XMLInputFactory2) InputFactoryImpl.newInstance();
        f.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.FALSE);
        f.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
        f.setProperty(XMLInputFactory.IS_VALIDATING, Boolean.FALSE);
        f.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.FALSE);
        f.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);

        f.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);
        f.setProperty(XMLInputFactory2.P_PRESERVE_LOCATION, Boolean.FALSE);
        f.setProperty(XMLInputFactory2.P_REPORT_PROLOG_WHITESPACE, Boolean.FALSE);
        f.setProperty(XMLInputFactory2.P_LAZY_PARSING, Boolean.TRUE);
        f.configureForSpeed();
        return (XMLInputFactory2) f;
    }

    private String tagName(XMLStreamReader streamReader) {
        return streamReader.getName().getLocalPart();
    }

    public Book process(InputStream inputStream) throws FileNotFoundException, XMLStreamException, TransformerException {
        XMLInputFactory factory = this.factory.get();
        XMLStreamReader streamReader = factory.createXMLStreamReader(inputStream);
        var stackPath = new TagStack();
        var stackStruct = new Stack<XmlTag>();
        AbstractBook model = getParsingModel();
        var book = new Book();
        TagParser parser = null;
        XmlTag next = model.getModel();

        boolean eod = false;
        while (!eod && streamReader.hasNext()) {
            int eventType = streamReader.next();
            switch (eventType) {
                case START_ELEMENT -> {
                    String tag = tagName(streamReader);
                    stackPath.push(tag);
                    stackStruct.push(next);
                    if (null != next) {
                        next = next.getTag(tag);
                        if (null !=next) {
                            parser = next.getParser(tag, book);
                        } else
                        if (parser != null && !parser.allowChilds()) {
                             parser = null;
                        }
                    }
                    if (null != parser) parser.process(eventType, streamReader, stackPath);
                }
                case END_ELEMENT -> {
                    String tag = tagName(streamReader);
                    if (model.rootTag().equals(tag)) {
                        eod = true;
                    } else {
                        if (null != parser) parser.process(eventType, streamReader, stackPath);
                        stackPath.pop(); //tagStack.pop(tag)
                        next = stackStruct.pop();
                        if (null != next) {
                            parser = next.getParser(stackPath.peek(), book);
                        }
                    }
                }
                case START_DOCUMENT -> { /* ignore */}
                case END_DOCUMENT -> { eod = true; }
                case CDATA, CHARACTERS, ENTITY_DECLARATION, ENTITY_REFERENCE -> {
                    if (null != parser) parser.process(eventType, streamReader, stackPath);
                }
                default -> {
                    System.out.println("UNPARSED EVENT: "+eventType);
                }
            }
        }


        System.out.println("FINISHED: "+book.getAuthors());
        //TagProcessor t = processorMap.get(tagStack);
        return book;
    }

    abstract AbstractBook getParsingModel();

}
