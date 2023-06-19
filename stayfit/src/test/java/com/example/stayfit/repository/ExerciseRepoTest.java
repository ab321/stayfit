package com.example.stayfit.repository;

import com.example.stayfit.model.Database;
import com.example.stayfit.model.entity.Exercise;
import com.example.stayfit.model.repository.ExerciseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ExerciseRepoTest {
    ArrayList<Exercise> allExercises = new ArrayList<Exercise>();
    private Connection connection;
    @BeforeEach
    public void beforeEach(){
        connection = Database.openConnection();
    }

    @AfterEach
    public void afterEach() {
        try {
            ExerciseRepository exerciseRepository = new ExerciseRepository();

            for (int i = 0; i < allExercises.size(); i++){
                exerciseRepository.delete(allExercises.get(i));
            }

            if(connection != null && !connection.isClosed()){
                Database.closeConnection();
            }

            Database.shutdownDatabase();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void insert(){
        //arrange
        ExerciseRepository exerciseRepository = new ExerciseRepository();
        Exercise exercise = new Exercise("Bankdrucken");


        //act
        exerciseRepository.insert(exercise);
        allExercises.add(exercise);

        //assert
        assertEquals(exercise.getName(), "Bankdrucken");
    }


}
