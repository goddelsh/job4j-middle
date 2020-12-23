package models;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;


    public static Category of(String name) {
        Category category = new Category();
        category.name = name;
        return category;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        return id == category.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


//    public static void main(String[] args) {
//        List<Category> list = new ArrayList<>();
//        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure().build();
//        try {
//            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
//            Session session = sf.openSession();
//            session.beginTransaction();
//
//            List.of(Category.of("Работа"),
//                    Category.of("Продукты"),
//                    Category.of("Хобби"),
//                    Category.of("Прочее")).forEach(category -> session.save(category));
//
//            session.getTransaction().commit();
//            session.close();
//        }  catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            StandardServiceRegistryBuilder.destroy(registry);
//        }
//    }
}