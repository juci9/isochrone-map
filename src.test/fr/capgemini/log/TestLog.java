package fr.capgemini.log;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class TestLog {
	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.setProperty("project-name", "transport");
		FileWriter writer= null;
		try {
			writer = new FileWriter("/Web-INF/config.properties");
			properties.store(writer, "cap");
		}catch (IOException ex) {
			ex.printStackTrace();
		}finally {
			if (writer != null) {
				try {
					writer.close();
				}catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		
		
}
	}
