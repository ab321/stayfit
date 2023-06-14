package com.example.stayfit.model.repository;

import com.example.stayfit.model.Database;
import com.example.stayfit.model.entity.Exercise;
import com.example.stayfit.model.entity.ExercisePosition;
import com.example.stayfit.stayfitApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.LinkedList;

public class ExercisePositionRepository {

    private static Connection connection = Database.openConnection();
    private static ObservableList<ExercisePosition> exercisePositions;

    public ExercisePositionRepository(){
        exercisePositions = FXCollections.observableList(new LinkedList<>());
        connection = stayfitApp.getConnection();
        this.findAll();
    }

    public ObservableList<ExercisePosition> getExercisePositions() throws SQLException {
        return FXCollections.unmodifiableObservableList(this.exercisePositions);
    }

    public void save(ExercisePosition exercisePosition) {

        if(exercisePosition.getId() == null){
            insert(exercisePosition);
        }else{
            throw new RuntimeException("Error: The exercisePosition is already existing");
        }
    }


    public void insert(ExercisePosition exercisePosition) {

        try(Connection connection1 = Database.openConnection()) {
            String sql = "INSERT INTO S_EXERCISEPOSITION(EXERCISE_NR, TEMPLATE_NR) VALUES (?, ?)";

            PreparedStatement statement = connection1.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, exercisePosition.getExerciseNr());
            statement.setLong(2, exercisePosition.getTemplateNr());


            if(statement.executeUpdate() == 0){
                throw new SQLException("Insert of Exercise position failed, no rows affected");
            }

            exercisePositions.add(exercisePosition);

            try(ResultSet keys = statement.getGeneratedKeys()) {
                if(keys.next()){
                    exercisePosition.setId(keys.getLong(1));
                }else{
                    throw new SQLWarning("Insert into Exercise position failed, no ID obtained");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<ExercisePosition> findAll() {
        try {

            if (connection == null) {
                connection = Database.openConnection();
            }

            String sql = "SELECT * FROM S_EXERCISEPOSITION";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Long id = result.getLong("EXERCISEPOSITION_NR");
                Long exerciseNr = result.getLong("EXERCISE_NR");
                Long templateNr = result.getLong("TEMPLATE_NR");
                ExercisePosition exercisePosition = new ExercisePosition(exerciseNr, templateNr);
                exercisePosition.setId(id);

                exercisePositions.add(exercisePosition);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exercisePositions;
    }

    public ObservableList<Exercise> findWithTemplateId(Long templateId) throws SQLException{
        try {
            if (connection == null) {
                connection = Database.openConnection();
            }

            String sql = "SELECT * FROM S_EXERCISEPOSITION";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            ObservableList<Exercise> exercises = FXCollections.observableArrayList();

            while (result.next()) {
                if(templateId == result.getInt("TEMPLATE_NR")) {
                    int exerciseId = result.getInt("EXERCISE_NR");
                    ExerciseRepository exerciseRepository = new ExerciseRepository();
                    Exercise exercise = exerciseRepository.findById((long) exerciseId);

                    exercises.add(exercise);
                }

            }

            return exercises;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
