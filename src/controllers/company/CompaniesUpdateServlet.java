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
 * Servlet implementation class CompaniesUpdateServlet
 */
@WebServlet("/companies/update")
public class CompaniesUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompaniesUpdateServlet() {
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

            company c = em.find(company.class,((company)request.getSession().getAttribute("login_company")).getId());


            Boolean code_duplicate_check = true;
            if (c.getCode().equals(request.getParameter("code"))) {
                code_duplicate_check = false;

            } else {
                c.setCode(request.getParameter("code"));

            }

            Boolean password_check_flag = true;
            String password = request.getParameter("password");
            if(password == null || password.equals("")) {
                password_check_flag = false;
            } else {
                c.setPassword(
                        EncryptUtil.getPasswordEncrypt(
                                password,
                                (String)this.getServletContext().getAttribute("salt")
                                )
                        );

            }

            c.setName(request.getParameter("name"));


            List<String> errors = CompanyValidator.validate(c, code_duplicate_check, password_check_flag);

            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("company", c);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/companies/edit.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("up_flag", "更新");
                response.sendRedirect(request.getContextPath()+"/logout");
            }
        }
    }
}
