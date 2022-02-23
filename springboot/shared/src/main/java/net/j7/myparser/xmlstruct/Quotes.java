package net.j7.myparser.xmlstruct;

public enum Quotes {
    DEFAULT(String.valueOf('"')),
    SINGLE("'"),
    NONE(""),
    NO_VALUE("")
    ;

    Quotes(String s) {
    }
}
