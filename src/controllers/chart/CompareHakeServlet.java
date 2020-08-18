package controllers.chart;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class CompareHakeServlet
 */
@WebServlet("/places/circle")
public class CompareHakeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompareHakeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        EntityManager em = DBUtil.createEntityManager();

        Issue1 i1 = em.find(Issue1.class, (Integer)request.getSession().getAttribute("i1_id"));
        company c =(company)request.getSession().getAttribute("login_company");

        Integer flag = Integer.parseInt(request.getParameter("id"));

        List<Issue2> li=em.createNamedQuery("top5places",Issue2.class).setParameter("can_flag",flag).setParameter("company",c).setParameter("newspaper", i1.getNewspaper()).getResultList();

        //トップ4のハケの場所
        String a =li.get(0).getPlace().getName();
        String b =li.get(1).getPlace().getName();
        String f =li.get(2).getPlace().getName();
        String d =li.get(3).getPlace().getName();
        Long hake;//全体のハケ
        //全体のハケ
        if (flag==1) {
            hake = i1.getHiyoshi_a();
        } else {
            hake = i1.getMita_a();

        }

        //ハケの割合を%で
        Integer aa = (int) (li.get(0).getAct()*100/hake);
        Integer bb = (int) (li.get(1).getAct()*100/hake);
        Integer cc = (int) (li.get(2).getAct()*100/hake);
        Integer dd = (int) (li.get(3).getAct()*100/hake);
        Integer ee = 100-bb-cc-dd-aa;


        request.setAttribute("a",a);
        request.setAttribute("b",b);
        request.setAttribute("c",f);
        request.setAttribute("d",d);
        request.setAttribute("e", "その他");
        request.setAttribute("aa",aa);
        request.setAttribute("bb",bb);
        request.setAttribute("cc",cc);
        request.setAttribute("dd",dd);
        request.setAttribute("ee", ee);



        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/chart/chart.jsp");
        rd.forward(request, response);
        em.close();

    }

}
