package fr.capgemini.utils;

import java.util.List;

public interface ListUtil {
	
	/**
	 * Ne sert à rien, c'est juste pour les tests !
	 * @param liste
	 */
	public static void afficherListe(List<String> liste) {
		int i=0;
		for (String string : liste) {			
			System.out.println(i+" : "+string);
			i++;
		}
	}
}
