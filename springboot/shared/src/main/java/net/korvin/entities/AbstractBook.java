package net.korvin.entities;

import net.j7.ebook.entity.ebook.Book;
import net.korvin.api.StaxModel;

public abstract class AbstractBook implements StaxModel {

    public static final String CALL_RELEASE_BOOK_FIRST = "You can't register new book, before releasing old one. Call releaseBook() first.";

//    private ConcurrentHashMap<String, TagParser> map = new ConcurrentHashMap<>();
//
//    public <E extends TagParser>E get(String key, Function<String, E> mappingFunction) {
//        //map.clear();
//        //return (E) map.computeIfAbsent(key, mappingFunction);
//        return mappingFunction.apply(key);
//    }

    protected BookCage holder;

    public AbstractBook() {
        holder = new BookCage();
    }

    public Book newBook() {
        Book book = new Book();
        if (null != holder.getBook()) throw new IllegalStateException(CALL_RELEASE_BOOK_FIRST);
        holder.setBook(book);
        return book;
    }

    public Book releaseBook() {
        Book book = holder.getBook();
        holder.setBook(null);
        return book;
    }

    public Book getBook() {
        return holder.getBook();
    }
}
