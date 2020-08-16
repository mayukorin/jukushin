package models.validator;

import java.util.List;

import javax.persistence.EntityManager;

import models.Issue1;
import models.company;
import utils.DBUtil;

public class YearValidator {

    public static String _validateDouble(company c,Integer year) {
        EntityManager em = DBUtil.createEntityManager();

        List<Issue1> is = em.createNamedQuery("selected_year",Issue1.class).setParameter("company", c).setParameter("year", year).getResultList();//その会社の入力した年のissue1があるか

        em.close();
        if (is.size() > 0) {
            return "入力された年度の情報はすでに存在しています。";
        }
        return "";

    }

}
