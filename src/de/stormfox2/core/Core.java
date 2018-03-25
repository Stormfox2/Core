package de.stormfox2.core;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import de.stormfox2.core.commands.ExportCommand;
import de.stormfox2.core.language.LanguageManager;
import de.stormfox2.core.mysql.MySQL;

public class Core extends JavaPlugin{

	public static Core instance;
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		new MySQL();
		new LanguageManager();
		getCommand("export").setExecutor(new ExportCommand());
		
	}
	
	@Override
	public void onDisable() {
		MySQL.getInstance().disconnect();
	}
	
	public static Core getInstance() {
		return instance;
	}
}
