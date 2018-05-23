package de.stormfox2.core;

import de.stormfox2.core.command.commands.ExportCommand;
import de.stormfox2.core.command.commands.ImportCommand;
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

		createTranslations();


		this.getCommand("import").setExecutor(new ImportCommand());
		this.getCommand("export").setExecutor(new ExportCommand());

	}

	private void createTranslations() {
		LanguageManager.registerDefault("core.error.onlyconsole", "This Command is only for console users.");
		LanguageManager.registerDefault("core.error.nopermissions", "You dont have the permission to use this command!");
		LanguageManager.registerDefault("core.error.invalidArgs", "False usage of the command!");
		LanguageManager.registerDefault("core.info.usage", "Usage: %c");
		LanguageManager.registerDefault("core.info.createdConfig", "Successfully created the config");
		LanguageManager.registerDefault("core.error.languageNotExist", "Language %c not exist!");
		LanguageManager.registerDefault("core.lang.serveronly.on", "Only servermode turned §aon");
		LanguageManager.registerDefault("core.lang.serveronly.off", "Only servermode turned §coff");
		LanguageManager.registerDefault("core.lang.deleteothers.on", "Key overriding turned §aon");
		LanguageManager.registerDefault("core.lang.deleteothers.off", "Key overriding turned §coff");
		LanguageManager.registerDefault("core.lang.all", "All keys replaced! Globally");
		LanguageManager.registerDefault("core.lang.this", "All keys replaced! Only Serversite");
		LanguageManager.registerDefault("core.lang.addall", "Added new keys! Globally");
		LanguageManager.registerDefault("core.lang.addthis", "Added new keys! Only Serversite");
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
