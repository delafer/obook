package net.j7.ebook.entity.ebook;

public class Authors {
    private Author[] authors;
    
    public Author addAuthor(Author author) {
        if (null == authors) {
            authors = new Author[] {author};
        } else {
            authors = java.util.Arrays.copyOf(authors, authors.length+1);
            authors[authors.length-1] = author;
        }
        return author;
    }

    public Author newAuthor() {
        return this.addAuthor(new Author());
    }

    public Author lastAuthor() {
        if (authors.length == 0) {
            addAuthor(new Author());
        }
        return authors[authors.length-1];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (null != authors)
        for (Author author : authors) {
            //if (sb.length() > 0) {}
            sb.append("\r\n");
            sb.append(author);
        }
        sb.insert(0, "Authors{");
        sb.append('}');
        return sb.toString();
    }
}
