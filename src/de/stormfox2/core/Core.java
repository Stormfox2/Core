package de.stormfox2.core;

import de.stormfox2.core.commands.ExportCommand;
import de.stormfox2.core.file.ConfigurationFile;
import org.bukkit.plugin.java.JavaPlugin;

import de.stormfox2.core.language.LanguageManager;
import de.stormfox2.core.mysql.MySQL;

import java.util.HashMap;

public class Core extends JavaPlugin{

	public static Core instance;
	private HashMap<String, ConfigurationFile> configs;
	private HashMap<String, MySQL> mysqlconnections;
	private MySQL mysql;
	private LanguageManager languageManager;

	@Override

	public void onEnable() {
		configs = new HashMap<>();
		mysqlconnections = new HashMap<>();

		instance = this;
		
		mysql = new MySQL();
		mysqlconnections.put("language", mysql);

		languageManager = new LanguageManager();

		ConfigurationFile cfg = new ConfigurationFile("config.yml");
		cfg.options().copyDefaults(true);
		cfg.addDefault("config.onlyAPIMode", "false");
		cfg.save();

		this.getCommand("export").setExecutor(new ExportCommand());

	}
	
	@Override
	public void onDisable() {
		MySQL.getInstance().disconnect();
	}
	
	public static Core getInstance() {
		return instance;
	}

	public LanguageManager getLanguageManager() {
		return languageManager;
	}

	public ConfigurationFile getConfig(String name) {
		return configs.get(name);
	}
}
