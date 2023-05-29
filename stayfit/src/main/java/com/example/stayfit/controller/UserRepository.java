package com.example.stayfit.controller;

import com.example.stayfit.entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Persistent<User> {

    private DataSource dataSource = Database.getDataSource();
    @Override
    public void save(User user) {
        if (user.getId() == null) {
            insert(user);
        } else {
            update(user);
        }
    }

    @Override
    public void insert(User user) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO S_USER(USER_NAME,USER_PASSWORD) VALUES (?,?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Insert of User failed, no rows affected");
            }


            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getLong(1));
                } else {
                    throw new SQLException("Insert into User failed, no ID obtained");
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM S_USER WHERE USER_ID=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1,user.getId() );

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Delete from User failed, no rows affected");
            }
            user.setId(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> findAll() {
        List<User> billList = new ArrayList<>();


        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM S_USER";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {

                Long id = result.getLong("BILL_NR");
                String name = result.getString("USER_NAME");
                String password = result.getString("USER_PASSWORD");
                User user = new User(name, password);
                user.setId(id);

                billList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return billList;
    }

    @Override
    public User findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM S_USER WHERE USER_ID=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                if(id == result.getInt("BILL_NR")) {

                    String name = result.getString("USER_NAME");
                    String password = result.getString("USER_PASSWORD");
                    User user = new User(name, password);
                    user.setId(id);

                    return user;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(User user) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE S_USER SET USER_NAME=?,USER_PASSWORD=? WHERE USER_ID=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setLong(3, user.getId());


            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of User failed, no rows affected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
