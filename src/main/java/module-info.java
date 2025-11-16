module com.cvbuilder {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.cvbuilder to javafx.fxml;
    opens com.cvbuilder.controller to javafx.fxml;
    opens com.cvbuilder.model to javafx.base;

    exports com.cvbuilder;
    exports com.cvbuilder.controller;
    exports com.cvbuilder.model;
}
