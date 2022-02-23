package net.korvin.transcoder;

import net.korvin.utils.TagStack.TagStack;
import org.codehaus.stax2.XMLStreamReader2;
import org.codehaus.stax2.XMLStreamWriter2;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.TransformerException;
import java.io.*;

import static javax.xml.XMLConstants.DEFAULT_NS_PREFIX;
import static javax.xml.stream.XMLStreamConstants.*;

public class StaxWriter extends BaseStaxWriter {


    public void process(String fileName, String fileOnly) throws IOException, TransformerException, XMLStreamException {
        FileInputStream fis = new FileInputStream(fileName);
        FileOutputStream fos = new FileOutputStream("C:\\workspace\\spielplatz\\output\\"+fileOnly);
        process(fis, fos);
        fos.flush();
        fos.close();
        fis.close();
    }


    public void process(InputStream inputStream, OutputStream os) throws FileNotFoundException, XMLStreamException, TransformerException {
        XMLInputFactory factory = this.factory.get();
        XMLStreamReader2 reader = (XMLStreamReader2)factory.createXMLStreamReader(inputStream);

        XMLStreamWriter2 writer = this.getOutputWriter(os);

        var stackPath = new TagStack();
        boolean once = false;
        boolean eod = false;
        while (!eod && reader.hasNext()) {
            int eventType = reader.next();
            System.out.println(":::"+stackPath.toString());
            switch (eventType) {
                case START_ELEMENT -> {
                    String tag = tagName(reader);
                    stackPath.push(tag);
                   // writer.writeStartElement(reader.getName().getLocalPart());
                    if (tag.equals("out")) writer.writeStartElement("hihi");
                    handleStartElement( reader, writer );
                    //if (tag.equals("out")) writer.writeStartElement("hoho");
                }
                case END_ELEMENT -> {
                    String tag = tagName(reader);
                    stackPath.pop(tag); //tagStack.pop(tag)
                    //writer.copyEventFromReader(reader, true);
                    if (tag.equals("out")) writer.writeEndElement();
                    writer.writeEndElement();
                    if (!once) {
                        writer.writeEmptyElement("blabla");
                        once = true;
                    }

                }
                case START_DOCUMENT -> {
                    writer.writeStartDocument();

                }
                case END_DOCUMENT -> {
                    eod = true;
                    writer.writeEndDocument();
                }
                case  CHARACTERS -> {
                    writer.writeCharacters(reader.getText());
                }
                case CDATA, ENTITY_DECLARATION, ENTITY_REFERENCE -> {
                    writer.copyEventFromReader(reader, true);
                }
                default -> {
                    System.out.println(" EVENT: "+eventType);
                    writer.copyEventFromReader(reader, true);

                    //.writeComment(text);
                    //.writeEntityRef(name);
                    //.writeCharacters("");
                    //.writeRaw(data);
                    //.writeCData( inStream.getText() );
                }
            }
        }
        writer.flush();
        writer.close();
        reader.close();
        System.out.println("FINISHED: ");
        //TagProcessor t = processorMap.get(tagStack);
    }

    private static void handleStartElement( XMLStreamReader inStream, XMLStreamWriter writer )
            throws XMLStreamException {
        String prefix = inStream.getPrefix();
        String namespaceURI = inStream.getNamespaceURI();
        if ( namespaceURI == "" && ( prefix == DEFAULT_NS_PREFIX || prefix == null ) ) {
            writer.writeStartElement( inStream.getLocalName() );
        } else {
            if ( prefix != null && writer.getNamespaceContext().getPrefix( prefix ) == "" ) {
                // TODO handle special cases for prefix binding, see
                // http://download.oracle.com/docs/cd/E17409_01/javase/6/docs/api/javax/xml/namespace/NamespaceContext.html#getNamespaceURI(java.lang.String)
                writer.setPrefix( prefix, namespaceURI );
            }
            if ( prefix == null ) {
                writer.writeStartElement( "", inStream.getLocalName(), namespaceURI );
            } else {
                writer.writeStartElement( prefix, inStream.getLocalName(), namespaceURI );
            }
        }

        copyNamespaceBindings( inStream, writer );
        copyAttributes( inStream, writer );
    }

    private static void copyNamespaceBindings( XMLStreamReader inStream, XMLStreamWriter writer )
            throws XMLStreamException {
        for ( int i = 0; i < inStream.getNamespaceCount(); i++ ) {
            String nsPrefix = inStream.getNamespacePrefix( i );
            String nsURI = inStream.getNamespaceURI( i );
            if ( nsPrefix != null && nsURI != null ) {
                writer.writeNamespace( nsPrefix, nsURI );
            } else if ( nsPrefix == null ) {
                writer.writeDefaultNamespace( nsURI );
            }
        }
    }

    private static void copyAttributes( XMLStreamReader inStream, XMLStreamWriter writer )
            throws XMLStreamException {
        for ( int i = 0; i < inStream.getAttributeCount(); i++ ) {
            String localName = inStream.getAttributeLocalName( i )+"x";
            String nsPrefix = inStream.getAttributePrefix( i );
            String value = inStream.getAttributeValue( i );
            String nsURI = inStream.getAttributeNamespace( i );
            if ( nsURI == null ) {
                writer.writeAttribute( localName, value );
            } else {
                writer.writeAttribute( nsPrefix, nsURI, localName, value );
            }
        }
    }

}
