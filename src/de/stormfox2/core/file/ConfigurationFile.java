package de.stormfox2.core.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import de.stormfox2.core.Core;

public class ConfigurationFile extends YamlConfiguration {

	private File file;

	public ConfigurationFile(String path) {
		this(path, "");
	}

	public ConfigurationFile(String path, String defaultPath) {

		Core core = Core.getInstance();
		path = core.getDataFolder() + "/" + path;

		file = new File(path);

		try {
			if (!file.exists()) {
				//noinspection ResultOfMethodCallIgnored
				file.getParentFile().mkdirs();
				//noinspection ResultOfMethodCallIgnored
				file.createNewFile();
				if(!defaultPath.equals(""))
					getDefaultConfig(defaultPath);
			}
			load(file);
		} catch (InvalidConfigurationException | IOException e) {
			e.printStackTrace();
		}
	}

	private void getDefaultConfig(String defaultPath) {
		try
		{
			InputStreamReader defConfigStream = new InputStreamReader(Core.getInstance().getResource("res/config/" + defaultPath), "UTF8");
			loadConfiguration(defConfigStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		save();
	}

	public void save() {
		try {
			save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void delete() {
		//noinspection ResultOfMethodCallIgnored
		file.delete();
	}
}
