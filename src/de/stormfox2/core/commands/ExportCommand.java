package de.stormfox2.core.commands;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.Configuration;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.stormfox2.core.file.ConfigurationFile;
import de.stormfox2.core.language.Language;
import de.stormfox2.core.language.LanguageManager;

public class ExportCommand implements CommandExecutor{

	public static ExportCommand instance;
	
	public ExportCommand() {
		instance = this;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().length() == 1) {
			Language lang = LanguageManager.getInstance().getLanguage(args[0]);
			HashMap<String, String> keys = lang.getKeys();
			ConfigurationFile file = new ConfigurationFile("/languages", lang.getName() + "/" + lang.getId());
			for(Map.Entry<String, String> e : keys.entrySet())
				file.addDefault(e.getKey(), e.getValue());
			file.save();
			
		}
		return true;
	}
	
	
	
}
