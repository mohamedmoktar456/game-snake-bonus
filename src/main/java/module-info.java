module com.example.advance_bouns {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.advance_bouns to javafx.fxml;
    exports com.example.advance_bouns;
}
