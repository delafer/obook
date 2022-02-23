package net.j7.myparser;


import net.j7.myparser.xmlstruct.Tag;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;

public class ReadXml {


    void readXml(String name) {
        try (FileInputStream is = new FileInputStream(name)) {
           readXml(is);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void readXml(InputStream is) throws IOException {
        System.out.println("===================");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        is,
                        Charset.forName("UTF-8")));
        int c;
        StringBuilder sb = new StringBuilder();

        Tag tag = new Tag(null);

        while((c = reader.read()) > 0) {
            char ch = (char) c;

            if (ch=='<') {
                flushText(sb, tag);
                handleTag(reader, tag);
            } else {
                sb.append(ch);
            }
        }
        flushText(sb, tag);
        reader.close();
    }

    private void flushText(StringBuilder sb, Tag tag) {
        if (sb.length()>0) tag.addText(sb.toString());
    }

    private void handleTag(BufferedReader reader, Tag tag) throws IOException {
        char ch = (char) reader.read();
        if (ch == '!')
            handleMarkupOrCDATAOrComment(reader);
        else
        if (ch == '?')
            handleProcessingInstruction(reader);
        else
        if (ch == '/')
            handleEndTag(reader);
        else 
            handleStartTag(reader);
    }

    private void handleStartTag(BufferedReader reader) {
    }

    private void handleEndTag(BufferedReader reader) {
    }

    private void handleProcessingInstruction(BufferedReader reader) {
    }

    private void handleMarkupOrCDATAOrComment(BufferedReader reader) throws IOException {
        //<!ENTITY - markup
        //<!-- comment
        //<![CDATA[ -- CDATA 
        char ch = (char) reader.read();
        if (ch == '-') 
            handleComment(reader);
        else
        if (ch == '[') 
            handleCDATA(reader);
        else 
            handleMarkup(reader);

    }

    private void handleMarkup(BufferedReader reader) {
    }

    private void handleCDATA(BufferedReader reader) throws IOException {
        char[] cbuf = new char[6];
        reader.read(cbuf);

        if (!Arrays.equals("CDATA[".toCharArray(), cbuf)) skipUnknownBlock(reader);
    }

    private void handleComment(BufferedReader reader) throws IOException {
        char ch = (char) reader.read();
        if (ch != '-') skipUnknownBlock(reader);
    }

    private void skipUnknownBlock(BufferedReader reader) {
    }

    private void hanleComment(BufferedReader reader) {
    }

    private void handleCommentStart(StringBuilder sb) {
    }

    private boolean startsWith(StringBuilder sb, String s) {
        return sb.length() >= s.length() && sb.indexOf(s) == 0;
    }

    public static void main(String[] args) {
        ReadXml reader = new ReadXml();
        reader.readXml("C:\\work\\github\\xmls\\Test1r.xml");
    }

}
