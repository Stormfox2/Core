package de.stormfox2.core.de.stormfox2.chat;

import de.stormfox2.core.language.LanguageManager;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;

public class Chat
{
    public static void sendMessage(CommandSender sender, MessageType messageType, String name, String path)
    {
        sendMessage(sender, messageType, name, path, new ChatArg[] {});
    }

    public static void sendMessage(CommandSender sender, MessageType messageType, String path, ChatArg... args)
    {
        sendMessage(sender, messageType, "§6V§eontex§8.§eNET", path, args);
    }

    public static void sendMessage(CommandSender sender, MessageType messageType, String name, String path, ChatArg... args)
    {
        String nameColorTag = "";
        String argColorTag = "";
        String msgColorTag = "";

        switch (messageType)
        {
            case INFO:
                nameColorTag = "§6";
                argColorTag = "§6";
                msgColorTag = "§7";
                break;
            case NORMAL:
                nameColorTag = "§e";
                argColorTag = "§e";
                msgColorTag = "§7";
                break;
            case ERROR:
                nameColorTag = "§c";
                argColorTag = "§c";
                msgColorTag = "§7";
                break;
            default:
                nameColorTag = "§e";
                msgColorTag = "§f";
                argColorTag = "§f";
                break;
        }
        String message = "";
        if(!(sender instanceof Player))
            message = LanguageManager.getValue("de-de", path);
        else{
            Player p = (Player) sender;
            message = LanguageManager.getValue(p, path);
        }

        // Message Formatting
        message = replaceArgs(message, args, argColorTag, msgColorTag);


        // Name Formatting
        name = (name.equals("")) ? msgColorTag : "§7[" + nameColorTag + name + "§7] ";

        sender.sendMessage(name + message);
    }

    static String replaceArgs(String message, ChatArg[] args, String argColorTag, String msgColorTag)
    {
        for (ChatArg arg : args)
        {
            message = message.replace(arg.getReplace(), argColorTag + arg.getValue() + msgColorTag);
        }

        return message;
    }
}
