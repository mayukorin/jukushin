package controllers.place;

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

import models.Issue1;
import models.Issue2;
import models.company;
import utils.DBUtil;

/**
 * Servlet implementation class PlaceIndexServlet
 */
@WebServlet("/place/index")
public class PlaceIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlaceIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
      //セッションスコープに新聞のidが格納されていない場合（新聞を新規作成して、初めてindexページへ遷移する時など）は、格納する。
        EntityManager em =DBUtil.createEntityManager();
        Issue1 i1;
        try {
            Integer nes_id =Integer.parseInt(request.getParameter("id"));///newspapaerindexの詳細を見るのリンクのクエリパラメータから得たidを取り出す
            request.getSession().setAttribute("nes_id", nes_id);
        } catch(Exception e) {

        }
        //

        company c =(company)request.getSession().getAttribute("login_company");

        try {
                i1 = em.find(Issue1.class,Integer.parseInt(request.getParameter("id")));
                request.getSession().setAttribute("i1_id",i1.getId());//詳細を見るIssue1のidを格納
        } catch (Exception e) {
            i1 = em.find(Issue1.class,((Integer) request.getSession().getAttribute("i1_id")));
        }

        request.setAttribute("i1", i1);
        request.getSession().setAttribute("month",i1.getNewspaper().getMonth());


        //キャンパスごとの新聞ごとのotherplaceを取り出す。

        List<Issue2> othes_m = em.createNamedQuery("getcanplaces",Issue2.class).setParameter("flag", 0).setParameter("company",c).setParameter("newspaper",i1.getNewspaper()).getResultList();//三田に持ってきた新聞で配布するやつ

        List<Issue2> othes_h = em.createNamedQuery("getcanplaces",Issue2.class).setParameter("flag", 1).setParameter("company",c).setParameter("newspaper",i1.getNewspaper()).getResultList();//日吉に持ってきた新聞で配布するやつ

        List<Issue2> othes_o = em.createNamedQuery("getcanplaces",Issue2.class).setParameter("flag", 2).setParameter("company",c).setParameter("newspaper",i1.getNewspaper()).getResultList();//その他で持ってきた新聞で配布するやつ
        ///



        request.setAttribute("oth", othes_h);
        request.setAttribute("othm", othes_m);
        request.setAttribute("otho", othes_o);

        ////振り分け確定パラメータをjspへ送る

        request.setAttribute("decision", i1.getDecision());


        Integer flag=0;//グラフページに行くか行かないかのパラメータ


        if (i1.getDecision()==1) {
            //////////////振り分け確定後の場合
            List<String> errors = new ArrayList<String>();

            ////三田用と日吉用の新聞目標の合計を計算


            Long m_con=0L;//三田での現時点での想定配布数

            Iterator<Issue2> it = othes_m.iterator();
            while(it.hasNext()) {
                Issue2 oo = it.next();
                if (oo.getAct()==0||oo.getPlace().getName().equals("三田ラック")||oo.getPlace().getName().equals("研究室棟")) {//実際の配布数が0、または三田ラック、日吉ラックの時
                    if (oo.getAct() > oo.getAim()) {
                        m_con+=oo.getAct();
                    } else {
                        m_con+=oo.getAim();
                    }
                } else {

                    m_con+=oo.getAct();
                }

            }

            Long h_con=0L;

            Iterator<Issue2> ith = othes_h.iterator();
            while(ith.hasNext()) {
                Issue2 ooo = ith.next();
                if (ooo.getAct()==0|ooo.getPlace().equals("日吉ラック")|ooo.getPlace().equals("矢上ラック")) {//実際の配布数が0または、配布先が日吉ラック、矢上ラックの時
                    if (ooo.getAct() > ooo.getAim()) {//目標より実際の方が多かった時
                        h_con+=ooo.getAct();

                    } else {
                        h_con+=ooo.getAim();
                    }
                } else {
                    h_con+=ooo.getAct();
                }

            }

            //List<String>errors= ActualValidator.validat(i1.getMita(), i1.getHiyoshi(), i1);



            request.setAttribute("m", m_con);
            request.setAttribute("h", h_con);
            ///発送部数と目標設定数との差を表示する
            Long mm;

            if (i1.getMita()-m_con > 0) {
                mm=i1.getMita()-m_con;
                errors.add("このままの配布見込みだと三田で残部が出てしまいます。\n実際の配布数を登録・更新するには、あと"+mm+"部を三田の目標に割り振る必要があります。");

            } else {
                mm=0L;
            }



            Long hh;

            if (i1.getHiyoshi()-h_con > 0) {
                hh=i1.getHiyoshi()-h_con;
                errors.add("このままの配布見込みだと日吉で残部が出てしまいます。\n実際の配布数を登録・更新するには、あと"+hh+"部を日吉に目標に割り振る必要があります。");

            } else {
                hh=0L;
            }

            request.setAttribute("mm", mm);//三田での残部
            request.setAttribute("hh", hh);//日吉での残部
            if (errors.size() >0) {
                request.setAttribute("error", errors);//残部警告メッセージ
            }
            if (i1.getHiyoshi_a()!=0 && i1.getMita_a()!=0) {
                flag=1;
            }
        }

        ///登録が完了しましたなどのフラッシュメッセージの表示
        if(request.getSession().getAttribute("flush")!=null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }
        ///振り分け確定時のエラーのメッセージの表示
        if(request.getSession().getAttribute("error")!=null) {
            request.setAttribute("errorr", request.getSession().getAttribute("error"));
            request.getSession().removeAttribute("error");
        }

        request.setAttribute("flag",flag);
        em.close();
        //index.jspへ遷移
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/places/index.jsp");
        rd.forward(request, response);


    }

}
