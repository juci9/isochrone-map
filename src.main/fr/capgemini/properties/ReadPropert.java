package fr.capgemini.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadPropert {
	public static void main(final String[] args) {
		getProperties();
	}

	public static Properties getProperties() {

		final Properties prop = new Properties();
		// final Properties proper=;
		FileInputStream input = null;
		// C:\\Users\\nabercha\\git\\PlanMobiliteEntreprise\\WebContent\\WEB-INF\\config.properties

		// File file = new File("/WEB-INF/config.properties");

		try {
			String filename = "../WebContent/WEB-INF/config.properties";
			input = new FileInputStream("../WebContent/WEB-INF/config.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			System.out.println(prop.getProperty("db.database"));
			System.out.println(prop.getProperty("db.username"));
			System.out.println(prop.getProperty("db.password"));
			System.out.println(prop.getProperty("db.loginnavitia"));
			System.out.println(prop.getProperty("db.passwordnavitia"));

		} catch (final IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
}
