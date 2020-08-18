package controllers.place;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Issue1;
import models.Issue2;
import utils.DBUtil;

/**
 * Servlet implementation class PlaceDestroyServlet
 */
@WebServlet("/places/destroy")
public class PlaceDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlaceDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        String _token=(String)request.getParameter("_token");

        if (_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Issue2 i2 = em.find(Issue2.class, (Integer)request.getSession().getAttribute("i2_id"));
            Issue1 i1 = em.find(Issue1.class, (Integer)request.getSession().getAttribute("i1_id"));

            //目標数を減らす
            if (i1.getDecision()==0) {
                if (i2.getCan_flag()==0) {
                    //もともと三田
                    i1.setMita(i1.getMita()-i2.getAim());
                } else if (i2.getCan_flag() == 1) {
                    //もともと日吉
                    i1.setHiyoshi(i1.getHiyoshi()-i2.getAim());
                } else {
                    i1.setOther(i1.getOther()-i2.getAim());
                }

                i1.setRemain(i1.getRemain()+i2.getAim());//残りを減らす
            }


            em.getTransaction().begin();
            em.remove(i2);
            em.getTransaction().commit();
            em.close();

            request.getSession().removeAttribute("i2_id");

            response.sendRedirect(request.getContextPath()+"/place/index");
        }
    }
}





