package model;

import java.io.Serializable;
import java.util.Set;

// Abstract Class: Base for inheritance hierarchy
// SOLID: Open-Closed Principle (Open for extension via subclasses)
public abstract class Anime implements Serializable {
    private String title;
    private int releaseYear;
    private String studio;
    private int episodeCount;
    private Status status;
    private int rating;
    private Set<Genre> genres;

    public Anime(String title, int releaseYear, String studio, int episodeCount,
                 Status status, int rating, Set<Genre> genres) {
        // GRASP: Information Expert - The object validates its own internal state
        setTitle(title);
        this.releaseYear = releaseYear;
        this.studio = studio;
        this.episodeCount = episodeCount;
        this.status = status;
        setRating(rating);
        this.genres = genres;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        this.title = title;
    }

    public int getReleaseYear() { return releaseYear; }
    public String getStudio() { return studio; }
    public int getEpisodeCount() { return episodeCount; }
    public Status getStatus() { return status; }

    public int getRating() { return rating; }

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.rating = rating;
    }

    public Set<Genre> getGenres() { return genres; }
}