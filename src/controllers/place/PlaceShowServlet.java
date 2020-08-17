package controllers.place;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Issue1;
import models.Issue2;
import utils.DBUtil;

/**
 * Servlet implementation class PlaceShowServlet
 */
@WebServlet("/places/show")
public class PlaceShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlaceShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();

        Issue2 i2 =em.find(Issue2.class, Integer.parseInt(request.getParameter("id")));
        Issue1 i1 = (Issue1) request.getSession().getAttribute("i1");


        em.close();

        request.setAttribute("i2", i2);
        request.setAttribute("_token", request.getSession().getId());
        //確定前か確定後七日のパラメタを送る
        request.setAttribute("decision",i1.getDecision());
        request.getSession().setAttribute("i2",i2);//destroy用にパラメータをセッションスコープへ

        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/places/show.jsp");
        rd.forward(request, response);
    }

}
