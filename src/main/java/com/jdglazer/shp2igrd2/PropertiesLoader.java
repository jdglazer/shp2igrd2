package com.jdglazer.shp2igrd2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.naming.ConfigurationException;

public class PropertiesLoader {
	
	private final String PROPERTIES_FILE_FMT = "%s.properties";
	private Properties properties;
	
	public PropertiesLoader(String applicationName) throws ConfigurationException {
		String propertiesFile = String.format(PROPERTIES_FILE_FMT, applicationName);
		InputStream propertiesInputStream = null;
		
		if((propertiesInputStream = getClass().getResourceAsStream(propertiesFile)) == null ) {
			throw new ConfigurationException("No core application properties found");
		}
		
		properties = new Properties();
		
		try {
			properties.load(propertiesInputStream);
		} catch (IOException e) {
			throw new ConfigurationException("Error loading core application properties file");
		}
	}
	
	public String getApplicationProperty(String key) {
		return properties.getProperty(key, "");
	}
}
