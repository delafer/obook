package net.korvin.entities.parsers;

import javax.xml.stream.XMLStreamReader;
import java.util.HashMap;
import java.util.Map;

public final class Attr {

    Map<String, String> map;

    public Attr(XMLStreamReader reader) {
        int j = reader.getAttributeCount();
        if (j > 0) {
            map = new HashMap<>(j, 1f);
            for (int i = 0; i < j; i++) {
                map.put(reader.getAttributeLocalName(i), reader.getAttributeValue(i));
            }
        }
    }

    public String get(String name) {
      return map != null ? map.get(name) : null;
    }

}
