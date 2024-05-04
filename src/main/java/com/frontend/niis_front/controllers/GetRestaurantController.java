package com.frontend.niis_front.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import com.frontend.niis_front.dto.RestaurantDTO;
import com.frontend.niis_front.service.BackendService;

public class GetRestaurantController {

    @FXML
    private ListView<RestaurantDTO> restaurantList;

    private final BackendService backendService = new BackendService();

    @FXML
    protected void onRefresh() {
        backendService.getAllRestaurant()
                .thenAccept(restaurants -> javafx.application.Platform.runLater(() ->
                        restaurantList.getItems().setAll(restaurants)));
    }
}
