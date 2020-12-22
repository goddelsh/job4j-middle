package lazy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brands_lazy")
public class BrandLazy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "brand")
    private List<CarLazy> cars = new ArrayList<>();

    public BrandLazy(String name) {
        this.name = name;
    }

    public BrandLazy() {
    }


    public static BrandLazy of(String name) {
        return new BrandLazy(name);
    }

    public void addCarLazy(CarLazy car) {
        this.cars.add(car);
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




    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        List<BrandLazy> result = new ArrayList<>();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            List<CarLazy> mersCars = List.of(CarLazy.of("S100"), CarLazy.of("L30"), CarLazy.of("A50"), CarLazy.of("S200"));
            List<CarLazy> bmwCars = List.of(CarLazy.of("C330X"), CarLazy.of("M1511"), CarLazy.of("LK-17"));

            List<BrandLazy> brands = List.of(BrandLazy.of("Mercedes"), BrandLazy.of("BMW"));
            brands.forEach(brand -> session.save(brand));

            mersCars.forEach(car -> car.setBrand(brands.get(0)));
            bmwCars.forEach(car -> car.setBrand(brands.get(1)));

            mersCars.forEach(car -> session.save(car));
            bmwCars.forEach(car -> session.save(car));

            result = session.createQuery("select distinct b from BrandLazy b join fetch b.cars").list();

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        result.forEach(brand ->
                brand.cars.forEach(car ->
                        System.out.println(brand.getName() + " " + car.getName())));
    }

    public List<CarLazy> getCars() {
        return cars;
    }

    public void setCars(List<CarLazy> cars) {
        this.cars = cars;
    }
}
