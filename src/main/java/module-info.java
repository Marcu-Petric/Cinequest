module com.example.proiect {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens com.example.proiect to javafx.fxml;
    exports com.example.proiect;
}