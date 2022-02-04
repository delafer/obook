package net.j7.ebook.entity.ebook;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.checkerframework.checker.units.qual.C;

import java.io.File;

@Getter
@Setter
//@Accessors(fluent = true)
public class Book {

    private String ISBN; //10 or 13
    private String title;
    private String srcTitle;
    private String subTitle;
    private String description;
    private String notes;//annotation

    private Series series;//volume

    @Getter(lazy = true)
    private final Authors authors = new Authors();

    @Getter(lazy = true)
    private final Publischer publisher = new Publischer();

    @Getter(lazy = true)
    private final Genres genres = new Genres();

    private String copyright;

    private Integer editionNo; //redakcija

    @Getter(lazy = true)
    private final Volume size = new Volume();

    private Lang lang; //publication Language
    private Lang srcLang; //sourceLang / orignLang

    @Getter(lazy = true)
    private final Creator creator = new Creator();

    @Getter(lazy = true)
    private final AgeRating ageRating = new AgeRating();

    private Format format;

    private String[] keywords;

    private File location;
    private String coverUUID;

    public Series getSeries() {
        if (null == series) series = new Series();
        return series;
    }

}
