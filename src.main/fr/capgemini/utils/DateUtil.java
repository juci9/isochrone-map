package fr.capgemini.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe utilitaire concernant les dates
 * 
 * @author aulecoin
 *
 */
public interface DateUtil {

	/**
	 * Une méthode qui convertir une localdatetime en une chaîne lisible pour une
	 * url navitia, sous la forme : yyyyMMddThhmmss
	 * 
	 * @param dateTime
	 * @return
	 * @throws UtilsException
	 */
	public static String getDateTimeForNavitia(LocalDateTime dateTime) throws UtilsException {
		StringBuilder sb = new StringBuilder();
		DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyyMMdd");
		DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HHmmss");
		if (dateTime != null) {
			sb.append(dateTime.format(formatterDate));
			sb.append("T");
			sb.append(dateTime.format(formatterTime));
		} else {
			throw new UtilsException("DateTime is null.");
		}

		return sb.toString();
	}

}
