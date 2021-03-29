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
    @ManyToOne(targetEntity = User.class)
    private User user;
    @ManyToOne(targetEntity = Room.class)
    private Room room;

    public Message() {
    }

    public Message(String message, User user, Room room) {
        this.message = message;
        this.user = user;
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
                Objects.equals(user, message1.user) &&
                Objects.equals(room, message1.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, createDate, user, room);
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
