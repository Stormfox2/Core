package de.stormfox2.core.de.stormfox2.chat;

import de.stormfox2.core.language.LanguageManager;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;

public interface Chat
{
    public static void sendMessage(CommandSender sender, MessageType messageType, String path)
    {
        sendMessage(sender, messageType, "Server", path, new ChatArg[] {});
    }

    public static void sendMessage(CommandSender sender, MessageType messageType, String name, String path)
    {
        sendMessage(sender, messageType, name, path, new ChatArg[] {});
    }

    public static void sendMessage(CommandSender sender, MessageType messageType, String path, ChatArg... args)
    {
        sendMessage(sender, messageType, "Server", path, args);
    }

    public static void sendMessage(CommandSender sender, MessageType messageType, String name, String path, ChatArg... args)
    {
        String nameColorTag = "";
        String argColorTag = "";
        String msgColorTag = "";

        switch (messageType)
        {
            case NONE:
                argColorTag = "§f";
                msgColorTag = "§f";
                break;
            case NORMAL:
                nameColorTag = "§6";
                argColorTag = "§f";
                msgColorTag = "§7";
                break;
            case ERROR:
                nameColorTag = "§c";
                argColorTag = "§c";
                msgColorTag = "§7";
                break;
            default:
                argColorTag = "§f";
                argColorTag = "§f";
                break;
        }
        String message = "";
        if(!(sender instanceof Player))
            message = LanguageManager.getValue("de_de", path);
        else
            message = LanguageManager.getValue((Player)sender, path);

        // Message Formatting
        message = replaceArgs(message, args, argColorTag, msgColorTag);

        // Name Formatting
        name = (name.equals("")) ? msgColorTag : "§7[" + nameColorTag + name + "§7] " + msgColorTag;

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
