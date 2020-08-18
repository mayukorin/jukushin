package controllers.place;

import java.io.IOException;
import java.sql.Timestamp;

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
 * Servlet implementation class PlaceUpdateServlet
 */
@WebServlet("/places/update")
public class PlaceUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlaceUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        String _token =(String) request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em =DBUtil.createEntityManager();

            Issue1 i1 = em.find(Issue1.class,((Integer) request.getSession().getAttribute("i1_id")));
            Issue2 i2 = em.find(Issue2.class,((Integer) request.getSession().getAttribute("i2_id")));



            if (i1.getDecision()==0) {
                //振り分け確定前


                //全体の残部を更新する
                i1.setRemain(i1.getRemain()+i2.getAim()-Integer.parseInt(request.getParameter("aim")));

                if (i2.getCan_flag() != 2) {

                    if (i2.getCan_flag()==Integer.parseInt(request.getParameter("mh"))&&Integer.parseInt(request.getParameter("mh"))==1) {
                        //元々配達場所が日吉のものがそのまま日吉。部数だけ更新
                        i1.setHiyoshi(i1.getHiyoshi()-i2.getAim()+Integer.parseInt(request.getParameter("aim")));
                    } else if (i2.getCan_flag()==Integer.parseInt(request.getParameter("mh"))&&Integer.parseInt(request.getParameter("mh"))==0) {
                        //元々配達場所が三田のものがそのまま三田。部数だけ更新
                        i1.setMita(i1.getMita()-i2.getAim()+Integer.parseInt(request.getParameter("aim")));
                    } else if (i2.getCan_flag()!=Integer.parseInt(request.getParameter("mh"))&&Integer.parseInt(request.getParameter("mh"))==1) {
                        //元々の配達場所が三田のものが日吉になった。
                        i1.setMita(i1.getMita()-i2.getAim());
                        i1.setHiyoshi(i1.getHiyoshi()+Integer.parseInt(request.getParameter("aim")));
                    } else {
                        //元々の配達場所が日吉のものが三田になった。
                        i1.setHiyoshi(i1.getHiyoshi()-i2.getAim());
                        i1.setMita(i1.getMita()+Integer.parseInt(request.getParameter("aim")));
                    }
                    i2.setCan_flag(Integer.parseInt(request.getParameter("mh")));
                } else if (i2.getCan_flag() == 2) {
                    //それ以外の配布場所
                    i1.setOther(i1.getOther()-i2.getAim()+Integer.parseInt(request.getParameter("aim")));
                    i2.setCan_flag(2);
                }

            }


            i2.setAim(Long.parseLong(request.getParameter("aim")));

            if (i1.getDecision()==0) {
                //振り分け確定前
                i2.setAimconst(Long.parseLong(request.getParameter("aim")));//固定の値の目標を変更
            }


            i2.setContent(request.getParameter("content"));
            i2.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            //データベースを更新
            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "更新が完了しました。");


            response.sendRedirect(request.getContextPath()+"/place/index");
        }
    }
}




