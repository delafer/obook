package net.korvin.entities.parsers;

import net.korvin.utils.TagStack.TagStack;

import javax.xml.stream.XMLStreamReader;

public abstract class TextTagParser extends TagParser {

    private StringBuilder buff;

    @Override
    public void start(XMLStreamReader reader, TagStack tagPath) {
        buff = new StringBuilder();
    }

    @Override
    public void end(XMLStreamReader reader, TagStack tagPath) {
        setValue(buff.toString());
    }

    public abstract void setValue(String value);

    @Override
    public void chars(XMLStreamReader reader, TagStack tagPath) {
            buff.append(reader.getText());
    }


}
