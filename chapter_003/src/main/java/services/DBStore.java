package services;

import models.Item;
import models.User;
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
                filtered
                        ? session.createQuery("from models.Item where done = false").list()
                        : session.createQuery("from models.Item").list());
    }

    @Override
    public void markTask(Item item) {
        tx(session -> {
            session.createQuery("update models.Item set done = :done where id = :id")
                    .setParameter("done", item.isDone())
                    .setParameter("id", item.getId()).executeUpdate();
            return null;
        });
    }

    @Override
    public User getUser(User user) {
        return tx(session -> (User) session.createQuery("from models.User where email=:email and password=:password")
                .setParameter("email", user.getEmail())
                .setParameter("password", user.getPassword())
                .list().stream()
                .findFirst().orElse(null));
    }

    @Override
    public List<Item> getItemsByUser(Integer userId, boolean filtred) {
        return tx(session -> filtred
                ? session.createQuery("from models.Item where done = false and user_id = :user").setParameter("user", userId).list()
                : session.createQuery("from models.Item where user_id = :user").setParameter("user", userId).list());
    }

    @Override
    public User createUser(User user) {
        return tx(session -> {
            session.save(user);
            return user;
        });
    }

}
