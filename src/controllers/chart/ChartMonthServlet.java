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
 * Servlet implementation class ChartMonthServlet
 */
@WebServlet("/chart/ore")
public class ChartMonthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChartMonthServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em =DBUtil.createEntityManager();

        company c= (company)request.getSession().getAttribute("login_company")
                ;

        Integer y=Integer.parseInt(request.getParameter("id"));//その年のグラフ

        List<Issue1> i1s = em.createNamedQuery("selected_year",Issue1.class).setParameter("company",c).setParameter("year",y).getResultList();


        if (i1s.size() > 0) {



            List<String> s = new ArrayList<String>();
            List<Double> i = new ArrayList<Double>();


            Iterator<Issue1> it =i1s.iterator();
            while (it.hasNext()) {
                Issue1 n = it.next();
                s.add(String.valueOf(n.getNewspaper().getMonth())+"月");
                i.add(n.getRate()*100);

            }


            request.setAttribute("s", s);
            request.setAttribute("i", i);
            RequestDispatcher rd =request.getRequestDispatcher("/WEB-INF/views/chart/month_chart.jsp");
            rd.forward(request,response);
        } else {
            //データがなかった時
            request.setAttribute("flush", "グラフにするデータがありません。");
            RequestDispatcher rd =request.getRequestDispatcher("/WEB-INF/views/newspapers/index.jsp");
            rd.forward(request,response);
        }
    }
}
