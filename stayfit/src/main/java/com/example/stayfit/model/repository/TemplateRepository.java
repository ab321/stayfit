package com.example.stayfit.model.repository;

import com.example.stayfit.model.Database;
import com.example.stayfit.model.entity.Exercise;
import com.example.stayfit.model.entity.Template;
import com.example.stayfit.model.entity.User;
import com.example.stayfit.stayfitApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TemplateRepository implements Persistent<Template> {
    private static Connection connection = Database.openConnection();
    private User currentUser;
    private static ObservableList<Template> templates;

    public TemplateRepository(User user){
        templates = FXCollections.observableList(new LinkedList<>());
        this.currentUser = user;
        connection = stayfitApp.getConnection();
        this.findAll();
    }

    public ObservableList<Template> getTemplates() throws SQLException {
        return FXCollections.unmodifiableObservableList(this.templates);
    }
    @Override
    public void save(Template template) {
        if(template.getId() == null){
            insert(template);
        }else{
            update(template);
        }

    }

    @Override
    public void insert(Template template) {
        try {
            String sql = "INSERT INTO S_TEMPLATE(USER_ID,TEMPLATE_NAME) VALUES (?,?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, template.getUser().getId());
            statement.setString(2, template.getName());

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Insert of Template failed, no rows affected");
            }

            templates.add(template);

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    template.setId(keys.getLong(1));
                } else {
                    throw new SQLException("Insert into Template failed, no ID obtained");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Template template) {
        try {
            String sql = "DELETE FROM S_TEMPLATE WHERE TEMPLATE_NR=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1,template.getId() );

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Delete from Template failed, no rows affected");
            }

            templates.remove(template);
            template.setId(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Template> findAll() {
        try {
            String sql = "SELECT * FROM S_TEMPLATE WHERE USER_ID=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, currentUser.getId());
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                UserRepository userRepository = new UserRepository();

                Long id = result.getLong("TEMPLATE_NR");
                String name = result.getString("TEMPLATE_NAME");
                User user = userRepository.findById(result.getLong("USER_ID"));

                Template template = new Template(user, name);
                template.setId(id);

                templates.add(template);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return templates;
    }

    @Override
    public Template findById(Long id) {

        try {
            String sql = "SELECT * FROM S_TEMPLATE WHERE TEMPLATE_NR=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                if(id == result.getInt("TEMPLATE_NR")) {
                    UserRepository userRepository = new UserRepository();

                    String name = result.getString("TEMPLATE_NAME");

                    Template template = new Template(userRepository.findById(result.getLong("USER_ID")), name);
                    template.setId(id);

                    return template;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(Template template) {

        try {
            String sql = "UPDATE S_TEMPLATE SET USER_ID=?,TEMPLATE_NAME=? WHERE TEMPLATE_NR=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setLong(1, template.getUser().getId());
            statement.setString(2, template.getName());
            statement.setLong(3, template.getId());


            if (statement.executeUpdate() == 0) {
                throw new SQLException("Update of Template failed, no rows affected");
            }

            Template t = this.templates.stream().filter(te -> te.getId() == template.getId()).findFirst().get();
            t.setName(template.getName());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
