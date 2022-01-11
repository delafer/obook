package net.korvin.sax;

import net.j7.commons.logger.LoggerJUL;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * The Class Fb2Parser.
 * simple SAX class to parse Fiction Book Formats
 * <p>
 * Supported Fiction books versions 1.0 - 2.1
 * Both: uncompressed data and compressed ZIP container  supported
 *
 * @author Alexander Tavrovsky
 */
public class Fb2Parser {

    private final static LoggerJUL.Logger log = LoggerJUL.getLogger(Fb2Parser.class, "1");

    private static final String METHOD_FICTION_BOOK_PARSER = "FictionBook parser";

    private static final String ERROR_PARSING_FB_ZIP_CONTAINER = "error parsing fb.zip container";
    private static final String ERROR_PARSING_FICTION_BOOK_XML = "error parsing fiction book xml";
    private static final String UNKNOWN_NOT_A_FICTION_BOOK_FILE = "Unknown / not a fiction book file";


    /**
     * http://www.ibm.com/developerworks/library/x-tipsaxstop/
     * Stop a SAX parser when you have enough data
     */
    private static final int MAX_SIZE = Integer.MAX_VALUE - 1000;

    enum MetaInfo {Title, Source, Publisher, Additional}

    //interesting Tags Names
    enum Tag {

        Body("body"),//contains main text
        TitleInfo("FictionBook/description/title-info", MetaInfo.Title), //meta-information (translated)
        SrcTitleInfo("FictionBook/description/src-title-info", MetaInfo.Source), //meta-information (original)
        PublishInfo("FictionBook/description/publish-info", MetaInfo.Publisher), //publisher info (e.G. ISBN Id.)
        CustomInfo("FictionBook/description/custom-info", MetaInfo.Additional), //custom information about book
        Binary("binary"), // base64 encoded data. contains images (covers). ignored
        Image("image"), // reference/link to an image. should be ignored
        Link("a"), // link to resources. should be ignored
        Table("table"); // contains table. should be ignored


        final String name;
        final MetaInfo meta;

        Tag(String arg1) {
            this(arg1, null);
        }

        Tag(String arg1, MetaInfo metaTag) {
            name = arg1;
            meta = metaTag;
        }
    }

    static SAXParser parser;
    static Map<String, Tag> tags;

    static {
        //register xml tags
        tags = new HashMap<String, Tag>(10, 1f);
        for (Tag next : Tag.values())
            tags.put(next.name, next);

        //register SAX2 parser factory
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);
        try {
            // Creates a SAXParser and its thread safe so best to initialize it once to save creation cost at the time of call
            parser = factory.newSAXParser();
        } catch (Exception e) {
            log.error("Failed to create SAXParser", e);
        }
    }


    /**
     * Default (empty) constructor
     */
    public Fb2Parser() {
        log.info("entering. method {}", METHOD_FICTION_BOOK_PARSER);
    }

    /**
     * Checks if it is an irrelevant (ignored) block.
     * If (true) such block is ignored
     * If (false) text information is extracted
     *
     * @param tag        the tag
     * @param attributes the attributes
     * @return true, if is irrelevant block
     */
    private static boolean isIrrelevantBlock(Tag tag, Attributes attributes) {
        switch (tag) {

            case Body:
                String val = attributes.getValue("name");
                return "notes".equals(val);

            case Binary:
            case Link:
            case Image:
            case Table:
                return true;

            default:
                return false;
        }

    }

    /**
     * a default method to be called for text extraction
     *
     * @param file to be parsed
     * @return extracted text
     */
    public String extractMetadataAndContent(File file) throws IOException {

        if (null == file) return null;

        String name = file.getName().toLowerCase();
        StringBuilder text = new StringBuilder(1 << 16);

        if (name.endsWith(".fb2")) {
            parseXMLFile(file, text);
        } else if (name.endsWith(".fb2.zip") ||
                name.endsWith(".fb3")) {
            parserZipContainer(file, text);
        } else {
            IOException e = new IOException(UNKNOWN_NOT_A_FICTION_BOOK_FILE);
            log.warn("Not a fiction book file format", e);
            throw e;
        }

        return text.toString();
    }

    private void parseXMLFile(File file, StringBuilder text) throws IOException {
        try {
            parseRawXML(new FileInputStream(file), text);
        } catch (Exception e) {

            IOException e1 = new IOException(ERROR_PARSING_FICTION_BOOK_XML, e);
            log.error("Error parsing fiction book file format", e);
            throw e1;

        }
    }

    private void parserZipContainer(File file, StringBuilder text) throws IOException {
        try {
            ZipFile zipFile = new ZipFile(file);
            String name;
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.isDirectory() || entry.getSize() == 0l) continue;
                name = entry.getName().toLowerCase();
                if (name.endsWith(".fb2") || name.endsWith(".xml")) {
                    parseRawXML(zipFile.getInputStream(entry), text);
                }
            }
        } catch (Exception e) {
            IOException e1 = new IOException(ERROR_PARSING_FB_ZIP_CONTAINER, e);
            log.error("Error parsing fiction book zip container", e);
            throw e1;
        }
    }

    private void parseRawXML(InputStream is, StringBuilder text) throws SAXException, IOException {
        if (null != is)
            try {
                XMLReader reader = parser.getXMLReader();
                reader.setContentHandler(new Fb2Handler(text));
                reader.parse(new InputSource(is));
            } catch (LimitReachedException e) {
                //simply ignore it
                log.warn("File size limit reached: {}", e, MAX_SIZE);
            } finally {
                is.close();
            }
    }

    /**
     * SAX2 event handler for parsing FictionBook files
     * http://fictionbook.org/index.php/Eng:FictionBook_description
     */
    private static class Fb2Handler extends DefaultHandler {

        // As we read any XML element we will push that in this stack
        Stack<Node> elementStack;

        StringBuilder cData;


        public Fb2Handler(StringBuilder text) {
            elementStack = new Stack<Node>();
            cData = text;
        }

        /**
         * This will be called when the tags of the XML starts.
         **/
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            Node parent = currentElement();
            elementStack.push(new Node(qName, parent, attributes));
        }

        /**
         * This will be called when the tags of the XML end.
         **/
        public void endElement(String uri, String localName, String qName) throws SAXException {
            elementStack.pop(); // Remove last added element
        }

        /**
         * Utility method for getting the current element in processing
         */
        private Node currentElement() {
            return elementStack.empty() ? null : elementStack.peek();
        }

        /**
         * This is called to get the tags value
         **/
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (0 == length) return;
            Node current = currentElement();
            if (null != current)
                if (!current.isSkipped) {

                    String toAdd = new String(ch, start, length).trim();

                    if (toAdd.length() == 0) return;

                    if (null != current.metaInfo) {
                        this.cData.append(current.metaInfo)
                                .append('=').append(toAdd.trim())
                                .append('\n');
                    } else {
                        this.cData.append(' ').append(toAdd);
                    }

                    if (this.cData.length() > MAX_SIZE) {
                        this.cData.setLength(MAX_SIZE);
                        throw LimitReachedException.INSTANCE;
                    }
                }
        }
    }

    private static class Node {

        String qName;
        String path;
        Node parent;
        boolean isSkipped;
        String metaInfo;

        Node(String name, Node parent, Attributes attrs) {
            this.qName = name;
            this.parent = parent;
            this.path = parent == null ? name : parent.path + '/' + name;
            this.checkIrrelevantBlock(attrs);
            if (parent != null && parent.metaInfo != null) {
                metaInfo = parent.metaInfo + '.' + name;
            }
        }

        private void checkIrrelevantBlock(Attributes attrs) {
            Tag tag = Fb2Parser.tags.get(path);

            if (null == tag && null != parent)
                tag = Fb2Parser.tags.get(qName);

            if (null != tag) {
                isSkipped = Fb2Parser.isIrrelevantBlock(tag, attrs);
                metaInfo = tag.meta != null ? tag.meta.name().toLowerCase() : null;
            } else
                isSkipped = parent == null || parent.isSkipped;
        }

    }


    private static class LimitReachedException extends SAXException {

        @Serial
        private static final long serialVersionUID = 1L;

        final static SAXException INSTANCE = new LimitReachedException();
    }

}
