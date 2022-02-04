package net.j7.ebook.entity.ebook;

public class Authors {
    private Author[] authors;
    
    public void addAuthor(Author author) {
        if (null == authors) {
            authors = new Author[] {author};
        } else {
            authors = java.util.Arrays.copyOf(authors, authors.length+1);
            authors[authors.length-1] = author;
        }
    }
}
