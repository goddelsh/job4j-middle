package many;

import javax.persistence.*;

@Entity
@Table(name = "carsold")
public class CarOld {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public CarOld() {
    }

    public CarOld(String name) {
        this.name = name;
    }

    public static CarOld of(String name) {
        return new CarOld(name);
    }

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
}
