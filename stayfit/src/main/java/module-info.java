module com.example.stayfit {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.stayfit to javafx.fxml;
    exports com.example.stayfit;
}