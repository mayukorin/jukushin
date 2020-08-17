package controllers.newspaper;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Issue1;
import utils.DBUtil;

/**
 * Servlet implementation class NewspapersNewServlet
 */
@WebServlet("/newspapers/new")
public class NewspapersNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewspapersNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        EntityManager em = DBUtil.createEntityManager();

        Issue1 i1 = em.find(Issue1.class, Integer.parseInt(request.getParameter("id")));


        em.close();



        request.setAttribute("i1", i1);
        request.setAttribute("_token", request.getSession().getId());

        request.getSession().setAttribute("i1_id", i1.getId());

        RequestDispatcher rd =request.getRequestDispatcher("/WEB-INF/views/newspapers/new.jsp");
        rd.forward(request, response);

    }

}
