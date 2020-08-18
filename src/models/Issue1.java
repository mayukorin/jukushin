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
            name = "average_rate",
            query = "select avg(i.rate) from Issue1 as i where i.newspaper.year = :year and i.company = :company and i.rate > 0"
            ),
    @NamedQuery(
            name="collect_year",
            query = "select distinct i.newspaper.year from Issue1 as i where i.company = :company order by i.newspaper.year"
            ),
    @NamedQuery(
            name="selected_year",
            query = "select i from Issue1 as i where i.company = :company and i.newspaper.year = :year"
            ),
    @NamedQuery(
            name="getmonthnews",
            query = "select i from Issue1 as i where i.company = :company and i.newspaper.month = :month and i.decision = :decision order by i.newspaper.year"
                  )

})
@Table(name="issue1")
public class Issue1 {

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

    @Column(name="volumn")
    private Long volumn;//発行部数

    @Lob
    @Column(name="content")
    private String content;//コメント

    @Column(name="created_at",nullable=false)
    private Timestamp created_at;

    @Column(name="updated_at",nullable=false)
    private Timestamp updated_at;

    @Column(name="hake")
    private Long hake;//ハケ数

    @Column(name="rate")
    private Double rate;//ハケ率

    @Column(name="remain")
    private Long remain;//残部

    @Column(name="remainact")
    private Long remainact;//実際の残部

    @Column(name="hiyoshi")
    private Long hiyoshi;//日吉への割り当て

    @Column(name="mita")
    private Long mita;//三田への割り当て

    @Column(name="other")
    private Long other;//他の場所の割り当て

    @Column(name="hiyoshi_a")
    private Long hiyoshi_a;//日吉のハケ数

    @Column(name="mita_a")
    private Long mita_a;//三田のハケ数

    @Column(name="other_a")
    private Long other_a;//それ以外のハケ数

    @Column(name="decision")
    private Integer decision;

    @Column(name="vo_decision")
    private Integer vo_decision;//発行部数が決まっているか

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

    public Long getVolumn() {
        return volumn;
    }

    public void setVolumn(Long volumn) {
        this.volumn = volumn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Long getHake() {
        return hake;
    }

    public void setHake(Long hake) {
        this.hake = hake;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Long getRemain() {
        return remain;
    }

    public void setRemain(Long remain) {
        this.remain = remain;
    }

    public Long getRemainact() {
        return remainact;
    }

    public void setRemainact(Long remainact) {
        this.remainact = remainact;
    }

    public Long getHiyoshi() {
        return hiyoshi;
    }

    public void setHiyoshi(Long hiyoshi) {
        this.hiyoshi = hiyoshi;
    }

    public Long getMita() {
        return mita;
    }

    public Long getOther() {
        return other;
    }

    public void setOther(Long other) {
        this.other = other;
    }

    public Long getOther_a() {
        return other_a;
    }

    public void setOther_a(Long other_a) {
        this.other_a = other_a;
    }

    public Integer getVo_decision() {
        return vo_decision;
    }

    public void setVo_decision(Integer vo_decision) {
        this.vo_decision = vo_decision;
    }

    public void setMita(Long mita) {
        this.mita = mita;
    }

    public Long getHiyoshi_a() {
        return hiyoshi_a;
    }

    public void setHiyoshi_a(Long hiyoshi_a) {
        this.hiyoshi_a = hiyoshi_a;
    }

    public Long getMita_a() {
        return mita_a;
    }

    public void setMita_a(Long mita_a) {
        this.mita_a = mita_a;
    }

    public Integer getDecision() {
        return decision;
    }

    public void setDecision(Integer decision) {
        this.decision = decision;
    }

    public Long cacultate(Integer flag) {
        EntityManager em = DBUtil.createEntityManager();

        List<Issue2> othes_m = em.createNamedQuery("getcanplaces",Issue2.class).setParameter("flag", flag).setParameter("company",this.getCompany()).setParameter("newspaper",this.getNewspaper()).getResultList();
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
            mm=this.getMita()-m_con;

            if (mm <0) {
                mm=0L;

            }

        } else if (flag==1) {
            mm=this.getHiyoshi()-m_con;

            if (mm <0) {
                mm=0L;

            }
        }


        return mm;
    }










}
