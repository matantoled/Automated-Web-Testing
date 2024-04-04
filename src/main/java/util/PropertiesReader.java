package util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesReader {

	private Properties props;
	
	public PropertiesReader(String propertiesFilePath) throws Exception {
		props = new Properties();
		FileInputStream fis = new FileInputStream(new File(propertiesFilePath));
		props.load(fis);
		fis.close();
	}
	
	public String readString(String propertyName) {
		String propertyValue = props.getProperty(propertyName, "");
		return propertyValue;
	}
	
	public int readInt(String propertyName) {
		int propertyValue = Integer.parseInt(props.getProperty(propertyName, "-999"));
		return propertyValue;
	}
	
	public boolean readBoolean(String propertyName) {
		boolean propertyValue = Boolean.parseBoolean(props.getProperty(propertyName, "false"));
		return propertyValue;
	}
	
	public <T extends Enum<T>> T readEnum(Class<T> enumType, String propertyName) {
		T propertyValue = Enum.valueOf(enumType, props.getProperty(propertyName, null).toUpperCase());
		return propertyValue;
	}
}
