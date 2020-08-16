package controllers.year;

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
 * Servlet implementation class YearIndexServlet
 */
@WebServlet("/index.html")
public class YearIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public YearIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        List<List<Object>> years_rates = new ArrayList<List<Object>>();
        Integer flag = 0;

        EntityManager em = DBUtil.createEntityManager();

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }


        company c = (company) request.getSession().getAttribute("login_company");//ログインしているcompanyを取得

        List<Integer> years = em.createNamedQuery("collect_year",Integer.class).setParameter("company",c).setFirstResult(15*(page-1)).setMaxResults(15).getResultList();//その会社が登録している年
        Iterator<Integer> yi = years.iterator();
        long year_count = (em.createNamedQuery("collect_year",Integer.class).setParameter("company",c).getResultList()).size();

        while(yi.hasNext()) {
            List<Object> year_rate = new ArrayList<Object>();

            Integer year = yi.next();
            year_rate.add(year);

            Double rate = em.createNamedQuery("average_rate",Double.class).setParameter("year",year).setParameter("company",c).getSingleResult();//その会社の、その年の新聞のハケ率
            if (rate != 0) {
                flag = 1;
            }
            year_rate.add(rate);

            years_rates.add(year_rate);//年とその年のハケ率


        }

        em.close();

        request.setAttribute("years_rates", years_rates);
        request.setAttribute("page", page);
        request.setAttribute("year_count", year_count);
        request.setAttribute("flag", flag);
        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));;
            request.getSession().removeAttribute("flush");

        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/years/index.jsp");
        rd.forward(request,response);




    }

}
