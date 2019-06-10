package fr.capgemini.servlets;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.math.NumberUtils;

import fr.capgemini.model.Adresse;
import fr.capgemini.model.Coordonnees;
import fr.capgemini.model.MessagesErreur;
import fr.capgemini.model.TypeVoie;
import fr.capgemini.utils.ServletUtil;
import fr.capgemini.utils.VerifSaisieUtil;
import fr.capgemini.webservices.apiadresse.ApiAdresseInfosUrl;
import fr.capgemini.webservices.apiadresse.ApiAdresseGetter;
import fr.capgemini.webservices.common.APIException;

/**
 * Servlet implementation class VerifAdresseServlet
 */
@WebServlet("/VerifAdresse")
public class VerifAdresseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int VALEUR_ECHEC_INT = -1;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		// je récupère toutes les valeurs saisies dans le formulaire par l'utilisateur
		String transport = request.getParameter("transport");
		String typeVoie = request.getParameter("typeVoie");
		String ville = request.getParameter("ville");
		String code = request.getParameter("codep");
		String nomVoie = Normalizer.normalize(request.getParameter("nomVoie"), Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "");
		int numero = NumberUtils.toInt(request.getParameter("numero"), VALEUR_ECHEC_INT);
		int codeP = NumberUtils.toInt(code, VALEUR_ECHEC_INT);
		int max = VerifSaisieUtil.toSeconds((NumberUtils.toInt(request.getParameter("max"), VALEUR_ECHEC_INT)));
		int intervalle = VerifSaisieUtil
				.toSeconds(NumberUtils.toInt(request.getParameter("intervalle"), VALEUR_ECHEC_INT));
		TypeVoie typeDeVoie = getTypeVoie(typeVoie);
		// s'il y a une erreur ou si je n'ai pas de type de voie correspondant
		if (typeDeVoie == null || !VerifSaisieUtil.formulaireAdresseEstCorrectementRempli(numero, nomVoie, codeP, ville,
				max, intervalle, VALEUR_ECHEC_INT)) {
			ServletUtil.sendErrorMessageToErrorJsp(MessagesErreur.ERROR_FORM, request, response);
		} else {
			Adresse adresseSaisie = new Adresse(numero, typeDeVoie, nomVoie, code, ville);
			ApiAdresseInfosUrl urlApiAdresse = new ApiAdresseInfosUrl(adresseSaisie);
			List<String> listePropositions = new ArrayList<>();
			List<Coordonnees> coord = new ArrayList<>();
			try {
				listePropositions = ApiAdresseGetter.getListeAdressesFromUrl(urlApiAdresse);
				coord = ApiAdresseGetter.getCoordFromUrl(urlApiAdresse);
			} catch (APIException e) {
				ServletUtil.sendErrorMessageToErrorJsp(MessagesErreur.ERROR_GET_ADRESSES, request, response);

			}

			if (CollectionUtils.isEmpty(listePropositions) || CollectionUtils.isEmpty(coord)) {
				ServletUtil.sendErrorMessageToErrorJsp(MessagesErreur.ERROR_ADRESSE_NOT_FOUND, request, response);
			} else {
				requestSet(request, listePropositions, coord);
				sessionSet(session, transport, code, max, intervalle, urlApiAdresse);
				ServletUtil.dispatchToJsp(request, response, "VerifAdresse");
			}

		}
	}

	private TypeVoie getTypeVoie(String typeVoie) {
		TypeVoie typeDeVoie = null;

		if (EnumUtils.isValidEnum(TypeVoie.class, typeVoie)) {
			typeDeVoie = TypeVoie.valueOf(TypeVoie.class, typeVoie);

		}
		return typeDeVoie;
	}

	private void requestSet(HttpServletRequest request, List<String> listeAdresses, List<Coordonnees> coord) {
		request.setAttribute("listeAdresses", listeAdresses);
		request.setAttribute("coordAdresses", coord);
	}

	private void sessionSet(HttpSession session, String transport, String code, int max, int intervalle,
			ApiAdresseInfosUrl urlApiAdresse) {
		session.setAttribute("urlApiAdresse", urlApiAdresse);
		session.setAttribute("max", max);
		session.setAttribute("intervalle", intervalle);
		session.setAttribute("transport", transport);
		session.setAttribute("codePostal", code);
	}

}
