package com.frontend.niis_front.controllers;

import com.frontend.niis_front.service.BackendService;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class HelloController {

    @FXML
    private TabPane tabPane;

    private BackendService backendService;

    @FXML
    public void initialize() {
        backendService = new BackendService();
    }
}
