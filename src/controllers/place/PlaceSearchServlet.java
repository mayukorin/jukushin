package controllers.place;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Issue2;
import utils.DBUtil;

/**
 * Servlet implementation class PlaceSearchServlet
 */
@WebServlet("/place/search")
public class PlaceSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlaceSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();
        Issue2 i2 = em.find(Issue2.class,Integer.parseInt(request.getParameter("id")));

        List<Issue2> i2s = em.createNamedQuery("search",Issue2.class).setParameter("company", i2.getCompany()).setParameter("month", i2.getNewspaper().getMonth()).setParameter("place", i2.getPlace()).getResultList();//（同じ月に）同じ場所を配達した時のissu2

        em.close();
        request.setAttribute("i2s",i2s);
        request.setAttribute("i2", i2);


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/places/search.jsp");
        rd.forward(request, response);
    }

}
