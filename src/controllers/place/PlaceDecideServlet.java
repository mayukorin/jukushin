package controllers.place;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Issue1;
import models.Issue2;
import models.company;
import utils.DBUtil;

/**
 * Servlet implementation class PlaceDecideServlet
 */
@WebServlet("/places/decide")
public class PlaceDecideServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlaceDecideServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        Long hake=0L;

        EntityManager em = DBUtil.createEntityManager();

        Issue1 i1 = em.find(Issue1.class, (Integer)request.getSession().getAttribute("i1_id"));

        company c = (company)request.getSession().getAttribute("login_company");



        Long remain = i1.getRemain();

        if (remain!=0) {
            //残部がまだ残っている時
            String error = "残部が0になっていません。\nあと"+remain+"部を振り分けなければいけません。";
            request.getSession().setAttribute("error",error);
            em.close();

            response.sendRedirect(request.getContextPath()+"/place/index");
        } else {
            //残部が残っていない時
            request.getSession().removeAttribute("decision");
            i1.setDecision(1);////振り分け確定フラッグを1にする

            //その他へ送る分
            List<Issue2> places =em.createNamedQuery("getcanplaces",Issue2.class).setParameter("flag",2).setParameter("company",c).setParameter("newspaper",i1.getNewspaper()).getResultList();
            Iterator<Issue2> it =places.iterator();
            while (it.hasNext()) {
                Issue2 o =it.next();

                o.setAct(o.getAim());

                hake += o.getAct();

            }
            em.getTransaction().begin();
            em.getTransaction().commit();
            /////////////////////////////////////////////


            i1.setRemain(i1.getVolumn()-hake);///振り分け確定後は、発行部数全て余っているので0にする。
            i1.setHake(hake);//ハケはその他のものだけ追加
            i1.setRate((double) i1.getHake()/i1.getVolumn());

            ///更新したデータベースを保存する
            em.getTransaction().begin();
            em.getTransaction().commit();

            em.close();

            request.getSession().setAttribute("flush", "振り分けが確定しました。");
            response.sendRedirect(request.getContextPath()+"/place/index");
        }
    }
}
