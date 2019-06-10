package fr.capgemini.utils;

/**
 * Classe contenant pour le moment nos identifiants pour les différentes API -->
 * à migrer !
 * 
 * @author aulecoin
 *
 */
public abstract class LoginUtil {

	private LoginUtil() {
		throw new IllegalStateException("Utility class");
	}

	private static final String LOGINNAVITIA = "c4f9d646-058e-46e8-b103-4e4e3b64c258";
	private static final String PASSWORDNAVITIA = "";
	private static final String APIKEYORS = "58d904a497c67e00015b45fc640de93e3f5f4d2bbb2db939bbad526d";

	public static String getLOGINNAVITIA() {
		return LOGINNAVITIA;
	}

	public static String getPASSWORDNAVITIA() {
		return PASSWORDNAVITIA;
	}

	public static String getAPIKEYORS() {
		return APIKEYORS;
	}

}
