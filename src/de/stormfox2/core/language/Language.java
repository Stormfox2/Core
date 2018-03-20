package de.stormfox2.core.language;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import de.stormfox2.core.mysql.MySQL;

public class Language {
	
	private String id;
	private String name;
	
	private HashMap<String, String> keys;
	
	public Language(String id, String name) {
		
		this.id = id;
		this.name = name;
		keys = new HashMap<>();
		
		init();
	}
	
	private void init() {
		MySQL mySQL = MySQL.getInstance();
		String sql = "SELECT ID, Value FROM LanguageLine WHERE LanguageID=?";
		try {
			PreparedStatement request = mySQL.getConnection().prepareStatement(sql);
			request.setString(1, getId());
			ResultSet result = request.executeQuery();
			while(result.next()) {
				
				String key = result.getString(1);
				String value = result.getString(2);
				addValue(key, value);
			}
			request.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addValue(String key, String value) {
		keys.put(key, value);
	}
	
	public int getValueLength() {
		return keys.size();
	}
	
	public String getValue(String key) {
		return keys.get(key);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
