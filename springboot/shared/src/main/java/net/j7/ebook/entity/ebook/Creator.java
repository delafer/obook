package net.j7.ebook.entity.ebook;

import lombok.Data;

import java.util.Date;
import java.util.Objects;

import static net.korvin.utils.Utils.equal;
@Data
public class Creator {
    private String software;
    private String name;
    private Date date;
    private String srcUri;
    private String srcInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Creator cmp)) return false;
        return equal(software, cmp.software) &&
               equal(name, cmp.name) &&
               equal(srcUri, cmp.srcUri) &&
               equal(srcInfo, cmp.srcInfo) &&
               equal(date, cmp.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(software, name, srcUri, srcInfo, date);
    }
}
