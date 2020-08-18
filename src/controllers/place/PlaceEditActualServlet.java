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
 * Servlet implementation class PlaceEditActualServlet
 */
@WebServlet("/places/editj")
public class PlaceEditActualServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlaceEditActualServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub


        EntityManager em =DBUtil.createEntityManager();

        Issue1 i1 = em.find(Issue1.class, (Integer)request.getSession().getAttribute("i1_id"));
        Issue2 i2 = em.find(Issue2.class, Integer.parseInt(request.getParameter("id")));

        Long remain = i1.cacultate(i2.getCan_flag());//実際の数を編集しようとしている場所で、もともとの発行部数に、想定目標が届いているか確認する。

        if (remain > 0 && i2.getAct() == 0) {

            response.sendRedirect(request.getContextPath()+"/place/index");

        } else {

            request.setAttribute("i2",i2);
            request.setAttribute("_token",request.getSession().getId());


            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/places/edita.jsp");
            rd.forward(request, response);

        }
    }
}
