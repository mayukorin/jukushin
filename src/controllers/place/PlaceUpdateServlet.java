package controllers.place;

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
import models.Issue2;
import utils.DBUtil;

/**
 * Servlet implementation class PlaceUpdateServlet
 */
@WebServlet("/places/update")
public class PlaceUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlaceUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        String _token =(String) request.getParameter("_token");
        List<String> errors = new ArrayList<String>();
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em =DBUtil.createEntityManager();

            Issue1 i1 = em.find(Issue1.class,((Integer) request.getSession().getAttribute("i1_id")));
            Issue2 i2 = em.find(Issue2.class,((Integer) request.getSession().getAttribute("i2_id")));



            if (i1.getDecision()==0) {
                //振り分け確定前


                //全体の残部を更新する
                i1.setRemain(i1.getRemain()+i2.getAim()-Integer.parseInt(request.getParameter("aim")));
                i2.setDate(request.getParameter("date"));


                if (i2.getCan_flag() != 2) {

                    if (i2.getCan_flag()==Integer.parseInt(request.getParameter("mh"))&&Integer.parseInt(request.getParameter("mh"))==1) {
                        //元々配達場所が日吉のものがそのまま日吉。部数だけ更新
                        i1.setHiyoshi(i1.getHiyoshi()-i2.getAim()+Integer.parseInt(request.getParameter("aim")));
                    } else if (i2.getCan_flag()==Integer.parseInt(request.getParameter("mh"))&&Integer.parseInt(request.getParameter("mh"))==0) {
                        //元々配達場所が三田のものがそのまま三田。部数だけ更新
                        i1.setMita(i1.getMita()-i2.getAim()+Integer.parseInt(request.getParameter("aim")));

                    } else if (i2.getCan_flag()!=Integer.parseInt(request.getParameter("mh"))&&Integer.parseInt(request.getParameter("mh"))==1) {
                        //元々の配達場所が三田のものが日吉になった。
                        i1.setMita(i1.getMita()-i2.getAim());
                        i1.setHiyoshi(i1.getHiyoshi()+Integer.parseInt(request.getParameter("aim")));
                    } else if (i2.getCan_flag()!=Integer.parseInt(request.getParameter("mh"))&&Integer.parseInt(request.getParameter("mh"))==0) {
                        //元々の配達場所が日吉のものが三田になった。
                        i1.setHiyoshi(i1.getHiyoshi()-i2.getAim());
                        i1.setMita(i1.getMita()+Integer.parseInt(request.getParameter("aim")));
                    } else if (i2.getCan_flag() ==  1 &&Integer.parseInt(request.getParameter("mh"))==2) {
                        //元々の配達場所が日吉のものがその他の場所になった
                        i1.setHiyoshi(i1.getHiyoshi()-i2.getAim());
                        i1.setOther(i1.getOther()+Integer.parseInt(request.getParameter("aim")));
                    } else if (i2.getCan_flag() == 0 &&Integer.parseInt(request.getParameter("mh"))==2) {
                        //元々の配達場所が三田のものが日吉になった
                        i1.setMita(i1.getMita()-i2.getAim());
                        i1.setOther(i1.getOther()+Integer.parseInt(request.getParameter("aim")));
                    }
                    i2.setCan_flag(Integer.parseInt(request.getParameter("mh")));
                } else if (i2.getCan_flag() == 2) {
                    //それ以外の配布場所
                    if (Integer.parseInt(request.getParameter("mh"))==2) {
                        //そのままその他の配布場所
                        i1.setOther(i1.getOther()-i2.getAim()+Integer.parseInt(request.getParameter("aim")));
                    } else if (Integer.parseInt(request.getParameter("mh"))==0) {
                        //配布場所が三田になった
                        i1.setOther(i1.getOther()-i2.getAim());
                        i1.setMita(i1.getMita()+Integer.parseInt(request.getParameter("aim")));
                    } else if (Integer.parseInt(request.getParameter("mh"))==1) {
                        //配布場所が日吉になった
                        i1.setOther(i1.getOther()-i2.getAim());
                        i1.setHiyoshi(i1.getHiyoshi()+Integer.parseInt(request.getParameter("aim")));
                    }
                    i2.setCan_flag(Integer.parseInt(request.getParameter("mh")));
                }

                i2.setAim(Long.parseLong(request.getParameter("aim")));
                i2.setAimconst(Long.parseLong(request.getParameter("aim")));//固定の値の目標を変更

            } else {
                //振り分け確定後
                if (i2.getCan_flag() != 2) {
                    Long aim = Long.parseLong(request.getParameter("aim"));
                    i2.setDate(request.getParameter("date"));

                    if (i2.getCan_flag() != Integer.parseInt(request.getParameter("mh")) && i2.getCan_flag() == 0) {
                        //元々三田部室から持ってこようとしていたものを、日吉部室から持ってくるように変更する場合
                        if (i1.cacultate(1) - aim >= 0) {
                            //その分の新聞が日吉部室に残っている場合
                            i2.setCan_flag(1);

                            i2.setAim(aim);
                        } else {
                            errors.add("日吉部室から、その部数の新聞を確保することはできません。");
                        }
                    } else if (i2.getCan_flag() != Integer.parseInt(request.getParameter("mh")) && i2.getCan_flag() == 1) {
                        //元々日吉部室から持ってこようとしていたものを、三田部室から持ってくるように変更する場合
                        if (i1.cacultate(0) - aim >= 0) {
                            //その分の新聞が日吉部室に残っている場合
                            i2.setCan_flag(0);
                            i2.setAim(aim);
                        } else {
                            errors.add("三田部室から、その部数の新聞を確保することはできません。");
                        }
                    } else {
                        //持ってくる部室の場所は変わらない時
                        i2.setAim(aim);
                    }
                }
            }

            i2.setContent(request.getParameter("content"));
            i2.setUpdated_at(new Timestamp(System.currentTimeMillis()));





            if (errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("i2", i2);
                request.setAttribute("errors", errors);
                request.setAttribute("decision", i1.getDecision());



                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/places/edit.jsp");
                rd.forward(request, response);
            } else {
                //データベースを更新


                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "更新が完了しました。");



                response.sendRedirect(request.getContextPath()+"/place/index");
            }
        }
    }
}







