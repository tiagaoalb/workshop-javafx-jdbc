module com.javafx.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens com.javafx.workshopjavafxjdbc to javafx.fxml;
    exports com.javafx.workshopjavafxjdbc;
    exports com.javafx.workshopjavafxjdbc.util;
    opens com.javafx.workshopjavafxjdbc.util to javafx.fxml;
}