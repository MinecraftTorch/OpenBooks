package net.gooday2die.openbooks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class BookReader {
    YamlConfiguration bookData;
    Map<String, ItemStack> books;
    public BookReader(String booksDirectory) throws FileNotFoundException {
        this.bookData = YamlConfiguration.loadConfiguration(new FileReader(booksDirectory));
        this.read();
    }
    public void read() throws CouldNotLoadBooks{
        Set<String> bookNames = this.bookData.getKeys(false);

        System.out.println(bookNames);

        for (String name : bookNames) {
            String title = (String) this.bookData.get(name + ".title");
            String author = (String) this.bookData.get(name + ".author");
            int bookSlot = 0;
            boolean keepBook = false;
            
            try { // When it cannot parse boolean or integer, Exception will be thrown
                keepBook = (boolean) this.bookData.get(name + ".keepBook");
                bookSlot = (int) this.bookData.get(name + ".title");
            } catch (NullPointerException | java.lang.ClassCastException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[OpenBooks] " + ChatColor.WHITE + "Failed to load " + ChatColor.RED + name);
            }
        }
    }
    private ItemStack generateBook(List<String> content, String bookName, String title, String author) {
        ItemStack writtenBook = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) writtenBook.getItemMeta();
        assert bookMeta != null;

        bookMeta.setTitle(title);
        bookMeta.setAuthor(author);
        bookMeta.setPages(content);
        writtenBook.setItemMeta(bookMeta);

        return writtenBook;
    }

    public static class CouldNotLoadBooks extends RuntimeException {
        List<String> errorBooks;
        public CouldNotLoadBooks(List<String> errorBooks) {
            this.errorBooks = errorBooks;
        }
    }
}

