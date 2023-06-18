package com.example.stayfit.entity;

import com.example.stayfit.model.entity.Exercise;
import com.example.stayfit.model.entity.Set;
import com.example.stayfit.model.entity.Template;
import com.example.stayfit.model.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

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
        User user = new User("testUser", "testPassword");
        user.setId(656L);
        Template template = new Template(user, "testTemplate");
        double weight = 80;
        int reps = 10;
        Date date = new Date();

        Set set = new Set(exercise, user, template, weight, date, reps);

        //act

        //assert
        assertThat(set.getId()).isNull();
        assertThat(set.getWeight()).isEqualTo(80);
        assertThat(set.getUser().getId()).isEqualTo(656L);
        assertThat(set.getTemplate().getName()).isEqualTo("testTemplate");
    }
}
