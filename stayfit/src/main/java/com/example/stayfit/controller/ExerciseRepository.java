package com.example.stayfit.controller;

import com.example.stayfit.entity.Exercise;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class ExerciseRepository implements Persistent<Exercise>{

    private DataSource dataSource = Database.getDataSource();

    @Override
    public void save(Exercise exercise) {

        if(exercise.getId() == null){
            insert(exercise);
        }else{
            update(exercise);
        }

    }

    @Override
    public void insert(Exercise exercise) {

        try(Connection connection = dataSource.getConnection()){
            String sql = "INSERT INTO S_EXERCISE(EXERCISE_NAME) VALUES (?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, exercise.getName());


            if(statement.executeUpdate() == 0){
                throw new SQLException("Insert of Exercise failed, no rows affected");
            }

            try(ResultSet keys = statement.getGeneratedKeys()) {
                if(keys.next()){
                    exercise.setId(keys.getLong(1));
                }else{
                    throw new SQLWarning("Insert into Exercise failed, no ID obtained");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Exercise exercise) {
        try(Connection connection = dataSource.getConnection()){
            String sql = "DELETE FROM S_EXERCISE WHERE EXERCISE_NR=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, exercise.getId());

            if(statement.executeUpdate() == 0){
                throw new SQLException("Delete from S_Exercise failed, no rows affected");
            }
            exercise.setId(null);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Exercise> findAll() {
        List<Exercise> exerciseList = new ArrayList<>();


        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM S_EXERCISE";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Long id = result.getLong("EXERCISE_NR");
                String name = result.getString("EXERCISE_NAME");
                Exercise exercise = new Exercise(name);
                exercise.setId(id);

                exerciseList.add(exercise);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exerciseList;
    }

    @Override
    public Exercise findById(Long id) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM S_EXERCISE WHERE EXERCISE_NR=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                if(id == result.getInt("EXERCISE_NR")) {
                    String name = result.getString("EXERCISE_NAME");
                    Exercise exercise = new Exercise(name);
                    exercise.setId(id);

                    return exercise;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(Exercise exercise) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE S_EXERCISE SET EXERCISE_NAME=? WHERE EXERCISE_NR=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, exercise.getName());
            statement.setLong(2, exercise.getId());


            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of S_EXERCISE failed, no rows affected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
