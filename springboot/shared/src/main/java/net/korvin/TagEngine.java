package net.korvin;

import com.fasterxml.aalto.stax.InputFactoryImpl;
import net.j7.ebook.entity.ebook.Book;
import net.korvin.entities.TagParser;
import net.korvin.entities.XmlTag;
import net.korvin.fb2.Fb2Book;
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

import static javax.xml.stream.XMLStreamConstants.*;

public class TagEngine {


    public void process(String fileName) throws IOException,
            TransformerException, XMLStreamException {
        FileInputStream fis = new FileInputStream(fileName);
        process(fis);
        fis.close();
    }

    protected XMLInputFactory2 getFactory()
    {
        XMLInputFactory2 f = (XMLInputFactory2) InputFactoryImpl.newInstance();
        //System.out.println("Factory instance: "+f.getClass());


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

    public void process(InputStream inputStream) throws FileNotFoundException,
            XMLStreamException, TransformerException {
        XMLInputFactory factory = getFactory();
        factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES,Boolean.FALSE);
        XMLStreamReader streamReader = factory.createXMLStreamReader(inputStream);
        var stackPath = new TagStack();
        var stackStruct = new Stack<XmlTag>();
        Fb2Book model = new Fb2Book();
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
                        if (stackStruct.peek().skipChilds()) {
                             parser = null;
                        }
                    }
                    if (null != parser) parser.process(eventType, streamReader);
                }
                case END_ELEMENT -> {
                    String tag = tagName(streamReader);
                    if (model.rootTag().equals(tag)) {
                        eod = true;
                    } else {
                        if (null != parser) parser.process(eventType, streamReader);
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
                    if (null != parser) parser.process(eventType, streamReader);
                }
                default -> {
                    System.out.println("UNPARSED EVENT: "+eventType);
                }
            }
        }
        //TagProcessor t = processorMap.get(tagStack);
    }

}
