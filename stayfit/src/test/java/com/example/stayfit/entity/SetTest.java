package com.example.stayfit.entity;

import com.example.stayfit.model.entity.Exercise;
import com.example.stayfit.model.entity.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SetTest {
    @BeforeEach
    public void setUp(){

    }

    @AfterEach
    public void tearDown() {

    }


    @Test
    void createSet(){
        //arrange
        Exercise exercise = new Exercise();
        Long weight = 80L;
        Long reps = 10L;

        Set set = new Set(exercise, weight, reps);

        //act

        //assert
        assertThat(set.getId()).isNull();
        assertThat(set.getExercise().getId()).isNull();
        assertThat(set.getWeight()).isEqualTo(80L);
        assertThat(set.getReps()).isEqualTo(10L);
    }
}
