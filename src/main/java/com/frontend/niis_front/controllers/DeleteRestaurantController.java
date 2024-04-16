package com.frontend.niis_front.controllers;

import com.frontend.niis_front.dto.RestaurantDTO;
import com.frontend.niis_front.service.BackendService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

public class DeleteRestaurantController {

    @FXML
    private ListView<RestaurantDTO> restaurantListView;

    private final BackendService backendService = new BackendService();

    @FXML
    public void initialize() {
        loadRestaurantItems();
    }

    private void loadRestaurantItems() {
        backendService.getAllRestaurant().thenAccept(restaurantList -> {
            javafx.application.Platform.runLater(() -> restaurantListView.getItems().setAll(restaurantList));
        });
    }

    @FXML
    private void deleteSelectedRestaurant() {
        RestaurantDTO selectedRestaurant = restaurantListView.getSelectionModel().getSelectedItem();
        if (selectedRestaurant != null) {
            backendService.deleteRestaurant(selectedRestaurant.getId())
                    .thenRun(() -> {
                        javafx.application.Platform.runLater(() -> {
                            restaurantListView.getItems().remove(selectedRestaurant);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setHeaderText(null);
                            alert.setContentText("Restaurant deleted successfully!");
                            alert.showAndWait();
                        });
                    })
                    .exceptionally(ex -> {
                        ex.printStackTrace();
                        return null;
                    });
        }
    }
}
