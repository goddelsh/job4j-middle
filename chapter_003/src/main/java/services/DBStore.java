package services;

import com.google.gson.Gson;
import models.Item;
import models.Wrapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Date;
import java.util.List;

public class DBStore implements Store {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static Store store = new DBStore();

    public static Store getInstance() {
        return store;
    }

    @Override
    public void addTask(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        item.setCreated(new Date());
        session.save(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Item> getItems(boolean filtered) {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = filtered ? session.createQuery("from models.Item where done=false").list() : session.createQuery("from models.Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void markTask(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("update models.Item set done = :done where id = :id")
                .setParameter("done", item.isDone()).setParameter("id", item.getId()).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

//    public static void main (String[] args) {
//        String str = "{ 'action': 'MARK', 'items' : [{'id':1, 'done':true}]}";
//        Wrapper w = new Gson().fromJson(str, Wrapper.class);
//        System.out.println("123");
//    }

}
