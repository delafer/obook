package net.korvin.utils;

import java.util.Iterator;

public final class StringIterator implements Iterator<String> {

    private String str;
    private char ch;
    private int at;

    public static Iterator<String> path(String str) {
        return new StringIterator(str, '/');
    }

    public StringIterator(String str, char div) {
        this.str = str;
        this.ch = div;
    }

    public boolean hasNext() {
        return str != null && at < str.length();
    }

    public String next() {
        String ret;
        for (int j = at; j < str.length(); j++) {
            if (ch == str.charAt(j)) {
                ret = StringIterator.getPart(str, at, j);
                at = ++j;
                return ret;
            }
        }
        ret = StringIterator.getPart(str, at, str.length());
        at = str.length();
        return ret;
    }

    private static String getPart(String str, int at, int to) {
        //System.out.println("s:"+str+" ["+at+","+to+"]");
        return  (to > at) ? str.substring(at, to) : "";
    }

    public static void main(String[] args) {
        Iterator<String> r = new StringIterator(null, ',');
        while (r.hasNext()) {
            System.out.println(">>>["+r.next()+"]");
        }
        System.out.println(System.currentTimeMillis());
    }
}
