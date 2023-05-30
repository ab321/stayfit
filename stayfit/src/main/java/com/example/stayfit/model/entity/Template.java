package com.example.stayfit.model.entity;

public class Template {
    private Long id;

    private User user;

    private String name;

    public Template(){

    }
    public Template(User user, String name) {
        this.user = user;
        this.name = name;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Template{" +
                "template_nr=" + id +
                ", user_id=" + getUser().getId() +
                ", name='" + name + '\'' +
                '}';
    }
}
