package com.example.stayfit.model.entity;

public class Set {
    private Long id;
    private Exercise exercise;
    private Long weight;
    private Long reps;

    public Set(){

    }
    public Set(Exercise exercise, Long weight, Long reps) {
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
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

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getReps() {
        return reps;
    }

    public void setReps(Long reps) {
        this.reps = reps;
    }

    @Override
    public String toString() {
        return "Set{" +
                "set_nr=" + id +
                ", exercise_nr=" + getExercise().getId() +
                ", set_weight=" + weight +
                ", set_reps=" + reps +
                '}';
    }
}
