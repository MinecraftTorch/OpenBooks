package net.gooday2die.openbooks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;


/**
 * A class that extens JavaPlugin for OpenBooks.
 */
public final class OpenBooks extends JavaPlugin {
    private Map<String, Book> bookData; // The book data.

    /**
     * Override for onEnable();
     */
    @Override
    public void onEnable() {
        Path filePath = Paths.get(this.getDataFolder().getAbsolutePath(), "books.yml");

        // Plugin startup logic
        saveDefaultConfig(); // save default config for future

        if (!new File(filePath.toString()).exists()) // if books.yml does not exist, generate one
            saveResource("books.yml", false);

        FileConfiguration config = this.getConfig(); // Load config

        ConfigValues.PlaceHolderAPI = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;

        if (ConfigValues.PlaceHolderAPI)
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[OpenBooks] " +
                    ChatColor.WHITE + "Found PlaceHolderAPI!");

        ConfigValues.bookName = config.getStringList("JoinBook");
        ConfigValues.NewUserJoinBook = config.getString("NewUserJoinBook");
        ConfigValues.UserJoinBook = config.getString("UserJoinBook");

    try { // Try loading books.yml.
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[OpenBooks] " +
                ChatColor.WHITE + "Loading books.yml...");
        Bukkit.getLogger().info(filePath.toString());
        BookReader bookReader = new BookReader(filePath.toString()); // Load books.
        this.bookData = bookReader.getBooks(); // Save loaded books.
    } catch (FileNotFoundException e) { // Meaning that Book.yml could not be loaded.
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[OpenBooks] " +
                ChatColor.WHITE + "Could not load books.yml. Please check its contents.");
        this.getPluginLoader().disablePlugin(this); // disable plugin since books.yml could not be loaded.
    }

    getServer().getPluginManager().registerEvents(new BookEventHandler(this.bookData), this); // register events for bypassing.
}

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[OpenBooks] " +
                ChatColor.WHITE + "Disabling OpenBooks");
    }
}
