package fr.capgemini.webservices.apiadresse;

import org.apache.commons.lang3.StringUtils;

import fr.capgemini.webservices.common.APIException;

/**
 * Classe utilitaire pour fabriquer une url pour l'api data gouv
 * 
 * @author aulecoin
 *
 */
public final class ApiAdresseUrlBuilder {
	private ApiAdresseUrlBuilder() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Retourne une url servant à appeler l'api data gouv
	 * 
	 * @return
	 * @throws APIException
	 */
	public static String getUrl(ApiAdresseInfosUrl infos) throws APIException {
		StringBuilder sb = new StringBuilder();
		String protocole = "https://";
		String adresseSite = "api-adresse.data.gouv.fr/";
		String methode = "search/?q=";
		if (infos != null && infos.getAdresse() != null && infos.getAdresse().getNumero() != 0
				&& infos.getAdresse().getVoie() != null && infos.getAdresse().getCodePostal() != null) {
			sb.append(protocole).append(adresseSite).append(methode);
			sb.append(infos.getAdresse().getNumero()).append("+");
			sb.append(infos.getAdresse().getVoie().toString()).append("+");
			sb.append(splitRue(infos.getAdresse().getNomVoie())).append("&");
			sb.append("postcode=").append(infos.getAdresse().getCodePostal());
		} else {
			throw new APIException("APIAdresseInfosURL aren't correctly filled. ");
		}

		return sb.toString();
	}

	/**
	 * découpe le nom de la rue de manière lisible pour l'API adresse data gouv
	 * 
	 * @param rue
	 * @return
	 * @throws APIException
	 */
	private static String splitRue(String rue) throws APIException {
		StringBuilder sb = new StringBuilder();
		int index = 0;
		if (!StringUtils.isBlank(rue)) {
			String rueSansTiret = rue.replaceAll("-", " ").replaceAll("[^-a-zA-Z0-9]", " ");
			String[] arraySplit = rueSansTiret.split(" ");

			for (String str : arraySplit) {
				index++;
				if (index < arraySplit.length) {
					sb.append(str).append("+");
				} else {
					sb.append(str);
				}

			}
		} else {
			throw new APIException("Rue is blank");
		}
		return sb.toString();
	}
}
