package net.j7.ebook.entity.ebook;

import lombok.Data;

import java.util.Set;
import java.util.TreeSet;

@Data
public class Genres {

    Set<String> genres;

    @Override
    public String toString() {
        return "genres=" + (genres != null ? genres : "");
    }

    public void addGenre(String genre) {
        if (null == genres) genres = new TreeSet<String>();
        genres.add(genre);
    }

}
