package com.example.proiect;

public class Movie {
    private String title;

    private Integer userRating;

    private Integer year;

    private String director;

    public Integer getUserRating() {
        return userRating;
    }

    public void setUserRating(Integer userRating) {
        this.userRating = userRating;
    }

    public Movie(String title, Integer userRating, Integer year, String director, String description, String genre, Double avrRating, Integer id) {
        this.title = title;
        this.userRating = userRating;
        this.year = year;
        this.director = director;
        this.description = description;
        this.genre = genre;
        this.avrRating = avrRating;
        this.id = id;
    }

    private String description;

    private String genre;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Double avrRating;

    private Integer id;

    public Movie(String title, Integer year, String director, String description, String genre, Double avrRating, Integer id) {
        this.title = title;
        this.year = year;
        this.director = director;
        this.description = description;
        this.genre = genre;
        this.avrRating = avrRating;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getAvrRating() {
        return avrRating;
    }

    public void setAvrRating(Double avrRating) {
        this.avrRating = avrRating;
    }
}
