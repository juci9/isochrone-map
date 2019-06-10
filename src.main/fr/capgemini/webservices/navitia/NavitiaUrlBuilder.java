package fr.capgemini.webservices.navitia;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import fr.capgemini.model.RegionsFranceNavitia;
import fr.capgemini.utils.DateUtil;
import fr.capgemini.utils.UtilsException;
import fr.capgemini.webservices.common.APIException;

/**
 * Classe utilitaire pour fabriquer des url pour Navitia
 * 
 * @author aulecoin
 *
 */
public final class NavitiaUrlBuilder {
	private static final String BOUNDARY_DURATION = "boundary_duration%5B%5D=";
	private static final String POURCENT = "%";
	private static final String ET = "&";

	private NavitiaUrlBuilder() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Url pour afficher une seule isochrone, sans notion d'intervalle.
	 * 
	 * @throws APIException
	 * @throws UtilsException
	 */
	public static String getUrlMaxRangeOnly(NavitiaInfosUrl infos) throws APIException {
		StringBuilder sb = new StringBuilder();
		if (infos != null && infos.getMaxValue() != 0) {
			sb.append(getDebutUrl(infos));
			sb.append(BOUNDARY_DURATION).append(infos.getMaxValue()).append(ET);

		} else {
			throw new APIException("Max value is null.");
		}

		return sb.toString();

	}

	private static String getDebutUrl(NavitiaInfosUrl infos) throws APIException {
		StringBuilder sb = new StringBuilder();
		String protocole = "https://";
		String adresse = "api.navitia.io/v1/coverage/";
		String methode = "isochrones?";
		RegionsFranceNavitia region = getRegionFromPostCode(infos.getCodePostal());

		if (region == null) {
			throw new APIException("Navitia region not found from postcode.");
		} else {
			if (infos.getCoord() != null && infos.getCoord().getLongitude() != 0 && infos.getCoord().getLatitude() != 0
					&& infos.getDate() != null) {
				sb.append(protocole).append(adresse).append(region.toString()).append("/").append(methode);
				sb.append("from=").append(infos.getCoord().getLongitude()).append(POURCENT).append("3B")
						.append(infos.getCoord().getLatitude());
				try {
					sb.append(ET).append("datetime=").append(DateUtil.getDateTimeForNavitia(infos.getDate()))
							.append(ET);
				} catch (UtilsException e) {
					e.printStackTrace();
				}
			} else {
				throw new APIException("Coords or date is empty.");
			}
		}

		return sb.toString();
	}

	/**
	 * Url pour pouvoir afficher plusieurs isochrones, à des intervalles différents
	 * 
	 * @throws APIException
	 */
	public static String getUrlWithInterval(NavitiaInfosUrl infos) throws APIException {
		StringBuilder sb = new StringBuilder();
		int sommeCumul = 0;

		if (infos != null && infos.getIntervalle() != 0 && infos.getMaxValue() != 0) {
			sb.append(getDebutUrl(infos));
			while (sommeCumul < infos.getMaxValue() - infos.getIntervalle()) {
				sommeCumul += infos.getIntervalle();
				sb.append(BOUNDARY_DURATION).append(sommeCumul).append(ET);
			}
			sb.append(BOUNDARY_DURATION).append(infos.getMaxValue()).append(ET);
		} else {
			throw new APIException("Intervalle or max value is null.");
		}

		return sb.toString();
	}

	private static RegionsFranceNavitia getRegionFromPostCode(String codeP) throws APIException {
		RegionsFranceNavitia region = null;
		ArrayList<String> codesIdf = new ArrayList<>(Arrays.asList("75", "77", "78", "91", "92", "93", "94", "95"));
		ArrayList<String> codesSO = new ArrayList<>(Arrays.asList("09", "12", "16", "17", "19", "23", "24", "31", "32",
				"33", "40", "46", "47", "64", "65", "79", "81", "82", "86", "87"));
		ArrayList<String> codesNE = new ArrayList<>(Arrays.asList("67", "68", "21", "71", "58", "89", "02", "60", "80",
				"70", "90", "25", "39", "62", "59", "08", "51", "10", "52"));
		ArrayList<String> codesNO = new ArrayList<>(Arrays.asList("76", "27", "14", "50", "61", "53", "72", "44", "49",
				"85", "28", "41", "45", "37", "36", "18"));
		ArrayList<String> codesBretagne = new ArrayList<>(Arrays.asList("29", "22", "56", "35"));
		ArrayList<String> codesSE = new ArrayList<>(Arrays.asList("01", "74", "73", "38", "26", "07", "42", "69", "05",
				"04", "06", "83", "13", "84", "48", "30", "34", "11", "66", "03", "63", "15", "43"));
		if (!StringUtils.isBlank(codeP)) {
			String debutCodep = codeP.substring(0, 2);

			if (codesIdf.contains(debutCodep)) {
				region = RegionsFranceNavitia.ILEDEFRANCE;
			} else if (codesSO.contains(debutCodep)) {
				region = RegionsFranceNavitia.SUDOUEST;
			} else if (codesNE.contains(debutCodep)) {
				region = RegionsFranceNavitia.NORDEST;
			} else if (codesNO.contains(debutCodep)) {
				region = RegionsFranceNavitia.NORDOUEST;
			} else if (codesBretagne.contains(debutCodep)) {
				region = RegionsFranceNavitia.BRETAGNE;
			} else if (codesSE.contains(debutCodep)) {
				region = RegionsFranceNavitia.SUDEST;
			}
		} else {
			throw new APIException("Code postal is blank");
		}

		return region;
		// amiens : 80000 à 80099, 80903 à 80905, 80909, 80919, 800891
		// Caen : 14000 à 14099, 14901 à 14909, 14911 à 14916, 14919 14921 14922 14923
		// 14931 14932 14933 14934 14949

	}

}
