package net.j7.ebook.entity.ebook;

import lombok.Data;

import java.util.Objects;

import static net.korvin.utils.Utils.equal;

@Data
public class Volume {

    private static final int CHARS_PER_PAGE = 1943; //

    private Integer pages;
    private Integer chars;
    private Integer size;


//    public static void main(String[] args) {
//        double x = 0;
//        x += 1949d * 3;
//        x += 1944.5d * 3;
//        x+= 2083 * 2;
//        x+= 2052 * 2;
//        x+= 2009;
//        x+= 1361 * 1;
//
//        double z = x / (3+3+2+2+1+1);
//        System.out.println(z);
//
//    }

    public int getPages() {
        if (pages != null) return pages;
        if (chars != null) return Math.round(chars.floatValue() / CHARS_PER_PAGE);
        return 0;
    }

    public int getChars() {
        if (chars != null) return chars;
        if (pages != null) return Math.round(pages.floatValue() * CHARS_PER_PAGE);
        return 0;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Volume cmp)) return false;
        return equal(pages, cmp.pages) &&
                equal(chars, cmp.chars) &&
                equal(size, cmp.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pages, chars, size);
    }

}
