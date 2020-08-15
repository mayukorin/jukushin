package controllers.company;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.company;
import models.validator.CompanyValidator;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class CompaniesCreateServlet
 */
@WebServlet("/companies/create")
public class CompaniesCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompaniesCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String _token = (String)request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            company c = new company();

            c.setCode(request.getParameter("code"));
            c.setName(request.getParameter("name"));
            c.setPassword(
                    EncryptUtil.getPasswordEncrypt(request.getParameter("password"), (String)this.getServletContext().getAttribute("salt")));

            List<String>errors = CompanyValidator.validate(c, true, true);

            if (errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("company", c);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/companies/new.jsp");
                rd.forward(request, response);

            } else {
                em.getTransaction().begin();
                em.persist(c);
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "登録が完了しました。");

                response.sendRedirect(request.getContextPath() + "/login");
            }
        }
    }
}

