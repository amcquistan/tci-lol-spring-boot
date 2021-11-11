package com.thecodinginterface.tcilol.models;

import javax.persistence.*;
import java.util.List;

@Entity(name = "user_lists")
public class UserList extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;

    @OneToMany(mappedBy = "userList")
    private List<ListItem> items;

    public UserList(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "UserList{" +
                "id=" + id +
                ", title='" + title + "'" +
                ", user=" + user + "}";
    }
}
