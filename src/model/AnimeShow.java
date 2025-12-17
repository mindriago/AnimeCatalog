package model;

import java.util.Set;

// SOLID: Liskov Substitution Principle (LSP): extends functionality without breaking parent contract
public class AnimeShow extends Anime {
    private boolean isAiring; // Specific to Shows

    public AnimeShow(String title, int releaseYear, String studio, int episodeCount,
                     Status status, int rating, Set<Genre> genres, boolean isAiring) {
        super(title, releaseYear, studio, episodeCount, status, rating, genres);
        this.isAiring = isAiring;
    }

    public boolean isAiring() { return isAiring; }
}