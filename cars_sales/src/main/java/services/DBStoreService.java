package services;

import models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.function.Function;

public class DBStoreService implements StoreService {

    static private StoreService service = null;

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public void init() {
        if (getCarBrands().isEmpty()) {
            init(List.of(new CarBrand("BMW"), new CarBrand("Mercedes"), new CarBrand("Audi")));
        }
    }

    static public StoreService getStoreService() {
        if (service == null) {
            service = new DBStoreService();
        }
        return service;
    }

    private <T> void init(List<T> collection) {
        collection.forEach(element -> tx(session -> {
            session.save(element);
            return null;
        }));
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
    public List<CarBody> getCarBodies() {
        return tx(session -> session.createQuery("from models.CarBody").list());
    }

    @Override
    public List<CarBrand> getCarBrands() {
        return tx(session -> session.createQuery("from models.CarBrand").list());
    }

    @Override
    public List<CarEngine> getCarEngines() {
        return tx(session -> session.createQuery("from models.CarEngine").list());
    }

    @Override
    public List<CarModel> getCarModels() {
        return tx(session -> session.createQuery("from models.CarModel").list());
    }

    @Override
    public List<CarType> getCarTypes() {
        return tx(session -> session.createQuery("from models.CarType").list());
    }

    @Override
    public List<SellerContact> getSellerContact(User user) {
        return null;
    }

    @Override
    public List<Announcement> getAnnouncements(User user) {
        return tx(session -> session.createQuery("from models.Announcement where user_id = :user_id").setParameter("user_id", user.getId()).list());
    }

    @Override
    public Announcement getFullAnnouncements(Announcement announcement) {
        return tx(session -> (Announcement) session.createQuery("from models.Announcement "
                + "join models.CarBody "
                + "join models.CarBrand " + "join models.CarEngine "
                + "join models.CarModel "
                + "join models.CarType "
                + "join models.SellerContact "
                + "join models.CarPhoto "
                + "where id = :id").setParameter("id", announcement.getId()).list().stream().findFirst().orElse(null));
    }

    @Override
    public List<CarPhoto> getCarPhotos(Announcement announcement) {
        return null;
    }

    @Override
    public void addAnnouncement(Announcement announcement) {
        tx(session -> {
            session.save(announcement);
            return announcement;
        });
    }

    @Override
    public void editAnnouncement(Announcement announcement) {
        tx(session -> {
            session.update(announcement);
            return null;
        });
    }

    @Override
    public void addCarPhoto(CarPhoto carPhoto) {

    }

    @Override
    public void deleteCarPhoto(CarPhoto carPhoto) {

    }

    @Override
    public void addSellerContact(SellerContact sellerContact) {

    }

    @Override
    public void deleteSellerContact(SellerContact sellerContact) {

    }

    @Override
    public User addUser(User user) {
        return tx(session -> {
            session.save(user);
            return user;
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
}
