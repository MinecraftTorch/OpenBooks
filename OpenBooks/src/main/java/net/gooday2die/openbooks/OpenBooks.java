package net.gooday2die.openbooks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.parser.ParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;


/**
 * A class that extens JavaPlugin for OpenBooks.
 */
public final class OpenBooks extends JavaPlugin {
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

        try { // try loading config.yml
            ConfigValues.bookName = config.getStringList("JoinBook");
            ConfigValues.NewUserJoinBook = config.getString("NewUserJoinBook");
            ConfigValues.UserJoinBook = config.getString("UserJoinBook");
        } catch (ParserException e) { // If anything was wrong, pop up error message
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[OpenBooks] " +
                    ChatColor.WHITE + "Could not load config.yml. Please check config.yml");
            e.printStackTrace();
        }
        try { // Try loading books.yml.
            BookReader bookReader = new BookReader(filePath.toString()); // Load books.
            ConfigValues.bookData = bookReader.getBooks(); // Save loaded books.
        } catch (FileNotFoundException e) { // If anything was wrong, pop up error message
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[OpenBooks] " +
                    ChatColor.WHITE + "Could not load books.yml. Please check books.yml");
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
        }

    getCommand("openbooks").setExecutor(new CommandHandler(this));
    getCommand("ob").setExecutor(new CommandHandler(this));

    getServer().getPluginManager().registerEvents(new BookEventHandler(), this); // register events for bypassing.
}

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[OpenBooks] " +
                ChatColor.WHITE + "Disabling OpenBooks");
    }
}
