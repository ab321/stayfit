package com.example.stayfit.entity;

import com.example.stayfit.model.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class UserTest {
    @BeforeEach
    public void setUp(){

    }

    @AfterEach
    public void tearDown() {

    }


    @Test
    void createUser(){
        //arrange
        String name = "Abdullah";
        String password = "app";

        User user = new User(name, password);

        //act

        //assert
        assertThat(user.getId()).isEqualTo(null);
        assertThat(user.getName()).isEqualTo("Abdullah");
        assertThat(user.getPassword()).isEqualTo("app");

    }
}
