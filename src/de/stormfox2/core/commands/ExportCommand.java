package de.stormfox2.core.commands;

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
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ExportCommand implements CommandExecutor {
    String usage = "/export <language>";

    public ExportCommand(){
        LanguageManager.registerDefault("core.error.onlyconsole", "&cThis Command is only for console users.");
        LanguageManager.registerDefault("core.error.invalidArgs", "False usage of the command!");
        LanguageManager.registerDefault("core.command.usage", "Usage: %c");
        LanguageManager.registerDefault("core.info.createdConfig", "Successfully created the config %c");
        LanguageManager.registerDefault("core.error.languageNotExist", "Language %c not exist!");


    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof ConsoleCommandSender)){
            //sender.sendMessage(LanguageManager.getInstance().getValue((Player)sender, "core.error.onlyconsole"));
            Chat.sendMessage(sender, MessageType.ERROR, "Vontex.NET", "core.error.onlyconsole");
            return true;
        }
        if (!(args.length == 1)){
            //sender.sendMessage(LanguageManager.getInstance().getValue(p, "core.error.invalidArgs"));
            Chat.sendMessage(sender, MessageType.ERROR, "Vontex.NET", "core.error.invalidArgs");
            //sender.sendMessage(LanguageManager.getInstance().getValue(p, "core.command.usage") + usage);
            Chat.sendMessage(sender, MessageType.ERROR, "Vontex.NET","core.command.usage",new ChatArg("%c", usage));
        } else {
            Language lang = LanguageManager.getInstance().getLanguage(args[0]);
            if(lang == null){
                Chat.sendMessage(sender, MessageType.ERROR, "Vontex.NET", "core.error.languageNotExist", new ChatArg("%c", args[0]));
                return true;
            }
            HashMap<String, String> keys = lang.getKeys();
            ConfigurationFile file = new ConfigurationFile("languages/" + lang.getName());
            file.options().copyDefaults(true);

            for(Map.Entry<String, String> entry : keys.entrySet())
                file.addDefault(entry.getKey(), entry.getValue());

            file.save();
            Chat.sendMessage(sender, MessageType.NORMAL, "Vontex.NET", "core.info.createdConfig");
        }
        return true;
    }
}
