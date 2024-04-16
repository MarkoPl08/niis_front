package com.frontend.niis_front.controllers;

import com.frontend.niis_front.dto.RestaurantDTO;
import com.frontend.niis_front.dto.ReviewDTO;
import com.frontend.niis_front.service.BackendService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddReviewController {

    @FXML
    private ListView<RestaurantDTO> restaurantListView;
    @FXML
    private TextField titleField, ratingField;
    @FXML
    private TextArea textField;

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
    private void addReviewForSelectedRestaurant() {
        RestaurantDTO selectedRestaurant = restaurantListView.getSelectionModel().getSelectedItem();
        if (selectedRestaurant != null) {
            ReviewDTO review = new ReviewDTO();
            review.setTitle(titleField.getText());
            review.setText(textField.getText());
            review.setRating(Integer.parseInt(ratingField.getText()));

            backendService.createReview(selectedRestaurant.getId(), review)
                    .thenAccept(result -> {
                        javafx.application.Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Review Added");
                            alert.setHeaderText(null);
                            alert.setContentText("The review has been successfully added!");
                            alert.showAndWait();

                            titleField.clear();
                            textField.clear();
                            ratingField.clear();
                        });
                    })
                    .exceptionally(ex -> {
                        ex.printStackTrace();
                        return null;
                    });
        }
    }
}
