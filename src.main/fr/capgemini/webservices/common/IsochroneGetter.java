package fr.capgemini.webservices.common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe utilitaire permettant de récupérer un Objet Json avec les coordonnées
 * d'un isochrone voulu.
 * 
 * @author aulecoin
 *
 */
public final class IsochroneGetter {
	protected IsochroneGetter() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Methode qui recupere un objet Json pour un isochrone donne.
	 * 
	 * @param json
	 *            : objet recupere a l'url
	 * @param value
	 *            : valeur max d'une isochrone
	 * @param keyArrayEntier
	 *            : la cle permettant de recuperer le tableau entier provenant du
	 *            flux
	 * @param keyJsonVoulu
	 *            : la cle correspondant a l'objet json que l'on veut avoir a la fin
	 *            (ie : contenant seulement les coordonnees qui nous interessent)
	 * @param keyIndiquantValeursIsochrone
	 *            : cle a laquelle figure les valeurs max de chaque isochrone
	 * @return
	 * @throws APIException
	 */
	public static JSONObject getJsonIsochroneFromJson(JSONObject json, int value, String keyArrayEntier,
			String keyJsonVoulu, String keyIndiquantValeursIsochrone) throws APIException {
		JSONObject jsonRetourne = new JSONObject();
		try {
			// récupérer l'array json entier
			JSONArray jsonIsoComplet = json.getJSONArray(keyArrayEntier);
			// récupérer l'objet json qui contient la value voulue
			JSONObject jsonMaxDurationVoulue = getJsonMaxDuration(jsonIsoComplet, value, keyIndiquantValeursIsochrone);
			// dans cet objet, récupérer l'objet qui correspond
			if (jsonMaxDurationVoulue != null) {
				jsonRetourne = jsonMaxDurationVoulue.getJSONObject(keyJsonVoulu);
			}

		} catch (JSONException jsone) {

			throw new APIException("Problem with the " + keyArrayEntier + " search in this json object.", jsone);
		}

		return jsonRetourne;
	}

	/**
	 * Méthode qui récupère l'objet Json correspondant à la durée max de l'isochrone
	 * voulu.
	 * 
	 * @param arrayJson
	 *            : le tableau entier recupere
	 * @param value
	 *            : valeur max de l'isochrone qui nous interesse
	 * @param keyIndiquantValeursIsochrone
	 *            : cle a laquelle figure les valeurs max de chaque isochrone
	 * @return
	 * @throws APIException
	 */
	private static JSONObject getJsonMaxDuration(JSONArray arrayJson, int value, String keyIndiquantValeursIsochrone)
			throws APIException {
		JSONObject jsonMaxDurationVoulue = new JSONObject();

		int maxValuesSuccessives = 0;
		for (int i = 0; i < arrayJson.length(); i++) {
			maxValuesSuccessives = getMaxValuesSuccessives(arrayJson, i, keyIndiquantValeursIsochrone);
			if (maxValuesSuccessives == value) {
				try {
					jsonMaxDurationVoulue = arrayJson.getJSONObject(i);
					break;
				} catch (JSONException jsone) {
					throw new APIException("Json Array for value given not found.", jsone);
				}

			}

		}
		return jsonMaxDurationVoulue;
	}

	/**
	 * Methode qui permet d'afficher les valeurs successives des limites max de
	 * chaque isochrone. Le if est là pour les ORSIsochrone pour lesquelles ces
	 * valeurs sont rangées dans un autre objetJson !
	 * 
	 * @param arrayJson
	 *            : le tableau entier recupere
	 * @param index
	 *            : celui qui sera utilise dans une boucle pour parcourir le tableau
	 * @param keyIndiquantValeursIsochrone
	 *            : cle a laquelle figure les valeurs max de chaque isochrone
	 * @return
	 * @throws APIException
	 */
	private static int getMaxValuesSuccessives(JSONArray arrayJson, int index, String keyIndiquantValeursIsochrone)
			throws APIException {
		int maxValuesSuccessives = -1;
		try {
			if (arrayJson.getJSONObject(index).has("properties")) {
				maxValuesSuccessives = arrayJson.getJSONObject(index).getJSONObject("properties")
						.getInt(keyIndiquantValeursIsochrone);
			} else {
				maxValuesSuccessives = arrayJson.getJSONObject(index).getInt(keyIndiquantValeursIsochrone);
			}

		} catch (JSONException jsone) {
			throw new APIException("This value is not in this json array.", jsone);
		}
		return maxValuesSuccessives;

	}

}
