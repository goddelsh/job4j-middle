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
    private List<Car> cars = new ArrayList<>();

    public Brand(String name) {
        this.name = name;
    }

    public Brand() {
    }

    public void addCar(Car car) {
        cars.add(car);
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

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }


    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            List<Car> mersCars = List.of(Car.of("S100"), Car.of("L30"), Car.of("A50"), Car.of("S200"));
            List<Car> bmwCars = List.of(Car.of("C330X"), Car.of("M1511"), Car.of("LK-17"));
            mersCars.forEach(car -> session.save(car));
            bmwCars.forEach(car -> session.save(car));

            List<Brand> brands = List.of(Brand.of("Mercedes"), Brand.of("BMW"));

            mersCars.forEach(car -> brands.get(0).addCar(session.get(Car.class, car.getId())));
            bmwCars.forEach(car -> brands.get(1).addCar(session.get(Car.class, car.getId())));

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
