package models;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import utils.DBUtil;

@Entity
@NamedQueries({
    @NamedQuery(
            name="getcanplaces",
            query="select i2 from Issue2 as i2 where i2.can_flag = :flag and i2.company = :company and i2.newspaper = :newspaper"),
    @NamedQuery(
            name="top5places",
            query="SELECT i2 FROM Issue2 AS i2 WHERE i2.can_flag=:can_flag and i2.company = :company and i2.newspaper = :newspaper ORDER BY i2.act DESC"),



})
@Table(name="issue2")
public class Issue2 {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "company")
    private company company;

    @ManyToOne
    @JoinColumn(name="newspaper")
    private Newspaper newspaper;

    @ManyToOne
    @JoinColumn(name="place")
    private Place place;

    @Column(name="created_at",nullable=false)
    private Timestamp created_at;

    @Column(name="updated_at",nullable=false)
    private Timestamp updated_at;

    @Column(name="can_flag",nullable=false)
    private Integer can_flag;//どこに届いた新聞を使うか。三田だと0、日吉だと1、その他だと2。

    @Column(name="aim")
    private Long aim;

    @Column(name="aimconst")
    private Long aimconst;

    @Column(name="act")
    private Long act;

    @Lob
    @Column(name="content")
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public company getCompany() {
        return company;
    }

    public void setCompany(company company) {
        this.company = company;
    }

    public Newspaper getNewspaper() {
        return newspaper;
    }

    public void setNewspaper(Newspaper newspaper) {
        this.newspaper = newspaper;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getCan_flag() {
        return can_flag;
    }

    public void setCan_flag(Integer can_flag) {
        this.can_flag = can_flag;
    }

    public Long getAim() {
        return aim;
    }

    public void setAim(Long aim) {
        this.aim = aim;
    }

    public Long getAimconst() {
        return aimconst;
    }

    public void setAimconst(Long aimconst) {
        this.aimconst = aimconst;
    }

    public Long getAct() {
        return act;
    }

    public void setAct(Long act) {
        this.act = act;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long cacultate(Integer flag,Issue1 i1) {
        EntityManager em = DBUtil.createEntityManager();

        List<Issue2> othes_m = em.createNamedQuery("getcanplaces",Issue2.class).setParameter("flag", flag).setParameter("company",this.getCompany()).setParameter("newspapaer",this.getNewspaper()).getResultList();
        Long m_con=0L;
        Long mm=0L;

        Iterator<Issue2> it = othes_m.iterator();
        while(it.hasNext()) {
            Issue2 oo = it.next();

            if (flag==0) {

                if (oo.getAct()==0|oo.getPlace().getName().equals("三田ラック")|oo.getPlace().getName().equals("研究室棟")) {
                    m_con+=oo.getAim();
                } else {
                    m_con+=oo.getAct();
                }
            } else if (flag==1) {
                if (oo.getAct()==0|oo.getPlace().equals("日吉ラック")|oo.getPlace().equals("矢上ラック")) {
                    m_con+=oo.getAim();
                } else {
                    m_con+=oo.getAct();
                }
            }

        }

        if (flag ==0) {
            mm=i1.getMita()-m_con;

            if (mm <0) {
                mm=0L;

            }

        } else if (flag==1) {
            mm=i1.getHiyoshi()-m_con;

            if (mm <0) {
                mm=0L;

            }
        }


        return mm;
    }





}
