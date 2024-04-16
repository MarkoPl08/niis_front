module com.frontend.niis_front {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
    requires java.logging;

    exports com.frontend.niis_front.controllers to javafx.fxml;
    exports com.frontend.niis_front to javafx.graphics;
    opens com.frontend.niis_front.controllers to javafx.fxml;
    opens com.frontend.niis_front.dto to com.google.gson;
}