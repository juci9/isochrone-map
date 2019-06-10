package fr.capgemini.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.capgemini.model.MessagesErreur;

public interface ServletUtil {

	public static Properties getProperties(ServletContext sc) {
		String fileName = "/WEB-INF/config.properties";
		InputStream input = sc.getResourceAsStream(fileName);
		Properties proper = new Properties();
		if (input != null) {
			try {
				proper.load(input);
			} catch (IOException e) {

				e.printStackTrace();
			} finally {
				try {
					input.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}

			}
		}
		return proper;
	}

	/**
	 * Set le message d'erreur dans la requête, puis renvoie la requête et la
	 * réponse à la jsp traitant les erreurs.
	 * 
	 * @param messageErreur
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void sendErrorMessageToErrorJsp(MessagesErreur message, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("messageErreur", message.toString());
		dispatchToJsp(request, response, "PageErreur");
	}

	/**
	 * Méthode permettant de rediriger une requête et une réponse, d'une servlet à
	 * une JSP
	 * 
	 * @param request
	 * @param response
	 * @param jspName
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void dispatchToJsp(HttpServletRequest request, HttpServletResponse response, String jspName)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/" + jspName + ".jsp");
		rd.forward(request, response);
	}
}
