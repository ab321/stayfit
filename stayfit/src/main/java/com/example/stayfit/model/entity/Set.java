package com.example.stayfit.model.entity;

import java.time.LocalDate;
import java.util.Date;

public class Set {
    private Long id;
    private Exercise exercise;
    private double weight;
    private int reps;
    private java.util.Date date;
    private Template template;
    private User user;

    public Set(){

    }
    public Set(Exercise exercise, User user, Template template, Double weight, java.util.Date date, int reps) {
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        this.user = user;
        this.template = template;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return this.getExercise().getName() + " - Set";
    }
}
