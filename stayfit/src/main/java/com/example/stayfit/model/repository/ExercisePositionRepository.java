package com.example.stayfit.model.repository;

import com.example.stayfit.model.Database;
import com.example.stayfit.model.entity.Exercise;
import com.example.stayfit.model.entity.ExercisePosition;
import com.example.stayfit.stayfitApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

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
        try (Connection connection = Database.openConnection()) {
            String checkSql = "SELECT COUNT(*) FROM S_EXERCISEPOSITION WHERE EXERCISE_NR = ? AND TEMPLATE_NR = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
                checkStatement.setLong(1, exercisePosition.getExerciseNr());
                checkStatement.setLong(2, exercisePosition.getTemplateNr());

                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        if (count > 0) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Not available");
                            alert.setHeaderText(null);
                            alert.setContentText("Position already exists");
                            alert.showAndWait();
                            return;
                        }
                    }
                }
            }

            String insertSql = "INSERT INTO S_EXERCISEPOSITION(EXERCISE_NR, TEMPLATE_NR) VALUES (?, ?)";

            try (PreparedStatement insertStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insertStatement.setLong(1, exercisePosition.getExerciseNr());
                insertStatement.setLong(2, exercisePosition.getTemplateNr());

                if (insertStatement.executeUpdate() == 0) {
                    throw new SQLException("Insert of Exercise position failed, no rows affected");
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Saved Template");
                alert.setHeaderText(null);
                alert.setContentText("The exercise has been successfully added to the template");
                alert.showAndWait();
                exercisePositions.add(exercisePosition);

                try (ResultSet keys = insertStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        exercisePosition.setId(keys.getLong(1));
                    } else {
                        throw new SQLWarning("Insert into Exercise position failed, no ID obtained");
                    }
                }
            }
        } catch (SQLException e) {
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

    public void deleteExerciseFromTemplate(Long exerciseId, Long templateId) throws SQLException {
        try {

            String sql = "DELETE FROM S_EXERCISEPOSITION WHERE EXERCISE_NR=? AND TEMPLATE_NR=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, exerciseId);
            statement.setLong(2, templateId);


            if (statement.executeUpdate() == 0) {
                throw new SQLException("Delete from S_ExercisePosition failed, no rows affected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
