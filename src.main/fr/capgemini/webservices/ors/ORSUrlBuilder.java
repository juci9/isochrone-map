package fr.capgemini.webservices.ors;

import fr.capgemini.utils.LoginUtil;
import fr.capgemini.webservices.common.APIException;

/**
 * Classe utilitaire pour fabriquer des urls pour open route service
 * 
 * @author aulecoin
 *
 */
public final class ORSUrlBuilder {
	private ORSUrlBuilder() {
		throw new IllegalStateException("Utility class");
	}

	private static final String POURCENT = "%";
	private static final String ET = "&";

	/**
	 * Url pour afficher une seule isochrone, sans notion d'intervalle.
	 * 
	 * @throws APIException
	 */
	public static String getUrlMaxRangeOnly(ORSInfosUrl infos) throws APIException {
		StringBuilder sb = new StringBuilder();
		if (infos != null && infos.getMaxValue() != 0) {
			sb.append(getDebutUrl(infos));
			sb.append("range=").append(infos.getMaxValue());
			sb.append(ET).append("interval=").append(infos.getMaxValue()).append(ET);
			sb.append("location_type=start");
		} else {
			throw new APIException("Max value is null.");
		}

		return sb.toString();
	}

	private static String getDebutUrl(ORSInfosUrl infos) throws APIException {
		StringBuilder sb = new StringBuilder();

		String protocole = "https://";
		String adresse = "api.openrouteservice.org/";
		String methode = "isochrones?";
		if (infos != null && infos.getCoord() != null && infos.getCoord().getLongitude() != 0
				&& infos.getCoord().getLatitude() != 0 && infos.getMoyen() != null) {
			sb.append(protocole).append(adresse).append(methode);
			sb.append("api_key=").append(LoginUtil.getAPIKEYORS()).append(ET);
			sb.append("locations=");
			sb.append(infos.getCoord().getLongitude()).append(POURCENT).append("2C")
					.append(infos.getCoord().getLatitude());
			sb.append(ET).append("profile=").append(infos.getMoyen().toString());
			sb.append(ET).append("range_type=time").append(ET);
		} else {
			throw new APIException("Coords or transport is empty.");
		}

		return sb.toString();
	}

	/**
	 * Url pour pouvoir afficher plusieurs isochrones, à des intervalles différents
	 * 
	 * @throws APIException
	 */
	public static String getUrlWithInterval(ORSInfosUrl infos) throws APIException {
		StringBuilder sb = new StringBuilder();
		if (infos != null && infos.getIntervalle() != 0 && infos.getMaxValue() != 0) {
			sb.append(getDebutUrl(infos));
			sb.append("range=").append(infos.getMaxValue());
			sb.append(ET).append("interval=").append(infos.getIntervalle()).append(ET);
			sb.append("location_type=start");
		} else {
			throw new APIException("Intervalle or max value is null.");
		}

		return sb.toString();
	}
}
