package net.j7.ebook.entity.ebook;

import lombok.Data;

import java.io.File;

@Data
public class Book {

    private String ISBN; //10 or 13
    private String title;
    private String srcTitle;
    private String subTitle;
    private String description;
    private String notes;//annotation

    private Series series;//volume

    private Authors authors;
    private Publischer publisher;
    private Genres genres;

    private String copyright;

    private Integer editionNo; //redakcija
    private Volume size;

    private Lang lang; //publication Language
    private Lang srcLang; //sourceLang / orignLang

    private Creator creator;
    private AgeRating ageRating;
    private Format format;

    private String[] keywords;

    private File location;
    private String coverUUID;
}
