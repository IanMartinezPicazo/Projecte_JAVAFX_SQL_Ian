module ian.projecte_javafx_sql_ian {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Necessari.

    opens ian.projecte_javafx_sql_ian to javafx.fxml;
    exports ian.projecte_javafx_sql_ian;
}
