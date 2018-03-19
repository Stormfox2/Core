package de.stormfox2.core.language;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.stormfox2.core.mysql.MySQL;

public class LanguageManager {
	public static LanguageManager instance;
	private HashMap<String, String> players;
	private HashMap<String, Language> languages;
	
	public LanguageManager() {
		instance = this;
		
		players = new HashMap<>();
		languages = new HashMap<>();
		
		
		init();
	}
	
	public void init(){
		MySQL mysql = MySQL.getInstance();
		String sql = "SELECT * FROM Language";
		try {
			PreparedStatement request = mysql.getConnection().prepareStatement(sql);
			ResultSet result = request.executeQuery();
			while(result.next()) {
				
				String languageID= result.getString(1);
				String name = result.getString(2);
				
				Language language = new Language(languageID, name);
				System.out.println("[LanguageManager] Initializing language: " + name + "/" + languageID + " | " + language.getValueLength() + " translations loaded!");
				languages.put(languageID, language);
			}
			request.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getValue(String languageID, String key) {
		return languages.get(languageID).getValue(key);
	}
	
	public String getValue(Player player, String key) {
		return getValue(player.getUniqueId(), key);
	}
	
	public String getValue(UUID uuid, String key) {
		return getValue(getPlayerLanguage(uuid), key);
	}
	
	public HashMap<String, String> getPlayer() {
		return players;
	}

	public void setPlayer(String UUID, String LID) {
		players.put(UUID, LID);
	}

	public static LanguageManager getInstance() {
		return instance;
	}
	
	public String getPlayerLanguage(Player p) {
		String uuid = p.getUniqueId().toString();
		
		if(!players.containsKey(uuid)) return "";
		return players.get(uuid);
	}
	
	public String getPlayerLanguage(UUID uuid) {

		if(!players.containsKey(uuid.toString())) return "";
		return players.get(uuid.toString());
	}
	
}
