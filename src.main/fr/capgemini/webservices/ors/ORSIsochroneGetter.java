package fr.capgemini.webservices.ors;

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
 * Classe utilitaire permettant d'avoir un objetJson d'un isochrone pour
 * OpenRouteService
 * 
 * @author aulecoin
 *
 */
public final class ORSIsochroneGetter {
	private ORSIsochroneGetter() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Renvoie l'objet JSON contenu dans un fichier JSON pour une durée max donnée.
	 * Le fichier json, celui qu'on a recupere à l'url open route service
	 * 
	 * @param json
	 *            - provenant de l'url open route service
	 * @param value
	 *            - une des value presentes dans l'url open route services
	 * @return JSONObject
	 * @throws APIException
	 */
	private static JSONObject getJsonIsochroneFromJson(JSONObject json, int value) throws APIException {
		return IsochroneGetter.getJsonIsochroneFromJson(json, value, "features", "geometry", "value");

	}

	/**
	 * Méthode pour récupérer seulement les coordonnées dans un objet Json contenant
	 * seulement les infos pour une isochrone.
	 * 
	 * @param jsonIsochrone
	 *            : celui isolé pour l'isochrone, avec la méthode
	 *            getJsonIsochroneFromJson
	 * @return
	 * @throws APIException
	 */
	private static Isochrone getOnlyCoords(JSONObject jsonIsochrone) throws APIException {
		Isochrone iso = new Isochrone();
		try {
			JSONArray tableauCoordORS = jsonIsochrone.getJSONArray("coordinates");
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < tableauCoordORS.length(); i++) {
				sb.append(tableauCoordORS.get(i));
			}
			iso.getGeoPolygones().add(new GeoPolygon(sb.toString()));
		} catch (JSONException jsone) {
			throw new APIException("Problem with coords search with open route service : ", jsone);
		}

		return iso;
	}

	/**
	 * Méthode retournant directement les coordonnées d'une isochrone à partir du
	 * fichier json entier et de la valeur de l'isochrone voulue.
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
	public static List<Isochrone> getEveryIsochrones(JSONObject json, ORSInfosUrl infos) throws APIException {
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
