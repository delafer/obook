package net.korvin.transcoder;

import com.fasterxml.aalto.stax.InputFactoryImpl;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLOutputFactory2;
import org.codehaus.stax2.XMLStreamWriter2;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.OutputStream;
import java.util.function.Supplier;

public class BaseStaxWriter {

    protected Supplier<XMLInputFactory2> factory = () -> {
        XMLInputFactory2 val = getFactory();
        factory = () -> val;
        return val;
    };

    protected XMLInputFactory2 getFactory()
    {
        XMLInputFactory2 f = (XMLInputFactory2) InputFactoryImpl.newInstance();
        f.setProperty(XMLInputFactory2.P_LAZY_PARSING, Boolean.TRUE);
        f.configureForXmlConformance();
        f.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.TRUE);//FALSE
        f.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.TRUE);//FALSE
        f.setProperty(XMLInputFactory.IS_VALIDATING, Boolean.FALSE);
        f.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.FALSE);
        f.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.TRUE);//FALSE

        f.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);
        f.setProperty(XMLInputFactory2.P_PRESERVE_LOCATION, Boolean.TRUE);//FALSE
        f.setProperty(XMLInputFactory2.P_REPORT_PROLOG_WHITESPACE, Boolean.TRUE);//FALSE
        f.configureForRoundTripping();

        return (XMLInputFactory2) f;
    }
    protected String tagName(XMLStreamReader streamReader) {
        return streamReader.getName().getLocalPart();
    }


    protected XMLOutputFactory2 getOutputFactory()
    {
        XMLOutputFactory f =  XMLOutputFactory.newInstance();
        f.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, Boolean.TRUE);
        f.setProperty(XMLOutputFactory2.P_AUTOMATIC_EMPTY_ELEMENTS, Boolean.FALSE);
        f.setProperty(XMLOutputFactory2.P_AUTO_CLOSE_OUTPUT, Boolean.TRUE);
        f.setProperty(XMLOutputFactory2.P_TEXT_ESCAPER, null);
        f.setProperty(XMLOutputFactory2.P_ATTR_VALUE_ESCAPER, null);
        return (XMLOutputFactory2) f;
    }

    protected XMLStreamWriter2 getOutputWriter(OutputStream os) throws XMLStreamException {
        XMLOutputFactory2 fact = getOutputFactory();
        return (XMLStreamWriter2) fact.createXMLStreamWriter(os, "UTF-8");

    }

}
