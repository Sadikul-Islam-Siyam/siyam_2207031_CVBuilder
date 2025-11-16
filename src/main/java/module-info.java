module com.example.siyam_2207031_cvbuilder {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.siyam_2207031_cvbuilder to javafx.fxml;
    exports com.example.siyam_2207031_cvbuilder;
    exports com.example.siyam_2207031_cvbuilder.controller;
    opens com.example.siyam_2207031_cvbuilder.controller to javafx.fxml;
    exports com.example.siyam_2207031_cvbuilder.model;
    opens com.example.siyam_2207031_cvbuilder.model to javafx.fxml;
}