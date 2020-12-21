package manytomany;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}) //cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();


    public static Author of(String name) {
        return new Author(name);
    }

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
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

    public static void main(String[] agrgs) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            List<Book> books = List.of(Book.of("Сами боги"), Book.of("Стальные пещеры"), Book.of("Хроники Норби"), Book.of("Последний бессмертный"));
            List<Author> authors = List.of(Author.of("Айзек Азимов"), Author.of("Джаннет Азимова"));
            authors.get(0).setBooks(books.subList(0, 3));
            authors.get(1).setBooks(books.subList(2, 4));

       //     authors.forEach(author -> session.save(author));

            Author author = session.get(Author.class, 2);
            session.remove(author);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }


}
