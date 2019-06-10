package fr.capgemini.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe utilitaire permettant d'obtenir un objet Json à partir d'un flux
 * 
 * @author aulecoin
 *
 */
public class JsonReader {
	private JsonReader() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Permet de renvoyer une chaîne de caractères à partir d'un reader. Avec ça, on
	 * va récupérer notre contenu json en format texte
	 * 
	 * @param rd
	 * @return
	 * @throws IOException
	 */
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	/**
	 * Méthode principale qui permet de récupérer un objet Json à partir d'une Url
	 * donnée et d'une combinaison login et password
	 * 
	 * @param url
	 * @param login
	 * @param password
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public static JSONObject readJsonFromUrl(String url, String login, String password) throws IOException {

		URL monUrl = new URL(url);
		HttpsURLConnection connection = (HttpsURLConnection) monUrl.openConnection();
		String loginPassword = login + ":" + password;
		String encodedAuthorization = Base64.getEncoder().encodeToString(loginPassword.getBytes());
		// permet de set l'autorisation !
		connection.setRequestProperty("Authorization", "Basic " + encodedAuthorization);

		InputStream is = connection.getInputStream();
		try (BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));) {
			String jsonText = readAll(rd);
			return new JSONObject(jsonText);
		} finally {
			is.close();

		}
	}

	/**
	 * Sans login password pour open route service
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public static JSONObject readJsonFromUrl(String url) throws IOException {

		URL monUrl = new URL(url);
		HttpsURLConnection connection = (HttpsURLConnection) monUrl.openConnection();
		InputStream is = connection.getInputStream();
		try (BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));) {
			String jsonText = readAll(rd);

			return new JSONObject(jsonText);
		} finally {
			is.close();

		}
	}

}
