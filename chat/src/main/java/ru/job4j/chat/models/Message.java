package ru.job4j.chat.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String message;
    @Column(name = "create_date")
    private Date createDate;
    @ManyToOne(targetEntity = Person.class)
    private Person person;
    @ManyToOne(targetEntity = Room.class)
    private Room room;

    public Message() {
    }

    public Message(String message, Person person, Room room) {
        this.message = message;
        this.person = person;
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(id, message1.id) &&
                Objects.equals(message, message1.message) &&
                Objects.equals(createDate, message1.createDate) &&
                Objects.equals(person, message1.person) &&
                Objects.equals(room, message1.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, createDate, person, room);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
