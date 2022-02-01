package net.j7.ebook.entity.ebook;

import lombok.Data;

@Data
public class Series {
    private String title;
    private String srcTitle;
    private Integer index;
    private Integer total;
    private String ISSN;
}
