package com.frontend.niis_front.dto;

public class ReviewDTO {
    private String id;
    private String title;
    private String text;
    private Number rating;
    private String hardwareId;

    public ReviewDTO() {
    }

    public ReviewDTO(String id, String title, String text, Number rating, String hardwareId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.rating = rating;
        this.hardwareId = hardwareId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Number getRating() {
        return rating;
    }

    public void setRating(Number rating) {
        this.rating = rating;
    }

    public String getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    @Override
    public String toString() {
        return "ReviewDTO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", rating=" + rating +
                ", hardwareId='" + hardwareId + '\'' +
                '}';
    }
}
