package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
    @NamedQuery(
            name = "default_place",
            query = "select p from Place as p where p.default_vo > 0"
            ),//デフォルトで配達部数が決まっているのだけ取り出す

})
@Table(name="place")
public class Place {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="default_vo")//デフォルトの配達部数
    private Long default_vo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDefault_vo() {
        return default_vo;
    }

    public void setDefault_vo(Long default_vo) {
        this.default_vo = default_vo;
    }


}
