package com.frontend.niis_front.service;

import com.frontend.niis_front.dto.RestaurantDTO;
import com.frontend.niis_front.dto.ReviewDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BackendService {

    private final HttpClient httpClient = HttpClient.newBuilder().build();
    private final String baseUrl = "http://localhost:8080";
    private final Gson gson = new Gson();

    public CompletableFuture<List<RestaurantDTO>> getAllRestaurant() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/restaurant"))
                .GET()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(jsonString -> {
                    Type listType = new TypeToken<List<RestaurantDTO>>() {
                    }.getType();
                    return gson.fromJson(jsonString, listType);
                });
    }


    public CompletableFuture<RestaurantDTO> createRestaurant(RestaurantDTO restaurant) {
        String restaurantJson = gson.toJson(restaurant);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/restaurant"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(restaurantJson))
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200 || response.statusCode() == 201) {
                        return gson.fromJson(response.body(), RestaurantDTO.class);
                    } else {
                        Logger.getLogger(BackendService.class.getName()).log(Level.SEVERE, "Failed to create restaurant: " + response.body());
                        return null;
                    }
                })
                .exceptionally(ex -> {
                    Logger.getLogger(BackendService.class.getName()).log(Level.SEVERE, "Error creating restaurant", ex);
                    return null;
                });
    }

    public CompletableFuture<Void> updateRestaurant(String id, RestaurantDTO restaurant) {
        String restaurantJson = gson.toJson(restaurant);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/restaurant/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(restaurantJson))
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                .thenApply(response -> null);
    }

    public CompletableFuture<Void> deleteRestaurant(String restaurantId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/restaurant/" + restaurantId))
                .DELETE()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                .thenApply(response -> null);
    }

    public CompletableFuture<String> getReviewsForRestaurant(String restaurantId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/restaurant/" + restaurantId + "/reviews"))
                .GET()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }

    public CompletableFuture<ReviewDTO> createReview(String restaurantId, ReviewDTO review) {
        String reviewJson = gson.toJson(review);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/restaurant/" + restaurantId + "/reviews"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(reviewJson))
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> gson.fromJson(response.body(), ReviewDTO.class));
    }

    public CompletableFuture<Void> updateReview(String restaurantId, String reviewId, ReviewDTO review) {
        String reviewJson = gson.toJson(review);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/restaurant/" + restaurantId + "/reviews/" + reviewId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(reviewJson))
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                .thenApply(response -> null);
    }

    public CompletableFuture<Void> deleteReview(String restaurantId, String reviewId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/restaurant/" + restaurantId + "/reviews/" + reviewId))
                .DELETE()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                .thenApply(response -> null);
    }
}
