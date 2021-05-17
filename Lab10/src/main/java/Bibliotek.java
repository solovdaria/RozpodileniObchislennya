package main.java;

import java.util.ArrayList;

public class Bibliotek {
    ArrayList<Author> authors;

    Bibliotek()
    {
        this.authors=new ArrayList<Author>();
    }

    public void add(Author author) {
        authors.add(author);
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int k = 1;
        for (Author author: authors) {
            sb.append(k++ + ". " + author.toString() + "\n\n\n");
        }
        return sb.toString();
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }
}
