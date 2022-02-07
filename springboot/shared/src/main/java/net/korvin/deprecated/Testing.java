package net.korvin.deprecated;

import net.korvin.utils.TagStack.TagStack;

import java.util.*;

public class Testing {

    public static void main(String[] args) {
        String[] arr = new String[]{
                "FictionBook/description/title-info",
                "FictionBook/description/src-title-info",
                "FictionBook/description/publish-info",
                "FictionBook/description/custom-info",
                "FictionBook/descriptio/custom-info",
                "FictionBook/info/custom-info",
                "FictionBook/info/custom-info/aaaa",
                "FictionBook/info/custom-info/bbbbb",
                "FictionBook/info/custom-info/aaaaa",
                "description/publish-info",
                "description/src-title-info",
                "FictionBook/description/",
                "FictionBook/description",
                "FictionBook/description/publish-info/xxx",


                "FictionBaok/description/title-info",
                "FictionBook/description/src-title-info",
                "FictionBook/publish-info",
                "description/custom-info",
                "description/custom-info/x",
                "description/custom-info/x",
                "x/description/custom-info/x",
                "y/description/custom-info/x",
                "FictionBook/description/src-title-infa",
                "FictionBook/descriptio/custom-info",
                "2FictionBook/descriptio/custom-info",

                "1",
                "1/2",
                "1/2/3",
                "0/1/2",
                "2/1",
                "1/1",
                "1/2/3",
                "2/2/3",
                "2/2/3",
                "1/2/4",
                "1/3/3",
                "2/2/3",
                "1/2/1",
                "2/3",
                "1/1/2/3",
                "0/1/2/3",
                "2/1/2/3",
                "0/0/2/3",
                "1/1/2b/3",
                "1/1b/2/3",
                "1b/1/2/3",
                "1/1/2/3b",
                "0/1/2/3",
                "0/1/2/2",
                "0/1/2/1",
                "0/1/1/3",
                "0/0/2/3",
                "3/2/1/0",
        };

        List<TagStack> xxx = new ArrayList<TagStack>();
        Set<TagStack> xxx2 = new TreeSet<TagStack>();
        for (String s : arr) {
            xxx.add(TagStack.parseTags(s));
            xxx2.add(TagStack.parseTags(s));
        }

        long t1 = System.nanoTime();
        Collections.sort(xxx, new Comparator<TagStack>() {
            static int x = 0;
            @Override
            public int compare(TagStack o1, TagStack o2) {
//                x++;
//                System.out.println(">>>"+x);
                return o1.compareTo(o2);
            }
        });
        long t2 = System.nanoTime();
        System.out.println("time: "+(t2-t1));

        for (TagStack tagStack : xxx2) {
            System.out.println(tagStack);
        }
        System.out.println("----------");
        for (TagStack tagStack : xxx) {
            System.out.println(tagStack);
        }
    }

}


