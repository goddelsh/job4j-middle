package models;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String descr;
    private Date created;
    private boolean done;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String desc) {
        this.descr = desc;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id &&
                done == item.done &&
                Objects.equals(descr, item.descr) &&
                Objects.equals(created, item.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descr, created, done);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", desc='" + descr + '\'' +
                ", created=" + created +
                ", done=" + done +
                '}';
    }
}
