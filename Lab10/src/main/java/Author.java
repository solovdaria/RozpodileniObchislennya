package main.java;

import java.util.ArrayList;

public class Author {
    private String code;
    private String name;
    private ArrayList<Book> books;

    public Author()
    {
        books = new ArrayList<>();
    };
    public Author(String code, String name)
    {
        this.code=code;
        this.name=name;
    }

    public ArrayList<Book> getBook() {
        return books;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void removeBook(Book book){
        this.books.removeIf(value -> value.getName().equals(book.getName()));
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
