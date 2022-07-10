package net.gooday2die.openbooks;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.parser.ParserException;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


/**
 * A class that handles commands for openbooks
 */
public class CommandHandler implements CommandExecutor {
    JavaPlugin plugin;
    Map<String, Integer> commandList = new HashMap<>(); // a hashmap that stores commands and their arg counts.
    /**
     * A constructor method for this class
     * @param argPlugin the plugin instance object
     */
    public CommandHandler(JavaPlugin argPlugin) {
        this.plugin = argPlugin;
        this.commandList.put("reload", 0);
        this.commandList.put("open", 2);
        this.commandList.put("help", 0);
    }

    /**
     * An overriding for onCommand
     * @param sender the sender
     * @param command the command
     * @param label the label
     * @param args the args
     * @return always true
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        this.parseCommand(sender, args);
        return true;
    }

    /**
     * A method for class CommandHandler that parses command
     * @param sender the sender
     * @param args the args
     */
    private void parseCommand(CommandSender sender, String[] args) {
        if (args.length == 0) { // if user issued bare /openbooks or /ob
           sender.sendMessage(ChatColor.RED + "[OpenBooks] " + ChatColor.WHITE +
                    "Please use " + ChatColor.GREEN + "/openbooks help" + ChatColor.WHITE + " for more help.");
        } else { // when user issued command that was at least not blank
            try { // try getting the argument information
                int argCount = this.commandList.get(args[0]); // get argument count;
                if (argCount == args.length - 1) { // if argument was valid, then execute command
                    if (Objects.equals(args[0], "reload")) { // if label was reload
                        this.reloadBooks(sender);  // execute reload
                    } else if (Objects.equals(args[0], "open")) {
                        this.openBookTo(sender, args);
                    } else if (Objects.equals(args[0], "help")) {
                        this.help(sender);
                    }
                } else { // if argument count was invalid, do not execute it.
                    sender.sendMessage(ChatColor.RED + "[OpenBooks] " + ChatColor.GREEN + args[0] + ChatColor.GREEN +
                            " takes " + ChatColor.WHITE + argCount + " arguments. Use " + ChatColor.GREEN +
                            "/openbooks help" + ChatColor.WHITE + " for more information");
                }
            } catch (NullPointerException e) {
                sender.sendMessage(ChatColor.RED + "[OpenBooks] " + ChatColor.WHITE +
                        " please use " + ChatColor.GREEN + "/openbooks help" + ChatColor.WHITE + " for more help.");
            }
        }
    }

    /**
     * A method that reloads config.yml and books.yml.
     * This method corresponds to /openbooks reload
     * @param sender the sender
     */
    private void reloadBooks(CommandSender sender) {
        if (sender.hasPermission("openbooks.reload")) {
            // get absolute path for books.yml
            Path filePath = Paths.get(this.plugin.getDataFolder().getAbsolutePath(), "books.yml");
            this.plugin.reloadConfig(); // reload config
            FileConfiguration config = this.plugin.getConfig(); // Load config

            try { // try loading config.yml
                ConfigValues.bookName = config.getStringList("JoinBook");
                ConfigValues.NewUserJoinBook = config.getString("NewUserJoinBook");
                ConfigValues.UserJoinBook = config.getString("UserJoinBook");
            } catch (ParserException e) { // If anything was wrong, pop up error message
                sender.sendMessage(ChatColor.RED + "[OpenBooks] " +
                        ChatColor.WHITE + "Could not load config.yml. Please check config.yml");
                e.printStackTrace();
                return;
            }
            try { // Try loading books.yml.
                BookReader bookReader = new BookReader(filePath.toString()); // Load books.
                ConfigValues.bookData = bookReader.getBooks(); // Save loaded books.
            } catch (FileNotFoundException e) { // If anything was wrong, pop up error message
                sender.sendMessage(ChatColor.RED + "[OpenBooks] " +
                        ChatColor.WHITE + "Could not load books.yml. Please check books.yml");
                e.printStackTrace();
                return;
            }
            // if successful, tell them it was successful.
            sender.sendMessage(ChatColor.GOLD + "[OpenBooks] " + ChatColor.WHITE +
                    "Successfully reloaded books.yml and config.yml");
        } else {
            sender.sendMessage(ChatColor.RED + "[OpenBooks] " + ChatColor.WHITE +
                    "You do not have permission for this command");
        }
    }

    /**
     * A method that opens book to a book to player(s)
     * @param sender the sender of this command
     * @param args arguments
     */
    private void openBookTo(CommandSender sender, String[] args) {
        if (sender.hasPermission("openbooks.openbookto")) {
            String bookName = args[1];
            String targetPlayer = args[2];

            if (targetPlayer.equals("ALL")) { // if this was for all users
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!player.hasPermission("openbooks.bypass")) {
                        Book curBookData = ConfigValues.bookData.get(bookName);
                        this.openBook(curBookData, player); // generate book
                        sender.sendMessage(ChatColor.GOLD + "[OpenBooks] " + ChatColor.WHITE +
                                "Successfully opened " + ChatColor.GREEN + bookName + ChatColor.WHITE +
                                " to " + ChatColor.GREEN + targetPlayer); // let them know it was successful.
                    }
                }
            } else { // if this was for specific user
                if (ConfigValues.bookName.contains(bookName)) {
                    Player target = Bukkit.getPlayer(targetPlayer);
                    if (target == null) {
                        sender.sendMessage(ChatColor.RED + "[OpenBooks] " +
                                ChatColor.WHITE + "User " + ChatColor.GREEN +
                                targetPlayer + ChatColor.WHITE + " could not be found.");
                    } else { // if this was for specific user
                        if (target.hasPermission("openbooks.bypass")) {
                            sender.sendMessage(ChatColor.RED + "[OpenBooks] " +
                                    ChatColor.WHITE + "You cannot open books to " + ChatColor.GREEN + targetPlayer);
                        } else {
                            Book curBookData = ConfigValues.bookData.get(bookName);
                            this.openBook(curBookData, target); // generate book
                            sender.sendMessage(ChatColor.GOLD + "[OpenBooks] " + ChatColor.WHITE +
                                    "Successfully opened " + ChatColor.GREEN + bookName + ChatColor.WHITE +
                                    " to " + ChatColor.GREEN + targetPlayer); // let them know it was successful.
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "[OpenBooks] " +
                            ChatColor.WHITE + "Could not find book with name: " + ChatColor.GREEN + bookName
                            + ChatColor.WHITE + " Please check books.yml");
                }
            }
        }
    }

    /**
     * A method that generates book and opens book to a specific user
     * @param curBookData the book data to generate book from
     * @param player the player that plugin should open book to
     */
    private void openBook(Book curBookData, Player player) {
        // Generate book
        List<String> content = curBookData.pages;
        String title = curBookData.title;
        String author = curBookData.author;

        ItemStack writtenBook = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) writtenBook.getItemMeta();

        List<String> pages = new ArrayList<>(); // generate a new pages

        for (String page : content) {
            String pageString = page;
            if (ConfigValues.PlaceHolderAPI) { // If placeholderAPI is here, try it.
                pageString = PlaceholderAPI.setPlaceholders(player, page);
            }
            pages.add(pageString);
        }

        assert bookMeta != null;

        bookMeta.setTitle(title);
        bookMeta.setAuthor(author);
        bookMeta.setPages(pages);
        writtenBook.setItemMeta(bookMeta);

        // Get user's inventory
        Inventory inventory = player.getInventory(); // Copy user's inventory
        if (curBookData.keepBook) { // If keepBook was set,
            inventory.setItem(curBookData.bookSlot, writtenBook); // set that book slot with the book.
            player.updateInventory(); // update player's inventory
        }
        player.openBook(writtenBook); // Open book for the user by force.
    }

    /**
     * A method for printing out help message
     * @param sender the sender that issued command.
     */
    private void help(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "--------=[OpenBooks]=--------");
        sender.sendMessage(ChatColor.GREEN + "- /openbooks reload" + ChatColor.WHITE + " : reloads books.yml and config.yml");
        sender.sendMessage(ChatColor.GREEN + "- /openbooks open bookname username" + ChatColor.WHITE + " : open book to specific user");
    }
}
