module com.example.siyam_2207031_cvbuilder {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.example.siyam_2207031_cvbuilder to javafx.fxml;
    opens com.example.siyam_2207031_cvbuilder.controller to javafx.fxml;
    opens com.example.siyam_2207031_cvbuilder.model to javafx.base;

    exports com.example.siyam_2207031_cvbuilder;
    exports com.example.siyam_2207031_cvbuilder.controller;
    exports com.example.siyam_2207031_cvbuilder.model;
}
