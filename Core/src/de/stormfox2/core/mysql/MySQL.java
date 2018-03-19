package de.stormfox2.core.mysql;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import de.stormfox2.core.file.ConfigurationFile;

public class MySQL {

	private String host;
	private int port;
	private String database;
	private String username;
	private String password;
	
	private ConfigurationFile config;
	private Connection connection;
	public static MySQL instance;
	
	public MySQL() {
		instance = this;
		
		config = new ConfigurationFile("mysql.yml");
		
		setDefaults();
		getData();
		connect();
	}
	
	public void connect() {
		if(!isConnected()) {
			try {
				connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
				System.out.println("[MySQL] Verbindung aufgebaut!");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void disconnect() {
		if (isConnected()) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setDefaults() {
		
		config.options().copyDefaults(true);
		config.addDefault("config.host", "localhost");
		config.addDefault("config.port", 3306);
		config.addDefault("config.username", "root");
		config.addDefault("config.password", "password");
		config.addDefault("config.database", "languagemanager");
		config.save();
	}
	
	public void getData() {

		setHost(config.getString("config.host"));
		setPort(config.getInt("config.port"));
		setDatabase(config.getString("config.database"));
		setUsername(config.getString("config.username"));
		setPassword(config.getString("config.password"));
		
	}
	
	private Boolean isConnected() {
		return !(connection == null);
	}
		
	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Connection getConnection() {
		return connection;
	}

	public static MySQL getInstance() {
		return instance;
	}
}
