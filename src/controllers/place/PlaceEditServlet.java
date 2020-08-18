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
 * Servlet implementation class PlaceEditServlet
 */
@WebServlet("/places/edit")
public class PlaceEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlaceEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();

        Issue2 i2 = em.find(Issue2.class,((Integer) request.getSession().getAttribute("i2_id")));
        Issue1 i1 = em.find(Issue1.class,((Integer) request.getSession().getAttribute("i1_id")));



        if (i1.getDecision()==0) {
            //振り分け確定前の時は、配布目標欄に残部＋今登録している部数までを上限にする
            Long remain=i1.getRemain();
            request.setAttribute("remain", remain);


        } else {
            //振り分け確定後
            Long remain = 0L;
            if (i2.getCan_flag()==0) {
                //三田
                remain = i1.cacultate(0);

                request.setAttribute("remain", remain);

            } else {
                //日吉
                remain = i1.cacultate(1);

                request.setAttribute("remain", remain);
            }


        }

        request.setAttribute("i2",i2);
        request.setAttribute("_token", request.getSession().getId());


        em.close();

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/places/edit.jsp");
        rd.forward(request, response);
    }
}
