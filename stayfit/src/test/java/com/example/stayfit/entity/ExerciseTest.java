package com.example.stayfit.entity;

import com.example.stayfit.model.entity.Exercise;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ExerciseTest {
    @BeforeEach
    public void setUp(){

    }

    @AfterEach
    public void tearDown() {

    }


    @Test
    void createExercise(){
        //arrange
        String exerciseName = "T-bar Row";
        Exercise exercise = new Exercise(exerciseName);

        //act

        //assert
        assertThat(exercise.getId()).isEqualTo(null);
        assertThat(exercise.getName()).isEqualTo("T-bar Row");
    }
}
