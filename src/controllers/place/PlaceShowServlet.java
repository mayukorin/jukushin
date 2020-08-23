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
        request.getSession().setAttribute("i2_id", i2.getId());

        Issue1 i1 = em.find(Issue1.class,((Integer) request.getSession().getAttribute("i1_id")));

        if (i1.getDecision() == 1 && i2.getCan_flag() !=2) {
            Long remain = 0L;
            if (i2.getCan_flag()==0) {
                //三田
                remain = i1.cacultate(0);

                request.setAttribute("remain", remain);

            } else {
                //日吉
                remain = i1.cacultate(1);
                System.out.println("あああああああああ"+i2.getAim()+remain);

                request.setAttribute("remain", remain);
            }
        }


        em.close();

        request.setAttribute("i2", i2);
        request.setAttribute("_token", request.getSession().getId());
        //確定前か確定後なのかのパラメタを送る
        request.setAttribute("decision",i1.getDecision());


        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/places/show.jsp");
        rd.forward(request, response);
    }

}
