package net.korvin;

import com.fasterxml.aalto.stax.InputFactoryImpl;
import net.j7.ebook.entity.ebook.Book;
import net.korvin.api.StaxModel;
import net.korvin.entities.Tag;
import net.korvin.entities.TagParser;
import net.korvin.fb2.Fb2Book;
import net.korvin.utils.TagStack;
import org.codehaus.stax2.XMLInputFactory2;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

import static javax.xml.stream.XMLStreamConstants.*;

public class TagEngine {
    private final Map<List<String>, TagProcessor> processorMap = new HashMap<List<String>, TagProcessor>();


    public TagEngine add(TagProcessor processor) {
        processorMap.put(new ArrayList<String>(Arrays.asList(processor.getTagPath().split("/"))),
                processor);
        return this;
    }

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
        f.setProperty(XMLInputFactory2.IS_REPLACING_ENTITY_REFERENCES, Boolean.FALSE);
        f.setProperty(XMLInputFactory2.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);

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

    private String asString(Stack<String> stack) {
        StringBuilder sb = new StringBuilder();
        for (String next : stack) {
            if (!sb.isEmpty()) {
                sb.append('/');
            }
            sb.append(next);
        }
        return sb.toString();
    }

    public void process(InputStream inputStream) throws FileNotFoundException,
            XMLStreamException, TransformerException {
        XMLInputFactory factory = getFactory();
        factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES,Boolean.FALSE);
        XMLStreamReader streamReader = factory.createXMLStreamReader(inputStream);
        var tagStack = new TagStack();

        StaxModel model = new Fb2Book();
        var current = Tag.of("",model.getModel());
        current.init();
        var book = new Book();
        TagParser parser = null;

        boolean eod = false;
        while (!eod && streamReader.hasNext()) {
            int eventType = streamReader.next();
//            System.out.println(eventType+"current: "+current.path()+ " ["+tagStack+"]");
            switch (eventType) {
                case START_ELEMENT -> {
                    String tag = tagName(streamReader);

                    tagStack.push(tag);
                    parser = Tag.parser(tagStack.toString(),tag, book); //TODO

//                    System.out.println("start: ["+tag+"] ("+tagStack+") >>>"+parser);

                    if (null != parser) parser.process(eventType, streamReader);
                }
                case END_ELEMENT -> {
                    if (null != parser) parser.process(eventType, streamReader);
                    String tag = tagName(streamReader);
//                  Tag child = current.childTag(tag);
//                    System.out.println("end: ["+tag+"] ("+tagStack+")");
                    tagStack.pop();
                    parser = null;
                    if ("FictionBook".equals(tagName(streamReader))) {
                        eod = true;
                    }
                }
                case START_DOCUMENT -> {
                    System.out.println("START_DOCUMENT");
                }
                case END_DOCUMENT -> {
                    eod = true;
                    System.out.println("END_DOCUMENT");
                }
                case CDATA, CHARACTERS, ENTITY_DECLARATION, ENTITY_REFERENCE -> {
                    if (null != parser) parser.process(eventType, streamReader);
                }
                default -> {
                    System.out.println("UNPARSED EVENT: "+eventType);
                }
            }
        }
    }

    void test(XMLStreamReader reader){
        StAXSource stAXSource = new StAXSource(reader);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();


        OutputStream outputStream = System.out;
        try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(stAXSource, new StreamResult(outputStream));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
