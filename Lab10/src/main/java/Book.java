package main.java;

public class Book {
    private int code;
    private String name;
    private String pages;
    private Author author;

    public Book() {};
    public Book(int code, String name, String pages, Author author)
    {
        this.code=code;
        this.name=name;
        this.pages=pages;
        this.author=author;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public Author getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public String getPages() {
        return pages;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }
}
