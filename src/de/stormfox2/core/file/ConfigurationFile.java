package de.stormfox2.core.file;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import de.stormfox2.core.Core;

public class ConfigurationFile extends YamlConfiguration{

	private File file;
	
	public ConfigurationFile(String name) {
		this("", name);
	}
	
	public ConfigurationFile(String path, String name) {
		
		Core core = Core.getInstance();
		path = core.getDataFolder() + path;
		
		file = new File(path, name);

		try {
			if(!file.exists()) {
				file.mkdirs();
				file.createNewFile();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		loadConfiguration(file);
	}
	
	public void save() {
		try {
			save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
