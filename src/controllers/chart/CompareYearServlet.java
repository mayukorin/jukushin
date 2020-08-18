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

import models.company;
import utils.DBUtil;

/**
 * Servlet implementation class CompareYearServlet
 */
@WebServlet("/chart/annualole")
public class CompareYearServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompareYearServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        List<String> s = new ArrayList<String>();
        List<Double> i = new ArrayList<Double>();

        EntityManager em =DBUtil.createEntityManager();

        company c= (company)request.getSession().getAttribute("login_company");

        List<Integer> years = em.createNamedQuery("collect_year",Integer.class).setParameter("company", c).getResultList();
        Iterator<Integer> yi = years.iterator();

        while (yi.hasNext()) {
            Integer year = yi.next();
            Double rate = em.createNamedQuery("average_rate",Double.class).setParameter("year", year).setParameter("company", c).getSingleResult();

            if (rate != null) {
                s.add(String.valueOf(year)+"年");
                i.add(rate*100);
            }
        }

        request.setAttribute("s", s);
        request.setAttribute("i", i);
        RequestDispatcher rd =request.getRequestDispatcher("/WEB-INF/views/chart/year_chart.jsp");
        rd.forward(request,response);

    }

}
