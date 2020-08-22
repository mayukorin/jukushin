package controllers.company;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.company;

/**
 * Servlet implementation class CompaniesEditServlet
 */
@WebServlet("/companies/edit")
public class CompaniesEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompaniesEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        company c = (company) request.getSession().getAttribute("login_company");//ログインしているcompany情報

        request.setAttribute("_token", request.getSession().getId());
        request.setAttribute("company",c);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/companies/edit.jsp");
        rd.forward(request, response);
    }

}
