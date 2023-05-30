package com.example.stayfit.model.repository;

import com.example.stayfit.model.Database;
import com.example.stayfit.model.entity.Exercise;
import com.example.stayfit.model.entity.Set;

import java.sql.Connection;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import javax.sql.DataSource;

public class SetRepository  implements Persistent<Set> {

    private DataSource dataSource = Database.getDataSource();
    @Override
    public void save(Set set) {
        if(set.getId() == null){
            insert(set);
        }else{
            update(set);
        }
    }

    @Override
    public void insert(Set set) {
        try(Connection connection = dataSource.getConnection()){
            String sql = "INSERT INTO S_SET(EXERCISE_NR, SET_WEIGHT, SET_REPS) VALUES (?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, set.getExercise().getId());
            statement.setLong(2, set.getWeight());
            statement.setLong(3, set.getReps());

            if(statement.executeUpdate() == 0){
                throw new SQLException("Insert of Set failed, no rows affected");
            }

            try (ResultSet keys = statement.getGeneratedKeys()){
                if(keys.next()){
                    set.setId(keys.getLong(1));
                }else{
                    throw new SQLException("Insert into Set failed, no ID obtained");
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Set set) {
        try(Connection connection = dataSource.getConnection()){
            String sql = "DELETE FROM S_SET WHERE SET_NR=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1,set.getId());


            if(statement.executeUpdate() == 0){
                throw new SQLException("Delete from Set failed, no rows affected");
            }

            set.setId(null);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Set> findAll() {
        List<Set> setList = new ArrayList<>();

        try(Connection connection = dataSource.getConnection()){
            String sql = "SELECT * FROM S_SET";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()){
                ExerciseRepository exerciseRepository = new ExerciseRepository();

                Long id = result.getLong("BILL_NR");
                Long weight = result.getLong("SET_WEIGHT");
                Long reps = result.getLong("SET_REPS");
                Exercise exercise = exerciseRepository.findById(result.getLong("EXERCISE_NR"));
                Set set = new Set(exercise, weight, reps);
                set.setId(id);

                setList.add(set);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return setList;
    }

    @Override
    public Set findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM S_SET WHERE SET_NR=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                if(id == result.getInt("SET_NR")) {
                    ExerciseRepository exerciseRepository = new ExerciseRepository();
                    Long weight = result.getLong("SET_WEIGHT");
                    Long reps = result.getLong("SET_REPS");

                    Set set = new Set(exerciseRepository.findById(result.getLong("EXERCISE_NR")), weight, reps);

                    return set;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(Set set) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE S_SET SET EXERCISE_NR=?,SET_WEIGHT, SET_REPS=? WHERE SET_NR=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setLong(1, set.getExercise().getId());
            statement.setLong(2, set.getWeight());
            statement.setLong(3, set.getReps());
            statement.setLong(4, set.getId());


            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of Set failed, no rows affected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
