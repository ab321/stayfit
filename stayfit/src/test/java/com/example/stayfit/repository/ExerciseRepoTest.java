package com.example.stayfit.repository;

import com.example.stayfit.model.Database;
import com.example.stayfit.model.entity.Exercise;
import com.example.stayfit.model.repository.ExerciseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ExerciseRepoTest {
    private Connection connection;
    @BeforeEach
    public void setUp(){
        connection = Database.openConnection();
    }

    @AfterEach
    public void tearDown() {
        try {
            if(connection != null && !connection.isClosed()){
                Database.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void insert(){
        //arrange
        ExerciseRepository exerciseRepository = new ExerciseRepository();

        Exercise exercise = new Exercise("Legs");

        //act
        exerciseRepository.insert(exercise);

        //assert
        assertEquals(exercise.getName(), "Legs");
    }

}
