module com.javafx.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.javafx.workshopjavafxjdbc to javafx.fxml;
    exports com.javafx.workshopjavafxjdbc;
}