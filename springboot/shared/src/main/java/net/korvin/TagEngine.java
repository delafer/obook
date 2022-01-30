package net.korvin;

import com.fasterxml.aalto.stax.InputFactoryImpl;
import org.codehaus.stax2.XMLInputFactory2;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.TransformerException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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

    public void process(InputStream inputStream) throws FileNotFoundException,
            XMLStreamException, TransformerException {
        XMLInputFactory factory = getFactory();
        factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES,Boolean.FALSE);
        XMLStreamReader streamReader = factory.createXMLStreamReader(inputStream);
        Stack<String> tagStack = new Stack<String>();
        boolean eod = false;
        while (!eod && streamReader.hasNext()) {
            int eventType = streamReader.next();

            switch (eventType) {
                case XMLStreamConstants.START_ELEMENT:
//                    String x = streamReader.getName().getLocalPart();
//                    if ("first-name".equalsIgnoreCase(x) || "last-name".equalsIgnoreCase(x)) {
//                        System.out.println(streamReader.getElementText());
//                    }
                    //System.out.println("START_ELEMENT");
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if ("FictionBook".equals(streamReader.getName().getLocalPart())) {
                        eod = true;
                    }
                    //System.out.println("END_ELEMENT");
                    break;
                case XMLStreamConstants.START_DOCUMENT:
                    System.out.println("START_DOCUMENT");
                    break;
                case XMLStreamConstants.END_DOCUMENT:
                    eod = true;
                    //System.out.println("END_DOCUMENT");
                    break;
                case XMLStreamConstants.ATTRIBUTE:
                    System.out.println("ATTRIBUTE");
                    break;
                case XMLStreamConstants.CDATA:
                    //System.out.println("CDATA");
                    //System.out.println("text: "+streamReader.getText());
                    //System.out.println("from "+streamReader.getTextStart()+" len "+streamReader.getTextLength()+" chars: "+streamReader.getTextCharacters().length);
                    break;
                case XMLStreamConstants.CHARACTERS:
                    //System.out.println("CHARACTERS");
                    //System.out.println("CH: ["+streamReader.getText()+"]");
                    break;
                case XMLStreamConstants.COMMENT:
                    System.out.println("COMMENT");
                    break;
                case XMLStreamConstants.DTD:
                    System.out.println("DTD");
                    break;
                case XMLStreamConstants.SPACE:
                    System.out.println("SPACE");
                    break;
                case XMLStreamConstants.NAMESPACE:
                    System.out.println("NAMESPACE");
                    break;
                case XMLStreamConstants.PROCESSING_INSTRUCTION:
                    System.out.println("PROCESSING_INSTRUCTION");
                    break;
                case XMLStreamConstants.ENTITY_DECLARATION:
                    System.out.println("ENTITY_DECLARATION");
                    break;
                case XMLStreamConstants.ENTITY_REFERENCE:
                    System.out.println("ENTITY_REFERENCE");
                    break;
                case XMLStreamConstants.NOTATION_DECLARATION:
                    System.out.println("NOTATION_DECLARATION");
                    break;
            }

//            if(eventType == XMLStreamConstants.START_ELEMENT) {
//
//                System.out.println("START: "+streamReader.getName().toString());
//                System.out.println("START2: "+streamReader.getName().getPrefix());
//
//                tagStack.push(streamReader.getName().toString());
//
//                TagProcessor t = processorMap.get(tagStack);
//
//                if(t != null) {
//                    t.process(streamReader);
//                    tagStack.pop();
//                }
//            } else if(eventType == XMLStreamConstants.END_ELEMENT) {
//                tagStack.pop();
//            }
        }
    }

}