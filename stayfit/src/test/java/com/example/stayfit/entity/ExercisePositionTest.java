package com.example.stayfit.entity;

import com.example.stayfit.model.entity.Exercise;
import com.example.stayfit.model.entity.ExercisePosition;
import com.example.stayfit.model.entity.Template;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ExercisePositionTest {
    @BeforeEach
    public void setUp(){

    }

    @AfterEach
    public void tearDown() {

    }


    @Test
    void createExercisePosition(){
        //arrange
        Template template = new Template();
        Exercise exercise = new Exercise();

        ExercisePosition exercisePosition = new ExercisePosition(exercise.getId(), template.getId());

        //act

        //assert
        assertThat(exercisePosition.getId()).isNull();
        assertThat(exercisePosition.getExerciseNr()).isNull();
        assertThat(exercisePosition.getTemplateNr()).isNull();

    }
}
