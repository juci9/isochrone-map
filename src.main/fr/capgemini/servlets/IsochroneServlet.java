package fr.capgemini.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONException;
import org.json.JSONObject;

import fr.capgemini.model.Coordonnees;
import fr.capgemini.model.MessagesErreur;
import fr.capgemini.model.MoyenTransport;
import fr.capgemini.utils.JsonReader;
import fr.capgemini.utils.ServletUtil;
import fr.capgemini.webservices.apiadresse.ApiAdresseInfosUrl;
import fr.capgemini.webservices.apiadresse.ApiAdresseGetter;
import fr.capgemini.webservices.common.APIException;
import fr.capgemini.webservices.common.IsochronesInfosUrl;
import fr.capgemini.webservices.navitia.NavitiaInfosUrl;
import fr.capgemini.webservices.navitia.NavitiaIsochroneGetter;
import fr.capgemini.webservices.navitia.NavitiaUrlBuilder;
import fr.capgemini.webservices.ors.ORSInfosUrl;
import fr.capgemini.webservices.ors.ORSIsochroneGetter;
import fr.capgemini.webservices.ors.ORSUrlBuilder;

/**
 * Servlet implementation class IsochroneServlet
 */
@WebServlet("/Isochrone")
public class IsochroneServlet extends HttpServlet {
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
		String transport = (String) session.getAttribute("transport");
		String codeP = (String) session.getAttribute("codePostal");
		int max = (int) session.getAttribute("max");
		int intervalle = (int) session.getAttribute("intervalle");
		ApiAdresseInfosUrl urlApiAdresse = (ApiAdresseInfosUrl) session.getAttribute("urlApiAdresse");
		int positionAdresseDansListe = NumberUtils.toInt(request.getParameter("adresse"), VALEUR_ECHEC_INT);
		// Cette position est celle de l'objet json qui m'intéresse

		verifPositionAdresseDansListe(request, response, VALEUR_ECHEC_INT, positionAdresseDansListe);
		Coordonnees coordDepart = getCoordDepart(request, response, urlApiAdresse, positionAdresseDansListe);
		MoyenTransport moyen = getMoyenTransport(transport);

		if ("commun".equals(transport)) {
			goNavitia(request, response, codeP, max, intervalle, coordDepart);

		} else {
			if (moyen == null) {
				ServletUtil.sendErrorMessageToErrorJsp(MessagesErreur.ERROR_MOYENS, request, response);
			} else {
				goORS(request, response, max, intervalle, coordDepart, moyen);
			}
		}

	}

	private Coordonnees getCoordDepart(HttpServletRequest request, HttpServletResponse response,
			ApiAdresseInfosUrl urlApiAdresse, int positionAdresseDansListe) throws IOException, ServletException {
		Coordonnees coordDepart = new Coordonnees();
		try {
			coordDepart = ApiAdresseGetter.getCoordFromUrl(urlApiAdresse).get(positionAdresseDansListe);
		} catch (APIException e) {
			ServletUtil.sendErrorMessageToErrorJsp(MessagesErreur.ERROR_COORDS_DEPART, request, response);
		}
		request.setAttribute("longitudeDepart", coordDepart.getLongitude());
		request.setAttribute("latitudeDepart", coordDepart.getLatitude());
		return coordDepart;
	}

	private void verifPositionAdresseDansListe(HttpServletRequest request, HttpServletResponse response,
			int valeurEchecInt, int positionAdresseDansListe) throws ServletException, IOException {
		if (positionAdresseDansListe == valeurEchecInt) {
			ServletUtil.sendErrorMessageToErrorJsp(MessagesErreur.ERROR_SELECT_ADRESS, request, response);
		}
	}

	private void goORS(HttpServletRequest request, HttpServletResponse response, int max, int intervalle,
			Coordonnees coordDepart, MoyenTransport moyen) throws IOException, ServletException {
		ORSInfosUrl mesInfos = new ORSInfosUrl(coordDepart, moyen, max, intervalle);
		setCoordsFromUrl(request, mesInfos, response);
		ServletUtil.dispatchToJsp(request, response, "AffichageCarte");
	}

	private void goNavitia(HttpServletRequest request, HttpServletResponse response, String codeP, int max,
			int intervalle, Coordonnees coordDepart) throws IOException, ServletException {
		NavitiaInfosUrl mesInfos = new NavitiaInfosUrl(coordDepart, LocalDateTime.now(), max, codeP, intervalle);
		setCoordsFromUrl(request, mesInfos, response);
		ServletUtil.dispatchToJsp(request, response, "AffichageCarte");
	}

	private MoyenTransport getMoyenTransport(String transport) {
		MoyenTransport moyen = null;

		if (EnumUtils.isValidEnum(MoyenTransport.class, transport)) {
			moyen = MoyenTransport.valueOf(MoyenTransport.class, transport);

		}
		return moyen;
	}

	/**
	 * Permet de récupérer les coordonnées d'un isochrone et de les set dans le
	 * contexte de requête.
	 * 
	 * @param request
	 * @param response
	 * @param mesInfos
	 * @param max
	 * @throws JSONException
	 * @throws IOException
	 * @throws ServletException
	 */
	public void setCoordsFromUrl(HttpServletRequest request, IsochronesInfosUrl mesInfos,
			HttpServletResponse response) throws IOException, ServletException {
		if (mesInfos instanceof NavitiaInfosUrl) {

			NavitiaInfosUrl infosNavitia = (NavitiaInfosUrl) mesInfos;
			setCoordsForNavitia(request, response, infosNavitia);

		} else if (mesInfos instanceof ORSInfosUrl) {
			ORSInfosUrl infosOrs = (ORSInfosUrl) mesInfos;
			setCoordsForORS(request, response, infosOrs);
		}

	}

	private void setCoordsForORS(HttpServletRequest request, HttpServletResponse response, ORSInfosUrl infosOrs)
			throws ServletException, IOException {
		JSONObject jsonORS = new JSONObject();

		try {
			jsonORS = JsonReader.readJsonFromUrl(ORSUrlBuilder.getUrlWithInterval(infosOrs));
		} catch (APIException | IOException e1) {
			ServletUtil.sendErrorMessageToErrorJsp(MessagesErreur.ERROR_APPEL_ORS, request, response);
		}
		try {

			request.setAttribute("isochrones", ORSIsochroneGetter.getEveryIsochrones(jsonORS, infosOrs));

		} catch (APIException e) {

			ServletUtil.sendErrorMessageToErrorJsp(MessagesErreur.ERROR_ISO_ORS, request, response);
		}
	}

	private void setCoordsForNavitia(HttpServletRequest request, HttpServletResponse response,
			NavitiaInfosUrl infosNavitia) throws ServletException, IOException {
		JSONObject jsonNavitia = new JSONObject();
		Properties proper = ServletUtil.getProperties(getServletContext());

		try {
			jsonNavitia = JsonReader.readJsonFromUrl(NavitiaUrlBuilder.getUrlWithInterval(infosNavitia),
					proper.getProperty("db.loginnavitia"), proper.getProperty("db.passwordnavitia"));

		} catch (APIException | IOException e1) {

			ServletUtil.sendErrorMessageToErrorJsp(MessagesErreur.ERROR_APPEL_NAVITIA, request, response);
		}

		try {
			request.setAttribute("isochrones", NavitiaIsochroneGetter.getEveryIsochrones(jsonNavitia, infosNavitia));
		} catch (APIException e) {

			ServletUtil.sendErrorMessageToErrorJsp(MessagesErreur.ERROR_ISO_NAVITIA, request, response);
		}
	}

}
