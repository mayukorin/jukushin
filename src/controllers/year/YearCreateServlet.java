package controllers.year;

import java.io.IOException;
import java.sql.Timestamp;
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
import models.Newspaper;
import models.Place;
import models.company;
import models.validator.YearValidator;
import utils.DBUtil;

/**
 * Servlet implementation class YearCreateServlet
 */
@WebServlet("/years/create")
public class YearCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public YearCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        String _token = (String)request.getParameter("_token");
        if (_token!=null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();


            company c =(company)request.getSession().getAttribute("login_company");

            List<String> errors = new ArrayList<String>();


            String year_str = request.getParameter("annual_year");
            if (year_str == null || year_str.equals("")) {
                String year_error="年度を入力してください。";

                errors.add(year_error);

            } else {



                String error2 = YearValidator._validateDouble(c,Integer.parseInt(year_str));//入力した年がダブってないか確認
                if (!error2.equals("") || error2== null) {
                    errors.add(error2);
                }



            }



            if (errors.size() >0) {

                em.close();


                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("year",Integer.parseInt(year_str));
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/years/news.jsp");
                rd.forward(request, response);


            } else {
                Integer year = Integer.parseInt(year_str);//入力した年
                List<Newspaper> years = em.createNamedQuery("year_exit",Newspaper.class).setParameter("year", year).getResultList();//その年のnewspaperが存在するのか

                List<Place> df = em.createNamedQuery("default_place",Place.class).getResultList();//デフォルトで配達部数が決まっている場所


                Timestamp currentTime = new Timestamp(System.currentTimeMillis());

                if (years.size() > 0) {
                    //もし存在したら、issueだけ新しく作成
                    Iterator<Newspaper> yi = years.iterator();
                    while (yi.hasNext()) {
                        Newspaper y = yi.next();
                        //issue1を作成///////////////////
                        Issue1 i1 = new Issue1();
                        i1.setCompany(c);
                        i1.setNewspaper(y);
                        i1.setRate(0.0);
                        i1.setCreated_at(currentTime);
                        i1.setUpdated_at(currentTime);
                        i1.setDecision(0);
                        i1.setVo_decision(0);//まだ発行部数を決めていない

                        //////////////////////////////////


                        em.getTransaction().begin();
                        em.persist(i1);
                        em.getTransaction().commit();

                        //デフォルトのissue2を作成///////////////

                       for (int i=0;i<df.size();i++) {

                           Place pf = df.get(i);

                           Issue2 i2 = new Issue2();
                           i2.setCompany(c);
                           i2.setNewspaper(y);
                           i2.setPlace(pf);
                           i2.setCreated_at(currentTime);
                           i2.setUpdated_at(currentTime);
                           i2.setAim(pf.getDefault_vo());
                           if (1<= pf.getId() && pf.getId()<=5) {
                               //日吉に新聞を持っていく
                               i2.setCan_flag(1);
                           } else if (6<= pf.getId() && pf.getId() <= 9) {
                               //三田に新聞を持っていく
                               i2.setCan_flag(0);
                           } else {
                               //それ以外の場所に新聞を持っていく
                               i2.setCan_flag(2);
                           }
                           em.getTransaction().begin();
                           em.persist(i2);
                           em.getTransaction().commit();

                        }

                    }


                } else {
                    //もし存在しなかったら、issueとnewspaperも作成


                    for (int i=1;i<13;i++) {

                        //まず、newspaperを作成////////////
                        Newspaper n = new Newspaper();
                        n.setMonth(i);
                        n.setYear(year);

                        em.getTransaction().begin();
                        em.persist(n);
                        em.getTransaction().commit();
                        //////////////////////////////////

                        //issue1を作成//////////////////////
                        Issue1 i1 = new Issue1();
                        i1.setCompany(c);
                        i1.setNewspaper(n);
                        i1.setRate(0.0);
                        i1.setCreated_at(currentTime);
                        i1.setUpdated_at(currentTime);
                        i1.setDecision(0);
                        i1.setVo_decision(0);//まだ発行部数を決めていない


                        em.getTransaction().begin();
                        em.persist(i1);
                        em.getTransaction().commit();
                        ////////////////////////////////////

                        for (int k=0;k<df.size();k++) {

                            Place pf = df.get(k);
                            Issue2 i2 = new Issue2();
                            i2.setCompany(c);
                            i2.setNewspaper(n);
                            i2.setPlace(pf);
                            i2.setCreated_at(currentTime);
                            i2.setUpdated_at(currentTime);
                            i2.setAim(pf.getDefault_vo());
                            if (1<= pf.getId() && pf.getId()<=5) {
                                //日吉に新聞を持っていく
                                i2.setCan_flag(1);
                            } else if (6<= pf.getId() && pf.getId() <= 9) {
                                //三田に新聞を持っていく
                                i2.setCan_flag(0);
                            } else {
                                //それ以外の場所に新聞を持っていく
                                i2.setCan_flag(2);
                            }
                            em.getTransaction().begin();
                            em.persist(i2);
                            em.getTransaction().commit();

                         }


                    }

                }

                em.close();
                request.getSession().setAttribute("flush", "登録が完了しました。");

                response.sendRedirect(request.getContextPath() + "/index.html");
            }
        }
    }
}


