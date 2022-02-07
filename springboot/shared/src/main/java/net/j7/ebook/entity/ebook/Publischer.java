package net.j7.ebook.entity.ebook;

import lombok.Data;

import java.util.Objects;
import static net.korvin.utils.Utils.equal;

@Data
public class Publischer {

    private String name;
    private String city;
    private String year;
    private Integer copies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Publischer cmp)) return false;
        return equal(name, cmp.name) &&
               equal(city, cmp.city) &&
               equal(year, cmp.year) &&
               equal(copies, cmp.copies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, year, copies);
    }

}
