package com.frontend.niis_front.controllers;

import com.frontend.niis_front.dto.RestaurantDTO;
import com.frontend.niis_front.service.BackendService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class UpdateRestaurantController {

    @FXML
    private ListView<RestaurantDTO> restaurantListView;
    @FXML
    private TextField nameField, priceField, codeField;
    @FXML
    private Button updateButton;

    private final BackendService backendService = new BackendService();

    @FXML
    public void initialize() {
        loadRestaurantItems();
        restaurantListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameField.setText(newSelection.getName());
                priceField.setText(String.valueOf(newSelection.getZipCode()));
                codeField.setText(newSelection.getLocation());
            }
        });
    }

    private void loadRestaurantItems() {
        backendService.getAllRestaurant().thenAccept(restaurantList -> {
            javafx.application.Platform.runLater(() -> {
                restaurantListView.getItems().clear();
                restaurantListView.getItems().addAll(restaurantList);
            });
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }



    @FXML
    private void updateSelectedRestaurant() {
        RestaurantDTO selectedRestaurant = restaurantListView.getSelectionModel().getSelectedItem();
        if (selectedRestaurant != null) {
            selectedRestaurant.setName(nameField.getText());
            selectedRestaurant.setZipCode(Double.parseDouble(priceField.getText()));
            selectedRestaurant.setLocation(codeField.getText());

            backendService.updateRestaurant(selectedRestaurant.getId(), selectedRestaurant)
                    .thenRun(() -> {
                        javafx.application.Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Update Successful");
                            alert.setHeaderText(null);
                            alert.setContentText("The restaurant has been successfully updated.");
                            alert.showAndWait();
                        });
                    })
                    .exceptionally(ex -> {
                        javafx.application.Platform.runLater(() -> {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Update Failed");
                            errorAlert.setHeaderText("Failed to update the restaurant");
                            errorAlert.setContentText("An error occurred while trying to update the restaurant. Please try again.");
                            errorAlert.showAndWait();
                        });
                        return null;
                    });
        }
    }
}
