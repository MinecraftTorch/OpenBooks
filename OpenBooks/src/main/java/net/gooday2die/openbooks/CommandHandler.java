package net.gooday2die.openbooks;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
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
    public CommandHandler(JavaPlugin argPlugin){
        this.plugin = argPlugin;
        this.commandList.put("reload", 0); // set two commands for openbooks
        this.commandList.put("open", 2);
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
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.parseCommand(sender, label, args);
        return true;
    }

    /**
     * A method for class CommandHandler that parses command
     * @param sender the sender
     * @param label the command label
     * @param args the args
     */
    private void parseCommand(CommandSender sender, String label, String[] args) {
        if (args.length == 0) { // if user issued bare /openbooks or /ob
           sender.sendMessage(ChatColor.RED + "[OpenBooks] " + ChatColor.WHITE +
                    " please use " + ChatColor.GREEN + "/openbooks help" + ChatColor.WHITE + " for more help.");
        } else { // when user issued command that was at least not blank
            try { // try getting the argument information
                int argCount = this.commandList.get(args[0]); // get argument count;
                if (argCount == args.length - 1) { // if argument was valid, then execute command
                    if (Objects.equals(args[0], "reload")) { // if label was reload
                        this.reloadBooks(sender);  // execute reload
                    }
                } else { // if argument count was invalid, do not execute it.
                    sender.sendMessage(ChatColor.RED + "[OpenBooks] " + ChatColor.GREEN + label + ChatColor.GREEN + " takes" +
                            ChatColor.GREEN + argCount + ChatColor.WHITE + " arguments. Use /help for more information");
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
    }
}
