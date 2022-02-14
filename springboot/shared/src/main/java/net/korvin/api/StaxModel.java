package net.korvin.api;

import net.j7.ebook.entity.ebook.Book;
import net.korvin.entities.XmlTag;

public interface StaxModel {
    public String rootTag();
    public XmlTag getModel();
    public Book getBook();

}
