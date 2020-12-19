package services;

import com.google.gson.Gson;
import models.Item;
import models.Wrapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class DBStore implements Store {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static Store store = new DBStore();

    public static Store getInstance() {
        return store;
    }
    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }


    @Override
    public void addTask(Item item) {
        tx(session -> {
            item.setCreated(new Date());
            session.save(item);
            return null;
        });
    }

    @Override
    public List<Item> getItems(boolean filtered) {
        return tx(session ->
                filtered ?
                        session.createQuery("from models.Item where done=false").list()
                        : session.createQuery("from models.Item").list());
    }

    @Override
    public void markTask(Item item) {
        tx(session -> {
            session.createQuery("update models.Item set done = :done where id = :id");
            return null;
        });
    }

}
