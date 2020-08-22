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
import models.Place;
import models.company;
import utils.DBUtil;

/**
 * Servlet implementation class PlaceCreateServlet
 */
@WebServlet("/places/create")
public class PlaceCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlaceCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String _token=(String)request.getParameter("_token");
        if (_token!= null && _token.equals(request.getSession().getId())) {
            EntityManager em =DBUtil.createEntityManager();

            Issue2 i2 = new Issue2();
            Issue1 i1 = em.find(Issue1.class, (Integer)request.getSession().getAttribute("i1_id"));

            List<String> errors = new ArrayList<String>();

          //配布場所が入力されていない場合、エラーリストに格納
            String place_name =request.getParameter("name");
            if (place_name == null || place_name.equals("")) {
                errors.add("配布場所を入力してください");
            }

            //内容
            String content=request.getParameter("content");
            i2.setContent(content);


            //日吉か三田か、その他の場所か、new.jspから受け取ったパラメタを格納
            i2.setCan_flag(Integer.parseInt(request.getParameter("mh")));

            //目標値
            String aim=request.getParameter("aim");
            i2.setAim(Long.parseLong(aim));
            i2.setAimconst(Long.parseLong(aim));//変わらない初期値の目標をを入れる

            if (errors.size() > 0) {

                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("i2", i2);
                request.setAttribute("errors", errors);
                request.setAttribute("decision", i1.getDecision());


                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/places/new.jsp");
                rd.forward(request, response);
            } else {

                //実際のハケ数0に
                i2.setAct(0L);

                //時間
                Timestamp time = new Timestamp(System.currentTimeMillis());
                i2.setCreated_at(time);
                i2.setUpdated_at(time);

                //Company
                company c =(company)request.getSession().getAttribute("login_company");
                i2.setCompany(c);

                //Newspaper

                i2.setNewspaper(i1.getNewspaper());

                List<Place> places = em.createNamedQuery("place_exit",Place.class).setParameter("name", place_name).getResultList();//入力した名前のPlaceがすでに存在するか

                if (places.size() == 0) {
                    //存在しない場合、placeも新しく作成
                    Place p = new Place();
                    p.setDefault_vo(0L);
                    p.setName(place_name);

                    //次に、Issue2を作成
                    i2.setPlace(p);

                    em.getTransaction().begin();
                    em.persist(p);
                    em.persist(i2);
                    em.getTransaction().commit();

                } else {
                    i2.setPlace(places.get(0));//同じ名前のPlacesは一つだけのはず

                    em.getTransaction().begin();
                    em.persist(i2);
                    em.getTransaction().commit();

                }

                if (i1.getDecision() == 0) {
                    if (i2.getCan_flag() == 1) {
                        //日吉の振り分け追加
                        i1.setHiyoshi(i1.getHiyoshi()+i2.getAim());
                    } else if (i2.getCan_flag() == 0) {
                        //三田の振り分け追加
                        i1.setMita(i1.getMita()+i2.getAim());
                    } else {
                        //その他の場所を追加
                        i1.setOther(i1.getOther()+i2.getAim());
                    }

                    i1.setRemain(i1.getRemain()-i2.getAim());//全体の新聞の残部を更新（あとどれだけ振り分けなければいけないか）

                    em.getTransaction().begin();
                    em.getTransaction().commit();
                }


                em.close();
                request.getSession().setAttribute("flush","登録が完了しました。");

                response.sendRedirect(request.getContextPath()+"/place/index");
            }
        }
    }
}





