package com.frontend.niis_front.controllers;

import com.frontend.niis_front.dto.RestaurantDTO;
import com.frontend.niis_front.service.BackendService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddRestaurantController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField codeField;

    private final BackendService backendService = new BackendService();

    @FXML
    protected void onAddRestaurant() {
        RestaurantDTO restaurant = new RestaurantDTO();
        restaurant.setName(nameField.getText());
        restaurant.setZipCode(Double.parseDouble(priceField.getText()));
        restaurant.setLocation(codeField.getText());

        backendService.createRestaurant(restaurant).thenAccept(restaurantDTO -> {
            javafx.application.Platform.runLater(() -> {
                nameField.clear();
                priceField.clear();
                codeField.clear();
            });
        });
    }
}

