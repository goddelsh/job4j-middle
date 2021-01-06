package models;


import javax.persistence.*;

@Entity
@Table(name = "user_contacts")
public class SellerContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
