module com.example.javafxstore {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.javafxstore to javafx.fxml;
    exports com.example.javafxstore;
}