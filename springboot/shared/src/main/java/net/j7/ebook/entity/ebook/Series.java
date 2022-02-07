package net.j7.ebook.entity.ebook;

import lombok.Data;

import java.util.Objects;

import static net.korvin.utils.Utils.equal;

@Data
public class Series {
    private String title;
    private String srcTitle;
    private Integer index;
    private Integer total;
    private String ISSN;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Series cmp)) return false;
        return equal(index, cmp.index) &&
               equal(total, cmp.total) &&
               equal(title, cmp.title) &&
               equal(srcTitle, cmp.srcTitle) &&
               equal(ISSN, cmp.ISSN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, total, title, srcTitle, ISSN);
    }
}
