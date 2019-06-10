package fr.capgemini.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Classe utilitaire contenant des méthodes utilisées pour vérifier les saisies
 * 
 * 
 * @author aulecoin
 *
 */
public interface VerifSaisieUtil {

	public static int toSeconds(int minutes) {
		return minutes * 60;
	}

	public static boolean formulaireAdresseEstCorrectementRempli(int numero, String nomVoie, int codeP, String ville,
			int max, int intervalle, int valeurEchec) {
		boolean numeroOk = estEntierPositifNonNul(numero, valeurEchec) && numero < 1000;
		boolean voieOk = !StringUtils.isBlank(nomVoie);
		boolean codePOk = estUnCodePostal(codeP, valeurEchec);
		boolean villeOk = !StringUtils.isBlank(ville);
		boolean maxOk = estEntierPositifNonNul(max, valeurEchec);
		boolean intervalleOk = (estEntierPositifNonNul(intervalle, valeurEchec)) && (intervalle <= max);

		return numeroOk && voieOk && codePOk && villeOk && maxOk && intervalleOk;
	}

	public static boolean estUnCodePostal(int codeP, int valeurEchec) {
		return estEntierPositifNonNul(codeP, valeurEchec) && codeP > 999 && codeP < 100000;
	}

	public static boolean estEntierPositifNonNul(int nombreTest, int valeurEchec) {
		return (nombreTest > 0) && (nombreTest != valeurEchec);
	}

}
