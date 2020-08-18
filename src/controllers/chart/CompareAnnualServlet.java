package controllers.chart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Issue1;
import models.company;
import utils.DBUtil;

/**
 * Servlet implementation class CompareAnnualServlet
 */
@WebServlet("/chart/bo")
public class CompareAnnualServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompareAnnualServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();

        company c= (company)request.getSession().getAttribute("login_company");

        Issue1 i1 = em.find(Issue1.class, (Integer)request.getSession().getAttribute("i1_id"));

        Integer mo = i1.getNewspaper().getMonth();



        List<Issue1> nl = em.createNamedQuery("getmonthnews",Issue1.class).setParameter("month", mo).setParameter("company", c).setParameter("decision", 1).getResultList();//その年の新聞を取り出す

        if (nl.size() > 0) {

            List<String> s = new ArrayList<String>();
            List<Double> i = new ArrayList<Double>();


            Iterator<Issue1> it =nl.iterator();
            while (it.hasNext()) {
                Issue1 a = it.next();
                s.add(String.valueOf(a.getNewspaper().getYear()+"年"));
                i.add(a.getRate()*100);

            }

            request.setAttribute("mo", mo);
            request.setAttribute("s", s);
            request.setAttribute("i", i);
            RequestDispatcher rd =request.getRequestDispatcher("/WEB-INF/views/chart/bou.jsp");
            rd.forward(request,response);

        } else {
          //データがなかった時
            request.setAttribute("flush", "グラフにするデータがありません。");
            RequestDispatcher rd =request.getRequestDispatcher("/WEB-INF/views/places/index.jsp");
            rd.forward(request,response);
        }
    }
}

