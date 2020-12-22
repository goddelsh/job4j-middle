package lazy;

import javax.persistence.*;

@Entity
@Table(name = "cars_lazy")
public class CarLazy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandLazy brand;

    public CarLazy() {
    }

    public CarLazy(String name) {
        this.name = name;
    }

    public static CarLazy of(String name) {
        return new CarLazy(name);
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

    public BrandLazy getBrand() {
        return brand;
    }

    public void setBrand(BrandLazy brand) {
        this.brand = brand;
    }
}
