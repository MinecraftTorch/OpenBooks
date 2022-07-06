package net.gooday2die.openbooks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;


public final class OpenBooks extends JavaPlugin {    @Override
    public void onEnable() {
        Path filePath = Paths.get(this.getDataFolder().getAbsolutePath(), "books.yml");

        // Plugin startup logic
        saveDefaultConfig(); // save default config for future

        if (!new File(filePath.toString()).exists()) // if books.yml does not exist, generate one
            saveResource("books.yml", false);

        FileConfiguration config = this.getConfig(); // Load config
        ConfigValues.bookName = config.getStringList("JoinBook");
        ConfigValues.NewUserJoinBook = config.getString("NewUserJoinBook");
        ConfigValues.UserJoinBook = config.getString("UserJoinBook");

    try {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[OpenBooks] " + ChatColor.WHITE + "Loading books.yml.");
        Bukkit.getLogger().info(filePath.toString());
        BookReader bookReader = new BookReader(filePath.toString());
    } catch (FileNotFoundException e) { // Meaning that Book.yml could not be loaded.
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[OpenBooks] " + ChatColor.WHITE + "Could not load books.yml. Please check its contents.");
        this.getPluginLoader().disablePlugin(this); // disable plugin since books.yml could not be loaded.
    }
}

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
