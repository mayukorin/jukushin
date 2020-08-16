package controllers.newspaper;

import java.io.IOException;
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
 * Servlet implementation class NewspapersIndex
 */
@WebServlet("/newspapers/index")
public class NewspapersIndex extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewspapersIndex() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        Integer year;

        EntityManager em =DBUtil.createEntityManager();

        company c = (company) request.getSession().getAttribute("login_company");//ログインしているcompany情報

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page=1;
        }

        try {
            year = Integer.parseInt(request.getParameter("year"));
            request.getSession().setAttribute("year", year);//今見ている年度
        } catch(Exception e) {

        }


        List<Issue1> i1 = em.createNamedQuery("selected_year",Issue1.class).setParameter("company",c).setParameter("year",request.getSession().getAttribute("year")).setFirstResult(6*(page-1)).setMaxResults(6).getResultList();//その会社の、その年のissue1



        Iterator<Issue1> it = i1.iterator();

        Integer flag=0;

        while (it.hasNext()) {
            Issue1 i = it.next();
            System.out.println(i.getId());

            if (i.getDecision()==1) {
                flag=1;
                break;
            }
        }




        long issue1_count=(em.createNamedQuery("selected_year",Issue1.class).setParameter("company",c).setParameter("year",request.getSession().getAttribute("year")).getResultList()).size();

        em.close();

        request.setAttribute("i1",i1);
        request.setAttribute("newspaper_count",issue1_count);
        request.setAttribute("page", page);
        request.setAttribute("flag", flag);



        if(request.getSession().getAttribute("flush")!= null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");


        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/newspapers/index.jsp");

        rd.forward(request, response);
    }

}
