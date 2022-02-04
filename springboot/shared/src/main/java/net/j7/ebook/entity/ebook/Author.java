package net.j7.ebook.entity.ebook;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import static net.korvin.utils.Utils.equal;
@Getter
@Setter
public class Author {

    private String forename; //firstName
    private String surname; //lastName
    private String midname; //middleName
    private String penname; //nickName
    private String srcName;
    private String email;
    private String uri;
    private String illustrator;

    public Author(String forename, String surname) {
        this.forename = forename;
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author cmp)) return false;
        return equal(forename, cmp.forename) &&
               equal(surname, cmp.surname) &&
               equal(midname, cmp.midname) &&
               equal(penname, cmp.penname) &&
               equal(srcName, cmp.srcName) &&
               equal(email, cmp.email) &&
               equal(uri, cmp.uri) &&
               equal(illustrator, cmp.illustrator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(forename, surname, midname, penname, srcName, email, uri, illustrator);
    }

}
