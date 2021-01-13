package hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int experience;

    private int salary;

    public static Candidate of(String name, int experience, int salary) {
        Candidate candidate = new Candidate();
        candidate.name = name;
        candidate.experience = experience;
        candidate.salary = salary;
        return candidate;
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

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Student: id=%s, name=%s, age=%s, city=%s", id, name, experience, salary);
    }

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate one = Candidate.of("Alex", 5, 50000);
            Candidate two = Candidate.of("Nikolay", 2, 35000);
            Candidate three = Candidate.of("Nikita", 4, 40000);

            session.save(one);
            session.save(two);
            session.save(three);

            session.getTransaction().commit();

            System.out.println("опыт больше 2х лет");
            for (Object candidate : session.createQuery("from hql.Candidate c where c.experience > 2").list()) {
                System.out.println(candidate);
            }
            System.out.println("зарплата больше 40000");
            for (Object candidate : session.createQuery("from hql.Candidate c where c.salary > 40000").list()) {
                System.out.println(candidate);
            }

            System.out.println("Имя начинается на N");
            for (Object candidate : session.createQuery("from hql.Candidate c where c.name like 'N%' ").list()) {
                System.out.println(candidate);
            }

            session.beginTransaction();
            two = (Candidate) session.createQuery("from hql.Candidate c where c.experience = 2").list().stream().findFirst().orElse(null);
            two.setExperience(3);
            session.createQuery("update hql.Candidate c set c.experience = :experience where c.id = :id")
                    .setParameter("experience", 3).setParameter("id", two.id).executeUpdate();
            session.getTransaction().commit();

            System.out.println("опыт больше 2х лет");
            for (Object candidate : session.createQuery("from hql.Candidate c where c.experience > 2").list()) {
                System.out.println(candidate);
            }

            session.beginTransaction();
            three = (Candidate) session.createQuery("from hql.Candidate c where c.name = 'Nikita'").list().stream().findFirst().orElse(null);
            session.createQuery("delete from hql.Candidate c where c.id = :id")
                    .setParameter("id", three.id).executeUpdate();
            session.getTransaction().commit();

            System.out.println("Имя начинается на N");
            for (Object candidate : session.createQuery("from hql.Candidate c where c.name like 'N%' ").list()) {
                System.out.println(candidate);
            }

            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}