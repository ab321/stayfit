package com.example.stayfit.model.entity;

public class Exercise {
    private Long id;
    private String name;

    public Exercise(){

    }

    public Exercise(String name){
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "exercise_nr=" + id +
                ", exercise_name=" + getName() + '\'' +
                '}';
    }
}
