package net.j7.ebook.entity.ebook;

import lombok.Data;

import java.util.Objects;
import static net.korvin.utils.Utils.equal;
/**
 * The type Age/Content rating.
 */
@Data
public class AgeRating {
    private String id;
    private Integer from;
    private Integer to;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgeRating cmp)) return false;
        return equal(id, cmp.id) &&
               equal(from, cmp.from) &&
               equal(to, cmp.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to);
    }
}
