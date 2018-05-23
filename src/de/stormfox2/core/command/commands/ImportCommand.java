package de.stormfox2.core.command.commands;

import de.stormfox2.core.de.stormfox2.chat.Chat;
import de.stormfox2.core.de.stormfox2.chat.ChatArg;
import de.stormfox2.core.de.stormfox2.chat.MessageType;
import de.stormfox2.core.file.ConfigurationFile;
import de.stormfox2.core.language.Language;
import de.stormfox2.core.language.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ImportCommand implements CommandExecutor {
    String usage = "/import <language>";
    Boolean onlyservermode = false, deleteothers = false;
    HashMap<String, String> map;
    String val;


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        /*if(!sender.hasPermission("core.import")){
            Chat.sendMessage(sender, MessageType.ERROR, "core.error.nopermissions");
            return true;
        }*/

        if(args.length != 1){
            Chat.sendMessage(sender, MessageType.ERROR, "core.error.languageNotExist");
            return true;
        }else {
            Language lang = LanguageManager.getLanguage(args[0]);
            if(lang == null){
                System.out.println("null Lang");
                Chat.sendMessage(sender, MessageType.ERROR, "core.error.languageNotExist", new ChatArg("%c", args[0]));
                return true;
            }
            ConfigurationFile file = new ConfigurationFile("languages/" + args[0] + ".yml");

            System.out.println(file.get("config.onlyservermode"));

            for(String key : file.getKeys(false)){
                if(file.contains(key))
                    val = file.getString(key);

                System.out.println(key + " | " + val);

                if(key.equalsIgnoreCase("config.onlyservermode")){
                    if(val == "true") {
                        onlyservermode = true;
                        Chat.sendMessage(Bukkit.getConsoleSender(), MessageType.INFO, "core.lang.serveronly.on");
                    } else {
                        onlyservermode = false;
                        Chat.sendMessage(Bukkit.getConsoleSender(), MessageType.INFO, "core.lang.serveronly.off");
                    }
                } else if(key.equalsIgnoreCase("config.deleteothers")){
                    if(val == "true") {
                        deleteothers = true;
                        Chat.sendMessage(Bukkit.getConsoleSender(), MessageType.INFO, "core.lang.serveronly.on");
                    } else {
                        deleteothers = false;
                        Chat.sendMessage(Bukkit.getConsoleSender(), MessageType.INFO, "core.lang.serveronly.off");
                    }
                }
                System.out.println("Added Translation: " + key + " | " + val);
                map.put(key, val);
            }

            if(onlyservermode == false && deleteothers == false){
                Chat.sendMessage(Bukkit.getConsoleSender(), MessageType.INFO, "core.lang.addthis");
                lang.setKeys(map, "addThis");
            }else
            if(onlyservermode == true && deleteothers == false){
                Chat.sendMessage(Bukkit.getConsoleSender(), MessageType.INFO, "core.lang.addall");
                lang.setKeys(map, "addAll");
            }else
            if(onlyservermode == false && deleteothers == true){
                Chat.sendMessage(Bukkit.getConsoleSender(), MessageType.INFO, "core.lang.this");
                lang.setKeys(map, "This");
            }else
            if(onlyservermode == true && deleteothers == true){
                Chat.sendMessage(Bukkit.getConsoleSender(), MessageType.INFO, "core.lang.all");
                lang.setKeys(map, "All");
            }


        }

        return true;
    }
}
