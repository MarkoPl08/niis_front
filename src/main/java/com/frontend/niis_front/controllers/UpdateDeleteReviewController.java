package com.frontend.niis_front.controllers;

import com.frontend.niis_front.dto.RestaurantDTO;
import com.frontend.niis_front.dto.ReviewDTO;
import com.frontend.niis_front.service.BackendService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.lang.reflect.Type;
import java.util.List;

public class UpdateDeleteReviewController {

    @FXML
    private ComboBox<RestaurantDTO> restaurantListView;
    @FXML
    private ListView<ReviewDTO> reviewListView;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea textField;
    @FXML
    private TextField ratingField;

    private final BackendService backendService = new BackendService();
    private RestaurantDTO selectedRestaurant;
    private ReviewDTO selectedReview;

    @FXML
    public void initialize() {
        restaurantListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedRestaurant = newSelection;
            loadReviewsForSelectedRestaurant();
        });

        reviewListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedReview = newVal;
            displaySelectedReviewDetails();
        });

        loadRestaurants();
    }

    private void loadRestaurants() {
        backendService.getAllRestaurant().thenAccept(restaurantList -> {
            javafx.application.Platform.runLater(() -> restaurantListView.getItems().setAll(restaurantList));
        });
    }

    private void loadReviewsForSelectedRestaurant() {
        if (selectedRestaurant != null) {
            backendService.getReviewsForRestaurant(selectedRestaurant.getId()).thenApply(reviewsJson -> {
                Type listType = new TypeToken<List<ReviewDTO>>() {
                }.getType();
                List<ReviewDTO> reviews = new Gson().fromJson(reviewsJson, listType);
                javafx.application.Platform.runLater(() -> {
                    reviewListView.getItems().setAll(reviews);
                });
                return null;
            }).exceptionally(ex -> {
                javafx.application.Platform.runLater(() -> {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Load Failed");
                    errorAlert.setHeaderText("Failed to load the review");
                    errorAlert.setContentText("An error occurred while trying to load the restaurant. Please try again.");
                    errorAlert.showAndWait();
                });
                return null;
            });
        }
    }

    private void displaySelectedReviewDetails() {
        if (selectedReview != null) {
            titleField.setText(selectedReview.getTitle());
            textField.setText(selectedReview.getText());
            ratingField.setText(String.valueOf(selectedReview.getRating()));
        }
    }

    @FXML
    private void updateSelectedReview() {
        if (selectedReview != null && selectedRestaurant != null) {
            selectedReview.setTitle(titleField.getText());
            selectedReview.setText(textField.getText());
            selectedReview.setRating(Double.parseDouble(ratingField.getText()));

            backendService.updateReview(selectedRestaurant.getId(), selectedReview.getId(), selectedReview)
                    .thenRun(() -> {
                        javafx.application.Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Update Successful");
                            alert.setHeaderText(null);
                            alert.setContentText("The Review has been successfully updated.");
                            alert.showAndWait();

                            loadReviewsForSelectedRestaurant();
                        });
                    })
                    .exceptionally(ex -> {
                        javafx.application.Platform.runLater(() -> {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Update Failed");
                            errorAlert.setHeaderText("Failed to update the review");
                            errorAlert.setContentText("An error occurred while trying to update the review. Please try again.");
                            errorAlert.showAndWait();
                        });
                        return null;
                    });
        }
    }


    @FXML
    private void deleteSelectedReview() {
        if (selectedReview != null && selectedRestaurant != null) {
            backendService.deleteReview(selectedRestaurant.getId(), selectedReview.getId())
                    .thenRun(() -> {
                        javafx.application.Platform.runLater(() -> {
                            reviewListView.getItems().remove(selectedReview);
                            clearReviewDetails();
                        });
                    })
                    .exceptionally(ex -> {
                        javafx.application.Platform.runLater(() -> {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Delete Failed");
                            errorAlert.setHeaderText("Failed to delete the review");
                            errorAlert.setContentText("An error occurred while trying to delete the restaurant. Please try again.");
                            errorAlert.showAndWait();
                        });
                        return null;
                    });
        }
    }

    private void clearReviewDetails() {
        titleField.clear();
        textField.clear();
        ratingField.clear();
        selectedReview = null;
    }
}

