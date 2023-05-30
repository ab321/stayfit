package com.example.stayfit.entity;

import com.example.stayfit.model.entity.Template;
import com.example.stayfit.model.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

public class TemplateTest {

    @BeforeEach
    public void setUp(){

    }

    @AfterEach
    public void tearDown() {

    }


    @Test
    void createTemplate(){
        //arrange
        User user = new User();
        String templateName = "Pull";
        Template template = new Template(user, templateName);

        //act

        //assert
        assertThat(template.getId()).isNull();
        assertThat(template.getUser().getId()).isEqualTo(null);
        assertThat(template.getName()).isEqualTo("Pull");

    }
}
