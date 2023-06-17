package com.example.stayfit.model.repository;

import com.example.stayfit.model.Database;
import com.example.stayfit.model.entity.Exercise;
import com.example.stayfit.model.entity.Set;
import com.example.stayfit.model.entity.Template;
import com.example.stayfit.model.entity.User;
import com.example.stayfit.stayfitApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import javax.sql.DataSource;

public class SetRepository  implements Persistent<Set> {
    private static Connection connection = Database.openConnection();
    private User currentUser;
    private static ObservableList<Set> sets;

    public SetRepository(User user){
        currentUser = user;
        sets = FXCollections.observableList(new LinkedList<>());
        connection = stayfitApp.getConnection();
        this.findAll();
    }

    public ObservableList<Set> getSets() throws SQLException {
        return FXCollections.unmodifiableObservableList(this.sets);
    }

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
        try {
            String sql = "INSERT INTO S_SET(EXERCISE_NR, SET_USER_ID, SET_TEMPLATE_NR, SET_WEIGHT, SET_DATE, SET_REPS) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, set.getExercise().getId());
            statement.setLong(2, set.getUser().getId());
            statement.setLong(3, set.getTemplate().getId());
            statement.setDouble(4, set.getWeight());
            statement.setDate(5, (Date) set.getDate());
            statement.setInt(6, set.getReps());

            if(statement.executeUpdate() == 0){
                throw new SQLException("Insert of Set failed, no rows affected");
            }

            sets.add(set);

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
        try {
            String sql = "DELETE FROM S_SET WHERE SET_NR=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1,set.getId());


            if(statement.executeUpdate() == 0){
                throw new SQLException("Delete from Set failed, no rows affected");
            }

            sets.remove(set);
            set.setId(null);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Set> findAll() {
        try {
            String sql = "SELECT * FROM S_SET WHERE SET_USER_ID=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1,currentUser.getId());
            ResultSet result = statement.executeQuery();

            while (result.next()){
                ExerciseRepository exerciseRepository = new ExerciseRepository();
                TemplateRepository templateRepository = new TemplateRepository(currentUser);
                UserRepository userRepository = new UserRepository();

                Long id = result.getLong("SET_NR");
                double weight = result.getDouble("SET_WEIGHT");
                int reps = result.getInt("SET_REPS");

                Set set = new Set(
                        exerciseRepository.findById(result.getLong("EXERCISE_NR")),
                        userRepository.findById(result.getLong("SET_USER_ID")),
                        templateRepository.findById(result.getLong("SET_TEMPLATE_NR")),
                        weight,
                        result.getDate("SET_DATE"),
                        reps);

                set.setId(id);

                sets.add(set);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return sets;
    }

    @Override
    public Set findById(Long id) {
        try {
            String sql = "SELECT * FROM S_SET WHERE SET_NR=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                if(id == result.getInt("SET_NR")) {
                    ExerciseRepository exerciseRepository = new ExerciseRepository();
                    UserRepository userRepository = new UserRepository();
                    TemplateRepository templateRepository = new TemplateRepository(currentUser);
                    double weight = result.getDouble("SET_WEIGHT");
                    int reps = result.getInt("SET_REPS");

                    Set set = new Set(
                            exerciseRepository.findById(result.getLong("EXERCISE_NR")),
                            userRepository.findById(result.getLong("SET_USER_ID")),
                            templateRepository.findById(result.getLong("SET_TEMPLATE_NR")),
                            weight,
                            result.getDate("SET_DATE"),
                            reps);

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
        try {
            String sql = "UPDATE S_SET SET EXERCISE_NR=?, SET_USER_ID=?, SET_TEMPLATE_NR=?, SET_WEIGHT=?, SET_DATE=?, SET_REPS=? WHERE SET_NR=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setLong(1, set.getExercise().getId());
            statement.setLong(2, set.getUser().getId());
            statement.setLong(3, set.getTemplate().getId());
            statement.setDouble(4, set.getWeight());
            statement.setDate(5, (Date) set.getDate());
            statement.setInt(6, set.getReps());
            statement.setLong(7, set.getId());


            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of Set failed, no rows affected");
            }

            Set s = this.sets.stream().filter(se -> se.getId() == set.getId()).findFirst().get();
            s.setExercise(set.getExercise());
            s.setReps(set.getReps());
            s.setWeight(set.getWeight());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
