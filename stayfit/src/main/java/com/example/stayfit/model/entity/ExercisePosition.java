package com.example.stayfit.model.entity;

public class ExercisePosition {
    private Long id;
    private Exercise exercise;
    private Template template;

    public ExercisePosition(){ }
    public ExercisePosition(Exercise exercise, Template template) {
        this.exercise = exercise;
        this.template = template;
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

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    @Override
    public String toString() {
        return "ExercisePosition{" +
                "ExercisePosition_nr=" + id +
                ", exercise_nr=" + getExercise().getId() +
                ", template_nr=" + getTemplate().getId() +
                '}';
    }
}
