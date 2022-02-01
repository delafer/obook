package net.j7.ebook.entity.ebook;

import lombok.Data;

@Data
public class Author {

    private String forename; //firstName
    private String surname; //lastName
    private String midname; //middleName
    private String penname; //nickName
    private String srcName;
    private String email;
    private String uri;
    private String illustrator;

}
