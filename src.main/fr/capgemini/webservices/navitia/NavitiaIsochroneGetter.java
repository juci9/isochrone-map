package fr.capgemini.webservices.navitia;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.capgemini.model.GeoPolygon;
import fr.capgemini.model.Isochrone;
import fr.capgemini.webservices.common.APIException;
import fr.capgemini.webservices.common.IsochroneGetter;

/**
 * Classe utilitaire permettant d'avoir un objetJson d'un isochrone pour Navitia
 * 
 * @author aulecoin
 *
 */
public final class NavitiaIsochroneGetter {
	private NavitiaIsochroneGetter() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Renvoie l'objet JSON contenu dans un fichier JSON pour une durée max donnée.
	 * Le fichier json, celui qu'on a recupere à l'url navitia
	 * 
	 * @param json
	 *            - provenant de l'url navitia
	 * @param maxDuration
	 *            - une des maxDuration presentes dans l'url navitia
	 * @return JSONObject
	 * @throws APIException
	 */
	private static JSONObject getJsonIsochroneFromJson(JSONObject json, int maxDuration) throws APIException {

		return IsochroneGetter.getJsonIsochroneFromJson(json, maxDuration, "isochrones", "geojson", "max_duration");
	}

	/**
	 * Méthode permettant de récupérer les coordonnées pour UN isochrone
	 * 
	 * @param jsonIsochrone
	 * @return
	 * @throws APIException
	 */
	private static Isochrone getOnlyCoords(JSONObject jsonIsochrone) throws APIException {
		Isochrone iso = new Isochrone();
		try {
			JSONArray premierGrandTableau = jsonIsochrone.getJSONArray("coordinates");
			for (int i = 0; i < premierGrandTableau.length(); i++) {
				JSONArray deuxiemeTableau = premierGrandTableau.getJSONArray(i);
				if (deuxiemeTableau.getJSONArray(0).get(0) instanceof JSONArray) {
					for (int j = 0; j < deuxiemeTableau.length(); j++) {
						JSONArray tableauContenu = deuxiemeTableau.getJSONArray(j);
						iso.getGeoPolygones().add(buildCoords(tableauContenu));

					}

				} else {
					iso.getGeoPolygones().add(buildCoords(deuxiemeTableau));
				}

			}
		} catch (JSONException jsone) {
			throw new APIException("Problem with coords search in Navitia : ", jsone);
		}

		return iso;
	}

	/**
	 * Méthode pour construire le string contenant les coordonnées des sommets du
	 * polygone
	 * 
	 * @param tableauContenu
	 * @return
	 */
	private static GeoPolygon buildCoords(JSONArray tableauContenu) {
		StringBuilder sb = new StringBuilder();

		for (int z = 0; z < tableauContenu.length(); z++) {

			if (z == tableauContenu.length() - 1) {
				sb.append(tableauContenu.get(z)).append("]");
			} else if (z == 0) {
				sb.append("[").append(tableauContenu.get(z)).append(",");
			} else {
				sb.append(tableauContenu.get(z)).append(",");
			}
		}
		return new GeoPolygon(sb.toString());
	}

	/**
	 * Récupère l'isochrone associé au json renvoyé par l'api navitia
	 * 
	 * @param json
	 * @param value
	 * @return
	 * @throws APIException
	 */
	public static Isochrone getOnlyCoords(JSONObject json, int value) throws APIException {
		return getOnlyCoords(getJsonIsochroneFromJson(json, value));
	}

	/**
	 * Méthode pour avoir tous les isochrones en fonction de la valeur max de trajet
	 * et des intervalles.
	 * 
	 * @param json
	 * @param infos
	 * @return
	 * @throws APIException
	 */
	public static List<Isochrone> getEveryIsochrones(JSONObject json, NavitiaInfosUrl infos) throws APIException {
		List<Isochrone> isochrones = new ArrayList<>();
		int sommeCumul = 0;
		if (infos != null && infos.getMaxValue() != 0 && infos.getIntervalle() != 0) {
			while (sommeCumul < infos.getMaxValue() - infos.getIntervalle()) {
				sommeCumul += infos.getIntervalle();
				isochrones.add(getOnlyCoords(json, sommeCumul));

			}
			isochrones.add(getOnlyCoords(json, infos.getMaxValue()));
		} else {
			throw new APIException("Intervalle or max value is null.");
		}

		return isochrones;
	}

}
