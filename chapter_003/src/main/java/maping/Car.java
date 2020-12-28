package maping;


import manytomany.Author;
import manytomany.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "owner_history")
    private List<Driver> ownerHistory = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "driverId")
    private Driver driver;


    @ManyToOne
    @JoinColumn(name = "engineId")
    private Engine engine;

    public Car(String name) {
        this.name = name;
    }


    public static Car of(String name) {
        return new Car(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Driver> getOwnerHistory() {
        return ownerHistory;
    }

    public void setOwnerHistory(List<Driver> ownerHistory) {
        this.ownerHistory = ownerHistory;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            List<Car> cars = List.of(Car.of("Mercedes"), Car.of("BWM"));

            List<Driver> drivers = List.of(Driver.of("Adam"), Driver.of("Eve"));

            List<Engine> engines = List.of(Engine.of("E1"), Engine.of("E2"));

            drivers.forEach(driver -> session.save(driver));
            engines.forEach(engine -> session.save(engine));

            cars.get(0).setDriver(drivers.get(0));
            cars.get(1).setDriver(drivers.get(1));

            cars.get(0).setEngine(engines.get(0));
            cars.get(1).setEngine(engines.get(1));

            cars.get(0).setOwnerHistory(List.of(drivers.get(1)));
            cars.get(1).setOwnerHistory(List.of(drivers.get(0)));

            cars.forEach(car -> session.save(car));

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}
