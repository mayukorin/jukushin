package controllers.place;

import java.io.IOException;
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
import models.Issue2;
import utils.DBUtil;

/**
 * Servlet implementation class PlaceUpdateActual
 */
@WebServlet("/places/updata")
public class PlaceUpdateActual extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlaceUpdateActual() {
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



            Issue2 i2 = em.find(Issue2.class, (Integer)request.getSession().getAttribute("i2_id"));


            i2.setContent(request.getParameter("content"));

            String act_str=request.getParameter("act");

            if (act_str==null | act_str.equals("")) {
                em.close();
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("i2", i2);
                List<String> errors= new ArrayList<String>();
                errors.add("実質配布数を入力してください。");
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/places/edita.jsp");
                rd.forward(request,response);
            } else {


                Issue1 i1 = em.find(Issue1.class, (Integer)request.getSession().getAttribute("i1_id"));
                if (i2.getCan_flag()==0) {
                    //三田

                    Long m_a;
                    if (i2.getAct() !=0) {
                        //もともと実際の配布数が入力されている場合
                        m_a=i1.getMita_a()-i2.getAct()+Integer.parseInt(act_str);
                    } else {
                        m_a=i1.getMita_a()+Integer.parseInt(act_str);
                    }
                    i1.setMita_a(m_a);
                } else {
                    Long h_a;
                    if (i2.getAct() !=0) {
                        h_a=i1.getHiyoshi_a()-i2.getAct()+Integer.parseInt(act_str);
                    } else {
                        h_a=i1.getHiyoshi_a()+Integer.parseInt(act_str);
                    }
                    i1.setHiyoshi_a(h_a);
                }
                i2.setAct(Long.parseLong(act_str));//otherplaceの方に、実際の数を入れる。

                i1.setRemain(i1.getRemain()-i2.getAct());///実際にはけた分だけ全体からあまりを引く。
                i1.setHake(i1.getHake()+i2.getAct());//新聞のハケ数の方にカウントさせる。
                i1.setRate((double)i1.getHake()/i1.getVolumn());

                em.getTransaction().begin();
                em.getTransaction().commit();


                em.close();

                request.getSession().setAttribute("flush", "更新が完了しました");

                response.sendRedirect(request.getContextPath()+"/place/index");
            }
        }
    }
}
