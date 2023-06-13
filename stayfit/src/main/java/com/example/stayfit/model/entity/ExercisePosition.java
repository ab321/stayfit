package com.example.stayfit.model.entity;

public class ExercisePosition {
    private Long id;
    private Long exerciseNr;
    private Long templateNr;

    public ExercisePosition(){ }
    public ExercisePosition(Long exerciseNr, Long templateNr) {
        this.exerciseNr = exerciseNr;
        this.templateNr = templateNr;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getExerciseNr() {
        return exerciseNr;
    }

    public void setExerciseNr(Long exerciseNr) {
        this.exerciseNr = exerciseNr;
    }

    public Long getTemplateNr() {
        return templateNr;
    }

    public void setTemplateNr(Long templateNr) {
        this.templateNr = templateNr;
    }

    @Override
    public String toString() {
        return "ExercisePosition{" +
                "ExercisePosition_nr=" + id +
                ", exercise_nr=" + getExerciseNr() +
                ", template_nr=" + getTemplateNr() +
                '}';
    }
}
