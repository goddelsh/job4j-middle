package utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class InitTables {

//    static public void createTable(){
//        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure().build();
//        try {
//            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
//            Session session = sf.openSession();
//            session.beginTransaction();
//
//            List<Book> books = List.of(Book.of("Сами боги"), Book.of("Стальные пещеры"), Book.of("Хроники Норби"), Book.of("Последний бессмертный"));
//            List<Author> authors = List.of(Author.of("Айзек Азимов"), Author.of("Джаннет Азимова"));
//            authors.get(0).setBooks(books.subList(0, 3));
//            authors.get(1).setBooks(books.subList(2, 4));
//
//            //     authors.forEach(author -> session.save(author));
//
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
