package net.gooday2die.openbooks;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * A class that is for reading books.yml.
 */
public class BookReader {
    private final YamlConfiguration bookData; // An YamlConfiguration instance for loading books.yml
    private final Map<String, Book> books = new HashMap<String, Book>(); // A hashmap for storing book data.

    /**
     * A constructor method for class BookReader that reads book.yml.
     * @param booksDirectory the books.yml directory in string.
     * @throws FileNotFoundException When books.yml was not found, this exception is thrown.
     */
    public BookReader(String booksDirectory) throws FileNotFoundException {
        this.bookData = YamlConfiguration.loadConfiguration(new FileReader(booksDirectory));
        this.read(); // call read method for reading the config
    }

    /**
     * A method that reads config values from book.yml.
     */
    public void read(){
        Set<String> bookNames = this.bookData.getKeys(false); // Get book names

        for (String name : bookNames) { // for all book names
            String title = (String) this.bookData.get(name + ".title"); // get title
            String author = (String) this.bookData.get(name + ".author"); // get author
            List<String> pages = this.bookData.getStringList(name + ".pages"); // get pages
            int bookSlot = this.bookData.getInt(name + ".bookSlot"); // get bookSlot
            boolean keepBook = this.bookData.getBoolean(name + ".keepBook"); // get keepBook

            Book book = new Book(name, keepBook, bookSlot, pages, title, author); // Generate Book instance.

            this.books.put(name, book); // store that information as hashmap
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[OpenBooks] " + ChatColor.WHITE +
                    "Successfully loaded book : " + ChatColor.GREEN + name);
        }
    }

    /**
     * A simple getter method for attribute books
     * @return returns Books.
     */
    public Map<String, Book> getBooks() {
        return this.books;
    }
}

