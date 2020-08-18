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
 * Servlet implementation class PlaceNewServlet
 */
@WebServlet("/places/new")
public class PlaceNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlaceNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        Long remain = 0L;

        request.setAttribute("_token",request.getSession().getId());
        Integer flag = Integer.parseInt(request.getParameter("flag"));//振り分け確定前なのか、振り分け確定後の日吉・三田どちらか


        EntityManager em =DBUtil.createEntityManager();

        Issue1 i1 = em.find(Issue1.class, (Integer)request.getSession().getAttribute("i1_id"));

        if (flag == 0) {
            //振り分け確定前
            remain = i1.getRemain();
        } else if (flag == 1) {
            //振り分け確定後、三田
            remain = i1.cacultate(0);
        } else if (flag == 2) {
            //振り分け確定後、日吉
            remain = i1.cacultate(1);
        }

        request.setAttribute("remain", remain);//あとどれくらい新聞を割り振ることができるか
        request.setAttribute("flag", flag);

        request.setAttribute("i2", new Issue2());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/places/new.jsp");
        rd.forward(request, response);


    }

}
