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

        Fb2Book model = new Fb2Book();
//        var current = Tag.of("",model.getModel());
        //current.init();
        var book = new Book();
        TagParser parser = null;
        //XmlTag current = new XmlTag("", model.getModel());
        XmlTag current = model.getModel();

        Map<TagStack, XmlTag> map = new HashMap<>();
        int level = 0;
        boolean eod = false;
        while (!eod && streamReader.hasNext()) {
            int eventType = streamReader.next();
//            System.out.println(eventType+"current: "+current.path()+ " ["+tagStack+"]");
            switch (eventType) {
                case START_ELEMENT -> {

                    String tag = tagName(streamReader);
                    tagStack.push(tag);
                    if (null != current) {
                        current = current.getTag(tag);
                        if (null !=current) {
                            map.put(tagStack.copy(), current);
                            //System.out.println("ADD: "+(current!=null)+"->"+tagStack.chunk+"  ("+tag+")");
                            parser = current.getParser(tag, book);
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
                        tagStack.pop(tag); //tagStack.pop()
                        //parser = null;
                        current = map.get(tagStack);
                        //System.out.println("GET: "+tagStack.chunk+"->"+(current!=null)+"  ("+tag+")");
                        if (null != current) {
                            parser = current.getParser(tagStack.peek(), book);
                        }
                    }
                }
                case START_DOCUMENT -> {}
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
