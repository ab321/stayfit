module com.example.stayfit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.derby.tools;


    opens com.example.stayfit to javafx.fxml;
    exports com.example.stayfit;
    exports com.example.stayfit.controller;
    opens com.example.stayfit.controller to javafx.fxml;
}