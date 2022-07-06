package net.gooday2die.openbooks;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class that is for handling player join events for OpenBooks.
 */
public class BookEventHandler implements Listener {
    private final Map<String, Book> bookData; // A map object that is for storing books

    /**
     * A constructor method for class BookEventHandler.
     * @param bookData the Map that represents book data.
     */
    public BookEventHandler(Map<String, Book> bookData) {
        this.bookData = bookData;
    }

    /**
     * A method that handles player join events for OpenBooks.
     * @param event The PlayerJoinEvent instance.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer(); // get player
        Book curBookData;

        if (player.hasPermission("openbooks.bypass")) return; // if user has permission openbooks.bypass, don't open book.

        if (player.hasPlayedBefore()) { // if player joined before, use UserJoinBook config.
            curBookData = this.bookData.get(ConfigValues.UserJoinBook); // get curBookData
        } else { // if player is first time visiting, use NewUserJoinBook config.
            curBookData = this.bookData.get(ConfigValues.NewUserJoinBook);
        }

        if (curBookData == null) { // If book data was null, which means the book name was invalid
            // let console know
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[OpenBooks] " + ChatColor.WHITE +
                    "Could not find " + ChatColor.RED + ConfigValues.NewUserJoinBook);
        } else { // If it was valid, generate book.
            ItemStack book = this.generateBook(curBookData.pages, curBookData.title, curBookData.author, event);
            Inventory inventory = player.getInventory(); // Copy user's inventory
            if (curBookData.keepBook) { // If keepBook was set,
                inventory.setItem(curBookData.bookSlot, book); // set that book slot with the book.
                player.updateInventory(); // update player's inventory
            }
            player.openBook(book); // Open book for the user by force.
        }
    }

    /**
     * A method that generates Book ItemStack
     * @param content The List<String> instance that represents the content of the book for pages.
     * @param title The string that represents title of the book.
     * @param author The string that represents author of the book.
     * @param event The event that player joined (this is for PAPI)
     * @return returns ItemStack instance that represents Book item.
     */
    private ItemStack generateBook(List<String> content, String title, String author, PlayerJoinEvent event) {
        ItemStack writtenBook = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) writtenBook.getItemMeta();

        List<String> pages = new ArrayList<>(); // generate a new pages

        for (String page : content) {
            String pageString = page;
            if (ConfigValues.PlaceHolderAPI) { // If placeholderAPI is here, try it.
                pageString = PlaceholderAPI.setPlaceholders(event.getPlayer(), page);
            }
            pages.add(pageString);
        }

        assert bookMeta != null;

        bookMeta.setTitle(title);
        bookMeta.setAuthor(author);
        bookMeta.setPages(pages);
        writtenBook.setItemMeta(bookMeta);

        return writtenBook;
    }
}
