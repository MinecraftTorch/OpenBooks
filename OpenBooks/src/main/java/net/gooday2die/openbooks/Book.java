package net.gooday2die.openbooks;

import java.util.List;


/**
 * A class that stores information about a single book.
 */
public class Book {
    public String name;
    public boolean keepBook;
    public int bookSlot;
    public List<String> pages;
    public String title;
    public String author;

    /**
     * A constructor method for class Book.
     * @param name the name of this book.
     * @param keepBook boolean that determines whether to keep book or not.
     * @param bookSlot integer that determines which slot shall the book be stored.
     */
    Book(String name, boolean keepBook, int bookSlot, List<String> pages, String title, String author) {
        this.name = name;
        this.keepBook = keepBook;
        this.bookSlot = bookSlot;
        this.pages = pages;
        this.title = title;
        this.author = author;
    }
}
