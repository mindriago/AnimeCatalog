package model;

import java.util.Set;

// SOLID: Liskov Substitution Principle (LSP) - Extends functionality without breaking parent contract
public class AnimeMovie extends Anime {
    private int duration; // Specific to Movies

    public AnimeMovie(String title, int releaseYear, String studio, int episodeCount,
                      Status status, int rating, Set<Genre> genres, int duration) {
        super(title, releaseYear, studio, episodeCount, status, rating, genres);
        if (duration <= 0) throw new IllegalArgumentException("Duration must be positive");
        this.duration = duration;
    }

    public int getDuration() { return duration; }
}