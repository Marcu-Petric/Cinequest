package com.example.proiect;

public class Review {

    private Integer reviewID;
    private String movie;

    private String username;

    private Integer rating;

    private String thoughts;

    private String actual_review;

    private Integer likesCount;

    private Integer dislikeCount;

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(Integer dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public Review(Integer reviewID, String username, Integer rating, String thoughts, String actual_review, Integer likesCount, Integer dislikeCount) {
        this.reviewID = reviewID;
        this.username = username;
        this.rating = rating;
        this.thoughts = thoughts;
        this.actual_review = actual_review;
        this.likesCount = likesCount;
        this.dislikeCount = dislikeCount;
    }

    public Review(Integer reviewID, Integer rating, String movie, String actual_review, Integer likesCount, Integer dislikeCount) {
        this.reviewID = reviewID;
        this.movie = movie;
        this.rating = rating;
        this.actual_review = actual_review;
        this.likesCount = likesCount;
        this.dislikeCount = dislikeCount;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public Integer getReviewID() {
        return reviewID;
    }

    public void setReviewID(Integer reviewID) {
        this.reviewID = reviewID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getThoughts() {
        return thoughts;
    }

    public void setThoughts(String thoughts) {
        this.thoughts = thoughts;
    }

    public String getActual_review() {
        return actual_review;
    }

    public void setActual_review(String actual_review) {
        this.actual_review = actual_review;
    }
}
