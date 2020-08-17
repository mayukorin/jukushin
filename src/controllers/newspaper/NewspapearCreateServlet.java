package controllers.newspaper;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Issue1;
import models.validator.NewpaperValidator;
import utils.DBUtil;

/**
 * Servlet implementation class NewspapearCreateServlet
 */
@WebServlet("/newspapers/create")
public class NewspapearCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewspapearCreateServlet() {
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

            Issue1 i1 = em.find(Issue1.class, (Integer)(request.getSession().getAttribute("i1_id")));//発行部数の情報を加えたい

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            i1.setUpdated_at(currentTime);



            String vo_str = request.getParameter("volumn");
            List<String>errors=NewpaperValidator.validate(vo_str);



            if (errors.size() >0) {
                //入力内容にエラーがあったら

                request.setAttribute("i1", i1);
                request.setAttribute("errors", errors);
                RequestDispatcher rd= request.getRequestDispatcher("/WEB-INF/views/newspapers/new.jsp");
                rd.forward(request, response);
            } else {

                i1.setVo_decision(1);//発行部数を決めた

                i1.setVolumn(Long.parseLong(vo_str));//発行部数
                i1.setHiyoshi((long) 351);//デフォルトの日吉
                i1.setMita((long)1725);//デフォルトの三田
                i1.setOther((long)450);//デフォルトのその他


                i1.setRemain(i1.getVolumn()-i1.getOther()-i1.getMita()-i1.getHiyoshi());


                em.getTransaction().begin();
                em.getTransaction().commit();
                request.getSession().setAttribute("flush", "発行部数を登録しました。");
                em.close();

                response.sendRedirect(request.getContextPath()+"/newspapers/index");
            }
        }
    }
}



