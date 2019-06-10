package fr.capgemini.servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.capgemini.model.TypeVoie;
import fr.capgemini.utils.ServletUtil;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/Home")
public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	//private static final Logger logger = Logger.getLogger(HomeServlet.class);
	@Override
	public void init() throws ServletException {
		ServletContext sc = getServletContext();
		sc.setAttribute("typesVoie", TypeVoie.values());
		super.init();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtil.dispatchToJsp(request, response, "Home");
	}

}
