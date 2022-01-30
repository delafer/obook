package net.j7.ebook.entity.ebook;

import lombok.Data;

import java.util.Date;
@Data
public class Creator {
    private String software;
    private String name;
    private Date date;
    private String srcUri;
    private String srcInfo;
}
