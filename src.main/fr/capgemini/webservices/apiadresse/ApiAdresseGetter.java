package fr.capgemini.webservices.apiadresse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.capgemini.model.Coordonnees;
import fr.capgemini.utils.JsonReader;
import fr.capgemini.webservices.common.APIException;

/**
 * Classe utilitaire avec des méthodes pour l'API qui nous retourne des
 * informations sur une adresse postale
 * 
 * @author aulecoin
 *
 */
public interface ApiAdresseGetter {

	public static final String FEATURES = "features";

	/**
	 * Retourne la liste des coordonnées pour les différentes propositions faites
	 * par l'API adresse data gouv
	 * 
	 * @param urlAdresse
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 * @throws APIException
	 */
	public static List<Coordonnees> getCoordFromUrl(ApiAdresseInfosUrl urlAdresse) throws IOException, APIException {

		JSONObject json = JsonReader.readJsonFromUrl(ApiAdresseUrlBuilder.getUrl(urlAdresse));

		return getCoordFromJson(json);
	}

	/**
	 * Retourne la liste des coordonnées pour les différentes propositions faites
	 * par l'API adresse data gouv
	 * 
	 * @param json
	 * @return
	 * @throws APIException
	 * @throws JSONException
	 * @throws IOException
	 */
	public static List<Coordonnees> getCoordFromJson(JSONObject json) throws APIException {
		List<Coordonnees> coords = new ArrayList<>();
		try {
			JSONArray jsonArray = json.getJSONArray(FEATURES);
			// je parcours chaque tableau pour chaque réponse
			// en effet, une seule adresse peut donner lieu à plusieurs propositions de la
			// part de l'API
			for (int i = 0; i < jsonArray.length(); i++) {
				// je récupère chaque tableau de coordonnées
				JSONArray arrayCoord = jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");
				// je rentre ces coordonnées dans un objet
				Coordonnees coordTemp = new Coordonnees(arrayCoord.getDouble(0), arrayCoord.getDouble(1));
				// je l'ajoute à ma liste :
				coords.add(coordTemp);
			}
		} catch (JSONException jsone) {
			throw new APIException("Probleme avec la recherche de la liste des coordonnees des adresses : ", jsone);
		}

		return coords;
	}

	/**
	 * Retourne la liste des différentes propositions d'adresses postales faites par
	 * l'API adresse data gouv
	 * 
	 * @param urlAdresse
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 * @throws APIException
	 */
	public static List<String> getListeAdressesFromUrl(ApiAdresseInfosUrl urlAdresse) throws IOException, APIException {
		JSONObject json = JsonReader.readJsonFromUrl(ApiAdresseUrlBuilder.getUrl(urlAdresse));

		return getListeAdressesFromJson(json);
	}

	/**
	 * Retourne la liste des différentes propositions d'adresses postales faites par
	 * l'API adresse data gouv
	 * 
	 * @param json
	 * @return
	 * @throws APIException
	 * @throws JSONException
	 * @throws IOException
	 */
	public static List<String> getListeAdressesFromJson(JSONObject json) throws APIException {
		List<String> listeAdresses = new ArrayList<>();
		try {
			JSONArray jsonArray = json.getJSONArray(FEATURES);
			for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject jsonProperties = jsonArray.getJSONObject(i).getJSONObject("properties");
				listeAdresses.add(jsonProperties.getString("label"));
			}

		} catch (JSONException jsone) {

			throw new APIException("Probleme avec la recherche des adresses : ", jsone);
		}
		return listeAdresses;
	}
}
