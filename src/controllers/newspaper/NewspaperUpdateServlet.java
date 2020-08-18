package controllers.newspaper;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Issue1;
import utils.DBUtil;

/**
 * Servlet implementation class NewspaperUpdateServlet
 */
@WebServlet("/newspaper/update")
public class NewspaperUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewspaperUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        String _token=(String)request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Issue1 i1 = em.find(Issue1.class, (Integer)request.getSession().getAttribute("i1_id"));

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            i1.setUpdated_at(currentTime);

            List<String> errors =new ArrayList<String>();

            String vo_str = request.getParameter("volumn");
            if (vo_str==null | vo_str.equals("")) {

                errors.add("発行部数を入力してください。");

            }

            Long vo = Long.parseLong(vo_str);
            Long con = i1.getHiyoshi()+i1.getMita()+i1.getOther();


            if (con>vo) {
                //今登録している目標よりも低い発行部数を設定したら
                errors.add(con+"部以上を入力してください。");
            }

            if (errors.size() >0) {
                request.setAttribute("i1", i1);
                request.setAttribute("errors", errors);
                RequestDispatcher rd= request.getRequestDispatcher("/WEB-INF/views/newspapers/edit.jsp");
                rd.forward(request, response);
            } else {

                i1.setVolumn(vo);
                i1.setRemain(vo-i1.getOther()-i1.getMita()-i1.getHiyoshi());


                em.getTransaction().begin();
                em.getTransaction().commit();
                request.getSession().setAttribute("flush", "発行部数の更新が完了しました。");
                em.close();

                response.sendRedirect(request.getContextPath()+"/newspapers/index");
            }
        }
    }
}