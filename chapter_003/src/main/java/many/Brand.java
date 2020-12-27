package many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarOld> carOlds = new ArrayList<>();

    public Brand(String name) {
        this.name = name;
    }

    public Brand() {
    }

    public void addCar(CarOld carOld) {
        carOlds.add(carOld);
    }

    public static Brand of(String name) {
        return new Brand(name);
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

    public List<CarOld> getCars() {
        return carOlds;
    }

    public void setCars(List<CarOld> carOlds) {
        this.carOlds = carOlds;
    }


    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            List<CarOld> mersCarOlds = List.of(CarOld.of("S100"), CarOld.of("L30"), CarOld.of("A50"), CarOld.of("S200"));
            List<CarOld> bmwCarOlds = List.of(CarOld.of("C330X"), CarOld.of("M1511"), CarOld.of("LK-17"));
            mersCarOlds.forEach(carOld -> session.save(carOld));
            bmwCarOlds.forEach(carOld -> session.save(carOld));

            List<Brand> brands = List.of(Brand.of("Mercedes"), Brand.of("BMW"));

            mersCarOlds.forEach(carOld -> brands.get(0).addCar(session.get(CarOld.class, carOld.getId())));
            bmwCarOlds.forEach(carOld -> brands.get(1).addCar(session.get(CarOld.class, carOld.getId())));

            brands.forEach(brand -> session.save(brand));

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
